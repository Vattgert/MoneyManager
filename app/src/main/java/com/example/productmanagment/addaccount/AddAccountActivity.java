package com.example.productmanagment.addaccount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;

public class AddAccountActivity extends AppCompatActivity {
    AddAccountPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        setTitle("Створити рахунок");
        int groupId = getIntent().getExtras().getInt("group_id");
        AddAccountFragment fragment = AddAccountFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.addAccountContent, fragment).commit();
        presenter = new AddAccountPresenter(groupId, fragment,
                Injection.provideExpensesRepository(this),
                Injection.provideSchedulerProvider());
    }
}
