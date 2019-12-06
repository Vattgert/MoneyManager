package com.example.productmanagment.addexpenses;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.productmanagment.categories.CategoryActivity;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.remote.responses.SuccessResponse;
import com.example.productmanagment.data.source.users.UserSession;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Ivan on 30.01.2018.
 */

public class AddExpensePresenter implements AddExpenseContract.Presenter {
    private int groupId;
    private ExpensesRepository expensesRepository;
    private RemoteDataRepository remoteDataRepository;
    private AddExpenseContract.View view;
    private Context context;
    private Category chosenCategory;
    private Place chosenPlace;
    private BaseSchedulerProvider provider;
    private UserSession userSession;

    public AddExpensePresenter(int groupId, ExpensesRepository expensesRepository, AddExpenseContract.View view,
                               Context context, BaseSchedulerProvider provider) {
        this.groupId = groupId;
        this.expensesRepository = expensesRepository;
        this.view = view;
        this.view.setPresenter(this);
        this.context = context;
        this.provider = provider;
        this.remoteDataRepository = new RemoteDataRepository();
        this.userSession = new UserSession(context);
    }

    @Override
    public void subscribe() {
        if (groupId == -1)
            loadAccounts();
        else
            remoteDataRepository.getSubcategories()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(subcategoryResponse -> {
                    if(subcategoryResponse.subcategoriesList.size() > 0){
                        view.setCategories(subcategoryResponse.subcategoriesList);
                    }
                });
            remoteDataRepository.getAccounts(String.valueOf(groupId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(accountResponse -> this.processAccounts(accountResponse.accounts));
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
                    Place place = PlacePicker.getPlace(context,data);
                    this.chosenPlace = place;
                    view.setChosenPlace(String.format("%s", chosenPlace.getAddress()));
                    view.setAddress(chosenPlace);
                    break;
            }
        }
    }

    @Override
    public void saveExpense(Expense expense) {
        expense.setUser(userSession.getUserDetails());
        if(groupId == -1) {
            expensesRepository.saveExpense(expense);
        }
        else
            remoteDataRepository.createTransaction(expense)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(expensesResponse -> {
                    if(expensesResponse.getSuccess().equals("0")){
                        view.showMessage("Транзакція створена");
                    }
                    else{
                        view.showMessage("Виникла помилка створення запису");
                    }
                });

    }

    @Override
    public void loadAccounts() {
        expensesRepository.getAccounts()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processAccounts);
    }

    @Override
    public User getCurrentUser() {
        return userSession.getUserDetails();
    }

    private void processAccounts(List<Account> accountList){
        view.showAccounts(accountList);
    }

    private void processSuccessResponse(SuccessResponse response){
        if(response.response.equals("success"))
            view.showMessage(response.data);
        else
            view.showMessage(response.errorData);
        view.showExpenses();
    }
}
