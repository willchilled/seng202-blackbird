package seng202.group2.blackbirdControl;

import seng202.group2.blackbirdModel.AirportPoint;

/**
 * Created by mch230 on 30/08/16.
 */
public class Analyser {

    public static double calculateDistance(AirportPoint airport1, AirportPoint airport2) {
        //calculate the distance between two airports
        //based off the haversine formula: http://www.movable-type.co.uk/scripts/latlong.html
        //issue- apparently this is slow. Google maps has a function to calculate distance between two points, could we use this?
        float lat1 = airport1.getLatitude();
        float long1 = airport1.getLongitude();

        float lat2 = airport2.getLatitude();
        float long2 = airport2.getLongitude();

        float R = 6371; // Radius of the earth in km
        double dLat = degToRad(lat2-lat1);  // degToRad below
        double dLon = degToRad(long2-long1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(degToRad(lat1)) * Math.cos(degToRad(lat2)) *
                Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km

        return d;
        }

    private static double degToRad(float deg) {
        //helper function to convert from degrees to radians
        return deg * (Math.PI/180.0);
    }

    //ranks airports based on the number of routes
    public static void rankAirports(boolean mostRoutes) {
        if (mostRoutes) {   //rank by the most routes

        } else {    //rank by the fewest routes

        }
    }
}
