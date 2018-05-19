package com.example.productmanagment.currencydetailandedit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.MyCurrency;

public class CurrencyDetailAndEditFragment extends Fragment implements CurrencyDetailAndEditContract.View {
    CurrencyDetailAndEditContract.Presenter presenter;
    private EditText currencyTitleEditText, currencyCodeEditText, currencySymbolEditText, currencyRateEditText,
            currencyReverseRateEditText;

    public CurrencyDetailAndEditFragment() {
    }

    public static CurrencyDetailAndEditFragment newInstance() {
        CurrencyDetailAndEditFragment fragment = new CurrencyDetailAndEditFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_currency_detail_and_edit, container, false);
        currencyTitleEditText = view.findViewById(R.id.detailCurrencyTitleEditText);
        currencyCodeEditText = view.findViewById(R.id.currencyCodeEditText);
        currencySymbolEditText = view.findViewById(R.id.currencySymbolEditText);
        currencyRateEditText = view.findViewById(R.id.currencyRateEditText);
        currencyReverseRateEditText = view.findViewById(R.id.currencyReverseRateEditText);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_edit_fragment_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                getDataAndSave();
                break;
            case R.id.action_delete:
                presenter.deleteCurrency();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setCurrencyTitle(String title) {
        currencyTitleEditText.setText(title);
    }

    @Override
    public void setCurrencyRate(String rate) {
        currencyRateEditText.setText(rate);
    }

    @Override
    public void setCurrencyReverseRate(String rate) {
        currencyReverseRateEditText.setText(rate);
    }

    @Override
    public void setCurrencyCode(String code) {
        currencyCodeEditText.setText(code);
    }

    @Override
    public void setCurrencySymbol(String symbol) {
        currencySymbolEditText.setText(symbol);
    }

    @Override
    public void setPresenter(CurrencyDetailAndEditContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void getDataAndSave(){
        String rate = currencyRateEditText.getText().toString();
        String reverseRate = currencyReverseRateEditText.getText().toString();
        String code = currencyCodeEditText.getText().toString();
        String symbol = currencySymbolEditText.getText().toString();
        MyCurrency myCurrency = new MyCurrency();
        myCurrency.setRateToBaseCurrency(Double.valueOf(rate));
        myCurrency.setRateBaseToThis(Double.valueOf(reverseRate));
        myCurrency.setCode(code);
        myCurrency.setSymbol(symbol);
        presenter.updateCurrency(myCurrency);
    }
}
