package com.mohamed.halim.essa.earthquake.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mohamed.halim.essa.earthquake.data.EarthquakeResponse.Earthquake;

@Database(entities = {Earthquake.class}, version = 1, exportSchema = false)
public abstract class EarthquakeDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "earthquakes";
    private static EarthquakeDatabase sInstance;

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

    public abstract EarthquakeDao earthquakeDao();
}
