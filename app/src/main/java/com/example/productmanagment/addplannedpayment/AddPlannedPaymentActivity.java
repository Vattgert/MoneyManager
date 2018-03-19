package com.example.productmanagment.addplannedpayment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;

public class AddPlannedPaymentActivity extends AppCompatActivity {
    AddPlannedPaymentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_planned_payment);

        AddPlannedPaymentFragment addPlannedPaymentFragment = AddPlannedPaymentFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.addPlannedPaymentContent, addPlannedPaymentFragment).commit();

        presenter = new AddPlannedPaymentPresenter(Injection.provideExpensesRepository(getApplicationContext()), Injection.provideSchedulerProvider(), addPlannedPaymentFragment);
    }
}
