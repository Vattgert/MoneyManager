package com.example.productmanagment.data.source.expenses;

/**
 * Created by Ivan on 30.01.2018.
 */

public class ExpensePersistenceContract {
    public ExpensePersistenceContract() {}

    public class ExpenseEntry{
        public static final String TABLE_NAME = "expense";
        public static final String COLUMN_NAME_ID = "id_expense";
        public static final String COLUMN_NAME_COST = "cost";
        public static final String COLUMN_NOTE = "note";
        public static final String COLUMN_NAME_MARKS = "marks";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_RECEIVER = "receiver";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_NAME_TYPE_OF_PAYMENT = "typeOfPayment";
        public static final String COLUMN_NAME_PLACE = "place";
        public static final String COLUMN_ADDITION = "addition";
    }
}
