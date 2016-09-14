package seng202.group2.blackbirdControl;

import junit.framework.TestCase;
import seng202.group2.blackbirdModel.*;

import java.io.File;
import java.util.ArrayList;

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

        airlinesFileString = cwd + "/JUnitTesting/airlines.txt";
        airportsFileString = cwd + "/JUnitTesting/airports.txt";
        routesFileString = cwd + "/JUnitTesting/route.txt";

        File airlinesFile = new File(airlinesFileString);
        File airportsFile = new File(airportsFileString);
        File routesFile = new File(routesFileString);

        ArrayList<AirlinePoint> airlinePoints = Parser.parseAirlineData(airlinesFile);
        ArrayList<AirportPoint> airportPoints = Parser.parseAirportData(airportsFile);
        ArrayList<RoutePoint> routePoints = Parser.parseRouteData(routesFile);


        BBDatabase.deleteDBFile();
        BBDatabase.createTables();
        BBDatabase.addAirlinePointstoDB(airlinePoints);
        BBDatabase.addAiportPortsToDB(airportPoints);
        BBDatabase.addRoutePointstoDB(routePoints);
        //myAirlineData = Filter.getAllAirlinePointsfromDB();
    }


    public void testFilterAirportCountry() throws Exception {



        /*
        ArrayList<AirportPoint> airportPoints = new ArrayList<AirportPoint>();
        ArrayList<AirportPoint> empty = new ArrayList<AirportPoint>();

        AirportPoint testAirport = new AirportPoint(1, "Christchurch");
        AirportPoint testAirpor2 = new AirportPoint(2, "Sydney");

        testAirport.setAirportCountry("New Zealand");
        testAirpor2.setAirportCountry("Australia");

        airportPoints.add(testAirport);
        airportPoints.add(testAirpor2);

        airportPoints = Filter.filterAirportCountry(airportPoints,"New Zealand");

        //filter our one country
        assertEquals(airportPoints.size(), 1);
        assertEquals(airportPoints.get(0).getAirportName(), "Christchurch");

        //filtering an empty list
        empty = Filter.filterAirportCountry(empty, "New Zealand");
        assertEquals(empty.size(), 0);

        //Filter a list with a result that does exist
        airportPoints = Filter.filterAirportCountry(airportPoints, "I DONT EXIST");
        assertEquals(airportPoints.size(), 0);
        */





    }


    public void testFilterAirlineCountry() throws Exception {
        /* adds two airlines, filters one of them out, */

        ArrayList<AirlinePoint> airlinePoints = new ArrayList<AirlinePoint>();

        AirlinePoint testAirline = new AirlinePoint(1, "Test Airline 1");
        testAirline.setAirlineAlias("ANA");
        testAirline.setIata("NH");
        testAirline.setIcao("ANA");
        testAirline.setCallsign("ALL NIPPON");
        testAirline.setCountry("Japan");
        testAirline.setActive("Y");

        AirlinePoint testAirline2 = new AirlinePoint(2, "Test Airline 2");
        testAirline2.setAirlineAlias("YYT");
        testAirline2.setIata("RE");
        testAirline2.setIcao("YYT");
        testAirline2.setCallsign("WOW TEST");
        testAirline2.setCountry("Japans");
        testAirline2.setActive("Y");

        airlinePoints.add(testAirline);
        airlinePoints.add(testAirline2);


        airlinePoints = Filter.filterAirlineCountry(airlinePoints, "Japan");

        assertEquals(airlinePoints.size(), 1);
        assertEquals(airlinePoints.get(0).getCountry(), "Japan");


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

    }

    public void testFilterAirLineCountries() throws Exception {

    }

    public void testGetAllAirlinePointsfromDB(){
        ArrayList<AirlinePoint> airlinePoints = new ArrayList<>();
        airlinePoints = Filter.getAllAirlinePointsfromDB();
        assertEquals(airlinePoints.size(), 99);
    }

    public void testGetAllAirportPointsFromDB(){
        ArrayList<AirportPoint> airportPoints = new ArrayList<>();
        airportPoints = Filter.getAllAirportPointsFromDB();
        assertEquals(airportPoints.size(), 100);
    }

    public void testFilterAirlinesBySelections(){
        String search = "";
        ArrayList<AirlinePoint> airlinePoints = new ArrayList<>();

        ArrayList<String> selectedFields = new ArrayList<>();
        selectedFields.add("None");
        selectedFields.add("None");
        airlinePoints = Filter.filterAirlinesBySelections(selectedFields, search);

        assertEquals(airlinePoints.size(), 99); //Both lines are None

        selectedFields.removeAll(selectedFields);
        selectedFields.add("None");
        selectedFields.add("Y");
        airlinePoints = Filter.filterAirlinesBySelections(selectedFields, search);

        assertEquals(airlinePoints.size(), 20); // "None", "Y"

        selectedFields.removeAll(selectedFields);
        selectedFields.add("Germany");
        selectedFields.add("None");
        airlinePoints = Filter.filterAirlinesBySelections(selectedFields, search);
        assertEquals(airlinePoints.size(), 1); // "Germany", "None"

        selectedFields.removeAll(selectedFields);
        selectedFields.add("Canada");
        selectedFields.add("N");
        airlinePoints = Filter.filterAirlinesBySelections(selectedFields, search);
        assertEquals(airlinePoints.size(), 9);



    }

    public void testFindDistinctStringsFromRoutes(){
        Filter.findDistinctStringFromTable("Src", "ROUTE");

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
}