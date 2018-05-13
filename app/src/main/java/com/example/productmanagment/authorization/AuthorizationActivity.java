package com.example.productmanagment.authorization;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.signup.SignUpFragment;
import com.example.productmanagment.signup.SignUpPresenter;

public class AuthorizationActivity extends AppCompatActivity implements AuthorizationFragment.OnSignUpClickListener{
    AuthorizationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        AuthorizationFragment authorizationFragment = AuthorizationFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.authorizationContent, authorizationFragment).commit();

        presenter = new AuthorizationPresenter(authorizationFragment, this, new RemoteDataRepository(), Injection.provideSchedulerProvider());
    }

    @Override
    public void onSignUpClick() {
        SignUpFragment fragment = SignUpFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.authorizationContent, fragment).addToBackStack(null).commit();

        SignUpPresenter presenter = new SignUpPresenter(fragment, new RemoteDataRepository(), Injection.provideSchedulerProvider());
    }
}
