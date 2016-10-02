package seng202.group2.blackbirdModel;

import java.util.ArrayList;
import java.util.List;

/**A class for making a route for the purposes of displaying on the GUI
 * Created by wmu16 on 26/09/16.
 */
public class Route {

    private List<Position> route = new ArrayList<>();

    /**
     * A constructor for making the route out of points
     * @param positions a series of Points of the form (lat, long)
     */
    public Route(ArrayList<Position> positions) {
        route = positions;
    }

    /**
     * conversion to a JScript executable string of the route
     * @return The JScript Array
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
     * @param flight with all its flight points
     * @return an Arraylist of the positions
     */
    public static ArrayList<Position> makeRoutePoints(Flight flight){
        ArrayList<Position> positions = new ArrayList<>();
        ArrayList<DataPoint> flightPoints = flight.getFlightPoints();

        for (int i = 0; i < flightPoints.size(); i++) {
            FlightPoint thisPoint = (FlightPoint) flightPoints.get(i);
            double lat = thisPoint.getLatitude();
            double lng = thisPoint.getLongitude();
            Position position = new Position(lat, lng);
            positions.add(position);
        }
        return positions;
    }

}
