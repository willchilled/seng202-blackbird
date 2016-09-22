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


        ArrayList<DataPoint> airlinePoints = ParserRefactor.parseFile(airlinesFile, DataTypes.AIRLINEPOINT);
        ArrayList<DataPoint> airportPoint = ParserRefactor.parseFile(airportsFile, DataTypes.AIRPORTPOINT);

        ArrayList<DataPoint> routePoints = ParserRefactor.parseFile(routesFile, DataTypes.ROUTEPOINT);
        ArrayList<DataPoint> flightPoints = ParserRefactor.parseFile(flightFile, DataTypes.FLIGHTPOINT);
        //ArrayList<Flight>

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

    public void testFilterSelectionsWithAirports() throws Exception {

        String search = "";
        ArrayList<String> selectedFields = new ArrayList<>(Arrays.asList("Greenland"));
        ArrayList<DataPoint> dataPoints = FilterRefactor.filterSelections(selectedFields, "", DataTypes.AIRPORTPOINT);
        //System.out.println(dataPoints);
        assertEquals(dataPoints.size(), 4);

        selectedFields = new ArrayList<>(Arrays.asList("None"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, search, DataTypes.AIRPORTPOINT);
        assertEquals(dataPoints.size(), 100); //Both lines are None

        selectedFields = new ArrayList<>(Arrays.asList("None"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "Greenland", DataTypes.AIRPORTPOINT);
        assertEquals(dataPoints.size(), 4); //Both lines are None
//
        selectedFields = new ArrayList<>(Arrays.asList("Iceland"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "Greenland", DataTypes.AIRPORTPOINT);
        assertEquals(dataPoints.size(), 0); //Both lines are None

        selectedFields = new ArrayList<>(Arrays.asList("Iceland"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "AEY", DataTypes.AIRPORTPOINT);
        assertEquals(dataPoints.size(), 1); //Both lines are None
    }

    public void testFilterSelectionsWithAirlines() throws Exception{

        String search = "";
        ArrayList<String> selectedFields = new ArrayList<>(Arrays.asList("Russia", "None"));
        ArrayList<DataPoint> dataPoints = FilterRefactor.filterSelections(selectedFields, "", DataTypes.AIRLINEPOINT);
        assertEquals(dataPoints.size(), 5);

        selectedFields = new ArrayList<>(Arrays.asList("None", "None"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, search, DataTypes.AIRLINEPOINT);
        assertEquals(dataPoints.size(), 100); //Both lines are None

        selectedFields = new ArrayList<>(Arrays.asList("Russia", "Y"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, search, DataTypes.AIRLINEPOINT);
        assertEquals(dataPoints.size(), 0); //Both selections no search

        selectedFields = new ArrayList<>(Arrays.asList("Russia", "N"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, search, DataTypes.AIRLINEPOINT);
        assertEquals(dataPoints.size(), 5); //Both selections no search

        selectedFields = new ArrayList<>(Arrays.asList("Russia", "N"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "AED", DataTypes.AIRLINEPOINT);
        assertEquals(dataPoints.size(), 1); //Both selections and Search

        selectedFields = new ArrayList<>(Arrays.asList("Russia", "None"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "N", DataTypes.AIRLINEPOINT);
        assertEquals(dataPoints.size(), 5); //One selection and Search

        selectedFields = new ArrayList<>(Arrays.asList("None", "N"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "Russia", DataTypes.AIRLINEPOINT);
        assertEquals(dataPoints.size(), 5); //One selection and Search

        selectedFields = new ArrayList<>(Arrays.asList("None", "None"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "Russia", DataTypes.AIRLINEPOINT);
        assertEquals(dataPoints.size(), 5); //One selection and Search

    }
    public void testRouteFilterSelections() throws  Exception{
        String search = "";
        ArrayList<String> selectedFields = new ArrayList<>(Arrays.asList("None", "None", "None", "None"));
        ArrayList<DataPoint> dataPoints = FilterRefactor.filterSelections(selectedFields, "Poos", DataTypes.ROUTEPOINT);
        //assertEquals(dataPoints.size(), 5);
    }

}