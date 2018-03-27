package com.example.productmanagment;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.productmanagment.debts.DebtsContract;
import com.example.productmanagment.debts.DebtsFragment;
import com.example.productmanagment.debts.DebtsPresenter;
import com.example.productmanagment.expenses.ExpensesContract;
import com.example.productmanagment.expenses.ExpensesFragment;
import com.example.productmanagment.expenses.ExpensesPresenter;
import com.example.productmanagment.plannedpayment.PlannedPaymentContract;
import com.example.productmanagment.plannedpayment.PlannedPaymentFragment;
import com.example.productmanagment.plannedpayment.PlannedPaymentPresenter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentTransaction fragmentTransaction;
    Fragment view;
    BasePresenter presenter;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    if(view != null)
                        fragmentTransaction(view);
                }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_expenses:
                view = ExpensesFragment.newInstance();
                presenter = new ExpensesPresenter(
                        Injection.provideExpensesRepository(getApplicationContext()),
                        (ExpensesContract.View)view, Injection.provideSchedulerProvider()
                );
                break;
            case R.id.nav_debts:
                view = DebtsFragment.newInstance();
                presenter = new DebtsPresenter(
                        Injection.provideExpensesRepository(getApplicationContext()),
                        (DebtsContract.View)view, Injection.provideSchedulerProvider(),
                        getResources()
                );
                break;
            case R.id.nav_planned_payments:
                view = PlannedPaymentFragment.newInstance();
                presenter = new PlannedPaymentPresenter(
                        Injection.provideExpensesRepository(getApplicationContext()),
                        Injection.provideSchedulerProvider(), (PlannedPaymentContract.View)view
                );
                break;
            default:
                break;
        }

        drawer.closeDrawers();
        return true;
    }

    private void fragmentTransaction(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.viewContent, fragment).commit();
    }
}
