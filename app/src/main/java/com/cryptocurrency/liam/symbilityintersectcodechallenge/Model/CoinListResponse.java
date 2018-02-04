package com.cryptocurrency.liam.symbilityintersectcodechallenge.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Liam on 2018-02-02.
 */

public class CoinListResponse {

    @SerializedName("Response")
    private String response;
    @SerializedName("BaseImageUrl")
    private String baseImageUrl;
    @SerializedName("BaseLinkUrl")
    private String baseLinkUrl;
    @SerializedName("Data")
    private HashMap<String, CryptoCurrency> cryptoCurrencyList;
    private List<CryptoCurrency> dataList = new ArrayList<>();
    private List<String> keyList = new ArrayList<>();

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getBaseImageUrl() {
        return baseImageUrl;
    }

    public void setBaseImageUrl(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    public String getBaseLinkUrl() {
        return baseLinkUrl;
    }

    public void setBaseLinkUrl(String baseLinkUrl) {
        this.baseLinkUrl = baseLinkUrl;
    }

    public List<CryptoCurrency> getCryptoCurrencyList() {
        Log.i("xxx", "lalala " + cryptoCurrencyList.size());
       dataList.addAll( cryptoCurrencyList.values());
       keyList.addAll(cryptoCurrencyList.keySet());
       return dataList;
    }


}


