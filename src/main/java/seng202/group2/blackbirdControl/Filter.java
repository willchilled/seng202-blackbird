package seng202.group2.blackbirdControl;

import seng202.group2.blackbirdModel.AirlinePoint;
import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.BBDatabase;
import seng202.group2.blackbirdModel.RoutePoint;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mch230 on 25/08/16.
 */
public class Filter {
    //filter airports when given a single country
    public static ArrayList<AirportPoint> filterAirportCountry(ArrayList<AirportPoint> airports, String country) {
       // System.out.println(country);
       // country = "\"" + country + "\""; //THIS WILL NEED TO CHANGE IF WE Change how stuff goes intot he table
        ArrayList<AirportPoint> filteredAirports = new ArrayList<AirportPoint>();
        for (AirportPoint airport : airports) {
           // System.out.println(airport.getAirportName());
            if (airport.getAirportCountry().equals(country)) {
                filteredAirports.add(airport);
            }
        }
        return filteredAirports;
    }

    //filter airlines when given a single country
    public static ArrayList<AirlinePoint> filterAirlineCountry(ArrayList<AirlinePoint> airlines, String country) {
        ArrayList<AirlinePoint> filteredAirlines = new ArrayList<AirlinePoint>();
        for (AirlinePoint airline : airlines) {
            if (airline.getCountry().equals(country)) {
                filteredAirlines.add(airline);
            }
        }
        return filteredAirlines;
    }

    //filter active or inactive airlines
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

    //filter routes based on departure location
    public static void filterRouteSrc(ArrayList<RoutePoint> routes, String srcCountry) {
//        for (RoutePoint route : routes) {
//
//            if (route.getSrcAirportID())
//        }
    }

    //filter routes based on destination
    public static void filterRoutes(ArrayList<RoutePoint> routes, String destCountry) {
        for (RoutePoint route : routes) {
            //if (route.getSrcAirportID() && route.getDstAirportID()) {   //should airport be inside the route point? - linking of data

        }
    }

    //filter direct or indirect routes
    public static ArrayList<RoutePoint> directRoutes(ArrayList<RoutePoint> routes, boolean direct) {
        if (direct) {
            ArrayList<RoutePoint> directRoutes = new ArrayList<RoutePoint>();
            for (RoutePoint route : routes) {
                if (route.getStops() == 0) {
                    directRoutes.add(route);
                }
            }
            return directRoutes;
        } else {
            ArrayList<RoutePoint> indirectRoutes = new ArrayList<RoutePoint>();
            for (RoutePoint route : routes) {
                if (route.getStops() != 0) {
                    indirectRoutes.add(route);
                }
            }
            return indirectRoutes;
        }
    }

    //filter routes based on equipment (from drop down?)
    public static void routeEquipment(ArrayList<RoutePoint> routes, String equipment) {
        for (RoutePoint route : routes) {
            //if (route.getEquipment())
        }
    }



    public static ArrayList<String> filterUniqueAirportCountries(){
        //Finds unique countries that have aiports using thie database
        ArrayList<String> allCountries = new ArrayList<String>();
        ArrayList<AirportPoint> allPoints = getAllAirportPointsFromDB();
        String currentCountry;
        for (AirportPoint airport : allPoints) {
            currentCountry = airport.getAirportCountry();
            if (!allCountries.contains(currentCountry)) {
                //System.out.println(currentCountry);
                allCountries.add(currentCountry);
            }

        }
        Collections.sort(allCountries, String.CASE_INSENSITIVE_ORDER);
        return allCountries;
    }


    public static ArrayList<String> filterUniqueAirLineCountries(ArrayList<AirlinePoint> allPoints){
        //Returns a list of all unique countries
        ArrayList<String> allCountries = new ArrayList<String>();
        String currentCountry;
        for (AirlinePoint airline : allPoints) {
            currentCountry = airline.getCountry();
            //System.out.println(currentCountry);
            if (!allCountries.contains(currentCountry)) {
                //System.out.println(currentCountry);
                allCountries.add(currentCountry);
            }

        }
        Collections.sort(allCountries, String.CASE_INSENSITIVE_ORDER);
        return allCountries;

    }


    public static ArrayList<AirlinePoint> getAllAirlinePointsfromDB() {
        String sql = " SELECT * FROM AIRLINE;";
        ArrayList<AirlinePoint> allPoints = BBDatabase.performAirlinesQuery(sql);
        return allPoints;
    }

    public static ArrayList<AirportPoint> getAllAirportPointsFromDB() {
        //gets all Aiport Points from the database
        String sql = "SELECT * FROM AIRPORT;";
        ArrayList<AirportPoint> allPoints = new ArrayList<AirportPoint>();
        allPoints = BBDatabase.performAirpointsQuery(sql);
        return allPoints;
    }

    public static ArrayList<AirportPoint> filterAiportsByCountryUsingDB(String countrySelection) {
        String sql = "SELECT * FROM AIRPORT WHERE COUNTRY=\"" + countrySelection + "\";";
        ArrayList<AirportPoint> allPoints = new ArrayList<AirportPoint>();
        allPoints = BBDatabase.performAirpointsQuery(sql);
        return allPoints;
    }

    public static ArrayList<AirlinePoint> filterAirlinesBySelections(ArrayList<String> menusPressed) {
        //System.out.println(menusPressed);
        String sql = "SELECT * FROM AIRLINE WHERE ";
        String countryselection = menusPressed.get(0);
        String activeSelection = menusPressed.get(1);

        String active = "ACTIVE=\"";
        String country = "COUNTRY=\"" + countryselection + "\"";

        if (activeSelection == "Active"){
            active += "Y\"";
        }
        else if (activeSelection == "Inactive"){
            active += "N\"";
        }

        if(countryselection == "None" && activeSelection=="None"){
            sql = "SELECT * FROM AIRLINE";
        }
        else if (countryselection != "None" && activeSelection != "None"){
            sql += country +  " AND " + active + ";";
        }
        else if(countryselection == "None"){
            sql += active + ";";
        }
        else if (activeSelection == "None"){

            sql += country;
        }


        System.out.println("Perfomring query:"+ sql);

        ArrayList<AirlinePoint> allPoints = BBDatabase.performAirlinesQuery(sql);
        return allPoints;
    }
}