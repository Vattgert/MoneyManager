package com.example.productmanagment.addcurrency;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.MyCurrency;

import java.util.Currency;
import java.util.List;

public interface AddCurrencyContract {
    interface Presenter extends BasePresenter{
        void uploadCurrencies();
        void uploadBaseCurrency();
        void setCurrencyData(Currency currency);
        void createCurrency(MyCurrency currency);
        void updateCurrencyRate(String fcode, String scode);
    }

    interface View extends BaseView<Presenter>{
        void showCurrencies(List<Currency> currencyList);
        void setSymbol(String symbol);
        void setCode(String code);
        void setRate(String rate, String currencyCode);
        void setReverseRate(String reverseRate, String currencyCode);
        void showCreateCurrencyMessage();
        void showMessage(String message);
        void finish();
    }
}
