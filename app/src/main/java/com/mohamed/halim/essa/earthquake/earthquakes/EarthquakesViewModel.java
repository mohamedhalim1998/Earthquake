package com.mohamed.halim.essa.earthquake.earthquakes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;
import com.mohamed.halim.essa.earthquake.data.Repository;

import java.util.List;

/**
 * view model for earthquake fragment
 */
public class EarthquakesViewModel extends ViewModel {
    private Repository repository;
    private LiveData<List<Earthquake>> earthquakes;
    private MutableLiveData<Float> magnitude;

    /**
     * crete a new view model
     *
     * @param repository : to ge tthe date from
     */
    EarthquakesViewModel(Repository repository) {
        this.repository = repository;
        earthquakes = repository.getEarthquakes();
        magnitude = new MutableLiveData<>();
        magnitude.setValue(2.0f);
    }

    /**
     * getter for the earthquake data
     */
    public LiveData<List<Earthquake>> getEarthquakes() {
        return earthquakes;
    }

    /**
     * request update from the repo
     */
    void updateDate(int offset) {
        repository.updateDate(offset, magnitude.getValue());
    }


    public LiveData<Float> getMagnitude() {
        return magnitude;
    }

    public void updateMagnitude(float mag){
        magnitude.setValue(mag);
        repository.updateDate(1, magnitude.getValue());
    }
}
