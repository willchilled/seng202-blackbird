package seng202.group2.blackbirdModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for making a route for the purposes of displaying on the GUI.
 * Note: this is different to the RoutePoint class.
 *
 * @author Team2
 * @version 1.0
 * @since 26/9/2016
 */
public class Route {

    private List<Position> route = new ArrayList<>();

    /**
     * A constructor for making the route out of points
     *
     * @param positions a series of Points of the form (lat, long)
     */
    public Route(ArrayList<Position> positions) {
        route = positions;
    }

    /**
     * conversion to a JSON query of the route
     *
     * @return The JSON Array
     */
    public String toJSONArray() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        route.forEach(pos -> stringBuilder.append(
                String.format("{lat: %f, lng: %f}, ", pos.lat, pos.lng)));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /**
     * Creates a list of Positions using the position class in order to create a route
     *
     * @param flight with all its flight points
     * @return an Arraylist of the positions
     */
    public static ArrayList<Position> makeRoutePoints(Flight flight) {
        ArrayList<Position> positions = new ArrayList<>();
        ArrayList<DataPoint> flightPoints = flight.getFlightPoints();

        for (DataPoint flightPoint : flightPoints) {
            FlightPoint thisPoint = (FlightPoint) flightPoint;
            double lat = thisPoint.getLatitude();
            double lng = thisPoint.getLongitude();
            Position position = new Position(lat, lng);
            positions.add(position);
        }
        return positions;
    }

}
