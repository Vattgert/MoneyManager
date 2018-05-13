package com.example.productmanagment.accountdetailandedit;


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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.productmanagment.R;
import com.example.productmanagment.adapters.MyCurrencySpinnerAdapter;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.MyCurrency;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountDetailAndEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountDetailAndEditFragment extends Fragment implements AccountDetailAndEditContract.View{
    AccountDetailAndEditContract.Presenter presenter;
    EditText accountTitleEditText, accountColorEditText, accountCurrencyDetailEditText;

    public AccountDetailAndEditFragment() {
        // Required empty public constructor
    }


    public static AccountDetailAndEditFragment newInstance() {
        return new AccountDetailAndEditFragment();
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
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_detail_and_edit, container, false);
        setHasOptionsMenu(true);
        accountTitleEditText = view.findViewById(R.id.accountNameDetailEditText);
        accountColorEditText = view.findViewById(R.id.accountColorDetailEditText);
        accountCurrencyDetailEditText = view.findViewById(R.id.accountCurrencyEditText);
        accountColorEditText.setOnClickListener(__->presenter.openColorPick());
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_edit_fragment_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                getDataAndUpdate();
                break;
            case R.id.action_delete:
                presenter.deleteAccount();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void setAccountTitle(String accountTitle) {
        accountTitleEditText.setText(accountTitle);
    }

    @Override
    public void setAccountCurrency(MyCurrency currency) {
        accountCurrencyDetailEditText.setText(currency.getCode());
    }

    @Override
    public void setAccountColor(String color) {
        accountColorEditText.setText(color);
    }


    @Override
    public void showColorPick() {
        new SpectrumDialog.Builder(getContext())
                .setColors(R.array.pallete_colors)
                .setDismissOnColorSelected(true)
                .setOutlineWidth(2)
                .setOnColorSelectedListener((positiveResult, color) -> {
                    if (positiveResult) {
                        setColorValue("#" + Integer.toHexString(color).substring(2));
                    } else {
                        Toast.makeText(getContext(), "Колір не був обраний", Toast.LENGTH_SHORT).show();
                    }
                }).build().show(getFragmentManager(), "dialog_demo_1");
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void closeView() {
        getActivity().finish();
    }

    @Override
    public void setPresenter(AccountDetailAndEditContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void getDataAndUpdate(){
        String title = accountTitleEditText.getText().toString();
        String color = accountColorEditText.getText().toString();
        Account account = new Account();
        account.setName(title);
        account.setColor(color);
        presenter.updateAccount(account);
    }

    public void setColorValue(String colorValue) {
        accountColorEditText.setText(colorValue);
    }
}
