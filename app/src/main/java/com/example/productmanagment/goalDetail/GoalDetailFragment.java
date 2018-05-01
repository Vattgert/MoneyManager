package com.example.productmanagment.goalDetail;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productmanagment.R;
import com.example.productmanagment.goalEdit.GoalEditActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalDetailFragment extends Fragment implements GoalDetailContract.View {
    GoalDetailContract.Presenter presenter;
    TextView goalTitleTextView, goalWantedDateTextView, goalProgressTextView, goalMinPerMonthTextView;
    EditText noteEditText;
    Button addAmountButton, makeReachedButton;
    CircularProgressIndicator progressIndicator;

    public GoalDetailFragment() {
        // Required empty public constructor
    }

    public static GoalDetailFragment newInstance() {
        GoalDetailFragment fragment = new GoalDetailFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goal_detail, container, false);
        setHasOptionsMenu(true);
        goalTitleTextView = view.findViewById(R.id.goalTitleDetailTextView);
        goalWantedDateTextView = view.findViewById(R.id.goalWantedDateDetailTextView);
        goalMinPerMonthTextView = view.findViewById(R.id.minSumPerMonthValueTextView);
        goalProgressTextView = view.findViewById(R.id.goalProgressTextView);
        noteEditText = view.findViewById(R.id.goalNoteDetailEditText);
        progressIndicator = view.findViewById(R.id.goalProgressBar);
        addAmountButton = view.findViewById(R.id.addGoalAmountButton);
        addAmountButton.setOnClickListener(__ -> presenter.openAddAmount());
        makeReachedButton = view.findViewById(R.id.makeGoalReachedButton);
        makeReachedButton.setOnClickListener(__->presenter.makeGoalReached());
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.goal_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                presenter.openGoalEdit();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void showGoalEdit(String goalId) {
        Intent intent = new Intent(getContext(), GoalEditActivity.class);
        intent.putExtra("goalId", goalId);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void setTitle(String title) {
        goalTitleTextView.setText(title);
    }

    @Override
    public void setWantedDate(String wantedDate) {
        String date = getResources().getString(R.string.goal_detail_wanted_date, wantedDate);
        goalWantedDateTextView.setText(date);
    }

    @Override
    public void setNeededAmount(double neededAmount) {
        progressIndicator.setMaxProgress((int)neededAmount);
    }

    @Override
    public void setAccumulatedAmount(double accumulatedAmount) {
        progressIndicator.setCurrentProgress((int)accumulatedAmount);
    }

    @Override
    public void setMinAmountPerMonth(String minAmountPerMonth) {
        goalMinPerMonthTextView.setText(minAmountPerMonth);
    }

    @Override
    public void setProgressText(String neededAmount, String accumulatedAmount) {
        String res = getResources().getString(R.string.goal_detail_progress, accumulatedAmount, neededAmount);
        goalProgressTextView.setText(res);
    }

    @Override
    public void setNote(String note) {
        String res = note;
        if(res == null)
            res = "Замітка";
        noteEditText.setText(res);
    }

    @Override
    public void setGoalButtonsGone() {
        makeReachedButton.setVisibility(View.GONE);
        addAmountButton.setVisibility(View.GONE);
    }

    @Override
    public void showFullAccumulatedAmount() {
        Toast.makeText(getContext(), "Сума повністю зібрана", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showGoalReached() {
        Toast.makeText(getContext(), "Ціль помічена як досягнена", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAddAmount() {
        showAddAmountDialog().show();
    }

    @Override
    public void setPresenter(GoalDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private Dialog showAddAmountDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_enter_goal_amount, null);
        builder.setTitle(getResources().getString(R.string.goal_dialog_enter_amount_title))
                .setView(view)
                .setPositiveButton(R.string.debt_enter_sum_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText amountEditText = view.findViewById(R.id.dialogGoalAmountEditText);
                        Double amount = Double.valueOf(amountEditText.getText().toString());
                        presenter.addAmount(amount);
                    }
                })
                .setNegativeButton(R.string.debt_enter_sum_reject, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }


}
