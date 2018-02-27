package com.example.productmanagment.data.source.expenses;

import android.support.annotation.NonNull;

import com.example.productmanagment.data.models.Expense;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Ivan on 30.01.2018.
 */

public interface ExpensesDataSource {
    Flowable<List<Expense>> getExpenses();
    void saveExpense(@NonNull Expense expense);
    void deleteExpense(@NonNull String expenseId);
    Flowable<Expense> getExpenseById(@NonNull String expenseId);
    void updateExpense(@NonNull String expenseId, Expense expense);
}
