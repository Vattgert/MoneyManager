package com.example.productmanagment.currencydetailandedit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;

public class CurrencyDetailAndEditActivity extends AppCompatActivity {
    CurrencyDetailAndEditPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_detail_and_edit);

        setTitle("Редагувати валюту");

        String currencyId = getIntent().getExtras().getString("currencyId");
        int groupId = getIntent().getExtras().getInt("groupId");

        CurrencyDetailAndEditFragment fragment = CurrencyDetailAndEditFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.currencyDetailAndEditContent, fragment).commit();
        presenter = new CurrencyDetailAndEditPresenter(groupId, currencyId, fragment,
                Injection.provideExpensesRepository(getApplicationContext()),
                Injection.provideSchedulerProvider());
    }
}
