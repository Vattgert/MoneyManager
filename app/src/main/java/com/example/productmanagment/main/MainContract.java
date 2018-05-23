package com.example.productmanagment.main;

import android.graphics.Color;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Expense;

import java.util.List;

public interface MainContract {
    interface Presenter extends BasePresenter{
        void loadBalance();
        void loadAccountData();
        void loadLastRecords();
        void loadLastDataDiagram();
    }

    interface View extends BaseView<Presenter>{
        void setBalance(String balance, int color);
        void setAccountData(List<Account> data);
        void setLastRecords(List<Expense> lastRecords);
        void setLastDataDiagram();
    }
}
