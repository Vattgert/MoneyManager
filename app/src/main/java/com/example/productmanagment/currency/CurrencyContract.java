package com.example.productmanagment.currency;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.MyCurrency;

import java.util.Currency;
import java.util.List;

public interface CurrencyContract {
    interface Presenter extends BasePresenter{
        void loadCurrencies();
        void loadRemoteCurrencies(int groupId);
        void openAddCurrency();
        void openDetailAndEditCurrency(String currencyId);
    }

    interface View extends BaseView<Presenter>{
        void showCurrencies(List<MyCurrency> currencies);
        void showAddCurrency();
        void showDetailAndEditCurrency(int groupId, String currencyId);
    }
}
