package com.example.productmanagment.currencydetailandedit;

import android.util.Log;

import com.example.productmanagment.data.models.MyCurrency;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.Disposable;

public class CurrencyDetailAndEditPresenter implements CurrencyDetailAndEditContract.Presenter{
    int groupId;
    String currencyId;
    CurrencyDetailAndEditContract.View view;
    ExpensesRepository repository;
    RemoteDataRepository remoteDataRepository;
    BaseSchedulerProvider provider;
    MyCurrency currency;

    public CurrencyDetailAndEditPresenter(int groupId, String currencyId,
                                          CurrencyDetailAndEditContract.View view,
                                          ExpensesRepository repository,
                                          BaseSchedulerProvider provider) {
        this.groupId = groupId;
        this.currencyId = currencyId;
        this.view = view;
        this.repository = repository;
        this.remoteDataRepository = new RemoteDataRepository();
        this.provider = provider;
        this.view.setPresenter(this);
    }

    @Override
    public void loadCurrencyById(String currencyId) {
        Disposable disposable = repository.getCurrencyById(currencyId)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processCurrency);
    }

    @Override
    public void deleteCurrency() {
        if(currency.getIsBase() == 0)
            if(groupId == -1)
                repository.deleteCurrency(this.currencyId);
            else
                remoteDataRepository.deleteCurrency(this.currencyId);
        else
            view.showMessage("Базову валюту неможливо видалити");
    }

    @Override
    public void updateCurrency(MyCurrency myCurrency) {
        if(currency.getIsBase() == 0) {
            if (groupId == -1)
                repository.updateCurrency(this.currencyId, myCurrency);
            else {
                myCurrency.setId(this.currency.getId());
                remoteDataRepository.updateCurrency(myCurrency).subscribeOn(provider.io()).observeOn(provider.ui()).subscribe();
            }
        }
        else
            view.showMessage("Базову валюту неможливо змінити");

    }

    @Override
    public void subscribe() {
        if(groupId == -1)
            loadCurrencyById(this.currencyId);
        else
            remoteDataRepository.getCurrencyById(this.currencyId)
            .subscribeOn(provider.io())
            .observeOn(provider.ui())
            .subscribe(this::processCurrency);
    }

    @Override
    public void unsubscribe() {

    }

    private void processCurrency(MyCurrency currency){
        this.currency = currency;
        Log.wtf("CurrencyLog", currency.toString());
        view.setCurrencyTitle(currency.getTitle());
        view.setCurrencyRate(String.valueOf(currency.getRateToBaseCurrency()));
        view.setCurrencyReverseRate(String.valueOf(currency.getRateBaseToThis()));
        view.setCurrencyCode(currency.getCode());
        view.setCurrencySymbol(currency.getSymbol());
    }
}
