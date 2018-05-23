package com.example.productmanagment.places;

import android.util.Log;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.data.models.Place;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class PlacesPresenter implements PlacesContract.Presenter {
    private PlacesContract.View view;
    private ExpensesRepository repository;
    private BaseSchedulerProvider baseSchedulerProvider;
    private CompositeDisposable compositeDisposable;

    public PlacesPresenter(PlacesContract.View view, ExpensesRepository repository, BaseSchedulerProvider baseSchedulerProvider) {
        this.view = view;
        this.repository = repository;
        this.baseSchedulerProvider = baseSchedulerProvider;
        this.compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void loadAddresses() {
        Disposable disposable = repository.getExpenseAddresses()
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(this::processAddresses,
                        throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadAddressesByDate(String fdate, String sdate) {
        Disposable disposable = repository.getExpenseAddressesByDate(fdate, sdate)
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(this::processAddresses,
                        throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void subscribe() {
        loadAddresses();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private void processAddresses(List<Place> addresses){
        view.showAddresses(addresses);
    }
}
