package com.mohamed.halim.essa.earthquake.data.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.mohamed.halim.essa.earthquake.data.DataSource;
import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse;
import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;

import java.util.List;

public class LocalDataSource implements DataSource {
    private EarthquakeDao database;

    public LocalDataSource(Context context) {
        this.database = EarthquakeDatabase.getInstance(context).earthquakeDao();
    }

    @Override
    public LiveData<List<Earthquake>> getEarthquakes() {

        return database.getEarthquakes();
    }

    public void updateCache(List<Earthquake> earthquakes) {
        EarthquakeExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.insertAll(earthquakes);
            }
        });
    }
}
