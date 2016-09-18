package seng202.group2.blackbirdControl;

import seng202.group2.blackbirdModel.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mch230 on 25/08/16.
 */
public class Filter {

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
    public static ArrayList<RoutePoint> routeEquipment(ArrayList<RoutePoint> routes, String equipment) {
        ArrayList<RoutePoint> equipmentRoutes = new ArrayList<>();
        String patternString;
        if (equipment.isEmpty()) {
            patternString = "^$";
        } else {
            String[] newString = equipment.split(" ");
            patternString = "\\b(" + String.join("|", newString) + ")\\b";
        }

        Pattern pattern = Pattern.compile(patternString);

        for (RoutePoint route : routes) {
            Matcher matcher = pattern.matcher(route.getEquipment());
            if (matcher.find()) {
                equipmentRoutes.add(route);
            }
        }
        return equipmentRoutes;
    }


    public static ArrayList<String> filterUniqueAirportCountries(){
        //Finds unique countries that have aiports using thie database
        ArrayList<String> allCountries = new ArrayList<String>();
        ArrayList<AirportPoint> allPoints = getAllAirportPointsFromDB();
        String currentCountry;
        for (AirportPoint airport : allPoints) {
            currentCountry = airport.getAirportCountry();
            if (!allCountries.contains(currentCountry)) {
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
            if (!allCountries.contains(currentCountry)) {
                allCountries.add(currentCountry);
            }

        }
        Collections.sort(allCountries, String.CASE_INSENSITIVE_ORDER);
        return allCountries;

    }


    public static ArrayList<AirlinePoint> getAllAirlinePointsfromDB() {
        String sql = " SELECT * FROM AIRLINE;";
        return BBDatabase.performAirlinesQuery(sql);
    }

//    public static ArrayList<FlightPoint> getallFlightPoints() {
//        String sql = " SELECT * FROM FLIGHT;";
//        return BBDatabase.performFlightsQuery(sql);
//    }

    public static ArrayList<AirportPoint> getAllAirportPointsFromDB() {
        String sql = "SELECT * FROM AIRPORT;";
        return BBDatabase.performAirportsQuery(sql);
    }

    public static ArrayList<RoutePoint> getAllRoutePointsFromDB() {
        String sql = "SELECT * FROM ROUTE;";
        return BBDatabase.performRoutesQuery(sql);
    }

    public static ArrayList<AirportPoint> filterAirportsBySelections(String countrySelection, String query) {
        //Filters aiports by country and string query
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
            return BBDatabase.performAirportsQuery(sql);
        }else{
            if (query.length() >0) { //If there is a searchString query
                sql += "WHERE " + searchString;
            }

            return BBDatabase.performAirportsQuery(sql);

        }
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



        return BBDatabase.performAirlinesQuery(outputString);
    }



    public static ArrayList<RoutePoint> filterRoutesBySelections(ArrayList<String> menusPressed, String searchQuery) {
        ArrayList<String> allSelections = new ArrayList<>(Arrays.asList("Src=\"%s\" AND ", "Dst=\"%s\" AND ", "Stops=\"%s\" AND ", "(EQUIPMENT LIKE \"%%%s%%\") AND " ));

        String sql = "SELECT * FROM ROUTE ";
        boolean allNone = true;
        for (String currentSelection: menusPressed){    //all none method in refactored filter
            if (currentSelection != "None"){
                allNone = false;
            }
        }
        if (!allNone){
            sql += "WHERE ";
            for (int i=0; i<menusPressed.size(); i++){
                String currentSelection = menusPressed.get(i);
                if(currentSelection != "None"){
                    sql += String.format(allSelections.get(i), currentSelection);

                }
            }
            sql = sql.replaceAll("%%", "");
            sql = removeLastAND(sql);
        }

        String search = "";
        if (searchQuery.length() >0){
            String searchStatement = "(ROUTE.IDnum=\"%1$s\" OR ROUTE.IDnum=\"%1$s\" OR ROUTE.AirlineID=\"%1$s\""
                    + "OR ROUTE.Src=\"%1$s\" OR ROUTE.SrcID=\"%1$s\" OR ROUTE.Dst=\"%1$s\" OR ROUTE.Dstid=\"%1$s\""
                    + "OR ROUTE.Codeshare=\"%1$s\" OR ROUTE.Stops=\"%1$s\" OR EQUIPMENT LIKE \"%%%1$s%%\" );";
            search = String.format(searchStatement, searchQuery);
            if(allNone){
                sql += " WHERE ";
            }
            else{
                sql += " AND ";
            }
        }


        sql += search;
        ArrayList<RoutePoint> routePoints = BBDatabase.performRoutesQuery(sql);

        return routePoints;
    }

//    private static String joinEquipArray(ArrayList<String> equipArray) {
//        //This function adds all the strings in the equipment array seperating them by spaces
//        String myEquipment = "";
//        for (int i=0; i<equipArray.size()-1; i++){
//            myEquipment += equipArray.get(i) + " ";
//        }
//        myEquipment += equipArray.get(equipArray.size()-1);
//        return myEquipment;
//    }


//    private static ArrayList<String> getEquipInfoFromDB(RoutePoint currentPoint) {
//        int id = currentPoint.getRouteID();
//        String sql = "SELECT EquipmentName FROM EQUIPMENT WHERE IDnum=\"%1$s\"";
//        String myEquipment = "";
//        sql = String.format(sql, id);
//        return BBDatabase.performDistinctStringQuery(sql);
//    }

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

        return uniqueSources;

    }

    public static ArrayList<String> findDistinctStringsFromEquip(String input) {
        String sql = "SELECT DISTINCT %s from EQUIPMENT";
        sql = String.format(sql, input);
        ArrayList<String> uniqueSources = BBDatabase.performDistinctStringQuery(sql);
        //ArrayList<String> allPoints = new ArrayList<String>();
        //uniqueSources = Collections.sort(uniqueSources);
        Collections.sort(uniqueSources, String.CASE_INSENSITIVE_ORDER);


        return uniqueSources;

    }


}

