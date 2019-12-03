package com.example.productmanagment.aisystem.predictions;

import android.util.Log;

import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.remote.remotemodels.ExpensePrediction;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SubcategoryPredictionsPresenter implements SubcategoryPredictionsContract.Presenter {
    int groupId;
    SubcategoryPredictionsContract.View view;
    RemoteDataRepository remoteDataRepository;
    BaseSchedulerProvider provider;
    CompositeDisposable compositeDisposable;

    public SubcategoryPredictionsPresenter(int groupId, SubcategoryPredictionsContract.View view, RemoteDataRepository repository,
                             BaseSchedulerProvider provider) {
        this.groupId = groupId;
        this.view = view;
        this.remoteDataRepository = new RemoteDataRepository();
        this.provider = provider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    public void loadCategories() {
        Disposable disposable = remoteDataRepository.getSubcategories()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(subcategoryResponse -> view.setCategories(subcategoryResponse.subcategoriesList));
        compositeDisposable.add(disposable);
    }

    @Override
    public void getPredictions(String householdId, String subcategoryId, String period) {
        if(groupId != -1)
            if(Integer.valueOf(period) <= 24)
                remoteDataRepository.getSubcategoryForecast(String.valueOf(groupId), subcategoryId, period)
                        .subscribeOn(provider.io())
                        .observeOn(provider.ui())
                        .subscribe(expensePredictionResponse -> {
                            view.setPredictionsResults(expensePredictionResponse.getPredictionList());
                        });
            else
                view.showMessage("Оберіть період в діапазоні від 0 до 24");
    }

    @Override
    public void subscribe() {
        Log.wtf("MyLog Forecast", groupId + "");
        if(groupId != -1)
            loadCategories();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

}
