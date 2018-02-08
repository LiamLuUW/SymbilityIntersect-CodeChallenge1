package com.cryptocurrency.liam.symbilityintersectcodechallenge.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.cryptocurrency.liam.symbilityintersectcodechallenge.R;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.ViewModel.CurrencyListViewModel;

/**
 * Activity fot splash screen
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        setContentView(R.layout.splash_view);

        // get currency list from server
        CurrencyListViewModel currencyListViewModel = ViewModelProviders.of(this).get(CurrencyListViewModel.class);
        currencyListViewModel.prepareListFromServer();

        // start the root activity once the currency list is loaded from the server
        currencyListViewModel.setListReadyWrapperListener(new CurrencyListViewModel.ListReadyWrapperListener() {
            @Override
            public void onListReady() {
                Intent i = new Intent(SplashActivity.this, RootActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
