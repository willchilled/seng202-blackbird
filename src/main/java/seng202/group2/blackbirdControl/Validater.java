package seng202.group2.blackbirdControl;

/**
 * Created by wmu16 on 21/09/16.
 * Class for checking the various attributes of data objects before performing tasks on them.
 */
public class Validater {


    private static boolean isValidID(String id){

        if(id.equals("")){
            return false;
        }
        try {
            int num = Integer.parseInt(id);   // Checks to see if it is an int. However if it passes here it could still
        }catch(NumberFormatException e){      // fail later at the database if it is not unique
            return false;
        }
        return true;

    }

    //ID IS A KEY ATTRIBUTE OF AIRLINE SO IS THE ONLY ATTRIBUTE THAT DOES NOT ALLOW CHECKING FOR EMPTY STRING!!!!

    private static boolean isValidName(String name){

        return ((!name.equals("") && name.length() <= 40) || name.equals(""));

    }

    private static boolean isValidAlias(String alias){

        return ((!alias.equals("") && alias.length() <= 40) || alias.equals(""));

    }

    private static boolean isValidIATA(String iata){

        return ((!iata.equals("") && iata.length() <= 3 && isAllUpper(iata)) || iata.equals(""));

    }

    private static boolean isValidICAO(String icao){

        return ((!icao.equals("") && icao.length() <= 4 && isAllUpper(icao)) || icao.equals(""));

    }

    private static boolean isValidAirlineCallsign(String callsign){

        return ((!callsign.equals("") && callsign.length() <= 40) || callsign.equals(""));

    }

    private static boolean isValidCountry(String country){

        return ((!country.equals("") && country.length() <= 40) || country.equals(""));

    }

    private static boolean isValidActive(String active){

        return ((!active.equals("") && (active.equals("Y") || active.equals("N"))) || active.equals(""));

    }

    private static boolean isValidCity(String city){

        return ((!city.equals("") && city.length() <= 40) || city.equals(""));

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
     *
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


//        System.out.println("Checking Data in Validater: ...");
//        if(!isValidAirlineID(id)){
//            System.out.println("Bad id");
//        }
//        if(!isValidName(name)){
//            System.out.println("Bad name");
//        }
//        if(!isValidAirlineAlias(alias)){
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
//        if(!isValidAirlineCountry(country)){
//            System.out.println("Bad country");
//        }
//        if(!isValidActive(active)){
//            System.out.println("Bad active");
//        }

        return (isValidID(id) &&
                isValidName(name) &&
                isValidAlias(alias) &&
                isValidIATA(iata) &&
                isValidICAO(icao) &&
                isValidAirlineCallsign(callsign) &&
                isValidCountry(country) &&
                isValidActive(active));

    }

    public static boolean checkAirport(String[] attributes){

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

        if(!isValidID(id)){
            System.out.println("BAD ID");
        }
        if(!isValidName(name)){
            System.out.println("BAD name");
        }
        if(!isValidCity(city)){
            System.out.println("BAD city");
        }
        if(!isValidCountry(country)){
            System.out.println("BAD country");
        }
        if(!isValidIATA(iata)){
            System.out.println("BAD iata");
        }
        if(!isValidICAO(icao)){
            System.out.println("BAD icao");
        }
        if(!isValidLat(lat)){
            System.out.println("BAD lat");
        }
        if(!isValidLong(lon)){
            System.out.println("BAD lon");
        }
        if(!isValidAlt(alt)){
            System.out.println("BAD alt");
        }
        if(!isValidTimeZone(timeZone)){
            System.out.println("BAD timeZone");
        }
        if(!isValidDST(dst)){
            System.out.println("BAD dst");
        }
        if(!isValidTZ(tz)){
            System.out.println("BAD tz");
        }

        return (isValidID(id) &&
                isValidName(name) &&
                isValidCity(city) &&
                isValidCountry(country) &&
                isValidIATA(iata) &&
                isValidICAO(icao) &&
                isValidLat(lat) &&
                isValidLong(lon) &&
                isValidAlt(alt) &&
                isValidTimeZone(timeZone) &&
                isValidDST(dst) &&
                isValidTZ(tz));

    }

    private static boolean isAllUpper(String s) {
        for(char c : s.toCharArray()) {
            if(Character.isLetter(c) && Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }



}
