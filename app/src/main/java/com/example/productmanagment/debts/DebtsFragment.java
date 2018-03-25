package com.example.productmanagment.debts;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productmanagment.R;
import com.example.productmanagment.adddebts.AddDebtActivity;
import com.example.productmanagment.data.models.Debt;
import com.example.productmanagment.debtpayments.DebtPaymentsActivity;
import com.example.productmanagment.debtsdetailandedit.DebtDetailAndEditActivity;
import com.example.productmanagment.utils.schedulers.interfaces.OnFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DebtsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DebtsFragment extends Fragment implements DebtsContract.View{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    DebtsContract.Presenter presenter;
    DebtsAdapter adapter;

    public DebtsFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static DebtsFragment newInstance() {
        DebtsFragment fragment = new DebtsFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton button = getActivity().findViewById(R.id.addDebtButton);
        button.setOnClickListener(view -> presenter.addNewDebt());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new DebtsAdapter(new ArrayList<>(0), itemListener);
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
        View view = inflater.inflate(R.layout.fragment_debts, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.debtsRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void setPresenter(DebtsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showDebts(List<Debt> debts) {
        adapter.setData(debts);
    }

    @Override
    public void showAddDebt() {
        Intent intent = new Intent(getActivity(), AddDebtActivity.class);
        startActivityForResult(intent, AddDebtActivity.ADD_DEBT_REQUEST);
    }

    @Override
    public void showDebtDetailAndEdit(int debtId) {
        Intent intent = new Intent(getActivity(), DebtDetailAndEditActivity.class);
        intent.putExtra("debt_id", debtId);
        startActivity(intent);
    }

    @Override
    public void showDebtPayments(int debtId) {
        Intent intent = new Intent(getActivity(), DebtPaymentsActivity.class);
        intent.putExtra("debt_id", debtId);
        startActivity(intent);
    }

    @Override
    public void showPayDebt(Debt debt) {
        showDebtDialog(debt).show();
    }

    @Override
    public void showEnterDebtSum(Debt debt) {
        showEnterSumDialog(debt).show();
    }

    @Override
    public void showLoadingExpensesError() {

    }

    @Override
    public void showNoDebts() {

    }

    @Override
    public void showDebtSuccessfullySavedMessage() {

    }

    @Override
    public void showDebtIsPayedMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private Dialog showDebtDialog(Debt debt){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.debt_pay_confirm_message)
                .setPositiveButton(R.string.debt_pay_full, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.closeDebt(debt);
                    }
                })
                .setNegativeButton(R.string.debt_pay_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                })
                .setNeutralButton(R.string.debt_pay_part, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.enterDebtPartSum(debt);
                    }
                });
        return builder.create();
    }

    private Dialog showEnterSumDialog(Debt debt){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_enter_debt_sum, null);
        builder.setTitle("Часткова оплата боргу")
                .setView(view)
                .setPositiveButton(R.string.debt_enter_sum_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText sumEditText = view.findViewById(R.id.debtSumEditText);
                        String sum = sumEditText.getText().toString();
                        presenter.closeDebtPart(debt, sum);
                    }
                })
                .setNegativeButton(R.string.debt_enter_sum_reject, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

    DebtsItemListener itemListener = new DebtsItemListener() {
        @Override
        public void onDebtClick(Debt clicked) {
            presenter.openDebtDetails(clicked);
        }

        @Override
        public void onMenuDebtClick(Debt clicked, View view) {
            PopupMenu popup = new PopupMenu(getContext(), view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.debt_actions, popup.getMenu());
            popup.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()){
                    case R.id.action_pay_debt:
                        if(Integer.valueOf(clicked.getRemain()) != 0)
                            presenter.payDebt(clicked);
                        else
                            presenter.debtIsPayedMessage();
                        break;
                    case R.id.action_show_debt_parts:
                        presenter.openDebtPayments(clicked.getId());
                        break;
                }
                return false;
            });
            popup.show();
        }
    };

    private static class DebtsAdapter extends RecyclerView.Adapter<DebtsFragment.DebtsAdapter.ViewHolder>{
        private List<Debt> debtList;
        DebtsFragment.DebtsItemListener itemListener;

        public DebtsAdapter(List<Debt> debtList, DebtsFragment.DebtsItemListener itemListener) {
            this.debtList = debtList;
            this.itemListener = itemListener;
        }

        @Override
        public DebtsFragment.DebtsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_debts, parent, false);
            return new DebtsFragment.DebtsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DebtsFragment.DebtsAdapter.ViewHolder holder, int position) {
            Debt debt = debtList.get(position);
            holder.bind(debt);
        }

        @Override
        public int getItemCount() {
            return debtList.size();
        }

        public void setData(List<Debt> debtList){
            this.debtList = debtList;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            Debt debt;
            TextView remainSumTextView, borrowerTextVew, borrowDateTextView, repayDateTypeTextView
                    ,descriptionTextView;
            ProgressBar debtProgressBar;
            ImageButton menuButton;
            Resources resources;

            public ViewHolder(View view) {
                super(view);
                remainSumTextView = view.findViewById(R.id.remainSumTextView);
                borrowerTextVew = view.findViewById(R.id.borrowerTextView);
                borrowDateTextView = view.findViewById(R.id.borrowDateTextView);
                repayDateTypeTextView = view.findViewById(R.id.repayDateTextView);
                descriptionTextView = view.findViewById(R.id.debtDescriptionTextView);
                debtProgressBar = view.findViewById(R.id.debtProgressBar);
                resources = view.getContext().getResources();
                menuButton = view.findViewById(R.id.debtMenuImageButton);
                menuButton.setOnClickListener(__ -> itemListener.onMenuDebtClick(debt, menuButton));
                view.setOnClickListener(__ -> itemListener.onDebtClick(debt));
            }

            public void bind(Debt debt){
                this.debt = debt;
                if(debt.getDebtType() == 1) {
                    borrowerTextVew.setText(resources.getString(R.string.borrowed, debt.getBorrower()));
                }
                else{
                    borrowerTextVew.setText(resources.getString(R.string.lent, debt.getBorrower()));
                }
                debtProgressBar.setMax(Integer.valueOf(debt.getSum()));
                if(debt.getRemain().equals("0")){
                    debtProgressBar.setProgress(Integer.valueOf(debt.getSum()));
                    remainSumTextView.setText(resources.getString(R.string.debt_repay));
                }
                else {
                    debtProgressBar.setProgress(Integer.valueOf(debt.getSum()) - Integer.valueOf(debt.getRemain()));
                    remainSumTextView.setText(resources.getString(R.string.debt_remain, debt.getRemain()));
                }
                descriptionTextView.setText(debt.getDescription());
                borrowDateTextView.setText(debt.getBorrowDate());
                repayDateTypeTextView.setText(resources.getString(R.string.debt_pay_date, debt.getRepayDate()));
            }
        }
    }

    public interface DebtsItemListener {

        void onDebtClick(Debt clicked);
        void onMenuDebtClick(Debt clicked, View view);
    }

}
