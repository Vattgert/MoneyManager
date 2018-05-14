package com.example.productmanagment;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.productmanagment.account.AccountContract;
import com.example.productmanagment.account.AccountFragment;
import com.example.productmanagment.account.AccountPresenter;
import com.example.productmanagment.adapters.SimpleAccountSpinnerAdapter;
import com.example.productmanagment.adapters.SimpleGroupSpinnerAdapter;
import com.example.productmanagment.currency.CurrencyFragment;
import com.example.productmanagment.currency.CurrencyPresenter;
import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.users.UserSession;
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
import com.example.productmanagment.main.MainFragment;
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
import com.example.productmanagment.userinfo.UserInfoActivity;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentTransaction fragmentTransaction;
    Fragment view;
    BasePresenter presenter;
    DrawerLayout drawer;
    TextView userEmailTextView;
    UserSession userSession;
    Spinner groupChoseSpinner;
    SimpleGroupSpinnerAdapter adapter;
    RemoteDataRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userSession = new UserSession(this);
        repository = new RemoteDataRepository();
        adapter = new SimpleGroupSpinnerAdapter(this, android.R.layout.simple_spinner_item,
                new ArrayList<>(Arrays.asList(new Group(-1, "Мій рахунок"))),
                android.R.layout.simple_spinner_dropdown_item);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getGroups(String.valueOf(userSession.getUserDetails().getUserId()));
            }

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
        MaterialLetterIcon userIcon = header.findViewById(R.id.userIconImageView);
        userIcon.setLetter(userSession.getUserDetails().getEmail().substring(0,3));
        userEmailTextView = header.findViewById(R.id.userEmailTextView);
        userEmailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                startActivity(intent);
            }
        });

        groupChoseSpinner = header.findViewById(R.id.groupChoseSpinner);
        groupChoseSpinner.setAdapter(adapter);
        groupChoseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Group group = adapter.getItem(i);
                if(group.getGroupId() > -1){
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.activity_main_remote_drawer);
                }
                else{
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.activity_main_drawer);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(userSession != null){
            userEmailTextView.setText(userSession.getUserDetails().getEmail());
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
        Log.wtf("MyLog", "drawer selected item id = " + id);
        Group group = (Group) groupChoseSpinner.getSelectedItem();
        switch (id){
            case R.id.nav_main:
                view = MainFragment.newInstance();
                break;
            case R.id.nav_accounts:
                view = AccountFragment.newInstance();
                presenter = new AccountPresenter(group.getGroupId(),
                        (AccountContract.View)view,
                        Injection.provideExpensesRepository(getApplicationContext()),
                        Injection.provideSchedulerProvider()
                );
                break;
            case R.id.nav_expenses:
                view = ExpensesFragment.newInstance();
                presenter = new ExpensesPresenter(group.getGroupId(),
                        Injection.provideExpensesRepository(getApplicationContext()),
                        (ExpensesContract.View)view, Injection.provideSchedulerProvider());
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
                view = DiagramFragment.newInstance();
                presenter = new DiagramPresenter(group.getGroupId(),
                        Injection.provideExpensesRepository(getApplicationContext()),
                        (DiagramContract.View)view,
                        Injection.provideSchedulerProvider()
                );
                break;
            case R.id.nav_groups:
                view = GroupsFragment.newInstance();
                presenter = new GroupsPresenter(new RemoteDataRepository(),
                        (GroupsContract.View)view,
                        Injection.provideSchedulerProvider(), new UserSession(this));
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
                        Injection.provideExpensesRepository(getApplicationContext()),
                        Injection.provideSchedulerProvider());
                break;
            case R.id.nav_purchase_list:
                view = PurchaseListFragment.newInstance();
                presenter = new PurchaseListPresenter((PurchaseListContract.View)view,
                        Injection.provideExpensesRepository(getApplicationContext()),
                        Injection.provideSchedulerProvider());
                break;
            default:
                view = MainFragment.newInstance();
                break;
        }

        drawer.closeDrawers();
        return true;
    }

    private void fragmentTransaction(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.viewContent, fragment).commit();
    }

    private void getGroups(String userId){
        BaseSchedulerProvider provider = Injection.provideSchedulerProvider();
        repository.getGroups(userId)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(groupsResponse -> {
                    List<Group> groups = groupsResponse.groupList;
                    adapter.setData(groups);
                });
    }


}
