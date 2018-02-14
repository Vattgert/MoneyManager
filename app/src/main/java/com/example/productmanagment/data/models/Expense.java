package com.example.productmanagment.data.models;

import java.util.ArrayList;

import io.reactivex.annotations.Nullable;

/**
 * Created by Ivan on 22.01.2018.
 */

public class Expense {
    private int id;
    private String category;
    private double cost;
    private String marks;
    private String note;
    private String receiver;
    private String date;
    private String typeOfPayment;
    private String place;
    private String addition;

    public Expense(int id, double cost, @Nullable String marks, @Nullable String note,
                   @Nullable String receiver, @Nullable String date, @Nullable String typeOfPayment,
                   @Nullable String place, @Nullable String addition) {
        this.id = id;
        this.cost = cost;
        this.marks = marks;
        this.note = note;
        this.receiver = receiver;
        this.date = date;
        this.typeOfPayment = typeOfPayment;
        this.place = place;
        this.addition = addition;
    }

    public Expense(double cost, @Nullable String note, @Nullable String marks, @Nullable String category,
                   @Nullable String receiver, @Nullable String date, @Nullable String typeOfPayment,
                   @Nullable String place, @Nullable String addition) {
        this.cost = cost;
        this.marks = marks;
        this.note = note;
        this.receiver = receiver;
        this.date = date;
        this.typeOfPayment = typeOfPayment;
        this.place = place;
        this.addition = addition;
    }

    public String getCategory() {
        return category;
    }

    public int getId() { return id; }

    public double getCost() {
        return cost;
    }

    public String getMarks() {
        return marks;
    }

    public String getNote() {
        return note;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getDate() {
        return date;
    }

    public String getTypeOfPayment() {
        return typeOfPayment;
    }

    public String getPlace() {
        return place;
    }

    public String getAddition() {
        return addition;
    }

    public void setId(int id) { this.id = id; }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTypeOfPayment(String typeOfPayment) {
        this.typeOfPayment = typeOfPayment;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }
}
