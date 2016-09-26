package seng202.group2.blackbirdControl;

import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.DataPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mch230 on 30/08/16.
 */
public class Analyser {

    //calculate distance between two airports
    //may need to convert the input parameters to be strings instead, if that's easier for the GUI?
    public static double calculateDistance(AirportPoint airport1, AirportPoint airport2) {
        //based off the haversine formula: http://www.movable-type.co.uk/scripts/latlong.html
        //issue- apparently this is slow. Google maps has a function to calculate distance between two points which might be better?
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
    public static ArrayList<DataPoint> rankAirports(ArrayList<DataPoint> airports, boolean mostRoutes) {
        //To whoever does this just make sure it returns and arraylist of datapoints and not airport poitns doesnt work

        ArrayList<DataPoint> rankedData = new ArrayList<DataPoint>();

        List<AirportPoint> myPoints = new ArrayList<>();
        for(DataPoint cp: airports){
            AirportPoint cp2 = (AirportPoint) cp;
            myPoints.add(cp2);
        }


        //Collections.sort(myPoints, (x, y) -> compareAirportPointSize(x, y));

        if (!mostRoutes) {   //rank by least Routes
            Collections.reverse(myPoints);
        }

        for(AirportPoint currentPoint: myPoints){
            //AirportPoint cp2 = (AirportPoint) cp;
            //System.out.println(cp.toStringWithRoutes());
            DataPoint convertedRoutePoint = currentPoint;
            rankedData.add(convertedRoutePoint);

        }
        return rankedData;
    }


    public static int compareAirportPointSize(AirportPoint a, AirportPoint another) {
        if (a.getIncomingRoutes() + a.getOutgoingRoutes() < another.getIncomingRoutes() + another.getOutgoingRoutes()){
            return 1;
        }else{
            return -1;
        }
    }


}
