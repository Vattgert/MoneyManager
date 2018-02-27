package com.example.productmanagment.data.models;

import java.util.ArrayList;

import io.reactivex.annotations.Nullable;

/**
 * Created by Ivan on 22.01.2018.
 */

public class Expense {
    private int id;
    private double cost;
    private Category category;
    private ExpenseInformation expenseInformation;


    public Expense(int id, double cost, Category category, ExpenseInformation expenseInformation) {
        this.id = id;
        this.cost = cost;
        this.category = category;
        this.expenseInformation = expenseInformation;
    }

    public Expense(double cost, Category category, ExpenseInformation expenseInformation) {
        this.cost = cost;
        this.category = category;
        this.expenseInformation = expenseInformation;
    }

    public int getId() { return id; }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ExpenseInformation getExpenseInformation() {
        return expenseInformation;
    }

    public void setExpenseInformation(ExpenseInformation expenseInformation) {
        this.expenseInformation = expenseInformation;
    }
}
