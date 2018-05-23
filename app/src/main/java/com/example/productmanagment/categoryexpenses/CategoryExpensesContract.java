package com.example.productmanagment.categoryexpenses;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Expense;

import java.util.List;

public interface CategoryExpensesContract {
    interface Presenter extends BasePresenter{
        void loadByCategory(String category);
    }

    interface View extends BaseView<Presenter>{
        void showExpenses(List<Expense> data);
    }
}
