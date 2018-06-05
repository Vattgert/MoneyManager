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
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.models.Template;
import com.example.productmanagment.data.models.UserRight;
import com.example.productmanagment.data.models.diagram.ExpensesByCategory;
import com.example.productmanagment.data.models.report.CategoryReport;
import com.example.productmanagment.data.models.report.SubcategoryReport;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

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
    Flowable<List<Purchase>> getPurchasesByList(String purchaseListId);
    void deletePurchaseList(int purchaseList);
    void createPurchaseList(String purchaseListTitle);
    void createPurchase(Purchase purchase);
    void deletePurchase(int purchaseId);
    void renamePurchaseList(int purchaseListId, String name);

    //Діаграми
    Flowable<List<ExpensesByCategory>> getExpensesStructureData(String type);
    Flowable<List<ExpensesByCategory>> getExpensesStructureDataByDate(String type, String fdate, String sdate);

    //Цілі
    Flowable<List<Goal>> getGoals(int state);
    Flowable<Goal> getGoalById(@NonNull String goalId);
    void saveGoal(@NonNull Goal goal);
    void editGoal(String goalId, @NonNull Goal goal);
    void deleteGoal(@NonNull String goalId);
    void makeGoalPaused(String goalId);
    void makeGoalAchieved(int goalId);
    void makeGoalActive(String goalId);
    void addAmount(String goalId, double amount);

    //Місця
    Flowable<List<Place>> getExpenseAddresses();
    Flowable<List<Place>> getExpenseAddressesByDate(String fdate, String sdate);

    //Валюти
    Flowable<List<MyCurrency>> getCurrencies();
    Flowable<MyCurrency> getCurrencyById(String id);
    Flowable<MyCurrency> getCurrencyByCode(String code);
    Flowable<MyCurrency> getBaseCurrency();
    void saveCurrency(MyCurrency currency);
    void updateCurrency(String currencyId, MyCurrency currency);
    void deleteCurrency(String currencyId);

    Flowable<List<UserRight>> getUserRightsList();

    //Звіти
    Flowable<List<CategoryReport>> getCategoryReport();
    Flowable<List<SubcategoryReport>> getSubcategoryReport(String categoryId);
    Flowable<List<CategoryReport>> getCategoryReportByDate(String fdate, String sdate);
    Flowable<List<SubcategoryReport>> getSubcategoryReportByDate(String categoryId, String fdate, String sdate);

    //Головна
    Flowable<BigDecimal> getBalance();
    Flowable<List<Expense>> getLastFiveRecords();

    //Шаблони
    Flowable<List<Template>> getTemplates();
    Flowable<Template> getTemplateById(String id);
    void saveTemplate(@NonNull Template template);
    void deleteTemplate(@NonNull String templateId);
    void updateTemplate(@NonNull String expenseId, Template template);

}
