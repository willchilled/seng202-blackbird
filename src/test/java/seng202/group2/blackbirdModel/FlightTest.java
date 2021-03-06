package seng202.group2.blackbirdModel;

import junit.framework.TestCase;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by emr65 on 12/09/16.
 */
public class FlightTest extends TestCase {

    public void testGetSrcAirport() throws Exception {

        String cwd = System.getProperty("user.dir");
        String flightsFileString;
        flightsFileString = cwd + "/JUnitTesting/flight.txt";
        File flightsFile = new File(flightsFileString);

        ArrayList<FlightPoint> testFlightPoints = Parser.parseFlightData(flightsFile);
        Flight testFlight = new Flight(testFlightPoints);

        assertEquals("NZCH", testFlight.getSrcAirport());

    }

    public void testGetDestAirport() throws Exception {

        String cwd = System.getProperty("user.dir");
        String flightsFileString;
        flightsFileString = cwd + "/JUnitTesting/flight.txt";
        File flightsFile = new File(flightsFileString);

        ArrayList<FlightPoint> testFlightPoints = Parser.parseFlightData(flightsFile);
        Flight testFlight = new Flight(testFlightPoints);

        assertEquals("WSSS", testFlight.getDestAirport());

    }

    public void testGetFlightPoints() throws Exception {
        String cwd = System.getProperty("user.dir");
        String flightsFileString;
        flightsFileString = cwd + "/JUnitTesting/flight.txt";
        File flightsFile = new File(flightsFileString);

        ArrayList<FlightPoint> testFlightPoints = Parser.parseFlightData(flightsFile);
        Flight testFlight = new Flight(testFlightPoints);

        assertEquals(testFlightPoints, testFlight.getFlightPoints());
    }

}