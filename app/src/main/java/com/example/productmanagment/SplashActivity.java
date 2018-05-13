package com.example.productmanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.authorization.AuthorizationActivity;
import com.example.productmanagment.data.source.users.UserSession;

public class SplashActivity extends AppCompatActivity {
    UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userSession = new UserSession(this);
        Intent intent;
        if(userSession.isLoggedIn()){
            intent = new Intent(this, MainActivity.class);
        }
        else {
            intent = new Intent(this, AuthorizationActivity.class);
        }
        startActivity(intent);
    }

}
