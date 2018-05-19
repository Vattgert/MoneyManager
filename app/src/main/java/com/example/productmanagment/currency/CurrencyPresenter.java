package com.example.productmanagment.currency;

import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CurrencyPresenter implements CurrencyContract.Presenter{
    int groupId = -1;
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
        Disposable disposable = remoteDataRepository.getCurrencies(String.valueOf(groupId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(currencyResponse -> view.showCurrencies(currencyResponse.currencyList));
        compositeDisposable.add(disposable);
    }

    @Override
    public void openAddCurrency() {
        view.showAddCurrency();
    }

    @Override
    public void openDetailAndEditCurrency(String currencyId) {
        view.showDetailAndEditCurrency(this.groupId, currencyId);
    }

    @Override
    public void subscribe() {
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
