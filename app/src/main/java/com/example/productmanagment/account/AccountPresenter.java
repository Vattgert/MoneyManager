package com.example.productmanagment.account;

import android.util.Log;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 27.03.2018.
 */

public class AccountPresenter implements AccountContract.Presenter {
    private int groupId;
    private AccountContract.View view;
    private ExpensesRepository repository;
    private RemoteDataRepository remoteDataRepository;
    private CompositeDisposable compositeDisposable;
    private BaseSchedulerProvider baseSchedulerProvider;

    public AccountPresenter(int groupId, AccountContract.View view, ExpensesRepository repository,
                            BaseSchedulerProvider baseSchedulerProvider) {
        this.view = view;
        this.groupId = groupId;
        this.repository = repository;
        this.baseSchedulerProvider = baseSchedulerProvider;
        this.remoteDataRepository = new RemoteDataRepository();
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void loadAccount() {
        Disposable disposable = repository.getAccounts()
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(this::processAccounts,
                        throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadAccountsByGroup(String groupId) {
        Disposable disposable = remoteDataRepository.getAccountsByGroup(groupId)
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(accountResponse -> processAccounts(accountResponse.accounts),
                        throwable -> Log.wtf("MyLog", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void openAddAccount() {
        view.showAddAccount(groupId);
    }

    @Override
    public void openAccountDetailAndEdit(Account account) {
        view.showAccountDetailAndEdit(String.valueOf(account.getId()), groupId);
    }

    @Override
    public void subscribe() {
        if(groupId == -1)
            loadAccount();
        else{
            loadAccountsByGroup(String.valueOf(groupId));
        }
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private void processAccounts(List<Account> accountList){
        for(Account a : accountList){
            Log.wtf("AccountsLog", a.getName());
            Log.wtf("AccountsLog", a.getColor());
        }
        view.showAccounts(accountList);
    }

}
