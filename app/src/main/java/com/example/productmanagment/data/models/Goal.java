package com.example.productmanagment.data.models;

public class Goal {
    /*
    Стани цілі: 1 - активна, 2 - призупинена, 3 - досягнена*/

    private int id;
    private String title;
    private double neededAmount;
    private double accumulatedAmount;
    private String startDate;
    private String wantedDate;
    private String note;
    private String color;
    private String icon;
    private int state;

    public Goal(int id, String title, double neededAmount, double accumulatedAmount, String startDate,
                String wantedDate, String note, String color, String icon, int state) {
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
    }

    public Goal(String title, double neededAmount, double accumulatedAmount, String startDate, String wantedDate,
                String note, String color, String icon, int state) {
        this.title = title;
        this.neededAmount = neededAmount;
        this.accumulatedAmount = accumulatedAmount;
        this.startDate = startDate;
        this.wantedDate = wantedDate;
        this.note = note;
        this.color = color;
        this.icon = icon;
        this.state = state;
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
}
