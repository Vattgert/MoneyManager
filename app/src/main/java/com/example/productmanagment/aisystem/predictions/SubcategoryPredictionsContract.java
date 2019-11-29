package com.example.productmanagment.aisystem.predictions;

import android.content.Intent;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.source.remote.remotemodels.ExpensePrediction;
import com.example.productmanagment.data.source.remote.remotemodels.Subcategory;
import com.example.productmanagment.data.source.remote.responses.TimeSeriesForecast;

import java.util.List;

public interface SubcategoryPredictionsContract {
    interface View extends BaseView<SubcategoryPredictionsContract.Presenter> {
        void showMessage(String message);
        void showPredictionResults(String predictionResults);
        void setCategories(List<Subcategory> subcategoryList);
        void setPredictionsResults(List<ExpensePrediction> forecast);
    }

    interface Presenter extends BasePresenter {
        void loadCategories();
        void getPredictions(String householdId, String subcategoryId, String period);
    }
}
