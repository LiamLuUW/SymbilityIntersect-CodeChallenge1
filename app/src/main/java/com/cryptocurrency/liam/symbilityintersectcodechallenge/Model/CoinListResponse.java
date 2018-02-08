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
        return cryptoCurrencyList;
    }
}


