package com.example.productmanagment.templatedetailandedit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.productmanagment.R;
import com.example.productmanagment.adapters.SimpleAccountSpinnerAdapter;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.expensedetailandedit.ExpenseDetailAndEditContract;

import java.util.List;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;

public class TemplateDetailAndEditFragment extends Fragment implements TemplateDetailAndEditContract.View {
    TemplateDetailAndEditContract.Presenter presenter;

    private EditText costEditText, noteEditText, categoryEditText, receiverEditText,
            dateEditText, timeEditText;
    private Spinner typeOfPaymentSpinner, accountSpinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private ToggleSwitch expenseTypeToggleSwitch;

    private SimpleAccountSpinnerAdapter accountSpinnerAdapter;
    public static TemplateDetailAndEditFragment newInstance(String param1, String param2) {
        TemplateDetailAndEditFragment fragment = new TemplateDetailAndEditFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_template_detail_and_edit, container, false);
    }

    @Override
    public void setAccounts(List<Account> accounts) {

    }

    @Override
    public void showTitle(String title) {

    }

    @Override
    public void showCost(String cost) {

    }

    @Override
    public void showNote(String note) {

    }

    @Override
    public void showReceiver(String receiver) {

    }

    @Override
    public void showDate(String date) {

    }

    @Override
    public void showTime(String time) {

    }

    @Override
    public void showTypeOfPayment(String typeOfPayment) {

    }

    @Override
    public void showCategory(String category) {

    }

    @Override
    public void showPlace(String address) {

    }

    @Override
    public void showAddition() {

    }

    @Override
    public void showExpenseType(String expenseType) {

    }

    @Override
    public void showAccount(int accountId) {

    }

    @Override
    public void showNoExpense() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void finish() {

    }

    @Override
    public void setPresenter(TemplateDetailAndEditContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
