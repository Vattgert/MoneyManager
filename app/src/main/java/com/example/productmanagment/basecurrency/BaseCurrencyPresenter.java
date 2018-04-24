package com.example.productmanagment.basecurrency;

import com.example.productmanagment.data.models.MyCurrency;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;

import java.util.Currency;

public class BaseCurrencyPresenter implements BaseCurrencyContract.Presenter {
    BaseCurrencyContract.View view;
    ExpensesRepository repository;
    Currency currency;

    public BaseCurrencyPresenter(BaseCurrencyContract.View view, ExpensesRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }

    @Override
    public void openShowCurrenciesDialog() {
        view.showCurrenciesDialog();
    }

    @Override
    public void selectCurrency() {
        if(currency != null)
        {
            MyCurrency myCurrency = new MyCurrency(
                    currency.getDisplayName(),
                    currency.getCurrencyCode(),
                    currency.getSymbol(),
                    0.0, 0.0, 1
            );
            repository.saveCurrency(myCurrency);
        }
        //repository save base currency
    }

    @Override
    public void setCurrencyButtonText(String text) {
        view.showCurrencyButtonText(text);
    }

    @Override
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
