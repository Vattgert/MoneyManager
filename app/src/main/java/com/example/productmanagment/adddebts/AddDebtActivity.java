package com.example.productmanagment.adddebts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;

public class AddDebtActivity extends AppCompatActivity {
    public static final int ADD_DEBT_REQUEST = 1;

    AddDebtPresenter presenter;
    AddDebtFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debt);

        fragment = AddDebtFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.addDebtContent, fragment).commit();
        presenter = new AddDebtPresenter(fragment, Injection.provideExpensesRepository(this), getApplicationContext());

    }
}
