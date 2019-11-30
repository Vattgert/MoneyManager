package com.example.productmanagment.addgoal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;

public class AddGoalActivity extends AppCompatActivity {
    AddGoalPresenter presenter;
    int householdId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        setTitle("Створити ціль");
        householdId = Integer.valueOf(getIntent().getExtras().getString("householdId"));

        AddGoalFragment fragment = AddGoalFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.addGoalContent, fragment).commit();
        presenter = new AddGoalPresenter(householdId, fragment,
                Injection.provideExpensesRepository(getApplicationContext()),
                new RemoteDataRepository(),
                Injection.provideSchedulerProvider());
    }
}
