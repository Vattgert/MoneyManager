package com.example.productmanagment.data.models;

/**
 * Created by Ivan on 25.02.2018.
 */

public class PlannedPayment extends Expense {
    private String endDate;
    private String paymentFrequency;
    private String notificationFrequency;
    private String notificationTimeFrequency;

    public PlannedPayment(int id, double cost, Category category, ExpenseInformation expenseInformation) {
        super(id, cost, category, expenseInformation);
    }

    public PlannedPayment(double cost, Category category, ExpenseInformation expenseInformation) {
        super(cost, category, expenseInformation);
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
