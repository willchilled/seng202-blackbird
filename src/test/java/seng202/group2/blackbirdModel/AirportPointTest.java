package seng202.group2.blackbirdModel;

import junit.framework.TestCase;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by emr65 on 14/09/16.
 */
public class AirportPointTest extends TestCase {
    public void tearDown() throws Exception {

    }

    public void testGetAirportID() throws Exception {

    }

    public void testGetCorrectEntry() throws Exception {
        String cwd = System.getProperty("user.dir");
        String airportFileString;
        airportFileString = cwd + "/JUnitTesting/airports.txt";
        File airportFile = new File(airportFileString);

        ArrayList<AirportPoint> testAirportPoints = Parser.parseAirportData(airportFile);



        AirportPoint testWorking = testAirportPoints.get(0);
       // AirportPoint testNotWorking = testAirportPoints.get(4647);


        assertEquals(Boolean.TRUE, testWorking.getCorrectEntry());
        //assertEquals(Boolean.FALSE, testNotWorking.getCorrectEntry());



       // assertEquals("NZCH", testFlight.getSrcAirport());


    }

    public void testSetCorrectEntry() throws Exception {

    }

}