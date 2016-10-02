package seng202.group2.blackbirdControl;

import javafx.scene.control.Alert;
import org.apache.commons.lang3.StringUtils;
import seng202.group2.blackbirdModel.DataBaseRefactor;

/**
 * Created by emr65 on 22/09/16.
 */
public class Validator {
    private static boolean isValidID(String id){

        if(id.equals("")){
            return false;
        }
        try {
            int num = Integer.parseInt(id);   // Checks to see if it is an int. However if it passes here it could still
            if(num > 0){
                return true;
            }
        }catch(NumberFormatException e){      // fail later at the database if it is not unique
            return false;
        }
        return false;

    }

    //ID IS A KEY ATTRIBUTE OF AIRLINE SO IS THE ONLY ATTRIBUTE THAT DOES NOT ALLOW CHECKING FOR EMPTY STRING!!!!

    private static boolean isValidName(String name){
         return (!name.equals("") && name.length() <= 40);
    }

    private static boolean isValidAlias(String alias){
        return (!alias.equals("") && alias.length() <= 40 || alias.equals(""));
    }

    private static boolean isValidIATA(String iata){
        return (!iata.equals("") && iata.length() <= 3 && isAllUpper(iata)|| iata.equals(""));
    }

    private static boolean isValidICAO(String icao){
        return (!icao.equals("") && icao.length() <= 4 && isAllUpper(icao) || icao.equals(""));
    }

    private static boolean isValidAirlineCallsign(String callsign){
        return (!callsign.equals("") && callsign.length() <= 40 || callsign.equals(""));
    }

    private static boolean isValidCountry(String country){
        return (!country.equals("") && country.length() <= 40);
    }

    private static boolean isValidAirlineCountry(String country){
        return (country.length() <= 40 && StringUtils.isAlphaSpace(country) || country.equals(""));
    }

    private static boolean isValidActive(String active){
        return (!active.equals("") && active.length() == 1 && isAllUpper(active)|| active.equals(""));
    }

    private static boolean isValidCity(String city){
        return (!city.equals("") && city.length() <= 40 || city.equals(""));
    }
    private static boolean isValidLocaleID(String localeID){
        return (!localeID.equals("") && localeID.length() <= 40 || localeID.equals(""));
    }

    private static boolean isValidType(String type){
        return (!type.equals("") && type.length() <= 40 || type.equals(""));
    }

    private static boolean isValidLat(String lat){

        if(lat.equals("")){
            return true;
        }
        try {
            float num = Float.parseFloat(lat);
            if(num <= 90 && num >= -90){
                return true;
            }
        }catch(NumberFormatException e){
            return false;
        }
        return false;

    }

    private static boolean isValidLong(String lon){

        if(lon.equals("")){
            return true;
        }
        try {
            float num = Float.parseFloat(lon);
            if(num <= 180.0 && num >= -180.0){
                return true;
            }
        }catch(NumberFormatException e){
            return false;
        }
        return false;

    }

    private static boolean isValidTimeZone(String timeZone){

        if(timeZone.equals("")){
            return true;
        }
        try {
            float num = Float.parseFloat(timeZone);
            if(num <= 14.0 && num >= -12.0){
                return true;
            }
        }catch(NumberFormatException e){
            return false;
        }
        return true;

    }

    private static boolean isValidAlt(String alt){

        if(alt.equals("")){
            return true;
        }
        try {
            float num = Float.parseFloat(alt);
            if(num >= 0.00){
                return true;
            }
        }catch(NumberFormatException e){
            return false;
        }
        return true;

    }

    private static boolean isValidDST(String DST){

        return ((!DST.equals("") && DST.length() == 1 && isAllUpper(DST) ) || DST.equals(""));

    }

    private static boolean isValidTZ(String TZ){

        return ((!TZ.equals("") && TZ.length() <= 40) || TZ.equals(""));

    }


    /**
     * Checks all attributes of an airline individually for validity. Checks type, length and required attributes.
     * @param attributes the attributes to be made into an airline point in the order:
     *                   id
     *                   name
     *                   alias
     *                   iata
     *                   icao
     *                   callsign
     *                   country
     *                   active
     * @return True if valid, False otherwise
     */
    public static boolean checkAirline(String[] attributes){

        String id = attributes[0];
        String name = attributes[1];
        String alias = attributes[2];
        String iata = attributes[3];
        String icao = attributes[4];
        String callsign = attributes[5];
        String country = attributes[6];
        String active = attributes[7];

//
//        System.out.println("--------VALIDATING AIRLINE---------");
//        if(!isValidID(id)){
//            System.out.println("Bad id");
//        }
//        if(!isValidName(name)){
//            System.out.println("Bad name");
//        }
//        if(!isValidAlias(alias)){
//            System.out.println("Bad alias");
//        }
//        if(!isValidIATA(iata)){
//            System.out.println("Bad iata");
//        }
//        if(!isValidICAO(icao)){
//            System.out.println("Bad icao");
//        }
//        if(!isValidAirlineCallsign(callsign)){
//            System.out.println("Bad callsign");
//        }
//        if(!isValidCountry(country)){
//            System.out.println("Bad country");
//        }
//        if(!isValidActive(active)){
//            System.out.println("Bad active");
//        }
//        System.out.println("================");


        return (isValidID(id) &&
                isValidName(name) &&
                isValidAlias(alias) &&
                isValidIATA(iata) &&
                isValidICAO(icao) &&
                isValidAirlineCallsign(callsign) &&
                isValidAirlineCountry(country) &&
                isValidActive(active));

    }

    /**
     * Checks all attributes of an Airport for validity before trying to perform actions on them. Checks type, lenghth
     * and validity
     * @param attributes The attributes to be made into an airport point in the order:
     *                   id
     *                   name
     *                   city
     *                   country
     *                   iata
     *                   icao
     *                   lat
     *                   lon
     *                   alt
     *                   timeZone
     *                   dst
     *                   tz
     * @return A boolean indicating whether the data is valid or not
     */
    public static String[] checkAirport(String[] attributes){

        String[] badData = new String[11];
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

        //System.out.println("----VALIDATING AIRPORT----");
        if(!isValidID(id)){
            badData[0] = "ID";
            //System.out.println("BAD ID");
        }
        if(!isValidName(name)){
            badData[1] = "name";
            //System.out.println("BAD name");
        }
        if(!isValidCity(city)){
            badData[2] = "city";
            //System.out.println("BAD city");
        }
        if(!isValidCountry(country)){
            badData[3] = "country";
            //System.out.println("BAD country");
        }
        if(!isValidIATA(iata)){
            badData[4] = "iata";
            //System.out.println("BAD iata");
        }
        if(!isValidICAO(icao)){
            badData[5] = "icao";
            //System.out.println("BAD icao");
        }
        if(!isValidLat(lat)){
            badData[6] = "lat";
            //System.out.println("BAD lat");
        }
        if(!isValidLong(lon)){
            badData[7] = "lon";
            //System.out.println("BAD lon");
        }
        if(!isValidAlt(alt)){
            badData[8] = "alt";
            //System.out.println("BAD alt");
        }
        if(!isValidTimeZone(timeZone)){
            badData[9] = "timeZone";
            //System.out.println("BAD timeZone");
        }
        if(!isValidDST(dst)){
            badData[10] = "dst";
            //System.out.println("BAD dst");
        }
        if(!isValidTZ(tz)){
            badData[11] = "tz";
            //System.out.println("BAD tz");
        }
        //System.out.println("------------------");

        return badData;

    }

    public static boolean checkRoute(String[] attributes){  //myAirline, mySource, myDest, routeCodeshare, routeStops, routeEquipment
        boolean valid = true;
        if (attributes[0].equals("None") || attributes[1].equals("None") || attributes[2].equals("None")) {
            valid = false;
        }
        boolean num = StringUtils.isNumeric(attributes[4]);
        if (!num) {
            valid = false;
        }

        boolean validEquip = StringUtils.isAlphanumericSpace(attributes[5]);
        if (!validEquip) {
            valid = false;
        }
        return valid;
    }

    public static boolean checkFlightPoint(String[] attributes){ // localeID, wayPointType, alt, lat, long

        String localeid = attributes[0];
        String type = attributes[1];
        String alt = attributes[2];
        String lat = attributes[3];
        String lng = attributes[4];

        return((isValidLocaleID(localeid) && !localeid.equals("")) &&
                (isValidType(type) && !type.equals("")) &&
                (isValidAlt(alt) && !alt.equals("")) &&
                (isValidLat(lat) && !lat.equals("")) &&
                (isValidLong(lng) && !lng.equals("")));

    }

    /**
     * A helper function for checking if all characters ar upper case
     * @param s The string to check
     * @return A Boolean indicating if it is in fact all upper case
     */
    private static boolean isAllUpper(String s) {
        for(char c : s.toCharArray()) {
            if(Character.isLetter(c) && Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    //IDK if this is the correct place for this function
    /**
     * A method to check if a table has the correct columns
     * @param table the table to check
     * @param columns the columns to checkare in the table
     * @return A boolean describing if the has
     */
    public static boolean tableColumnchecker(String table, String[] columns){
        boolean correct = true;
        for(String column : columns){
            if(DataBaseRefactor.checkDBForColumn(table, column) == false){
                correct = false;
            }
        }
        return correct;
    }

    public static void displayAirportError(String[] checkData) {
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
                "IATA:\t\t3 Letters, UpperCase\n" +
                "ICAO:\t\t3 Letters, UpperCase\n" +
                "Latitude:\t\t-90.0 < Decimal < +90.0\n" +
                "Longitude:\t-180.0 < Decimal < +180.0\n" +
                "Altitude:\t\tDecimal > 0\n" +
                "Time Zone:\tAny word");

        alert.showAndWait();
    }

}
