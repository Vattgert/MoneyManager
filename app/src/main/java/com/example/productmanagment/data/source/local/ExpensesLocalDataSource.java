package com.example.productmanagment.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Debt;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.data.models.PlannedPayment;
import com.example.productmanagment.data.models.Purchase;
import com.example.productmanagment.data.models.PurchaseList;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.source.categories.CategoryPersistenceContract;
import com.example.productmanagment.data.source.expenses.ExpensePersistenceContract;
import com.example.productmanagment.data.source.expenses.ExpensesDataSource;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    private ExpensesLocalDataSource(Context context, BaseSchedulerProvider schedulerProvider) {
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        ExpenseManagerDatabaseHelper helper = new ExpenseManagerDatabaseHelper(context);
        databaseHelper = sqlBrite.wrapDatabaseHelper(helper, schedulerProvider.io());
        expenseMapperFunction = this::getExpense;
        plannedPaymentMapperFunction = this::getPlannedPayment;
        debtMapperFunction = this::getDebt;
        purchaseListMapperFunction = this::getPurchaseLists;
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
        int expenseType = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_EXPENSE_TYPE));
        return new Expense(itemId, cost, getCategory(c), expenseType, getExpenseInformation(c));
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
        int categoryId = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.PlannedPaymentEntry.COLUMN_CATEGORY_ID));
        Subcategory subcategory = getCategory(c);
        return new PlannedPayment(cost, subcategory, null, title, startDate, endDate, frequency, timing);
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
        return new Debt(id, sum, remain, description, borrowDate, repayDate, borrower, debtType, 0);
    }

    private ExpenseInformation getExpenseInformation(Cursor c){
        String note = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NOTE));
        String marks = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_MARKS));
        String receiver = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_RECEIVER));
        String date = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_DATE));
        String time = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_TIME));
        String typeOfPayment = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_TYPE_OF_PAYMENT));
        String place = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_PLACE));
        String addition = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDITION));
        return new ExpenseInformation(note, marks, receiver, date,
                time, typeOfPayment, place, addition);
    }

    private Subcategory getCategory(Cursor c){
        int subcategoryId = c.getInt(c.getColumnIndexOrThrow(CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID));
        String title = c.getString(c.getColumnIndexOrThrow(CategoryPersistenceContract.SubcategoryEntry.COLUMN_TITLE));
        int categoryId = c.getInt(c.getColumnIndexOrThrow(CategoryPersistenceContract.SubcategoryEntry.COLUMN_CATEGORY_ID));
        return new Subcategory(subcategoryId, title, categoryId);
    }

    private PurchaseList getPurchaseLists(Cursor c){
        int id = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.PurchaseListEntry.COLUMN_ID));
        String title = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.PurchaseListEntry.COLUMN_TITLE));
        return new PurchaseList(id, title);
    }

    @Override
    public Flowable<List<Expense>> getExpenses() {
        String[] fInnerJoin = {CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID };
        String[] sInnerJoin = {ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY};
        String sql = String.format("SELECT %s,%s FROM %s INNER JOIN %s ON %s = %s",
                TextUtils.join(",", getExpenseProjection()),
                TextUtils.join(",", getCategoryProjection()),
                ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                TextUtils.join(".", fInnerJoin),
                TextUtils.join(".", sInnerJoin));
        Log.wtf("sqlite", sql);

        return databaseHelper.createQuery(getExpenseTableList(), sql)
                .mapToList(expenseMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }
    //TODO: Уменьшить размер функций
    @Override
    public void saveExpense(@NonNull Expense expense) {
        ContentValues expenseValues = new ContentValues();
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_COST, expense.getCost());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY, expense.getCategory().getId());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NOTE, expense.getExpenseInformation().getNote());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_MARKS, expense.getExpenseInformation().getMarks());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_RECEIVER, expense.getExpenseInformation().getReceiver());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_TYPE_OF_PAYMENT, expense.getExpenseInformation().getTypeOfPayment());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_DATE, expense.getExpenseInformation().getDate());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_TIME, expense.getExpenseInformation().getTime());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_PLACE, expense.getExpenseInformation().getPlace());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDITION, expense.getExpenseInformation().getAddition());
        expenseValues.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_DEBT, expense.getDebtId());

        databaseHelper.insert(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, expenseValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void deleteExpense(@NonNull String expenseId) {
        String selection = ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = {expenseId};
        databaseHelper.delete(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public Flowable<Expense> getExpenseById(@NonNull String expenseId) {

        String[] sInnerJoin = {CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID };
        String[] fInnerJoin = {ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY};
        String sql = String.format("SELECT %s,%s,%s FROM %s INNER JOIN %s ON %s = %s INNER JOIN %s ON %s = %s WHERE %s LIKE ?",
                TextUtils.join(",", getExpenseProjection()),
                TextUtils.join(",", getCategoryProjection()),
                ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                TextUtils.join(".", fInnerJoin),
                TextUtils.join(".", sInnerJoin),
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_ID);
        Log.wtf("sqlite", sql);
        return databaseHelper.createQuery(getExpenseTableList(), sql, expenseId)
                .mapToOne(cursor -> expenseMapperFunction.apply(cursor))
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
                ExpensePersistenceContract.ExpenseEntry.COLUMN_ACCOUNT};
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

    private ArrayList<String> getExpenseTableList(){
        return new ArrayList<>(Arrays.asList(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME
                ,CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME));
    }

    private ArrayList<String> getPlannedPaymentTableList(){
        return new ArrayList<>(Arrays.asList(ExpensePersistenceContract.PlannedPaymentEntry.TABLE_NAME
                ,CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME));
    }

    @Override
    public void updateExpense(@NonNull String expenseId, Expense expense) {
        ContentValues values = new ContentValues();
        ExpenseInformation information = expense.getExpenseInformation();
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_COST, expense.getCost());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NOTE, information.getNote());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_MARKS, information.getMarks());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY, expense.getCategory().getId());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_RECEIVER, information.getReceiver());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_DATE, information.getDate());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_TIME, information.getTime());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_TYPE_OF_PAYMENT, information.getTypeOfPayment());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_PLACE, information.getPlace());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDITION, information.getAddition());
        databaseHelper.update(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, values, String.format("id=%s", expenseId));
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
        String sql = String.format("SELECT %s FROM %s",
                TextUtils.join(",", getDebtProjection()),
                ExpensePersistenceContract.DebtEntry.TABLE_NAME);
        return databaseHelper.createQuery(ExpensePersistenceContract.DebtEntry.TABLE_NAME, sql)
                .mapToList(debtMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<Debt> getDebtById(@NonNull int debtId) {
        String sql = String.format("SELECT * FROM debt WHERE id_debt LIKE ?");
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
        String[] sInnerJoin = {CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID };
        String[] fInnerJoin = {ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY};
        String sql = String.format("SELECT %s,%s FROM expense INNER JOIN %s ON %s = %s WHERE %s LIKE ?",
                TextUtils.join(",", getExpenseProjection()),
                TextUtils.join(",", getCategoryProjection()),
                CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                TextUtils.join(".", fInnerJoin),
                TextUtils.join(".", sInnerJoin),
                ExpensePersistenceContract.ExpenseEntry.COLUMN_DEBT);
        Log.wtf("string_sql", sql);
        return databaseHelper.createQuery
                (getExpenseTableList(), sql, String.valueOf(debtId))
                .mapToList(expenseMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<PurchaseList>> getPurchaseLists() {
        String sql = "SELECT FROM purchaseList";
        return databaseHelper.createQuery(ExpensePersistenceContract.PurchaseListEntry.TABLE_NAME, sql)
                .mapToList(purchaseListMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public void saveDebtPayment(@NonNull Expense expense) {
        saveExpense(expense);
    }
}
