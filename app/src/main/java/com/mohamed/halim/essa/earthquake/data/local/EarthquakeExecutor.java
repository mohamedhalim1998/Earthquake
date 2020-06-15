package com.mohamed.halim.essa.earthquake.data.local;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EarthquakeExecutor {
    private static final Object LOCK = new Object();
    private static EarthquakeExecutor sInstance;
    private final Executor diskIO;


    private EarthquakeExecutor(Executor diskIO) {
        this.diskIO = diskIO;

    }

    public static EarthquakeExecutor getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new EarthquakeExecutor(Executors.newSingleThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor diskIO() {
        return diskIO;
    }
}
