package com.example.productmanagment.purchaseslist;

import android.util.Log;

import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.PurchaseList;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class PurchaseListPresenter implements PurchaseListContract.Presenter{
    private PurchaseListContract.View view;
    private ExpensesRepository repository;
    private BaseSchedulerProvider provider;
    private CompositeDisposable compositeDisposable;

    public PurchaseListPresenter(PurchaseListContract.View view, ExpensesRepository repository, BaseSchedulerProvider provider) {
        this.view = view;
        this.repository = repository;
        this.provider = provider;
        this.compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadPurchasesList();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void loadPurchasesList() {
        Disposable disposable = repository.getPurchaseLists()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processPurchaseLists,
                        throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadPurchasesById(int purchaseListId) {

    }

    @Override
    public void deletePurchase(int purchaseId) {

    }

    @Override
    public void selectPurchases() {

    }

    @Override
    public void removePurchasesSelection() {

    }

    @Override
    public void deletePurchasesList() {

    }

    @Override
    public void sendPurchasesList() {

    }

    private void processPurchaseLists(List<PurchaseList> list){
        view.showPurchasesList(list);
    }
}
