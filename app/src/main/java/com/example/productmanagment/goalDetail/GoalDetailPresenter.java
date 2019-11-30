package com.example.productmanagment.goalDetail;

import android.util.Log;

import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import org.joda.time.LocalDate;
import org.joda.time.Months;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GoalDetailPresenter implements GoalDetailContract.Presenter {
    int householdId;
    String goalId;
    Goal goal;
    GoalDetailContract.View view;
    ExpensesRepository repository;
    BaseSchedulerProvider provider;
    CompositeDisposable compositeDisposable;
    RemoteDataRepository remoteDataRepository;

    public GoalDetailPresenter(int householdId, String goalId, GoalDetailContract.View view, ExpensesRepository repository, RemoteDataRepository remoteDataRepository, BaseSchedulerProvider provider) {
        this.householdId = householdId;
        this.goalId = goalId;
        this.view = view;
        this.repository = repository;
        this.provider = provider;
        compositeDisposable = new CompositeDisposable();
        this.remoteDataRepository = remoteDataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void openGoal(String goalId) {
        if(householdId == -1) {
            Disposable disposable = repository.getGoalById(goalId)
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
                    .subscribe(this::processGoal);
            compositeDisposable.add(disposable);
        }
        else{
            Disposable disposable = remoteDataRepository.getGoalById(goalId)
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
                    .subscribe(this::processGoal);
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void openGoalEdit() {
        if(householdId == -1)
            view.showGoalEdit(this.goalId);
        else
            view.showGoalRemoteEdit(goalId, String.valueOf(householdId));
    }

    @Override
    public void makeGoalReached() {
        repository.makeGoalAchieved(Integer.valueOf(goalId));
        view.showGoalReached();
    }

    @Override
    public void openAddAmount() {
        if (goal.getAccumulatedAmount() == goal.getNeededAmount())
            view.showFullAccumulatedAmount();
        else
            view.showAddAmount();
    }

    @Override
    public void addAmount(double amount) {
        double result = goal.getAccumulatedAmount() + amount;
        if(result < goal.getNeededAmount())
            repository.addAmount(this.goalId, result);
        else
            repository.addAmount(this.goalId, goal.getNeededAmount());
    }

    @Override
    public String getMinAmountPerMonth(Goal goal) {
        LocalDate startDate = LocalDate.parse(goal.getStartDate());
        LocalDate wantedDate = LocalDate.parse(goal.getWantedDate());
        int month = Months.monthsBetween(startDate, wantedDate).getMonths();
        double result = (goal.getNeededAmount() - goal.getAccumulatedAmount()) / month;
        return String.format("%.2f", result);
    }

    @Override
    public void subscribe() {
        openGoal(this.goalId);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private void processGoal(Goal goal){
        this.goal = goal;
        view.setTitle(goal.getTitle());
        view.setWantedDate(goal.getWantedDate());
        view.setNeededAmount(goal.getNeededAmount());
        view.setAccumulatedAmount(goal.getAccumulatedAmount());
        view.setProgressText(String.valueOf(goal.getNeededAmount()),
                String.valueOf(goal.getAccumulatedAmount()), goal.getCurrency().getSymbol());
        view.setMinAmountPerMonth(this.getMinAmountPerMonth(goal).concat(goal.getCurrency().getSymbol()));
        view.setNote(goal.getNote());
        if(goal.getState() == 3)
            view.setGoalButtonsGone();
    }
}
