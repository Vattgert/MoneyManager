package com.example.productmanagment.signup;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.User;

public interface SignUpContract {
    interface Presenter extends BasePresenter{
        void signUp(User user);
        void authAfterSignUp(String email, String password);
    }

    interface View extends BaseView<Presenter> {
        void finish();
        void showMessage(String message);
        boolean isAuthAfterSignUpChecked();
        void openMainActivity();
    }
}
