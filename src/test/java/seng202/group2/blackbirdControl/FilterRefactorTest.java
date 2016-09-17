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

        ArrayList<AirlinePoint> airlinePoints = Parser.parseAirlineData(airlinesFile);
        ArrayList<AirportPoint> airportPoints = Parser.parseAirportData(airportsFile);
        ArrayList<RoutePoint> routePoints = Parser.parseRouteData(routesFile);



        BBDatabase.deleteDBFile();
        BBDatabase.createTables();
        BBDatabase.addAirlinePointstoDB(airlinePoints);
        BBDatabase.addAirportPointsToDB(airportPoints);
        BBDatabase.addRoutePointstoDB(routePoints);

    }

    public void testFilterSelections() throws Exception {

        String search = "";
        ArrayList<String> selectedFields = new ArrayList<>(Arrays.asList("Russia", "None"));
        ArrayList<DataPoint> dataPoints = FilterRefactor.filterSelections(selectedFields, "", "AirlinePoint");
        assertEquals(dataPoints.size(), 5);

        selectedFields = new ArrayList<>(Arrays.asList("None", "None"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, search, "AirlinePoint");
        assertEquals(dataPoints.size(), 98); //Both lines are None

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

}