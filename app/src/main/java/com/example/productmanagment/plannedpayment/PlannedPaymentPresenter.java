package com.example.productmanagment.plannedpayment;

import android.util.Log;

import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.PlannedPayment;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 25.02.2018.
 */

public class PlannedPaymentPresenter implements PlannedPaymentContract.Presenter {
    private ExpensesRepository expensesRepository;
    private CompositeDisposable compositeDisposable;
    private BaseSchedulerProvider baseSchedulerProvider;
    private PlannedPaymentContract.View view;

    public PlannedPaymentPresenter(ExpensesRepository expensesRepository, BaseSchedulerProvider baseSchedulerProvider, PlannedPaymentContract.View view) {
        this.expensesRepository = expensesRepository;
        this.baseSchedulerProvider = baseSchedulerProvider;
        this.compositeDisposable = new CompositeDisposable();
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadPlannedPayments();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void loadPlannedPayments() {
        Disposable disposable = expensesRepository.getPlannedPayments()
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(this::processPlannedPayment, throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void addPlannedPayments() {

    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    private void processPlannedPayment(List<PlannedPayment> plannedPaymentList){
        view.showPlannedPayments(plannedPaymentList);
    }
}
