package com.example.productmanagment.categoryexpenses;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.expenses.ExpensesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryExpenseFragment extends Fragment implements CategoryExpensesContract.View {
    private CategoryExpensesContract.Presenter presenter;
    private ExpensesFragment.ExpensesAdapter adapter;

    public CategoryExpenseFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CategoryExpenseFragment newInstance() {
        CategoryExpenseFragment fragment = new CategoryExpenseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ExpensesFragment.ExpensesAdapter(new ArrayList<>(0), null);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_expense, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.categoryExpensesRecyclerView);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration
                (new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void showExpenses(List<Expense> data) {
        adapter.setData(data);
    }

    @Override
    public void setPresenter(CategoryExpensesContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
