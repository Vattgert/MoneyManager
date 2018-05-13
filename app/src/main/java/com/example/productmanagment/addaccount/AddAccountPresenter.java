package com.example.productmanagment.addaccount;

import android.util.Log;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.MyCurrency;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.remote.responses.SuccessResponse;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class AddAccountPresenter implements AddAccountContract.Presenter {
    private int groupId;
    private AddAccountContract.View view;
    private ExpensesRepository repository;
    private RemoteDataRepository remoteDataRepository;
    private BaseSchedulerProvider provider;

    public AddAccountPresenter(int groupId, AddAccountContract.View view, ExpensesRepository repository,
                               BaseSchedulerProvider provider) {
        this.view = view;
        this.groupId = groupId;
        this.repository = repository;
        this.remoteDataRepository = new RemoteDataRepository();
        this.provider = provider;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        uploadCurrencies();
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void createAccount(Account account) {
        if(groupId == -1) {
            repository.saveAccount(account);
            view.closeView();
        }
        else
            createRemoteAccount(String.valueOf(groupId), account);
    }

    @Override
    public void createRemoteAccount(String groupId, Account account) {
        remoteDataRepository.addAccount(groupId, account)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processSuccessResponse);
    }

    @Override
    public void openColorPickDialog() {
        view.showColorPickDialog();
    }

    @Override
    public void uploadCurrencies() {
        Disposable disposable = repository.getCurrencies()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processCurrencies);
    }

    private void processCurrencies(List<MyCurrency> currencyList){
        for (MyCurrency c : currencyList){
            Log.wtf("MyLog", c.getTitle());
        }
        view.setCurrenciesToSpinner(currencyList);

    }

    private void processSuccessResponse(SuccessResponse response){
        if(response.response.equals("success"))
        {
            view.showMessage(response.data);
        }
        else{
            view.showMessage(response.errorData);
        }
        view.closeView();
    }

}
