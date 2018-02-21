package com.example.productmanagment.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.source.expenses.ExpensePersistenceContract;
import com.example.productmanagment.data.source.expenses.ExpensesDataSource;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

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
        String category = c.getString(c.getColumnIndexOrThrow(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY));
        return new Expense(itemId, cost, marks, note, receiver, date, time, typeOfPayment, place, addition, category);
    }
    @Override
    public Flowable<List<Expense>> getExpenses() {
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
        String sql = String.format("SELECT %s FROM %s ",
                TextUtils.join(",", projection),
                ExpensePersistenceContract.ExpenseEntry.TABLE_NAME);
        return databaseHelper.createQuery(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, sql)
                .mapToList(expenseMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public void saveExpense(@NonNull Expense expense) {
        ContentValues values = new ContentValues();
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_COST, expense.getCost());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NOTE, expense.getNote());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_MARKS, expense.getMarks());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY, expense.getCategory());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_RECEIVER, expense.getReceiver());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_DATE, expense.getDate());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_TIME, expense.getTime());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_TYPE_OF_PAYMENT, expense.getTypeOfPayment());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_PLACE, expense.getPlace());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDITION, expense.getAddition());
        databaseHelper.insert(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void deleteExpense(@NonNull String expenseId) {
        String selection = ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = {expenseId};
        databaseHelper.delete(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public Flowable<Expense> getExpense(@NonNull String expenseId) {
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
        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?",
                TextUtils.join(",", projection), ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_ID);
        return databaseHelper.createQuery(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, sql, expenseId)
                .mapToOne(cursor -> expenseMapperFunction.apply(cursor))
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public void updateExpense(@NonNull String expenseId, Expense expense) {
        ContentValues values = new ContentValues();
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_COST, expense.getCost());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NOTE, expense.getNote());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_MARKS, expense.getMarks());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_CATEGORY, expense.getCategory());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_RECEIVER, expense.getReceiver());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_DATE, expense.getDate());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_TIME, expense.getTime());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_TYPE_OF_PAYMENT, expense.getTypeOfPayment());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_NAME_PLACE, expense.getPlace());
        values.put(ExpensePersistenceContract.ExpenseEntry.COLUMN_ADDITION, expense.getAddition());
        databaseHelper.update(ExpensePersistenceContract.ExpenseEntry.TABLE_NAME, values, String.format("id=%s", expenseId));
    }
}
