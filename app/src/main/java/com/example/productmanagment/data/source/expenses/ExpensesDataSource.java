package com.example.productmanagment.data.source.expenses;

import android.support.annotation.NonNull;

import com.example.productmanagment.data.models.Debt;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.PlannedPayment;
import com.example.productmanagment.data.models.PurchaseList;

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

    Flowable<List<PlannedPayment>> getPlannedPayments();

    Flowable<List<Debt>> getDebts();
    void saveDebt(@NonNull Debt debt);

    Flowable<List<Expense>> getDebtPayments(int debtId);
    void saveDebtPayment(@NonNull Expense expense);

    Flowable<List<PurchaseList>> getPurchaseLists();
}
