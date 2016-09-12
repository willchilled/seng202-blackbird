package seng202.group2.blackbirdModel;

import junit.framework.TestCase;
import seng202.group2.blackbirdControl.Filter;

import javax.swing.*;
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

        airlinesFileString = cwd + "/JUnitTesting/airlines.txt";
        airportsFileString = cwd + "/JUnitTesting/airports.txt";

        File airlinesFile = new File(airlinesFileString);
        File airportsFile = new File(airportsFileString);

        ArrayList<AirlinePoint> airlinePoints = Parser.parseAirlineData(airlinesFile);
        ArrayList<AirportPoint> airportPoints = Parser.parseAirportData(airportsFile);


        BBDatabase.deleteDBFile();
        BBDatabase.createTables();
        BBDatabase.addAirlinePointstoDB(airlinePoints);
        BBDatabase.addAiportPortsToDB(airportPoints);
        //myAirlineData = Filter.getAllAirlinePointsfromDB();




    }

    public void testGetDatabaseName() throws Exception {
        assertEquals(BBDatabase.getDatabaseName(), "jdbc:sqlite:default.db");
    }

    public void testPerformAirpointsQuery() throws Exception {
        ArrayList<AirportPoint> airportPoints = BBDatabase.performAirpointsQuery("SELECT * FROM AIRPORT");
        assertEquals(airportPoints.size(), 100);
    }

    public void testPerformAirlinesQuery() throws Exception {
        ArrayList<AirlinePoint> airlinePoints = BBDatabase.performAirlinesQuery("SELECT * FROM AIRLINE");
        assertEquals(airlinePoints.size(), 100);

    }

}