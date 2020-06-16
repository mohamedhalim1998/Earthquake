package com.mohamed.halim.essa.earthquake.earthquakes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mohamed.halim.essa.earthquake.R;
import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;
import com.mohamed.halim.essa.earthquake.data.Repository;
import com.mohamed.halim.essa.earthquake.data.local.LocalDataSource;
import com.mohamed.halim.essa.earthquake.data.remote.RemoteDataSource;
import com.mohamed.halim.essa.earthquake.databinding.FragmentEarthquakesBinding;


import java.util.List;

import timber.log.Timber;

import static androidx.navigation.fragment.NavHostFragment.findNavController;


public class EarthquakesFragment extends Fragment implements EarthquakesAdapter.ItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private Repository repository;
    private EarthquakesViewModel viewModel;
    private FragmentEarthquakesBinding binding;

    public EarthquakesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        PreferenceManager.getDefaultSharedPreferences(requireContext()).registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_earthquakes, container, false);
        binding.setLifecycleOwner(this);
        initRepo();


        EarthquakesViewModelFactory factory = new EarthquakesViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(EarthquakesViewModel.class);
        initRecycleView();
        return binding.getRoot();
    }

    private void initRecycleView() {
        EarthquakesAdapter adapter = new EarthquakesAdapter(this);
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
                int size = viewModel.getEarthquakes().getValue() != null ? viewModel.getEarthquakes().getValue().size() : 0;
                viewModel.updateDate(size + 1);
            }
        });
    }

    private void initRepo() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String value = sharedPreferences.getString(getString(R.string.magnitude_setting_key), "2.0");
        float mag = Float.parseFloat(value);
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        LocalDataSource localDataSource = new LocalDataSource(requireContext());
        repository = new Repository(remoteDataSource, localDataSource);
        repository.setMagnitude(mag);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                findNavController(this).
                        navigate(EarthquakesFragmentDirections.actionEarthquakesFragmentToPreferenceFragment());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(requireContext()).unregisterOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onItemClick(String url) {
        Timber.d(url);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        if (i.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(i);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String value = sharedPreferences.getString(key, "2.0");
        viewModel.updateMagnitude(Float.parseFloat(value));

    }
}
