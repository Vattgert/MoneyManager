package com.example.productmanagment.addgoal;

import android.util.Log;

import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AddGoalPresenter implements AddGoalContract.Presenter{
    int householdId;
    AddGoalContract.View view;
    ExpensesRepository repository;
    RemoteDataRepository remoteDataRepository;
    BaseSchedulerProvider baseSchedulerProvider;
    CompositeDisposable compositeDisposable;

    public AddGoalPresenter(int householdId, AddGoalContract.View view, ExpensesRepository repository,
                            RemoteDataRepository remoteDataRepository, BaseSchedulerProvider baseSchedulerProvider) {
        this.householdId = householdId;
        this.view = view;
        this.repository = repository;
        this.remoteDataRepository = remoteDataRepository;
        this.baseSchedulerProvider = baseSchedulerProvider;
        this.compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void createGoal(Goal goal) {
        if(householdId == -1) {
            repository.saveGoal(goal);
            view.showGoalCreatedMessage();
        }
        else{
            Disposable disposable = remoteDataRepository.createGoal(goal, String.valueOf(householdId))
                    .subscribeOn(baseSchedulerProvider.io())
                    .observeOn(baseSchedulerProvider.ui())
                    .subscribe(goalResponse -> {
                        if(goalResponse.getSuccess().equals("0"))
                            view.showGoalCreatedMessage();
                    }, throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void openDatePick() {
        view.showDatePick();
    }

    @Override
    public void subscribe() {
        view.setAccumulatedAmountDefaultValue();
        view.setNeededAmountDefaultValue();
    }

    @Override
    public void unsubscribe() {

    }
}
