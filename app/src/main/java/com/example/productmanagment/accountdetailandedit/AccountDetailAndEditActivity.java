package com.example.productmanagment.accountdetailandedit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;

public class AccountDetailAndEditActivity extends AppCompatActivity {
    AccountDetailAndEditPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail_and_edit);
        setTitle("Деталі рахунку");

        String accountId = getIntent().getExtras().getString("account_id");

        AccountDetailAndEditFragment accountDetailAndEditFragment = AccountDetailAndEditFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.detailAndEditAccountContent, accountDetailAndEditFragment).commit();

        Log.wtf("MyLog", "account id = " + accountId);

        presenter = new AccountDetailAndEditPresenter(accountId, accountDetailAndEditFragment,
                Injection.provideExpensesRepository(getApplicationContext()),
                Injection.provideSchedulerProvider());
    }
}
