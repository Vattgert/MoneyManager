package com.example.productmanagment.adddebts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Debt;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Ivan on 13.03.2018.
 */

public class AddDebtPresenter implements AddDebtContract.Presenter {
    AddDebtContract.View view;
    ExpensesRepository repository;
    Context context;
    BaseSchedulerProvider provider;

    public AddDebtPresenter(AddDebtContract.View view, ExpensesRepository repository, Context context, BaseSchedulerProvider provider) {
        this.view = view;
        this.repository = repository;
        this.context = context;
        this.provider = provider;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadAccounts();
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadAccounts() {
        repository.getAccounts()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processAccounts);
    }

    @Override
    public void saveDebtExpense(Expense expense) {
        repository.saveExpense(expense);
    }

    @Override
    public void showEmptyDebtError() {

    }

    @Override
    public void showDebts() {
        view.showDebts();
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void saveDebt(Debt debt) {
        repository.saveDebt(debt);

        showDebts();
    }



    private void processAccounts(List<Account> accountList){
        view.showAccounts(accountList);
    }
}
