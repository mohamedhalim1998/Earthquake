package com.mohamed.halim.essa.earthquake.earthquakes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
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

import java.sql.Time;
import java.util.List;

import timber.log.Timber;


public class EarthquakesFragment extends Fragment implements EarthquakesAdapter.ItemClickListener {

    private Repository repository;
    private EarthquakesViewModel viewModel;
    private FragmentEarthquakesBinding binding;

    public EarthquakesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_earthquakes, container, false);
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
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        LocalDataSource localDataSource = new LocalDataSource(requireContext());
        repository = new Repository(remoteDataSource, localDataSource);
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
}
