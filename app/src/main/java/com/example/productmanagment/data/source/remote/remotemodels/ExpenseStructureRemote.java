package com.example.productmanagment.data.source.remote.remotemodels;

import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.diagram.ExpensesByCategory;
import com.example.productmanagment.data.models.diagram.ExpensesByUser;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpenseStructureRemote {
    @SerializedName("expenses_structure")
    List<ExpenseStructure> expensesStructure;

    @SerializedName("incomes_structure")
    List<ExpenseStructure> incomesStructure;

    @SerializedName("user_expenses")
    List<ExpenseUser> expensesByUsers;

    @SerializedName("user_incomes")
    List<ExpenseUser> incomesByUser;

    public List<ExpenseStructure> getExpensesStructure() {
        return expensesStructure;
    }

    public List<ExpenseStructure> getIncomesStructure() {
        return incomesStructure;
    }

    public List<ExpenseUser> getExpensesByUsers() {
        return expensesByUsers;
    }

    public List<ExpenseUser> getIncomesByUser() {
        return incomesByUser;
    }

    public class ExpenseStructure{
        @SerializedName("category")
        String category;
        @SerializedName("balance")
        double balance;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }

    public class ExpenseUser{
        @SerializedName("user")
        String user;
        @SerializedName("balance")
        double balance;

        public String getUser() {
            return user;
        }

        public void setCategory(String user) {
            this.user = user;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }
}

