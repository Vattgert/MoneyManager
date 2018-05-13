package com.example.productmanagment.data.source.remote.responses;

import com.example.productmanagment.data.models.diagram.ExpensesByCategory;
import com.example.productmanagment.data.models.diagram.ExpensesByUser;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DiagramResponse {
    @SerializedName("expensesByCategoryData")
    public List<ExpensesByCategory> expensesByCategoryList;

    @SerializedName("expensesByUserData")
    public List<ExpensesByUser> expensesByUserList;
}
