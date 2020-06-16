package com.mohamed.halim.essa.earthquake.data.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.mohamed.halim.essa.earthquake.data.DataSource;
import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse;
import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;

import java.util.List;

/**
 * local data source to get when offline
 */
public class LocalDataSource implements DataSource {
    private EarthquakeDao database;

    public LocalDataSource(Context context) {
        this.database = EarthquakeDatabase.getInstance(context).earthquakeDao();
    }

    /**
     * to start a network request to get the data
     *
     * @return a list of earthquakes wrapped in live data
     */
    @Override
    public LiveData<List<Earthquake>> getEarthquakes() {

        return database.getEarthquakes();
    }

    /**
     * insert new data into room cahce
     *
     * @param earthquakes : to insert
     */
    public void updateCache(List<Earthquake> earthquakes) {
        EarthquakeExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.insertAll(earthquakes);
            }
        });
    }
}
