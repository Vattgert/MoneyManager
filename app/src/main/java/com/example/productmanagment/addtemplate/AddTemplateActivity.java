package com.example.productmanagment.addtemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;

public class AddTemplateActivity extends AppCompatActivity {
    AddTemplateFragment fragment;
    AddTemplatePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template);
        setTitle("Додати шаблон");

        fragment = AddTemplateFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.addTemplateContent, fragment).commit();
        presenter = new AddTemplatePresenter(fragment, Injection.provideExpensesRepository(this), getApplicationContext(), Injection.provideSchedulerProvider());
    }
}
