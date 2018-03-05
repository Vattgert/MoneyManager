package com.example.productmanagment.data.models;

/**
 * Created by Ivan on 25.02.2018.
 */

public class ExpenseInformation {
    private int id;
    private String marks;
    private String note;
    private String receiver;
    private String date;
    private String time;
    private String typeOfPayment;
    private String place;
    private String addition;

    public ExpenseInformation(int id, String note, String marks, String receiver, String date, String time,
                              String typeOfPayment, String place, String addition) {
        this.id = id;
        this.marks = marks;
        this.note = note;
        this.receiver = receiver;
        this.date = date;
        this.time = time;
        this.typeOfPayment = typeOfPayment;
        this.place = place;
        this.addition = addition;
    }

    public ExpenseInformation(String note, String marks, String receiver, String date, String time,
                              String typeOfPayment, String place, String addition) {
        this.id = id;
        this.marks = marks;
        this.note = note;
        this.receiver = receiver;
        this.date = date;
        this.time = time;
        this.typeOfPayment = typeOfPayment;
        this.place = place;
        this.addition = addition;
    }

    public ExpenseInformation(String note, String marks, String receiver, String date, String time,
                              String typeOfPayment, String place, String addition, int plannedPaymentId) {
        this.marks = marks;
        this.note = note;
        this.receiver = receiver;
        this.date = date;
        this.time = time;
        this.typeOfPayment = typeOfPayment;
        this.place = place;
        this.addition = addition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
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
}
