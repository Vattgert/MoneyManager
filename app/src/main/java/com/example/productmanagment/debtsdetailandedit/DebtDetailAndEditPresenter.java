package com.example.productmanagment.debtsdetailandedit;

import android.util.Log;

import com.example.productmanagment.data.models.Debt;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 25.03.2018.
 */

public class DebtDetailAndEditPresenter implements DebtDetailAndEditContract.Presenter {
    int debtId;
    DebtDetailAndEditContract.View view;
    ExpensesRepository repository;
    CompositeDisposable compositeDisposable;
    BaseSchedulerProvider provider;

    public DebtDetailAndEditPresenter(int debtId, DebtDetailAndEditContract.View view, ExpensesRepository repository, BaseSchedulerProvider provider) {
        this.debtId = debtId;
        this.view = view;
        this.repository = repository;
        this.compositeDisposable = new CompositeDisposable();
        this.provider = provider;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        openDebt(this.debtId);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void openDebt(int debtId) {
        if(debtId == -1){
            return;
        }
        Disposable disposable = repository.getDebtById(debtId)
                .subscribeOn(provider.computation())
                .observeOn(provider.ui())
                .subscribe(this::processDebtDetail, throwable -> {
                    Log.wtf("tag", throwable.getMessage()); });
        compositeDisposable.add(disposable);
    }

    @Override
    public void editDebt(Debt debt) {
        debt.setId(this.debtId);
        repository.editDebt(debt);
        view.finish();
    }

    @Override
    public void deleteDebt() {
        deleteDebt(this.debtId);
    }

    private void processDebtDetail(Debt debt){
        view.showCost(Double.valueOf(debt.getSum()));
        view.showDebtType(debt.getDebtType());
        view.showReceiver(debt.getBorrower());
        view.showDescription(debt.getDescription());
        view.showBorrowDate(debt.getBorrowDate());
        view.showRepayDate(debt.getRepayDate());
    }

    private void deleteDebt(int debtId){
        repository.deleteDebt(debtId);
        view.finish();
    }
}
