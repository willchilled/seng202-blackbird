package seng202.group2.blackbirdControl;

import seng202.group2.blackbirdModel.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mch230 on 17/09/16.
 */
public class FilterRefactor {

    public static ArrayList<DataPoint> getAllPoints(String type) {
        String sql;
        switch (type) {
            case "AirlinePoint": sql = "SELECT * FROM AIRLINE;"; break;
            case "AirportPoint": sql = "SELECT * FROM AIRPORT;"; break;
            case "RoutePoint": sql = "SELECT * FROM ROUTE;"; break;
            //case "Flight": sql = "SELECT * FROM AIRLINE;";    //FLIGHTS UNABLE TO BE FILTERED ATM
            default: return null;
        }
        return DataBaseRefactor.performGenericQuery(sql, type);      //NEED DB METHOD HERE
    }


    public static ArrayList<DataPoint> filterSelections(ArrayList<String> menusPressed, String searchString, String type) {
        ArrayList<DataPoint> filtered;
        String myQuery = "";
        switch (type) {
            case "AirlinePoint": myQuery = airlineFilter(menusPressed, searchString); break;
            case "AirportPoint": myQuery = airportFilter(menusPressed, searchString); break;
            case "RoutePoint": myQuery = routeFilter(menusPressed, searchString); break;
            case "FlightPoint": myQuery = flightFilter(menusPressed, searchString); break;   //FLIGHTS UNABLE TO BE FILTERED ATM
            default: return null;
        }
        filtered =  DataBaseRefactor.performGenericQuery(myQuery, type);
        return filtered;
    }


    private static String flightFilter(ArrayList<String> menusPressed, String searchString) {
        //TODO
        //No filters for flights currently
        return null;
    }


    private static String routeFilter(ArrayList<String> menusPressed, String searchString) {
        ArrayList<String> allSelections = new ArrayList<>(Arrays.asList("Src=\"%s\" AND ", "Dst=\"%s\" AND ", "Stops=\"%s\" AND ", "EQUIPMENT.equipmentName=\"%s\" AND " ));

        String sql = "SELECT * FROM ROUTE LEFT OUTER JOIN EQUIPMENT ON EQUIPMENT.IDnum = ROUTE.IDnum WHERE ";
        sql = generateQueryString(sql, menusPressed, allSelections);

        String search = "";
        if (searchString.length() >0){
            String searchStatement = "(ROUTE.IDnum=\"%1$s\" OR ROUTE.IDnum=\"%1$s\" OR ROUTE.AirlineID=\"%1$s\""
                    + "OR ROUTE.Src=\"%1$s\" OR ROUTE.SrcID=\"%1$s\" OR ROUTE.Dst=\"%1$s\" OR ROUTE.Dstid=\"%1$s\""
                    + "OR ROUTE.Codeshare=\"%1$s\" OR ROUTE.Stops=\"%1$s\" OR EQUIPMENT.EquipmentName=\"%1$s\" );";
            search = String.format(searchStatement, searchString);
            sql += searchString;
        } else {
            sql = removeLastAND(sql);
        }

        //routePoints = BBDatabase.performJoinRoutesEquipQuery(sql);    //DB METHOD HERE
        return sql;
    }


    private static String airportFilter(ArrayList<String> menusPressed, String searchString) {
        String sql = "SELECT * FROM AIRPORT ";

        boolean allNone = checkEmptyMenus(menusPressed);
        if (!allNone){
            sql += "WHERE COUNTRY=\"" + menusPressed.get(0) + "\"";         //GET(0) HERE CURRENTLY, SINCE RIGHT NOW WE ONLY HAVE ONE MENU FOR COUNTRY
        }

        String search = "";
        if (searchString.length() > 0) {
            if (!allNone) {
                sql += " AND ";
            }
            else{
                sql += " WHERE ";
            }
            search = String.format("(ID='%1$s' OR NAME='%1$s' OR CITY='%1$s' OR COUNTRY='%1$s'" +
                    " OR IATA='%1$s' OR ICAO='%1$s' OR LATITUDE='%1$s' OR LONGITUDE='%1$s' OR ALTITUDE='%1$s'" +
                    " OR TIMEZONE='%1$s' OR DST='%1$s' OR TZ='%1$s');", searchString);
        }

        sql += search;
        //allPoints = BBDatabase.performQuery(sql);     //PERFORM DB QUERY
        //return null;
        return sql;
    }


    private static String airlineFilter(ArrayList<String> menusPressed, String searchString) {
        String sql = "SELECT * FROM AIRLINE ";

        ArrayList<String> allSelections = new ArrayList<>(Arrays.asList("COUNTRY=\"%s\" AND ", "ACTIVE=\"%s\" AND "));

        boolean allNone = checkEmptyMenus(menusPressed);
        if (!allNone){
            sql = generateQueryString(sql, menusPressed, allSelections);
        }

        sql = removeLastAND(sql);

        String search = "";
        if (searchString.length() >0){
            search = String.format("(ID='%1$s' OR NAME='%1$s' OR ALIAS='%1$s' " +
                    "OR IATA='%1$s' OR ICAO='%1$s' OR CALLSIGN='%1$s' OR COUNTRY='%1$s' OR ACTIVE='%1$s');", searchString);
            if(allNone){
                sql += " WHERE ";
            }
            else{
                sql += " AND ";
            }
        }

        sql += search;
       // System.out.println("Performing query:"+ sql);
        //ArrayList<DataPoint> allPoints = DataBaseRefactor.performGenericQuery(sql, type);    //DB METHOD HERE
        //System.out.println("SIZE: " + allPoints.size());
        return sql; //return an arraylist
    }



    public static ArrayList<DataPoint> filterRouteEquipment(ArrayList<DataPoint> routes, String equipment) {
        ArrayList<DataPoint> equipmentRoutes = new ArrayList<>();
        String patternString;
        if (equipment.isEmpty()) {
            patternString = "^$";
        } else {
            String[] newString = equipment.split(" ");
            patternString = "\\b(" + String.join("|", newString) + ")\\b";
        }

        Pattern pattern = Pattern.compile(patternString);

        for (DataPoint route : routes) {
            RoutePoint myroute = (RoutePoint) route;    //casting here
            Matcher matcher = pattern.matcher(myroute.getEquipment());
            if (matcher.find()) {
                equipmentRoutes.add(route);
            }
        }
        return equipmentRoutes;
    }



    private static ArrayList<String> filterUnique(String type, String input) {    //input- relying on GUI to give the type and input e.g. Src, Dst??
        String sql = "SELECT DISTINCT %s from %s";
        sql = String.format(sql, input, type);
        //ArrayList<String> menuItems = DataBaseRefactor.performDistinctQuery(sql, type);   //DB method to grab distinct stuff
        //UNCOMMENT ABOVE WHEN READY
        ArrayList<String> menuItems = null;
        Collections.sort(menuItems, String.CASE_INSENSITIVE_ORDER);
        return menuItems;
    }



    private static String generateQueryString(String current, ArrayList<String> menusPressed, ArrayList<String> allSelections) {
        current += "WHERE ";
        for (int i=0; i<menusPressed.size(); i++){
            String currentSelection = menusPressed.get(i);
            if(currentSelection != "None"){
                current += String.format(allSelections.get(i), currentSelection);
            }
        }
        return current;
    }

    private static boolean checkEmptyMenus(ArrayList<String> menusPressed) {
        boolean allNone = true;
        for (String currentSelection: menusPressed){
            if (currentSelection != "None"){
                allNone = false;
            }
        }
        return allNone;
    }

    private static String removeLastAND(String outputString) {

        String substring = outputString.substring(outputString.length()-4, outputString.length()-1);
        if (substring.equals("AND")){
            outputString = outputString.substring(0, outputString.length()-4);
        }

        return outputString;

    }

}
