package seng202.group2.blackbirdControl;

import junit.framework.TestCase;
import seng202.group2.blackbirdModel.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sha162 on 4/09/16.
 */
public class FilterTest extends TestCase {

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



    public void testFilterAirlinesBySelections(){
        String search = "";
        ArrayList<AirlinePoint> airlinePoints = new ArrayList<>();

        ArrayList<String> selectedFields = new ArrayList<>(Arrays.asList("Russia", "None"));
        airlinePoints = Filter.filterAirlinesBySelections(selectedFields, search);
        assertEquals(airlinePoints.size(), 5); //Country Selected

        selectedFields = new ArrayList<>(Arrays.asList("None", "None"));
        airlinePoints = Filter.filterAirlinesBySelections(selectedFields, search);
        assertEquals(airlinePoints.size(), 98); //Both lines are None

        selectedFields = new ArrayList<>(Arrays.asList("Russia", "Y"));
        airlinePoints = Filter.filterAirlinesBySelections(selectedFields, search);
        assertEquals(airlinePoints.size(), 0); //Both selections no search

        selectedFields = new ArrayList<>(Arrays.asList("Russia", "N"));
        airlinePoints = Filter.filterAirlinesBySelections(selectedFields, search);
        assertEquals(airlinePoints.size(), 5); //Both selections no search

        selectedFields = new ArrayList<>(Arrays.asList("Russia", "N"));
        airlinePoints = Filter.filterAirlinesBySelections(selectedFields, "AED");
        assertEquals(airlinePoints.size(), 1); //Both selections and Search

        selectedFields = new ArrayList<>(Arrays.asList("Russia", "None"));
        airlinePoints = Filter.filterAirlinesBySelections(selectedFields, "N");
        assertEquals(airlinePoints.size(), 5); //One selection and Search

        selectedFields = new ArrayList<>(Arrays.asList("None", "N"));
        airlinePoints = Filter.filterAirlinesBySelections(selectedFields, "Russia");
        assertEquals(airlinePoints.size(), 5); //One selection and Search




    }


    public void testFilterAiportsBySelections() throws Exception {
        ArrayList<AirportPoint> allPoints = new ArrayList<>();
        allPoints = Filter.filterAirportsBySelections("None", "");

        assertEquals(allPoints.size(), 100);

        allPoints = Filter.filterAirportsBySelections("Greenland", "");
        assertEquals(allPoints.size(), 4);


        allPoints = Filter.filterAirportsBySelections("Greenland", "Sondrestrom");
        assertEquals(allPoints.size(), 1);

        allPoints = Filter.filterAirportsBySelections("None", "Sondrestrom");
        assertEquals(allPoints.size(), 1);


    }

    public void testFilterRoutesBySelections() throws Exception{
        ArrayList<RoutePoint> routePoints = new ArrayList<>();
        String search = "";

        ArrayList<String> selectedFields = new ArrayList<>(Arrays.asList("None", "None", "None", "CRJ"));
        routePoints = Filter.filterRoutesBySelections(selectedFields, search);
        assertEquals(routePoints.size(), 12); //Nothing in query



        selectedFields = new ArrayList<>(Arrays.asList("TPP", "None", "None", "None"));
        routePoints = Filter.filterRoutesBySelections(selectedFields, search);
        assertEquals(routePoints.size(), 3); //One item in quert

        selectedFields = new ArrayList<>(Arrays.asList("None", "KZN", "None", "None"));
        routePoints = Filter.filterRoutesBySelections(selectedFields, search);
        assertEquals(routePoints.size(), 7); //One item in query

        selectedFields = new ArrayList<>(Arrays.asList("None", "None", "1", "None"));
        routePoints = Filter.filterRoutesBySelections(selectedFields, search);
        assertEquals(routePoints.size(), 1); //One item in quer

        search = "CRJ";
        selectedFields = new ArrayList<>(Arrays.asList("None", "None", "None", "None"));
        routePoints = Filter.filterRoutesBySelections(selectedFields, search);
        assertEquals(routePoints.size(), 12); //One item in quer

    }

    public void testGetAllAirlinePointsfromDB(){
        ArrayList<AirlinePoint> airlinePoints = new ArrayList<>();
        airlinePoints = Filter.getAllAirlinePointsfromDB();
        assertEquals(airlinePoints.size(), 98);
    }

    public void testGetAllAirportPointsFromDB(){
        ArrayList<AirportPoint> airportPoints = new ArrayList<>();
        airportPoints = Filter.getAllAirportPointsFromDB();
        assertEquals(airportPoints.size(), 100);
    }

    public void testFindDistinctStringsFromRoutes(){
        Filter.findDistinctStringFromTable("Src", "ROUTE");

    }


    public void testActiveAirlines() throws Exception {

    }

    public void testFilterRouteSrc() throws Exception {

    }

    public void testFilterRoutes() throws Exception {

    }

    public void testDirectRoutes() throws Exception {

    }

    public void testRouteEquipment() throws Exception {

    }

    public void testFilterAirportCountries() throws Exception {
        //ArrayList<String> myCountries = new ArrayList<>(Arrays.asList("New Zealand", "New Zealand", "Japan"));
        ArrayList<String> filterResult = Filter.filterUniqueAirportCountries();
        assertEquals(filterResult.size(), 4);

        //Add another point to the database
        ArrayList<AirportPoint> myPoints =  new ArrayList<AirportPoint>();
        AirportPoint myPoint = new AirportPoint(101, "test");
        myPoint.setAirportCountry("New Country");
        myPoint.setAirportCity("Hello");
        myPoints.add(myPoint);
        BBDatabase.addAirportPointsToDB(myPoints);

        filterResult = Filter.filterUniqueAirportCountries();
        assertEquals(filterResult.size(), 5);


    }

    public void testFilterAirLineCountries() throws Exception {

    }




}