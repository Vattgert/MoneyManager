package com.example.productmanagment.adddebts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.productmanagment.data.models.Debt;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;

/**
 * Created by Ivan on 13.03.2018.
 */

public class AddDebtPresenter implements AddDebtContract.Presenter {
    AddDebtContract.View view;
    ExpensesRepository repository;
    Context context;

    public AddDebtPresenter(AddDebtContract.View view, ExpensesRepository repository, Context context) {
        this.view = view;
        this.repository = repository;
        this.context = context;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

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
}
