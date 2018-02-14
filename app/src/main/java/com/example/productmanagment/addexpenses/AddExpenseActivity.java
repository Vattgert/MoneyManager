package com.example.productmanagment.addexpenses;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;

public class AddExpenseActivity extends AppCompatActivity implements AddExpenseFragment.OnFragmentInteractionListener{
    public static int REQUEST_ADD_EXPENSE = 1;
    AddExpensePresenter presenter;
    AddExpenseFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        fragment = AddExpenseFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.addExpenseContent, fragment).commit();
        presenter = new AddExpensePresenter(Injection.provideExpensesRepository(this), fragment, Injection.provideSchedulerProvider());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
