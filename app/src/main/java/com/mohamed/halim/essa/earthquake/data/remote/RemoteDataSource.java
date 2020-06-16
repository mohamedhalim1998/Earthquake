package com.mohamed.halim.essa.earthquake.data.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mohamed.halim.essa.earthquake.data.DataSource;
import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse;
import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;


import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * class to handle getting data from the api server
 */
public class RemoteDataSource implements DataSource {
    private static final String BASE_URL = "https://earthquake.usgs.gov/fdsnws/event/1/";
    // to handle the requests
    private ApiService apiService;
    // to store the earthquakes info
    private MutableLiveData<List<Earthquake>> earthquakes;
    private static RemoteDataSource INSTANCE;
    private Callback<EarthquakeResponse> callback;

    // singleton pattern to call the data
    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }

    // private constructor to prevent creating new objects
    private RemoteDataSource() {
        Gson gson = new GsonBuilder().create();
        this.apiService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(ApiService.class);
        earthquakes = new MutableLiveData<>();
    }

    /**
     * to start a network request to get the data
     *
     * @return a list of earthquakes wrapped in live data
     */
    @Override
    public LiveData<List<Earthquake>> getEarthquakes() {
        return earthquakes;
    }

    /**
     * load new data starting with the given offset
     *
     * @param offset : to start from
     */
    public void refreshData(int offset) {
        apiService.getEarthquakes(offset).enqueue(callback);
    }


    // setter methods
    public void setCallback(Callback<EarthquakeResponse> callback) {
        this.callback = callback;
    }

    public void setEarthquakes(List<Earthquake> earthquakes) {
        this.earthquakes.setValue(earthquakes);
    }
}
