package com.example.productmanagment.addcurrency;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productmanagment.R;
import com.example.productmanagment.adapters.CurrencySpinnerAdapter;
import com.example.productmanagment.data.models.MyCurrency;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCurrencyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCurrencyFragment extends Fragment implements AddCurrencyContract.View {
    private AddCurrencyContract.Presenter presenter;
    private CurrencySpinnerAdapter adapter;
    private Spinner currencySpinner;
    private TextView currencyRateTextView, currencyReverseRateTextView;
    private EditText currencyCodeEditText, currencySymbolEditText, currencyRateEditText,
            currencyReverseRateEditText;
    private Button updateCurrencyRateButton;

    public AddCurrencyFragment() {
        // Required empty public constructor
    }


    public static AddCurrencyFragment newInstance() {
        return new AddCurrencyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Додати валюту");
        adapter = new CurrencySpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>(0), android.R.layout.simple_spinner_dropdown_item);
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
        View view = inflater.inflate(R.layout.fragment_add_currency, container, false);
        setHasOptionsMenu(true);
        currencyCodeEditText = view.findViewById(R.id.currencyCodeEditText);
        currencySymbolEditText = view.findViewById(R.id.currencySymbolEditText);
        currencyRateEditText = view.findViewById(R.id.currencyRateEditText);
        currencyReverseRateEditText = view.findViewById(R.id.currencyReverseRateEditText);
        currencyRateTextView = view.findViewById(R.id.currencyRateTextView);
        currencyReverseRateTextView = view.findViewById(R.id.currencyReverseRateTextView);
        currencySpinner = view.findViewById(R.id.chooseCurrencySpinner);
        currencySpinner.setAdapter(adapter);
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Currency currency = adapter.getItem(i);
                presenter.setCurrencyData(currency);
                presenter.updateCurrencyRate(currency.getCurrencyCode(), "UAH");
                presenter.updateCurrencyRate("UAH", currency.getCurrencyCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
    public void showCurrencies(List<Currency> currencyList) {
        adapter.setData(currencyList);
    }

    @Override
    public void setSymbol(String symbol) {
        currencySymbolEditText.setText(symbol);
    }

    @Override
    public void setCode(String code) {
        currencyCodeEditText.setText(code);
    }

    @Override
    public void setRate(String rate, String currencyCode) {
        currencyRateTextView.setText(currencyCode);
        currencyRateEditText.setText(rate);
    }

    @Override
    public void setReverseRate(String reverseRate, String currencyCode) {
        currencyReverseRateTextView.setText(currencyCode);
        currencyReverseRateEditText.setText(reverseRate);
    }

    @Override
    public void showCreateCurrencyMessage() {
        Toast.makeText(getContext(), "Валюта успішно додана", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(AddCurrencyContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void getDataAndSave(){
        String title = ((Currency)currencySpinner.getSelectedItem()).getDisplayName();
        String code = currencyCodeEditText.getText().toString();
        String symbol = currencySymbolEditText.getText().toString();
        double rateToBaseCurrency = Double.valueOf(currencyRateEditText.getText().toString());
        double rateBaseToThis = Double.valueOf(currencyReverseRateEditText.getText().toString());
        int isBase = 0;
        MyCurrency myCurrency = new MyCurrency(title, code, symbol, rateToBaseCurrency, rateBaseToThis, isBase);
        presenter.createCurrency(myCurrency);
    }
}
