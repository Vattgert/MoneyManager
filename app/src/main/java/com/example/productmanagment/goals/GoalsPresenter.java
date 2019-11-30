package com.example.productmanagment.goals;

import android.util.Log;

import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GoalsPresenter implements GoalsContract.Presenter {
    int householdId;
    GoalsContract.View view;
    ExpensesRepository repository;
    RemoteDataRepository remoteDataRepository;
    BaseSchedulerProvider baseSchedulerProvider;
    CompositeDisposable compositeDisposable;

    public GoalsPresenter(int householdId, GoalsContract.View view, ExpensesRepository repository, RemoteDataRepository remoteDataRepository, BaseSchedulerProvider baseSchedulerProvider) {
        this.householdId = householdId;
        this.view = view;
        this.repository = repository;
        this.remoteDataRepository = remoteDataRepository;
        this.baseSchedulerProvider = baseSchedulerProvider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void loadGoals(int state) {
        if(householdId == -1) {
            Disposable disposable = repository.getGoals(state)
                    .subscribeOn(baseSchedulerProvider.io())
                    .observeOn(baseSchedulerProvider.ui())
                    .subscribe(this::processGoals, throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
            compositeDisposable.add(disposable);
        }
        else{
            Disposable disposable = remoteDataRepository.getGoals(String.valueOf(householdId))
                    .subscribeOn(baseSchedulerProvider.io())
                    .observeOn(baseSchedulerProvider.ui())
                    .subscribe(goalResponse -> this.processGoals(goalResponse.getGoalList()), throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void goalsLoading(int state) {
        loadGoals(state);
    }

    @Override
    public void openAddGoal() {
        if(householdId == -1)
            view.showAddGoal();
        else
            view.showAddGoalRemote(String.valueOf(householdId));
    }

    @Override
    public void openDetailGoal(String goalId) {
        if(householdId == -1)
            view.showDetailGoal(goalId);
        else
            view.showDetailGoalRemote(goalId, String.valueOf(householdId));
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
