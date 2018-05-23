package com.example.productmanagment.main;

import android.graphics.Color;
import android.util.Log;

import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MainPresenter implements MainContract.Presenter{
    private MainContract.View view;
    private ExpensesRepository repository;
    private BaseSchedulerProvider provider;

    public MainPresenter(MainContract.View view, ExpensesRepository repository,
                         BaseSchedulerProvider provider) {
        this.view = view;
        this.repository = repository;
        this.provider = provider;
        this.view.setPresenter(this);
    }

    @Override
    public void loadBalance() {
        repository.getBalance()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processBalance, throwable -> Log.wtf("MainLog", throwable.getMessage()));
    }

    @Override
    public void loadAccountData() {

    }

    @Override
    public void loadLastRecords() {
        repository.getLastFiveRecords()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(expenses -> view.setLastRecords(expenses),
                        throwable -> Log.wtf("MainLog", throwable.getMessage()));
    }

    @Override
    public void loadLastDataDiagram() {

    }

    @Override
    public void subscribe() {
        loadBalance();
        loadLastRecords();
    }

    @Override
    public void unsubscribe() {

    }

    private void processBalance(BigDecimal balance){
        int color = -1;
        if(balance.doubleValue() < 0)
            color = Color.RED;
        else if(balance.doubleValue() >= 0)
            color = Color.GREEN;
        view.setBalance(new DecimalFormat("#0.00").format(balance).concat("₴"), color);

    }
}
