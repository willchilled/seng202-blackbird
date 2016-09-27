package seng202.group2.blackbirdModel;

/**
 * Created by wmu16 on 26/09/16.
 */
public class Position {
    public double lat;
    public double lng;

    /**
     * A constructor for creating positioni objects with lat and long
     * @param lat double Latitude
     * @param lng double Longitude
     */
    public Position(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
