package com.example.productmanagment.filters;

import com.example.productmanagment.data.models.Category;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    private String title;
    private String[] expenseTypes;
    private List<Category> categories;
    private List<String> currencies;
    private String[] typesOfPayment;
    private boolean transfersOn;
    private boolean debtsOn;
    private String text;

    public Filter(){
        title = "Новий фільтр";
        expenseTypes = new String[2];
        categories = new ArrayList<>();
        currencies = new ArrayList<>();
        typesOfPayment = new String[10];
        transfersOn = true;
        debtsOn = true;
        text = "";
    }
}
