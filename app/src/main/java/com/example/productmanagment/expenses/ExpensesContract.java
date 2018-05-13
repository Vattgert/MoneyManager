package com.example.productmanagment.expenses;

import android.support.annotation.NonNull;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Expense;

import java.util.List;

/**
 * Created by Ivan on 24.01.2018.
 */

public interface ExpensesContract {
    interface View extends BaseView<Presenter>{
        void setLoadingIndicator(boolean active);
        void showExpenses(List<Expense> expenses);
        void showAccounts(List<Account> accounts);
        void showAddExpense(int groupId);
        void showLoadingExpensesError();
        void showNoExpenses();
        void showExpenseDetail(int expenseId, int groupId);
        void showExpenseSuccessfullySavedMessage();
        int getSelectedAccountId();
    }

    interface Presenter extends BasePresenter {
        void expenseLoading(String accountId);
        void loadExpenses(boolean showLoadingUi);
        void loadRemoteExpenses(String accountId);
        void loadExpensesByAccount(String accountId);
        void loadAccounts();
        void loadRemoteAccounts(String groupId);
        void openExpenseDetails(@NonNull Expense requestedTask);
        void addNewExpense();
        void result(int requestCode, int resultCode);
    }
}
