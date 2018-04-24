package com.example.productmanagment.addgoal;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Goal;

public interface AddGoalContract {
    interface Presenter extends BasePresenter{
        void createGoal(Goal goal);
        void openDatePick();
    }

    interface View extends BaseView<Presenter>{
        void setAccumulatedAmountDefaultValue();
        void setNeededAmountDefaultValue();
        void showDatePick();
        void showGoalCreatedMessage();
        void showRestrictedGoalMessage();
    }
}
