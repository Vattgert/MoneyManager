package com.example.productmanagment.data.models;

import java.util.ArrayList;

import io.reactivex.annotations.Nullable;

/**
 * Created by Ivan on 22.01.2018.
 */

public class Expense {
    //Expense type 1 = expense, 2 = income
    private int id;
    private double cost;
    private int expenseType = -1;
    private int plannedPaymentId = -1;
    private int debtId = -1;
    private Category category;
    private int accountId = -1;
    private ExpenseInformation expenseInformation;

    public Expense() {
    }

    public Expense(int id, double cost, Category category, int expenseType, ExpenseInformation expenseInformation) {
        this.id = id;
        this.cost = cost;
        this.category = category;
        this.expenseType = expenseType;
        this.expenseInformation = expenseInformation;
    }

    public Expense(double cost, Category category, ExpenseInformation expenseInformation) {
        this.cost = cost;
        this.category = category;
        this.expenseInformation = expenseInformation;
    }

    public Expense(double cost, int plannedPaymentId, Category category, ExpenseInformation expenseInformation) {
        this.cost = cost;
        this.category = category;
        this.expenseInformation = expenseInformation;
        this.plannedPaymentId = plannedPaymentId;
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

    public int getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(int expenseType) {
        this.expenseType = expenseType;
    }

    public ExpenseInformation getExpenseInformation() {
        return expenseInformation;
    }

    public void setExpenseInformation(ExpenseInformation expenseInformation) {
        this.expenseInformation = expenseInformation;
    }

    public int getPlannedPaymentId() {
        return plannedPaymentId;
    }

    public void setPlannedPaymentId(int plannedPaymentId) {
        this.plannedPaymentId = plannedPaymentId;
    }

    public int getDebtId() {
        return debtId;
    }

    public void setDebtId(int debtId) {
        this.debtId = debtId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
