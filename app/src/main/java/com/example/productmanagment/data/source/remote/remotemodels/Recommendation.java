package com.example.productmanagment.data.source.remote.remotemodels;

import com.google.gson.annotations.SerializedName;

public class Recommendation {
    @SerializedName("recommendation")
    String recommendations;

    public Recommendation(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getAdvice() {
        return recommendations;
    }

    public void setAdvice(String recommendations) {
        this.recommendations = recommendations;
    }
}
