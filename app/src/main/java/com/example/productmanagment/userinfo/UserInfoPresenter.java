package com.example.productmanagment.userinfo;

import android.content.Context;

import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.users.UserSession;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

public class UserInfoPresenter implements UserInfoContract.Presenter {
    Context context;
    UserInfoContract.View view;
    RemoteDataRepository repository;
    UserSession userSession;
    BaseSchedulerProvider provider;

    public UserInfoPresenter(Context context, UserInfoContract.View view,
                             RemoteDataRepository repository, BaseSchedulerProvider provider) {
        this.view = view;
        this.repository = repository;
        this.provider = provider;
        this.context = context;
        userSession = new UserSession(context);
        this.view.setPresenter(this);
    }

    @Override
    public void changePassword(String password) {

    }

    @Override
    public void changeUserData(String firstName, String lastName) {

    }

    @Override
    public void logout() {
        userSession.logoutUser();
    }

    @Override
    public void deleteUserData() {

    }

    @Override
    public void subscribe() {
        view.showUserEmail(userSession.getUserDetails().getEmail());
    }

    @Override
    public void unsubscribe() {

    }
}
