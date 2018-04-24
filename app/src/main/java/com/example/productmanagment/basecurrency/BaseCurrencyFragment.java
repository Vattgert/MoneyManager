package com.example.productmanagment.basecurrency;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.productmanagment.R;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseCurrencyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseCurrencyFragment extends Fragment implements BaseCurrencyContract.View{
    BaseCurrencyContract.Presenter presenter;
    Button currencyButton, selectCurrencyButton;

    public BaseCurrencyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BaseCurrencyFragment newInstance() {
        BaseCurrencyFragment fragment = new BaseCurrencyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_base_currency, container, false);
        currencyButton = view.findViewById(R.id.currenciesDialogButton);
        selectCurrencyButton = view.findViewById(R.id.selectCurrencyButton);
        currencyButton.setOnClickListener(__ -> presenter.openShowCurrenciesDialog());
        selectCurrencyButton.setOnClickListener(__ -> presenter.selectCurrency());
        return view;
    }

    @Override
    public void showCurrenciesDialog() {
        selectBaseCurrencyDialog().show();
    }

    @Override
    public void showCurrencyButtonText(String text) {
        currencyButton.setText(text);
    }

    @Override
    public void setPresenter(BaseCurrencyContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private Dialog selectBaseCurrencyDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_select_base_currency, null);
        RecyclerView recyclerView = view.findViewById(R.id.baseCurrencyRecyclerView);
        BaseCurrencyAdapter adapter = new BaseCurrencyAdapter(new ArrayList<>(Currency.getAvailableCurrencies()));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        builder.setTitle("Оберіть базову валюту")
                .setView(view)
                .setPositiveButton(R.string.debt_enter_sum_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(adapter.getLastSelectedPosition() != -1){
                            Currency currency = adapter.getItem(adapter.getLastSelectedPosition());
                            presenter.setCurrencyButtonText(getString(R.string.base_currency_select_item,
                                    currency.getCurrencyCode(), currency.getDisplayName()));
                            presenter.setCurrency(currency);
                        }
                    }
                })
                .setNegativeButton(R.string.debt_enter_sum_reject, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

    private static class BaseCurrencyAdapter extends RecyclerView.Adapter<BaseCurrencyAdapter.ViewHolder>{
        private List<Currency> currencies;
        private int lastSelectedPosition = -1;

        public BaseCurrencyAdapter(List<Currency> currencies) {
            this.currencies = currencies;
        }

        @Override
        public BaseCurrencyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dialog_select_base_currency, parent, false);
            return new BaseCurrencyAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(BaseCurrencyAdapter.ViewHolder holder, int position) {
            Currency debt = currencies.get(position);
            holder.bind(debt);
        }

        @Override
        public int getItemCount() {
            return currencies.size();
        }

        public void setData(List<Currency> debtList){
            this.currencies = currencies;
            notifyDataSetChanged();
        }

        public int getLastSelectedPosition() {
            return lastSelectedPosition;
        }

        public Currency getItem(int position){
            return currencies.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            Currency currency;
            TextView currencyTextView;
            RadioButton currencyRadioButton;
            Resources resources;

            public ViewHolder(View view) {
                super(view);
                currencyTextView = view.findViewById(R.id.baseCurrencyTextView);
                currencyRadioButton = view.findViewById(R.id.baseCurrencyRadioButton);
                currencyRadioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lastSelectedPosition = getAdapterPosition();
                    }
                });
                resources = view.getResources();
            }

            public void bind(Currency currency){
                this.currency = currency;
                currencyTextView.setText(resources.getString(R.string.base_currency_select_item,
                        currency.getCurrencyCode(), currency.getDisplayName()));
            }
        }
    }
}
