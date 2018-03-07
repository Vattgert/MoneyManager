package com.example.productmanagment.addplannedpayment;

import android.content.Intent;

import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.PlannedPayment;

/**
 * Created by Ivan on 26.02.2018.
 */

public class AddPlannedPaymentPresenter implements AddPlannedPaymentContract.Presenter {
    AddPlannedPaymentContract.View view;

    public AddPlannedPaymentPresenter(AddPlannedPaymentContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public Category getChosenCategory() {
        return null;
    }

    @Override
    public void choosePlace() {
        view.showChoosePlacePicker();
    }

    @Override
    public void chooseFrequency() {
        view.showFrequencyDialog();
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void savePlannedPayment(PlannedPayment plannedPayment) {

    }

    @Override
    public void createExpensesForPayment() {

    }
}
