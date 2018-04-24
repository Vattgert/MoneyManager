package com.example.productmanagment.basecurrency;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;

import java.util.Currency;

public interface BaseCurrencyContract {
    interface Presenter extends BasePresenter {
        void openShowCurrenciesDialog();
        void selectCurrency();
        void setCurrencyButtonText(String text);
        void setCurrency(Currency currency);
    }

    interface View extends BaseView<Presenter> {
        void showCurrenciesDialog();
        void showCurrencyButtonText(String text);

    }
}
