package com.example.productmanagment.addaccount;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productmanagment.R;
import com.example.productmanagment.adapters.MyCurrencySpinnerAdapter;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.MyCurrency;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAccountFragment extends Fragment implements AddAccountContract.View {
    private AddAccountContract.Presenter presenter;
    private EditText accountNameEditText, accountStartValueEditText, accountColorEditText;
    private Spinner chooseCurrencySpinner;
    private MyCurrencySpinnerAdapter adapter;


    public AddAccountFragment() {
        // Required empty public constructor
    }

    public static AddAccountFragment newInstance() {
        return new AddAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MyCurrencySpinnerAdapter(getContext(), android.R.layout.simple_spinner_item,
                new ArrayList<>(0), android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_account, container, false);
        setHasOptionsMenu(true);
        accountNameEditText = view.findViewById(R.id.accountNameAddEditText);
        accountStartValueEditText = view.findViewById(R.id.accountStartValueAddEditText);
        accountColorEditText = view.findViewById(R.id.accountColorAddEditText);
        accountColorEditText.setOnClickListener(__ -> presenter.openColorPickDialog());
        chooseCurrencySpinner = view.findViewById(R.id.accountCurrencyAddSpinner);
        chooseCurrencySpinner.setAdapter(adapter);
        setStartAccountValue();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_fragment_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                getDataAndSave();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        //getActivity().finish();
        return true;
    }

    @Override
    public void setPresenter(AddAccountContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setStartAccountValue() {
        accountStartValueEditText.setText("0.0");
    }

    @Override
    public void setColorValue(String color) {
        accountColorEditText.setText(color);
    }

    @Override
    public void showColorPickDialog() {
        new SpectrumDialog.Builder(getContext())
                .setColors(R.array.pallete_colors)
                .setDismissOnColorSelected(true)
                .setOutlineWidth(2)
                .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                    @Override public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                        if (positiveResult) {
                            setColorValue("#" + Integer.toHexString(color).substring(2));
                        } else {
                            Toast.makeText(getContext(), "Колір не був обраний", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).build().show(getFragmentManager(), "dialog_demo_1");
    }

    @Override
    public void setCurrenciesToSpinner(List<MyCurrency> currencies) {
        adapter.setData(currencies);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void closeView() {
        getActivity().finish();
    }

    private void getDataAndSave(){
        //TODO: Сделать еще валюты
        Account account;
        String accountName = accountNameEditText.getText().toString();
        BigDecimal accountStartValue = BigDecimal.valueOf(Double.valueOf(accountStartValueEditText.getText().toString()));
        MyCurrency currency = null;
        String color = accountColorEditText.getText().toString();
        account = new Account(accountName, accountStartValue, currency, color);
        presenter.createAccount(account);
    }


}
