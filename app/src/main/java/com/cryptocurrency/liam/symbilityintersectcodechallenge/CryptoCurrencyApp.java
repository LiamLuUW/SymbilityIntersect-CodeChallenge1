package com.cryptocurrency.liam.symbilityintersectcodechallenge;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * application wrapper for using Stetho
 */

public class CryptoCurrencyApp extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

    }
}
