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
        setObserveData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceBundle){
        final View view = inflater.inflate(R.layout.currency_list_fragment_view, viewGroup, false);
        RecyclerView recyclerView = view.findViewById(R.id.currency_list);
        recyclerView.setAdapter(currencyListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return  view;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        currencyListViewModel.clearRepoCache();
    }

    public void setObserveData(){
        if(currencyListViewModel != null) {
            assert (getActivity()!= null);
            currencyListViewModel.getCurrencyList().observe(getActivity(), new Observer<List<CryptoCurrency>>() {
                @Override
                public void onChanged(@Nullable List<CryptoCurrency> cryptoCurrencies) {
                    currencyListAdapter.setCryptoCurrencyList(cryptoCurrencies);
                    currencyListAdapter.notifyDataSetChanged();
                }
            });
        }
    }

}
