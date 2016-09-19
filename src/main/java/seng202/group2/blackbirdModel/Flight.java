package seng202.group2.blackbirdModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A subclass of DataPoint to hold information about a Flight
 */
public class Flight extends DataPoint {

    private String srcAirport;
    private String destAirport;
    private Collection<FlightPoint> flightPoints;
    private int flightID;

    /**
     * Creates a flight from a list of FlightPoints
     * @param flightPoints a list of flight points with the first flightPoint giving the localeID for srcAirport and the last flightPoint giving the the localeId for the destAirport
     */
    public Flight(List<FlightPoint> flightPoints) {
        this.flightPoints = flightPoints;
        FlightPoint source = flightPoints.get(0);
        FlightPoint dest = flightPoints.get(flightPoints.size() - 1);
        this.srcAirport = source.getLocaleID();
        this.destAirport = dest.getLocaleID();

    }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) { this.flightID = flightID;}

    public String getSrcAirport() {
        return srcAirport;
    }

    public void setSrcAirport(String srcAirport) { this.srcAirport = srcAirport;}

    public String getDestAirport() {
        return destAirport;
    }

    public void setDestAirport(String destAirport) { this.destAirport = destAirport;}

    public Collection<FlightPoint> getFlightPoints() {
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


