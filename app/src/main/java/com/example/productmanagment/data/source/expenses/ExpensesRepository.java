package com.example.productmanagment.data.source.expenses;

import android.support.annotation.NonNull;

import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.PlannedPayment;
import com.example.productmanagment.data.source.expenses.ExpensesDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Ivan on 30.01.2018.
 */

public class ExpensesRepository implements ExpensesDataSource {
    private ExpensesDataSource localExpenseDataSource;
    private static ExpensesRepository INSTANCE = null;

    private ExpensesRepository(@NonNull ExpensesDataSource localExpenseDataSource) {
        this.localExpenseDataSource = localExpenseDataSource;
    }

    public static ExpensesRepository getInstance(@NonNull ExpensesDataSource localExpenseDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ExpensesRepository(localExpenseDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Flowable<List<Expense>> getExpenses() {
        return localExpenseDataSource.getExpenses();
    }

    @Override
    public Flowable<List<PlannedPayment>> getPlannedPayments() {
        return localExpenseDataSource.getPlannedPayments();
    }

    @Override
    public void saveExpense(@NonNull Expense expense) {
        localExpenseDataSource.saveExpense(expense);
    }

    @Override
    public void deleteExpense(@NonNull String expenseId) {
        localExpenseDataSource.deleteExpense(expenseId);
    }

    @Override
    public Flowable<Expense> getExpenseById(@NonNull String expenseId) {
        return localExpenseDataSource.getExpenseById(expenseId);
    }

    @Override
    public void updateExpense(@NonNull String expenseId, Expense expense) {
        localExpenseDataSource.updateExpense(expenseId, expense);
    }
}
