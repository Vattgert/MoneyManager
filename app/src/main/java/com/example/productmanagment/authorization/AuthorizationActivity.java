package com.example.productmanagment.authorization;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.R;

public class AuthorizationActivity extends AppCompatActivity {
    AuthorizationPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        AuthorizationFragment authorizationFragment = AuthorizationFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.authorizationContent, authorizationFragment).commit();

        presenter = new AuthorizationPresenter(authorizationFragment, this);
    }
}
