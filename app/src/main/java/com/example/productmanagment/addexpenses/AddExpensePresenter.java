package com.example.productmanagment.addexpenses;

import android.app.Activity;
import android.content.Intent;

import com.example.productmanagment.categories.CategoryActivity;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Ivan on 30.01.2018.
 */

public class AddExpensePresenter implements AddExpenseContract.Presenter {
    private ExpensesRepository expensesRepository;
    private AddExpenseContract.View view;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;

    public AddExpensePresenter(ExpensesRepository expensesRepository, AddExpenseContract.View view, BaseSchedulerProvider schedulerProvider) {
        this.expensesRepository = expensesRepository;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void showEmptyExpenseError() {

    }

    @Override
    public void showExpenses() {
        view.showExpenses();
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if(CategoryActivity.GET_CATEGORY_REQUEST == requestCode && Activity.RESULT_OK == resultCode){
            view.setChosenCategory(data.getStringExtra("subcategoryTitle"));
        }
    }

    private void createTask(double cost, String note, String marks, String category, String receiver, String date, String typeOfPayment, String place, String addition) {
        Expense expense  = new Expense(cost, note, marks, category, receiver, date, typeOfPayment, place, addition);
        expensesRepository.saveExpense(expense);
        view.showExpenses();
    }
}
