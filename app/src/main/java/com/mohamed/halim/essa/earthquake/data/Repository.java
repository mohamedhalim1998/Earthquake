package com.mohamed.halim.essa.earthquake.data;

import androidx.lifecycle.LiveData;

import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;
import com.mohamed.halim.essa.earthquake.data.remote.RemoteDataSource;

import java.util.List;

public class Repository  {
    private RemoteDataSource remoteDataSource;
    private LiveData<List<Earthquake>> earthquakes;
    public Repository(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
        earthquakes = remoteDataSource.getEarthquakes();
    }


    public LiveData<List<Earthquake>> getEarthquakes() {
        return earthquakes;
    }


}
