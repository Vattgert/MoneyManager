package com.example.productmanagment.data.source.remote.remotemodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecommendationsResponse {
    @SerializedName("recommendations")
    List<Recommendation> recommendationList;
}
