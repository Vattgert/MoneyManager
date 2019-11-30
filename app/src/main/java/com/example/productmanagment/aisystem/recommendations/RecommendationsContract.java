package com.example.productmanagment.aisystem.recommendations;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.source.remote.remotemodels.Recommendation;

import java.util.List;

public interface RecommendationsContract {
    interface View extends BaseView<RecommendationsContract.Presenter> {
        void showMessage(String message);
        void setRecommendations(List<Recommendation> recommendations);
    }

    interface Presenter extends BasePresenter {
        void loadCurrentStateRecommendations();
    }
}
