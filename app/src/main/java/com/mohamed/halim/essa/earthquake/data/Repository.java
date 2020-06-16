package com.mohamed.halim.essa.earthquake.data;

import androidx.lifecycle.LiveData;

import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;
import com.mohamed.halim.essa.earthquake.data.local.LocalDataSource;
import com.mohamed.halim.essa.earthquake.data.remote.RemoteDataSource;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository to request the data from
 */
public class Repository implements Callback<EarthquakeResponse> {
    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;
    private LiveData<List<Earthquake>> earthquakes;
    private float magnitude;

    /**
     * create a new repo
     *
     * @param remoteDataSource : the remote data source
     * @param localDataSource  : the local data source
     */
    public Repository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        earthquakes = localDataSource.getEarthquakes();
        remoteDataSource.setCallback(this);
    }

    /**
     * getter for the data field
     *
     * @return list of earthquakes
     */
    public LiveData<List<Earthquake>> getEarthquakes() {
        remoteDataSource.refreshData(1, magnitude);
        return earthquakes;
    }

    /**
     * request more data from the server
     *
     * @param offset : to start the query from
     */
    public void updateDate(int offset, float magnitude) {
        if (magnitude != this.magnitude) {
            localDataSource.clearCache();
            this.magnitude = magnitude;
        }
        remoteDataSource.refreshData(offset, magnitude);
    }

    public float getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
    }

    // call backs for the remote data source
    @Override
    public void onResponse(@NotNull Call<EarthquakeResponse> call, @NotNull Response<EarthquakeResponse> response) {
        if (response.body() != null) {
            remoteDataSource.setEarthquakes(response.body().getEarthquakes());
            earthquakes = remoteDataSource.getEarthquakes();
            localDataSource.updateCache(response.body().getEarthquakes());
        }
    }

    @Override
    public void onFailure(@NotNull Call<EarthquakeResponse> call, @NotNull Throwable t) {
    }
}
