package com.example.productmanagment.accountdetailandedit;

import android.support.annotation.ColorInt;
import android.util.Log;
import android.widget.Toast;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.MyCurrency;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.remote.responses.SuccessResponse;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AccountDetailAndEditPresenter implements AccountDetailAndEditContract.Presenter {
    String accountId;
    int groupId;
    AccountDetailAndEditContract.View view;
    ExpensesRepository repository;
    RemoteDataRepository remoteDataRepository;
    BaseSchedulerProvider baseSchedulerProvider;
    CompositeDisposable compositeDisposable;

    public AccountDetailAndEditPresenter(String accountId, int groupId, AccountDetailAndEditContract.View view, ExpensesRepository repository, BaseSchedulerProvider baseSchedulerProvider) {
        this.accountId = accountId;
        this.groupId = groupId;
        this.view = view;
        this.repository = repository;
        this.remoteDataRepository = new RemoteDataRepository();
        this.baseSchedulerProvider = baseSchedulerProvider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void openAccount(String accountId) {
        Disposable disposable = repository.getAccountById(accountId)
                .subscribeOn(baseSchedulerProvider.computation())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(this::showAccount, throwable -> {
                    Log.wtf("tag", throwable.getMessage()); });
        compositeDisposable.add(disposable);
    }

    @Override
    public void openRemoteAccount(String accountId) {
        Disposable disposable = remoteDataRepository.getAccountById(accountId)
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(this::showAccount, throwable -> {
                    Log.wtf("MyLog", throwable.getMessage()); });
        compositeDisposable.add(disposable);
    }

    @Override
    public void updateAccount(Account account) {
        if(groupId == -1)
            repository.updateAccount(this.accountId, account);
        else {
            account.setId(Integer.valueOf(this.accountId));
            updateRemoteAccount(account);
        }
    }

    @Override
    public void updateRemoteAccount(Account account) {
        remoteDataRepository.updateAccount(account)
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(this::processSuccessResponse, throwable -> Log.wtf("MyLog", throwable.getMessage()));
    }

    @Override
    public void deleteAccount() {
        if(groupId == -1)
            repository.deleteAccount(this.accountId);
        else
            deleteRemoteAccount();
    }

    @Override
    public void deleteRemoteAccount() {
        remoteDataRepository.deleteAccount(this.accountId)
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(this::processSuccessResponse);
    }

    @Override
    public void openColorPick() {
        view.showColorPick();
    }

    @Override
    public void subscribe() {
        if(groupId == -1)
            openAccount(this.accountId);
        else
            openRemoteAccount(this.accountId);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private void showAccount(Account account){
        view.setAccountTitle(account.getName());
//        view.setAccountCurrency(account.getCurrency());
        view.setAccountColor(account.getColor());
    }

    private void processSuccessResponse(SuccessResponse successResponse){
        if(successResponse.response.equals("success")){
            view.showMessage(successResponse.data);
            view.closeView();
        }
        else{
            view.showMessage(successResponse.errorData);
        }
    }
}
