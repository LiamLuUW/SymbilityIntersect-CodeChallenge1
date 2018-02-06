package com.cryptocurrency.liam.symbilityintersectcodechallenge.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cryptocurrency.liam.symbilityintersectcodechallenge.Model.CryptoCurrency;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.R;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.ViewModel.CurrencyListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment UI for displaying the currency list
 */

public class CurrencyListFragment extends Fragment {
    final String TAG = getClass().getName();
    private CurrencyListAdapter currencyListAdapter;
    private CurrencyListViewModel currencyListViewModel;

    @Override
    public void onCreate(Bundle savedInstanceBundle){
        Log.i(TAG, "created");
        super.onCreate(savedInstanceBundle);
        currencyListAdapter = new CurrencyListAdapter();
        OnLikeClickedListener onLikeClickedListener = new OnLikeClickedListener() {
            @Override
            public void onLikeClicked(int pos) {
                currencyListViewModel.changeLikeStatus(pos);
            }
        };
        currencyListAdapter.setOnLikeClickedListener(onLikeClickedListener);
        currencyListViewModel = ViewModelProviders.of(getActivity()).get(CurrencyListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceBundle){
        final View view = inflater.inflate(R.layout.currency_list_fragment_view, viewGroup, false);
        RecyclerView recyclerView = view.findViewById(R.id.currency_list);
        recyclerView.setAdapter(currencyListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //setFakeData();
        setObserveData();

        return  view;
    }

    @Override
    public void onResume(){
        super.onResume();
    }


    public void setObserveData(){
        if(currencyListViewModel != null) {
            assert (getActivity()!= null);
            currencyListViewModel.getCurrencyList().observe(getActivity(), new Observer<List<CryptoCurrency>>() {
                @Override
                public void onChanged(@Nullable List<CryptoCurrency> cryptoCurrencies) {
                    Log.i("xxx", "notifed change of observing data");
                    currencyListAdapter.setCryptoCurrencyList(cryptoCurrencies);
                    currencyListAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public void setFakeData(){
        List<CryptoCurrency> testList = new ArrayList<>();
        CryptoCurrency c1 = new CryptoCurrency();
        c1.setFullName("Test Coin 1");
        c1.setName("aa");
        c1.setPrice("12.222");
        CryptoCurrency c2 = new CryptoCurrency();
        c2.setFullName("Test Coin 2");
        c2.setName("ab");
        c2.setPrice("12367.111");
        testList.add(c2);
        testList.add(c1);
        Log.i(TAG, "setting data size of " + testList.size());
        currencyListAdapter.setCryptoCurrencyList(testList);
        currencyListAdapter.notifyDataSetChanged();
    }


}
