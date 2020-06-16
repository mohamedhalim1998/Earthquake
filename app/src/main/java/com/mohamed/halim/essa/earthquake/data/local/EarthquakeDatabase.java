package com.mohamed.halim.essa.earthquake.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;

/**
 * abstract class to create the database
 */
@Database(entities = {Earthquake.class}, version = 1, exportSchema = false)
public abstract class EarthquakeDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "earthquakes";
    private static EarthquakeDatabase sInstance;

    /**
     * get an instance of the database class
     *
     * @param context : to get the database from
     * @return database instance
     */
    public static EarthquakeDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (new Object()) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        EarthquakeDatabase.class, EarthquakeDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    // to access database methods
    public abstract EarthquakeDao earthquakeDao();
}
