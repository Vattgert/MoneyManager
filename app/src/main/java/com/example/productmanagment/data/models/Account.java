package com.example.productmanagment.data.models;

import android.graphics.Color;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Created by Ivan on 27.03.2018.
 */

public class Account {
    //TODO: СДелать из валюты и цвета обьекты
    private int id;
    private String name;
    private BigDecimal value;
    private MyCurrency currency;
    private String color;

    public Account(){

    }

    public Account(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Account(String name, BigDecimal value, MyCurrency currency) {
        this.name = name;
        this.value = value;
        this.currency = currency;
    }

    public Account(int id, String name, BigDecimal value, MyCurrency currency, String color) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.currency = currency;
        this.color = color;
    }

    public Account(String name, BigDecimal value, MyCurrency currency, String color) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.currency = currency;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public MyCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(MyCurrency currency) {
        this.currency = currency;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
