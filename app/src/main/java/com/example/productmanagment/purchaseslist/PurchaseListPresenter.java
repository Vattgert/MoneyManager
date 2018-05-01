package com.example.productmanagment.purchaseslist;

import android.util.Log;

import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Purchase;
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
        Disposable disposable = repository.getPurchasesByList(String.valueOf(purchaseListId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processPurchases);
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadAccounts() {
        Disposable disposable = repository.getAccounts()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(view::showAccountList, throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
    }

    @Override
    public void deletePurchase(int purchaseId) {
        repository.deletePurchase(purchaseId);
    }

    @Override
    public void selectPurchases() {
        view.showAllPurchasesSelection();
    }

    @Override
    public void createPurchase(Purchase purchase) {
        repository.createPurchase(purchase);
    }

    @Override
    public void removePurchasesSelection() {
        view.showAllPurchasesDeselection();
    }

    @Override
    public void deletePurchasesList(int purchaseListId) {
        repository.deletePurchaseList(purchaseListId);
    }

    @Override
    public void sendPurchasesList(String text) {
        view.showSendDialog(text);
    }

    @Override
    public void openPurchaseRecordDialog() {
        loadAccounts();
        view.showAddExpenseRecord();
    }

    @Override
    public void openAddPurchaseListDialog() {
        view.showAddPurchaseList();
    }

    @Override
    public void openRenamePurchaseListDialog() {
        view.showRenamePurchaseList();
    }

    @Override
    public void renamePurchaseList(int purchaseId, String rename) {
        repository.renamePurchaseList(purchaseId, rename);
    }

    @Override
    public void createPurchaseList(String name) {
        repository.createPurchaseList(name);
    }

    @Override
    public void createExpenseRecord(Expense expense) {
        repository.saveExpense(expense);
    }

    private void processPurchaseLists(List<PurchaseList> list){
        view.showPurchasesList(list);
        for(PurchaseList p : list)
            Log.wtf("PurchaseLog", p.getTitle());
    }

    private void processPurchases(List<Purchase> list){
        view.showPurchases(list);
    }
}
