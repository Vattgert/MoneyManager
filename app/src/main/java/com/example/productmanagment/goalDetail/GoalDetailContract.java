package com.example.productmanagment.goalDetail;

import android.app.Dialog;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Goal;

public interface GoalDetailContract {
    interface Presenter extends BasePresenter{
        void openGoal(String goalId);
        void makeGoalReached();
        void openAddAmount();
        void addAmount(double amount);
        String getMinAmountPerMonth(Goal goal);
    }

    interface View extends BaseView<Presenter> {
        void setTitle(String title);
        void setWantedDate(String wantedDate);
        void setNeededAmount(double neededAmount);
        void setAccumulatedAmount(double accumulatedAmount);
        void setMinAmountPerMonth(String minAmountPerMonth);
        void setProgressText(String neededAmount, String accumulatedAmount);
        void setNote(String note);
        void setGoalButtonsGone();
        void showFullAccumulatedAmount();
        void showGoalReached();
        void showAddAmount();
    }
}
