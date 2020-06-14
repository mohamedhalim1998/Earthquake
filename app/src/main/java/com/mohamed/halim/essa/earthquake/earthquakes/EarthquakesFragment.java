package com.mohamed.halim.essa.earthquake.earthquakes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mohamed.halim.essa.earthquake.R;


public class EarthquakesFragment extends Fragment {

    public EarthquakesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_earthquakes, container, false);
    }
}
