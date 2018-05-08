package com.example.productmanagment.data.models.report;

import com.example.productmanagment.data.models.Category;

public class CategoryReport {
    Category category;
    double amount;

    public CategoryReport(Category category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
