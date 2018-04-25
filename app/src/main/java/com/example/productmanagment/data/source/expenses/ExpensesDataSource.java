package com.example.productmanagment.data.source.expenses;

import android.support.annotation.NonNull;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Debt;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.data.models.MyCurrency;
import com.example.productmanagment.data.models.PlannedPayment;
import com.example.productmanagment.data.models.PurchaseList;

import java.util.Currency;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Ivan on 30.01.2018.
 */

public interface ExpensesDataSource {
    //Рахунки
    Flowable<List<Account>> getAccountList();
    Flowable<List<Account>> getAccounts();
    Flowable<Account> getAccountById(String accountId);
    void saveAccount(@NonNull Account account);
    void deleteAccount(@NonNull String accountId);
    void updateAccount(@NonNull String accountId, Account account);

    //Витрати
    Flowable<List<Expense>> getExpenses();
    Flowable<List<Expense>> getExpensesByAccount(String accountId);
    Flowable<List<Expense>> getExpensesByDate(String fdate, String sdate);
    Flowable<Expense> getExpenseById(@NonNull String expenseId);
    void saveExpense(@NonNull Expense expense);
    void deleteExpense(@NonNull String expenseId);
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
    Flowable<HashMap<String, String>> getExpensesDataByDate(String fdate, String sdate);

    //Цілі
    Flowable<List<Goal>> getGoals(int state);
    Flowable<Goal> getGoalById(@NonNull String goalId);
    void saveGoal(@NonNull Goal goal);
    void editGoal(@NonNull Goal goal);
    void deleteGoal(@NonNull int goalId);
    void makeGoalAchieved(int goalId);
    void addAmount(String goalId, double amount);

    //Місця
    Flowable<List<String>> getExpenseAddresses();
    Flowable<List<String>> getExpenseAddressesByDate(String fdate, String sdate);

    //Валюти
    Flowable<List<MyCurrency>> getCurrencies();
    Flowable<MyCurrency> getCurrencyById(String id);
    Flowable<MyCurrency> getCurrencyByCode(String code);
    Flowable<MyCurrency> getBaseCurrency();
    void saveCurrency(MyCurrency currency);
    void updateCurrency(MyCurrency currency);
}
