package com.example.productmanagment.utils.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Created by Ivan on 24.01.2018.
 */

public interface BaseSchedulerProvider {
    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
