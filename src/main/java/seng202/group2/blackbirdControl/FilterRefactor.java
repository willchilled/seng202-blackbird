

package seng202.group2.blackbirdControl;

import seng202.group2.blackbirdModel.*;

import javax.xml.crypto.Data;
import java.util.*;

/**
 * This class helps us to perform filters on the inputted data by generating sql strings to be performed for
 * a filter query, then performing this query via the database methods.
 */
public class FilterRefactor {

    /**
     * Gets all points within the database of the specified type
     * @param type The type of data that we want
     * @return The arraylist of datapoints returned from the database query.
     * @see DataBaseRefactor
     */
    public static ArrayList<DataPoint> getAllPoints(DataTypes type) {
        String sql;
        switch (type) {
            case AIRLINEPOINT: sql = "SELECT * FROM AIRLINE"; break;
            case AIRPORTPOINT: sql = "SELECT * FROM AIRPORT";
                //This needs special case because it has to join routes nd airports;
                ArrayList<DataPoint> myAirportPoints = DataBaseRefactor.performGenericQuery(sql, type);
                myAirportPoints = linkRoutesandAirports(myAirportPoints);
                return myAirportPoints;

            case ROUTEPOINT: sql = getJoinForRoutesTableSql(); break;
            case FLIGHTPOINT: sql = "SELECT * FROM FLIGHTPOINT;"; break;   //FLIGHTS UNABLE TO BE FILTERED ATM
            case FLIGHT: sql = "SELECT * FROM FLIGHT;"; break;
            default: return null;
        }
        return DataBaseRefactor.performGenericQuery(sql, type);
    }

    /**
     * Finds the number of incoming and outoging routes for each airport
     * @param myAirportPoints The list of airport pints wanting to be linked
     * @return airport points with number of incoming and outgoing routes set
     */
    private static ArrayList<DataPoint> linkRoutesandAirports(ArrayList<DataPoint> myAirportPoints) {
        ArrayList<DataPoint> linkedAirportPoints = new ArrayList<DataPoint>();
        ArrayList<DataPoint> myRoutes = getAllPoints(DataTypes.ROUTEPOINT);
        Map<Integer, Integer> incomingRoutesDict = new HashMap<Integer, Integer>();
        Map<Integer, Integer> outgoingRoutesDict = new HashMap<Integer, Integer>();

        for (DataPoint myPoint : myAirportPoints) {
            int key = ((AirportPoint) myPoint).getAirportID();
            incomingRoutesDict.put(key, 0);
            outgoingRoutesDict.put(key, 0);
        }

        for (DataPoint myPoint : myRoutes) {

            try{
                RoutePoint currentRoutePoint =  (RoutePoint) myPoint;
                int outGoingRouteKey = currentRoutePoint.getSrcAirportID();
                int incomingRouteKey = currentRoutePoint.getDstAirportID();

                int currentOutAmount = outgoingRoutesDict.get(outGoingRouteKey);
                outgoingRoutesDict.put(outGoingRouteKey, currentOutAmount+1);

                int currentIncAmount = incomingRoutesDict.get(incomingRouteKey);
                incomingRoutesDict.put(incomingRouteKey, currentIncAmount+1);
            }
            catch (Exception e){

            }
        }

        for(DataPoint myPoint: myAirportPoints){
            AirportPoint currentPoint  = (AirportPoint) myPoint;
            int key = currentPoint.getAirportID();
            currentPoint.setOutgoingRoutes(outgoingRoutesDict.get(key));

            currentPoint.setIncomingRoutes(incomingRoutesDict.get(key));
            DataPoint xPoint = currentPoint;
            linkedAirportPoints.add(xPoint);
        }

        return linkedAirportPoints;
    }

    /**
     * Calls upon the needed method to generate the sql string for a specified data type, then calls upon the
     * database method to execute this query.
     * @param menusPressed The arraylist of strings for each menu selection
     * @param searchString The search string from the search bar
     * @param type The type of data we are filtering
     * @return The arraylist of datapoints returned from the filter query.
     * @see DataBaseRefactor
     */
    public static ArrayList<DataPoint> filterSelections(ArrayList<String> menusPressed, String searchString, DataTypes type) {
        ArrayList<DataPoint> filtered;
        String myQuery = "";
        switch (type) {
            case AIRLINEPOINT: myQuery = airlineFilter(menusPressed, searchString); break;
            case AIRPORTPOINT: myQuery = airportFilter(menusPressed, searchString);
                ArrayList<DataPoint> myAirportPoints = DataBaseRefactor.performGenericQuery(myQuery, type);
                myAirportPoints = linkRoutesandAirports(myAirportPoints);
                return myAirportPoints;
            case ROUTEPOINT: myQuery = routeFilter(menusPressed, searchString); //a special case because we want to filter by selections not in the databaase
                            ArrayList<DataPoint> myRoutePoints = DataBaseRefactor.performGenericQuery(myQuery, type);
                            //myRoutePoints = refineRoutesQuery(myRoutePoints, searchString);
                            return myRoutePoints;

                //break;
            case FLIGHT: myQuery = flightFilter(menusPressed, searchString); break;   //FLIGHTS UNABLE TO BE FILTERED ATM
            default: return null;
        }
        filtered =  DataBaseRefactor.performGenericQuery(myQuery, type);
        return filtered;
    }

    /**
     * Filters a list of route points for a search string
     * @param myRoutePoints list of route points wanting to be filtered
     * @param searchString the string that we want to match
     * @return a filtered array list of points
     */
    private static ArrayList<DataPoint> refineRoutesQuery(ArrayList<DataPoint> myRoutePoints, String searchString) {
        System.out.println(searchString + "search string");
        if (searchString.length() > 1) {
            ArrayList<DataPoint> refinedPoints = new ArrayList<DataPoint>();
            for (DataPoint currentPoint : myRoutePoints) {
                RoutePoint currentRoutePoint = (RoutePoint) currentPoint;
                System.out.println(currentRoutePoint.toStringWithAirports() + "----" + searchString);
                ArrayList<String> srcAndDstList = new ArrayList<String>(Arrays.asList(currentRoutePoint.getSrcAirportName(), currentRoutePoint.getSrcAirportCountry(),
                        currentRoutePoint.getDstAirportName(), currentRoutePoint.getDstAirportCountry()));

                if (srcAndDstList.contains(searchString)) {
                    DataPoint pointToAdd = currentRoutePoint;
                    refinedPoints.add(pointToAdd);
                }
            }
            return refinedPoints;
        }
        else{
            return myRoutePoints;
        }

    }


    /**
     * Returns the sql string for the filters specified
     * @param menusPressed The arraylist of strings for each menu selection
     * @param searchString The search string from the search bar
     * @return The sql filter string to be executed.
     */
    private static String flightFilter(ArrayList<String> menusPressed, String searchString) {

        String sql = "SELECT * FROM FLIGHT ";

        ArrayList<String> allSelections = new ArrayList<>(Arrays.asList("SrcICAO=\"%s\" AND ", "DstICAO=\"%s\" AND "));

        boolean allNone = checkEmptyMenus(menusPressed);
        if (!allNone){
            sql = generateQueryString(sql, menusPressed, allSelections);
        }

        sql = removeLastAND(sql);

        String search = "";
        if (searchString.length() >0){
            search = String.format("(SrcICAO='%1$s' OR DstICAO='%1$s')", searchString);
            if(allNone){
                sql += " WHERE ";
            }
            else{
                sql += " AND ";
            }
        }

        sql += search;
        //sql = removeLastWHERE(sql);
        //System.out.println(sql);
        //sql = sql.replaceAll()
        // System.out.println("Performing query:"+ sql);
        //ArrayList<DataPoint> allPoints = DataBaseRefactor.performGenericQuery(sql, type);    //DB METHOD HERE
        //System.out.println("SIZE: " + allPoints.size());
        System.out.println("sql flight search; " + sql);
        return sql; //return an arraylist
    }

    /**
     * Returns the sql string for the filters specified
     * @param menusPressed The arraylist of strings for each menu selection
     * @param searchString The search string from the search bar
     * @return The sql filter string to be executed.
     */
    private static String routeFilter(ArrayList<String> menusPressed, String searchString) {
        ArrayList<String> allSelections = new ArrayList<>(Arrays.asList("Src=\"%s\" AND ", "Dst=\"%s\" AND ", "Stops=\"%s\" AND ", "(EQUIPMENT LIKE \"%%%s%%\") AND " ));

        String sql = getJoinForRoutesTableSql() ;
        boolean allNone = checkEmptyMenus(menusPressed);

        if (!allNone){
            sql = generateQueryString(sql, menusPressed, allSelections);
        }

        sql = removeLastAND(sql);
        sql = sql.replaceAll("%%", "");

        String search = "";
        if (searchString.length() >0){
            String searchStatement = "(IDnum=\"%1$s\" OR IDnum=\"%1$s\" OR AirlineID=\"%1$s\""
                    + "OR Src=\"%1$s\" OR SrcID=\"%1$s\" OR Dst=\"%1$s\" OR Dstid=\"%1$s\""
                    + "OR Codeshare=\"%1$s\" OR Stops=\"%1$s\" OR EQUIPMENT LIKE \"%%%1$s%%\"" +
                    " or srcname LIKE \"%%%1$s%%\" or srccountry LIKE  \"%%%1$s%%\" " +
                    " or dstname LIKE \"%%%1$s%%\" or dstcountry LIKE \"%%%1$s%%\" );";
            search = String.format(searchStatement, searchString);
            if(allNone){
                sql += " WHERE ";
            } else {
                sql += " AND ";
            }
        }

        sql += search;
        return sql;
    }

    /**
     * Returns the sql string for the filters specified
     * @param menusPressed The arraylist of strings for each menu selection
     * @param searchString The search string from the search bar
     * @return The sql filter string to be executed.
     */
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
        //System.out.println(sql);
        //allPoints = BBDatabase.performQuery(sql);     //PERFORM DB QUERY
        //return null;
        return sql;
    }

    /**
     * Returns the sql string for the filters specified
     * @param menusPressed The arraylist of strings for each menu selection
     * @param searchString The search string from the search bar
     * @return The sql filter string to be executed.
     */
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
        return sql; //return an arraylist
    }


//    public static ArrayList<DataPoint> filterRouteEquipment(ArrayList<DataPoint> routes, String equipment) {
//        ArrayList<DataPoint> equipmentRoutes = new ArrayList<>();
//        String patternString;
//        if (equipment.isEmpty()) {
//            patternString = "^$";
//        } else {
//            String[] newString = equipment.split(" ");
//            patternString = "\\b(" + String.join("|", newString) + ")\\b";
//        }
//
//        Pattern pattern = Pattern.compile(patternString);
//
//        for (DataPoint route : routes) {
//            RoutePoint myroute = (RoutePoint) route;    //casting here
//            Matcher matcher = pattern.matcher(myroute.getEquipment());
//            if (matcher.find()) {
//                equipmentRoutes.add(route);
//            }
//        }
//        return equipmentRoutes;
//    }


    /**
     * Method that helps us to filter unique entries within a specified column from a table. Used to populate
     * the filter dropdown menus.
     * @return The arraylist of distinct strings returned from the database
     * @see DataBaseRefactor
     */
    protected static ArrayList<String> filterDistinct(String column, String table) {    //input- relying on GUI to give the type and input e.g. Src, Dst??
        String sql = "SELECT DISTINCT %s from %s";
        sql = String.format(sql, column, table);
        ArrayList<String> menuItems = DataBaseRefactor.performDistinctQuery(sql);   //DB method to grab distinct stuff
        Collections.sort(menuItems, String.CASE_INSENSITIVE_ORDER);
        return menuItems;
    }


    protected static DataPoint findAirportPointForDistance(String airportToCalc){
        String sql = "SELECT * FROM AIRPORT WHERE name = \"%s\" AND icao = \"%s\"";
        String name = "";
        String icao = "";

        int i = airportToCalc.length()-1;

        while (airportToCalc.charAt(i) != ' '){
            i-=1;
        }

        name = airportToCalc.substring(0, i);
        icao = airportToCalc.substring(i+1, airportToCalc.length());

        sql = String.format(sql, name, icao);
        DataPoint airport = DataBaseRefactor.performGenericQuery(sql, DataTypes.AIRPORTPOINT).get(0);

        return airport;
    }


    //------------------------------------------HELPER FUNCTIONS----------------------------------------------------//

    private static String getJoinForAirportsTableSql(){
        String sql = " SELECT * FROM (select *, (select count(*) from route where route.Srcid = airport.id)  as incoming,\n" +
                "(select count(*) from route where route.dstid=airport.id)\n" +
                "as outgoing from airport\n)  ";
        return sql;
    }

    private static String getJoinForRoutesTableSql(){
        return " SELECT * FROM (SELECT route.*, a1.name as srcname, a1.country as srccountry, a2.name as dstname, a2.country as dstcountry\n" +
                "FROM route\n" +
                "LEFT JOIN airport as a1 ON route.Srcid =  a1.id LEFT JOIN airport as a2 ON route.Dstid = a2.id\n) ";
    }
    /**
     * Helper function to append to the current sql string using the selected filter dropdowns.
     * @param current The current sql string
     * @param menusPressed Items selected in the filter dropdowns
     * @param allSelections All of the filter menus, in order to iterate through them
     * @return The sql string updated with each filter dropdown selection
     */
    private static String generateQueryString(String current, ArrayList<String> menusPressed, ArrayList<String> allSelections) {
        current += "WHERE ";
        for (int i=0; i < menusPressed.size(); i++){
            String currentSelection = menusPressed.get(i);
            if(currentSelection != "None"){
                current += String.format(allSelections.get(i), currentSelection);
            }
        }
        return current;
    }

    /**
     * Helper function to see if all of the filter dropdowns have not been selected
     * @param menusPressed The arraylist of filter dropdown selections
     * @return A boolean value: true if all filter dropdowns have no selection; false if the user has made a dropdown selection
     */
    private static boolean checkEmptyMenus(ArrayList<String> menusPressed) {
        boolean allNone = true;
        for (String currentSelection: menusPressed){
            if (currentSelection != "None"){
                allNone = false;
            }
        }
        return allNone;
    }

    /**
     * Helper function to remove the last 'and' of a sql string, if 'and' is the last word.
     * @param sqlString The current sql string
     * @return The sql string with the last 'and' removed, if 'and' is the last word.
     */
    private static String removeLastAND(String sqlString) {

        String substring = sqlString.substring(sqlString.length()-4, sqlString.length()-1);
        if (substring.equals("AND")){
            sqlString = sqlString.substring(0, sqlString.length()-4);
        }
        return sqlString;
    }

    /**
     * Helper function to remove the last 'where' of a sql string, if 'where' is the last word.
     * @param sqlString The current sql string
     * @return The sql string with the last 'where' removed, if 'and' is the last word.
     */
    private static String removeLastWHERE(String sqlString) {
        //System.out.println(sqlString.substring(sqlString.length()-6, sqlString.length()-1));
        String substring = sqlString.substring(sqlString.length()-6, sqlString.length()-1);
        if (substring.equals("WHERE")){
            sqlString = sqlString.substring(0, sqlString.length()-6);
        }
        return sqlString;
    }

}

