package com.example.productmanagment.goals;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Goal;

import java.util.List;

public interface GoalsContract {
    interface Presenter extends BasePresenter{
        void loadGoals(int state);
        void goalsLoading(int state);
        void openAddGoal();
        void openDetailGoal();
        void openEditGoal();
    }

    interface View extends BaseView<Presenter> {
        void showGoals(List<Goal> goalList);
        void showAddGoal();
        void showDetailGoal();
        void showEditGoal();
    }
}
