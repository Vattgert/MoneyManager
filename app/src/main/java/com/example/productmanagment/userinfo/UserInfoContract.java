package com.example.productmanagment.userinfo;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;

public interface UserInfoContract {
    interface Presenter extends BasePresenter{
        void changePassword(String password);
        void changeUserData(String firstName, String lastName);
        void logout();
        void deleteUserData();
    }

    interface View extends BaseView<Presenter>{
        void showUserFirstName(String firstName);
        void showUserLastName(String lastName);
        void showUserEmail(String email);
    }
}
