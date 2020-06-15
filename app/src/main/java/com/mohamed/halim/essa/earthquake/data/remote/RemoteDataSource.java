package com.mohamed.halim.essa.earthquake.data.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mohamed.halim.essa.earthquake.data.DataSource;
import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse;
import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import timber.log.Timber;

/**
 * class to handle getting data from the api server
 */
public class RemoteDataSource implements DataSource {
    public static final String BASE_URL = "https://earthquake.usgs.gov/fdsnws/event/1/";
    // to handle the requests
    private ApiService apiService;
    // to store the earthquakes info
    private MutableLiveData<List<Earthquake>> earthquakes;
    private static RemoteDataSource INSTANCE;

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
     * @return a list of earthquakes wrapped in live data
     */
    @Override
    public LiveData<List<Earthquake>> getEarthquakes() {
        refreshData();
        return earthquakes;
    }

    public void refreshData() {
        apiService.getEarthquakes(1).enqueue(new Callback<EarthquakeResponse>() {
            @Override
            public void onResponse(@NotNull Call<EarthquakeResponse> call, @NotNull Response<EarthquakeResponse> response) {
                if (response.body() != null) {
                    earthquakes.setValue(response.body().getEarthquakes());
                }
            }

            @Override
            public void onFailure(@NotNull Call<EarthquakeResponse> call, @NotNull Throwable t) {

            }
        });
    }
}