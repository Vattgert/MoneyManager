package com.example.productmanagment.debtpayments;

import android.util.Log;

import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 11.03.2018.
 */

public class DebtPaymentsPresenter implements DebtPaymentsContract.Presenter {
    int debtId;
    DebtPaymentsContract.View view;
    ExpensesRepository repository;
    CompositeDisposable compositeDisposable;
    BaseSchedulerProvider provider;

    public DebtPaymentsPresenter(int debtId, DebtPaymentsContract.View view, ExpensesRepository repository, BaseSchedulerProvider provider) {
        this.view = view;
        this.debtId = debtId;
        this.repository = repository;
        this.provider = provider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);

    }

    @Override
    public void subscribe() {
        loadDebtPayments(this.debtId);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void loadDebtPayments(int debtId) {
        Disposable disposable = repository.getDebtPayments(debtId)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processDebtPayment, throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    private void processDebtPayment(List<Expense> debtPaymentsList){
        view.showDebtPayments(debtPaymentsList);
    }
}
