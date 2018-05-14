package com.example.productmanagment.debtsdetailandedit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Debt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DebtDetailAndEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DebtDetailAndEditFragment extends Fragment implements DebtDetailAndEditContract.View {
    DebtDetailAndEditContract.Presenter presenter;
    EditText detailDebtTypeEditText, detailDebtReceiverEditText, detailDebtSumEditText,
            detailDebtDescriptionEditText, detailBorrowDateEditText, detailRepayDateEditText,
            detailAccountEditText;

    public DebtDetailAndEditFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DebtDetailAndEditFragment newInstance() {
        return new DebtDetailAndEditFragment();
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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_edit_fragment_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                editDebt();
                break;
            case R.id.action_delete:
                presenter.deleteDebt();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_debt_detail_and_edit, container, false);
        setHasOptionsMenu(true);
        detailDebtTypeEditText = view.findViewById(R.id.detailDebtTypeEditText);
        detailDebtReceiverEditText = view.findViewById(R.id.detailDebtReceiverEditText);
        detailDebtSumEditText = view.findViewById(R.id.detailDebtSumEditText);
        detailDebtDescriptionEditText = view.findViewById(R.id.detailDebtDescriptionEditText);
        detailBorrowDateEditText = view.findViewById(R.id.detailBorrowDateEditText);
        detailRepayDateEditText = view.findViewById(R.id.detailRepayDateEditText);
        detailAccountEditText = view.findViewById(R.id.detailAccountDebtEditText);
        return view;
    }

    @Override
    public void setPresenter(DebtDetailAndEditContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showCost(double cost) {
        detailDebtSumEditText.setText(String.valueOf(cost));
    }

    @Override
    public void showDebtType(int debtType) {
        if(debtType == 1)
            detailDebtTypeEditText.setText(getResources().getStringArray(R.array.debt_type)[0]);
        else if(debtType == 2)
            detailDebtTypeEditText.setText(getResources().getStringArray(R.array.debt_type)[1]);
    }

    @Override
    public void showReceiver(String receiver) {
        detailDebtReceiverEditText.setText(receiver);
    }

    @Override
    public void showBorrowDate(String date) {
        detailBorrowDateEditText.setText(date);
    }

    @Override
    public void showRepayDate(String date) {
        detailRepayDateEditText.setText(date);
    }

    @Override
    public void showAccount(String account) {
        detailAccountEditText.setText(account);
    }

    @Override
    public void showDescription(String description) {
        detailDebtDescriptionEditText.setText(description);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    private void editDebt(){
        String debtReceiver = detailDebtReceiverEditText.getText().toString();
        String debtDescription = detailDebtDescriptionEditText.getText().toString();
        Debt debt = new Debt();
        debt.setBorrower(debtReceiver);
        debt.setDescription(debtDescription);
        presenter.editDebt(debt);
    }
}
