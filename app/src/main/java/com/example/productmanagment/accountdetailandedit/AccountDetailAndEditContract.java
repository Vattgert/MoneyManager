package com.example.productmanagment.accountdetailandedit;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Account;

public interface AccountDetailAndEditContract {
    interface Presenter extends BasePresenter{
        void openAccount(String accountId);
        void updateAccount(Account account);
        void deleteAccount();
        void openColorPick();
    }

    interface View extends BaseView<Presenter>{
        void setAccountTitle(String accountTitle);
        void setAccountCurrency(String currency);
        void setAccountColor(String color);
        void showColorPick();
    }
}
