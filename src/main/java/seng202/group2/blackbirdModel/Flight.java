package seng202.group2.blackbirdModel;

import java.util.ArrayList;

/**
 * Created by emr65 on 12/09/16.
 */
public class Flight extends DataPoint{

    private int flightID;
    private String srcAirport;
    private String destAirport;
    private ArrayList<FlightPoint> flightPoints;

    public Flight(ArrayList<FlightPoint> flightPoints){
        this.flightPoints = flightPoints;
        FlightPoint source = flightPoints.get(0);
        FlightPoint dest = flightPoints.get(flightPoints.size()-1);
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

    public ArrayList<FlightPoint> getFlightPoints() {
        return flightPoints;
    }

}
