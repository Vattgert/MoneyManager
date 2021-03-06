package com.example.productmanagment.authorization;

import android.content.Intent;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Ivan on 26.02.2018.
 */

public interface AuthorizationContract {
    interface View extends BaseView<Presenter> {
        void showMessage(String message);
        void setAuthUserUi(String email);
        void showAuthorizationFailedMessage();
        void showMainActivityIfAuth();
        void showGoogleSignInActivity(Intent intent);
        void showSignUp();
    }

    interface Presenter extends BasePresenter{
        void signInWithEmailAndPassword(String email, String password);
        void openSignUp();
    }
}
