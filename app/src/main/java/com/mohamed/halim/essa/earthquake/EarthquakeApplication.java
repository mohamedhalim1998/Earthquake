package com.mohamed.halim.essa.earthquake;

import android.app.Application;

import timber.log.Timber;

public class EarthquakeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
