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

/**
 * interface to interact with the local database
 */
@Dao
public interface EarthquakeDao {
    // get all data from db
    @Query("SELECT * FROM earthquake ORDER BY time DESC")
    LiveData<List<Earthquake>> getEarthquakes();

    // insert a list of earthquake into db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Earthquake> earthquakes);

    // delete all data
    @Query("DELETE FROM earthquake")
    void deleteAll();
}
