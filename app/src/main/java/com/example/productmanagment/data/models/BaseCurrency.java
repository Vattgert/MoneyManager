package com.example.productmanagment.data.models;

import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

public class BaseCurrency {
    private static BaseCurrency INSTANCE;
    private static MyCurrency baseCurrency;

    private BaseCurrency(){
    }

    public static BaseCurrency getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BaseCurrency();
        }
        return INSTANCE;
    }



    public static MyCurrency getBaseCurrency(){
        if (baseCurrency != null)
            return baseCurrency;
        return null;
    }

    public static void setBaseCurrency(MyCurrency currency){
        if(baseCurrency == null)
            baseCurrency = currency;
    }

    public static void disable(){
        INSTANCE = null;
    }
}
