package seng202.group2.blackbirdModel;

import junit.framework.TestCase;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by sha162 on 16/09/16.
 */
public class ParserRefactorTest extends TestCase {
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

       // ArrayList<AirlinePoint> airlinePoints = Parser.parseAirlineData(airlinesFile);
       // ArrayList<AirportPoint> airportPoints = Parser.parseAirportData(airportsFile);
        //ArrayList<RoutePoint> routePoints = Parser.parseRouteData(routesFile);


        ArrayList<DataPoint> airlinePoints = ParserRefactor.parseFile(airlinesFile, "AirlinePoint");
        ArrayList<DataPoint> airportPoint = ParserRefactor.parseFile(airportsFile, "AirportPoint");

        ArrayList<DataPoint> routePoints = ParserRefactor.parseFile(routesFile, "RoutePoint");
        ArrayList<DataPoint> flightPoints = ParserRefactor.parseFile(flightFile, "FlightPoint");
       // System.out.println(airlinePoints.get(0).getType());



        Flight hello = new Flight(flightPoints);
        DataPoint myFlight = hello;
        myFlight.setType("FlightPoint");

        DataPoint myFlight2 = new Flight(flightPoints);
        System.out.println(myFlight2.getType());

        //DataPoint myFlight3 = new DataPoint("Flight", flightPoints);



        //System.out.println(hello.getType());
        //DataPoint test = DataPoint.createDataPointFromStringArray(flightPoints, "FlightPoint");//new DataPoint(flightPoints);
//
//
        testDatatype(airlinePoints, "AirlinePoint");
        testDatatype(airportPoint, "AirportPoint");
        testDatatype(routePoints, "RoutePoint");
        testDatatype(flightPoints, "FlightPoint");

         //System.out.println(flightPoints.get(0).getType());

//        ArrayList<AirportPoint> airportPoints = Parser.parseAirportData(airportsFile);
//        ArrayList<RoutePoint> routePoints = Parser.parseRouteData(routesFile);


//        BBDatabase.deleteDBFile();
//        BBDatabase.createTables();
//        BBDatabase.addAirlinePointstoDB(airlinePoints);
//        BBDatabase.addAirportPointsToDB(airportPoints);
//        BBDatabase.addRoutePointstoDB(routePoints);
        //myAirlineData = Filter.getAllAirlinePointsfromDB();

    }

    public void testDatatype(ArrayList<DataPoint> allPoints, String type){
        //System.out.println(type);
        for (int i=0; i<allPoints.size(); i++){
            assertEquals(allPoints.get(i).getType(), type);
        }
    }

    public void testParseFlightData() throws Exception {

    }

}