package seng202.group2.blackbirdControl;

import junit.framework.TestCase;
import seng202.group2.blackbirdModel.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Filter;

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
        routesFileString = cwd + "/JUnitTesting/routeSmallWithNoFailing";
        flightFileString = cwd + "/JUnitTesting/flight.txt";

        File airlinesFile = new File(airlinesFileString);
        File airportsFile = new File(airportsFileString);
        File routesFile = new File(routesFileString);
        File flightFile = new File(flightFileString);


        ArrayList<DataPoint> airlinePoints = ParserRefactor.parseFile(airlinesFile, DataTypes.AIRLINEPOINT, null);
        ArrayList<DataPoint> airportPoint = ParserRefactor.parseFile(airportsFile, DataTypes.AIRPORTPOINT, null);

        ArrayList<DataPoint> routePoints = ParserRefactor.parseFile(routesFile, DataTypes.ROUTEPOINT, null);
        ArrayList<DataPoint> flightPoints = ParserRefactor.parseFile(flightFile, DataTypes.FLIGHTPOINT, null);
        //ArrayList<Flight>

        Flight flight = new Flight(flightPoints);
        flight.setType(DataTypes.FLIGHT);
        DataPoint f = flight;
        ArrayList<DataPoint> myFlight = new ArrayList<>();
        myFlight.add(f);
        // System.out.println(flight.getType() + "--------------------------");

        DataBaseRefactor.createTables();
        DataBaseRefactor.insertDataPoints(airlinePoints, null);
        DataBaseRefactor.insertDataPoints(airportPoint, null);
        DataBaseRefactor.insertDataPoints(routePoints, null);
        DataBaseRefactor.insertDataPoints(myFlight, null);


        // ArrayList<Fl> a= flightPoints;

        DataBaseRefactor.insertDataPoints(flightPoints, null);


    }

    public void testFilterSelectionsWithAirports() throws Exception {

        String search = "";
        ArrayList<String> selectedFields = new ArrayList<>(Arrays.asList("Greenland"));
        ArrayList<DataPoint> dataPoints = FilterRefactor.filterSelections(selectedFields, "", DataTypes.AIRPORTPOINT);
        //System.out.println(dataPoints);
        //System.out.println(dataPoints);
//        AirportPoint testPoint = (AirportPoint) dataPoints.get(0);
//        System.out.println(testPoint.toStringWithRoutes());
//        assertEquals(testPoint.getOutgoingRoutes(), 1); //Might add more of these tests later and also they should really be in a different place
//        assertEquals(testPoint.getIncomingRoutes(), 2);//These two tests are to make sure the number of incoming + outgoing routes is correct


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
        assertEquals(dataPoints.size(), 2); //Both lines are None
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

        dataPoints = FilterRefactor.getAllPoints(DataTypes.AIRLINEPOINT);
        assertEquals(dataPoints.size(), 100);

    }

    public void testRouteFilterSelections() throws  Exception{
        String search = "";
        ArrayList<String> selectedFields = new ArrayList<>(Arrays.asList("None", "None", "None", "None"));
        ArrayList<DataPoint> dataPoints = FilterRefactor.filterSelections(selectedFields, "", DataTypes.ROUTEPOINT);
        assertEquals(dataPoints.size(), 96);

        RoutePoint myRoutePoint = (RoutePoint) dataPoints.get(0);
        //System.out.println(myRoutePoint.toStringWithAirports());
        assertEquals(myRoutePoint.getSrcAirportName(), "Narsarsuaq");


        selectedFields = new ArrayList<>(Arrays.asList("AER", "None", "None", "None"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "", DataTypes.ROUTEPOINT);
        assertEquals(dataPoints.size(), 1);

        selectedFields = new ArrayList<>(Arrays.asList("None", "None", "None", "CR2"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "", DataTypes.ROUTEPOINT);
        assertEquals(dataPoints.size(), 40);

        selectedFields = new ArrayList<>(Arrays.asList("None", "None", "None", "CR2"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "EGO", DataTypes.ROUTEPOINT);
        assertEquals(dataPoints.size(), 4);


        selectedFields = new ArrayList<>(Arrays.asList("None", "None", "None", "None"));
        dataPoints = FilterRefactor.filterSelections(selectedFields, "Narsarsuaq", DataTypes.ROUTEPOINT);
        assertEquals(dataPoints.size(), 2);






    }

    public void testFlightFilter() throws Exception{
        ArrayList<String> menusPressed = new ArrayList<>();
        menusPressed.add("None");
        menusPressed.add("None");
        ArrayList<DataPoint> mp = FilterRefactor.filterSelections(menusPressed, "", DataTypes.FLIGHT);
        assertEquals(mp.size(), 1);

        menusPressed = new ArrayList<>();
        menusPressed.add("None");
        menusPressed.add("WSSS");
        mp = FilterRefactor.filterSelections(menusPressed, "NZCH", DataTypes.FLIGHT);
        assertEquals(mp.size(), 1);

        menusPressed = new ArrayList<>();
        menusPressed.add("None");
        menusPressed.add("None");
        mp = FilterRefactor.filterSelections(menusPressed, "NZCH", DataTypes.FLIGHT);
        assertEquals(mp.size(), 1);

        mp = FilterRefactor.getAllPoints(DataTypes.FLIGHT);
        assertEquals(mp.size(), 1);

    }


    public void testFindAirportPointForDistance(){
        FilterRefactor.findAirportPointForDistance("Goroka AYGA");




    }

    public void testFilterDistinct(){
        ArrayList<String> conutries = FilterRefactor.filterDistinct("Country", "Airline");
        assertEquals(conutries.size(), 46);
    }


    public void testAirportLinkWithRoutes(){
        DataBaseRefactor.createTables();

        String cwd = System.getProperty("user.dir");
        String airportsFileString;
        String routesFileString;

        airportsFileString = cwd + "/JUnitTesting/airportsSmall.txt";
        routesFileString = cwd + "/JUnitTesting/routeSmall";

        File airportsFile = new File(airportsFileString);
        File routesFile = new File(routesFileString);


        ArrayList<DataPoint> airportPoint = ParserRefactor.parseFile(airportsFile, DataTypes.AIRPORTPOINT, null);

        ArrayList<DataPoint> routePoints = ParserRefactor.parseFile(routesFile, DataTypes.ROUTEPOINT, null);
        //ArrayList<Flight>

        DataBaseRefactor.createTables();
        DataBaseRefactor.insertDataPoints(airportPoint, null);
        DataBaseRefactor.insertDataPoints(routePoints, null);


        ArrayList<DataPoint> airportPoints = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);

        for (DataPoint p: airportPoints){
            AirportPoint myp = (AirportPoint) p;
            //System.out.println(myp.toStringWithRoutes());
        }
        AirportPoint currentPoint = (AirportPoint) airportPoints.get(0); //These are hard coded into the files
        assertEquals(currentPoint.getOutgoingRoutes(), 2);
        assertEquals(currentPoint.getIncomingRoutes(), 1);

        currentPoint = (AirportPoint) airportPoints.get(1);
        assertEquals(currentPoint.getOutgoingRoutes(), 0);
        assertEquals(currentPoint.getIncomingRoutes(), 0);

        currentPoint = (AirportPoint) airportPoints.get(2);
        assertEquals(currentPoint.getOutgoingRoutes(), 1);
        assertEquals(currentPoint.getIncomingRoutes(), 2);



    }



    public void testBenchmarkFilterSpeeds(){
        int benchMarkTime = 5;
        String cwd = System.getProperty("user.dir");
        String airlinesFileString;
        String airportsFileString;
        String routesFileString;
        String flightFileString;
        DataBaseRefactor.createTables();

        airlinesFileString = cwd + "/JUnitTesting/airlinesLargeWithNoFailing";
        airportsFileString = cwd + "/JUnitTesting/airports.txt";
        routesFileString = cwd + "/JUnitTesting/routesWithNoFailing";
        flightFileString = cwd + "/JUnitTesting/flight.txt";

        File airlinesFile = new File(airlinesFileString);
        File airportsFile = new File(airportsFileString);
        File routesFile = new File(routesFileString);
        File flightFile = new File(flightFileString);


        ArrayList<DataPoint> airlinePoints = ParserRefactor.parseFile(airlinesFile, DataTypes.AIRLINEPOINT, null);

        ArrayList<DataPoint> flightPoints = ParserRefactor.parseFile(flightFile, DataTypes.FLIGHTPOINT, null);


        Flight flight = new Flight(flightPoints);
        flight.setType(DataTypes.FLIGHT);
        DataPoint f = flight;
        ArrayList<DataPoint> myFlight = new ArrayList<>();
        myFlight.add(f);

        DataBaseRefactor.insertDataPoints(airlinePoints, null);

        DataBaseRefactor.insertDataPoints(myFlight, null);
        DataBaseRefactor.insertDataPoints(flightPoints, null);
        double numTrials = 3;
        long totalStart1 =0;
        long totalFinish1 = 0;
        long totalStart2 =0;
        long totalFinish2 = 0;
        for (int i = 0; i < numTrials; i++) {
            long startTime = System.nanoTime();
            ArrayList<DataPoint> airportPoint = ParserRefactor.parseFile(airportsFile, DataTypes.AIRPORTPOINT, null);
            ArrayList<DataPoint> routePoints = ParserRefactor.parseFile(routesFile, DataTypes.ROUTEPOINT, null);
            long endTime = System.nanoTime();

            totalStart1 += startTime;
            totalFinish1 += endTime;

            DataBaseRefactor.createTables();
            startTime = System.nanoTime();

            DataBaseRefactor.insertDataPoints(airportPoint, null);
            DataBaseRefactor.insertDataPoints(routePoints, null);
            endTime = System.nanoTime();

            totalStart2 += startTime;
            totalFinish2 += endTime;

        }

        assertTrue(((totalFinish1 - totalStart1)/1000000000.0)/ numTrials < benchMarkTime); //Ensuring parsing airports and routes takes under 3 seconds
        assertTrue(((totalFinish2 - totalStart2)/1000000000.0)/ numTrials < benchMarkTime); //Adding airports and routes should take less than 3 seconds
        assertTrue(findTimeTakes(numTrials, DataTypes.ROUTEPOINT) < benchMarkTime); //Ensuring Filtering routes can be done in under 3 seconds
        assertTrue(findTimeTakes(numTrials, DataTypes.AIRPORTPOINT) < benchMarkTime);
        assertTrue(findTimeTakes(numTrials, DataTypes.AIRLINEPOINT) < benchMarkTime);
        assertTrue(findTimeTakes(numTrials, DataTypes.FLIGHT) < 3);
    }

    public double findTimeTakes(double numTrials, DataTypes type){
        long totalStartTime=0;
        long totalFinishTime=0;

        for (int i = 0; i < numTrials; i++) {

            long startTime = System.nanoTime();
            FilterRefactor.getAllPoints(type);
            long endTime = System.nanoTime();
            totalStartTime += startTime;
            totalFinishTime += endTime;
        }

        return ((totalFinishTime - totalStartTime)/ numTrials)/1000000000.0;
    }



}