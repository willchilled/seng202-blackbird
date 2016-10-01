package seng202.group2.blackbirdControl;

import seng202.group2.blackbirdModel.*;

import javax.xml.crypto.Data;
import java.util.*;

/**
 * Created by mch230 on 30/08/16.
 */
public class Analyser {


    /**
     * Finds the distance between two airports
     * @param airport1 the source airport
     * @param airport2 the destination airport
     * @return the distance between airport 1 and 2
     */
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

    /**
     *Finds all of the airports in a county and ranks the airports by num of routes
     * @param country the country you want to rank
     * @return dictionary containing string of airports as key and number of routes as value
     */
    public static List<Map.Entry> rankAirportsByRoutes(String country) {
        ArrayList<DataPoint> myPoints = new ArrayList<>();
        if (country.equals("All")){
            myPoints = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        }
        else{
            ArrayList<String> menus = new ArrayList<>(Arrays.asList(country));
            myPoints = FilterRefactor.filterSelections(menus, "", DataTypes.AIRPORTPOINT);
        }


        ArrayList<String> allCountries = FilterRefactor.filterDistinct("country", "Airport");
        HashMap<String, Integer> airportsPerCountry = new HashMap<>();

        for(String currentCountry: allCountries){
            airportsPerCountry.put(currentCountry, 0);
        }

        for (DataPoint currentPoint: myPoints){
            AirportPoint currentAirportPoint = (AirportPoint) currentPoint;
            String newCountry = currentAirportPoint.getAirportCountry();
            int counter = airportsPerCountry.get(newCountry);

            airportsPerCountry.put(currentAirportPoint.getAirportCountry(), counter +1);
        }
        List<Map.Entry> results = new ArrayList(airportsPerCountry.entrySet());
        results = sortDictByValue(results);

        return results;


    }


    /**
     * Analyses all of given airports and ranks them according to most or least incoming
     * or outgoing routes
     * @param airports the list of airports needing to be filtered
     * @param mostRoutes if you want the airports to to be filtered by most or least routes
     * @return the list of airports points filtered by number of incoming and outgoing routes
     */
    public static ArrayList<DataPoint> rankAirportsByRoutes(ArrayList<DataPoint> airports, boolean mostRoutes) {
        //technically most routes is deprecated as we dont want to filter by least routes as it will return
        //lots of 0's, but its not worth the time to change just means you have to parse in an extra value
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
            DataPoint convertedRoutePoint = currentPoint;
            rankedData.add(convertedRoutePoint);

        }
        return rankedData;
    }


    private static int compareAirportPointSize(AirportPoint a, AirportPoint another) {
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

    /**
     * Finds the number of airports in each country
     * @return a dict with number of country as key and number of airports in it as value
     */
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

        List<Map.Entry> results = new ArrayList(airportsPerCountry.entrySet());
        results = sortDictByValue(results);

        return results;
    }

    /**
     * Ranks the number of airlines in all of the countries
     * @return a dict with countries as the key and number of airlines in that country as the value
     */
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


    /**
     * Finds the routes that involves each type of equipment
     * @return dictionary where the key is the equipment name and value is the number of route that use it
     */
    public static List<Map.Entry> routesPerEquipment(){
        ArrayList<DataPoint> routePoints = FilterRefactor.getAllPoints(DataTypes.ROUTEPOINT);
        ArrayList<String> uniqueEquip = new ArrayList<>();
        HashSet<String> equipSet = new HashSet<>();

        for (int i = 0; i <routePoints.size() ; i++) {
            RoutePoint currentPoint = (RoutePoint) routePoints.get(i);
            String currentEquip = currentPoint.getEquipment();

            if (currentEquip != null) {
                String[] equipArray = currentEquip.split(" ");
                for (String myEquip : equipArray) {
                    equipSet.add(myEquip);
                }
            }
        }

        uniqueEquip.addAll(equipSet);
        HashMap<String, Integer> routesPerEquip = new HashMap<>();

        for(String country: uniqueEquip){
            routesPerEquip.put(country, 0);
        }

        for (DataPoint currentPoint: routePoints){
            RoutePoint currentRoute = (RoutePoint) currentPoint;
            String currentEquip = currentRoute.getEquipment();

            if (currentEquip != null) {
                String[] equipArray = currentEquip.split(" ");
                for (String myEquip : equipArray) {
                    routesPerEquip.put(myEquip, routesPerEquip.get(myEquip) + 1);
                }
            }
        }
        List<Map.Entry> results = new ArrayList(routesPerEquip.entrySet());
        results = sortDictByValue(results);

        return results;
    }

    private static List<Map.Entry> sortDictByValue(List<Map.Entry> results) {
        // sorts dictionary by the value
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
