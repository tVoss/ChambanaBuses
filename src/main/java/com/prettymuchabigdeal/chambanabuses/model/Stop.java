package com.prettymuchabigdeal.chambanabuses.model;

import com.orm.SugarRecord;

/**
 * Created by tyler on 1/31/15.
 */
public class Stop extends SugarRecord<Stop> {

    private String code;
    private String stopId;
    private float stopLat;
    private float stopLon;
    private String stopName;

    private boolean mFavorite;

    public String getStopName() {
        return stopName;
    }

    public float getStopLon() {
        return stopLon;
    }

    public float getStopLat() {
        return stopLat;
    }

    public String getStopID() {
        return stopId;
    }

    public String getCode() {
        return code;
    }

    public boolean isFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean favorite) {
        mFavorite = favorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stop stop = (Stop) o;

        if (Float.compare(stop.stopLat, stopLat) != 0) return false;
        if (Float.compare(stop.stopLon, stopLon) != 0) return false;
        if (code != null ? !code.equals(stop.code) : stop.code != null) return false;
        if (!stopId.equals(stop.stopId)) return false;
        if (stopName != null ? !stopName.equals(stop.stopName) : stop.stopName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + stopId.hashCode();
        result = 31 * result + (stopLat != +0.0f ? Float.floatToIntBits(stopLat) : 0);
        result = 31 * result + (stopLon != +0.0f ? Float.floatToIntBits(stopLon) : 0);
        result = 31 * result + (stopName != null ? stopName.hashCode() : 0);
        return result;
    }
}
