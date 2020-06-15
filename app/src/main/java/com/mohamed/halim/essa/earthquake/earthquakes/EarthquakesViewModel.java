package com.mohamed.halim.essa.earthquake.earthquakes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse;
import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;
import com.mohamed.halim.essa.earthquake.data.Repository;

import java.util.List;

public class EarthquakesViewModel extends ViewModel {
    private Repository repository;
    private LiveData<List<Earthquake>> earthquakes;
    public EarthquakesViewModel(Repository repository) {
        this.repository = repository;
        earthquakes = repository.getEarthquakes();
    }

    public LiveData<List<Earthquake>> getEarthquakes() {
        return earthquakes;
    }

    public void updateDate(int offset){
        repository.updateDate(offset);
    }

}
