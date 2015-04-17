package com.prettymuchabigdeal.chambanabuses.model;

import com.orm.SugarRecord;

/**
 * Created by tyler on 1/31/15.
 */
public class Location extends SugarRecord<Location> {

    private float lat;
    private float lon;

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (Float.compare(location.lat, lat) != 0) return false;
        if (Float.compare(location.lon, lon) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (lat != +0.0f ? Float.floatToIntBits(lat) : 0);
        result = 31 * result + (lon != +0.0f ? Float.floatToIntBits(lon) : 0);
        return result;
    }
}
