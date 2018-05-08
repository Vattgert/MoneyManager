package com.example.productmanagment.goalEdit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;

public class GoalEditActivity extends AppCompatActivity {
    GoalEditPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_edit);

        setTitle("Редагування цілі");
        String goalId = getIntent().getExtras().getString("goalId");

        GoalEditFragment goalEditFragment = GoalEditFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.editGoalContent, goalEditFragment).commit();

        presenter = new GoalEditPresenter(goalId, goalEditFragment,
                Injection.provideExpensesRepository(getApplicationContext()),
                Injection.provideSchedulerProvider());
    }
}
