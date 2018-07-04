package com.example.productmanagment.adddebts;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.productmanagment.R;
import com.example.productmanagment.adapters.SimpleAccountSpinnerAdapter;
import com.example.productmanagment.categories.CategoryActivity;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Debt;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.data.models.Subcategory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddDebtFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDebtFragment extends Fragment implements AddDebtContract.View {
    AddDebtContract.Presenter presenter;
    EditText debtReceiverEditText, debtSumEditText, debtDescriptionEditText,
            borrowDateEditText, repayDateEditText;
    Spinner debtTypeSpinner, debtAccountSpinner;

    private SimpleAccountSpinnerAdapter accountSpinnerAdapter;

    public AddDebtFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddDebtFragment newInstance() {
        AddDebtFragment fragment = new AddDebtFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountSpinnerAdapter = new SimpleAccountSpinnerAdapter(getContext(),
                android.R.layout.simple_spinner_item, new ArrayList<>(0),
                android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_debt, container, false);
        setHasOptionsMenu(true);
        debtTypeSpinner = view.findViewById(R.id.debtTypeSpinner);
        debtReceiverEditText = view.findViewById(R.id.debtReceiverAddEditText);
        debtSumEditText = view.findViewById(R.id.debtSumAddEditText);
        debtDescriptionEditText = view.findViewById(R.id.debtDescriptionAddEditText);
        borrowDateEditText = view.findViewById(R.id.borrowDateAddEditText);
        repayDateEditText = view.findViewById(R.id.repayDateAddEditText);

        debtAccountSpinner = view.findViewById(R.id.accountAddSpinner);
        debtAccountSpinner.setAdapter(accountSpinnerAdapter);

        borrowDateEditText.setOnClickListener(editTextClick);
        repayDateEditText.setOnClickListener(editTextClick);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.result(requestCode, resultCode, data);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_fragment_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                getDataAndSave();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void setPresenter(AddDebtContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showDebts() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showAccounts(List<Account> accountList) {
        accountSpinnerAdapter.setData(accountList);
    }

    View.OnClickListener editTextClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.borrowDateAddEditText:
                    getDatePickerDialog(borrowDatePickerListener).show();
                    break;
                case R.id.repayDateAddEditText:
                    getDatePickerDialog(repayDatePickerListener).show();
            }
        }
    };

    private void getDataAndSave() {
        String sum = debtSumEditText.getText().toString();
        if(sum.equals(""))
            sum = "0.0";
        String receiver = debtReceiverEditText.getText().toString();
        int debtType;
        if (debtTypeSpinner.getSelectedItemPosition() == 0)
            debtType = 1;
        else
            debtType = 2;
        String description = debtDescriptionEditText.getText().toString();
        String borrowDate = borrowDateEditText.getText().toString();
        String repayDate = repayDateEditText.getText().toString();
        Account account = (Account) debtAccountSpinner.getSelectedItem();
        Debt debt = new Debt(sum, description, borrowDate, repayDate, receiver, debtType, account);
        Expense debtExpense = new Expense();
        debtExpense.setDebtId(debt.getId());
        debtExpense.setCost(Double.valueOf(debt.getSum()));
        debtExpense.setCategory(new Subcategory(82, null));
        if(debt.getDebtType() == 1) {
            debtExpense.setExpenseType("Дохід");
            debtExpense.setNote(getContext().getResources().getString(R.string.borrowed, debt.getBorrower()));
        }
        else {
            debtExpense.setExpenseType("Витрата");
            debtExpense.setNote(getContext().getResources().getString(R.string.lent, debt.getBorrower()));
        }
        debtExpense.setDate(getCurrentDate());
        debtExpense.setReceiver(debt.getBorrower());
        debtExpense.setAccount(debt.getAccount());
        presenter.saveDebtExpense(debtExpense);
        presenter.saveDebt(debt);
    }

    DatePickerDialog.OnDateSetListener borrowDatePickerListener = (datePicker, i, i1, i2) -> {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("ru"));
        Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
        borrowDateEditText.setText(format.format(date));
    };

    DatePickerDialog.OnDateSetListener repayDatePickerListener = (datePicker, i, i1, i2) -> {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("ru"));
        Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
        repayDateEditText.setText(format.format(date));
    };

    private DatePickerDialog getDatePickerDialog(DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = GregorianCalendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

    private String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
