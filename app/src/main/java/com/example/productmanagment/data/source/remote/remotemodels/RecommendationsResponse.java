package com.example.productmanagment.data.source.remote.remotemodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecommendationsResponse {
    @SerializedName("recommendations")
    List<Recommendation> recommendationList;
    @SerializedName("success")
    String success;

    public List<Recommendation> getRecommendationList() {
        return recommendationList;
    }

    public void setRecommendationList(List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
