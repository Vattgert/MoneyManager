package com.example.productmanagment.expenses;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.productmanagment.addexpenses.AddExpenseActivity;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.remote.responses.AccountResponse;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 24.01.2018.
 */

public class ExpensesPresenter implements ExpensesContract.Presenter{
    private int groupId;
    private ExpensesRepository expensesRepository;
    private RemoteDataRepository remoteDataRepository;
    private ExpensesContract.View view;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;

    public ExpensesPresenter(int groupId, ExpensesRepository expensesRepository, ExpensesContract.View view,
                             BaseSchedulerProvider schedulerProvider){
        this.view = view;
        this.groupId = groupId;
        this.expensesRepository = expensesRepository;
        this.remoteDataRepository = new RemoteDataRepository();
        this.schedulerProvider = schedulerProvider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }
    @Override
    public void subscribe() {
        if(groupId == -1)
            loadAccounts();
        else
            loadRemoteAccounts(String.valueOf(this.groupId));
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void expenseLoading(String accountId) {
        if(groupId == -1) {
            if (accountId.equals("0"))
                loadExpenses(true);
            else
                loadExpensesByAccount(accountId);
        }
        else{
            loadRemoteExpenses(accountId);
        }
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
    public void loadRemoteExpenses(String accountId) {
        Disposable disposable = remoteDataRepository.getExpensesByAccount(accountId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(expensesResponse -> this.processExpenses(expensesResponse.expenses), throwable -> Log.wtf("expenseLog", throwable.getMessage()));
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
    public void loadRemoteAccounts(String groupId) {
        Disposable disposable = remoteDataRepository.getAccountsByGroup(groupId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(accountResponse -> this.processAccounts(accountResponse.accounts));
        compositeDisposable.add(disposable);
    }

    @Override
    public void openExpenseDetails(@NonNull Expense expense) {
        view.showExpenseDetail(expense.getId(), this.groupId);
    }

    @Override
    public void addNewExpense() {
        view.showAddExpense(groupId);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (AddExpenseActivity.REQUEST_ADD_EXPENSE == requestCode
                && Activity.RESULT_OK == resultCode) {
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
