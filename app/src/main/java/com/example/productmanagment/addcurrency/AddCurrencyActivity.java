package com.example.productmanagment.addcurrency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;

public class AddCurrencyActivity extends AppCompatActivity {
    AddCurrencyPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_currency);

        int groupId = getIntent().getExtras().getInt("group_id");

        setTitle("Додати валюту");
        AddCurrencyFragment fragment = AddCurrencyFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.addCurrencyContent, fragment).commit();
        presenter = new AddCurrencyPresenter(groupId, fragment, Injection.provideExpensesRepository(getApplicationContext()), Injection.provideSchedulerProvider());
    }
}
