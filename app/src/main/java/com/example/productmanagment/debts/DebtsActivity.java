package com.example.productmanagment.debts;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;
import com.example.productmanagment.debtpayments.DebtPaymentsFragment;
import com.example.productmanagment.debtpayments.DebtPaymentsPresenter;
import com.example.productmanagment.utils.schedulers.interfaces.OnFragmentInteractionListener;

public class DebtsActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    DebtsPresenter presenter;
    DebtPaymentsPresenter debtPaymentsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debts);

        DebtsFragment debtsFragment = DebtsFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.debtsContent, debtsFragment).commit();

        presenter = new DebtsPresenter(Injection.provideExpensesRepository(getApplicationContext()), debtsFragment, Injection.provideSchedulerProvider(), getResources());
    }

    private void addFragmentToBackStack(Fragment fragment){
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.debtsContent, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onFragmentInteraction(int id) {
        DebtPaymentsFragment fragment = DebtPaymentsFragment.newInstance();
        addFragmentToBackStack(fragment);
        debtPaymentsPresenter = new DebtPaymentsPresenter(id, fragment, Injection.provideExpensesRepository(getApplicationContext()), Injection.provideSchedulerProvider());
    }
}
