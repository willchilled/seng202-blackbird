package seng202.group2.blackbirdControl;

import junit.framework.TestCase;
import seng202.group2.blackbirdModel.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sha162 on 25/09/16.
 */
public class AnalyserTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

        String cwd = System.getProperty("user.dir");
        String airlinesFileString;
        String airportsFileString;
        String routesFileString;
        String flightFileString;

        airlinesFileString = cwd + "/JUnitTesting/airlines.txt";
        airportsFileString = cwd + "/JUnitTesting/airports.txt";
        routesFileString = cwd + "/JUnitTesting/routesWithNoFailing";
        //routesFileString = cwd + "/JUnitTesting/route60000.txt";
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

        Database.createTables();
        Database.insertDataPoints(airlinePoints, null);
        Database.insertDataPoints(airportPoint, null);
        Database.insertDataPoints(routePoints, null);
        Database.insertDataPoints(myFlight, null);





    }
    public void testCalculateDistance() throws Exception {
        ArrayList<DataPoint>  myPoint = Filter.getAllPoints(DataTypes.AIRPORTPOINT);

        AirportPoint pointa = (AirportPoint) myPoint.get(0);
        AirportPoint pointb = (AirportPoint) myPoint.get(1);


        double distance = Analyser.calculateDistance(pointa, pointb);


        assertEquals(distance, 106.70512500498786); //eveyone knows the distance between goroka and madang is 106.70512500498786 km


    }

    public void testRankAirports() throws Exception {
        //This Test filters the points and then makes sure each point is in the correct order
        //By going through and making sure a>=b for number of incoming and outgoing routes
        //Cool because it doesnt actually depend on data you put, just checks the relative results

        ArrayList<DataPoint> airports =  Filter.getAllPoints(DataTypes.AIRPORTPOINT);
        airports  = Analyser.rankAirportsByRoutes(airports);

        int max_size = airports.size();
        int current = 0;

        while (current < max_size-1){
            AirportPoint currentPoint = (AirportPoint) airports.get(current);
            AirportPoint nextPoint = (AirportPoint) airports.get(current+1);
            int curIncAndOutRoutes = currentPoint.getIncomingRoutes() + currentPoint.getOutgoingRoutes();
            int nextIncAndOutRoutes =  nextPoint.getIncomingRoutes() + nextPoint.getOutgoingRoutes();
            assertTrue(curIncAndOutRoutes>= nextIncAndOutRoutes);
            current++;
        }
    }


    public void testNumAirportsPerCountry() throws Exception{
        List<Map.Entry> a = Analyser.numAirportsPerCountry();
        assertEquals(a.get(0).getValue(), 80);
        assertEquals(a.get(0).getKey(), "Canada");
    }

    public void testNumAirlinesPerCountry() throws Exception{
       List<Map.Entry> result =  Analyser.numAirlinesPerCountry();
        assertEquals(result.get(0).getValue(), 18); //Max value is usa with 18 airlines

    }

    public void testRoutesPerEquipment() throws Exception{
        List<Map.Entry> result =  Analyser.routesPerEquipment();
        assertEquals(result.get(0).getValue(), 15463);
        assertEquals(result.get(0).getKey(), "320");
    }

    public void testRankAirportsByRoutes() throws Exception{
        List<Map.Entry> result =  Analyser.rankAirportsByRoutes("All");
        assertEquals(result.get(0).getKey(), "Canada");
        assertEquals(result.get(0).getValue(), 80);
        result =  Analyser.rankAirportsByRoutes("Canada");
        assertEquals(result.get(0).getKey(), "Canada");
        assertEquals(result.get(0).getValue(), 80);
    }

}