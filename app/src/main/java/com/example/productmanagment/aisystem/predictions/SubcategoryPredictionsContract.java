package com.example.productmanagment.aisystem.predictions;

import android.content.Intent;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.authorization.AuthorizationContract;

public interface SubcategoryPredictionsContract {
    interface View extends BaseView<AuthorizationContract.Presenter> {
        void showMessage(String message);
        void showPredictionResults(String predictionResults);
        void setCategories();
    }

    interface Presenter extends BasePresenter {
        void sendPredictionOptions(String predictionOptions);
    }
}
