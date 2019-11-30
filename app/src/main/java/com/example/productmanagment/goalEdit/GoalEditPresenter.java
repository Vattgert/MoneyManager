package com.example.productmanagment.goalEdit;

import android.util.Log;

import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GoalEditPresenter implements GoalEditContract.Presenter {
    String id;
    Goal goal;
    GoalEditContract.View view;
    ExpensesRepository repository;
    BaseSchedulerProvider provider;
    CompositeDisposable compositeDisposable;
    RemoteDataRepository remoteDataRepository;
    int householdId;

    public GoalEditPresenter(int householdId, String id, GoalEditContract.View view, ExpensesRepository repository, RemoteDataRepository remoteDataRepository, BaseSchedulerProvider provider) {
        this.householdId = householdId;
        this.remoteDataRepository = remoteDataRepository;
        this.id = id;
        this.view = view;
        this.repository = repository;
        this.provider = provider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void openGoal() {
        if(householdId == -1) {
            Disposable disposable = repository.getGoalById(this.id)
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
                    .subscribe(this::processGoal);
            compositeDisposable.add(disposable);
        }
        else{
            Disposable disposable = remoteDataRepository.getGoalById(this.id)
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
                    .subscribe(this::processGoal);
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void deleteGoal() {
        if (householdId == -1) {
            repository.deleteGoal(this.id);
            view.showGoalDeleted();
            view.finishActivity();
        }
        else{
            Disposable disposable = remoteDataRepository.deleteGoal(this.id)
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
                    .subscribe(goalResponse -> {
                        if(goalResponse.getSuccess().equals("0")){
                            view.showGoalDeleted();
                            view.finishActivity();
                        }
                    }, throwable -> {
                        Log.wtf("Error", throwable.getMessage());});
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void updateGoal(Goal goal) {
        if(householdId == -1) {
            repository.editGoal(this.id, goal);
            view.showGoalUpdated();
            view.finishActivity();
        }
        else{
            Disposable disposable = remoteDataRepository.updateGoal(goal, this.id)
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
                    .subscribe(goalResponse -> {
                        if(goalResponse.getSuccess().equals("0")){
                            view.showGoalUpdated();
                            view.finishActivity();
                        }
                    });
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void pauseGoal() {
        repository.makeGoalPaused(this.id);
        view.showGoalPaused();
        view.finishActivity();
    }

    @Override
    public void activateGoal() {
        repository.makeGoalActive(this.id);
        view.showGoalActivated();
        view.finishActivity();
    }

    @Override
    public int getGoalState() {
        return this.goal.getState();
    }

    @Override
    public void subscribe() {
        openGoal();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private void processGoal(Goal goal){
        this.goal = goal;
        view.setGoalTitle(goal.getTitle());
        view.setGoalNeededSum(goal.getNeededAmount());
        view.setGoalAccumulatedSum(goal.getGoalStartAmount());
        view.setGoalWantedDate(goal.getWantedDate());
        view.setGoalNote(goal.getNote());
    }
}
