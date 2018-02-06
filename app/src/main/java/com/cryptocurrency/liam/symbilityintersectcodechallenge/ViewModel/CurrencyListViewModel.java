package com.cryptocurrency.liam.symbilityintersectcodechallenge.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.cryptocurrency.liam.symbilityintersectcodechallenge.CoinRepository;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.Model.CryptoCurrency;

import java.util.List;

/**
 * ViewModel for the currencyList fragment
 */

public class CurrencyListViewModel extends AndroidViewModel {
    private final String TAG = getClass().getName();
    private CoinRepository coinRepository;
    private MutableLiveData<List<CryptoCurrency>> cryptoCurrencyList = null;

    public CurrencyListViewModel(Application application){
        super(application);
        coinRepository = CoinRepository.getCoinRepository();
    }

    public LiveData<List<CryptoCurrency>> getCurrencyList(){
        if(cryptoCurrencyList == null ){
            cryptoCurrencyList = coinRepository.loadList();
        }

        return cryptoCurrencyList;
    }

    public void changeLikeStatus(int pos){
        coinRepository.changeCurrencyLikeStatus(pos);
    }



}
