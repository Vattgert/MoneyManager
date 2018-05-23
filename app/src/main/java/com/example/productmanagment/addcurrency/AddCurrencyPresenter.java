package com.example.productmanagment.addcurrency;

import android.util.Log;

import com.example.productmanagment.data.models.MyCurrency;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.remote.responses.SuccessResponse;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AddCurrencyPresenter implements AddCurrencyContract.Presenter {
    int groupId;
    private AddCurrencyContract.View view;
    private ExpensesRepository repository;
    private RemoteDataRepository remoteDataRepository;
    private AsyncHttpClient asyncHttpClient;
    private BaseSchedulerProvider provider;

    public AddCurrencyPresenter(int groupId, AddCurrencyContract.View view, ExpensesRepository repository, BaseSchedulerProvider provider) {
        this.groupId = groupId;
        this.view = view;
        this.repository = repository;
        this.remoteDataRepository = new RemoteDataRepository();
        asyncHttpClient = new AsyncHttpClient();
        this.provider = provider;
        this.view.setPresenter(this);
    }

    @Override
    public void uploadCurrencies() {
        List<Currency> currencyList = new ArrayList<>(Currency.getAvailableCurrencies());
        Collections.sort(currencyList, new Comparator<Currency>() {
            @Override
            public int compare(Currency currency, Currency t1) {
                return currency.getDisplayName().compareTo(t1.getDisplayName());
            }
        });
        view.showCurrencies(currencyList);
    }

    @Override
    public void uploadBaseCurrency() {

    }

    @Override
    public void setCurrencyData(Currency currency) {
        view.setCode(currency.getCurrencyCode());
        view.setSymbol(currency.getSymbol());
    }

    @Override
    public void createCurrency(MyCurrency currency) {
        if(groupId == -1)
            repository.saveCurrency(currency);
        else {
            currency.setGroupId(this.groupId);
            Log.wtf("CurrencyLog", currency.toString());
            remoteDataRepository.addCurrency(currency)
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
                    .subscribe(response -> processResponse(response),
                            throwable -> Log.wtf("CurrencyLog", throwable.getMessage()));
            view.showCreateCurrencyMessage();
        }
    }

    @Override
    public void updateCurrencyRate(String fcode, String scode) {
        currencyRateRequest(fcode, scode);
    }

    @Override
    public void subscribe() {
        Log.wtf("CurrencyLog", "group id = " + this.groupId);
        uploadCurrencies();
    }

    @Override
    public void unsubscribe() {

    }

    private void processResponse(SuccessResponse response){
        if(response.response.equals("success"))
            view.showMessage(response.data);
        else
            view.showMessage(response.errorData);
    }

    private void currencyRateRequest(String fcode, String scode) {
        String url = "http://free.currencyconverterapi.com/api/v5/convert?q=" + fcode + "_" + scode + "&compact=y";
        Log.wtf("MyLog", url);
        asyncHttpClient.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONObject result = null;
                try {
                    result = response.getJSONObject(fcode + "_" + scode);
                    String rate = result.getString("val");
                    if(fcode == "UAH") {
                        view.setRate(rate, "UAH");
                    }
                    else{
                        view.setReverseRate(rate, fcode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.wtf("MyLog", "errior" + throwable.getMessage());
            }
        });
    }
}
