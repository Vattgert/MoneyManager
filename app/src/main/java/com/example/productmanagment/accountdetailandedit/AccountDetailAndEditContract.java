package com.example.productmanagment.accountdetailandedit;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.MyCurrency;

import java.util.List;

public interface AccountDetailAndEditContract {
    interface Presenter extends BasePresenter{
        void openAccount(String accountId);
        void openRemoteAccount(String accountId);
        void updateAccount(Account account);
        void updateRemoteAccount(Account account);
        void deleteAccount();
        void deleteRemoteAccount();
        void openColorPick();
    }

    interface View extends BaseView<Presenter>{
        void setAccountTitle(String accountTitle);
        void setAccountCurrency(MyCurrency currency);
        void setAccountColor(String color);
        void showColorPick();
        void showMessage(String message);
        void closeView();
    }
}
