package com.example.santiagolopezgarcia.talleres.util.googlemaps;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class Leg {

    public RouteInformation distance;

    public RouteInformation duration;

    public String end_address;

    public Geolocation end_location;

    public String start_address;

    public Geolocation start_location;

    public List<Step> steps;

    public String[] via_waypoint;

}