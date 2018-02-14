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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.productmanagment.R;
import com.example.productmanagment.categories.CategoryActivity;

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
 * {@link AddExpenseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddExpenseFragment extends Fragment implements AddExpenseContract.View {
    // TODO: Rename parameter arguments, choose names that match

    private OnFragmentInteractionListener mListener;
    private AddExpenseContract.Presenter presenter;
    private EditText costTextView, noteEditText, categoryEditText, receiverEditText, dateEditText, timeEditText,
                     placeTextView, additionTextView;
    private Spinner typeOfPaymentSpinner;

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

        //dateEditText.setOnFocusChangeListener(editTextFocus);
        dateEditText.setOnClickListener(editTextClick);
        categoryEditText.setOnClickListener(editTextClick);
        timeEditText.setOnClickListener(editTextClick);
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
                showExpenses();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setPresenter(AddExpenseContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void saveExpense(double cost, String note, String marks, String category, String receiver, String date, String typeOfPayment, String place, String addition) {

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
