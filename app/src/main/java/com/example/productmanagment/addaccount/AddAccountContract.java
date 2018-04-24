package com.example.productmanagment.addaccount;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.MyCurrency;

import java.util.List;

public interface AddAccountContract {
    interface Presenter extends BasePresenter{
        void createAccount(Account account);
        void openColorPickDialog();
        void uploadCurrencies();
    }

    interface View extends BaseView<Presenter>{
        void setStartAccountValue();
        void setColorValue(String color);
        void showColorPickDialog();
        void setCurrenciesToSpinner(List<MyCurrency> currencies);

    }
}
