package com.example.productmanagment.signup;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpPresenter implements SignUpContract.Presenter{
    private SignUpContract.View view;
    private RemoteDataRepository repository;

    public SignUpPresenter(SignUpContract.View view, RemoteDataRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }

    @Override
    public void signUp(String email, String login, String password) {

    }

    @Override
    public void signUpToDatabase(String email, String login) {

    }

    @Override
    public void authAfterSignUp(String email, String password) {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
