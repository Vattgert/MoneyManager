package com.example.productmanagment.plannedpayment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;
import com.example.productmanagment.data.models.PlannedPayment;

public class PlannedPaymentActivity extends AppCompatActivity {
    private PlannedPaymentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_payment);

        PlannedPaymentFragment fragment = PlannedPaymentFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.plannedPaymentContent, fragment).commit();

        presenter = new PlannedPaymentPresenter(Injection.provideExpensesRepository(getApplicationContext()), Injection.provideSchedulerProvider(), fragment);
    }
}
