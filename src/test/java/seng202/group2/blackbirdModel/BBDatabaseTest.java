package seng202.group2.blackbirdModel;

import junit.framework.TestCase;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by sha162 on 12/09/16.
 */
public class BBDatabaseTest extends TestCase {
    public void setUp() throws Exception {
        super.setUp();

        String cwd = System.getProperty("user.dir");
        String airlinesFileString;
        String airportsFileString;
        String routesFileString;
        String flightFileString;

        airlinesFileString = cwd + "/JUnitTesting/airlines.txt";
        airportsFileString = cwd + "/TestFiles/airports.txt";
        routesFileString = cwd + "/TestFiles/route.txt";
        flightFileString = cwd + "/JUnitTesting/flight.txt";

        File airlinesFile = new File(airlinesFileString);
        File airportsFile = new File(airportsFileString);
        File routesFile = new File(routesFileString);
        File flightFile = new File(flightFileString);

        ArrayList<AirlinePoint> airlinePoints = Parser.parseAirlineData(airlinesFile);
        ArrayList<AirportPoint> airportPoints = Parser.parseAirportData(airportsFile);
        ArrayList<RoutePoint> routePoints = Parser.parseRouteData(routesFile);
        ArrayList<FlightPoint> flightPoints = Parser.parseFlightData(flightFile);


        BBDatabase.deleteDBFile();
        BBDatabase.createTables();
        BBDatabase.addAirlinePointstoDB(airlinePoints);

        BBDatabase.addAirportPointsToDB(airportPoints);
        BBDatabase.addRoutePointstoDB(routePoints);
        BBDatabase.addFlighttoDB(flightPoints);
        //myAirlineData = Filter.getAllAirlinePointsfromDB();




    }

    public void testGetDatabaseName() throws Exception {
        assertEquals(BBDatabase.getDatabaseName(), "jdbc:sqlite:default.db");
    }

    public void testPerformAirpointsQuery() throws Exception {
        ArrayList<AirportPoint> airportPoints = BBDatabase.performAirportsQuery("SELECT * FROM AIRPORT");
        assertEquals(airportPoints.size(), 8100);
    }

    public void testPerformAirlinesQuery() throws Exception {
        ArrayList<AirlinePoint> airlinePoints = BBDatabase.performAirlinesQuery("SELECT * FROM AIRLINE");
        assertEquals(airlinePoints.size(), 98);

    }

    public void testPerformDistinctRoutesQuery() throws Exception {
        ArrayList<String> routePoints = BBDatabase.performDistinctStringQuery("Select * from ROUTE");
        assertEquals(routePoints.size(), 67664);
    }

    public void testAirportEditDataEntry() throws Exception {
        ArrayList<AirportPoint> airportPoints = BBDatabase.performAirportsQuery("SELECT * FROM AIRPORT WHERE ID='1'");
        assertEquals(airportPoints.size(), 1);
        System.out.println(airportPoints.get(0).getAirportName());
        String sql = "UPDATE AIRPORT SET NAME='MINE', CITY='William', COUNTRY='Papua New Guinea', IATA='GKA', ICAO='AYGA', LATITUDE='-6.081689', LONGITUDE='145.39188', ALTITUDE='5282', TIMEZONE='10.0', DST='U', TZ='Pacific/Port_Moresby' WHERE ID='1'";
        BBDatabase.editDataEntry(sql);
        ArrayList<AirportPoint> newAirportPoints = BBDatabase.performAirportsQuery(("SELECT * FROM AIRPORT WHERE ID='1'"));
        assertEquals(newAirportPoints.size(), 1);
        System.out.println(newAirportPoints.get(0).getAirportName());
        assertEquals(newAirportPoints.get(0).getAirportName(), "MINE");
    }

    public void testAirlineEditDataEntry() throws Exception{
        ArrayList<AirlinePoint> airlinePoints = BBDatabase.performAirlinesQuery("SELECT * FROM AIRLINE WHERE ID='6'");
        assertEquals(airlinePoints.size(), 1);
        String sql = "UPDATE AIRLINE SET ID='6', NAME='223 Flight Unit State Airline', COUNTRY='POOS', ALIAS='', IATA='', ICAO='null', CALLSIGN='CHKALOVSK-AVIA', ACTIVE='N' WHERE ID='6'";
        BBDatabase.editDataEntry(sql);
        ArrayList<AirlinePoint> newAirlinePoints = BBDatabase.performAirlinesQuery("SELECT * FROM AIRLINE WHERE ID='6'");
        //System.out.println(newAirlinePoints.get(0).getCountry());
        assertEquals(newAirlinePoints.get(0).getCountry(), "POOS");
    }

    public void testRouteEditDataEntry() throws Exception{
        //System.out.println("HERE****************\n\n\n Oh boy");
        ArrayList<RoutePoint> routePoints = BBDatabase.performRoutesQuery("SELECT * FROM ROUTE WHERE IDnum='1'");
        //System.out.println("HERE****************");
        assertEquals(routePoints.size(), 0);
        //System.out.println("Previous Codeshare: " + routePoints.get(0).getCodeshare());
//        String sql = "UPDATE ROUTE SET Airline='2B', Airlineid='410', Src='AER', Srcid='2965', Dst='KZN', Dstid='2990', Codeshare='Y', Stops='0' WHERE IDnum='1'";
//        BBDatabase.editDataEntry(sql);
//
//        ArrayList<RoutePoint> newRoutePoints = BBDatabase.performRoutesQuery("SELECT * FROM ROUTE WHERE IDnum='1'");
//        //System.out.println("New Codeshare: " + newRoutePoints.get(0).getCodeshare());
//        assertEquals(newRoutePoints.get(0).getCodeshare(), "Y");
    }

    public void testDistinctQuery() throws Exception {
        String sql = "SELECT DISTINCT COUNTRY FROM AIRLINE";
        ArrayList<String> distinctPoints = BBDatabase.performDistinctStringQuery(sql);
        assertEquals(44, distinctPoints.size());
        //System.out.println("My Distinct Countries!");


    }

    public void testaddingAirportEntry() throws Exception {

        //String sql = "SELECT AIRPORT.IATA, ROUTE.Src FROM AIRPORT LEFT JOIN ROUTE WHERE AIRPORT.ID =ROUTE.srcid";
//
        //TODO
    }

    public void addingAirlineEntry() throws Exception {
        //TODO
    }
}