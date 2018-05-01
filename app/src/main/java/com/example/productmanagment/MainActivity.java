package com.example.productmanagment;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.productmanagment.account.AccountContract;
import com.example.productmanagment.account.AccountFragment;
import com.example.productmanagment.account.AccountPresenter;
import com.example.productmanagment.currency.CurrencyFragment;
import com.example.productmanagment.currency.CurrencyPresenter;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.debts.DebtsContract;
import com.example.productmanagment.debts.DebtsFragment;
import com.example.productmanagment.debts.DebtsPresenter;
import com.example.productmanagment.diagrams.DiagramContract;
import com.example.productmanagment.diagrams.DiagramFragment;
import com.example.productmanagment.diagrams.DiagramPresenter;
import com.example.productmanagment.expenses.ExpensesContract;
import com.example.productmanagment.expenses.ExpensesFragment;
import com.example.productmanagment.expenses.ExpensesPresenter;
import com.example.productmanagment.goals.GoalsContract;
import com.example.productmanagment.goals.GoalsFragment;
import com.example.productmanagment.goals.GoalsPresenter;
import com.example.productmanagment.groups.GroupsContract;
import com.example.productmanagment.groups.GroupsFragment;
import com.example.productmanagment.groups.GroupsPresenter;
import com.example.productmanagment.places.PlacesContract;
import com.example.productmanagment.places.PlacesFragment;
import com.example.productmanagment.places.PlacesPresenter;
import com.example.productmanagment.plannedpayment.PlannedPaymentContract;
import com.example.productmanagment.plannedpayment.PlannedPaymentFragment;
import com.example.productmanagment.plannedpayment.PlannedPaymentPresenter;
import com.example.productmanagment.purchaseslist.PurchaseListContract;
import com.example.productmanagment.purchaseslist.PurchaseListFragment;
import com.example.productmanagment.purchaseslist.PurchaseListPresenter;
import com.example.productmanagment.report.incomesandexpenses.IncomesAndExpensesContract;
import com.example.productmanagment.report.incomesandexpenses.IncomesAndExpensesFragment;
import com.example.productmanagment.report.incomesandexpenses.IncomesAndExpensesPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentTransaction fragmentTransaction;
    Fragment view;
    BasePresenter presenter;
    DrawerLayout drawer;
    FirebaseAuth auth;
    TextView userEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        auth = FirebaseAuth.getInstance();
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
        View header = navigationView.getHeaderView(0);
        userEmailTextView = header.findViewById(R.id.userEmailTextView);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference(".info/connected");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Log.wtf("MyLog", "connected");
                } else {
                    Log.wtf("MyLog", "not connected");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.wtf("MyLog", "listener wasnt called");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            userEmailTextView.setText(currentUser.getEmail());
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_accounts:
                view = AccountFragment.newInstance();
                presenter = new AccountPresenter(
                        (AccountContract.View)view,
                        Injection.provideExpensesRepository(getApplicationContext()),
                        Injection.provideSchedulerProvider()
                );
                break;
            case R.id.nav_expenses:
                view = ExpensesFragment.newInstance();
                presenter = new ExpensesPresenter(
                        Injection.provideExpensesRepository(getApplicationContext()),
                        (ExpensesContract.View)view, Injection.provideSchedulerProvider()
                );
                break;
            case R.id.nav_goals:
                view = GoalsFragment.newInstance();
                presenter = new GoalsPresenter(
                        (GoalsContract.View)view,
                        Injection.provideExpensesRepository(getApplicationContext()),
                        Injection.provideSchedulerProvider()
                );
                break;
            case R.id.nav_budgets:
                view = PlacesFragment.newInstance();
                presenter = new PlacesPresenter(
                        (PlacesContract.View)view,
                        Injection.provideExpensesRepository(getApplicationContext()),
                        Injection.provideSchedulerProvider()
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
            case R.id.nav_diagrams:
                view = DiagramFragment.newInstance(DiagramFragment.EXPENSES_BY_CATEGORY);
                presenter = new DiagramPresenter(DiagramFragment.EXPENSES_BY_CATEGORY,
                        Injection.provideExpensesRepository(getApplicationContext()),
                        (DiagramContract.View)view,
                        Injection.provideSchedulerProvider()
                );
                break;
            case R.id.nav_groups:
                view = GroupsFragment.newInstance();
                presenter = new GroupsPresenter(new RemoteDataRepository(),
                        (GroupsContract.View)view,
                        Injection.provideSchedulerProvider());
                break;
            case R.id.nav_currencies:
                view = CurrencyFragment.newInstance();
                presenter = new CurrencyPresenter((CurrencyFragment)view,
                        Injection.provideExpensesRepository(getApplicationContext()),
                        Injection.provideSchedulerProvider());
                break;
            case R.id.nav_report:
                view = IncomesAndExpensesFragment.newInstance();
                presenter = new IncomesAndExpensesPresenter((IncomesAndExpensesContract.View)view,
                        Injection.provideCategoriesRepository(getApplicationContext()),
                        Injection.provideSchedulerProvider());
            case R.id.nav_purchase_list:
                view = PurchaseListFragment.newInstance();
                presenter = new PurchaseListPresenter((PurchaseListContract.View)view,
                        Injection.provideExpensesRepository(getApplicationContext()),
                        Injection.provideSchedulerProvider());
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

    //TODO: Сделать фильтрацию и сортировку даных
    //TODO: Поменять формат даты на склайтовский
}
