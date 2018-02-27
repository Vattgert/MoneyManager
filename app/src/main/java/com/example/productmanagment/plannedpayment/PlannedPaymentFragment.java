package com.example.productmanagment.plannedpayment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.PlannedPayment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlannedPaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlannedPaymentFragment extends Fragment implements PlannedPaymentContract.View {

    public PlannedPaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlannedPaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlannedPaymentFragment newInstance(String param1, String param2) {
        PlannedPaymentFragment fragment = new PlannedPaymentFragment();
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
        return inflater.inflate(R.layout.fragment_planned_payment, container, false);
    }

    @Override
    public void setPresenter(PlannedPaymentContract.Presenter presenter) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showPlannedPayments(List<PlannedPayment> plannedPayments) {

    }

    @Override
    public void showAddPlannedPayment() {

    }

    @Override
    public void showLoadingPlannedPaymentsError() {

    }

    @Override
    public void showNoPlannedPayments() {

    }

    @Override
    public void showPlannedPaymentSuccessfullySavedMessage() {

    }
}
