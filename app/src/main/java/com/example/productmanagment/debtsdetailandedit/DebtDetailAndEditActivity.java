package com.example.productmanagment.debtsdetailandedit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;

public class DebtDetailAndEditActivity extends AppCompatActivity {
    DebtDetailAndEditPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_detail_and_edit);

        DebtDetailAndEditFragment debtsFragment = DebtDetailAndEditFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.detailDebtContent, debtsFragment).commit();

        presenter = new DebtDetailAndEditPresenter(
                getIntent().getIntExtra("debt_id", -1),
                debtsFragment, Injection.provideExpensesRepository(getApplicationContext()),
                Injection.provideSchedulerProvider()
        );
    }
}
