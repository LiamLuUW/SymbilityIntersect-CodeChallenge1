package com.cryptocurrency.liam.symbilityintersectcodechallenge;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cryptocurrency.liam.symbilityintersectcodechallenge.Model.CoinListResponse;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.Model.CryptoCurrency;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.Service.APIInterface;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.Service.RetrofitManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
    private static CoinRepository coinRepository = null;
    private static APIInterface apiInterface = null;
    private final static MutableLiveData<List<CryptoCurrency>> liveList = new MutableLiveData<>();
    private static HashMap<String, CryptoCurrency> currencyHashMap;
    private List<String> keyList = new ArrayList<>();
    private static List<CryptoCurrency> dataList = new ArrayList<>();
    private static Handler mainHandler;
    private ListReadyListener listReadyListener;

    private CoinRepository() {
        Retrofit retrofit = RetrofitManager.getRetrofit();
        apiInterface = retrofit.create(APIInterface.class);
        mainHandler = new Handler();
    }

    public static CoinRepository getCoinRepository() {
        if (coinRepository == null) {
            coinRepository = new CoinRepository();
        }
        return coinRepository;
    }

    private Comparator<CryptoCurrency> currencyComparator = new Comparator<CryptoCurrency>() {
        @Override
        public int compare(CryptoCurrency c1, CryptoCurrency c2) {
            return c1.getSortOrder() - c2.getSortOrder();
        }
    };

    public void changeCurrencyLikeStatus(int pos) {
        if (liveList.getValue() != null) {
            dataList = liveList.getValue();
            CryptoCurrency currency = dataList.remove(pos);
            if (currency.isLiked()) {
                dataList.add(currency.getSortOrder(), currency);
            } else {
                dataList.add(0, currency);
            }
            currency.setLiked(!currency.isLiked());
        }
        liveList.setValue(dataList);
    }

    public MutableLiveData<List<CryptoCurrency>> displayCurrencyList() {
        return liveList;
    }

    public void clearAllCache() {
        if (dataList != null) dataList.clear();
        if (keyList != null) keyList.clear();
        if (currencyHashMap != null) currencyHashMap.clear();
    }

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
                    Collections.sort(dataList, currencyComparator);
                    liveList.setValue(dataList);
                    keyList.addAll(currencyHashMap.keySet());

                    findAllPrices(keyList);
                    listReadyListener.notifyListReady();
                }

            }

            @Override
            public void onFailure(@NonNull Call<CoinListResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Error: CoinList response onFailure " + t.getMessage());
            }
        });

        //return liveList;
    }

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

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (String stringRequest : stringRequests) {
            String toCurrency = stringRequest.replace("*", "");
            try {
                executorService.submit(new getPricesRunnable(toCurrency, currencyHashMap));
            } catch (Exception ex) {
                Log.i(TAG, "execute runnable failed");
            }
        }
    }


    private static class getPricesRunnable implements Runnable {
        private final String TAG_R1 = getClass().getName();
        private final String fromCurrency = "CAD";
        String toCurrency;
        HashMap<String, CryptoCurrency> currencyList;
        HashMap<String, Double> priceList;
        DecimalFormat format;

        getPricesRunnable(String toCurrency, HashMap<String, CryptoCurrency> currencyList) {
            Log.v(TAG_R1, "new thread for requesting price created");
            this.toCurrency = toCurrency;
            this.currencyList = currencyList;
            format = new DecimalFormat("$##,###.######");
        }

        private void loadPrices() {
            if (toCurrency != null) {
                Response<HashMap<String, Double>> response = null;
                Call<HashMap<String, Double>> call = apiInterface.getPrices(fromCurrency, toCurrency);
                try {
                    response = call.execute();
                } catch (Exception ex) {
                    Log.e(TAG, "Error: PriceList response onFailure " + ex.getMessage());
                }

                if (response != null && response.isSuccessful()) {
                    priceList = response.body();

                }
            }

        }

        private synchronized void bindPriceWithCurrency() {
            for (final String key : priceList.keySet()) {
                if (currencyList.get(key) != null) {
                    Double price = priceList.get(key);
                    currencyList.get(key).setPrice(format.format(1 / price));
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
        private final static String TAG_R2 = "notifyUI_RUNNABLE";

        notifyUI() {
            // Log.v(TAG_R2, "called");
        }

        @Override
        public void run() {
            //update livedata
            dataList.addAll(currencyHashMap.values());
            liveList.setValue(dataList);
        }

    }

    public void setListReadyListener(ListReadyListener listener){
        this.listReadyListener = listener;
    }

    public interface ListReadyListener{
        void notifyListReady();
    }
}
