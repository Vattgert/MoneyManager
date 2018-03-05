package com.example.productmanagment.addplannedpayment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.productmanagment.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPlannedPaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPlannedPaymentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


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
        return inflater.inflate(R.layout.fragment_add_planned_payment, container, false);
    }

}
