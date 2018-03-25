package com.example.productmanagment.adddebts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Debt;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        Expense debtExpense = new Expense();
        debtExpense.setDebtId(debt.getId());
        debtExpense.setCost(Double.valueOf(debt.getSum()));
        debtExpense.setCategory(new Subcategory(82, null));
        ExpenseInformation information = new ExpenseInformation();
        if(debt.getDebtType() == 1) {
            debtExpense.setExpenseType(2);
            information.setNote(context.getResources().getString(R.string.borrowed, debt.getBorrower()));
        }
        else {
            debtExpense.setExpenseType(1);
            information.setNote(context.getResources().getString(R.string.lent, debt.getBorrower()));
        }
        information.setDate(getCurrentDate());
        information.setReceiver(debt.getBorrower());
        debtExpense.setExpenseInformation(information);
        repository.saveExpense(debtExpense);
        showDebts();
    }

    private String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
