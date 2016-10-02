package seng202.group2.blackbirdControl;

import junit.framework.TestCase;

/**
 * Created by emr65 on 22/09/16.
 */
public class ValidatorTest extends TestCase {

    public void testCheckAirline() throws Exception {

        String id = "1";                            //Int > 0
        String name = "Hello";                      //String < 40 char || empty
        String alias = "Helloy";                    //String < 40 char || empty
        String iata = "HL";                         //Upper String < 3 char || empty
        String icao = "HELL";                       //Upper String < 4 char || empty
        String callsign = "HEY THERE";              //String < 40 char || empty
        String country = "HELLO WORLD";             //String < 40 char || empty
        String active = "N";                        //Upper Char || empty



        String[] attributes = new String[] {id, name, alias, iata, icao, callsign, country, active};
        String[] checkData = Validator.checkAirline(attributes);
        assertTrue(HelperFunctions.allValid(checkData));

        iata = "11111";
        attributes = new String[] {id, name, alias, iata, icao, callsign, country, active};
        checkData = Validator.checkAirline(attributes);
        assertFalse(HelperFunctions.allValid(checkData));

    }

    public void testCheckAirport() throws Exception {

        String id = "1";                            //int > 0
        String name = "Bye";                        //String < 40 char || empty
        String city = "Byetown";                    //String < 40 char || empty
        String country = "Bye central";             //String < 40 char || empty
        String iata = "BY";                         //Upper String < 3 char || empty
        String icao = "BYTN";                       //Upper String < 4 char || empty
        String lat = "12.1251";                     // -90.0 <= Float <= +90.0 || empty
        String lon = "0.0000";                      // -180.0 <= Float <= +180.0 || empty
        String alt = "9999";                        // Float > 0 || empty
        String timeZone = "12.0";                   // -12.0 <= Float <= +14.0 || empty
        String dst = "A";                           // Upper Char || empty
        String tz = "The Goodbye zone/Farewll";     //String < 40 char || empty



        String[] attributes = new String[] {id, name, city, country, iata, icao, lat, lon, alt, timeZone, dst, tz};
        String[] checkData = Validator.checkAirport(attributes);
        assertTrue(HelperFunctions.allValid(checkData));

        lat = "9999999";
        attributes = new String[] {id, name, city, country, iata, icao, lat, lon, alt, timeZone, dst, tz};
        checkData = Validator.checkAirport(attributes);
        assertFalse(HelperFunctions.allValid(checkData));


    }

    public void testCheckRoute() throws Exception{
        String[] attributes = new String[] {"2B", "410", "AER", "Y", "1", "CR2"};
        boolean valid = Validator.checkRoute(attributes);
        assertEquals(true, valid);

        attributes = new String[] {"None", "410", "AER", "7", "KZN", "7", "hi"};
        assertEquals(false, Validator.checkRoute(attributes));

        attributes = new String[] {"2B", "410", "AER", "7", "KZN", "NOT_VALID"};
        assertEquals(false, Validator.checkRoute(attributes));
    }

    public void testCheckFlightPont() throws Exception{
        String[] attributes = new String[] {"1", "BAN", "1.23", "2.34", "3.45"};
        boolean valid = Validator.checkFlightPoint(attributes);
        assertEquals(true, valid);

        attributes = new String[] {"HI", "BAN", "apple", "2.34", "3.45"};
        valid = Validator.checkFlightPoint(attributes);
        assertEquals(false, valid);


    }

}