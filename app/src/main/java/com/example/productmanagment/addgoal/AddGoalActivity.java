package com.example.productmanagment.addgoal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;

public class AddGoalActivity extends AppCompatActivity {
    AddGoalPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        AddGoalFragment fragment = AddGoalFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.addGoalContent, fragment).commit();
        presenter = new AddGoalPresenter(fragment, Injection.provideExpensesRepository(getApplicationContext()));
    }
}
