package com.example.productmanagment.data.models.report;

import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Subcategory;
import com.google.gson.annotations.SerializedName;

public class SubcategoryReport {
    @SerializedName("subcategory")
    Category subcategory;
    @SerializedName("balance")
    ReportBalance.Balance balance;
    double amount;
    @SerializedName("incomes")
    double incomes;
    @SerializedName("expenses")
    double expenses;

    public SubcategoryReport(Subcategory subcategory, double amount) {
        this.subcategory = subcategory;
        this.amount = amount;
    }

    public Category getSubcategory() {
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

    public double getBalance(){return balance.incomes - balance.expenses;}
}
