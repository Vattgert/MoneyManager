package com.example.productmanagment.data.models;

import io.reactivex.annotations.Nullable;

/**
 * Created by Ivan on 25.02.2018.
 */

public class PlannedPayment {
    private int id;
    private double cost;
    private String title;
    private String startDate;
    private String endDate;
    private String paymentFrequency;
    private String notificationFrequency;
    private String notificationTimeFrequency;
    private String day;
    private Category category;

    public PlannedPayment(int id, double cost, String title, String startDate, String endDate,
                          String paymentFrequency, String notificationFrequency,
                          String notificationTimeFrequency, String day, Category category) {
        this.id = id;
        this.cost = cost;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentFrequency = paymentFrequency;
        this.notificationFrequency = notificationFrequency;
        this.notificationTimeFrequency = notificationTimeFrequency;
        this.day = day;
        this.category = category;
    }

    public PlannedPayment(double cost, String title, String startDate, String endDate,
                          String paymentFrequency, String notificationFrequency,
                          String notificationTimeFrequency, String day, Category category) {
        this.cost = cost;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentFrequency = paymentFrequency;
        this.notificationFrequency = notificationFrequency;
        this.notificationTimeFrequency = notificationTimeFrequency;
        this.day = day;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(String paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public String getNotificationFrequency() {
        return notificationFrequency;
    }

    public void setNotificationFrequency(String notificationFrequency) {
        this.notificationFrequency = notificationFrequency;
    }

    public String getNotificationTimeFrequency() {
        return notificationTimeFrequency;
    }

    public void setNotificationTimeFrequency(String notificationTimeFrequency) {
        this.notificationTimeFrequency = notificationTimeFrequency;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}