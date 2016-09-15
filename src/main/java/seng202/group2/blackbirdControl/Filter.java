package seng202.group2.blackbirdControl;

import seng202.group2.blackbirdModel.AirlinePoint;
import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.BBDatabase;
import seng202.group2.blackbirdModel.RoutePoint;

import java.util.*;

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
        allPoints = BBDatabase.performAirportsQuery(sql);
        return allPoints;
    }

    public static ArrayList<AirportPoint> filterAirportsBySelections(String countrySelection, String query) {
        //Filters aiports by country and string query
        ArrayList<AirportPoint> allPoints;
        String searchString = String.format("(ID='%1$s' OR NAME='%1$s' OR CITY='%1$s' OR COUNTRY='%1$s'" +
                " OR IATA='%1$s' OR ICAO='%1$s' OR LATITUDE='%1$s' OR LONGITUDE='%1$s' OR ALTITUDE='%1$s'" +
                " OR TIMEZONE='%1$s' OR DST='%1$s' OR TZ='%1$s');", query);
        String sql = "SELECT * FROM AIRPORT ";



        if(countrySelection != "None") {
            //If there is a country
            sql += "WHERE COUNTRY=\"" + countrySelection + "\"";
            if (query.length() != 0){
                //If there is a search string query
                sql += " AND " + searchString;
            }
            allPoints = BBDatabase.performAirportsQuery(sql);
        }else{
            if (query.length() >0) { //If there is a searchString query
                sql += "WHERE " + searchString;
            }

            allPoints = BBDatabase.performAirportsQuery(sql);

        }

        return allPoints;
    }

    public static ArrayList<RoutePoint> filterRoutesBySelections(ArrayList<String> menusPressed, String searchQuery) {
        String searchString = String.format("");
                                                                                    //Source dest stops equip
        ArrayList<String> allSelections = new ArrayList<String>(Arrays.asList("Src=\"%s\" AND ", "Dst=\"%s\" AND ", "Stops=\"%s\" AND ", "Src=\"%s\" AND " ));
//            "SELECT * from EQUIPMENT, ROUTE\n" +
//            "WHERE EQUIPMENT.IDnum = ROUTE.IDnum"
        String outputString = "SELECT * FROM ROUTE ";

        //SELECT * from EQUIPMENT, ROUTE WHERE EQUIPMENT.IDnum = ROUTE.IDnum AND EQUIPMENT.EquipmentName='CR2'

        //SELECT * from EQUIPMENT, ROUTE WHERE EQUIPMENT.EquipmentName='CR2'

        ArrayList<RoutePoint> routePoints = new ArrayList<RoutePoint>();
        boolean allNone = true;

        for (String currentSelection: menusPressed){
            if (currentSelection != "None"){
                allNone = false;
            }
        }

        if (!allNone){
            outputString += "WHERE ";
            for (int i=0; i<menusPressed.size(); i++){
                String currentSelection = menusPressed.get(i);
                if(currentSelection != "None"){
                    outputString += String.format(allSelections.get(i), currentSelection);
                }
            }

        }
        //If there are no filters selected, we must begin the statement with WHERE before beginning the search query
        //statement. However if there are filters selected, we must continue the statement with AND before appending
        // the search query statement.
        outputString = removeLastAND(outputString);
        if (searchQuery.length() >0){
            if(allNone){
                outputString += " WHERE ";
            }
            else{
                outputString += " AND ";
            }
        }

        System.out.println("\n\n");
        System.out.println(outputString);
        System.out.println("\n\n");
        outputString = removeLastAND(outputString);



        System.out.println("Perfomring query:"+ outputString);




        routePoints = BBDatabase.performRoutesQuery(outputString);
        return routePoints;
    }





    public static ArrayList<AirlinePoint> filterAirlinesBySelections(ArrayList<String> menusPressed, String search) {
        //Please read this before editing this function
        //The function takes all of the selections pressed, and then subs the values into the selections string using a string formatter
        //It then adds it to the query
        //it first checks if all strings are not none and if that is true it performs the query "Select * from Airline
        //Other wise subs all strings in
        //Ask Stefan Before messing with this
        String searchString = "";
        if (search.length() >0){
            searchString = String.format("(ID='%1$s' OR NAME='%1$s' OR ALIAS='%1$s' " +
                    "OR IATA='%1$s' OR ICAO='%1$s' OR CALLSIGN='%1$s' OR COUNTRY='%1$s' OR ACTIVE='%1$s');", search);
        }


        ArrayList<String> allSelections = new ArrayList<String>(Arrays.asList("COUNTRY=\"%s\" AND ", "ACTIVE=\"%s\" AND "));
        //System.out.println(menusPressed.get(1));
        String outputString = "SELECT * FROM AIRLINE ";

        boolean allNone = true;

        for (String currentSelection: menusPressed){
            if (currentSelection != "None"){
                allNone = false;
            }
        }


        if (!allNone){
            outputString += "WHERE ";
            for (int i=0; i<menusPressed.size(); i++){
                String currentSelection = menusPressed.get(i);
                if(currentSelection != "None"){
                    outputString += String.format(allSelections.get(i), currentSelection);
                }
            }

        }

        //If there are no filters selected, we must begin the statement with WHERE before beginning the search query
        //statement. However if there are filters selected, we must continue the statement with AND before appending
        // the search query statement.
        outputString = removeLastAND(outputString);
        if (search.length() >0){
            if(allNone){
                outputString += " WHERE ";
            }
            else{
                outputString += " AND ";
            }
        }
        outputString += searchString;

        System.out.println("\n\n");
        System.out.println(outputString);
        System.out.println("\n\n");



        System.out.println("Perfomring query:"+ outputString);

        ArrayList<AirlinePoint> allPoints = BBDatabase.performAirlinesQuery(outputString);
        return allPoints;
    }

    private static String removeLastAND(String outputString) {

        String substring = outputString.substring(outputString.length()-4, outputString.length()-1);
        if (substring.equals("AND")){
            outputString = outputString.substring(0, outputString.length()-4);
        }

        return outputString;

    }

    public static ArrayList<String> findDistinctStringFromTable(String input, String Table) {
        String sql = "SELECT DISTINCT %s from %s";
        sql = String.format(sql, input, Table);
        ArrayList<String> uniqueSources = BBDatabase.performDistinctStringQuery(sql);
        //ArrayList<String> allPoints = new ArrayList<String>();
        //uniqueSources = Collections.sort(uniqueSources);
        Collections.sort(uniqueSources, String.CASE_INSENSITIVE_ORDER);
        //System.out.println(uniqueSources);
        //Hello world

        return uniqueSources;

    }

    public static ArrayList<String> findDistinctStringsFromEquip(String input) {
        String sql = "SELECT DISTINCT %s from EQUIPMENT";
        sql = String.format(sql, input);
        ArrayList<String> uniqueSources = BBDatabase.performDistinctStringQuery(sql);
        //ArrayList<String> allPoints = new ArrayList<String>();
        //uniqueSources = Collections.sort(uniqueSources);
        Collections.sort(uniqueSources, String.CASE_INSENSITIVE_ORDER);
        //System.out.println(uniqueSources);
        //Hello world

        return uniqueSources;

    }


}

