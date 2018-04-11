package com.example.productmanagment.signup;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;

public interface SignUpContract {
    interface Presenter extends BasePresenter{
        void signUp(String email, String login, String password);
        void signUpToDatabase(String email, String login);
    }

    interface View extends BaseView<Presenter> {

    }
}
