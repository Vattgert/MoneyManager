package com.example.productmanagment.addplannedpayment;

import android.content.Intent;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.PlannedPayment;

/**
 * Created by Ivan on 26.02.2018.
 */

public interface AddPlannedPaymentContract {
    interface Presenter extends BasePresenter{
        Category getChosenCategory();
        void choosePlace();
        void chooseFrequency();
        void getFrequency();
        void result(int requestCode, int resultCode, Intent data);
        void savePlannedPayment(PlannedPayment plannedPayment);
        void createExpensesForPayment();
    }

    interface View extends BaseView<Presenter>{
        void setChosenCategory(String title);
        void setChosenPlace(String place);
        void showPlannedPayment();
        void showFrequencyDialog();
        void showChoosePlacePicker();
        void populateRepeats();
    }
}
