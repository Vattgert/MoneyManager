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
        public static final String COLUMN_EXPENSE_INFORMATION = "expense_information";
        public static final String COLUMN_NAME_RECEIVER = "receiver";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_NAME_TYPE_OF_PAYMENT = "typeOfPayment";
        public static final String COLUMN_NAME_PLACE = "place";
        public static final String COLUMN_ADDITION = "addition";
        public static final String COLUMN_ACCOUNT = "account_id";
    }

    public class PlannedPaymentEntry{
        public static final String TABLE_NAME = "planned_payment";
        public static final String COLUMN_ID = "id_planned_payment";
        public static final String COLUMN_COST = "cost";
        public static final String COLUMN_TITLE = "payment_title";
        public static final String COLUMN_START_DATE = "start_date";
        public static final String COLUMN_END_DATE = "end_date";
        public static final String COLUMN_FREQUENCY = "frequency";
        public static final String COLUMN_TIMING = "timing";
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_CATEGORY_ID = "id_category";
        public static final String COLUMN_ACCOUNT = "account";
    }
}
