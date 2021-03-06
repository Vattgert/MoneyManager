package com.example.productmanagment.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.Currency;

public class MyCurrency{
    @SerializedName("currency_id")
    private int id;
    @SerializedName("currency_title")
    private String title;
    @SerializedName("currency_code")
    private String code;
    @SerializedName("currency_symbol")
    private String symbol;
    @SerializedName("rate_to_base_currency")
    private double rateToBaseCurrency;
    @SerializedName("rate_base_currency_to_this")
    private double rateBaseToThis;
    @SerializedName("is_base")
    private int isBase;
    @SerializedName("household_id")
    private int groupId;

    public MyCurrency(){}

    public MyCurrency(String title, String code, String symbol, double rateToBaseCurrency, double rateBaseToThis, int isBase) {
        this.title = title;
        this.code = code;
        this.symbol = symbol;
        this.rateToBaseCurrency = rateToBaseCurrency;
        this.rateBaseToThis = rateBaseToThis;
        this.isBase = isBase;
    }

    public MyCurrency(int id, String title, String code, String symbol, double rateToBaseCurrency, double rateBaseToThis, int isBase) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.symbol = symbol;
        this.rateToBaseCurrency = rateToBaseCurrency;
        this.rateBaseToThis = rateBaseToThis;
        this.isBase = isBase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getRateToBaseCurrency() {
        return rateToBaseCurrency;
    }

    public void setRateToBaseCurrency(double rateToBaseCurrency) {
        this.rateToBaseCurrency = rateToBaseCurrency;
    }

    public double getRateBaseToThis() {
        return rateBaseToThis;
    }

    public void setRateBaseToThis(double rateBaseToThis) {
        this.rateBaseToThis = rateBaseToThis;
    }

    public int getIsBase() {
        return isBase;
    }

    public void setIsBase(int isBase) {
        this.isBase = isBase;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return title + "\n".concat(code + "\n")
                .concat (symbol + "\n")
                .concat(isBase  + "\n")
                .concat(rateToBaseCurrency  + "\n")
                .concat(rateBaseToThis  + "\n")
                .concat(groupId  + "\n");
    }
}
