package com.example.productmanagment.addtemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.productmanagment.addexpenses.AddExpenseActivity;
import com.example.productmanagment.categories.CategoryActivity;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.models.Template;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class AddTemplatePresenter implements AddTemplateContract.Presenter {
    private ExpensesRepository expensesRepository;
    private AddTemplateContract.View view;
    private Context context;
    private Category chosenCategory;
    private Place chosenPlace;
    private BaseSchedulerProvider provider;

    public AddTemplatePresenter(AddTemplateContract.View view, ExpensesRepository expensesRepository,
                                Context context, BaseSchedulerProvider provider) {
        this.expensesRepository = expensesRepository;
        this.view = view;
        this.context = context;
        this.provider = provider;
        this.view.setPresenter(this);
    }

    @Override
    public Category getChosenCategory() {
        if (chosenCategory != null)
            return chosenCategory;
        return null;
    }

    @Override
    public Place getChosenPlace() {
        if (chosenPlace != null)
            return chosenPlace;
        return null;
    }

    @Override
    public void choosePlace() {
        view.showChoosePlacePicker();
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if( resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case CategoryActivity.GET_CATEGORY_REQUEST:
                    view.setChosenCategory(data.getStringExtra("subcategoryTitle"));
                    chosenCategory = new Subcategory(data.getIntExtra("subcategoryId",0),
                            data.getStringExtra("subcategoryTitle"));
                    break;
                case AddExpenseActivity.REQUEST_PLACE_PICKER:
                    com.google.android.gms.location.places.Place place = PlacePicker.getPlace(context,data);
                    this.chosenPlace = place;
                    view.setChosenPlace(String.format("%s", chosenPlace.getAddress()));
                    view.setAddress(chosenPlace);
                    break;
            }
        }
    }

    @Override
    public void saveTemplate(Template template) {
        expensesRepository.saveTemplate(template);
    }

    @Override
    public void loadAccounts() {
        expensesRepository.getAccounts()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(view::showAccounts, throwable -> Log.wtf("MyLog", throwable.getMessage()));
    }

    @Override
    public void subscribe() {
        loadAccounts();
    }

    @Override
    public void unsubscribe() {

    }
}
