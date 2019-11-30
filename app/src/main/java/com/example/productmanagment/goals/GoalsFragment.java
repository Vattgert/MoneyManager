package com.example.productmanagment.goals;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.productmanagment.R;
import com.example.productmanagment.addgoal.AddGoalActivity;
import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.debts.DebtsFragment;
import com.example.productmanagment.goalDetail.GoalDetailActivity;
import com.example.productmanagment.utils.schedulers.UIUtils;

import java.util.ArrayList;
import java.util.List;
public class GoalsFragment extends Fragment implements GoalsContract.View {
    GoalsContract.Presenter presenter;
    GoalsAdapter adapter;
    Spinner goalStateSpinner;

    public GoalsFragment() {
        // Required empty public constructor
    }

    public static GoalsFragment newInstance() {
        return new GoalsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new GoalsAdapter(new ArrayList<Goal>(0), listener);
        getActivity().setTitle("Цілі");
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
        View view = inflater.inflate(R.layout.fragment_goals, container, false);
        setHasOptionsMenu(true);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.addGoalButton);
        floatingActionButton.setOnClickListener(__ -> presenter.openAddGoal());
        RecyclerView recyclerView = view.findViewById(R.id.goalsRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    //TODO: Исправить загрузку при переключении
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.goal_state_spinner, menu);
        MenuItem item = menu.findItem(R.id.goal_state_spinner);
        goalStateSpinner = (Spinner) MenuItemCompat.getActionView(item);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.goal_state, R.layout.spinner_goal_states);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalStateSpinner.setAdapter(adapter);
        goalStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int position = i + 1;
                presenter.goalsLoading(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showGoals(List<Goal> goalList) {
        for(Goal goal : goalList){
            Log.wtf("MyLog", goal.getTitle());
        }
        adapter.setData(goalList);
    }

    @Override
    public void showAddGoal() {
        Intent intent = new Intent(getContext(), AddGoalActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAddGoalRemote(String householdId) {
        Intent intent = new Intent(getContext(), AddGoalActivity.class);
        intent.putExtra("householdId", householdId);
        startActivity(intent);
    }

    @Override
    public void showDetailGoal(String goalId) {
        Intent intent = new Intent(getContext(), GoalDetailActivity.class);
        intent.putExtra("goalId", goalId);
        startActivity(intent);
    }

    @Override
    public void showEditGoal() {

    }



    GoalsItemListener listener = new GoalsItemListener() {
        @Override
        public void onGoalClick(Goal clicked) {
            presenter.openDetailGoal(String.valueOf(clicked.getId()));
        }
    };

    @Override
    public void setPresenter(GoalsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private static class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder>{
        private List<Goal> goalsList;
        private GoalsItemListener itemListener;

        public GoalsAdapter(List<Goal> goalsList, GoalsItemListener itemListener) {
            this.goalsList = goalsList;
            this.itemListener = itemListener;
        }

        @Override
        public GoalsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_goal, parent, false);
            return new GoalsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GoalsAdapter.ViewHolder holder, int position) {
            Goal goal = goalsList.get(position);
            holder.bind(goal);
        }

        @Override
        public int getItemCount() {
            return goalsList.size();
        }

        public void setData(List<Goal> goalsList){
            this.goalsList = goalsList;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            Goal goal;
            TextView goalTitleTextView, goalNeededAmountTextView, goalAccumulatedAmountTextView;
            ProgressBar goalProgressBar;
            Resources resources;

            public ViewHolder(View view) {
                super(view);
                resources = view.getContext().getResources();
                goalTitleTextView = view.findViewById(R.id.goalTitleTextView);
                goalNeededAmountTextView = view.findViewById(R.id.neededGoalAmountTextView);
                goalAccumulatedAmountTextView = view.findViewById(R.id.accumulatedGoalAmountTextView);
                goalProgressBar = view.findViewById(R.id.goalProgressBar);
                view.setOnClickListener(__ -> itemListener.onGoalClick(goal));
            }

            public void bind(Goal goal){
                this.goal = goal;
                goalTitleTextView.setText(goal.getTitle());
                goalNeededAmountTextView.setText(resources.getString(R.string.goal_needed,
                        String.valueOf(goal.getNeededAmount()), goal.getCurrency().getSymbol()));
                goalAccumulatedAmountTextView.setText(resources.getString(R.string.goal_accumulated,
                        String.valueOf(goal.getAccumulatedAmount()), goal.getCurrency().getSymbol()));
                goalProgressBar.setMax((int)goal.getNeededAmount());
                goalProgressBar.setProgress((int)goal.getAccumulatedAmount());
            }
        }
    }

    public interface GoalsItemListener {
        void onGoalClick(Goal clicked);
    }
}
