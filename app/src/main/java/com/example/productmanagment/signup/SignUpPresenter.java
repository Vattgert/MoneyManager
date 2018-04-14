package com.example.productmanagment.signup;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpPresenter implements SignUpContract.Presenter{
    private FirebaseAuth auth;
    private SignUpContract.View view;
    private RemoteDataRepository repository;

    public SignUpPresenter(SignUpContract.View view, RemoteDataRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void signUp(String email, String login, String password) {
        if(auth != null){
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    signUpToDatabase(email, login);
                    authAfterSignUp(email, password);
                    view.openMainActivity();
                    view.finish();
                }
                else{
                    Log.wtf("AuthLog", task.getException());
                }
            });
        }
    }

    @Override
    public void signUpToDatabase(String email, String login) {
        repository.signUpUserToDatabase(email, login);
    }

    @Override
    public void authAfterSignUp(String email, String password) {
        if(auth != null){
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if(!task.isSuccessful()){
                    Log.wtf("RemoteDatabaseLog", task.getException());
                }

            });
        }
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
