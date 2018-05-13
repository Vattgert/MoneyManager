package com.example.productmanagment.data.models.diagram;

import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.User;
import com.google.gson.annotations.SerializedName;

public class ExpensesByUser {
    @SerializedName("user")
    User user;
    @SerializedName("balance")
    double balance;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
