package com.prettymuchabigdeal.chambanabuses.model;

import com.orm.SugarRecord;

/**
 * Created by tyler on 1/31/15.
 */
public class Vehicle extends SugarRecord<Vehicle> {

    private String vehicleId;
    private Trip trip;
    private Location location;
    private String previousStopId;
    private String nextStopId;
    private String originStopId;
    private String destinationStopId;
    //private Date last_updated;

    public String getVehicleID() {
        return vehicleId;
    }

    public Trip getTrip() {
        return trip;
    }

    public Location getLocation() {
        return location;
    }

    public String getPreviousStopID() {
        return previousStopId;
    }

    public String getNextStopID() {
        return nextStopId;
    }

    public String getOriginStopID() {
        return originStopId;
    }

    public String getDestinationStopID() {
        return destinationStopId;
    }
}
