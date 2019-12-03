package com.example.productmanagment.data.models;

import com.google.gson.annotations.SerializedName;

public class Goal {
    /*
    Стани цілі: 1 - активна, 2 - призупинена, 3 - досягнена*/

    @SerializedName("goal_id")
    private int id;
    @SerializedName("goal_title")
    private String title;
    @SerializedName("goal_desired_amount")
    private double neededAmount;
    @SerializedName("goal_current_amount")
    private double accumulatedAmount;
    @SerializedName("goal_start_date")
    private String startDate;
    @SerializedName("goal_terms")
    private String wantedDate;
    @SerializedName("goal_start_amount")
    private  double goalStartAmount;
    @SerializedName("goal_predicted_date")
    private String predictedDate;

    public String getPredictedDate() {
        return predictedDate;
    }

    public void setPredictedDate(String predictedDate) {
        this.predictedDate = predictedDate;
    }

    public double getGoalStartAmount() {
        return goalStartAmount;
    }

    public void setGoalStartAmount(double goalStartAmount) {
        this.goalStartAmount = goalStartAmount;
    }

    private String note;
    private String color;
    private String icon;
    @SerializedName("goal_status")
    private int state;
    @SerializedName("currency")
    private MyCurrency currency;

    public Goal(){

    }

    public Goal(int id, String title, double neededAmount, double accumulatedAmount, String startDate,
                String wantedDate, String note, String color, String icon, int state, MyCurrency currency) {
        this.id = id;
        this.title = title;
        this.neededAmount = neededAmount;
        this.accumulatedAmount = accumulatedAmount;
        this.startDate = startDate;
        this.wantedDate = wantedDate;
        this.note = note;
        this.color = color;
        this.icon = icon;
        this.state = state;
        this.currency = currency;
    }

    public Goal(String title, double neededAmount, double accumulatedAmount, String startDate, String wantedDate,
                String note, String color, String icon, int state, MyCurrency currency) {
        this.title = title;
        this.neededAmount = neededAmount;
        this.accumulatedAmount = accumulatedAmount;
        this.startDate = startDate;
        this.wantedDate = wantedDate;
        this.note = note;
        this.color = color;
        this.icon = icon;
        this.state = state;
        this.currency = currency;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public double getNeededAmount() {
        return neededAmount;
    }

    public void setNeededAmount(double neededAmount) {
        this.neededAmount = neededAmount;
    }

    public double getAccumulatedAmount() {
        return accumulatedAmount;
    }

    public void setAccumulatedAmount(double accumulatedAmount) {
        this.accumulatedAmount = accumulatedAmount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getWantedDate() {
        return wantedDate;
    }

    public void setWantedDate(String wantedDate) {
        this.wantedDate = wantedDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public MyCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(MyCurrency currency) {
        this.currency = currency;
    }
}
