package seng202.group2.blackbirdControl;

import seng202.group2.blackbirdModel.AirportPoint;

/**
 * Created by mch230 on 30/08/16.
 */
public class Analyser {
    public static void calculateDistance(AirportPoint airport1, AirportPoint airport2) {
        float lat1 = airport1.getLatitude();
        float long1 = airport1.getLongitude();

        float lat2 = airport2.getLatitude();
        float long2 = airport2.getLongitude();

        //how to calculate distance?
    }

    //ranks airports based on the number of routes
    //how are we gonna implement this?
    public static void rankAirports(boolean mostRoutes) {
        if (mostRoutes) {   //rank by the most routes

        } else {    //rank by the fewest routes

        }
    }
}
