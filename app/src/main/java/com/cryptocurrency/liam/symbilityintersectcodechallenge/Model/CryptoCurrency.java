package com.cryptocurrency.liam.symbilityintersectcodechallenge.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Crypto currency object
 */

@Entity(tableName = "crypto_currency",
        indices = {@Index(value = "is_liked")})
public class CryptoCurrency {

    @PrimaryKey
    @SerializedName("Id")
    private long id;

    @SerializedName("Url")
    private String url;

    @ColumnInfo(name = "image_url")
    @SerializedName("ImageUrl")
    private String imageUrl;

    @SerializedName("Name")
    private String name;

    @SerializedName("Symbol")
    private String symbol;

    @ColumnInfo(name = "coin_name")
    @SerializedName("CoinName")
    private String coinName;

    @ColumnInfo(name = "full_name")
    @SerializedName("FullName")
    private String fullName;

    @SerializedName("Algorithm")
    private String algorithm;

    @ColumnInfo(name = "proof_type")
    @SerializedName("ProofType")
    private String proofType;

    @ColumnInfo(name = "sort_order")
    @SerializedName("SortOrder")
    private int sortOrder;

    @ColumnInfo(name = "is_liked")
    private boolean isLiked = false;

    private String price = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getProofType() {
        return proofType;
    }

    public void setProofType(String proofType) {
        this.proofType = proofType;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
