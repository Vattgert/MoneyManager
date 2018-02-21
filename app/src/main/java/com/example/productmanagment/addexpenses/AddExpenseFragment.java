package com.example.productmanagment.addexpenses;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.productmanagment.R;
import com.example.productmanagment.categories.CategoryActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AddExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddExpenseFragment extends Fragment implements AddExpenseContract.View {
    private AddExpenseContract.Presenter presenter;
    private EditText costTextView, noteEditText, categoryEditText, receiverEditText, dateEditText, timeEditText,
                     placeTextView, additionTextView;
    private ImageButton choosePlaceButton, makeAdditionButton;
    private Spinner typeOfPaymentSpinner;

    private TextView placeNameTextView;

    public AddExpenseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AddExpenseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddExpenseFragment newInstance() {
        AddExpenseFragment fragment = new AddExpenseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //TODO: Доделать чипсы (метки)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);
        setHasOptionsMenu(true);

        //costTextView = view.findViewById(R.id.)
        noteEditText = view.findViewById(R.id.noteAddEditText);
        dateEditText = view.findViewById(R.id.dateAddEditText);
        timeEditText = view.findViewById(R.id.timeAddEditText);
        receiverEditText = view.findViewById(R.id.receiverAddEditText);
        categoryEditText = view.findViewById(R.id.categoryAddEditText);
        typeOfPaymentSpinner = view.findViewById(R.id.typeOfPaymentSpinner);

        choosePlaceButton = view.findViewById(R.id.choosePlaceButton);

        placeNameTextView = view.findViewById(R.id.placeTextView);

        //dateEditText.setOnFocusChangeListener(editTextFocus);
        dateEditText.setOnClickListener(editTextClick);
        categoryEditText.setOnClickListener(editTextClick);
        timeEditText.setOnClickListener(editTextClick);

        choosePlaceButton.setOnClickListener(buttonClickListener);
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
        return true;
    }

    @Override
    public void setPresenter(AddExpenseContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setChosenCategory(String title) {
        categoryEditText.setText(title);
    }

    @Override
    public void showExpenses() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showChoosePlacePicker() {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this.getActivity()), AddExpenseActivity.REQUEST_PLACE_PICKER);
        } catch (GooglePlayServicesRepairableException e) {
            Log.wtf("google error", e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.wtf("google error", e.getMessage());
        }
    }

    @Override
    public void setChosenPlace(String place) {
        placeNameTextView.setText(place);
    }


    private DatePickerDialog getDatePickerDialog(){
        Calendar calendar = GregorianCalendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), datePickerListener, year, month, day);
    }

    private TimePickerDialog getTimePickerDialog(){
        return new TimePickerDialog(getActivity(), timePickerListener, 0, 0, true);
    }

    DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            SimpleDateFormat format = new SimpleDateFormat("dd MMM y", new Locale("ru"));
            Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
            dateEditText.setText(format.format(date));
        }
    };

    TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("k:mm");
            Date time = setCalendarTime(i, i1).getTime();
            timeEditText.setText(simpleDateFormat.format(time));
        }
    };

    View.OnClickListener editTextClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.dateAddEditText:
                    getDatePickerDialog().show();
                    break;
                case R.id.timeAddEditText:
                    getTimePickerDialog().show();
                    break;
                case R.id.categoryAddEditText:
                    Intent intent = new Intent(getContext(), CategoryActivity.class);
                    startActivityForResult(intent, CategoryActivity.GET_CATEGORY_REQUEST);
            }

        }
    };

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.choosePlaceButton:
                    presenter.choosePlace();
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.result(requestCode, resultCode, data);
    }

    private Calendar setCalendarDate(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar;
    }

    private Calendar setCalendarTime(int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }

    private void getDataAndSave(){
        double cost = 0.0; /*Double.valueOf(costTextView.getText().toString());*/
        String note = noteEditText.getText().toString();
        String category = categoryEditText.getText().toString();
        String receiver = receiverEditText.getText().toString();
        String place = ""; /*placeTextView.getText().toString();*/
        String date = dateEditText.getText().toString();
        String time = timeEditText.getText().toString();
        String typeOfPayment = typeOfPaymentSpinner.getSelectedItem().toString();
        String addition = "";
        String marks = "";
        presenter.saveExpense(cost, note, marks, receiver, date, time, typeOfPayment, place, addition, category);
    }
}
