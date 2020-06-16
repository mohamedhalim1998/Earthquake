package com.mohamed.halim.essa.earthquake.data;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

/**
 * class to hold the json response date
 */
public class EarthquakeResponse {
    @SerializedName("features")
    private List<Earthquake> earthquakes;

    public EarthquakeResponse(List<Earthquake> features) {
        this.earthquakes = features;
    }

    public List<Earthquake> getEarthquakes() {
        return earthquakes;
    }

    @Entity(tableName = "earthquake")
    public static class Earthquake {
        @PrimaryKey
        @NonNull
        String id;

        @Embedded
        @SerializedName("properties")
        Properties properties;

        public Earthquake(String id, Properties properties) {
            this.id = id;
            this.properties = properties;
        }

        public String getId() {
            return id;
        }


        public Properties getProperties() {
            return properties;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Earthquake that = (Earthquake) o;
            return Objects.equals(id, that.id) &&
                    Objects.equals(properties, that.properties);
        }

        public static class Properties {
            @SerializedName("mag")
            float magnitude;
            String place;
            long time;
            String url;

            public Properties(float magnitude, String place, long time, String url) {
                this.magnitude = magnitude;
                this.place = place;
                this.time = time;
                this.url = url;

            }

            public float getMagnitude() {
                return magnitude;
            }

            public String getOffset() {
                int index = place.indexOf("of");
                if (index == -1) {
                    return "Near the";
                } else {
                    return place.substring(0, index + 2);
                }
            }

            public String getLocation() {
                int index = place.indexOf("of");
                if (index == -1) {
                    return place;
                } else {
                    return place.substring(index + 2);
                }
            }

            public long getTime() {
                return time;
            }

            public String getUrl() {
                return url;
            }

            public String getPlace() {
                return place;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Properties that = (Properties) o;
                return Float.compare(that.magnitude, magnitude) == 0 &&
                        time == that.time &&
                        Objects.equals(place, that.place) &&
                        Objects.equals(url, that.url);
            }
        }
    }
}
