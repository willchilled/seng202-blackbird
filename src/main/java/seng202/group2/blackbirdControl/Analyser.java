package seng202.group2.blackbirdControl;

import seng202.group2.blackbirdModel.AirlinePoint;
import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.DataPoint;
import seng202.group2.blackbirdModel.DataTypes;

import java.util.*;

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
        //rename this to rank aiports by routes later ok!
        //To whoever does this just make sure it returns and arraylist of datapoints and not airport poitns doesnt work

        ArrayList<DataPoint> rankedData = new ArrayList<DataPoint>();

        List<AirportPoint> myPoints = new ArrayList<>();
        for(DataPoint cp: airports){
            AirportPoint cp2 = (AirportPoint) cp;
            myPoints.add(cp2);
        }


        Collections.sort(myPoints, (x, y) -> compareAirportPointSize(x, y));

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
        if (a.getIncomingRoutes() + a.getOutgoingRoutes() == another.getIncomingRoutes() + another.getOutgoingRoutes()){
            if (a.getAirportName().compareTo(another.getAirportName()) >=0 ){
                return 1;
            }
            else{
                return -1;
            }

        }
        else if (a.getIncomingRoutes() + a.getOutgoingRoutes() < another.getIncomingRoutes() + another.getOutgoingRoutes()){
            return 1;
        }else  {
            return -1;
        }

    }

    public static List<Map.Entry> numAirportsPerCountry(){
        ArrayList<DataPoint> allAirports = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        ArrayList<String> allCountries = FilterRefactor.filterDistinct("country", "Airport");

        HashMap<String, Integer> airportsPerCountry = new HashMap<>();

        for(String country: allCountries){
            airportsPerCountry.put(country, 0);
        }

        for (DataPoint currentPoint: allAirports){
            AirportPoint currentAirportPoint = (AirportPoint) currentPoint;
            String country = currentAirportPoint.getAirportCountry();
            int counter = airportsPerCountry.get(country);

            airportsPerCountry.put(currentAirportPoint.getAirportCountry(), counter +1);
        }
        //List<> list = new ArrayList<>(airportsPerCountry.values());
        List<Map.Entry> results = new ArrayList(airportsPerCountry.entrySet());

        results = sortDictByValue(results);



        return results;

    }

    public static List<Map.Entry> numAirlinesPerCountry(){
        ArrayList<DataPoint> allAirports = FilterRefactor.getAllPoints(DataTypes.AIRLINEPOINT);
        ArrayList<String> allCountries = FilterRefactor.filterDistinct("country", "Airline");

        HashMap<String, Integer> airlinesPerCountry = new HashMap<>();

        for(String country: allCountries){
            airlinesPerCountry.put(country, 0);
        }

        for (DataPoint currentPoint: allAirports){
            AirlinePoint currentAirlinePoint = (AirlinePoint) currentPoint;

            String country = currentAirlinePoint.getCountry();
            int counter = airlinesPerCountry.get(country);

            airlinesPerCountry.put(currentAirlinePoint.getCountry(), counter +1);
        }

        List<Map.Entry> results = new ArrayList(airlinesPerCountry.entrySet());

        results = sortDictByValue(results);



        return results;
    }

    private static List<Map.Entry> sortDictByValue(List<Map.Entry> results) {
        Collections.sort(results, new Comparator<Map.Entry>() {
            @Override
            public int compare(Map.Entry o1, Map.Entry o2) {
                int val1 = (int) o1.getValue();
                int val2 = (int) o2.getValue();

                if (val1 < val2){
                    return 1;
                }
                else if (val1 > val2)
                {
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        return results;
    }


}
