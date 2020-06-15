package com.mohamed.halim.essa.earthquake.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse;
import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;

import java.util.List;
@Dao
public interface EarthquakeDao {
    @Query("SELECT * FROM earthquake ORDER BY time ASC")
    LiveData<List<Earthquake>> getEarthquakes();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Earthquake> earthquakes);
    @Query("DELETE FROM earthquake")
    void deleteAll();
}
