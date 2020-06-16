package com.mohamed.halim.essa.earthquake.earthquakes;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;
import com.mohamed.halim.essa.earthquake.databinding.ListItemBinding;

/**
 * adapter class for the recycle view
 */
public class EarthquakesAdapter extends ListAdapter<Earthquake, EarthquakesAdapter.EarthquakeViewHolder> {
    private ItemClickListener itemClickListener;

    EarthquakesAdapter(ItemClickListener itemClickListener) {
        super(new EarthquakeDiffUtil());
        this.itemClickListener = itemClickListener;
    }

    /**
     * to create a new view holder
     */
    @NonNull
    @Override
    public EarthquakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding binding = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);

        return new EarthquakeViewHolder(binding);
    }

    /**
     * to bind data to the view
     */
    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, int position) {
        holder.binding.setEarthquake(getItem(position));
        holder.binding.setClickListener(itemClickListener);
    }

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    /**
     * inner class to create a view holder
     */
    static class EarthquakeViewHolder extends RecyclerView.ViewHolder {
        private ListItemBinding binding;

        EarthquakeViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /**
     * Diifutil calls to compare earthquake objects
     */
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
    public interface ItemClickListener {
        void onItemClick(String url);
    }
}
