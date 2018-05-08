package com.example.productmanagment.userrights;

import android.util.Log;

import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.remote.responses.SuccessResponse;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class UserRightsPresenter implements UserRightsContract.Presenter{
    String groupId, userId;
    UserRightsContract.View view;
    ExpensesRepository repository;
    RemoteDataRepository remoteDataRepository;
    BaseSchedulerProvider provider;
    CompositeDisposable compositeDisposable;

    public UserRightsPresenter(String groupId, String userId, UserRightsContract.View view,
                               ExpensesRepository repository, BaseSchedulerProvider provider) {
        this.groupId = groupId;
        this.userId = userId;
        this.view = view;
        this.repository = repository;
        this.provider = provider;
        compositeDisposable = new CompositeDisposable();
        remoteDataRepository = new RemoteDataRepository();
        this.view.setPresenter(this);
    }

    @Override
    public void setUserRights(String userRights) {
        remoteDataRepository.setUserRights(this.groupId, this.userId, userRights)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processSetUserRights);
    }

    @Override
    public void loadUserWithRights(String userId, String groupId) {
        remoteDataRepository.getUserRights(groupId, userId)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processUserRights);
    }

    @Override
    public void loadUserRights() {
        Disposable disposable = repository.getUserRightsList()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(view::showUserRights, throwable -> Log.wtf("GroupLog", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void subscribe() {
        loadUserRights();
        loadUserWithRights(this.userId, this.groupId);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private void processUserRights(SuccessResponse response){
        Log.wtf("GroupLog", "User rights response " + response.response);
        if(response.response.equals("success")){
            Log.wtf("GroupLog", "User rights " + response.additionalData);
            String[] rights = response.additionalData.split(",");
            view.setUserRightsEnabled(rights);
        }
    }

    private void processSetUserRights(SuccessResponse response){
        if(response.response.equals("success")){
            view.showMessage(response.data);
        }
        else{
            view.showMessage(response.errorData);
        }
    }
}
