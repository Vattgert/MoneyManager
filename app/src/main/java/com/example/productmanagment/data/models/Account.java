package com.example.productmanagment.data.models;

import android.graphics.Color;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Created by Ivan on 27.03.2018.
 */

public class Account {
    private int id;
    private String name;
    private BigDecimal value;
    private Currency currency;
    private Color color;

    public Account(String name, BigDecimal value, Currency currency) {
        this.name = name;
        this.value = value;
        this.currency = currency;
    }

    public Account(int id, String name, BigDecimal value, Currency currency) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.currency = currency;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
