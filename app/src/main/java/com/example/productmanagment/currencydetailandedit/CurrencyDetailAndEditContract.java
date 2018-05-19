package com.example.productmanagment.currencydetailandedit;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.MyCurrency;

public interface CurrencyDetailAndEditContract {
    interface Presenter extends BasePresenter{
        void loadCurrencyById(String currencyId);
        void deleteCurrency();
        void updateCurrency(MyCurrency myCurrency);
    }

    interface View extends BaseView<Presenter>{
        void showMessage(String message);
        void setCurrencyTitle(String title);
        void setCurrencyRate(String rate);
        void setCurrencyReverseRate(String rate);
        void setCurrencyCode(String code);
        void setCurrencySymbol(String symbol);
    }
}
