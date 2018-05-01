package com.example.productmanagment.goalEdit;

import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
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

    public GoalEditPresenter(String id, GoalEditContract.View view, ExpensesRepository repository, BaseSchedulerProvider provider) {
        this.id = id;
        this.view = view;
        this.repository = repository;
        this.provider = provider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void openGoal() {
        Disposable disposable = repository.getGoalById(this.id)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processGoal);
        compositeDisposable.add(disposable);
    }

    @Override
    public void deleteGoal() {
        repository.deleteGoal(this.id);
        view.showGoalDeleted();
        view.finishActivity();
    }

    @Override
    public void updateGoal(Goal goal) {
        repository.editGoal(this.id, goal);
        view.showGoalUpdated();
        view.finishActivity();
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
        view.setGoalAccumulatedSum(goal.getAccumulatedAmount());
        view.setGoalWantedDate(goal.getWantedDate());
        view.setGoalNote(goal.getNote());
    }
}
