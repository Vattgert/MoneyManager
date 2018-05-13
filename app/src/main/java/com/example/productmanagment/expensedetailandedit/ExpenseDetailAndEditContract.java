package com.example.productmanagment.expensedetailandedit;

import android.content.Intent;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.data.models.User;

import java.util.List;

/**
 * Created by Ivan on 12.02.2018.
 */

public interface ExpenseDetailAndEditContract {
    interface View extends BaseView<Presenter> {
        void setAccounts(List<Account> accounts);

        void showCost(double cost);
        void showNote(String note);
        void showReceiver(String receiver);
        void showDate(String date);
        void showTime(String time);
        void showTypeOfPayment(String typeOfPayment);
        void showCategory(String category);
        void showPlace(String address);
        void showAddition();
        void showExpenseType(String expenseType);
        void showAccount(int accountId);
        void showNoExpense();
        void showMessage(String message);
        void finish();
    }

    interface Presenter extends BasePresenter{
        void editExpense(Expense expense);
        Category getChosenCategory();
        User getCurrentUser();
        void deleteTask();
        void result(int requestCode, int resultCode, Intent data);
    }
}
