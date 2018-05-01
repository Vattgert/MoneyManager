package com.example.productmanagment.goalEdit;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Goal;

public interface GoalEditContract {
    interface Presenter extends BasePresenter{
        void openGoal();
        void deleteGoal();
        void updateGoal(Goal goal);
        void pauseGoal();
        void activateGoal();
        int getGoalState();
    }

    interface View extends BaseView<Presenter>{
        void setGoalTitle(String title);
        void setGoalNeededSum(double neededSum);
        void setGoalAccumulatedSum(double accumulatedSum);
        void setGoalWantedDate(String wantedDate);
        void setGoalNote(String note);
        void finishActivity();

        void showGoalPaused();
        void showGoalActivated();
        void showGoalDeleted();
        void showGoalUpdated();
    }
}
