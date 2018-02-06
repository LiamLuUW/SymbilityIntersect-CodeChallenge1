package com.cryptocurrency.liam.symbilityintersectcodechallenge.UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cryptocurrency.liam.symbilityintersectcodechallenge.Model.CryptoCurrency;
import com.cryptocurrency.liam.symbilityintersectcodechallenge.R;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView adapter for currency list
 */

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder> {
    private List<CryptoCurrency> cryptoCurrencyList;
    private OnLikeClickedListener onLikeClickedListener;

    public CurrencyListAdapter() {
        cryptoCurrencyList = new ArrayList<>();
    }

    public void setOnLikeClickedListener(OnLikeClickedListener listener) {
        this.onLikeClickedListener = listener;
    }

    @Override
    public CurrencyViewHolder onCreateViewHolder(ViewGroup viewGroup, int pos) {
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.currency_item_view, viewGroup, false);
        return new CurrencyViewHolder(view, onLikeClickedListener);
    }

    @Override
    public void onBindViewHolder(final CurrencyViewHolder viewHolder, int pos) {
        final CryptoCurrency cryptoCurrency = cryptoCurrencyList.get(pos);
        final String currencyName = cryptoCurrency.getCoinName();
        final String currencyPrice = cryptoCurrency.getPrice();
        final boolean isLiked = cryptoCurrency.isLiked();

        if (currencyName != null) viewHolder.currencyName.setText(currencyName);
        if (currencyPrice == null){
            viewHolder.currencyPrice.setText("No Price Data Available");
        }else{
            viewHolder.currencyPrice.setText(currencyPrice);
        }

        if (isLiked) {
            viewHolder.likedStar.setVisibility(View.VISIBLE);
            viewHolder.unLikedStar.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.unLikedStar.setVisibility(View.VISIBLE);
            viewHolder.likedStar.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return (cryptoCurrencyList == null) ? 0 : cryptoCurrencyList.size();
    }

    public void setCryptoCurrencyList(List<CryptoCurrency> cryptoCurrencyList) {
        this.cryptoCurrencyList = cryptoCurrencyList;
    }

    static class CurrencyViewHolder extends RecyclerView.ViewHolder {
        TextView currencyName;
        TextView currencyPrice;
        ImageView likedStar;
        ImageView unLikedStar;
        FrameLayout buttonLike;

        CurrencyViewHolder(View view, final OnLikeClickedListener onLikeClickedListener) {
            super(view);
            currencyName = view.findViewById(R.id.currency_name);
            currencyPrice = view.findViewById(R.id.currency_price);
            likedStar = view.findViewById(R.id.image_like);
            unLikedStar = view.findViewById(R.id.image_unlike);
            buttonLike = view.findViewById(R.id.button_like);
            buttonLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onLikeClickedListener != null)
                        onLikeClickedListener.onLikeClicked(getAdapterPosition());
                }
            });
        }

    }
}

interface OnLikeClickedListener {
    void onLikeClicked(int itemPosition);
}
