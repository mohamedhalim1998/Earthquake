package com.mohamed.halim.essa.earthquake.data.remote;

import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * interface to handle api requests using retrofit
 */
public interface ApiService {
    // to get the json response
    @GET("query?format=geojson&minmagnitude=2&limit=20")
    Call<EarthquakeResponse> getEarthquakes(@Query("offset") int offset);
}
