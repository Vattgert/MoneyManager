package com.example.productmanagment.addaccount;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;

public class AddAccountPresenter implements AddAccountContract.Presenter {

    private AddAccountContract.View view;
    private ExpensesRepository repository;

    public AddAccountPresenter(AddAccountContract.View view, ExpensesRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void createAccount(Account account) {
        repository.saveAccount(account);
    }

    @Override
    public void openColorPickDialog() {
        view.showColorPickDialog();
    }
}
