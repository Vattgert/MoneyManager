package com.example.productmanagment.data.models;

import io.reactivex.annotations.Nullable;

/**
 * Created by Ivan on 25.02.2018.
 */

public class PlannedPayment extends Expense {
    private String title;
    String startDate;
    private String endDate;
    private String paymentFrequency;
    private String notificationFrequency;
    private String notificationTimeFrequency;
    private String day;

    //TODO: Добавить счет в конструктор здесь и в расходах

    public PlannedPayment(int id, double cost, int expenseType, Category category, @Nullable ExpenseInformation expenseInformation) {

    }

    public PlannedPayment(double cost, Category category, ExpenseInformation information, String title, String startDate, String endDate,
                          String frequency, String timing) {
        super(cost, category, information);
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentFrequency = frequency;
        this.notificationFrequency = timing;
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

}
