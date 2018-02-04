package com.cryptocurrency.liam.symbilityintersectcodechallenge;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.cryptocurrency.liam.symbilityintersectcodechallenge.Model.CoinListResponse;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.Model.CryptoCurrency;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.Service.APIInterface;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.Service.RetrofitManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Liam on 2018-02-03.
 */

public class CoinRepository {
    private final static String TAG = "CoinRepository";
    private static CoinRepository coinRepository = null;
    private Retrofit retrofit;
    private static APIInterface apiInterface = null;

    private CoinRepository(){
        retrofit = RetrofitManager.getRetrofit();
        apiInterface = retrofit.create(APIInterface.class);
    }

    public static CoinRepository getCoinRepository() {
        if(coinRepository == null) {
            coinRepository = new CoinRepository();
        }
        return coinRepository;
    }
    /*public APIInterface getAPIInterface(){
        return (apiInterface == null) ? createAPIInterface() : apiInterface;
    }


    private APIInterface createAPIInterface() {
        if(retrofit != null){
            Log.i(TAG, "API instance created");
            apiInterface = retrofit.create(APIInterface.class);
            return apiInterface;
        }else{
            //error case
            Log.i(TAG, "API instance create error");
            return null;
        }
    }*/

    public  LiveData<List<CryptoCurrency>> loadList(){
        Log.i(TAG, "xxx ia m ere2222");
        final MutableLiveData<List<CryptoCurrency>> liveList = new MutableLiveData<>();
        final String coinListURL = RetrofitManager.getCoinListUrl();
        Call<CoinListResponse> call = apiInterface.getCoinList(coinListURL);
        call.enqueue(new Callback<CoinListResponse>() {
            @Override
            public void onResponse(Call<CoinListResponse> call, Response<CoinListResponse> response) {
                CoinListResponse coinListResponse = response.body();
                Log.i(TAG, "xxx ia m ere3333 " + coinListResponse.getBaseImageUrl());
                if(coinListResponse == null){
                    Log.e(TAG, "Error: empty response body received, just ignore");
                    return;
                }
                List<CryptoCurrency> temp = coinListResponse.getCryptoCurrencyList();
                Log.i(TAG, "xxxx " + temp.size());
                liveList.setValue(coinListResponse.getCryptoCurrencyList());
            }

            @Override
            public void onFailure(Call<CoinListResponse> call, Throwable t) {
                Log.e(TAG, "Error: CoinList response onFailure " + t.getMessage());
                return;
            }
        });

        return liveList;
    }
}
