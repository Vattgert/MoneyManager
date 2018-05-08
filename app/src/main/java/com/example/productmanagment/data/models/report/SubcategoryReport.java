package com.example.productmanagment.data.models.report;

import com.example.productmanagment.data.models.Subcategory;

public class SubcategoryReport {
    Subcategory subcategory;
    double amount;
    int percentage;

    public SubcategoryReport(Subcategory subcategory, double amount, int percentage) {
        this.subcategory = subcategory;
        this.amount = amount;
        this.percentage = percentage;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
