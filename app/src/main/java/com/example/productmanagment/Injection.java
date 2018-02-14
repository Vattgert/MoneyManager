package com.example.productmanagment;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.productmanagment.data.source.categories.CategoriesRepository;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.local.CategoriesLocalDataSource;
import com.example.productmanagment.data.source.local.ExpensesLocalDataSource;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.example.productmanagment.utils.schedulers.SchedulerProvider;

/**
 * Created by Ivan on 24.01.2018.
 */

public class Injection {
    public static CategoriesRepository provideCategoriesRepository(@NonNull Context context) {
            return CategoriesRepository.getInstance(CategoriesLocalDataSource.getInstance(context, provideSchedulerProvider()));
    }

    public static ExpensesRepository provideExpensesRepository(@NonNull Context context) {
        return ExpensesRepository.getInstance(ExpensesLocalDataSource.getInstance(context, provideSchedulerProvider()));
    }

    public static BaseSchedulerProvider provideSchedulerProvider(){
        return SchedulerProvider.getInstance();
    }
}
