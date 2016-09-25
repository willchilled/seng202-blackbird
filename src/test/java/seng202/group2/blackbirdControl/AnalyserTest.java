package seng202.group2.blackbirdControl;

import junit.framework.TestCase;
import seng202.group2.blackbirdModel.*;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Filter;

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
        routesFileString = cwd + "/JUnitTesting/route60000.txt";
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




    }
    public void testCalculateDistance() throws Exception {
        ArrayList<DataPoint>  myPoint = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);

        AirportPoint pointa = (AirportPoint) myPoint.get(0);
        AirportPoint pointb = (AirportPoint) myPoint.get(1);


        double distance = Analyser.calculateDistance(pointa, pointb);


        assertEquals(distance, 106.70512500498786); //eveyone knows the distance between goroka and madang is 106.70512500498786 km


    }

    public void testRankAirports() throws Exception {

        ArrayList<DataPoint> airports =  FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        Analyser.rankAirports(airports, true);

    }

}