package com.example.productmanagment.addplannedpayment;


import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.codetroopers.betterpickers.expirationpicker.ExpirationPickerBuilder;
import com.codetroopers.betterpickers.recurrencepicker.EventRecurrence;
import com.codetroopers.betterpickers.recurrencepicker.EventRecurrenceFormatter;
import com.codetroopers.betterpickers.recurrencepicker.RecurrencePickerDialogFragment;
import com.example.productmanagment.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPlannedPaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPlannedPaymentFragment extends Fragment implements AddPlannedPaymentContract.View{
    EditText frequencySpinner, nameEditText, startDateEditText, timingEditText;
    AddPlannedPaymentContract.Presenter presenter;
    String mRrule;
    String totalFrequency = "";
    private EventRecurrence mEventRecurrence = new EventRecurrence();

    public AddPlannedPaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AddPlannedPaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPlannedPaymentFragment newInstance() {
        AddPlannedPaymentFragment fragment = new AddPlannedPaymentFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_planned_payment, container, false);
        frequencySpinner = view.findViewById(R.id.plannedPaymentFrequencySpinner);
        nameEditText = view.findViewById(R.id.plannedPaymentNameEditText);
        startDateEditText = view.findViewById(R.id.plannedPaymentStartDateEditText);
        frequencySpinner.setOnClickListener(listener);
        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.plannedPaymentFrequencySpinner:
                    presenter.chooseFrequency();
                    break;
            }
        }
    };

    @Override
    public void setPresenter(AddPlannedPaymentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setChosenCategory(String title) {

    }

    @Override
    public void setChosenPlace(String place) {

    }

    @Override
    public void showPlannedPayment() {

    }

    @Override
    public void showFrequencyDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Bundle bundle = new Bundle();
        Time time = new Time();
        time.setToNow();
        bundle.putLong(RecurrencePickerDialogFragment.BUNDLE_START_TIME_MILLIS, time.toMillis(false));
        bundle.putString(RecurrencePickerDialogFragment.BUNDLE_TIME_ZONE, time.timezone);
        bundle.putString(RecurrencePickerDialogFragment.BUNDLE_RRULE, mRrule);
        bundle.putBoolean(RecurrencePickerDialogFragment.BUNDLE_HIDE_SWITCH_BUTTON, true);

        RecurrencePickerDialogFragment rpd = new RecurrencePickerDialogFragment();
        rpd.setArguments(bundle);
        rpd.setOnRecurrenceSetListener(reccurenceListener);
        rpd.show(fm, "");
    }

    @Override
    public void showChoosePlacePicker() {

    }

    @Override
    public void populateRepeats() {
        Resources r = getResources();
        String repeatString = "";
        boolean enabled;
        if (!TextUtils.isEmpty(mRrule)) {
            repeatString = EventRecurrenceFormatter.getRepeatString(this.getContext(), r, mEventRecurrence, true);
        }


        Log.wtf("repeat", mRrule + "\n" + repeatString);
    }

    RecurrencePickerDialogFragment.OnRecurrenceSetListener reccurenceListener = new RecurrencePickerDialogFragment.OnRecurrenceSetListener() {
        @Override
        public void onRecurrenceSet(String rrule) {
            mRrule = rrule;
            if (mRrule != null) {
                mEventRecurrence.parse(mRrule);
            }
            presenter.getFrequency();
        }
    };

    private void getDataAndSavePlannedPayment(){
        String name = nameEditText.getText().toString();
        String startDate = startDateEditText.getText().toString();
        String timing = timingEditText.getText().toString();

        String endDate;
    }

    private String[] parseFrequency(){
        ArrayList<String> frequencyDescription = new ArrayList<>(Arrays.asList(mRrule.split(";")));
        ArrayList<String> desks = new ArrayList<>(Arrays.asList("FREQ=", "UNTIL=", "INTERVAL=", "BYDAY="));
        String[] result = new String[5];
        for(int i = 0; i < frequencyDescription.size(); i++){
            if(frequencyDescription.get(i).startsWith(desks.get(i))){
                result[i] = frequencyDescription.get(i).substring(desks.get(i).length());
                frequencyDescription.remove(i);
                desks.remove(i);
            }
        }

        return result;
    }
}


