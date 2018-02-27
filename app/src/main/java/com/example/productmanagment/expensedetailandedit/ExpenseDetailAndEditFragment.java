package com.example.productmanagment.expensedetailandedit;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.expenses.ExpensesActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpenseDetailAndEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenseDetailAndEditFragment extends Fragment implements ExpenseDetailAndEditContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ExpenseDetailAndEditContract.Presenter presenter;
    private EditText costEditText, noteEditText, categoryEditText, receiverEditText,
    dateEditText, timeEditText;
    private Spinner typeOfPaymentSpinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    // TODO: Rename and change types of parameters

    //TODO: Добавить диалог на подтверждение выхода без сохранения при нажатии на back

    private String mParam1;

    public ExpenseDetailAndEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment ExpenseDetailAndEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpenseDetailAndEditFragment newInstance() {
        ExpenseDetailAndEditFragment fragment = new ExpenseDetailAndEditFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                editExpense();
                break;
            case R.id.action_delete:
                presenter.deleteTask();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_detail_and_edit, container, false);
        setHasOptionsMenu(true);

        costEditText = view.findViewById(R.id.costDetailEditText);
        noteEditText = view.findViewById(R.id.noteDetailEditText);
        receiverEditText = view.findViewById(R.id.receiverDetailEditText);
        dateEditText = view.findViewById(R.id.dateDetailEditText);
        timeEditText = view.findViewById(R.id.timeDetailEditText);
        categoryEditText = view.findViewById(R.id.categoryDetailEditText);
        typeOfPaymentSpinner = view.findViewById(R.id.typeOfPaymentDetailSpinner);

        spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.typeOfPayment, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfPaymentSpinner.setAdapter(spinnerAdapter);

        return view;
    }

    @Override
    public void setPresenter(ExpenseDetailAndEditContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showCost(double cost) {
        costEditText.setText(String.valueOf(cost));
    }

    @Override
    public void showNote(String note) {
        noteEditText.setText(note);
    }

    @Override
    public void showMarks() {

    }

    @Override
    public void showReceiver(String receiver) {
        receiverEditText.setText(receiver);
    }

    @Override
    public void showDate(String date) {
        dateEditText.setText(date);
    }

    @Override
    public void showTime(String time) {
        timeEditText.setText(time);
    }

    @Override
    public void showTypeOfPayment(String typeOfPayment) {
        int position = spinnerAdapter.getPosition(typeOfPayment);
        typeOfPaymentSpinner.setSelection(position);
    }

    @Override
    public void showCategory(String category) {
        categoryEditText.setText(category);
    }

    @Override
    public void showPlace(String address) {

    }

    @Override
    public void showAddition() {

    }

    @Override
    public void showNoExpense() {

    }


    @Override
    public void finish() {
        getActivity().finish();
    }

    private void editExpense(){
        double cost = 100.0;
        String note = noteEditText.getText().toString();
        //TODO: Method in local datasource to get category obj by name
        Category category = null;
        String receiver = receiverEditText.getText().toString();
        String place = ""; /*placeTextView.getText().toString();*/
        String date = dateEditText.getText().toString();
        String time = timeEditText.getText().toString();
        String typeOfPayment = typeOfPaymentSpinner.getSelectedItem().toString();
        String addition = "";
        String marks = "";
        ExpenseInformation information = new ExpenseInformation(note, marks, receiver, date, time,
                typeOfPayment, place, addition);
        presenter.editExpense(cost,  category, information);
    }
}
