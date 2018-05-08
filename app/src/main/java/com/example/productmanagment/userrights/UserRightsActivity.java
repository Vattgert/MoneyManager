package com.example.productmanagment.userrights;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;

public class UserRightsActivity extends AppCompatActivity {
    UserRightsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rights);

        String groupId = getIntent().getExtras().getString("group_id");
        String userId = getIntent().getExtras().getString("user_id");

        UserRightsFragment fragment = UserRightsFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.userRightsContent, fragment).commit();
        presenter = new UserRightsPresenter(groupId, userId, fragment,
                Injection.provideExpensesRepository(getApplicationContext()),
                Injection.provideSchedulerProvider());
    }
}
