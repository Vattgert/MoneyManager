package com.example.productmanagment.expensedetailandedit;

import android.support.annotation.NonNull;

import com.example.productmanagment.data.models.Expense;
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
        Disposable disposable = repository.getExpense(expenseId)
                .subscribeOn(provider.computation())
                .observeOn(provider.ui())
                .subscribe(this::showExpense, throwable -> {});
        compositeDisposable.add(disposable);
    }

    @Override
    public void unsubscribe() {

    }


    @Override
    public void editExpense(double cost, String note, String marks, String receiver, String date, String time, String typeOfPayment, String place, String addition, String category) {
        Expense expense = new Expense(cost, note, marks, receiver, date, time, typeOfPayment, place, addition, category);
        repository.updateExpense(expenseId, expense);
        view.finish();
    }

    @Override
    public void deleteTask() {
        repository.deleteExpense(expenseId);
        view.finish();
    }

    private void showExpense(@NonNull Expense expense){
        view.showCost(expense.getCost());
        view.showNote(expense.getNote());
        view.showReceiver(expense.getReceiver());
        view.showDate(expense.getDate());
        view.showTime(expense.getTime());
        view.showCategory(expense.getCategory());
    }
}
