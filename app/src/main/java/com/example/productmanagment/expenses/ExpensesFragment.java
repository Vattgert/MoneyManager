package com.example.productmanagment.expenses;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.productmanagment.R;
import com.example.productmanagment.addexpenses.AddExpenseActivity;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.expensedetailandedit.ExpenseDetailAndEditActivity;

import java.util.ArrayList;
import java.util.List;


public class ExpensesFragment extends Fragment implements ExpensesContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ExpensesContract.Presenter presenter;
    private ExpensesAdapter expenseAdapter;

    public ExpensesFragment() {
    }

    public static ExpensesFragment newInstance() {
        ExpensesFragment fragment = new ExpensesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expenseAdapter = new ExpensesAdapter(new ArrayList<Expense>(0), itemListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton addExpenseButton = getActivity().findViewById(R.id.addExpenseButton);
        addExpenseButton.setOnClickListener(__ -> presenter.addNewExpense());
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_expenses, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.expensesRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(expenseAdapter);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.result(requestCode, resultCode);
    }

    @Override
    public void setPresenter(ExpensesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showExpenses(List<Expense> expenses) {
        expenseAdapter.setData(expenses);
    }

    @Override
    public void showAddExpense() {
        Intent intent = new Intent(getContext(), AddExpenseActivity.class);
        startActivityForResult(intent, AddExpenseActivity.REQUEST_ADD_EXPENSE);
    }

    @Override
    public void showLoadingExpensesError() {

    }

    @Override
    public void showNoExpenses() {

    }

    @Override
    public void showExpenseSuccessfullySavedMessage() {
        showMessage("Новый расход успешно добавлен");
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    ExpensesItemListener itemListener = new ExpensesItemListener() {
        @Override
        public void onExpenseClick(Expense clicked) {
            Intent intent = new Intent(getContext(), ExpenseDetailAndEditActivity.class);
            intent.putExtra("expenseId", clicked.getId());
            startActivity(intent);
        }
    };

    //TODO: Не отображать текстовые поля если нет информации
    public static class ExpensesAdapter extends RecyclerView.Adapter<ExpensesFragment.ExpensesAdapter.ViewHolder>{
        private List<Expense> expenseList;
        ExpensesFragment.ExpensesItemListener itemListener;

        public ExpensesAdapter(List<Expense> expenseList, ExpensesFragment.ExpensesItemListener itemListener) {
            this.expenseList = expenseList;
            this.itemListener = itemListener;
        }

        @Override
        public ExpensesFragment.ExpensesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_expense, parent, false);
            return new ExpensesFragment.ExpensesAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ExpensesFragment.ExpensesAdapter.ViewHolder holder, int position) {
            Expense expense = expenseList.get(position);
            holder.bind(expense);
        }

        @Override
        public int getItemCount() {
            return expenseList.size();
        }

        public void setData(List<Expense> expenseList){
            this.expenseList = expenseList;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            Expense expense;
            TextView categoryNameTextView, noteTextView, placeTextView, payTypeTextView
                    ,expenseTextView, receiverTextView, dateTextView;

            public ViewHolder(View view) {
                super(view);
                categoryNameTextView = view.findViewById(R.id.categoryNameTextView);
                noteTextView = view.findViewById(R.id.noteTextView);
                placeTextView = view.findViewById(R.id.placeTextView);
                payTypeTextView = view.findViewById(R.id.payTypeTextView);
                expenseTextView = view.findViewById(R.id.expenseTextView);
                receiverTextView = view.findViewById(R.id.receiverTextView);
                dateTextView = view.findViewById(R.id.dateTextView);

                if(itemListener != null)
                    view.setOnClickListener(__ -> itemListener.onExpenseClick(expense));
            }

            public void bind(Expense expense){
                this.expense = expense;
                ExpenseInformation information = expense.getExpenseInformation();
                categoryNameTextView.setText(expense.getCategory().getName());
                noteTextView.setText(information.getNote());
                receiverTextView.setText(information.getReceiver());
                placeTextView.setText(information.getPlace());
                payTypeTextView.setText(information.getTypeOfPayment());
                if(expense.getExpenseType() == 1)
                    expenseTextView.setTextColor(Color.RED);
                else if(expense.getExpenseType() == 2)
                    expenseTextView.setTextColor(Color.GREEN);
                expenseTextView.setText(String.valueOf(expense.getCost()));
                dateTextView.setText(information.getDate());
            }
        }
    }

    public interface ExpensesItemListener {

        void onExpenseClick(Expense clicked);

    }
}
