package com.example.productmanagment.currency;

import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CurrencyPresenter implements CurrencyContract.Presenter{
    CurrencyContract.View view;
    ExpensesRepository repository;
    BaseSchedulerProvider provider;
    CompositeDisposable compositeDisposable;

    public CurrencyPresenter(CurrencyContract.View view, ExpensesRepository repository, BaseSchedulerProvider provider) {
        this.view = view;
        this.repository = repository;
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
    public void openAddCurrency() {
        view.showAddCurrency();
    }

    @Override
    public void openDetailAndEditCurrency(String currencyId) {
        view.showDetailAndEditCurrency(currencyId);
    }

    @Override
    public void subscribe() {
        loadCurrencies();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
