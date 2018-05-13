package com.example.productmanagment.authorization;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

//import com.google.android.gms.auth.api.Auth;
import com.example.productmanagment.R;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.users.UserSession;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

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
