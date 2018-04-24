package com.example.productmanagment.addgoal;


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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddGoalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddGoalFragment extends Fragment implements AddGoalContract.View {
    AddGoalContract.Presenter presenter;
    EditText goalTitleEditText, goalNeededAmountEditText, goalAccumulatedAmountEditText,
            goalNoteEditText, goalWantedDateEditText;

    public AddGoalFragment() {
        // Required empty public constructor
    }

    public static AddGoalFragment newInstance() {
        return new AddGoalFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_goal, container, false);
        setHasOptionsMenu(true);
        goalTitleEditText = view.findViewById(R.id.goalTitleAddEditText);
        goalNeededAmountEditText = view.findViewById(R.id.goalNeededAmountEditText);
        goalAccumulatedAmountEditText = view.findViewById(R.id.goalAccumulatedAmountEditText);
        goalNoteEditText = view.findViewById(R.id.noteAddEditText);
        goalWantedDateEditText = view.findViewById(R.id.wantedDateAddEditText);
        goalWantedDateEditText.setOnClickListener(__ -> presenter.openDatePick());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_fragment_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                getDataAndSave();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void setAccumulatedAmountDefaultValue() {
        goalAccumulatedAmountEditText.setText("0.0");
    }

    @Override
    public void setNeededAmountDefaultValue() {
        goalNeededAmountEditText.setText("0.0");
    }

    @Override
    public void showDatePick() {
        UIUtils.getDatePickerDialog(getContext(), onDateSetListener).show();
    }

    @Override
    public void showGoalCreatedMessage() {
        Toast.makeText(getContext(), "Ціль була успішно створена", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRestrictedGoalMessage() {
        Toast.makeText(getContext(), "Зібрана сума більша ніж цільова сума", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(AddGoalContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void getDataAndSave(){
        String title = goalTitleEditText.getText().toString();
        double neededAmount = Double.valueOf(goalNeededAmountEditText.getText().toString());
        double accumulatedAmount = Double.valueOf(goalAccumulatedAmountEditText.getText().toString());
        String wantedDate = goalWantedDateEditText.getText().toString();
        int status = 1;
        String color = "";
        String icon = "";
        String note = goalNoteEditText.getText().toString();
        if(neededAmount > accumulatedAmount){
            Goal goal = new Goal(title, neededAmount, accumulatedAmount, wantedDate, note, color, icon, status);
            presenter.createGoal(goal);
        }
        else{
            showRestrictedGoalMessage();
        }
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
