package com.example.productmanagment.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Debt;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.data.models.MyCurrency;
import com.example.productmanagment.data.models.PlannedPayment;
import com.example.productmanagment.data.models.Purchase;
import com.example.productmanagment.data.models.PurchaseList;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.models.UserRight;
import com.example.productmanagment.data.models.report.CategoryReport;
import com.example.productmanagment.data.models.report.SubcategoryReport;
import com.example.productmanagment.data.source.categories.CategoryPersistenceContract;
import com.example.productmanagment.data.source.expenses.ExpensePersistenceContract;
import com.example.productmanagment.data.source.expenses.ExpensesDataSource;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Created by Ivan on 30.01.2018.
 */

public class ExpensesLocalDataSource implements ExpensesDataSource {
    private static ExpensesLocalDataSource INSTANCE;
    private final BriteDatabase databaseHelper;

    private Function<Cursor, Expense> expenseMapperFunction;
    private Function<Cursor, PlannedPayment> plannedPaymentMapperFunction;
    private Function<Cursor, Debt> debtMapperFunction;
    private Function<Cursor, PurchaseList> purchaseListMapperFunction;
    private Function<Cursor, HashMap<String, Integer>> expenseStructureMapperFunction;
    private Function<Cursor, Account> accountMapperFunction;
    private Function<Cursor, Account> fullAccountMapperFunction;
    private Function<Cursor, Goal> goalMapperFunction;
    private Function<Cursor, MyCurrency> currencyMapperFunction;
    private Function<Cursor, Purchase> purchaseMapperFunction;
    private Function<Cursor, UserRight> userRightMapperFunction;
    private Function<Cursor, CategoryReport> categoryReportMapperFunction;
    private Function<Cursor, SubcategoryReport> subcategoryReportMapperFunction;


    private ExpensesLocalDataSource(Context context, BaseSchedulerProvider schedulerProvider) {
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        ExpenseManagerDatabaseHelper helper = new ExpenseManagerDatabaseHelper(context);
        databaseHelper = sqlBrite.wrapDatabaseHelper(helper, schedulerProvider.io());

        expenseMapperFunction = this::getExpense;
        plannedPaymentMapperFunction = this::getPlannedPayment;
        debtMapperFunction = this::getDebt;
        purchaseListMapperFunction = this::getPurchaseLists;
        expenseStructureMapperFunction = this::getExpensesStructureData;
        accountMapperFunction = this::getAccount;
        fullAccountMapperFunction = this::getFullAccount;
        goalMapperFunction = this::getGoal;
        currencyMapperFunction = this::getCurrency;
        purchaseMapperFunction = this::getPurchase;
        userRightMapperFunction = this::getUserRight;
        categoryReportMapperFunction = this::getCategoryReport;
        subcategoryReportMapperFunction = this::getSubcategoryReport;
    }

    public static ExpensesLocalDataSource getInstance(@NonNull Context context,
                                                        @NonNull BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new ExpensesLocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    //TODO: Считывать иконку категории
    //TODO: Перепроверить структуру репозиториев (почему тут метод getCategory)

    @NonNull
    private Expense getExpense(@NonNull Cursor c) {
        int itemId = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_ID));
        double cost = c.getDouble(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_COST));
        String expenseType = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_EXPENSE_TYPE));
        String note = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NOTE));
        String receiver = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_RECEIVER));
        String date = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_DATE));
        String time = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_TIME));
        String typeOfPayment = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_TYPE_OF_PAYMENT));
        String place = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_PLACE));
        String addition = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDITION));
        String addressCoordinates = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDRESS_COORDINATES));
        int plannedPaymentId = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_PLANNED_PAYMENT));
        int debtId = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_DEBT));
        return new Expense(itemId, cost, expenseType, note, receiver, date, time, typeOfPayment,
                place, addition, addressCoordinates, plannedPaymentId, debtId, getCategory(c),
                getFullAccount(c), null);
    }

    private PlannedPayment getPlannedPayment(@NonNull Cursor c){
        int id = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_ID));
        String title = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_TITLE));
        String startDate = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_START_DATE));
        String endDate = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_END_DATE));
        double cost = c.getDouble(c.getColumnIndexOrThrow(ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_COST));
        String frequency = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_FREQUENCY));
        String timing = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_TIMING));
        String day = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_DAY));
        return new PlannedPayment(cost, title ,startDate, endDate, frequency, timing, "", day, getCategory(c));
    }

    private Debt getDebt(@NonNull Cursor c){
        int id = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.DebtEntry.COLUMN_ID));
        String sum = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.DebtEntry.COLUMN_SUM));
        String borrowDate = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.DebtEntry.COLUMN_BORROW_DATE));
        String repayDate = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.DebtEntry.COLUMN_REPAY_DATE));
        String remain = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.DebtEntry.COLUMN_DEBT_REMAIN));
        String borrower = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.DebtEntry.COLUMN_BORROWER));
        String description = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.DebtEntry.COLUMN_DESCRIPTION));
        int debtType = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.DebtEntry.COLUMN_DEBT_TYPE));
        return new Debt(id, sum, remain, description, borrowDate, repayDate, borrower, debtType, getFullAccount(c));
    }

    private Account getAccount(@NonNull Cursor c){
        int id = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.AccountEntry.COLUMN_NAME_ID));
        String title = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.AccountEntry.COLUMN_NAME_TITLE));
        return new Account(id, title);
    }

    private Account getFullAccount(@NonNull Cursor c){
        int id = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.AccountEntry.COLUMN_NAME_ID));
        String title = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.AccountEntry.COLUMN_NAME_TITLE));
        double amount = c.getDouble(c.getColumnIndexOrThrow(ExpensePersistenceContract.AccountEntry.COLUMN_NAME_AMOUNT));
        String color = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.AccountEntry.COLUMN_COLOR));
        return new Account(id, title, new BigDecimal(amount), getCurrency(c), color);
    }

    private Subcategory getCategory(Cursor c){
        int subcategoryId = c.getInt(c.getColumnIndexOrThrow(CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID));
        String title = c.getString(c.getColumnIndexOrThrow(CategoryPersistenceContract.SubcategoryEntry.COLUMN_TITLE));
        int categoryId = c.getInt(c.getColumnIndexOrThrow(CategoryPersistenceContract.SubcategoryEntry.COLUMN_CATEGORY_ID));
        //String icon = c.getString(c.getColumnIndexOrThrow(CategoryPersistenceContract.CategoryEntry.COLUMN_ICON));
        return new Subcategory(subcategoryId, title, categoryId);
    }

    private PurchaseList getPurchaseLists(Cursor c){
        int id = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.PurchaseListEntry.COLUMN_ID));
        String title = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.PurchaseListEntry.COLUMN_TITLE));
        return new PurchaseList(id, title);
    }

    private UserRight getUserRight(Cursor c){
        int id = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.UserRightEntry.COLUMN_ID));
        String title = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.UserRightEntry.COLUMN_TITLE));
        String description = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.UserRightEntry.COLUMN_DESCRIPTION));
        return new UserRight(id, title, description);
    }

    private HashMap<String, Integer> getExpensesStructureData(Cursor c){
        HashMap<String, Integer> map = new HashMap<>();
        while (c.moveToNext()){
            Integer i = c.getInt(0);
            String s = c.getString(c.getColumnIndexOrThrow(CategoryPersistenceContract.SubcategoryEntry.COLUMN_TITLE));
            map.put(s, i);
        }
        return map;
    }

    private Goal getGoal(Cursor c){
        int id = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.GoalEntry.COLUMN_ID));
        String title = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.GoalEntry.COLUMN_TITLE));
        String note = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.GoalEntry.COLUMN_NOTE));
        double neededSum = c.getDouble(c.getColumnIndexOrThrow(ExpensePersistenceContract.GoalEntry.COLUMN_NEEDED_AMOUNT));
        double accumulatedSum = c.getDouble(c.getColumnIndexOrThrow(ExpensePersistenceContract.GoalEntry.COLUMN_ACCUMULATED_AMOUNT));
        String startDate = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.GoalEntry.COLUMN_START_DATE));
        String wantedDate = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.GoalEntry.COLUMN_WANTED_DATE));
        String color = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.GoalEntry.COLUMN_COLOR));
        String icon = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.GoalEntry.COLUMN_ICON));
        int state = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.GoalEntry.COLUMN_STATUS));
        return new Goal(id, title, neededSum, accumulatedSum, startDate, wantedDate, note, color, icon, state, getCurrency(c));
    }

    private MyCurrency getCurrency(Cursor c){
        int id = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.CurrencyEntry.COLUMN_ID));
        String title = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.CurrencyEntry.COLUMN_TITLE));
        String code = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.CurrencyEntry.COLUMN_CODE));
        String symbol = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.CurrencyEntry.COLUMN_SYMBOL));
        double rateToBase = c.getDouble(c.getColumnIndexOrThrow(ExpensePersistenceContract.CurrencyEntry.COLUMN_RATE_TO_BASE));
        double rateBaseToThis = c.getDouble(c.getColumnIndexOrThrow(ExpensePersistenceContract.CurrencyEntry.COLUMN_RATE_BASE_TO_THIS));
        int isBase = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.CurrencyEntry.COLUMN_IS_BASE));
        return new MyCurrency(id, title, code, symbol, rateToBase, rateBaseToThis, isBase);
    }

    private Purchase getPurchase(Cursor c){
        int id = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.PurchaseEntry.COLUMN_ID));
        String title = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.PurchaseEntry.COLUMN_TITLE));
        int listId = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.PurchaseEntry.COLUMN_PURCHASE_LIST));
        return new Purchase(id, title, listId);
    }

    private CategoryReport getCategoryReport(Cursor c){
        int id = c.getInt(c.getColumnIndexOrThrow(CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_ENTRY_ID));
        String title = c.getString(c.getColumnIndexOrThrow(CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_TITLE));
        String icon = c.getString(c.getColumnIndexOrThrow(CategoryPersistenceContract.CategoryEntry.COLUMN_ICON));
        Category category = new Category(id, title, icon);
        double amount = c.getDouble(c.getColumnIndexOrThrow("amount_by_category"));
        return new CategoryReport(category, amount);
    }

    private SubcategoryReport getSubcategoryReport(Cursor c){
        Subcategory subcategory = getCategory(c);
        double amount = c.getDouble(c.getColumnIndexOrThrow("amount_by_subcategory"));
        return new SubcategoryReport(subcategory, amount, 0);
    }

    private ArrayList<String> getExpenseTableList(){
        return new ArrayList<>(Arrays.asList(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME
                ,CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                ExpensePersistenceContract.AccountEntry.TABLE_NAME));
    }

    private ArrayList<String> getAccountTableList(){
        return new ArrayList<>(Arrays.asList(ExpensePersistenceContract.AccountEntry.TABLE_NAME
                ,ExpensePersistenceContract.CurrencyEntry.TABLE_NAME));
    }

    private ArrayList<String> getPlannedPaymentTableList(){
        return new ArrayList<>(Arrays.asList(ExpensePersistenceContract.PlannedPaymentEntry.TABLE_NAME
                ,CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME));
    }

    String[] fInnerJoin = {CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
            CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID };
    String[] sInnerJoin = {ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
            ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY};
    String[] tInnerJoin = { ExpensePersistenceContract.AccountEntry.TABLE_NAME,
            ExpensePersistenceContract.AccountEntry.COLUMN_NAME_ID };
    String[] frInnerJoin = {ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
            ExpensePersistenceContract.ExpenseEntry.COLUMN_ACCOUNT};
    String[] currencyInnerJoin = { ExpensePersistenceContract.CurrencyEntry.TABLE_NAME,
            ExpensePersistenceContract.CurrencyEntry.COLUMN_ID };
    String[] currencyToAccount = { ExpensePersistenceContract.AccountEntry.TABLE_NAME,
                                    ExpensePersistenceContract.AccountEntry.COLUMN_CURRENCY};

    @Override
    public Flowable<List<Expense>> getExpenses() {
        String sql = String.format("SELECT %s,%s,%s,%s FROM %s INNER JOIN %s ON %s = %s INNER JOIN %s ON %s = %s INNER JOIN %s ON %s = %s",
                TextUtils.join(",", getExpenseProjection()),
                TextUtils.join(",", getCategoryProjection()),
                TextUtils.join(",", getAccountProjection()),
                TextUtils.join(",", getCurrencyProjection()),
                ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                TextUtils.join(".", fInnerJoin),
                TextUtils.join(".", sInnerJoin),
                ExpensePersistenceContract.AccountEntry.TABLE_NAME,
                TextUtils.join(".", tInnerJoin),
                TextUtils.join(".", frInnerJoin),
                ExpensePersistenceContract.CurrencyEntry.TABLE_NAME,
                TextUtils.join(".", currencyInnerJoin),
                TextUtils.join(".", currencyToAccount));
        Log.wtf("sqlite", sql);

        return databaseHelper.createQuery(getExpenseTableList(), sql)
                .mapToList(expenseMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<Expense>> getExpensesByAccount(String accountId) {
        String sql = String.format("SELECT %s,%s,%s,%s FROM %s INNER JOIN %s ON %s = %s INNER JOIN %s ON %s = %s INNER JOIN %s ON %s = %s WHERE %s = %s",
                TextUtils.join(",", getExpenseProjection()),
                TextUtils.join(",", getCategoryProjection()),
                TextUtils.join(",", getAccountProjection()),
                TextUtils.join(",", getCurrencyProjection()),
                ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                TextUtils.join(".", fInnerJoin),
                TextUtils.join(".", sInnerJoin),
                ExpensePersistenceContract.AccountEntry.TABLE_NAME,
                TextUtils.join(".", tInnerJoin),
                TextUtils.join(".", frInnerJoin),
                ExpensePersistenceContract.CurrencyEntry.TABLE_NAME,
                TextUtils.join(".", currencyInnerJoin),
                TextUtils.join(".", currencyToAccount),
                ExpensePersistenceContract.ExpenseEntry.COLUMN_ACCOUNT,
                accountId);
        Log.wtf("sqlite", sql);

        return databaseHelper.createQuery(getExpenseTableList(), sql)
                .mapToList(expenseMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<Expense> getExpenseById(@NonNull String expenseId) {
        String sql = String.format("SELECT %s,%s,%s,%s FROM %s INNER JOIN %s ON %s = %s INNER JOIN %s ON %s = %s INNER JOIN %s ON %s = %s WHERE %s LIKE ?",
                TextUtils.join(",", getExpenseProjection()),
                TextUtils.join(",", getCategoryProjection()),
                TextUtils.join(",", getAccountProjection()),
                TextUtils.join(",", getCurrencyProjection()),
                ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                TextUtils.join(".", fInnerJoin),
                TextUtils.join(".", sInnerJoin),
                ExpensePersistenceContract.AccountEntry.TABLE_NAME,
                TextUtils.join(".", tInnerJoin),
                TextUtils.join(".", frInnerJoin),
                ExpensePersistenceContract.CurrencyEntry.TABLE_NAME,
                TextUtils.join(".", currencyInnerJoin),
                TextUtils.join(".", currencyToAccount),
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_ID);
        Log.wtf("sqlite", sql);
        return databaseHelper.createQuery(getExpenseTableList(), sql, expenseId)
                .mapToOne(cursor -> expenseMapperFunction.apply(cursor))
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<Expense>> getExpensesByDate(String fdate, String sdate) {
        return null;
    }

    //TODO: Уменьшить размер функций
    @Override
    public void saveExpense(@NonNull Expense expense) {
        ContentValues expenseValues = new ContentValues();
        Log.wtf("PurchaseLog", "expense local data source expense cost " + expense.getCost());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_COST, expense.getCost());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY, expense.getCategory().getId());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NOTE, expense.getNote());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_RECEIVER, expense.getReceiver());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_TYPE_OF_PAYMENT, expense.getTypeOfPayment());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_DATE, expense.getDate());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_TIME, expense.getTime());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_PLACE, expense.getPlace());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDITION, expense.getAddition());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_DEBT, expense.getDebtId());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_ACCOUNT, expense.getAccount().getId());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_EXPENSE_TYPE, expense.getExpenseType());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDRESS_COORDINATES, expense.getAddressCoordinates());
        databaseHelper.insert(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, expenseValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void updateExpense(@NonNull String expenseId, Expense expense) {
        ContentValues values = new ContentValues();
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_COST, expense.getCost());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NOTE, expense.getNote());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY, expense.getCategory().getId());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_RECEIVER, expense.getReceiver());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_DATE, expense.getDate());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_TIME, expense.getTime());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_TYPE_OF_PAYMENT, expense.getTypeOfPayment());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_PLACE, expense.getPlace());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDITION, expense.getAddition());
        databaseHelper.update(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, values, String.format(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_ID + "=%s", expenseId));
    }

    @Override
    public void deleteExpense(@NonNull String expenseId) {
        String selection = ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = {expenseId};
        databaseHelper.delete(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public Flowable<List<Account>> getAccounts() {
        String sql = String.format("SELECT %s,%s FROM %s INNER JOIN %s ON %s = %s",
                TextUtils.join(",", getAccountProjection()),
                TextUtils.join(",", getCurrencyProjection()),
                ExpensePersistenceContract.AccountEntry.TABLE_NAME,
                ExpensePersistenceContract.CurrencyEntry.TABLE_NAME,
                TextUtils.join(".", currencyToAccount),
                TextUtils.join(".", currencyInnerJoin)
                );
        Log.wtf("MyLog", sql);
        return databaseHelper.createQuery(getAccountTableList(), sql)
                .mapToList(fullAccountMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<Account>> getAccountList() {
        String sql = String.format("SELECT %s FROM %s", TextUtils.join(",", new String[]{
                ExpensePersistenceContract.AccountEntry.COLUMN_NAME_ID,
                ExpensePersistenceContract.AccountEntry.COLUMN_NAME_TITLE,
        }), ExpensePersistenceContract.AccountEntry.TABLE_NAME);
        Log.wtf("MyLog", sql);
        return databaseHelper.createQuery(ExpensePersistenceContract.AccountEntry.TABLE_NAME, sql)
                .mapToList(accountMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<Account> getAccountById(String accountId) {
        String sql = String.format("SELECT %s, %s FROM %s INNER JOIN %s ON %s = %s WHERE %s = %s",
                TextUtils.join(",", getAccountProjection()),
                TextUtils.join(",", getCurrencyProjection()),
                ExpensePersistenceContract.AccountEntry.TABLE_NAME,
                ExpensePersistenceContract.CurrencyEntry.TABLE_NAME,
                TextUtils.join(".", currencyToAccount),
                TextUtils.join(".", currencyInnerJoin),
                ExpensePersistenceContract.AccountEntry.COLUMN_NAME_ID,
                accountId
        );
        Log.wtf("AccountsLog", sql);
        return databaseHelper.createQuery(getAccountTableList(), sql)
                .mapToOne(cursor -> fullAccountMapperFunction.apply(cursor))
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public void saveAccount(@NonNull Account account) {
        ContentValues expenseValues = new ContentValues();
        expenseValues.put(ExpensePersistenceContract.AccountEntry.COLUMN_NAME_TITLE, account.getName());
        expenseValues.put(ExpensePersistenceContract.AccountEntry.COLUMN_NAME_AMOUNT, account.getValue().toString());
        expenseValues.put(ExpensePersistenceContract.AccountEntry.COLUMN_CURRENCY, account.getCurrency().getId());
        expenseValues.put(ExpensePersistenceContract.AccountEntry.COLUMN_COLOR, account.getColor());
        databaseHelper.insert(ExpensePersistenceContract.AccountEntry.TABLE_NAME, expenseValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void updateAccount(@NonNull String accountId, Account account) {
        ContentValues values = new ContentValues();
        values.put(ExpensePersistenceContract.AccountEntry.COLUMN_NAME_TITLE, account.getName());
        values.put(ExpensePersistenceContract.AccountEntry.COLUMN_COLOR, account.getColor());
        databaseHelper.update(ExpensePersistenceContract.AccountEntry.TABLE_NAME, values, String.format("%s=%s", ExpensePersistenceContract.AccountEntry.COLUMN_NAME_ID, accountId));
    }

    @Override
    public void deleteAccount(@NonNull String accountId) {
        String selection = ExpensePersistenceContract.AccountEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = {accountId};
        databaseHelper.delete(ExpensePersistenceContract.AccountEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public Flowable<List<MyCurrency>> getCurrencies() {
        String sql = "SELECT * FROM currency";
        return databaseHelper.createQuery(ExpensePersistenceContract.CurrencyEntry.TABLE_NAME, sql)
                .mapToList(currencyMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<MyCurrency> getCurrencyById(String id) {
        return null;
    }

    @Override
    public Flowable<MyCurrency> getCurrencyByCode(String code) {
        return null;
    }

    @Override
    public Flowable<MyCurrency> getBaseCurrency() {
        String sql = String.format("SELECT * FROM %s WHERE %s = %s",
                ExpensePersistenceContract.CurrencyEntry.TABLE_NAME,
                ExpensePersistenceContract.CurrencyEntry.COLUMN_IS_BASE, "1");
        return databaseHelper.createQuery(ExpensePersistenceContract.CurrencyEntry.TABLE_NAME, sql)
                .mapToOne(currencyMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public void saveCurrency(MyCurrency currency) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpensePersistenceContract.CurrencyEntry.COLUMN_TITLE, currency.getTitle());
        contentValues.put(ExpensePersistenceContract.CurrencyEntry.COLUMN_CODE, currency.getCode());
        contentValues.put(ExpensePersistenceContract.CurrencyEntry.COLUMN_SYMBOL, currency.getSymbol());
        contentValues.put(ExpensePersistenceContract.CurrencyEntry.COLUMN_RATE_TO_BASE, currency.getRateToBaseCurrency());
        contentValues.put(ExpensePersistenceContract.CurrencyEntry.COLUMN_RATE_BASE_TO_THIS, currency.getRateBaseToThis());
        contentValues.put(ExpensePersistenceContract.CurrencyEntry.COLUMN_IS_BASE, currency.getIsBase());
        databaseHelper.insert(ExpensePersistenceContract.CurrencyEntry.TABLE_NAME, contentValues);
    }

    @Override
    public void updateCurrency(MyCurrency currency) {

    }

    @Override
    public Flowable<List<UserRight>> getUserRightsList() {
        String sql = String.format("SELECT * FROM %s", ExpensePersistenceContract.UserRightEntry.TABLE_NAME);
        return databaseHelper.createQuery(ExpensePersistenceContract.UserRightEntry.TABLE_NAME, sql)
                .mapToList(userRightMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<CategoryReport>> getCategoryReport() {
        String sql = "SELECT category.categoryId, category.category_title, category.icon, sum(expense.cost) AS amount_by_category FROM category " +
                "LEFT JOIN subcategory on subcategory.category_id = category.categoryId " +
                "LEFT JOIN expense on subcategory.id_subcategory = expense.category group by category.categoryId";
        return databaseHelper.createQuery(
                Arrays.asList(CategoryPersistenceContract.CategoryEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                ExpensePersistenceContract.ExpenseEntry.TABLE_NAME), sql)
                .mapToList(categoryReportMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<SubcategoryReport>> getSubcategoryReport(String categoryId) {
        String sql = String.format("SELECT subcategory.id_subcategory, subcategory.subcategory_title, subcategory.category_id, sum(expense.cost) as amount_by_subcategory FROM subcategory\n"
                + "left join category on subcategory.category_id = category.categoryId\n"
                + "left join expense on subcategory.id_subcategory = expense.category  WHERE subcategory.category_id = %s group by subcategory.id_subcategory", categoryId);

        return databaseHelper.createQuery(
                Arrays.asList(CategoryPersistenceContract.CategoryEntry.TABLE_NAME,
                        CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                        ExpensePersistenceContract.ExpenseEntry.TABLE_NAME), sql)
                .mapToList(subcategoryReportMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<CategoryReport>> getCategoryReportByDate(String fdate, String sdate) {

        return null;
    }

    @Override
    public Flowable<List<SubcategoryReport>> getSubcategoryReportByDate(String categoryId, String fdate, String sdate) {
        return null;
    }

    @Override
    public Flowable<List<PlannedPayment>> getPlannedPayments() {
        String[] fInnerJoin = {CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID };
        String[] sInnerJoin = { ExpensePersistenceContract.PlannedPaymentEntry.TABLE_NAME,
                ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_CATEGORY_ID };
        String sql = String.format("SELECT %s,%s FROM %s INNER JOIN %s ON %s = %s",
                TextUtils.join(",", getPlannedPaymentProjection()),
                TextUtils.join(",", getCategoryProjection()),
                ExpensePersistenceContract.PlannedPaymentEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                TextUtils.join(".", fInnerJoin),
                TextUtils.join(".", sInnerJoin));
        Log.wtf("payment", sql);
        return databaseHelper.createQuery(getPlannedPaymentTableList(), sql)
                .mapToList(plannedPaymentMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<Debt>> getDebts() {
        String sql = String.format("SELECT %s,%s, %s FROM %s INNER JOIN %s on debt.account_id = account.id_account INNER JOIN %s ON account.currency = currency.id_currency",
                TextUtils.join(",", getDebtProjection()),
                TextUtils.join(",", getAccountProjection()),
                TextUtils.join(",", getCurrencyProjection()),
                ExpensePersistenceContract.DebtEntry.TABLE_NAME,
                ExpensePersistenceContract.AccountEntry.TABLE_NAME,
                ExpensePersistenceContract.CurrencyEntry.TABLE_NAME);
        return databaseHelper.createQuery(ExpensePersistenceContract.DebtEntry.TABLE_NAME, sql)
                .mapToList(debtMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<Debt> getDebtById(@NonNull int debtId) {
        String sql = String.format("SELECT %s,%s, %s FROM %s INNER JOIN %s on debt.account_id = account.id_account INNER JOIN %s ON account.currency = currency.id_currency WHERE id_debt LIKE ?",
                TextUtils.join(",", getDebtProjection()),
                TextUtils.join(",", getAccountProjection()),
                TextUtils.join(",", getCurrencyProjection()),
                ExpensePersistenceContract.DebtEntry.TABLE_NAME,
                ExpensePersistenceContract.AccountEntry.TABLE_NAME,
                ExpensePersistenceContract.CurrencyEntry.TABLE_NAME);
        return databaseHelper.createQuery(ExpensePersistenceContract.DebtEntry.TABLE_NAME, sql, String.valueOf(debtId))
                .mapToOne(cursor -> debtMapperFunction.apply(cursor))
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public void saveDebt(@NonNull Debt debt) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpensePersistenceContract.DebtEntry.COLUMN_SUM, debt.getSum());
        contentValues.put(ExpensePersistenceContract.DebtEntry.COLUMN_DEBT_TYPE, debt.getDebtType());
        contentValues.put(ExpensePersistenceContract.DebtEntry.COLUMN_DESCRIPTION, debt.getDescription());
        contentValues.put(ExpensePersistenceContract.DebtEntry.COLUMN_BORROW_DATE, debt.getBorrowDate());
        contentValues.put(ExpensePersistenceContract.DebtEntry.COLUMN_REPAY_DATE, debt.getRepayDate());
        contentValues.put(ExpensePersistenceContract.DebtEntry.COLUMN_BORROWER, debt.getBorrower());
        contentValues.put(ExpensePersistenceContract.DebtEntry.COLUMN_ACCOUNT, debt.getAccount().getId());
        databaseHelper.insert(ExpensePersistenceContract.DebtEntry.TABLE_NAME, contentValues);
    }

    @Override
    public void editDebt(@NonNull Debt debt) {
        ContentValues values = new ContentValues();
        values.put(ExpensePersistenceContract.DebtEntry.COLUMN_BORROWER, debt.getBorrower());
        values.put(ExpensePersistenceContract.DebtEntry.COLUMN_DESCRIPTION, debt.getDescription());
        databaseHelper.update(ExpensePersistenceContract.DebtEntry.TABLE_NAME, values, String.format("id_debt = %s", debt.getId()));
    }

    @Override
    public void deleteDebt(int debtId) {
        String selection = ExpensePersistenceContract.DebtEntry.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(debtId) };
        databaseHelper.delete(ExpensePersistenceContract.DebtEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public Flowable<List<Expense>> getDebtPayments(int debtId){
        String sql = String.format("SELECT %s,%s,%s,%s FROM %s INNER JOIN %s ON %s = %s INNER JOIN %s ON %s = %s INNER JOIN %s ON %s = %s WHERE %s = %s",
                TextUtils.join(",", getExpenseProjection()),
                TextUtils.join(",", getCategoryProjection()),
                TextUtils.join(",", getAccountProjection()),
                TextUtils.join(",", getCurrencyProjection()),
                ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                TextUtils.join(".", fInnerJoin),
                TextUtils.join(".", sInnerJoin),
                ExpensePersistenceContract.AccountEntry.TABLE_NAME,
                TextUtils.join(".", tInnerJoin),
                TextUtils.join(".", frInnerJoin),
                ExpensePersistenceContract.CurrencyEntry.TABLE_NAME,
                TextUtils.join(".", currencyInnerJoin),
                TextUtils.join(".", currencyToAccount),
                ExpensePersistenceContract.ExpenseEntry.COLUMN_DEBT,
                String.valueOf(debtId));
        Log.wtf("sqlite", sql);
        return databaseHelper.createQuery
                (getExpenseTableList(), sql)
                .mapToList(expenseMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public void saveDebtPayment(@NonNull Expense expense) {
        saveExpense(expense);
    }

    @Override
    public Flowable<List<PurchaseList>> getPurchaseLists() {
        String sql = "SELECT * FROM purchase_list";
        return databaseHelper.createQuery(ExpensePersistenceContract.PurchaseListEntry.TABLE_NAME, sql)
                .mapToList(purchaseListMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<Purchase>> getPurchasesByList(String purchaseListId) {
        String sql = String.format("SELECT * FROM %s WHERE %s = %s",
                ExpensePersistenceContract.PurchaseEntry.TABLE_NAME,
                ExpensePersistenceContract.PurchaseEntry.COLUMN_PURCHASE_LIST,
                purchaseListId);
        return databaseHelper.createQuery(ExpensePersistenceContract.PurchaseEntry.TABLE_NAME, sql)
                .mapToList(purchaseMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public void deletePurchase(int purchaseId) {
        String selection = ExpensePersistenceContract.PurchaseEntry.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(purchaseId) };
        databaseHelper.delete(ExpensePersistenceContract.PurchaseEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public void renamePurchaseList(int purchaseListId, String name) {
        ContentValues values = new ContentValues();
        values.put(ExpensePersistenceContract.PurchaseListEntry.COLUMN_TITLE, name);
        databaseHelper.update(ExpensePersistenceContract.PurchaseListEntry.TABLE_NAME, values,
                String.format("%s=%d",ExpensePersistenceContract.PurchaseListEntry.COLUMN_ID, purchaseListId));
    }

    @Override
    public void deletePurchaseList(int purchaseList) {
        String selection = ExpensePersistenceContract.PurchaseListEntry.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(purchaseList) };
        databaseHelper.delete(ExpensePersistenceContract.PurchaseListEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public void createPurchaseList(String purchaseListTitle) {
        ContentValues expenseValues = new ContentValues();
        expenseValues.put(ExpensePersistenceContract.PurchaseListEntry.COLUMN_TITLE, purchaseListTitle);
        databaseHelper.insert(ExpensePersistenceContract.PurchaseListEntry.TABLE_NAME,
                expenseValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void createPurchase(Purchase purchase) {
        ContentValues expenseValues = new ContentValues();
        expenseValues.put(ExpensePersistenceContract.PurchaseEntry.COLUMN_TITLE, purchase.getTitle());
        expenseValues.put(ExpensePersistenceContract.PurchaseEntry.COLUMN_PURCHASE_LIST, purchase.getPurchaseListId());
        databaseHelper.insert(ExpensePersistenceContract.PurchaseEntry.TABLE_NAME, expenseValues, SQLiteDatabase.CONFLICT_NONE);
    }

    @Override
    public Flowable<HashMap<String, Integer>> getExpensesStructureData(String type) {
        String[] sInnerJoin = {CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID};
        String[] fInnerJoin = {ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY};
        String sql = String.format("SELECT SUM(%s), %s FROM %s INNER JOIN %s ON %s = %s WHERE %s = %s GROUP BY %s",
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_COST,
                TextUtils.join(".", new String[]{CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME, CategoryPersistenceContract.SubcategoryEntry.COLUMN_TITLE} ),
                ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                TextUtils.join(".", fInnerJoin),
                TextUtils.join(".", sInnerJoin),
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_EXPENSE_TYPE,
                type,
                TextUtils.join(".", new String[]{CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME, CategoryPersistenceContract.SubcategoryEntry.COLUMN_TITLE} ));
        Log.wtf("myLog", sql);
        return databaseHelper.createQuery(getExpenseTableList(), sql)
                .mapToOne(expenseStructureMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<HashMap<String, String>> getExpensesDataByDate(String fdate, String sdate) {
        return null;
    }

    @Override
    public Flowable<List<Goal>> getGoals(int state) {
        String sql = String.format("SELECT %s, %s FROM %s INNER JOIN %s ON goal.currency_id = currency.id_currency WHERE %s = %d",
                TextUtils.join(",", getGoalProjection()),
                TextUtils.join(",", getCurrencyProjection()),
                ExpensePersistenceContract.GoalEntry.TABLE_NAME,
                ExpensePersistenceContract.CurrencyEntry.TABLE_NAME,
                ExpensePersistenceContract.GoalEntry.COLUMN_STATUS, state);
        Log.wtf("MyLog", sql);
        return databaseHelper.createQuery(ExpensePersistenceContract.GoalEntry.TABLE_NAME, sql)
                .mapToList(goalMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<Goal> getGoalById(@NonNull String goalId) {
        String sql = String.format("SELECT %s, %s FROM %s INNER JOIN %s ON goal.currency_id = currency.id_currency WHERE id_goal LIKE ?",
                TextUtils.join(",", getGoalProjection()),
                TextUtils.join(",", getCurrencyProjection()),
                ExpensePersistenceContract.GoalEntry.TABLE_NAME,
                ExpensePersistenceContract.CurrencyEntry.TABLE_NAME);
        return databaseHelper.createQuery(ExpensePersistenceContract.GoalEntry.TABLE_NAME, sql, goalId)
                .mapToOne(cursor -> goalMapperFunction.apply(cursor))
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public void saveGoal(@NonNull Goal goal) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpensePersistenceContract.GoalEntry.COLUMN_TITLE, goal.getTitle());
        contentValues.put(ExpensePersistenceContract.GoalEntry.COLUMN_NEEDED_AMOUNT, goal.getNeededAmount());
        contentValues.put(ExpensePersistenceContract.GoalEntry.COLUMN_ACCUMULATED_AMOUNT, goal.getAccumulatedAmount());
        contentValues.put(ExpensePersistenceContract.GoalEntry.COLUMN_WANTED_DATE, goal.getWantedDate());
        contentValues.put(ExpensePersistenceContract.GoalEntry.COLUMN_START_DATE, goal.getStartDate());
        contentValues.put(ExpensePersistenceContract.GoalEntry.COLUMN_NOTE, goal.getNote());
        contentValues.put(ExpensePersistenceContract.GoalEntry.COLUMN_STATUS, goal.getState());
        contentValues.put(ExpensePersistenceContract.GoalEntry.COLUMN_ICON, goal.getIcon());
        contentValues.put(ExpensePersistenceContract.GoalEntry.COLUMN_COLOR, goal.getColor());
        contentValues.put(ExpensePersistenceContract.GoalEntry.COLUMN_CURRENCY, goal.getCurrency().getId());
        databaseHelper.insert(ExpensePersistenceContract.GoalEntry.TABLE_NAME, contentValues);
    }

    @Override
    public void editGoal(String goalId, @NonNull Goal goal) {
        ContentValues values = new ContentValues();
        values.put(ExpensePersistenceContract.GoalEntry.COLUMN_TITLE, goal.getTitle());
        values.put(ExpensePersistenceContract.GoalEntry.COLUMN_NEEDED_AMOUNT, goal.getNeededAmount());
        values.put(ExpensePersistenceContract.GoalEntry.COLUMN_ACCUMULATED_AMOUNT, goal.getAccumulatedAmount());
        values.put(ExpensePersistenceContract.GoalEntry.COLUMN_WANTED_DATE, goal.getWantedDate());
        values.put(ExpensePersistenceContract.GoalEntry.COLUMN_NOTE, goal.getNote());
        databaseHelper.update(ExpensePersistenceContract.GoalEntry.TABLE_NAME, values, String.format("id_goal=%s", goalId));
    }

    @Override
    public void deleteGoal(@NonNull String goalId) {
        String selection = ExpensePersistenceContract.GoalEntry.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(goalId) };
        databaseHelper.delete(ExpensePersistenceContract.GoalEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public void makeGoalPaused(String goalId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpensePersistenceContract.GoalEntry.COLUMN_STATUS, 2);
        databaseHelper.update(ExpensePersistenceContract.GoalEntry.TABLE_NAME, contentValues, String.format("id_goal=%s", goalId));
    }

    @Override
    public void makeGoalAchieved(int goalId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpensePersistenceContract.GoalEntry.COLUMN_STATUS, 3);
        databaseHelper.update(ExpensePersistenceContract.GoalEntry.TABLE_NAME, contentValues, String.format("id_goal=%s", goalId));
    }

    @Override
    public void makeGoalActive(String goalId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpensePersistenceContract.GoalEntry.COLUMN_STATUS, 1);
        databaseHelper.update(ExpensePersistenceContract.GoalEntry.TABLE_NAME, contentValues, String.format("id_goal=%s", goalId));
    }

    @Override
    public void addAmount(String goalId, double amount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpensePersistenceContract.GoalEntry.COLUMN_ACCUMULATED_AMOUNT, amount);
        databaseHelper.update(ExpensePersistenceContract.GoalEntry.TABLE_NAME, contentValues, String.format("id_goal=%s", goalId));
    }

    @Override
    public Flowable<List<String>> getExpenseAddresses() {
        String sql = String.format("SELECT %s FROM %s WHERE address_coordinates IS NOT NULL", ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDRESS_COORDINATES, ExpensePersistenceContract.ExpenseEntry.TABLE_NAME);
        return databaseHelper.createQuery(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, sql)
                .mapToList(new Function<Cursor, String>() {
                    @Override
                    public String apply(Cursor cursor) throws Exception {
                        return cursor.getString(cursor.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDRESS_COORDINATES));
                    }
                })
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<String>> getExpenseAddressesByDate(String fdate, String sdate) {
        String sql = String.format("SELECT %s FROM %s WHERE address_coordinates IS NOT NULL AND date >= '%s' AND date <= '%s'",
                ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDRESS_COORDINATES,
                ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, fdate, sdate);
        Log.wtf("MyLog", sql);
        return databaseHelper.createQuery(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, sql)
                .mapToList(new Function<Cursor, String>() {
                    @Override
                    public String apply(Cursor cursor) throws Exception {
                        return cursor.getString(cursor.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDRESS_COORDINATES));
                    }
                })
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    private String[] getExpenseProjection(){
        return new String[]{
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_ID,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_COST,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_EXPENSE_TYPE,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NOTE,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_MARKS,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_RECEIVER,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_DATE,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_TIME,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_TYPE_OF_PAYMENT,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_PLACE,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDITION,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_ACCOUNT,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDRESS_COORDINATES,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_PLANNED_PAYMENT,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_DEBT};
    }

    private String[] getCategoryProjection(){
        return new String[]{ CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID,
                CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME + "." + CategoryPersistenceContract.SubcategoryEntry.COLUMN_TITLE,
                CategoryPersistenceContract.SubcategoryEntry.COLUMN_CATEGORY_ID};
    }

    private String[] getPlannedPaymentProjection(){
        return new String[]{ ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_ID,
                ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_COST,
                ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_TITLE,
                ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_START_DATE,
                ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_END_DATE,
                ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_FREQUENCY,
                ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_TIMING,
                ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_DAY,
                ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_CATEGORY_ID,
                ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_ACCOUNT};
    }

    private String[] getDebtProjection(){
        return new String[]{
                ExpensePersistenceContract.DebtEntry.COLUMN_ID,
                ExpensePersistenceContract.DebtEntry.COLUMN_SUM,
                ExpensePersistenceContract.DebtEntry.COLUMN_DESCRIPTION,
                ExpensePersistenceContract.DebtEntry.COLUMN_BORROW_DATE,
                ExpensePersistenceContract.DebtEntry.COLUMN_REPAY_DATE,
                ExpensePersistenceContract.DebtEntry.COLUMN_BORROWER,
                ExpensePersistenceContract.DebtEntry.COLUMN_DEBT_TYPE,
                ExpensePersistenceContract.DebtEntry.COLUMN_DEBT_REMAIN
        };
    }

    private String[] getAccountProjection(){
        return new String[]{
                ExpensePersistenceContract.AccountEntry.COLUMN_NAME_ID,
                ExpensePersistenceContract.AccountEntry.COLUMN_NAME_TITLE,
                ExpensePersistenceContract.AccountEntry.COLUMN_NAME_AMOUNT,
                ExpensePersistenceContract.AccountEntry.COLUMN_CURRENCY,
                ExpensePersistenceContract.AccountEntry.COLUMN_COLOR,
        };
    }

    private String[] getGoalProjection(){
        return new String[]{
                ExpensePersistenceContract.GoalEntry.COLUMN_ID,
                ExpensePersistenceContract.GoalEntry.COLUMN_TITLE,
                ExpensePersistenceContract.GoalEntry.COLUMN_NOTE,
                ExpensePersistenceContract.GoalEntry.COLUMN_NEEDED_AMOUNT,
                ExpensePersistenceContract.GoalEntry.COLUMN_ACCUMULATED_AMOUNT,
                ExpensePersistenceContract.GoalEntry.COLUMN_START_DATE,
                ExpensePersistenceContract.GoalEntry.COLUMN_WANTED_DATE,
                ExpensePersistenceContract.GoalEntry.COLUMN_ICON,
                ExpensePersistenceContract.GoalEntry.COLUMN_COLOR,
                ExpensePersistenceContract.GoalEntry.COLUMN_STATUS
        };
    }

    private String[] getCurrencyProjection(){
        return new String[]{
                ExpensePersistenceContract.CurrencyEntry.COLUMN_ID,
                ExpensePersistenceContract.CurrencyEntry.COLUMN_TITLE,
                ExpensePersistenceContract.CurrencyEntry.COLUMN_CODE,
                ExpensePersistenceContract.CurrencyEntry.COLUMN_SYMBOL,
                ExpensePersistenceContract.CurrencyEntry.COLUMN_RATE_TO_BASE,
                ExpensePersistenceContract.CurrencyEntry.COLUMN_RATE_BASE_TO_THIS,
                ExpensePersistenceContract.CurrencyEntry.COLUMN_IS_BASE,
        };
    }

}
