package com.example.productmanagment.debtpayments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.expenses.ExpensesFragment;
import com.example.productmanagment.utils.schedulers.interfaces.OnFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DebtPaymentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DebtPaymentsFragment extends Fragment implements DebtPaymentsContract.View {
    ExpensesFragment.ExpensesAdapter adapter;
    DebtPaymentsContract.Presenter presenter;
    private OnFragmentInteractionListener mListener;

    public DebtPaymentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment DebtPaymentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DebtPaymentsFragment newInstance() {
        DebtPaymentsFragment fragment = new DebtPaymentsFragment();
        return fragment;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ExpensesFragment.ExpensesAdapter(new ArrayList<Expense>(0), null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_debt_payments, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.debtPaymentsRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        //recyclerView.setClickable(false);
        return view;
    }

    @Override
    public void setPresenter(DebtPaymentsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showDebtPayments(List<Expense> debtPayments) {
        adapter.setData(debtPayments);
    }
}
