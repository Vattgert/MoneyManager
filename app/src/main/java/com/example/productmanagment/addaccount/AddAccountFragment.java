package com.example.productmanagment.addaccount;


import android.os.Bundle;
import android.support.annotation.ColorInt;
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
import com.example.productmanagment.data.models.Account;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.math.BigDecimal;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAccountFragment extends Fragment implements AddAccountContract.View {
    private AddAccountContract.Presenter presenter;
    private EditText accountNameEditText, accountStartValueEditText, accountColorEditText;


    public AddAccountFragment() {
        // Required empty public constructor
    }

    public static AddAccountFragment newInstance() {
        return new AddAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_account, container, false);
        setHasOptionsMenu(true);
        accountNameEditText = view.findViewById(R.id.accountNameAddEditText);
        accountStartValueEditText = view.findViewById(R.id.accountStartValueAddEditText);
        accountColorEditText = view.findViewById(R.id.accountColorAddEditText);
        accountColorEditText.setOnClickListener(__ -> presenter.openColorPickDialog());
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
        getActivity().finish();
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

    private void getDataAndSave(){
        //TODO: Сделать еще валюты
        Account account;
        String accountName = accountNameEditText.getText().toString();
        BigDecimal accountStartValue = BigDecimal.valueOf(Double.valueOf(accountStartValueEditText.getText().toString()));
        String currency = "";
        String color = accountColorEditText.getText().toString();
        account = new Account(accountName, accountStartValue, currency, color);
        presenter.createAccount(account);
    }
}
