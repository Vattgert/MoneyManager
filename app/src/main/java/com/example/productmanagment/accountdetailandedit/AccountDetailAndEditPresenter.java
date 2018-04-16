package com.example.productmanagment.accountdetailandedit;

import android.support.annotation.ColorInt;
import android.util.Log;
import android.widget.Toast;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.thebluealliance.spectrum.SpectrumDialog;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AccountDetailAndEditPresenter implements AccountDetailAndEditContract.Presenter {
    String accountId;
    AccountDetailAndEditContract.View view;
    ExpensesRepository repository;
    BaseSchedulerProvider baseSchedulerProvider;
    CompositeDisposable compositeDisposable;

    public AccountDetailAndEditPresenter(String accountId, AccountDetailAndEditContract.View view, ExpensesRepository repository, BaseSchedulerProvider baseSchedulerProvider) {
        this.accountId = accountId;
        this.view = view;
        this.repository = repository;
        this.baseSchedulerProvider = baseSchedulerProvider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void openAccount(String accountId) {

    }

    @Override
    public void updateAccount(Account account) {
        repository.updateAccount(this.accountId, account);
    }

    @Override
    public void deleteAccount() {
        repository.deleteAccount(this.accountId);
    }

    @Override
    public void openColorPick() {
        view.showColorPick();
    }

    @Override
    public void subscribe() {
        Disposable disposable = repository.getAccountById(this.accountId)
                .subscribeOn(baseSchedulerProvider.computation())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(this::showAccount, throwable -> {
                    Log.wtf("tag", throwable.getMessage()); });
        compositeDisposable.add(disposable);
    }

    @Override
    public void unsubscribe() {

    }

    private void showAccount(Account account){
        view.setAccountTitle(account.getName());
        view.setAccountCurrency(account.getCurrency());
        view.setAccountColor(account.getColor());
    }
}
