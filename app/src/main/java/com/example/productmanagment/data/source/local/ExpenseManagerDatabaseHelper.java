package com.example.productmanagment.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Ivan on 12.11.2017.
 */

public class ExpenseManagerDatabaseHelper extends SQLiteAssetHelper {
    private Context context;
    private static final String DB_NAME = "expenseDb.db";
    private static int DB_VERSION = 1;

    public ExpenseManagerDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
