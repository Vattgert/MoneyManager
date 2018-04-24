package com.example.productmanagment.data.source.expenses;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Debt;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.data.models.MyCurrency;
import com.example.productmanagment.data.models.PlannedPayment;
import com.example.productmanagment.data.models.PurchaseList;
import com.example.productmanagment.data.source.expenses.ExpensesDataSource;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
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
    public Flowable<List<Account>> getAccountList() {
        return localExpenseDataSource.getAccountList();
    }

    @Override
    public Flowable<Account> getAccountById(String accountId) {
        return localExpenseDataSource.getAccountById(accountId);
    }

    @Override
    public void saveAccount(@NonNull Account account) {
        localExpenseDataSource.saveAccount(account);
    }

    @Override
    public void deleteAccount(@NonNull String accountId) {
        localExpenseDataSource.deleteAccount(accountId);
    }

    @Override
    public void updateAccount(@NonNull String accountId, Account account) {
        localExpenseDataSource.updateAccount(accountId, account);
    }

    @Override
    public Flowable<List<Expense>> getExpenses() {
        return localExpenseDataSource.getExpenses();
    }

    @Override
    public Flowable<List<Expense>> getExpensesByAccount(String accountId) {
        return localExpenseDataSource.getExpensesByAccount(accountId);
    }

    @Override
    public Flowable<List<Expense>> getExpensesByDate(String fdate, String sdate) {
        return null;
    }

    @Override
    public Flowable<List<PlannedPayment>> getPlannedPayments() {
        return localExpenseDataSource.getPlannedPayments();
    }

    @Override
    public Flowable<List<Debt>> getDebts() {
        return localExpenseDataSource.getDebts();
    }

    @Override
    public Flowable<Debt> getDebtById(@NonNull int debtId) {
        return localExpenseDataSource.getDebtById(debtId);
    }

    @Override
    public Flowable<List<Expense>> getDebtPayments(int debtId) {
        return localExpenseDataSource.getDebtPayments(debtId);
    }

    @Override
    public Flowable<List<PurchaseList>> getPurchaseLists() {
        return localExpenseDataSource.getPurchaseLists();
    }

    @Override
    public Flowable<HashMap<String, Integer>> getExpensesStructureData(String type) {
        return localExpenseDataSource.getExpensesStructureData(type);
    }

    @Override
    public Flowable<HashMap<String, String>> getExpensesDataByDate(String fdate, String sdate) {
        return null;
    }

    @Override
    public Flowable<List<Goal>> getGoals(int state) {
        return localExpenseDataSource.getGoals(state);
    }

    @Override
    public void saveGoal(@NonNull Goal goal) {
        localExpenseDataSource.saveGoal(goal);
    }

    @Override
    public void editGoal(@NonNull Goal goal) {

    }

    @Override
    public void deleteGoal(@NonNull int goalId) {

    }

    @Override
    public void makeGoalAchieved(int goalId) {

    }

    @Override
    public Flowable<List<Account>> getAccounts() {
        return localExpenseDataSource.getAccounts();
    }

    @Override
    public Flowable<List<String>> getExpenseAddresses() {
        return localExpenseDataSource.getExpenseAddresses();
    }

    @Override
    public Flowable<List<String>> getExpenseAddressesByDate(String fdate, String sdate) {
        return localExpenseDataSource.getExpenseAddressesByDate(fdate, sdate);
    }

    @Override
    public Flowable<List<MyCurrency>> getCurrencies() {
        return localExpenseDataSource.getCurrencies();
    }

    @Override
    public Flowable<MyCurrency> getCurrencyById(String id) {
        return localExpenseDataSource.getCurrencyById(id);
    }

    @Override
    public Flowable<MyCurrency> getCurrencyByCode(String code) {
        return localExpenseDataSource.getCurrencyByCode(code);
    }

    @Override
    public Flowable<MyCurrency> getBaseCurrency() {
        return null;
    }

    @Override
    public void saveCurrency(MyCurrency currency) {
        localExpenseDataSource.saveCurrency(currency);
    }

    @Override
    public void updateCurrency(MyCurrency currency) {
        localExpenseDataSource.updateCurrency(currency);
    }

    @Override
    public void saveDebtPayment(@NonNull Expense expense) {
        localExpenseDataSource.saveDebtPayment(expense);
    }

    @Override
    public void saveDebt(@NonNull Debt debt) {
        localExpenseDataSource.saveDebt(debt);
    }

    @Override
    public void editDebt(@NonNull Debt debt) {
        localExpenseDataSource.editDebt(debt);
    }

    @Override
    public void deleteDebt(int debtId) {
        localExpenseDataSource.deleteDebt(debtId);
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
