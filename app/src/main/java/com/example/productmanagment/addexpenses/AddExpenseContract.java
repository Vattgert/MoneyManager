package com.example.productmanagment.addexpenses;

import android.content.Intent;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.google.android.gms.location.places.Place;

import java.util.List;

/**
 * Created by Ivan on 30.01.2018.
 */

public interface AddExpenseContract{
    interface Presenter extends BasePresenter{
        void showEmptyExpenseError();
        void showExpenses();
        Category getChosenCategory();
        void choosePlace();
        void result(int requestCode, int resultCode, Intent data);
        void saveExpense(Expense expense);
        void loadAccounts();
    }
    interface View extends BaseView<Presenter>{
        void setChosenCategory(String title);
        void showExpenses();
        void showChoosePlacePicker();
        void setChosenPlace(String place);
        void setAddress(Place place);
        void showAccounts(List<Account> accountList);
        void showMessage(String message);
    }
}
