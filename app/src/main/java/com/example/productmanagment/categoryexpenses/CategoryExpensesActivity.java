package com.example.productmanagment.categoryexpenses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;
import com.example.productmanagment.addaccount.AddAccountFragment;
import com.example.productmanagment.addaccount.AddAccountPresenter;

public class CategoryExpensesActivity extends AppCompatActivity {
    CategoryExpensesPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_expenses);

        setTitle("Витрати по категорії");
        int groupId = getIntent().getExtras().getInt("groupId");
        String category = getIntent().getExtras().getString("category");

        CategoryExpenseFragment fragment = CategoryExpenseFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.categoryExpensesContent, fragment).commit();
        presenter = new CategoryExpensesPresenter(groupId, category, fragment,
                Injection.provideExpensesRepository(this),
                Injection.provideSchedulerProvider());
    }
}
