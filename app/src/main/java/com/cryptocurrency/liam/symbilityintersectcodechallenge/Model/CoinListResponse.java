package com.cryptocurrency.liam.symbilityintersectcodechallenge.Model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Wrapper response for api coinList request
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
    //private List<CryptoCurrency> dataList = new ArrayList<>();
    //private List<String> keyList = new ArrayList<>();

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

    public HashMap<String, CryptoCurrency> getCryptoCurrencyList() {
        Log.i("xxx", "lalala " + cryptoCurrencyList.size());
       /*dataList.addAll( cryptoCurrencyList.values());
       keyList.addAll(cryptoCurrencyList.keySet());
       return dataList;*/
       return cryptoCurrencyList;
    }
/*
    public List<String> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<String> keyList) {
        this.keyList = keyList;
    }
    */
}


