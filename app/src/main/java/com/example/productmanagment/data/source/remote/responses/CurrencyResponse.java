package com.example.productmanagment.data.source.remote.responses;

import com.example.productmanagment.data.models.MyCurrency;
import com.google.gson.annotations.SerializedName;

import java.util.Currency;
import java.util.List;

public class CurrencyResponse {
    @SerializedName("currencies")
    public List<MyCurrency> currencyList;

    @SerializedName("success")
    public String success;

    public List<MyCurrency> getCurrencyList() {
        return currencyList;
    }

    public String getSuccess() {
        return success;
    }
}
