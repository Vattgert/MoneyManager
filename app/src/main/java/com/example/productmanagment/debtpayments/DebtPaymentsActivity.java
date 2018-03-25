package com.example.productmanagment.debtpayments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;

public class DebtPaymentsActivity extends AppCompatActivity {
    DebtPaymentsPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_payments);

        DebtPaymentsFragment debtPaymentsFragment = DebtPaymentsFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.debtPaymentsContent, debtPaymentsFragment).commit();

        presenter = new DebtPaymentsPresenter(getIntent().getIntExtra("debt_id", -1),
                debtPaymentsFragment, Injection.provideExpensesRepository(getApplicationContext()),
                Injection.provideSchedulerProvider());
        Log.wtf("debt", getIntent().getIntExtra("debt_id", -1) + "");
    }
}
