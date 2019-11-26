package com.example.productmanagment.authorization;

import android.content.Context;

import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.users.UserSession;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

/**
 * Created by Ivan on 26.02.2018.
 */

public class AuthorizationPresenter implements AuthorizationContract.Presenter {
    private AuthorizationContract.View view;
    private Context context;
    private RemoteDataRepository repository;
    private BaseSchedulerProvider provider;
    UserSession userSession;

    public AuthorizationPresenter(AuthorizationContract.View view, Context context,
                                  RemoteDataRepository repository, BaseSchedulerProvider provider) {
        this.view = view;
        this.view.setPresenter(this);
        this.repository = repository;
        this.context = context;
        this.provider = provider;
        userSession = new UserSession(context);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void signInWithEmailAndPassword(String email, String password) {
        repository.signInUser(email, password)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processUserAuth);
    }


    @Override
    public void openSignUp() {
        view.showSignUp();
    }

    private void processUserAuth(User user){
        if(user.getEmail() != null){
            userSession.createLoginSession(user);
            view.showMainActivityIfAuth();
        }
        else{
            view.showMessage("Користувача з такими даними немає");
        }
    }
}
