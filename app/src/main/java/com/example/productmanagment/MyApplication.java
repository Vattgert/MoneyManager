package com.example.productmanagment;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Ivan on 23.01.2018.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
