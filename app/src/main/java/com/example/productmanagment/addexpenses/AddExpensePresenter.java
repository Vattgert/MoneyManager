package com.example.productmanagment.addexpenses;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.productmanagment.categories.CategoryActivity;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Ivan on 30.01.2018.
 */

public class AddExpensePresenter implements AddExpenseContract.Presenter {
    private ExpensesRepository expensesRepository;
    private AddExpenseContract.View view;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;
    private Context context;
    public AddExpensePresenter(ExpensesRepository expensesRepository, AddExpenseContract.View view, BaseSchedulerProvider schedulerProvider, Context context) {
        this.expensesRepository = expensesRepository;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.view.setPresenter(this);
        this.context = context;
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
    public void choosePlace() {
        view.showChoosePlacePicker();
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if( Activity.RESULT_OK == resultCode){
            switch (requestCode){
                case CategoryActivity.GET_CATEGORY_REQUEST:
                    view.setChosenCategory(data.getStringExtra("subcategoryTitle"));
                    break;
                case AddExpenseActivity.REQUEST_PLACE_PICKER:
                    Place place = PlacePicker.getPlace(context,data);
                    view.setChosenPlace(String.format("%s", place.getAddress()));
                    break;
            }

        }
    }

    @Override
    public void saveExpense(double cost, String note, String marks, String receiver, String date, String time, String typeOfPayment, String place, String addition, String category) {
        createTask(cost, note, marks, receiver, date, time, typeOfPayment, place, addition, category);
    }

    private void createTask(double cost, String note, String marks, String receiver, String date, String time, String typeOfPayment, String place, String addition, String category) {
         Expense expense  = new Expense(cost, note, marks,
                 receiver, date, time, typeOfPayment, place, addition, category);
         expensesRepository.saveExpense(expense);
         view.showExpenses();
    }
}
