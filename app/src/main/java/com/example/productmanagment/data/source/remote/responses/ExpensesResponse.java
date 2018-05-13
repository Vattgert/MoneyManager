package com.example.productmanagment.data.source.remote.responses;

import com.example.productmanagment.data.models.Expense;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpensesResponse {
    @SerializedName("expenses")
    public List<Expense> expenses;
}
