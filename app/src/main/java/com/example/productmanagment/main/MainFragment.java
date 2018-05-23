package com.example.productmanagment.main;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.expenses.ExpensesFragment;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment implements MainContract.View{
    MainContract.Presenter presenter;

    TextView balanceTextView;
    RecyclerView lastRecordsRecyclerView;

    ExpensesFragment.ExpensesAdapter adapter;

    public MainFragment() {

    }

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Головна");
        adapter = new ExpensesFragment.ExpensesAdapter(new ArrayList<>(0), null);
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        balanceTextView = view.findViewById(R.id.balanceTextView);
        lastRecordsRecyclerView = view.findViewById(R.id.lastRecordsRecyclerView);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lastRecordsRecyclerView.addItemDecoration
                (new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        lastRecordsRecyclerView.setLayoutManager(mLayoutManager);
        lastRecordsRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void setBalance(String balance, int color) {
        balanceTextView.setTextColor(color);
        balanceTextView.setText(balance);
    }

    @Override
    public void setAccountData(List<Account> data) {

    }

    @Override
    public void setLastRecords(List<Expense> lastRecords) {
        adapter.setData(lastRecords);
    }

    @Override
    public void setLastDataDiagram() {

    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
