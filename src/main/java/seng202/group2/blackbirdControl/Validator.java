package seng202.group2.blackbirdControl;

import javafx.scene.control.Alert;
import org.apache.commons.lang3.StringUtils;
import seng202.group2.blackbirdModel.DatabaseInterface;

/**
 * This class acts to help validate if entries are valid, when manually adding data. These checks are performed
 * before adding to the database, in order to give users a detailed help message for adding.
 */
public class Validator {

    private static boolean isValidID(String id) {
        if (id.equals("")) {
            return false;
        }
        try {
            int num = Integer.parseInt(id);
            if (num > 0) {
                return true;
            }
        } catch (NumberFormatException e) {      // fail later at the database if it is not unique
            return false;
        }
        return false;
    }

    private static boolean isValidName(String name) {
        return (!name.equals("") && name.length() <= 40);
    }

    private static boolean isValidAlias(String alias) {
        return (!alias.equals("") && alias.length() <= 40 || alias.equals(""));
    }

    private static boolean isValidIATA(String iata) {
        return (!iata.equals("") && iata.length() <= 3 && isAllUpper(iata) || iata.equals(""));
    }

    private static boolean isValidICAO(String icao) {
        return (!icao.equals("") && icao.length() <= 4 && isAllUpper(icao) || icao.equals(""));
    }

    private static boolean isValidAirlineCallsign(String callsign) {
        return (!callsign.equals("") && callsign.length() <= 40 || callsign.equals(""));
    }

    private static boolean isValidCountry(String country) {
        return (!country.equals("") && country.length() <= 40);
    }

    private static boolean isValidAirlineCountry(String country) {
        return (country.length() <= 40 && StringUtils.isAlphaSpace(country) || country.equals(""));
    }

    private static boolean isValidActive(String active) {
        return (!active.equals("") && active.length() == 1 && isAllUpper(active) || active.equals(""));
    }

    private static boolean isValidCity(String city) {
        return (!city.equals("") && city.length() <= 40 || city.equals(""));
    }

    private static boolean isValidLocaleID(String localeID) {
        return (!localeID.equals("") && localeID.length() <= 40 || localeID.equals(""));
    }

    private static boolean isValidType(String type) {
        return (!type.equals("") && type.length() <= 40 || type.equals(""));
    }

    private static boolean isValidLat(String lat) {
        if (lat.equals("")) {
            return true;
        }
        try {
            float num = Float.parseFloat(lat);
            if (num <= 90 && num >= -90) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    private static boolean isValidLong(String lon) {
        if (lon.equals("")) {
            return true;
        }
        try {
            float num = Float.parseFloat(lon);
            if (num <= 180.0 && num >= -180.0) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    private static boolean isValidTimeZone(String timeZone) {
        if (timeZone.equals("")) {
            return true;
        }
        try {
            float num = Float.parseFloat(timeZone);
            if (num <= 14.0 && num >= -12.0) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static boolean isValidAlt(String alt) {
        if (alt.equals("")) {
            return true;
        }
        try {
            float num = Float.parseFloat(alt);
            if (num >= 0.00) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static boolean isValidDST(String DST) {
        return ((!DST.equals("") && DST.length() == 1 && isAllUpper(DST)) || DST.equals(""));
    }

    private static boolean isValidTZ(String TZ) {
        return ((!TZ.equals("") && TZ.length() <= 40) || TZ.equals(""));
    }


    /**
     * Checks each attribute of an attempt to create a AirlinePoint for correctness. If the attribute is wrong, it's name
     * is added to a bad data list which is then passed to the error message to tell the user what is wrong
     *
     * @param attributes Attributes of the datapoint for attempted creation
     * @return String List of any bad data fields
     */
    static String[] checkAirline(String[] attributes) {
        String[] badData = new String[8];
        String id = attributes[0];
        String name = attributes[1];
        String alias = attributes[2];
        String iata = attributes[3];
        String icao = attributes[4];
        String callsign = attributes[5];
        String country = attributes[6];
        String active = attributes[7];

        if (!isValidID(id)) {
            badData[0] = "ID";
        }
        if (!isValidName(name)) {
            badData[1] = "Name";
        }
        if (!isValidAlias(alias)) {
            badData[2] = "Alias";
        }
        if (!isValidIATA(iata)) {
            badData[3] = "IATA";
        }
        if (!isValidICAO(icao)) {
            badData[4] = "ICAO";
        }
        if (!isValidAirlineCallsign(callsign)) {
            badData[5] = "Callsign";
        }
        if (!isValidAirlineCountry(country)) {
            badData[6] = "Country";
        }
        if (!isValidActive(active)) {
            badData[7] = "Active";
        }
        return badData;
    }

    /**
     * Checks each attribute of an attempt to create anAirportPoints for correctness. If the attribute is wrong, it's name
     * is added to a bad data list which is then passed to the error message to tell the user what is wrong
     *
     * @param attributes Attributes of the datapoint for attempted creation
     * @return String List of any bad data fields
     */
    static String[] checkAirport(String[] attributes) {
        String[] badData = new String[12];
        String id = attributes[0];
        String name = attributes[1];
        String city = attributes[2];
        String country = attributes[3];
        String iata = attributes[4];
        String icao = attributes[5];
        String lat = attributes[6];
        String lon = attributes[7];
        String alt = attributes[8];
        String timeZone = attributes[9];
        String dst = attributes[10];
        String tz = attributes[11];

        if (!isValidID(id)) {
            badData[0] = "ID";
        }
        if (!isValidName(name)) {
            badData[1] = "name";
        }
        if (!isValidCity(city)) {
            badData[2] = "city";
        }
        if (!isValidCountry(country)) {
            badData[3] = "country";
        }
        if (!isValidIATA(iata)) {
            badData[4] = "iata";
        }
        if (!isValidICAO(icao)) {
            badData[5] = "icao";
        }
        if (!isValidLat(lat)) {
            badData[6] = "lat";
        }
        if (!isValidLong(lon)) {
            badData[7] = "lon";
        }
        if (!isValidAlt(alt)) {
            badData[8] = "alt";
        }
        if (!isValidTimeZone(timeZone)) {
            badData[9] = "timeZone";
        }
        if (!isValidDST(dst)) {
            badData[10] = "dst";
        }
        if (!isValidTZ(tz)) {
            badData[11] = "tz";
        }
        return badData;
    }

    /**
     * Checks each attribute of an attempt to create a RoutePoint for correctness. If the attribute is wrong, it's name
     * is added to a bad data list which is then passed to the error message to tell the user what is wrong
     *
     * @param attributes Attributes of the datapoint for attempted creation
     * @return String List of any bad data fields
     */
    static String[] checkRoute(String[] attributes) {  //myAirline, mySource, myDest, routeCodeshare, routeStops, routeEquipment
        String[] badData = new String[6];
        if (attributes[0].equals("None")) {
            badData[0] = "Airline";
        }
        if (attributes[1].equals("None")) {
            badData[1] = "Src Airport";
        }
        if (attributes[2].equals("None")) {
            badData[2] = "Dst Airport";
        }
        boolean num = StringUtils.isNumeric(attributes[4]);
        if (!num) {
            badData[4] = "Codeshare";
        }
        boolean validEquip = StringUtils.isAlphanumericSpace(attributes[5]);
        if (!validEquip) {
            badData[5] = "Equipment";
        }
        return badData;
    }

    /**
     * Checks each attribute of an attempt to create a FlightPoint for correctness. If the attribute is wrong, it's name
     * is added to a bad data list which is then passed to the error message to tell the user what is wrong
     *
     * @param attributes Attributes of the datapoint for attempted creation
     * @return String List of any bad data fields
     */
    static String[] checkFlightPoint(String[] attributes) { // type, id, alt, lat, long
        String[] badData = new String[5];
        String type = attributes[0];
        String localeID = attributes[1];
        String alt = attributes[2];
        String lat = attributes[3];
        String lng = attributes[4];
        if (!(isValidLocaleID(type) && !type.equals(""))) {
            badData[0] = "Type";
        }
        if (!(isValidType(localeID) && !localeID.equals(""))) {
            badData[1] = "LocaleID";
        }
        if (!(isValidAlt(alt) && !alt.equals(""))) {
            badData[2] = "Altitude";
        }
        if (!(isValidLat(lat) && !lat.equals(""))) {
            badData[3] = "Latitude";
        }
        if (!(isValidLong(lng) && !lng.equals(""))) {
            badData[4] = "Longitude";
        }
        return badData;
    }

    /**
     * A helper function for checking if all characters ar upper case
     *
     * @param s The string to check
     * @return A Boolean indicating if it is in fact all upper case
     */
    private static boolean isAllUpper(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c) && Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * A method to check if a table has the correct columns
     *
     * @param table   the table to check
     * @param columns the columns to checkare in the table
     * @return A boolean describing if the has
     */
    static boolean tableColumnchecker(String table, String[] columns) {
        boolean correct = true;
        for (String column : columns) {
            if (!DatabaseInterface.checkDBForColumn(table, column)) {
                correct = false;
            }
        }
        return correct;
    }

    /**
     * Formats and displays the error message upon given errors for AirportPoints when a user tries to create a point
     *
     * @param checkData The array of attributes of a dataPoint attempted to be created which has errors.
     */
    static void displayAirportError(String[] checkData) {
        String errorMessage = "Errors with: ";
        for (String error : checkData) {
            if (error != null) {
                errorMessage += (error + ", ");
            }
        }
        errorMessage = errorMessage.substring(0, errorMessage.length() - 2);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("IVALID DATA");
        alert.setHeaderText(errorMessage);
        alert.setContentText("Constraints:\n\n" +
                "*REQUIRED*:\tName, Country\n\n" +
                "Name:\t\tAny word\n" +
                "City:\t\t\tAny word\n" +
                "Country:\t\tAny word\n" +
                "IATA:\t\t 3 Letters, UpperCase\n" +
                "ICAO:\t\t4 Letters, UpperCase\n" +
                "Latitude:\t\t-90.0 < Latitude < +90.0\n" +
                "Longitude:\t-180.0 < Longitude < +180.0\n" +
                "Altitude:\t\tDecimal > 0\n" +
                "Time Zone:\tAny word");
        alert.showAndWait();
    }

    /**
     * Formats and displays the error message upon given errors for AirlinePoints when a user tries to create a point
     *
     * @param checkData The array of attributes of a dataPoint attempted to be created which has errors.
     */
    static void displayAirlineError(String[] checkData) {
        String errorMessage = "Errors with: ";
        for (String error : checkData) {
            if (error != null) {
                errorMessage += (error + ", ");
            }
        }
        errorMessage = errorMessage.substring(0, errorMessage.length() - 2);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("IVALID DATA");
        alert.setHeaderText(errorMessage);
        alert.setContentText("Constraints:\n\n" +
                "*REQUIRED*:\tName\n\n" +
                "Name:\t\tAny word\n" +
                "Alias:\t\tAny word\n" +
                "IATA:\t\t2 Letters, UpperCase\n" +
                "ICAO:\t\t3 Letters, UpperCase\n" +
                "Callsign:\t\tAny word\n" +
                "Country:\t\tAny word");
        alert.showAndWait();
    }

    /**
     * Formats and displays the error message upon given errors for RoutePoints when a user tries to create a point
     *
     * @param checkData The array of attributes of a dataPoint attempted to be created which has errors.
     */
    static void displayRouteError(String[] checkData) {
        String errorMessage = "Errors with: ";
        for (String error : checkData) {
            if (error != null) {
                errorMessage += (error + ", ");
            }
        }
        errorMessage = errorMessage.substring(0, errorMessage.length() - 2);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("IVALID DATA");
        alert.setHeaderText(errorMessage);
        alert.setContentText("Constraints:\n\n" +
                "*REQUIRED*:\tSrc Airport, Airline, Dst Airport\n\n" +
                "Src Airport:\t\tAny Selection\n" +
                "Dst Airport:\t\tAny Selection\n" +
                "Airline:\t\t\tAny Selection\n" +
                "Stops:\t\t\tSingle Integer\n" +
                "Equipment:\t\tAny Alphanumeric Sequence");

        alert.showAndWait();
    }

    /**
     * Formats and displays the error message upon given errors for FlightPoints when a user tries to create a point
     *
     * @param checkData The array of attributes of a dataPoint attempted to be created which has errors.
     */
    static void displayFlightPointError(String[] checkData) {
        String errorMessage = "Errors with: ";
        for (String error : checkData) {
            if (error != null) {
                errorMessage += (error + ", ");
            }
        }
        errorMessage = errorMessage.substring(0, errorMessage.length() - 2);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("IVALID DATA");
        alert.setHeaderText(errorMessage);
        alert.setContentText("Constraints:\n\n" +
                "*REQUIRED*:\tlocaleID, wayPointType, alt, lat, long\n\n" +
                "LocaleID:\t\t\tAny Alphanumeric Sequence\n" +
                "WayPointType:\t\tAny Selection\n" +
                "Altitude:\t\t\tAltitude > 0\n" +
                "Latitude:\t\t\t-90.0 < Latitude < +90.0\n" +
                "Longitude:\t\t-180.0 < Longitude < +180.0");

        alert.showAndWait();
    }

}
