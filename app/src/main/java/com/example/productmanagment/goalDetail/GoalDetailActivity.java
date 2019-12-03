package com.example.productmanagment.goalDetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.users.UserSession;

public class GoalDetailActivity extends AppCompatActivity {
    int householdId = -1;
    GoalDetailPresenter presenter;
    UserSession userSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);

        setTitle("Перегляд цілі");

        String goalId = getIntent().getExtras().getString("goalId");
        householdId = Integer.valueOf(getIntent().getExtras().getString("householdId"));
        userSession = new UserSession(this);

        GoalDetailFragment fragment = GoalDetailFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.goalDetailContent, fragment).commit();
        presenter = new GoalDetailPresenter(householdId, goalId, fragment, Injection.provideExpensesRepository(this),
                new RemoteDataRepository(), Injection.provideSchedulerProvider(), userSession);
    }
}
