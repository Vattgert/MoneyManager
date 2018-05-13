package com.example.productmanagment.data.models.diagram;

import com.example.productmanagment.data.models.Category;
import com.google.gson.annotations.SerializedName;

public class ExpensesByCategory {
    @SerializedName("category")
    Category category;
    @SerializedName("balance")
    double balance;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
