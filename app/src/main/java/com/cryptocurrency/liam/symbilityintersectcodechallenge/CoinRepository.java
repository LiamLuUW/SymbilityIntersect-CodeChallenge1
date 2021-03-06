package com.cryptocurrency.liam.symbilityintersectcodechallenge;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cryptocurrency.liam.symbilityintersectcodechallenge.DB.AppDataBase;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.Model.CoinListResponse;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.Model.CryptoCurrency;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.Service.APIInterface;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.Service.RetrofitManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * CoinRepository for handling crypto currency objects from server
 */

public class CoinRepository {
    private final static String TAG = "CoinRepository";
    private final static int MAX_THREAD_AMOUNT = 10;
    private final static String CURRENCY_FORMAT = "$##,###.######";
    private final static String DISPLAY_CURRENCY = "CAD";

    private static CoinRepository coinRepository = null;
    private static APIInterface apiInterface = null;
    private static AppDataBase appDataBase;
    private static Handler mainHandler;
    private static ListReadyListener listReadyListener;

    private final static MutableLiveData<List<CryptoCurrency>> liveList = new MutableLiveData<>();
    private static HashMap<String, CryptoCurrency> currencyHashMap;
    private static List<CryptoCurrency> dataList = new ArrayList<>();
    private static HashSet<Long> favouredList;

    private CoinRepository(Context context) {
        Retrofit retrofit = RetrofitManager.getRetrofit();
        apiInterface = retrofit.create(APIInterface.class);
        mainHandler = new Handler();
        appDataBase = AppDataBase.getInstance(context);
    }

    public static CoinRepository getCoinRepository(Context context) {
        if (coinRepository == null) {
            coinRepository = new CoinRepository(context);
        }
        //load previous liked currency ids from database
        new DBGetLikedCurrency(appDataBase).execute();
        return coinRepository;
    }

    // custom comparator to sort list upon sortOrder and like/unlike
    private Comparator<CryptoCurrency> favourComparator = new Comparator<CryptoCurrency>() {
        @Override
        public int compare(CryptoCurrency c1, CryptoCurrency c2) {
            if (favouredList.contains(c1.getId())) {
                c1.setLiked(true);
                return -1;
            }
            if (favouredList.contains(c2.getId())) {
                c2.setLiked(true);
                return 1;
            }
            return c1.getSortOrder() - c2.getSortOrder();
        }
    };

    //method to be called when UI noticed a click on Like/Unlike button
    public void changeCurrencyLikeStatus(int pos) {
        if (liveList.getValue() != null) {
            CryptoCurrency currency = dataList.get(pos);
            if (currency.isLiked()) {
                favouredList.remove(currency.getId());
            } else {
                favouredList.add(currency.getId());
            }
            currency.setLiked(!currency.isLiked());
        }
        Collections.sort(dataList, favourComparator);
        liveList.setValue(dataList);
    }

    //method to display the currency list as livedata for UI observers
    public MutableLiveData<List<CryptoCurrency>> displayCurrencyList() {
        return liveList;
    }

    // utility method to clear all ram cache
    private void clearAllCache() {
        if (dataList != null) dataList.clear();
        if (currencyHashMap != null) currencyHashMap.clear();
    }

    //method used to store currency data into database
    public synchronized void storeData() {
        new DBInsertCurrencyTask(appDataBase).execute(dataList);
    }

    // method used to load currency list and prices from the server
    public void loadList() {
        Log.v(TAG, "load currency list from server");
        clearAllCache();
        final String coinListURL = RetrofitManager.getCoinListUrl();
        Call<CoinListResponse> call = apiInterface.getCoinList(coinListURL);
        call.enqueue(new Callback<CoinListResponse>() {
            @Override
            public void onResponse(@NonNull Call<CoinListResponse> call, @NonNull Response<CoinListResponse> response) {
                CoinListResponse coinListResponse = response.body();
                Log.v(TAG, "currency list response received");
                if (coinListResponse != null) {
                    currencyHashMap = coinListResponse.getCryptoCurrencyList();
                    dataList.addAll(currencyHashMap.values());
                    Collections.sort(dataList, favourComparator);
                    liveList.setValue(dataList);
                    findAllPrices(new ArrayList<>(currencyHashMap.keySet()));
                    listReadyListener.notifyListReady();
                }

            }

            @Override
            public void onFailure(@NonNull Call<CoinListResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Error: CoinList response onFailure " + t.getMessage());
            }
        });

    }

    //helper method used to load all prices
    private void findAllPrices(List<String> data) {
        List<String> stringRequests = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < data.size()) {
            /* we send every 30 currencies for a request, so we manually parsing 30 symbols here */
            for (int j = 0; j < 30; j++) {
                if (i + j >= data.size()) break;
                String temp = data.get(i + j);
                sb.append(temp);
                if (j != 29) sb.append(",");
            }
            stringRequests.add(sb.toString());
            sb.setLength(0);
            i += 30;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_AMOUNT);
        for (String stringRequest : stringRequests) {
            String toCurrency = stringRequest.replace("*", "");
            try {
                executorService.submit(new getPricesRunnable(toCurrency));
            } catch (Exception ex) {
                Log.i(TAG, "execute runnable failed");
            }
        }
    }


    private static class getPricesRunnable implements Runnable {
        private final String TAG_R1 = getClass().getName();
        String toCurrency;
        HashMap<String, Double> priceList;
        DecimalFormat format;

        getPricesRunnable(String toCurrency) {
            this.toCurrency = toCurrency;
            format = new DecimalFormat(CURRENCY_FORMAT);
        }

        private void loadPrices() {
            if (toCurrency != null) {
                Response<HashMap<String, Double>> response = null;
                Call<HashMap<String, Double>> call = apiInterface.getPrices(DISPLAY_CURRENCY, toCurrency);
                try {
                    response = call.execute();
                } catch (Exception ex) {
                    Log.e(TAG_R1, "Error: PriceList response onFailure " + ex.getMessage());
                }

                if (response != null && response.isSuccessful()) {
                    priceList = response.body();

                }
            }

        }

        private synchronized void bindPriceWithCurrency() {
            for (final String key : priceList.keySet()) {
                if (currencyHashMap.get(key) != null) {
                    Double price = priceList.get(key);
                    currencyHashMap.get(key).setPrice(format.format(1 / price));
                }
            }
        }

        @Override
        public void run() {
            loadPrices();
            bindPriceWithCurrency();
            mainHandler.post(new notifyUI());
        }
    }

    private static class notifyUI implements Runnable {
        @Override
        public void run() {
            //update livedata
            liveList.setValue(dataList);
        }

    }

    public static void setListReadyListener(ListReadyListener listener) {
        listReadyListener = listener;
    }

    public interface ListReadyListener {
        void notifyListReady();
    }

    /*AsyncTasks for db operations*/
    private static class DBInsertCurrencyTask extends AsyncTask<List<CryptoCurrency>, Void, Void> {

        private AppDataBase mDB;

        DBInsertCurrencyTask(AppDataBase dataBase) {
            mDB = dataBase;
        }

        @Override
        protected Void doInBackground(final List<CryptoCurrency>... params) {
            mDB.getCryptoCurrencyDao().insert(params[0]);
            return null;
        }

    }

    private static class DBGetLikedCurrency extends AsyncTask<Void, Void, List<Long>> {

        private AppDataBase mDB;

        DBGetLikedCurrency(AppDataBase dataBase) {
            mDB = dataBase;
        }

        @Override
        protected List<Long> doInBackground(Void... args) {
            return mDB.getCryptoCurrencyDao().getLikedCurrencies();
        }

        @Override
        protected void onPostExecute(List<Long> currencyList) {
            if (currencyList != null) {
                favouredList = new HashSet<>(currencyList);
            } else {
                favouredList = new HashSet<>();
            }
        }


    }
}
