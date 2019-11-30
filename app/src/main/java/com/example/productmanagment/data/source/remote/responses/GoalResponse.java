package com.example.productmanagment.data.source.remote.responses;

import com.example.productmanagment.data.models.Goal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoalResponse {
    @SerializedName("goals")
    List<Goal> goalList;
    @SerializedName("success")
    String success;

    public List<Goal> getGoalList() {
        return goalList;
    }

    public String getSuccess() {
        return success;
    }
}
