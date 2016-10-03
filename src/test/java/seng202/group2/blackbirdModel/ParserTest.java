package seng202.group2.blackbirdModel;

import junit.framework.TestCase;

import java.io.File;
import java.util.ArrayList;

/**
 * Tests for the Parser class
 */
public class ParserTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        String cwd = System.getProperty("user.dir");
        String airlinesFileString;
        String airportsFileString;
        String routesFileString;
        String flightFileString;

        airlinesFileString = cwd + "/JUnitTesting/airlines.txt";
        airportsFileString = cwd + "/JUnitTesting/airports.txt";
        routesFileString = cwd + "/JUnitTesting/route.txt";
        flightFileString = cwd + "/JUnitTesting/flight.txt";

        File airlinesFile = new File(airlinesFileString);
        File airportsFile = new File(airportsFileString);
        File routesFile = new File(routesFileString);
        File flightFile = new File(flightFileString);

        ArrayList<DataPoint> airlinePoints = Parser.parseFile(airlinesFile, DataTypes.AIRLINEPOINT, null);
        ArrayList<DataPoint> airportPoint = Parser.parseFile(airportsFile, DataTypes.AIRPORTPOINT, null);

        ArrayList<DataPoint> routePoints = Parser.parseFile(routesFile, DataTypes.ROUTEPOINT, null);
        ArrayList<DataPoint> flightPoints = Parser.parseFile(flightFile, DataTypes.FLIGHTPOINT, null);
       // System.out.println(airlinePoints.get(0).getType());

        Flight hello = new Flight(flightPoints);
        DataPoint myFlight = hello;
        myFlight.setType(DataTypes.FLIGHTPOINT);

        DataPoint myFlight2 = new Flight(flightPoints);
        //System.out.println(myFlight2.getType());
        //DataPoint myFlight3 = new DataPoint("Flight", flightPoints);
        //System.out.println(hello.getType());
        //DataPoint test = DataPoint.createDataPointFromStringArray(flightPoints, "FlightPoint");//new DataPoint(flightPoints);

        testDatatype(airlinePoints, DataTypes.AIRLINEPOINT);
        testDatatype(airportPoint, DataTypes.AIRPORTPOINT);
        testDatatype(routePoints, DataTypes.ROUTEPOINT);
        testDatatype(flightPoints, DataTypes.FLIGHTPOINT);
         //System.out.println(flightPoints.get(0).getType());
    }

    public void testDatatype(ArrayList<DataPoint> allPoints, DataTypes type){
        for (int i=0; i<allPoints.size(); i++){
            assertEquals(allPoints.get(i).getType(), type);
        }
    }

    public void testParseFlightData() throws Exception {

    }

}