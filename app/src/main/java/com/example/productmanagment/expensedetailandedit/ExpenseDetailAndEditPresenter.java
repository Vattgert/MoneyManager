package com.example.productmanagment.expensedetailandedit;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.example.productmanagment.utils.schedulers.SchedulerProvider;

import java.util.Optional;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 12.02.2018.
 */

public class ExpenseDetailAndEditPresenter implements ExpenseDetailAndEditContract.Presenter {
    String expenseId;
    ExpenseDetailAndEditContract.View view;
    ExpensesRepository repository;
    CompositeDisposable compositeDisposable;
    BaseSchedulerProvider provider;

    public ExpenseDetailAndEditPresenter(String expenseId, ExpenseDetailAndEditContract.View view,
                                         ExpensesRepository repository,
                                         BaseSchedulerProvider provider) {
        this.expenseId = expenseId;
        this.view = view;
        this.repository = repository;
        this.compositeDisposable = new CompositeDisposable();
        this.provider = provider;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        openExpense();
    }

    private void openExpense(){
        if(expenseId == null || expenseId.equals("")){
            view.showNoExpense();
            return;
        }
        Disposable disposable = repository.getExpenseById(expenseId)
                .subscribeOn(provider.computation())
                .observeOn(provider.ui())
                .subscribe(this::showExpense, throwable -> {Log.wtf("tag", throwable.getMessage()); });
        compositeDisposable.add(disposable);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }


    @Override
    public void editExpense(Expense expense) {
        repository.updateExpense(expenseId, expense);
        view.finish();
    }

    @Override
    public void deleteTask() {
        repository.deleteExpense(expenseId);
        view.finish();
    }

    private void showExpense(@NonNull Expense expense){
        Log.wtf("sqlite", "lol");
        view.showCost(expense.getCost());
        view.showNote(expense.getNote());
        view.showReceiver(expense.getReceiver());
        view.showDate(expense.getDate());
        view.showTime(expense.getTime());
        view.showCategory(expense.getCategory().getName());
    }
}
