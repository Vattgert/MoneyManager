package com.example.productmanagment.plannedpayment;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.PlannedPayment;

import java.util.List;

/**
 * Created by Ivan on 25.02.2018.
 */

public interface PlannedPaymentContract {
    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);
        void showPlannedPayments(List<PlannedPayment> plannedPayments);
        void showAddPlannedPayment();
        void showLoadingPlannedPaymentsError();
        void showNoPlannedPayments();
        void showPlannedPaymentSuccessfullySavedMessage();
    }

    interface Presenter extends BasePresenter {
        void loadPlannedPayments();
        void addPlannedPayments();
        void result(int requestCode, int resultCode);
    }
}
