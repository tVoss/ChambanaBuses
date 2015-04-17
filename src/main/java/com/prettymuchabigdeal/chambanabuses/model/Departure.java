package com.prettymuchabigdeal.chambanabuses.model;

import com.orm.SugarRecord;

/**
 * Created by tyler on 1/31/15.
 */
public class Departure extends SugarRecord<Departure> {

    public static final Departure NONE = new Departure() {

        @Override
        public int getExpectedMins() {
            return Integer.MIN_VALUE;
        }

        @Override
        public String getHeadsign() {
            return ":( :(";
        }

        @Override
        public Trip getTrip() {
            return Trip.NONE;
        }
    };

    private Stop destination;
    //private Date expected;
    private int expectedMins;
    private String headsign;
    private Location location;
    private boolean isMonitored;
    private boolean isScheduled;
    private boolean isIstop;
    private Stop origin;
    private Route route;
    //private Date scheduled;
    private String stopId;
    private Trip trip;
    private String vehicleId;

    public Stop getDestination() {
        return destination;
    }

    public int getExpectedMins() {
        return expectedMins;
    }

    public String getHeadsign() {
        return headsign;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isMonitored() {
        return isMonitored;
    }

    public boolean isScheduled() {
        return isScheduled;
    }

    public boolean isIstop() {
        return isIstop;
    }

    public Stop getOrigin() {
        return origin;
    }

    public Route getRoute() {
        return route;
    }

    public String getStopID() {
        return stopId;
    }

    public Trip getTrip() {
        return trip;
    }

    public String getVehicleID() {
        return vehicleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Departure departure = (Departure) o;

        if (isIstop != departure.isIstop) return false;
        if (isMonitored != departure.isMonitored) return false;
        if (isScheduled != departure.isScheduled) return false;
        if (!destination.equals(departure.destination)) return false;
        if (!headsign.equals(departure.headsign)) return false;
        if (!location.equals(departure.location)) return false;
        if (!origin.equals(departure.origin)) return false;
        if (!route.equals(departure.route)) return false;
        if (!stopId.equals(departure.stopId)) return false;
        if (!trip.equals(departure.trip)) return false;
        if (!vehicleId.equals(departure.vehicleId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = destination.hashCode();
        result = 31 * result + headsign.hashCode();
        result = 31 * result + location.hashCode();
        result = 31 * result + (isMonitored ? 1 : 0);
        result = 31 * result + (isScheduled ? 1 : 0);
        result = 31 * result + (isIstop ? 1 : 0);
        result = 31 * result + origin.hashCode();
        result = 31 * result + route.hashCode();
        result = 31 * result + stopId.hashCode();
        result = 31 * result + trip.hashCode();
        result = 31 * result + vehicleId.hashCode();
        return result;
    }
}
