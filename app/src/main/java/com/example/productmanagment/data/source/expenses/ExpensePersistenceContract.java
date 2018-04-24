package com.example.productmanagment.data.source.expenses;

/**
 * Created by Ivan on 30.01.2018.
 */

public class ExpensePersistenceContract {
    public ExpensePersistenceContract() {}

    public class AccountEntry{
        public static final String TABLE_NAME = "account";
        public static final String COLUMN_NAME_ID = "id_account";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_CURRENCY = "currency";
        public static final String COLUMN_COLOR = "color";
    }

    public class ExpenseEntry{
        public static final String TABLE_NAME = "expense";
        public static final String COLUMN_NAME_ID = "id_expense";
        public static final String COLUMN_NAME_COST = "cost";
        public static final String COLUMN_NAME_EXPENSE_TYPE = "expense_type";
        public static final String COLUMN_NOTE = "note";
        public static final String COLUMN_NAME_MARKS = "marks";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_RECEIVER = "receiver";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_NAME_TYPE_OF_PAYMENT = "typeOfPayment";
        public static final String COLUMN_NAME_PLACE = "place";
        public static final String COLUMN_ADDRESS_COORDINATES = "address_coordinates";
        public static final String COLUMN_ADDITION = "addition";
        public static final String COLUMN_ACCOUNT = "account_id";
        public static final String COLUMN_DEBT = "debt_id";
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

    public class DebtEntry{
        public static final String TABLE_NAME = "debt";
        public static final String COLUMN_ID = "id_debt";
        public static final String COLUMN_SUM = "debt_sum";
        public static final String COLUMN_DESCRIPTION = "debt_description";
        public static final String COLUMN_BORROW_DATE = "borrow_date";
        public static final String COLUMN_REPAY_DATE = "repay_date";
        public static final String COLUMN_BORROWER = "borrower";
        public static final String COLUMN_DEBT_TYPE = "debt_type";
        public static final String COLUMN_DEBT_REMAIN = "debt_remain";
    }

    public class GoalEntry{
        public static  final String TABLE_NAME = "goal";
        public static  final String COLUMN_ID = "id_goal";
        public static  final String COLUMN_TITLE = "title";
        public static  final String COLUMN_NOTE = "note";
        public static  final String COLUMN_NEEDED_AMOUNT = "needed_amount";
        public static  final String COLUMN_ACCUMULATED_AMOUNT = "accumulated_amount";
        public static  final String COLUMN_WANTED_DATE = "wanted_date";
        public static  final String COLUMN_STATUS = "status";
        public static  final String COLUMN_ICON = "icon";
        public static  final String COLUMN_COLOR = "color";
    }

    public class PurchaseListEntry{
        public static final String TABLE_NAME = "purchase_list";
        public static final String COLUMN_ID = "id_purchase_list";
        public static final String COLUMN_TITLE = "title";
    }

    public class PurchaseEntry{
        public static final String TABLE_NAME = "purchase";
        public static final String COLUMN_ID = "id_purchase";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PURCHASE_LIST = "purchase_list_id";
    }

    public class CurrencyEntry{
        public static final String TABLE_NAME = "currency";
        public static final String COLUMN_ID = "id_currency";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_SYMBOL = "symbol";
        public static final String COLUMN_RATE_TO_BASE = "rateToBaseCurrency";
        public static final String COLUMN_RATE_BASE_TO_THIS = "rateBaseToThis";
        public static final String COLUMN_IS_BASE = "isBase";
    }
}
