package seng202.group2.blackbirdControl;

import seng202.group2.blackbirdModel.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mch230 on 17/09/16.
 */
public class FilterRefactor {

    public static ArrayList<DataPoint> getAllPoints(String type) {
        String sql;
        switch (type) {
            case "Airline": sql = "SELECT * FROM AIRLINE;"; break;
            case "Airport": sql = "SELECT * FROM AIRPORT;"; break;
            case "Route": sql = "SELECT * FROM ROUTE;"; break;
            //case "Flight": sql = "SELECT * FROM AIRLINE;";    //FLIGHTS UNABLE TO BE FILTERED ATM
            default: return null;
        }
        return null;
        //return BBDatabase.performQuery(sql, type);      //NEED DB METHOD HERE
    }


    public static ArrayList<DataPoint> filterOnSelections(ArrayList<String> menusPressed, String searchString, String type) {
        ArrayList<DataPoint> filtered;
        switch (type) {
            case "Airline": filtered = airlineFilter(menusPressed, searchString); break;
            case "Airport": filtered = airportFilter(menusPressed, searchString); break;
            case "Route": filtered = routeFilter(menusPressed, searchString); break;
            case "Flight": filtered = flightFilter(menusPressed, searchString); break;   //FLIGHTS UNABLE TO BE FILTERED ATM
            default: return null;
        }
        return filtered;
    }

    private static ArrayList<DataPoint> flightFilter(ArrayList<String> menusPressed, String searchString) {
        //TODO
        //No filters for flights currently
        return null;
    }


    private static ArrayList<DataPoint> routeFilter(ArrayList<String> menusPressed, String searchString) {
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
        return null;
    }



    private static ArrayList<DataPoint> airportFilter(ArrayList<String> menusPressed, String searchString) {
        String sql = "SELECT * FROM AIRPORT ";

        boolean allNone = checkEmptyMenus(menusPressed);
        if (!allNone){
            sql += "WHERE COUNTRY=\"" + menusPressed.get(0) + "\"";         //GET(0) HERE CURRENTLY, SINCE RIGHT NOW WE ONLY HAVE ONE MENU FOR COUNTRY
        }

        String search = "";
        if (searchString.length() > 0) {
            if (!allNone) {
                sql += "AND";
            }
            search = String.format("(ID='%1$s' OR NAME='%1$s' OR CITY='%1$s' OR COUNTRY='%1$s'" +
                    " OR IATA='%1$s' OR ICAO='%1$s' OR LATITUDE='%1$s' OR LONGITUDE='%1$s' OR ALTITUDE='%1$s'" +
                    " OR TIMEZONE='%1$s' OR DST='%1$s' OR TZ='%1$s');", searchString);
        }

        sql += search;
        //allPoints = BBDatabase.performQuery(sql);     //PERFORM DB QUERY
        return null;
    }



    private static ArrayList<DataPoint> airlineFilter(ArrayList<String> menusPressed, String searchString) {
        String sql = "SELECT * FROM AIRLINE ";

        ArrayList<String> allSelections = new ArrayList<>(Arrays.asList("COUNTRY=\"%s\" AND ", "ACTIVE=\"%s\" AND "));

        boolean allNone = checkEmptyMenus(menusPressed);
        if (!allNone){
            sql = generateQueryString(sql, menusPressed, allSelections);
        }
        sql = removeLastAND(sql);

        String search = "";
        if (searchString.length() >0){
            search = String.format("WHERE (ID='%1$s' OR NAME='%1$s' OR ALIAS='%1$s' " +
                    "OR IATA='%1$s' OR ICAO='%1$s' OR CALLSIGN='%1$s' OR COUNTRY='%1$s' OR ACTIVE='%1$s');", searchString);
        }

        sql += search;
        System.out.println("Performing query:"+ sql);

        //ArrayList<AirlinePoint> allPoints = BBDatabase.performQuery(outputString);    //DB METHOD HERE
        return null; //return an arraylist
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
