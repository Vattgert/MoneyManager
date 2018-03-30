package com.example.productmanagment.data.source.expenses;

import android.support.annotation.NonNull;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Debt;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.PlannedPayment;
import com.example.productmanagment.data.models.PurchaseList;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Ivan on 30.01.2018.
 */

public interface ExpensesDataSource {
    //Рахунки
    Flowable<List<Account>> getAccountList();
    //Витрати
    Flowable<List<Expense>> getExpenses();
    Flowable<List<Expense>> getExpensesByAccount(String accountId);
    void saveExpense(@NonNull Expense expense);
    void deleteExpense(@NonNull String expenseId);
    Flowable<Expense> getExpenseById(@NonNull String expenseId);
    void updateExpense(@NonNull String expenseId, Expense expense);

    //Заплановані витрати
    Flowable<List<PlannedPayment>> getPlannedPayments();

    //Борги
    Flowable<List<Debt>> getDebts();
    Flowable<Debt> getDebtById(@NonNull int debtId);
    void saveDebt(@NonNull Debt debt);
    void editDebt(@NonNull Debt debt);
    void deleteDebt(int debtId);
    Flowable<List<Expense>> getDebtPayments(int debtId);
    void saveDebtPayment(@NonNull Expense expense);

    //Списки покупок
    Flowable<List<PurchaseList>> getPurchaseLists();

    //Діаграми
    Flowable<HashMap<String, Integer>> getExpensesStructureData(String type);
    Flowable<HashMap<String, String>> getExpensesDateData(String fdate, String sdate);
}
