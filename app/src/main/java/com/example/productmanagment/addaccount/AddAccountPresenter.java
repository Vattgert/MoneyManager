package com.example.productmanagment.addaccount;

import android.util.Log;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.MyCurrency;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class AddAccountPresenter implements AddAccountContract.Presenter {

    private AddAccountContract.View view;
    private ExpensesRepository repository;
    private BaseSchedulerProvider provider;

    public AddAccountPresenter(AddAccountContract.View view, ExpensesRepository repository,
                               BaseSchedulerProvider provider) {
        this.view = view;
        this.repository = repository;
        this.provider = provider;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        uploadCurrencies();
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void createAccount(Account account) {
        repository.saveAccount(account);
    }

    @Override
    public void openColorPickDialog() {
        view.showColorPickDialog();
    }

    @Override
    public void uploadCurrencies() {
        Disposable disposable = repository.getCurrencies()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processCurrencies);
    }

    private void processCurrencies(List<MyCurrency> currencyList){
        for (MyCurrency c : currencyList){
            Log.wtf("MyLog", c.getTitle());
        }
        view.setCurrenciesToSpinner(currencyList);

    }

}
