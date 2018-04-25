package com.example.productmanagment.goals;

import android.util.Log;

import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

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
                .subscribe(this::processGoals, throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void goalsLoading(int state) {
        loadGoals(state);
    }



    @Override
    public void openAddGoal() {
        view.showAddGoal();
    }

    @Override
    public void openDetailGoal(String goalId) {
        view.showDetailGoal(goalId);
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

    private void processGoals(List<Goal> goalList){
        view.showGoals(goalList);
    }
}
