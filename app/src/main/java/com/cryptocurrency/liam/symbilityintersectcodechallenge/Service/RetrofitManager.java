package com.cryptocurrency.liam.symbilityintersectcodechallenge.Service;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Liam on 2018-02-02.
 */

public class RetrofitManager {
    private static final String TAG = "RETROFIT_MANAGER";
    private static final String COIN_LIST_URL = "https://www.cryptocompare.com/api/data/coinlist/";
    private static final String BASE_URL = "https://min-api.cryptocompare.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {

        if (retrofit==null) {
            // add an interceptor to log detail server response
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    //.client(httpClient.build())
                    .build();
        }
        return  retrofit;
    }

    public static String getCoinListUrl(){
        return  COIN_LIST_URL;
    }


}
