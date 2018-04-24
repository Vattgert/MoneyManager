package com.example.productmanagment.basecurrency;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.MainActivity;
import com.example.productmanagment.R;
import com.example.productmanagment.data.models.BaseCurrency;

public class BaseCurrencyActivity extends AppCompatActivity {
    BaseCurrencyPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_currency);
        BaseCurrencyFragment fragment = BaseCurrencyFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.baseCurrencyContent, fragment).commit();
        presenter = new BaseCurrencyPresenter(fragment, Injection.provideExpensesRepository(getApplicationContext()));
    }
}
