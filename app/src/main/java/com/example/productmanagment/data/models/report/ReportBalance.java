package com.example.productmanagment.data.models.report;

import com.example.productmanagment.data.models.Category;
import com.google.gson.annotations.SerializedName;

public class ReportBalance {
    @SerializedName("subcategory")
    Category category;
    @SerializedName("balance")
    Balance balance;

    public class Balance{
        @SerializedName("incomes")
        double incomes;
        @SerializedName("expenses")
        double expenses;

        public double getIncomes() {
            return incomes;
        }

        public void setIncomes(double incomes) {
            this.incomes = incomes;
        }

        public double getExpenses() {
            return expenses;
        }

        public void setExpenses(double expenses) {
            this.expenses = expenses;
        }

        public double getBalance(){
            return incomes - expenses;
        }
    }
}

