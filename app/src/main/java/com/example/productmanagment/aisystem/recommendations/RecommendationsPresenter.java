package com.example.productmanagment.aisystem.recommendations;

import android.util.Log;

import com.example.productmanagment.aisystem.predictions.SubcategoryPredictionsContract;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.remote.remotemodels.Recommendation;
import com.example.productmanagment.data.source.remote.remotemodels.RecommendationsResponse;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class RecommendationsPresenter implements RecommendationsContract.Presenter {
    int householdId;
    RecommendationsContract.View view;
    RemoteDataRepository remoteDataRepository;
    BaseSchedulerProvider provider;
    CompositeDisposable compositeDisposable;

    public RecommendationsPresenter(int groupId, RecommendationsContract.View view, RemoteDataRepository repository,
                                           BaseSchedulerProvider provider) {
        this.householdId = groupId;
        this.view = view;
        this.remoteDataRepository = new RemoteDataRepository();
        this.provider = provider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void loadCurrentStateRecommendations() {
        remoteDataRepository.getRecommendations(String.valueOf(householdId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(recommendationsResponse -> {
                    if(recommendationsResponse.getRecommendationList() != null){
                        view.setRecommendations(recommendationsResponse.getRecommendationList());
                    }
                }, throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
