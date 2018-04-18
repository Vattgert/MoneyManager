package com.example.productmanagment.goals;

import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GoalsPresenter implements GoalsContract.Presenter {
    GoalsContract.View view;
    ExpensesRepository repository;
    BaseSchedulerProvider baseSchedulerProvider;
    CompositeDisposable compositeDisposable;

    public GoalsPresenter(GoalsContract.View view, ExpensesRepository repository, BaseSchedulerProvider baseSchedulerProvider) {
        this.view = view;
        this.repository = repository;
        this.baseSchedulerProvider = baseSchedulerProvider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void loadGoals(int state) {
        Disposable disposable = repository.getGoals(state)
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadGoalStates() {

    }

    @Override
    public void openAddGoal() {

    }

    @Override
    public void openDetailGoal() {

    }

    @Override
    public void openEditGoal() {

    }

    @Override
    public void subscribe() {
        loadGoals(1);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
