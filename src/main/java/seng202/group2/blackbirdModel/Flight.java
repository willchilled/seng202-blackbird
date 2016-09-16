package seng202.group2.blackbirdModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by emr65 on 12/09/16.
 *
 */
public class Flight extends DataPoint {

    private final String srcAirport;
    private final String destAirport;
    private final Collection<FlightPoint> flightPoints;
    private int flightID;

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

    public String getSrcAirport() {
        return srcAirport;
    }

    public String getDestAirport() {
        return destAirport;
    }

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


