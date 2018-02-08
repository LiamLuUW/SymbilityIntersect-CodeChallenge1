package com.cryptocurrency.liam.symbilityintersectcodechallenge.UI;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.cryptocurrency.liam.symbilityintersectcodechallenge.R;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.UI.CurrencyListFragment;

/*
    Root Activity, a container for currency list fragment
 */
public class RootActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        CurrencyListFragment currencyListFragment = new CurrencyListFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, currencyListFragment).commit();
    }
}
