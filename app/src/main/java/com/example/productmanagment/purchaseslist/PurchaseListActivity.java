package com.example.productmanagment.purchaseslist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;

public class PurchaseListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        PurchaseListFragment purchaseListFragment = PurchaseListFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.purchaseListContent, purchaseListFragment).commit();

        PurchaseListPresenter presenter = new PurchaseListPresenter(purchaseListFragment, Injection.provideExpensesRepository(getApplicationContext()), Injection.provideSchedulerProvider());
    }
}
