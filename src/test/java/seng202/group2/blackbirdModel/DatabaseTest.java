package seng202.group2.blackbirdModel;

import junit.framework.TestCase;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by sha162 on 17/09/16.
 */
public class DatabaseTest extends TestCase {

    public void setUp() throws Exception {
        String cwd = System.getProperty("user.dir");
        String airlinesFileString;
        String airportsFileString;
        String routesFileString;
        String flightFileString;

        airlinesFileString = cwd + "/JUnitTesting/airlines.txt";
        airportsFileString = cwd + "/JUnitTesting/airports.txt";
        routesFileString = cwd + "/JUnitTesting/routesWithNoFailing";
        flightFileString = cwd + "/JUnitTesting/flight.txt";

        File airlinesFile = new File(airlinesFileString);
        File airportsFile = new File(airportsFileString);
        File routesFile = new File(routesFileString);
        File flightFile = new File(flightFileString);


        ArrayList<DataPoint> airlinePoints = Parser.parseFile(airlinesFile, DataTypes.AIRLINEPOINT, null);
        ArrayList<DataPoint> airportPoint = Parser.parseFile(airportsFile, DataTypes.AIRPORTPOINT, null);

        ArrayList<DataPoint> routePoints = Parser.parseFile(routesFile, DataTypes.ROUTEPOINT, null);
        ArrayList<DataPoint> flightPoints = Parser.parseFile(flightFile, DataTypes.FLIGHTPOINT, null);

        Flight flight = new Flight(flightPoints);
        flight.setType(DataTypes.FLIGHT);
        DataPoint f = flight;
        ArrayList<DataPoint> myFlight = new ArrayList<>();
        myFlight.add(f);
        // System.out.println(flight.getType() + "--------------------------");


        Database.createTables();
        Database.insertDataPoints(airlinePoints, null);
        Database.insertDataPoints(airportPoint, null);
        Database.insertDataPoints(routePoints, null);
        Database.insertDataPoints(myFlight, null);

        Database.insertDataPoints(flightPoints, null);

    }

    public void testInsertDataPoints() throws Exception {

    }

    public void testPerformGenericQuery() throws Exception {

        String sql = "SELECT * FROM FLIGHT";
        Database.performGenericQuery(sql, DataTypes.FLIGHTPOINT);
    }

    public void testPerformDistinctQuery() throws Exception {
        String sql = "SELECT DISTINCT COUNTRY FROM AIRLINE";
        ArrayList<String> distinctPoints = Database.performDistinctQuery(sql);
        assertEquals(46, distinctPoints.size());
       // System.out.println("My Distinct Countries!");
    }

    public void testEditDataPoint() throws Exception {
        String sql = "SELECT * FROM AIRLINE WHERE ID='2'";
        ArrayList<DataPoint> myPoints = Database.performGenericQuery(sql, DataTypes.AIRLINEPOINT);
        AirlinePoint myAirline = (AirlinePoint) myPoints.get(0);
        //System.out.println("name = " + myAirline.getAirlineName());
        String edit = "UPDATE AIRLINE SET NAME='name', COUNTRY='United States', ALIAS='', IATA='', ICAO='GNL', CALLSIGN='GENERAL', ACTIVE='N' WHERE ID='2'";
        Database.editDataEntry(edit);
        myPoints = Database.performGenericQuery(sql, DataTypes.AIRLINEPOINT);
        myAirline = (AirlinePoint) myPoints.get(0);
        assertEquals("name", myAirline.getAirlineName());



    }

}