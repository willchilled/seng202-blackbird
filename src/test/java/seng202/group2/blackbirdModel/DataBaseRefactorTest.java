package seng202.group2.blackbirdModel;

import junit.framework.TestCase;

import javax.xml.crypto.Data;
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


        ArrayList<DataPoint> airlinePoints = ParserRefactor.parseFile(airlinesFile, DataTypes.AIRLINEPOINT);
        ArrayList<DataPoint> airportPoint = ParserRefactor.parseFile(airportsFile, DataTypes.AIRPORTPOINT);

        ArrayList<DataPoint> routePoints = ParserRefactor.parseFile(routesFile, DataTypes.ROUTEPOINT);
        ArrayList<DataPoint> flightPoints = ParserRefactor.parseFile(flightFile, DataTypes.FLIGHTPOINT);

        Flight flight = new Flight(flightPoints);
        flight.setType(DataTypes.FLIGHT);
        DataPoint f = flight;
        ArrayList<DataPoint> myFlight = new ArrayList<>();
        myFlight.add(f);
        // System.out.println(flight.getType() + "--------------------------");


        DataBaseRefactor.createTables();
        DataBaseRefactor.insertDataPoints(airlinePoints);
        DataBaseRefactor.insertDataPoints(airportPoint);
        DataBaseRefactor.insertDataPoints(routePoints);
        DataBaseRefactor.insertDataPoints(myFlight);


        // ArrayList<Fl> a= flightPoints;

        DataBaseRefactor.insertDataPoints(flightPoints);

    }

    public void testInsertDataPoints() throws Exception {

    }

    public void testPerformGenericQuery() throws Exception {

        String sql = "SELECT * FROM FLIGHT";
        DataBaseRefactor.performGenericQuery(sql, DataTypes.FLIGHTPOINT);
    }

    public void testPerformDistinctQuery() throws Exception {
        String sql = "SELECT DISTINCT COUNTRY FROM AIRLINE";
        ArrayList<String> distinctPoints = DataBaseRefactor.performDistinctQuery(sql);
        assertEquals(46, distinctPoints.size());
        System.out.println("My Distinct Countries!");
    }

    public void testEditDataPoint() throws Exception {
        String sql = "SELECT * FROM AIRLINE WHERE ID='2'";
        ArrayList<DataPoint> myPoints = DataBaseRefactor.performGenericQuery(sql, DataTypes.AIRLINEPOINT);
        AirlinePoint myAirline = (AirlinePoint) myPoints.get(0);
        System.out.println("name = " + myAirline.getAirlineName());
        String edit = "UPDATE AIRLINE SET NAME='Poos', COUNTRY='United States', ALIAS='', IATA='', ICAO='GNL', CALLSIGN='GENERAL', ACTIVE='N' WHERE ID='2'";
        DataBaseRefactor.editDataEntry(edit);
        myPoints = DataBaseRefactor.performGenericQuery(sql, DataTypes.AIRLINEPOINT);
        myAirline = (AirlinePoint) myPoints.get(0);
        assertEquals("Poos", myAirline.getAirlineName());



    }

}