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

public class Repository implements Callback<EarthquakeResponse> {
    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;
    private LiveData<List<Earthquake>> earthquakes;

    public Repository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        earthquakes = localDataSource.getEarthquakes();
        remoteDataSource.setCallback(this);
    }


    public LiveData<List<Earthquake>> getEarthquakes() {
        remoteDataSource.refreshData();
        return earthquakes;
    }


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
