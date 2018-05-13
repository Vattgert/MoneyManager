package com.example.productmanagment.signup;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.remote.responses.SuccessResponse;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpPresenter implements SignUpContract.Presenter{
    private SignUpContract.View view;
    private RemoteDataRepository repository;
    private BaseSchedulerProvider provider;

    public SignUpPresenter(SignUpContract.View view, RemoteDataRepository repository, BaseSchedulerProvider provider) {
        this.view = view;
        this.repository = repository;
        this.provider = provider;
        this.view.setPresenter(this);
    }

    @Override
    public void signUp(User user) {
        repository.signUpUser(user)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processResponse);
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

    private void processResponse(SuccessResponse response){
        if (response.response.equals("success")){
            view.showMessage(response.data);
            if(view.isAuthAfterSignUpChecked()){

            }
        }
        else{
            view.showMessage(response.errorData);
        }
    }
}
