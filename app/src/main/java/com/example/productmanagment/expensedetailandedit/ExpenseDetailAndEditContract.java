package com.example.productmanagment.expensedetailandedit;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;

/**
 * Created by Ivan on 12.02.2018.
 */

public interface ExpenseDetailAndEditContract {
    interface View extends BaseView<Presenter> {
        void showCost(double cost);
        void showNote(String note);
        void showMarks();
        void showReceiver(String receiver);
        void showDate(String date);
        void showTime(String time);
        void showTypeOfPayment(String typeOfPayment);
        void showCategory(String category);
        void showPlace(String address);
        void showAddition();
        void showNoExpense();
        void finish();
    }

    interface Presenter extends BasePresenter{
        void editExpense(Expense expense);
        void deleteTask();
    }
}
