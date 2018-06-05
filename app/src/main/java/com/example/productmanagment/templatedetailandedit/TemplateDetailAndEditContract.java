package com.example.productmanagment.templatedetailandedit;

import android.content.Intent;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Template;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.expensedetailandedit.ExpenseDetailAndEditContract;

import java.util.List;

public interface TemplateDetailAndEditContract {
    interface View extends BaseView<Presenter> {
        void setAccounts(List<Account> accounts);
        void showTitle(String title);
        void showCost(String cost);
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

    interface Presenter extends BasePresenter {
        void editTemplate(Template template);
        Category getChosenCategory();
        User getCurrentUser();
        void deleteTemplate();
        void createExpenseByTemplate();
        void result(int requestCode, int resultCode, Intent data);
    }
}
