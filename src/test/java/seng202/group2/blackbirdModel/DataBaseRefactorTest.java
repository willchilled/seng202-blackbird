package seng202.group2.blackbirdModel;

import junit.framework.TestCase;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by sha162 on 17/09/16.
 */
public class DataBaseRefactorTest extends TestCase {
    public void setUp() throws Exception {
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

        // ArrayList<AirlinePoint> airlinePoints = Parser.parseAirlineData(airlinesFile);
        // ArrayList<AirportPoint> airportPoints = Parser.parseAirportData(airportsFile);
        //ArrayList<RoutePoint> routePoints = Parser.parseRouteData(routesFile);


        ArrayList<DataPoint> airlinePoints = ParserRefactor.parseFile(airlinesFile, "AirlinePoint");
        ArrayList<DataPoint> airportPoint = ParserRefactor.parseFile(airportsFile, "AirportPoint");

        ArrayList<DataPoint> routePoints = ParserRefactor.parseFile(routesFile, "RoutePoint");
        ArrayList<DataPoint> flightPoints = ParserRefactor.parseFile(flightFile, "FlightPoint");


        DataBaseRefactor.createTables();
        DataBaseRefactor.insertDataPoints(airlinePoints);



    }

    public void testInsertDataPoints() throws Exception {

    }

}