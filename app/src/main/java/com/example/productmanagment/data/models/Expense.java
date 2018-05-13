package com.example.productmanagment.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.reactivex.annotations.Nullable;

/**
 * Created by Ivan on 22.01.2018.
 */

public class Expense {
    //Expense type 1 = expense, 2 = income
    @SerializedName("id_expense")
    private int id;
    @SerializedName("cost")
    private double cost;
    @SerializedName("expense_type")
    private String expenseType;
    @SerializedName("note")
    private String note;
    @SerializedName("receiver")
    private String receiver;
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;
    @SerializedName("typeOfPayment")
    private String typeOfPayment;
    @SerializedName("place")
    private String place;
    @SerializedName("addition")
    private String addition;
    @SerializedName("address_coordinates")
    private String addressCoordinates;
    private int plannedPaymentId = -1;
    private int debtId = -1;
    @SerializedName("category")
    private Category category;
    @SerializedName("account")
    private Account account;
    @SerializedName("user")
    private User user;

    public Expense() {
    }

    public Expense(int id, double cost, String expenseType, @Nullable String note, @Nullable String receiver, String date,
                   String time, String typeOfPayment, @Nullable String place, @Nullable String addition,
                   @Nullable String addressCoordinates, @Nullable int plannedPaymentId, @Nullable int debtId,
                   Category category, Account account, @Nullable User user) {
        this.id = id;
        this.cost = cost;
        this.expenseType = expenseType;
        this.note = note;
        this.receiver = receiver;
        this.date = date;
        this.time = time;
        this.typeOfPayment = typeOfPayment;
        this.place = place;
        this.addition = addition;
        this.addressCoordinates = addressCoordinates;
        this.plannedPaymentId = plannedPaymentId;
        this.debtId = debtId;
        this.category = category;
        this.account = account;
        this.user = user;
    }

    public Expense(double cost, String expenseType, String note, String receiver, String date,
                   String time, String typeOfPayment, String place, String addition,
                   String addressCoordinates, Category category, Account account, @Nullable  User user) {
        this.cost = cost;
        this.expenseType = expenseType;
        this.note = note;
        this.receiver = receiver;
        this.date = date;
        this.time = time;
        this.typeOfPayment = typeOfPayment;
        this.place = place;
        this.addition = addition;
        this.addressCoordinates = addressCoordinates;
        this.category = category;
        this.account = account;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTypeOfPayment() {
        return typeOfPayment;
    }

    public void setTypeOfPayment(String typeOfPayment) {
        this.typeOfPayment = typeOfPayment;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public String getAddressCoordinates() {
        return addressCoordinates;
    }

    public void setAddressCoordinates(String addressCoordinates) {
        this.addressCoordinates = addressCoordinates;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
