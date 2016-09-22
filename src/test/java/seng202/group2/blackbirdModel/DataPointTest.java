package seng202.group2.blackbirdModel;

import junit.framework.TestCase;

import javax.xml.crypto.Data;

/**
 * Created by sha162 on 21/09/16.
 */
public class DataPointTest extends TestCase {
    public void testCreateDataPointFromStringArray() throws Exception {

        String[] myAirport = new String[]{"1", "A", "B", "C", "d", "e", "1", "2", "3", "10.0", "u", "HI", "5", "10"};
        //System.out.println(myAirport.length);
        DataPoint airport = DataPoint.createDataPointFromStringArray(myAirport, "AirportPoint");
        assertEquals(airport.getType(), "AirportPoint");
        //System.out.println("airport.toString() = " + airport.toString());
        AirportPoint myPoint  = (AirportPoint) airport;
        assertEquals(myPoint.getIncomingRoutes(), 10);
        assertEquals(myPoint.getOutgoingRoutes(), 5);


        //Testing to make sure you can make a new route point
        String[] myRoute = new String[]{"2G", "410", "AER", "100", "Letter", "100", "1", "10", "hi", "CHCHINT", "CHCH", "WELLY", "WELLYS"};

        DataPoint myRoutePoint = DataPoint.createDataPointFromStringArray(myRoute, "RoutePoint");
        assertEquals(myRoutePoint.getType(), "RoutePoint");
        RoutePoint myRoutePointCasted = (RoutePoint) myRoutePoint;
        assertEquals(myRoutePointCasted.getDstAirportName(), "WELLY");
        assertEquals(myRoutePointCasted.getDstAirportCountry(), "WELLYS");

        assertEquals(myRoutePointCasted.getSrcAirportName(), "CHCHINT");
        assertEquals(myRoutePointCasted.getSrcAirportCountry(), "CHCH");


    }

}