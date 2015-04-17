package com.prettymuchabigdeal.chambanabuses.model;

import com.orm.SugarRecord;

/**
 * Created by tyler on 1/31/15.
 */
public class Route extends SugarRecord<Route> {

    private String routeId;
    private String routeColor;
    private String routeLongName;
    private String routeShortName;
    private String routeTextColor;

    public String getRouteTextColor() {
        return routeTextColor;
    }

    public String getRouteShortName() {
        return routeShortName;
    }

    public String getRouteLongName() {
        return routeLongName;
    }

    public String getRouteID() {
        return routeId;
    }

    public String getRouteColor() {
        return routeColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (!routeColor.equals(route.routeColor)) return false;
        if (!routeId.equals(route.routeId)) return false;
        if (!routeLongName.equals(route.routeLongName)) return false;
        if (!routeShortName.equals(route.routeShortName)) return false;
        if (!routeTextColor.equals(route.routeTextColor)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = routeId.hashCode();
        result = 31 * result + routeColor.hashCode();
        result = 31 * result + routeLongName.hashCode();
        result = 31 * result + routeShortName.hashCode();
        result = 31 * result + routeTextColor.hashCode();
        return result;
    }
}
