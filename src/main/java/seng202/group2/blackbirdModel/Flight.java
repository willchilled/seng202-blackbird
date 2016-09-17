package seng202.group2.blackbirdModel;

import java.util.ArrayList;

/**
 * A subclass of DataPoint to hold information about a Flight
 */
public class Flight extends DataPoint {

    private final String srcAirport;
    private final String destAirport;
    private final ArrayList<DataPoint> flightPoints;
    private int flightID;

//    public Flight(List<FlightPoint> flightPoints) {
//        this.flightPoints = flightPoints;
//        FlightPoint source = flightPoints.get(0);
//        FlightPoint dest = flightPoints.get(flightPoints.size() - 1);
//        this.srcAirport = source.getLocaleID();
//        this.destAirport = dest.getLocaleID();
//
//    }

    /**
     * Creates a flight with flight point, srcAirport and destAirport from a list of flight points
     * @param flightPoints a list of flight points with the first flightPoint giving the localeID for srcAirport and the last flightPoint giving the the localeId for the destAirport
     */
    public Flight(ArrayList<DataPoint> flightPoints) {
        this.flightPoints = flightPoints;
        FlightPoint source = (FlightPoint) flightPoints.get(0);
        FlightPoint dest = (FlightPoint) flightPoints.get(flightPoints.size() - 1);
        this.srcAirport = source.getLocaleID();
        this.destAirport = dest.getLocaleID();
    }


    public int getFlightID() {
        return flightID;
    }

    public String getSrcAirport() {
        return srcAirport;
    }

    public String getDestAirport() {
        return destAirport;
    }

    public ArrayList<DataPoint> getFlightPoints() {
        return flightPoints;
    }

/*
    @Override
    public String toString() {

        String returnString = String.format("\"%s\",%s,\"%s\",%s,\"%s\",%s,\"%s\",%s,\"%s\"",
                airline, airlineID, srcAirport, srcAirportID, dstAirport, dstAirportID, codeshare, stops, equipment);
        //String, int, string, int, string, int, string, int, string

        return returnString;

    }
*/

}


