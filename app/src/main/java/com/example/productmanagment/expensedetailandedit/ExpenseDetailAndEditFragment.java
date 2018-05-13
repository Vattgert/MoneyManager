package com.example.productmanagment.expensedetailandedit;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.productmanagment.R;
import com.example.productmanagment.adapters.SimpleAccountSpinnerAdapter;
import com.example.productmanagment.addexpenses.AddExpenseActivity;
import com.example.productmanagment.categories.CategoryActivity;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.expenses.ExpensesActivity;
import com.example.productmanagment.utils.schedulers.UIUtils;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpenseDetailAndEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenseDetailAndEditFragment extends Fragment implements ExpenseDetailAndEditContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ExpenseDetailAndEditContract.Presenter presenter;
    private EditText costEditText, noteEditText, categoryEditText, receiverEditText,
    dateEditText, timeEditText;
    private Spinner typeOfPaymentSpinner, accountSpinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private ToggleSwitch expenseTypeToggleSwitch;

    private SimpleAccountSpinnerAdapter accountSpinnerAdapter;

    //TODO: Изменить весь модуль

    // TODO: Rename and change types of parameters

    //TODO: Добавить диалог на подтверждение выхода без сохранения при нажатии на back

    private String mParam1;

    public ExpenseDetailAndEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment ExpenseDetailAndEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpenseDetailAndEditFragment newInstance() {
        ExpenseDetailAndEditFragment fragment = new ExpenseDetailAndEditFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountSpinnerAdapter = new SimpleAccountSpinnerAdapter(getContext(),
                android.R.layout.simple_spinner_item, new ArrayList<>(0),
                android.R.layout.simple_spinner_dropdown_item);
        presenter.subscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_edit_fragment_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                editExpense();
                break;
            case R.id.action_delete:
                presenter.deleteTask();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_detail_and_edit, container, false);
        setHasOptionsMenu(true);

        costEditText = view.findViewById(R.id.costDetailEditText);
        noteEditText = view.findViewById(R.id.noteDetailEditText);
        receiverEditText = view.findViewById(R.id.receiverDetailEditText);
        dateEditText = view.findViewById(R.id.dateDetailEditText);
        timeEditText = view.findViewById(R.id.timeDetailEditText);
        categoryEditText = view.findViewById(R.id.categoryDetailEditText);
        typeOfPaymentSpinner = view.findViewById(R.id.typeOfPaymentDetailSpinner);
        accountSpinner = view.findViewById(R.id.accountDetailSpinner);

        spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.typeOfPayment, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfPaymentSpinner.setAdapter(spinnerAdapter);
        accountSpinner.setAdapter(accountSpinnerAdapter);

        expenseTypeToggleSwitch = view.findViewById(R.id.expenseTypeToggle);

        dateEditText.setOnClickListener(__ -> UIUtils.getDatePickerDialog(getContext(), (datePicker, i, i1, i2) -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("ru"));
            Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
            dateEditText.setText(format.format(date));
        }).show());

        categoryEditText.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), CategoryActivity.class);
            startActivityForResult(intent, CategoryActivity.GET_CATEGORY_REQUEST);
        });

        timeEditText.setOnClickListener(__ -> UIUtils.getTimePickerDialog(getContext(), (timePicker, i, i1) -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("k:mm");
            Date time = UIUtils.setCalendarTime(i, i1).getTime();
            timeEditText.setText(simpleDateFormat.format(time));
        }).show());
        return view;
    }

    @Override
    public void setPresenter(ExpenseDetailAndEditContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.result(requestCode, resultCode, data);
    }

    @Override
    public void setAccounts(List<Account> accounts) {
        accountSpinnerAdapter.setData(accounts);
    }

    @Override
    public void showCost(double cost) {
        costEditText.setText(String.valueOf(cost));
    }

    @Override
    public void showNote(String note) {
        noteEditText.setText(note);
    }

    @Override
    public void showReceiver(String receiver) {
        receiverEditText.setText(receiver);
    }

    @Override
    public void showDate(String date) {
        dateEditText.setText(date);
    }

    @Override
    public void showTime(String time) {
        timeEditText.setText(time);
    }

    @Override
    public void showTypeOfPayment(String typeOfPayment) {
        int position = spinnerAdapter.getPosition(typeOfPayment);
        typeOfPaymentSpinner.setSelection(position);
    }

    @Override
    public void showCategory(String category) {
        categoryEditText.setText(category);
    }

    @Override
    public void showPlace(String address) {

    }

    @Override
    public void showAddition() {

    }

    @Override
    public void showExpenseType(String expenseType) {
        if(expenseType.equals("Витрата"))
            expenseTypeToggleSwitch.setCheckedTogglePosition(0);
        else
            expenseTypeToggleSwitch.setCheckedTogglePosition(1);
    }

    @Override
    public void showAccount(int accountId) {
        accountSpinner.setSelection(accountSpinnerAdapter.getAccountPositionById(accountId));
    }

    @Override
    public void showNoExpense() {

    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void finish() {
        getActivity().finish();
    }

    private void editExpense(){
        double cost = Double.valueOf(costEditText.getText().toString());
        String note = noteEditText.getText().toString();
        String receiver = receiverEditText.getText().toString();
        String place = ""; /*placeTextView.getText().toString();*/
        String date = dateEditText.getText().toString();
        String time = timeEditText.getText().toString();
        String typeOfPayment = typeOfPaymentSpinner.getSelectedItem().toString();
        String expenseType = expenseTypeToggleSwitch.getCheckedTogglePosition() == 0 ? "Витрата" : "Дохід";
        Account account = accountSpinnerAdapter.getItem(accountSpinner.getSelectedItemPosition());
        Category category = presenter.getChosenCategory();
        Expense expense = new Expense(cost, expenseType, note,
                receiver, date, time, typeOfPayment, place, "", "", category, account, presenter.getCurrentUser());
        presenter.editExpense(expense);
    }
}
