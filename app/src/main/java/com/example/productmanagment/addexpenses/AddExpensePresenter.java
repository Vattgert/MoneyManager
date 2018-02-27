package com.example.productmanagment.addexpenses;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.productmanagment.categories.CategoryActivity;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.data.models.Subcategory;
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
    private Context context;
    private Category chosenCategory;

    public AddExpensePresenter(ExpensesRepository expensesRepository, AddExpenseContract.View view, Context context) {
        this.expensesRepository = expensesRepository;
        this.view = view;
        this.view.setPresenter(this);
        this.context = context;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void showEmptyExpenseError() {

    }

    @Override
    public void showExpenses() {
        view.showExpenses();
    }

    @Override
    public Category getChosenCategory() {
        if (chosenCategory != null)
            return chosenCategory;
        return null;
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
                    chosenCategory = new Subcategory(data.getIntExtra("subcategoryId",0),
                            data.getStringExtra("subcategoryTitle"));
                    break;
                case AddExpenseActivity.REQUEST_PLACE_PICKER:
                    Place place = PlacePicker.getPlace(context,data);
                    view.setChosenPlace(String.format("%s", place.getAddress()));
                    break;
            }

        }
    }

    @Override
    public void saveExpense(double cost, Category category, ExpenseInformation information) {
        createExpense(cost, category, information);
    }

    private void createExpense(double cost, Category category, ExpenseInformation information) {
         Expense expense  = new Expense(cost, category, information);
         expensesRepository.saveExpense(expense);
         view.showExpenses();
    }
}
