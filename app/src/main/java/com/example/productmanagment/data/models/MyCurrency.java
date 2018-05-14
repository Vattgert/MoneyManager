package com.example.productmanagment.data.models;

import java.util.Currency;

public class MyCurrency{
    private int id;
    private String title;
    private String code;
    private String symbol;
    private double rateToBaseCurrency;
    private double rateBaseToThis;
    private int isBase;

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
}
