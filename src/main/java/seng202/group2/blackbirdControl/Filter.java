package seng202.group2.blackbirdControl;

import seng202.group2.blackbirdModel.*;

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
        String[] newString = equipment.split(" ");
        String patternString = "\\b(" + String.join("|", newString) + ")\\b";
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
        return BBDatabase.performAirlinesQuery(sql);
    }

//    public static ArrayList<FlightPoint> getallFlightPoints() {
//        String sql = " SELECT * FROM FLIGHT;";
//        ArrayList<FlightPoint> allPoints = BBDatabase.performFlightsQuery(sql);
//        return allPoints;
//    }

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

        System.out.println("Perfomring query:"+ outputString);


        return BBDatabase.performAirlinesQuery(outputString);
    }

    public static ArrayList<RoutePoint> filterRoutesBySelections2(ArrayList<String> menusPressed, String searchQuery) {
        String searchString = String.format("");
        String equipmentSelection = menusPressed.get(3);
        menusPressed.remove(3);
        System.out.println("EQUIP SELECTION" + equipmentSelection);
        String equipment;
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



        System.out.println("\n\n");
        System.out.println(outputString);
        System.out.println("\n\n");



        System.out.println("Perfomring query:"+ outputString);
        routePoints = BBDatabase.performRoutesQuery(outputString);

        ArrayList<RoutePoint> routePointsCP = new ArrayList<RoutePoint>();
        ArrayList<String> equipArray = new ArrayList<>();

        for (RoutePoint currentPoint: routePoints){
            equipArray = getEquipInfoFromDB(currentPoint);
            equipment = joinEquipArray(equipArray);
            currentPoint.setEquipment(equipment);
            if (equipmentSelection == "None"){
                routePointsCP.add(currentPoint);
            }
            else{
                System.out.println("Equip:" + equipment + "SELECTION:" + equipmentSelection + "ARRAY" + equipArray);
                for (String currentEquipment: equipArray){

                    if (currentEquipment.equals(equipmentSelection)){
                        System.out.println("ADDING" + equipment);
                        routePointsCP.add(currentPoint);
                    }

                }

            }

        }



        return routePointsCP;
    }

    public static ArrayList<RoutePoint> filterRoutesBySelections(ArrayList<String> menusPressed, String searchQuery) {
        String searchString = "";
        if (searchQuery.length() >0){
            searchString = "(ROUTE.IDnum=\"%1$s\" OR ROUTE.IDnum=\"%1$s\" OR ROUTE.AirlineID=\"%1$s\""
            + "OR ROUTE.Src=\"%1$s\" OR ROUTE.SrcID=\"%1$s\" OR ROUTE.Dst=\"%1$s\" OR ROUTE.Dstid=\"%1$s\""
            + "OR ROUTE.Codeshare=\"%1$s\" OR ROUTE.Stops=\"%1$s\" OR EQUIPMENT.EquipmentName=\"%1$s\" );";
            searchString = String.format(searchString, searchQuery);

        }
        //System.out.println(searchString);
        ArrayList<RoutePoint> routePoints = new ArrayList<RoutePoint>();
        ArrayList<String> allSelections = new ArrayList<String>(Arrays.asList("Src=\"%s\" AND ", "Dst=\"%s\" AND ", "Stops=\"%s\" AND ", "EQUIPMENT.equipmentName=\"%s\" AND " ));

        String sql = "SELECT * FROM ROUTE LEFT OUTER JOIN EQUIPMENT ON EQUIPMENT.IDnum = ROUTE.IDnum WHERE ";
        String currentSelection;
        for (int i=0; i<menusPressed.size(); i++){
            currentSelection = menusPressed.get(i);
            if (!currentSelection.equals("None")){
                sql += String.format(allSelections.get(i),currentSelection);
            }
        }
        if (searchQuery.length() >0){
            sql += searchString;
        }
        else{
            sql = removeLastAND(sql);
        }


        System.out.println(sql);
        routePoints = BBDatabase.performJoinRoutesEquipQuery(sql);
        //System.out.println(sql);
//        String equipmentSelection = menusPressed.get(3);
//        menusPressed.remove(3);
//        System.out.println("EQUIP SELECTION" + equipmentSelection);
//        String equipment;
//        //Source dest stops equip
//        ArrayList<String> allSelections = new ArrayList<String>(Arrays.asList("Src=\"%s\" AND ", "Dst=\"%s\" AND ", "Stops=\"%s\" AND ", "Src=\"%s\" AND " ));
////            "SELECT * from EQUIPMENT, ROUTE\n" +
////            "WHERE EQUIPMENT.IDnum = ROUTE.IDnum"
//        String outputString = "SELECT * FROM ROUTE ";
//
//        //SELECT * from EQUIPMENT, ROUTE WHERE EQUIPMENT.IDnum = ROUTE.IDnum AND EQUIPMENT.EquipmentName='CR2'
//
//        //SELECT * from EQUIPMENT, ROUTE WHERE EQUIPMENT.EquipmentName='CR2'
//
//        ArrayList<RoutePoint> routePoints = new ArrayList<RoutePoint>();
//        boolean allNone = true;
//
//        for (String currentSelection: menusPressed){
//            if (currentSelection != "None"){
//                allNone = false;
//            }
//        }
//
//        if (!allNone){
//            outputString += "WHERE ";
//            for (int i=0; i<menusPressed.size(); i++){
//                String currentSelection = menusPressed.get(i);
//                if(currentSelection != "None"){
//                    outputString += String.format(allSelections.get(i), currentSelection);
//                }
//            }
//
//        }
//        //If there are no filters selected, we must begin the statement with WHERE before beginning the search query
//        //statement. However if there are filters selected, we must continue the statement with AND before appending
//        // the search query statement.
//        outputString = removeLastAND(outputString);
//        if (searchQuery.length() >0){
//            if(allNone){
//                outputString += " WHERE ";
//            }
//            else{
//                outputString += " AND ";
//            }
//        }
//
//        System.out.println("\n\n");
//        System.out.println(outputString);
//        System.out.println("\n\n");
//        outputString = removeLastAND(outputString);
//
//
//
//        System.out.println("\n\n");
//        System.out.println(outputString);
//        System.out.println("\n\n");
//
//
//
//        System.out.println("Perfomring query:"+ outputString);
//        routePoints = BBDatabase.performRoutesQuery(outputString);
//
//        ArrayList<RoutePoint> routePoints = new ArrayList<RoutePoint>();
//        ArrayList<String> equipArray = new ArrayList<>();
//
//        for (RoutePoint currentPoint: routePoints){
//            equipArray = getEquipInfoFromDB(currentPoint);
//            equipment = joinEquipArray(equipArray);
//            currentPoint.setEquipment(equipment);
//            if (equipmentSelection == "None"){
//                routePoints.add(currentPoint);
//            }
//            else{
//                System.out.println("Equip:" + equipment + "SELECTION:" + equipmentSelection + "ARRAY" + equipArray);
//                for (String currentEquipment: equipArray){
//
//                    if (currentEquipment.equals(equipmentSelection)){
//                        System.out.println("ADDING" + equipment);
//                        routePoints.add(currentPoint);
//                    }
//
//                }
//
//            }
//
//        }



        return routePoints;
    }

    private static String joinEquipArray(ArrayList<String> equipArray) {
        //This function adds all the strings in the equipment array seperating them by spaces
        String myEquipment = "";
        for (int i=0; i<equipArray.size()-1; i++){
            myEquipment += equipArray.get(i) + " ";
        }
        myEquipment += equipArray.get(equipArray.size()-1);
        return myEquipment;
    }


    private static ArrayList<String> getEquipInfoFromDB(RoutePoint currentPoint) {
        int id = currentPoint.getRouteID();
        String sql = "SELECT EquipmentName FROM EQUIPMENT WHERE IDnum=\"%1$s\"";
        String myEquipment = "";
        sql = String.format(sql, id);
        return BBDatabase.performDistinctStringQuery(sql);
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

