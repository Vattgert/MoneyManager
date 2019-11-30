package com.example.productmanagment.goalEdit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;

public class GoalEditActivity extends AppCompatActivity {
    int householdId = -1;
    GoalEditPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_edit);

        setTitle("Редагування цілі");
        String goalId = getIntent().getExtras().getString("goalId");
        householdId = Integer.valueOf(getIntent().getExtras().getString("householdId"));

        GoalEditFragment goalEditFragment = GoalEditFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.editGoalContent, goalEditFragment).commit();

        presenter = new GoalEditPresenter(householdId, goalId, goalEditFragment,
                Injection.provideExpensesRepository(getApplicationContext()), new RemoteDataRepository(),
                Injection.provideSchedulerProvider());
    }
}
