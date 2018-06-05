package com.example.productmanagment.data.source.expenses;

import android.support.annotation.NonNull;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Debt;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.data.models.MyCurrency;
import com.example.productmanagment.data.models.Place;
import com.example.productmanagment.data.models.PlannedPayment;
import com.example.productmanagment.data.models.Purchase;
import com.example.productmanagment.data.models.PurchaseList;
import com.example.productmanagment.data.models.Template;
import com.example.productmanagment.data.models.UserRight;
import com.example.productmanagment.data.models.diagram.ExpensesByCategory;
import com.example.productmanagment.data.models.report.CategoryReport;
import com.example.productmanagment.data.models.report.SubcategoryReport;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

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
    public Flowable<List<Purchase>> getPurchasesByList(String purchaseListId) {
        return localExpenseDataSource.getPurchasesByList(purchaseListId);
    }

    @Override
    public void deletePurchase(int purchaseId) {
        localExpenseDataSource.deletePurchase(purchaseId);
    }

    @Override
    public void renamePurchaseList(int purchaseListId, String name) {
        localExpenseDataSource.renamePurchaseList(purchaseListId, name);
    }

    @Override
    public void deletePurchaseList(int purchaseList) {
        localExpenseDataSource.deletePurchaseList(purchaseList);
    }

    @Override
    public void createPurchaseList(String purchaseListTitle) {
        localExpenseDataSource.createPurchaseList(purchaseListTitle);
    }

    @Override
    public void createPurchase(Purchase purchase) {
        localExpenseDataSource.createPurchase(purchase);
    }

    @Override
    public Flowable<List<ExpensesByCategory>> getExpensesStructureData(String type) {
        return localExpenseDataSource.getExpensesStructureData(type);
    }

    @Override
    public Flowable<List<ExpensesByCategory>> getExpensesStructureDataByDate(String type, String fdate, String sdate) {
        return null;
    }

    @Override
    public Flowable<List<Goal>> getGoals(int state) {
        return localExpenseDataSource.getGoals(state);
    }

    @Override
    public Flowable<Goal> getGoalById(@NonNull String goalId) {
        return localExpenseDataSource.getGoalById(goalId);
    }

    @Override
    public void saveGoal(@NonNull Goal goal) {
        localExpenseDataSource.saveGoal(goal);
    }

    @Override
    public void editGoal(String goalId, @NonNull Goal goal) {
        localExpenseDataSource.editGoal(goalId, goal);
    }

    @Override
    public void deleteGoal(@NonNull String goalId) {
        localExpenseDataSource.deleteGoal(goalId);
    }

    @Override
    public void makeGoalPaused(String goalId) {
        localExpenseDataSource.makeGoalPaused(goalId);
    }

    @Override
    public void makeGoalAchieved(int goalId) {
        localExpenseDataSource.makeGoalAchieved(goalId);
    }

    @Override
    public void makeGoalActive(String goalId) {
        localExpenseDataSource.makeGoalActive(goalId);
    }

    @Override
    public void addAmount(String goalId, double amount) {
        localExpenseDataSource.addAmount(goalId, amount);
    }

    @Override
    public Flowable<List<Account>> getAccounts() {
        return localExpenseDataSource.getAccounts();
    }

    @Override
    public Flowable<List<Place>> getExpenseAddresses() {
        return localExpenseDataSource.getExpenseAddresses();
    }

    @Override
    public Flowable<List<Place>> getExpenseAddressesByDate(String fdate, String sdate) {
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
    public void updateCurrency(String currencyId, MyCurrency currency) {
        localExpenseDataSource.updateCurrency(currencyId, currency);
    }

    @Override
    public void deleteCurrency(String currencyId) {
        localExpenseDataSource.deleteCurrency(currencyId);
    }

    @Override
    public Flowable<List<UserRight>> getUserRightsList() {
        return localExpenseDataSource.getUserRightsList();
    }

    @Override
    public Flowable<List<CategoryReport>> getCategoryReport() {
        return localExpenseDataSource.getCategoryReport();
    }

    @Override
    public Flowable<List<SubcategoryReport>> getSubcategoryReport(String categoryId) {
        return localExpenseDataSource.getSubcategoryReport(categoryId);
    }

    @Override
    public Flowable<List<CategoryReport>> getCategoryReportByDate(String fdate, String sdate) {
        return localExpenseDataSource.getCategoryReportByDate(fdate, sdate);
    }

    @Override
    public Flowable<List<SubcategoryReport>> getSubcategoryReportByDate(String categoryId, String fdate, String sdate) {
        return getSubcategoryReportByDate(categoryId, fdate, sdate);
    }

    @Override
    public Flowable<BigDecimal> getBalance() {
        return localExpenseDataSource.getBalance();
    }

    @Override
    public Flowable<List<Expense>> getLastFiveRecords() {
        return localExpenseDataSource.getLastFiveRecords();
    }

    @Override
    public Flowable<List<Template>> getTemplates() {
        return localExpenseDataSource.getTemplates();
    }

    @Override
    public Flowable<Template> getTemplateById(String id) {
        return localExpenseDataSource.getTemplateById(id);
    }

    @Override
    public void saveTemplate(@NonNull Template template) {
        localExpenseDataSource.saveTemplate(template);
    }

    @Override
    public void deleteTemplate(@NonNull String templateId) {
        localExpenseDataSource.deleteTemplate(templateId);
    }

    @Override
    public void updateTemplate(@NonNull String expenseId, Template template) {
        localExpenseDataSource.updateTemplate(expenseId, template);
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
