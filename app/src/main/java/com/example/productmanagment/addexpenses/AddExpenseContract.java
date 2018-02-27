package com.example.productmanagment.addexpenses;

import android.content.Intent;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.ExpenseInformation;

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
        void saveExpense(double cost, Category category, ExpenseInformation information);
    }
    interface View extends BaseView<Presenter>{
        void setChosenCategory(String title);
        void showExpenses();
        void showChoosePlacePicker();
        void setChosenPlace(String place);
    }
}
