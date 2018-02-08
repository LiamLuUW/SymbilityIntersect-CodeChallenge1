package com.cryptocurrency.liam.symbilityintersectcodechallenge.Service;

import com.cryptocurrency.liam.symbilityintersectcodechallenge.Model.CoinListResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * API Interface for Retrofit
 */

public interface APIInterface {
    @GET
    Call<CoinListResponse> getCoinList(@Url String url);

    @GET("data/price")
    Call<HashMap<String, Double>> getPrices(@Query("fsym") String currencies, @Query("tsyms") String toCurrencies);

}
