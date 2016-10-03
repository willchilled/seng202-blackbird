package seng202.group2.blackbirdModel;

/**
 * Helper class to create Position objects, used by the map to display points
 *
 * @author Team2
 * @version 1.0
 * @since 26/09/16.
 */
public class Position {
    public double lat;
    public double lng;

    /**
     * A constructor for creating position objects with lat and long
     * @param lat double Latitude
     * @param lng double Longitude
     */
    public Position(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

}
