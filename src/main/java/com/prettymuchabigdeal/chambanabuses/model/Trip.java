package com.prettymuchabigdeal.chambanabuses.model;

import com.orm.SugarRecord;

/**
 * Created by tyler on 1/31/15.
 */
public class Trip extends SugarRecord<Trip> {

    public static final Trip NONE = new Trip() {
        @Override
        public String getTripHeadsign() {
            return "No Routes Found";
        }
    };

    public String tripId;
    public String tripHeadsign;
    public String routeId;
    public String blockId;
    public String direction;
    public String serviceId;
    public String shapeId;

    public String getShapeID() {
        return shapeId;
    }

    public String getServiceID() {
        return serviceId;
    }

    public String getDirection() {
        return direction;
    }

    public String getBlockID() {
        return blockId;
    }

    public String getRouteID() {
        return routeId;
    }

    public String getTripHeadsign() {
        return tripHeadsign;
    }

    public String getTripID() {
        return tripId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trip trip = (Trip) o;

        if (!blockId.equals(trip.blockId)) return false;
        if (!direction.equals(trip.direction)) return false;
        if (!routeId.equals(trip.routeId)) return false;
        if (!serviceId.equals(trip.serviceId)) return false;
        if (!shapeId.equals(trip.shapeId)) return false;
        if (!tripHeadsign.equals(trip.tripHeadsign)) return false;
        if (!tripId.equals(trip.tripId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tripId.hashCode();
        result = 31 * result + tripHeadsign.hashCode();
        result = 31 * result + routeId.hashCode();
        result = 31 * result + blockId.hashCode();
        result = 31 * result + direction.hashCode();
        result = 31 * result + serviceId.hashCode();
        result = 31 * result + shapeId.hashCode();
        return result;
    }
}
