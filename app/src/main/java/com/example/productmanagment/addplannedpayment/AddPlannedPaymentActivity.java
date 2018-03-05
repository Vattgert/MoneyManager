package com.example.productmanagment.addplannedpayment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.R;

public class AddPlannedPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_planned_payment);

        AddPlannedPaymentFragment expensesFragment = AddPlannedPaymentFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.addPlannedPaymentContent, expensesFragment).commit();
    }
}
