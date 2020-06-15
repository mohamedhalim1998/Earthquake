package com.mohamed.halim.essa.earthquake.data;

import androidx.lifecycle.LiveData;

import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;

import java.util.List;

/**
 * interface to implement by the data sources local and remote
 */
public interface DataSource {
    LiveData<List<Earthquake>> getEarthquakes();

}
