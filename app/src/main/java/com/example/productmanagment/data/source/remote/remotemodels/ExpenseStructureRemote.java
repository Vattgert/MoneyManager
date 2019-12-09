package com.example.productmanagment.data.source.remote.remotemodels;

import com.example.productmanagment.data.models.diagram.ExpensesByCategory;
import com.example.productmanagment.data.models.diagram.ExpensesByUser;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpenseStructureRemote {
    @SerializedName("expenses_structure")
    List<ExpensesByCategory> expensesStructure;

    @SerializedName("incomes_structure")
    List<ExpensesByCategory> incomesStructure;

    @SerializedName("user_expenses")
    List<ExpensesByUser> expensesByUsers;

    @SerializedName("user_incomes")
    List<ExpensesByUser> incomesByUser;

    public List<ExpensesByCategory> getExpensesStructure() {
        return expensesStructure;
    }

    public List<ExpensesByCategory> getIncomesStructure() {
        return incomesStructure;
    }

    public List<ExpensesByUser> getExpensesByUsers() {
        return expensesByUsers;
    }

    public List<ExpensesByUser> getIncomesByUser() {
        return incomesByUser;
    }
}

