package com.example.productmanagment.data.source.remote.remotemodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpensePredictionResponse {
    @SerializedName("subcategory")
    String subcategory;

    @SerializedName("predictions")
    List<ExpensePrediction> predictionList;

    public String getSubcategory() {
        return subcategory;
    }

    public List<ExpensePrediction> getPredictionList() {
        return predictionList;
    }
}
