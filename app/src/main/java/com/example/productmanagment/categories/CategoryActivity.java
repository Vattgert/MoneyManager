package com.example.productmanagment.categories;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;
import com.example.productmanagment.subcategories.SubcategoryFragment;
import com.example.productmanagment.subcategories.SubcategoryPresenter;
import com.example.productmanagment.utils.schedulers.interfaces.OnFragmentInteractionListener;

public class CategoryActivity extends AppCompatActivity implements OnFragmentInteractionListener{
    private CategoryPresenter categoryPresenter;
    private SubcategoryPresenter subcategoryPresenter;
    public static final int GET_CATEGORY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        CategoryFragment categoryFragment = CategoryFragment.newInstance();
        addFragment(categoryFragment);

        categoryPresenter = new CategoryPresenter(Injection.provideCategoriesRepository(getApplicationContext()), categoryFragment, Injection.provideSchedulerProvider());
    }

    private void addFragment(Fragment fragment){
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.categoryContent, fragment).commit();
    }

    private void addFragmentToBackStack(Fragment fragment){
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.categoryContent, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onFragmentInteraction(int id) {
        SubcategoryFragment fragment = SubcategoryFragment.newInstance(id);
        addFragmentToBackStack(fragment);
        subcategoryPresenter = new SubcategoryPresenter(id, Injection.provideCategoriesRepository(getApplicationContext()), fragment, Injection.provideSchedulerProvider());
    }
}
