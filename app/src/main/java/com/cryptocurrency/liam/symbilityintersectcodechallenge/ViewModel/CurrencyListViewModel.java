package com.cryptocurrency.liam.symbilityintersectcodechallenge.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.cryptocurrency.liam.symbilityintersectcodechallenge.CoinRepository;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.Model.CoinListResponse;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.Model.CryptoCurrency;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.Service.APIInterface;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.Service.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Liam on 2018-02-03.
 */

public class CurrencyListViewModel extends AndroidViewModel {
    private final String TAG = getClass().getName();
    private CoinRepository coinRepository;
    private LiveData<List<CryptoCurrency>> cryptoCurrencyList = null;

    public CurrencyListViewModel(Application application){
        super(application);
        coinRepository = CoinRepository.getCoinRepository();
    }

    public LiveData<List<CryptoCurrency>> getCurrencyList(){
        Log.i(TAG, "xxx ia m ere");
        if(cryptoCurrencyList == null ){
            cryptoCurrencyList = coinRepository.loadList();
        }

        return cryptoCurrencyList;
    }



}
