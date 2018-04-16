package com.example.productmanagment.addaccount;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Account;

public interface AddAccountContract {
    interface Presenter extends BasePresenter{
        void createAccount(Account account);
        void openColorPickDialog();
    }

    interface View extends BaseView<Presenter>{
        void setStartAccountValue();
        void setColorValue(String color);
        void showColorPickDialog();
    }
}
