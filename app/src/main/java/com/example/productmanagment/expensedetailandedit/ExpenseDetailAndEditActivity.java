package com.example.productmanagment.expensedetailandedit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;

public class ExpenseDetailAndEditActivity extends AppCompatActivity {
    ExpenseDetailAndEditPresenter presenter;
    String expenseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail_and_edit);

        expenseId = String.valueOf(getIntent().getExtras().getInt("expenseId"));
        int groupId = getIntent().getExtras().getInt("groupId");

        Log.wtf("Tag", expenseId);
        setTitle("Деталі запису");

        ExpenseDetailAndEditFragment fragment = ExpenseDetailAndEditFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.detailExpenseContent, fragment).commit();
        presenter = new ExpenseDetailAndEditPresenter(groupId, expenseId, fragment, Injection.provideExpensesRepository(this), Injection.provideSchedulerProvider());
    }
}
