package com.mohamed.halim.essa.earthquake.earthquakes;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mohamed.halim.essa.earthquake.data.Repository;

/**
 * to create a Earthquake fragment view model
 */
public class EarthquakesViewModelFactory implements ViewModelProvider.Factory {
    private Repository repository;

    public EarthquakesViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(EarthquakesViewModel.class)){
            return (T) new EarthquakesViewModel(repository);
        }
        throw new IllegalArgumentException();
    }
}
