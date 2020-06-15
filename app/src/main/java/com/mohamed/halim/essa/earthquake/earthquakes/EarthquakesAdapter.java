package com.mohamed.halim.essa.earthquake.earthquakes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;
import com.mohamed.halim.essa.earthquake.databinding.ListItemBinding;


public class EarthquakesAdapter extends ListAdapter<Earthquake, EarthquakesAdapter.EarthquakeViewHolder> {

    public EarthquakesAdapter() {
        super(new EarthquakeDiffUtil());
    }

    @NonNull
    @Override
    public EarthquakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding binding = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);

        return new EarthquakeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, int position) {
        holder.binding.setEarthquake(getItem(position));
    }

    class EarthquakeViewHolder extends RecyclerView.ViewHolder {
        private ListItemBinding binding;

        public EarthquakeViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    static class EarthquakeDiffUtil extends DiffUtil.ItemCallback<Earthquake> {


        @Override
        public boolean areItemsTheSame(@NonNull Earthquake oldItem, @NonNull Earthquake newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Earthquake oldItem, @NonNull Earthquake newItem) {
            return oldItem.equals(newItem);
        }
    }
}
