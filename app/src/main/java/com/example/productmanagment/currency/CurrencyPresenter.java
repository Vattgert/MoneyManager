package com.example.productmanagment.currency;

import android.util.Log;

import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CurrencyPresenter implements CurrencyContract.Presenter{
    int groupId;
    CurrencyContract.View view;
    ExpensesRepository repository;
    RemoteDataRepository remoteDataRepository;
    BaseSchedulerProvider provider;
    CompositeDisposable compositeDisposable;

    public CurrencyPresenter(int groupId, CurrencyContract.View view, ExpensesRepository repository,
                             BaseSchedulerProvider provider) {
        this.groupId = groupId;
        this.view = view;
        this.repository = repository;
        this.remoteDataRepository = new RemoteDataRepository();
        this.provider = provider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void loadCurrencies() {
        Disposable disposable = repository.getCurrencies()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(view::showCurrencies);
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadRemoteCurrencies(int groupId) {
        Disposable disposable = remoteDataRepository.getCurrenciesRemote(String.valueOf(groupId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(currencyResponse -> view.showCurrencies(currencyResponse.getCurrencyList()), throwable -> {Log.wtf("MyLog", throwable.getCause());});
        compositeDisposable.add(disposable);
    }

    @Override
    public void openAddCurrency() {
        view.showAddCurrency(this.groupId);
    }

    @Override
    public void openDetailAndEditCurrency(String currencyId) {
        view.showDetailAndEditCurrency(this.groupId, currencyId);
    }

    @Override
    public void subscribe() {
        Log.wtf("CurrencyLog", groupId + "");
        if(groupId == -1)
            loadCurrencies();
        else
            loadRemoteCurrencies(this.groupId);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
