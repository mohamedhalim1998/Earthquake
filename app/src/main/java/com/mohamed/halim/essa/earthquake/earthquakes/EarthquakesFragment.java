package com.mohamed.halim.essa.earthquake.earthquakes;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mohamed.halim.essa.earthquake.R;
import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse;
import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;
import com.mohamed.halim.essa.earthquake.data.Repository;
import com.mohamed.halim.essa.earthquake.data.local.EarthquakeDatabase;
import com.mohamed.halim.essa.earthquake.data.local.EarthquakeExecutor;
import com.mohamed.halim.essa.earthquake.data.local.LocalDataSource;
import com.mohamed.halim.essa.earthquake.data.remote.RemoteDataSource;
import com.mohamed.halim.essa.earthquake.databinding.FragmentEarthquakesBinding;

import java.util.List;

import timber.log.Timber;


public class EarthquakesFragment extends Fragment {

    public EarthquakesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentEarthquakesBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_earthquakes, container, false);
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        LocalDataSource localDataSource = new LocalDataSource(requireContext());
        Repository repository = new Repository(remoteDataSource, localDataSource);
        EarthquakesViewModelFactory factory = new EarthquakesViewModelFactory(repository);
        EarthquakesViewModel viewModel = new ViewModelProvider(this, factory).get(EarthquakesViewModel.class);
        EarthquakesAdapter adapter = new EarthquakesAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        viewModel.getEarthquakes().observe(getViewLifecycleOwner(), new Observer<List<Earthquake>>() {
            @Override
            public void onChanged(List<Earthquake> earthquakes) {
                adapter.submitList(earthquakes);
            }
        });
        binding.earthquakesList.setAdapter(adapter);
        binding.earthquakesList.setLayoutManager(manager);
        binding.earthquakesList.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                viewModel.updateDate(page * 20 +1);
            }
        });
        return binding.getRoot();
    }
}
