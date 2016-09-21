package seng202.group2.blackbirdControl;

import junit.framework.TestCase;
import seng202.group2.blackbirdModel.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wmu16 on 17/09/16.
 */
public class FilterRefactorTest extends TestCase {
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


        ArrayList<DataPoint> airlinePoints = ParserRefactor.parseFile(airlinesFile, "AirlinePoint");
        ArrayList<DataPoint> airportPoint = ParserRefactor.parseFile(airportsFile, "AirportPoint");

        ArrayList<DataPoint> routePoints = ParserRefactor.parseFile(routesFile, "RoutePoint");
        ArrayList<DataPoint> flightPoints = ParserRefactor.parseFile(flightFile, "FlightPoint");
        //ArrayList<Flight>

        Flight flight = new Flight(flightPoints);
        flight.setType("Flight");
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

    public void testTest() throws Exception {
        assertEquals(0, 0);
    }

    public void testFilterSelectionsWithAirports() throws Exception {

        String search = "";
        ArrayList<String> selectedFields = new ArrayList<>(Arrays.asList("Greenland"));
        ArrayList<DataPoint> dataPoints = FilterRefactor.filterSelections(selectedFields, "", "AirportPoint");
        //System.out.println(dataPoints);
        assertEquals(dataPoints.size(), 4);

        selectedFields = new ArrayList<>(Arrays.asList("None"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, search, "AirportPoint");
        assertEquals(dataPoints.size(), 100); //Both lines are None

        selectedFields = new ArrayList<>(Arrays.asList("None"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "Greenland", "AirportPoint");
        assertEquals(dataPoints.size(), 4); //Both lines are None
//
        selectedFields = new ArrayList<>(Arrays.asList("Iceland"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "Greenland", "AirportPoint");
        assertEquals(dataPoints.size(), 0); //Both lines are None

        selectedFields = new ArrayList<>(Arrays.asList("Iceland"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "AEY", "AirportPoint");
        assertEquals(dataPoints.size(), 1); //Both lines are None
    }

    public void testFilterSelectionsWithAirlines() throws Exception{

        String search = "";
        ArrayList<String> selectedFields = new ArrayList<>(Arrays.asList("Russia", "None"));
        ArrayList<DataPoint> dataPoints = FilterRefactor.filterSelections(selectedFields, "", "AirlinePoint");
        assertEquals(dataPoints.size(), 5);

        selectedFields = new ArrayList<>(Arrays.asList("None", "None"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, search, "AirlinePoint");
        assertEquals(dataPoints.size(), 100); //Both lines are None

        selectedFields = new ArrayList<>(Arrays.asList("Russia", "Y"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, search, "AirlinePoint");
        assertEquals(dataPoints.size(), 0); //Both selections no search

        selectedFields = new ArrayList<>(Arrays.asList("Russia", "N"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, search, "AirlinePoint");
        assertEquals(dataPoints.size(), 5); //Both selections no search

        selectedFields = new ArrayList<>(Arrays.asList("Russia", "N"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "AED", "AirlinePoint");
        assertEquals(dataPoints.size(), 1); //Both selections and Search

        selectedFields = new ArrayList<>(Arrays.asList("Russia", "None"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "N", "AirlinePoint");
        assertEquals(dataPoints.size(), 5); //One selection and Search

        selectedFields = new ArrayList<>(Arrays.asList("None", "N"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "Russia", "AirlinePoint");
        assertEquals(dataPoints.size(), 5); //One selection and Search

        selectedFields = new ArrayList<>(Arrays.asList("None", "None"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "Russia", "AirlinePoint");
        assertEquals(dataPoints.size(), 5); //One selection and Search

    }
    public void testRouteFilterSelections() throws  Exception{
        String search = "";
        ArrayList<String> selectedFields = new ArrayList<>(Arrays.asList("None", "None", "None", "None"));
        ArrayList<DataPoint> dataPoints = FilterRefactor.filterSelections(selectedFields, "", "RoutePoint");
        assertEquals(dataPoints.size(), 98);


        selectedFields = new ArrayList<>(Arrays.asList("AER", "None", "None", "None"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "", "RoutePoint");
        assertEquals(dataPoints.size(), 1);

        selectedFields = new ArrayList<>(Arrays.asList("None", "None", "None", "CR2"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "", "RoutePoint");
        assertEquals(dataPoints.size(), 40);

        selectedFields = new ArrayList<>(Arrays.asList("None", "None", "None", "CR2"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "EGO", "RoutePoint");
        assertEquals(dataPoints.size(), 4);
    }

}