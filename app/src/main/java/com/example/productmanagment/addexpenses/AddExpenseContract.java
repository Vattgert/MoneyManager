package com.example.productmanagment.addexpenses;

import android.content.Intent;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;

/**
 * Created by Ivan on 30.01.2018.
 */

public interface AddExpenseContract{
    interface Presenter extends BasePresenter{
        void showEmptyExpenseError();
        void showExpenses();
        void choosePlace();
        void result(int requestCode, int resultCode, Intent data);
        void saveExpense(double cost, String note, String marks, String receiver, String date, String time,
                         String typeOfPayment, String place, String addition, String category);
    }
    interface View extends BaseView<Presenter>{
        void setChosenCategory(String title);
        void showExpenses();
        void showChoosePlacePicker();
        void setChosenPlace(String place);
    }
}
