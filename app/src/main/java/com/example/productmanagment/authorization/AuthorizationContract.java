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
        void setAuthUserUi(String email);
        void showAuthorizationFailedMessage();
        void showMainActivityIfAuth();
        void showGoogleSignInActivity(Intent intent);
    }

    interface Presenter extends BasePresenter{
        void signInWithEmailAndPassword(String email, String password);
        void signUpWithEmailAndPassword(String email, String password);
        void signInWithGoggle();
        void signInWithFacebook();
        void signInWithPhone();
        void result(int requestCode, int resultCode, Intent data);
    }
}
