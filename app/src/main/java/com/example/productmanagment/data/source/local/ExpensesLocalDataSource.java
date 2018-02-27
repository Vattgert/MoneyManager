package com.example.productmanagment.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.source.categories.CategoryPersistenceContract;
import com.example.productmanagment.data.source.expenses.ExpensePersistenceContract;
import com.example.productmanagment.data.source.expenses.ExpensesDataSource;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

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

    private ExpensesLocalDataSource(Context context, BaseSchedulerProvider schedulerProvider) {
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        ExpenseManagerDatabaseHelper helper = new ExpenseManagerDatabaseHelper(context);
        databaseHelper = sqlBrite.wrapDatabaseHelper(helper, schedulerProvider.io());
        expenseMapperFunction = this::getExpense;
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

    @NonNull
    private Expense getExpense(@NonNull Cursor c) {
        int itemId = c.getInt(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_ID));
        double cost = c.getDouble(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_COST));

        String note = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NOTE));
        String marks = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_MARKS));
        String receiver = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_RECEIVER));
        String date = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_DATE));
        String time = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_TIME));
        String typeOfPayment = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_TYPE_OF_PAYMENT));
        String place = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_PLACE));
        String addition = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDITION));
        ExpenseInformation expenseInformation = new ExpenseInformation(note, marks, receiver, date,
                time, typeOfPayment, place, addition);

        int subcategoryId = c.getInt(c.getColumnIndexOrThrow(CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID));
        String title = c.getString(c.getColumnIndexOrThrow(CategoryPersistenceContract.SubcategoryEntry.COLUMN_TITLE));
        int categoryId = c.getInt(c.getColumnIndexOrThrow(CategoryPersistenceContract.SubcategoryEntry.COLUMN_CATEGORY_ID));
        Subcategory category = new Subcategory(subcategoryId, title, categoryId);

        return new Expense(itemId, cost, category, expenseInformation);
    }
    @Override
    public Flowable<List<Expense>> getExpenses() {
        String[] expenseProjection = { ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_ID,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_COST,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NOTE,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_MARKS,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_RECEIVER,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_DATE,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_TIME,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_TYPE_OF_PAYMENT,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_PLACE,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDITION,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY};
        String[] categoryProjection = { CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID,
                CategoryPersistenceContract.SubcategoryEntry.COLUMN_TITLE,
                CategoryPersistenceContract.SubcategoryEntry.COLUMN_CATEGORY_ID };
        String[] fInnerJoin = {CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID };
        String[] sInnerJoin = {ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY};
        String sql = String.format("SELECT %s,%s FROM %s INNER JOIN %s ON %s = %s",
                TextUtils.join(",", expenseProjection),
                TextUtils.join(",", categoryProjection),
                ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                TextUtils.join(".", fInnerJoin),
                TextUtils.join(".", sInnerJoin));

        ArrayList<String> tableList = new ArrayList<>(Arrays.asList(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME
                ,CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME));
        return databaseHelper.createQuery(tableList, sql)
                .mapToList(expenseMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }
    //TODO: Уменьшить размер функций
    @Override
    public void saveExpense(@NonNull Expense expense) {
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
        databaseHelper.insert(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void deleteExpense(@NonNull String expenseId) {
        String selection = ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = {expenseId};
        databaseHelper.delete(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public Flowable<Expense> getExpenseById(@NonNull String expenseId) {
        String[] projection = { ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_ID,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_COST,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NOTE,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_MARKS,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_RECEIVER,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_DATE,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_TIME,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_TYPE_OF_PAYMENT,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_PLACE,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDITION,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY};
        String[] category = { CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID,
                             CategoryPersistenceContract.SubcategoryEntry.COLUMN_TITLE,
                             CategoryPersistenceContract.SubcategoryEntry.COLUMN_CATEGORY_ID};
        String[] sInnerJoin = {CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID };
        String[] fInnerJoin = {ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY};
        ArrayList<String> tableList = new ArrayList<>(Arrays.asList(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME
                ,CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME));
        String sql = String.format("SELECT %s,%s FROM %s INNER JOIN %s ON %s = %s WHERE %s LIKE ?",
                TextUtils.join(",", projection),
                TextUtils.join(",", category),
                ExpensePersistenceContract.ExpenseEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                TextUtils.join(".", fInnerJoin),
                TextUtils.join(".", sInnerJoin),
                ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_ID);
        Log.wtf("sqlite", sql);
        return databaseHelper.createQuery(tableList, sql, expenseId)
                .mapToOne(cursor -> expenseMapperFunction.apply(cursor))
                .toFlowable(BackpressureStrategy.BUFFER);
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
}
