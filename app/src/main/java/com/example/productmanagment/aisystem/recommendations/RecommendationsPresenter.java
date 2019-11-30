package com.example.productmanagment.aisystem.recommendations;

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

    }

    public List<Recommendation> getRecommendations(){
        List<Recommendation> recommendations = new ArrayList<>();
        recommendations.add(new Recommendation( "Домогосподарство може заощадити кошти зменшивши витрати на алкоголь та тютюн."));
        recommendations.add(new Recommendation(  "Витрачаючи гроші на азартні ігри домогосподарство ризикує потрапити у борги."));
        recommendations.add(new Recommendation( "Домогосподарство може заощадити кошти, зменшивши витрати по категоріям з низьким пріоритетом."));
        recommendations.add(new Recommendation( "Домогосподарству рекомендується зробити погашення боргу по кредиту пріоритетною метою."));
        return recommendations;
    }

    @Override
    public void subscribe() {
        List<Recommendation> recommendations = getRecommendations();
        view.setRecommendations(recommendations);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
