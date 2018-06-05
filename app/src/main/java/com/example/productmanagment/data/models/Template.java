package com.example.productmanagment.data.models;

import io.reactivex.annotations.Nullable;

public class Template{
    String title;
    private int id;
    private String cost;
    private String expenseType;
    private String note;
    private String receiver;
    private String date;
    private String time;
    private String typeOfPayment;
    private String place;
    private String addressCoordinates;
    private Category category;
    private Account account;
    private User user;
    public Template() {
    }

    public Template(int id, double cost, String expenseType, String note, String receiver,
                    String date, String time, String typeOfPayment, String place, String addition,
                    String addressCoordinates, Category category,
                    Account account, User user, String title) {
        this.title = title;
    }

    public Template(int id, String cost, String expenseType, String note, String receiver, String date,
                    String time, String typeOfPayment, String place, String addressCoordinates,
                    Category category, Account account, User user, String title) {
        this.title = title;
        this.id = id;
        this.cost = cost;
        this.expenseType = expenseType;
        this.note = note;
        this.receiver = receiver;
        this.date = date;
        this.time = time;
        this.typeOfPayment = typeOfPayment;
        this.place = place;
        this.addressCoordinates = addressCoordinates;
        this.category = category;
        this.account = account;
        this.user = user;
    }

    public Template(String cost, String expenseType, String note, String receiver,
                    String date, String time, String typeOfPayment,
                    String place, String addressCoordinates, Category category, Account account, User user, String title) {
        this.title = title;
        this.cost = cost;
        this.expenseType = expenseType;
        this.note = note;
        this.receiver = receiver;
        this.date = date;
        this.time = time;
        this.typeOfPayment = typeOfPayment;
        this.place = place;
        this.addressCoordinates = addressCoordinates;
        this.category = category;
        this.account = account;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
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

    public String getAddressCoordinates() {
        return addressCoordinates;
    }

    public void setAddressCoordinates(String addressCoordinates) {
        this.addressCoordinates = addressCoordinates;
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
