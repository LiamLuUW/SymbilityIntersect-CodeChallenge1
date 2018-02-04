package com.cryptocurrency.liam.symbilityintersectcodechallenge.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Liam on 2018-02-02.
 */

public class CryptoCurrency {

    @SerializedName("Id")
    private long id;
    @SerializedName("Url")
    private String url;
    @SerializedName("ImageUrl")
    private String imageUrl;
    @SerializedName("Name")
    private String name;
    @SerializedName("Symbol")
    private String symbol;
    @SerializedName("CoinName")
    private String coinName;
    @SerializedName("FullName")
    private String fullName;
    @SerializedName("Algorithm")
    private String algorithm;
    @SerializedName("ProofType")
    private String proofType;

    private boolean isLiked = false;
    private String price = "123456";

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
}
