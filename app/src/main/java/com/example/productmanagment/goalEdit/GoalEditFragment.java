package com.example.productmanagment.goalEdit;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.utils.schedulers.UIUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class GoalEditFragment extends Fragment implements GoalEditContract.View {
    GoalEditContract.Presenter presenter;
    EditText goalTitleEditText, goalNeededAmountEditText, goalAccumulatedAmountEditText,
            goalWantedDateEditText, goalNoteEditText;

    public static GoalEditFragment newInstance() {
        return new GoalEditFragment();
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
        View view = inflater.inflate(R.layout.fragment_goal_edit, container, false);
        setHasOptionsMenu(true);
        goalTitleEditText = view.findViewById(R.id.goalTitleEditEditText);
        goalNeededAmountEditText = view.findViewById(R.id.goalNeededAmountEditEditText);
        goalAccumulatedAmountEditText = view.findViewById(R.id.goalAccumulatedAmountEditEditText);
        goalWantedDateEditText = view.findViewById(R.id.goalWantedDateEditEditText);
        goalWantedDateEditText.setOnClickListener(__-> UIUtils.getDatePickerDialog(getContext(), onDateSetListener).show());
        goalNoteEditText = view.findViewById(R.id.goalNoteEditEditText);
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.goal_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_pause:
                if(presenter.getGoalState() == 1)
                    presenter.pauseGoal();
                else if(presenter.getGoalState() == 2)
                    presenter.activateGoal();
                break;
            case R.id.action_edit:
                getDataAndUpdate();
                break;
            case R.id.action_delete:
                presenter.deleteGoal();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void setGoalTitle(String title) {
        goalTitleEditText.setText(title);
    }

    @Override
    public void setGoalNeededSum(double neededSum) {
        goalNeededAmountEditText.setText(String.valueOf(neededSum));
    }

    @Override
    public void setGoalAccumulatedSum(double accumulatedSum) {
        goalAccumulatedAmountEditText.setText(String.valueOf(accumulatedSum));
    }

    @Override
    public void setGoalWantedDate(String wantedDate) {
        goalWantedDateEditText.setText(wantedDate);
    }

    @Override
    public void setGoalNote(String note) {
        goalNoteEditText.setText(note);
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }

    @Override
    public void showGoalPaused() {
        Toast.makeText(getContext(), "Ціль була призупинена", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showGoalActivated() {
        Toast.makeText(getContext(), "Ціль активна", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showGoalDeleted() {
        Toast.makeText(getContext(), "Ціль була видалена", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showGoalUpdated() {
        Toast.makeText(getContext(), "Ціль була змінена", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(GoalEditContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void getDataAndUpdate(){
        String title = goalTitleEditText.getText().toString();
        double neededAmount = 0.0;
        if(!goalNeededAmountEditText.getText().toString().equals(""))
            neededAmount = Double.valueOf(goalNeededAmountEditText.getText().toString());
        double accumulatedAmount = 0.0;
        if(!goalAccumulatedAmountEditText.getText().toString().equals(""))
            accumulatedAmount = Double.valueOf(goalAccumulatedAmountEditText.getText().toString());
        String wantedDate = goalWantedDateEditText.getText().toString();
        String note = goalNoteEditText.getText().toString();

        Goal goal = new Goal();
        goal.setTitle(title);
        goal.setNeededAmount(neededAmount);
        goal.setAccumulatedAmount(accumulatedAmount);
        goal.setWantedDate(wantedDate);
        goal.setNote(note);
        if(neededAmount > accumulatedAmount)
            presenter.updateGoal(goal);
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("ru"));
            Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
            goalWantedDateEditText.setText(format.format(date));
        }
    };
}
