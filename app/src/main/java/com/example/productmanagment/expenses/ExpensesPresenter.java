package com.example.productmanagment.expenses;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.productmanagment.addexpenses.AddExpenseActivity;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 24.01.2018.
 */

public class ExpensesPresenter implements ExpensesContract.Presenter{
    private ExpensesRepository expensesRepository;
    private ExpensesContract.View view;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;

    public ExpensesPresenter(ExpensesRepository expensesRepository, ExpensesContract.View view, BaseSchedulerProvider schedulerProvider){
        this.view = view;
        this.expensesRepository = expensesRepository;
        this.schedulerProvider = schedulerProvider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }
    @Override
    public void subscribe() {
        loadAccounts();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void expenseLoading(String accountId) {
        if(accountId.equals("0"))
            loadExpenses(true);
        else
            loadExpensesByAccount(accountId);
    }

    @Override
    public void loadExpenses(boolean showLoadingUi) {

        Disposable disposable = expensesRepository.getExpenses()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processExpenses,
                        throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadExpensesByAccount(String accountId) {
        Disposable disposable = expensesRepository.getExpensesByAccount(accountId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processExpenses,
                        throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadAccounts() {
        Disposable disposable = expensesRepository.getAccountList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processAccounts,
                        throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void openExpenseDetails(@NonNull Expense requestedTask) {

    }

    @Override
    public void addNewExpense() {
        view.showAddExpense();
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (AddExpenseActivity.REQUEST_ADD_EXPENSE == requestCode && Activity.RESULT_OK == resultCode) {
            view.showExpenseSuccessfullySavedMessage();
        }
    }

    private void processExpenses(List<Expense> expenseList){
        view.showExpenses(expenseList);
    }

    private void processAccounts(List<Account> accounts){
        for (Account a: accounts) {
            Log.wtf("MyLog", a.getName());
        }
        view.showAccounts(accounts);
    }
}
