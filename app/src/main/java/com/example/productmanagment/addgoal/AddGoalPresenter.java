package com.example.productmanagment.addgoal;

import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;

public class AddGoalPresenter implements AddGoalContract.Presenter{
    AddGoalContract.View view;
    ExpensesRepository repository;

    public AddGoalPresenter(AddGoalContract.View view, ExpensesRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }

    @Override
    public void createGoal(Goal goal) {
        repository.saveGoal(goal);
        view.showGoalCreatedMessage();
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
