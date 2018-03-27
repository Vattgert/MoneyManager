package com.example.productmanagment.expenses;

import android.support.annotation.NonNull;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Expense;

import java.util.List;

/**
 * Created by Ivan on 24.01.2018.
 */

public interface ExpensesContract {
    interface View extends BaseView<Presenter>{

        void setLoadingIndicator(boolean active);
        void showExpenses(List<Expense> expenses);
        void showAddExpense();
        void showLoadingExpensesError();
        void showNoExpenses();
        void showExpenseSuccessfullySavedMessage();
    }

    interface Presenter extends BasePresenter {
        void loadExpenses(boolean showLoadingUi);
        void openExpenseDetails(@NonNull Expense requestedTask);
        void addNewExpense();
        void result(int requestCode, int resultCode);
    }
}
