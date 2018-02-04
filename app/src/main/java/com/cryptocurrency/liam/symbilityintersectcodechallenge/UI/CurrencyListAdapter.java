package com.cryptocurrency.liam.symbilityintersectcodechallenge.UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cryptocurrency.liam.symbilityintersectcodechallenge.Model.CryptoCurrency;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liam on 2018-02-03.
 */

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder> {
    private List<CryptoCurrency> cryptoCurrencyList;

    public CurrencyListAdapter(){
        cryptoCurrencyList = new ArrayList<>();
    }

    @Override
    public CurrencyViewHolder onCreateViewHolder(ViewGroup viewGroup, int pos){
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.currency_item_view, viewGroup, false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder (final CurrencyViewHolder viewHolder, int pos){
        final CryptoCurrency cryptoCurrency = cryptoCurrencyList.get(pos);
        final String currencyName = cryptoCurrency.getCoinName();
        final String currencyPrice = cryptoCurrency.getPrice();
        final boolean isLiked = cryptoCurrency.isLiked();

        if(currencyName!= null)viewHolder.currencyName.setText(currencyName);
        if(currencyPrice!= null)viewHolder.currencyPrice.setText(currencyPrice);
        if(isLiked){
            viewHolder.likedStar.setVisibility(View.VISIBLE);
            viewHolder.unLikedStar.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.unLikedStar.setVisibility(View.VISIBLE);
            viewHolder.likedStar.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return (cryptoCurrencyList == null)? 0 : cryptoCurrencyList.size();
    }

    public void setCryptoCurrencyList(List<CryptoCurrency> cryptoCurrencyList){
        this.cryptoCurrencyList = cryptoCurrencyList;
    }

    static class CurrencyViewHolder extends RecyclerView.ViewHolder{
        TextView currencyName;
        TextView currencyPrice;
        ImageView likedStar;
        ImageView unLikedStar;

        CurrencyViewHolder(View view){
            super(view);
            currencyName = view.findViewById(R.id.currency_name);
            currencyPrice = view.findViewById(R.id.currency_price);
            likedStar = view.findViewById(R.id.image_like);
            unLikedStar = view.findViewById(R.id.image_unlike);
        }

    }
}
