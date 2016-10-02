package seng202.group2.blackbirdControl;

import seng202.group2.blackbirdModel.*;

import java.util.*;

/**
 * This class helps us to perform filters on the inputted data by generating sql strings to be performed for
 * a filter query, then performing this query via the database methods.
 */
public class Filter {

    /**
     * Gets all points within the database of the specified type
     *
     * @param type The type of data that we want
     * @return The arraylist of datapoints returned from the database query.
     * @see DataBaseRefactor
     */
    public static ArrayList<DataPoint> getAllPoints(DataTypes type) {
        String sql;
        switch (type) {
            case AIRLINEPOINT:
                sql = "SELECT * FROM AIRLINE";
                break;
            case AIRPORTPOINT:
                sql = "SELECT * FROM AIRPORT";
                ArrayList<DataPoint> myAirportPoints = DataBaseRefactor.performGenericQuery(sql, type);
                myAirportPoints = linkRoutesandAirports(myAirportPoints);
                return myAirportPoints;
            case ROUTEPOINT:
                sql = getJoinForRoutesTableSql();
                break;
            case FLIGHT:
                sql = "SELECT * FROM FLIGHT;";
                break;
            default:
                return null;
        }
        return DataBaseRefactor.performGenericQuery(sql, type);
    }

    /**
     * Finds the number of incoming and outoging routes for each airport
     *
     * @param myAirportPoints The list of airport pints wanting to be linked
     * @return airport points with number of incoming and outgoing routes set
     */
    private static ArrayList<DataPoint> linkRoutesandAirports(ArrayList<DataPoint> myAirportPoints) {
        ArrayList<DataPoint> linkedAirportPoints = new ArrayList<>();
        ArrayList<DataPoint> myRoutes = getAllPoints(DataTypes.ROUTEPOINT);
        Map<Integer, Integer> incomingRoutesDict = new HashMap<>();
        Map<Integer, Integer> outgoingRoutesDict = new HashMap<>();

        for (DataPoint myPoint : myAirportPoints) {
            int key = ((AirportPoint) myPoint).getAirportID();
            incomingRoutesDict.put(key, 0);
            outgoingRoutesDict.put(key, 0);
        }

        for (DataPoint myPoint : myRoutes) {
            try {
                RoutePoint currentRoutePoint = (RoutePoint) myPoint;
                int outGoingRouteKey = currentRoutePoint.getSrcAirportID();
                int incomingRouteKey = currentRoutePoint.getDstAirportID();

                int currentOutAmount = outgoingRoutesDict.get(outGoingRouteKey);
                outgoingRoutesDict.put(outGoingRouteKey, currentOutAmount + 1);

                int currentIncAmount = incomingRoutesDict.get(incomingRouteKey);
                incomingRoutesDict.put(incomingRouteKey, currentIncAmount + 1);
            } catch (Exception e) {
                //empty
            }
        }

        for (DataPoint myPoint : myAirportPoints) {
            AirportPoint currentPoint = (AirportPoint) myPoint;
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
     *
     * @param menusPressed The arraylist of strings for each menu selection
     * @param searchString The search string from the search bar
     * @param type         The type of data we are filtering
     * @return The arraylist of datapoints returned from the filter query.
     * @see DataBaseRefactor
     */
    static ArrayList<DataPoint> filterSelections(ArrayList<String> menusPressed, String searchString, DataTypes type) {
        ArrayList<DataPoint> filtered;
        String myQuery;
        switch (type) {
            case AIRLINEPOINT:
                myQuery = airlineFilter(menusPressed, searchString);
                break;
            case AIRPORTPOINT:
                myQuery = airportFilter(menusPressed, searchString);
                ArrayList<DataPoint> myAirportPoints = DataBaseRefactor.performGenericQuery(myQuery, type);
                myAirportPoints = linkRoutesandAirports(myAirportPoints);
                return myAirportPoints;
            case ROUTEPOINT:
                myQuery = routeFilter(menusPressed, searchString); //a special case because we want to filter by selections not in the database
                return DataBaseRefactor.performGenericQuery(myQuery, type);
            case FLIGHT:
                myQuery = flightFilter(menusPressed, searchString);
                break;
            default:
                return null;
        }
        filtered = DataBaseRefactor.performGenericQuery(myQuery, type);
        return filtered;
    }

    /**
     * Returns the sql string for the filters specified
     *
     * @param menusPressed The arraylist of strings for each menu selection
     * @param searchString The search string from the search bar
     * @return The sql filter string to be executed.
     */
    private static String flightFilter(ArrayList<String> menusPressed, String searchString) {
        String sql = "SELECT * FROM FLIGHT ";
        ArrayList<String> allSelections = new ArrayList<>(Arrays.asList("SrcICAO=\"%s\" AND ", "DstICAO=\"%s\" AND "));
        boolean allNone = checkEmptyMenus(menusPressed);
        if (!allNone) {
            sql = generateQueryString(sql, menusPressed, allSelections);
        }
        sql = removeLastAND(sql);
        String search = "";
        if (searchString.length() > 0) {
            search = String.format("(SrcICAO LIKE '%%%1$s%%' OR DstICAO LIKE '%%%1$s%%')", searchString);
            if (allNone) {
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
     *
     * @param menusPressed The arraylist of strings for each menu selection
     * @param searchString The search string from the search bar
     * @return The sql filter string to be executed.
     */
    private static String routeFilter(ArrayList<String> menusPressed, String searchString) {
        ArrayList<String> allSelections = new ArrayList<>(Arrays.asList("Src=\"%s\" AND ", "Dst=\"%s\" AND ", "Stops=\"%s\" AND ", "(EQUIPMENT LIKE \"%%%s%%\") AND "));
        String sql = getJoinForRoutesTableSql();
        boolean allNone = checkEmptyMenus(menusPressed);
        if (!allNone) {
            sql = generateQueryString(sql, menusPressed, allSelections);
        }
        sql = removeLastAND(sql);
        sql = sql.replaceAll("%%", ""); //if empty equipment has been selected as the equipment filter

        String search = "";
        if (searchString.length() > 0) {
            String searchStatement = "(IDnum LIKE \"%%%1$s%%\" OR IDnum LIKE \"%%%1$s%%\" OR AirlineID LIKE \"%%%1$s%%\""
                    + "OR Src LIKE \"%%%1$s%%\" OR SrcID LIKE \"%%%1$s%%\" OR Dst LIKE \"%%%1$s%%\" OR Dstid LIKE \"%%%1$s%%\""
                    + "OR Codeshare LIKE \"%%%1$s%%\" OR Stops LIKE \"%%%1$s%%\" OR EQUIPMENT LIKE \"%%%1$s%%\"" +
                    " or srcname LIKE \"%%%1$s%%\" or srccountry LIKE  \"%%%1$s%%\" " +
                    " or dstname LIKE \"%%%1$s%%\" or dstcountry LIKE \"%%%1$s%%\" );";
            search = String.format(searchStatement, searchString);
            if (allNone) {
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
     *
     * @param menusPressed The arraylist of strings for each menu selection
     * @param searchString The search string from the search bar
     * @return The sql filter string to be executed.
     */
    private static String airportFilter(ArrayList<String> menusPressed, String searchString) {
        String sql = "SELECT * FROM AIRPORT ";
        boolean allNone = checkEmptyMenus(menusPressed);
        if (!allNone) {
            sql += "WHERE COUNTRY=\"" + menusPressed.get(0) + "\"";
        }
        String search = "";
        if (searchString.length() > 0) {
            if (!allNone) {
                sql += " AND ";
            } else {
                sql += " WHERE ";
            }
            search = String.format("(ID LIKE \"%%%1$s%%\" OR NAME LIKE \"%%%1$s%%\" OR CITY LIKE \"%%%1$s%%\" OR COUNTRY LIKE \"%%%1$s%%\"" +
                    " OR IATA LIKE \"%%%1$s%%\" OR ICAO LIKE \"%%%1$s%%\" OR LATITUDE LIKE \"%%%1$s%%\" OR LONGITUDE LIKE \"%%%1$s%%\" OR ALTITUDE LIKE \"%%%1$s%%\"" +
                    " OR TIMEZONE LIKE \"%%%1$s%%\" OR DST LIKE \"%%%1$s%%\" OR TZ LIKE \"%%%1$s%%\");", searchString);
        }
        sql += search;
        return sql;
    }

    /**
     * Returns the sql string for the filters specified
     *
     * @param menusPressed The arraylist of strings for each menu selection
     * @param searchString The search string from the search bar
     * @return The sql filter string to be executed.
     */
    private static String airlineFilter(ArrayList<String> menusPressed, String searchString) {
        String sql = "SELECT * FROM AIRLINE ";
        ArrayList<String> allSelections = new ArrayList<>(Arrays.asList("COUNTRY=\"%s\" AND ", "ACTIVE=\"%s\" AND "));
        boolean allNone = checkEmptyMenus(menusPressed);
        if (!allNone) {
            sql = generateQueryString(sql, menusPressed, allSelections);
        }
        sql = removeLastAND(sql);
        String search = "";
        if (searchString.length() > 0) {
            search = String.format("(ID LIKE \"%%%1$s%%\" OR NAME LIKE \"%%%1$s%%\" OR ALIAS LIKE \"%%%1$s%%\" " +
                    "OR IATA LIKE \"%%%1$s%%\" OR ICAO LIKE \"%%%1$s%%\" OR CALLSIGN LIKE \"%%%1$s%%\" OR COUNTRY LIKE \"%%%1$s%%\" OR ACTIVE LIKE \"%%%1$s%%\");", searchString);
            if (allNone) {
                sql += " WHERE ";
            } else {
                sql += " AND ";
            }
        }
        sql += search;
        return sql;
    }

    /**
     * Method that helps us to filter unique entries within a specified column from a table. Used to populate
     * the filter dropdown menus.
     *
     * @return The arraylist of distinct strings returned from the database
     * @see DataBaseRefactor
     */
    protected static ArrayList<String> filterDistinct(String column, String table) {
        String sql = "SELECT DISTINCT %s from %s";
        sql = String.format(sql, column, table);
        ArrayList<String> menuItems = DataBaseRefactor.performDistinctQuery(sql);
        Collections.sort(menuItems, String.CASE_INSENSITIVE_ORDER);
        return menuItems;
    }

    /**
     * Helper function to grab the selected airport from the drop down box within the analysis tab, where the dropdowns
     * have airport names and their ICAO codes displayed together, by splitting the selection at the position of the last
     * space character.
     *
     * @param airportToCalc The selection from the dropdown box within the analysis tab
     * @return The selected airport point
     */
    static DataPoint findAirportPointForDistance(String airportToCalc) {
        String sql = "SELECT * FROM AIRPORT WHERE name = \"%s\" AND icao = \"%s\"";
        int i = airportToCalc.length() - 1;
        while (airportToCalc.charAt(i) != ' ') {
            i -= 1;
        }
        String name = airportToCalc.substring(0, i);
        String icao = airportToCalc.substring(i + 1, airportToCalc.length());
        sql = String.format(sql, name, icao);
        return DataBaseRefactor.performGenericQuery(sql, DataTypes.AIRPORTPOINT).get(0);
    }


    //------------------------------------------HELPER FUNCTIONS----------------------------------------------------//


    /**
     * Helper function to create the sql statement to link routes and airports together
     *
     * @return The sql string
     */
    private static String getJoinForRoutesTableSql() {
        return " SELECT * FROM (SELECT route.*, a1.name as srcname, a1.country as srccountry, a2.name as dstname, a2.country as dstcountry\n" +
                "FROM route\n" +
                "LEFT JOIN airport as a1 ON route.Srcid =  a1.id LEFT JOIN airport as a2 ON route.Dstid = a2.id\n) ";
    }

    /**
     * Helper function to append to the current sql string using the selected filter dropdowns.
     *
     * @param current       The current sql string
     * @param menusPressed  Items selected in the filter dropdowns
     * @param allSelections All of the filter menus, in order to iterate through them
     * @return The sql string updated with each filter dropdown selection
     */
    private static String generateQueryString(String current, ArrayList<String> menusPressed, ArrayList<String> allSelections) {
        current += "WHERE ";
        for (int i = 0; i < menusPressed.size(); i++) {
            String currentSelection = menusPressed.get(i);
            if (!currentSelection.equals("None")) {
                current += String.format(allSelections.get(i), currentSelection);
            }
        }
        return current;
    }

    /**
     * Helper function to see if all of the filter dropdowns have not been selected
     *
     * @param menusPressed The arraylist of filter dropdown selections
     * @return A boolean value: true if all filter dropdowns have no selection; false if the user has made a dropdown selection
     */
    private static boolean checkEmptyMenus(ArrayList<String> menusPressed) {
        boolean allNone = true;
        for (String currentSelection : menusPressed) {
            if (!currentSelection.equals("None")) {
                allNone = false;
            }
        }
        return allNone;
    }

    /**
     * Helper function to remove the last 'and' of a sql string, if 'and' is the last word.
     *
     * @param sqlString The current sql string
     * @return The sql string with the last 'and' removed, if 'and' is the last word.
     */
    private static String removeLastAND(String sqlString) {
        String substring = sqlString.substring(sqlString.length() - 4, sqlString.length() - 1);
        if (substring.equals("AND")) {
            sqlString = sqlString.substring(0, sqlString.length() - 4);
        }
        return sqlString;
    }

}

