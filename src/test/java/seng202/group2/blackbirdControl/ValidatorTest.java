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
        assertTrue(Validator.checkAirline(attributes));

        iata = "11111";
        attributes = new String[] {id, name, alias, iata, icao, callsign, country, active};
        assertFalse(Validator.checkAirline(attributes));

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
        assertTrue(Validator.checkAirport(attributes));

        lat = "9999999";
        attributes = new String[] {id, name, city, country, iata, icao, lat, lon, alt, timeZone, dst, tz};
        assertFalse(Validator.checkAirport(attributes));


    }

}