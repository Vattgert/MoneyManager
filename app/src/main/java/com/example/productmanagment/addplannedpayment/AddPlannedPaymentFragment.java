package com.example.productmanagment.addplannedpayment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.codetroopers.betterpickers.expirationpicker.ExpirationPickerBuilder;
import com.example.productmanagment.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPlannedPaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPlannedPaymentFragment extends Fragment implements AddPlannedPaymentContract.View{
    EditText frequencySpinner;
    AddPlannedPaymentContract.Presenter presenter;


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
        ExpirationPickerBuilder epb = new ExpirationPickerBuilder()
                .setFragmentManager(getFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setMinYear(2000);
        epb.show();
    }

    @Override
    public void showChoosePlacePicker() {

    }
}


