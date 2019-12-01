package com.example.productmanagment.data.source.remote.responses;

import com.example.productmanagment.data.models.Expense;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpensesResponse {
    @SerializedName("transactions")
    public List<Expense> expenses;
    @SerializedName("success")
    public String success;

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
