package seng202.group2.blackbirdControl;

import seng202.group2.blackbirdModel.AirlinePoint;
import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.RoutePoint;

import java.util.ArrayList;

/**
 * Created by mch230 on 25/08/16.
 */
public class Filter {
    public static ArrayList<AirportPoint> filterAirportCountry(ArrayList<AirportPoint> airports, String country) {
        ArrayList<AirportPoint> filteredAirports = new ArrayList<AirportPoint>();
        for (AirportPoint airport : airports) {
            if (airport.getAirportCountry().equals(country)) {
                filteredAirports.add(airport);
            }
        }
        return filteredAirports;
    }

    public static ArrayList<AirlinePoint> filterAirlineCountry(ArrayList<AirlinePoint> airlines, String country) {
        ArrayList<AirlinePoint> filteredAirlines = new ArrayList<AirlinePoint>();
        for (AirlinePoint airline : airlines) {
            if (airline.getCountry().equals(country)) {
                filteredAirlines.add(airline);
            }
        }
        return filteredAirlines;
    }

    public static ArrayList<AirlinePoint> activeAirlines(ArrayList<AirlinePoint> airlines, boolean active) {
        if (active) {
            ArrayList<AirlinePoint> activeAirlines = new ArrayList<AirlinePoint>();
            for (AirlinePoint airline : airlines) {
                if (airline.getActive().equals("Y")) {
                    activeAirlines.add(airline);
                }
            }
            return activeAirlines;
        } else {
            ArrayList<AirlinePoint> inactiveAirlines = new ArrayList<AirlinePoint>();
            for (AirlinePoint airline : airlines) {
                if (airline.getActive().equals("N")) {
                    inactiveAirlines.add(airline);
                }
            }
            return inactiveAirlines;
        }
    }

    public static void filterRouteSrc(ArrayList<RoutePoint> routes, String srcCountry) {
//        for (RoutePoint route : routes) {
//
//            if (route.getSrcAirportID())
//        }
    }

    public static void filterRoutes(ArrayList<RoutePoint> routes, String srcCountry, String destCountry) {
        for (RoutePoint route : routes) {
            //if (route.getSrcAirportID() && route.getDstAirportID()) {   //should airport be inside the route point? - linking of data

        }
    }
}