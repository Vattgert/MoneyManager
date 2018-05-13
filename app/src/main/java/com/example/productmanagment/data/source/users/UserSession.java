package com.example.productmanagment.data.source.users;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.productmanagment.authorization.AuthorizationActivity;
import com.example.productmanagment.data.models.User;

public class UserSession {
    int privateMode = 0;
    private final static String PREFERENCES_NAME = "user_session";
    private final static String IS_LOGGED_IN = "is_logged_in";
    private final static String USER_ID = "id_user";
    private final static String USER_EMAIL = "user_email";
    private final static String USER_LOGIN = "user_login";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    public UserSession(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCES_NAME, privateMode);
        editor = preferences.edit();
    }



    public void createLoginSession(User user){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putInt(USER_ID, user.getUserId());
        editor.putString(USER_EMAIL, user.getEmail());
        editor.putString(USER_LOGIN, user.getLogin());
        editor.commit();
    }

    public User getUserDetails(){
        User user = new User();
        user.setUserId(preferences.getInt(USER_ID, -1));
        user.setEmail(preferences.getString(USER_EMAIL, null));
        user.setLogin(preferences.getString(USER_LOGIN, null));
        return user;
    }

    public void isLogIn(){
        if(!isLoggedIn()){
            Intent intent = new Intent(context, AuthorizationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, AuthorizationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public boolean isLoggedIn(){
        return preferences.getBoolean(IS_LOGGED_IN, false);
    }
}
