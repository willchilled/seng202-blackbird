package seng202.group2.blackbirdControl;

import seng202.group2.blackbirdModel.*;

import java.util.*;

/**
 * Performs the analysis of data in the BlackBird program
 *
 * @author Team2
 * @version 1.0
 * @since 19/9/2016
 */
class Analyser {

    /**
     * Finds the distance between two airports, based off the Haversine Formula.
     *
     * @param airport1 the source airport
     * @param airport2 the destination airport
     * @return the distance between airport 1 and 2
     */
    static double calculateDistance(AirportPoint airport1, AirportPoint airport2) {
        float lat1 = airport1.getLatitude();
        float long1 = airport1.getLongitude();
        float lat2 = airport2.getLatitude();
        float long2 = airport2.getLongitude();
        float R = 6371; // Radius of the earth in km

        double dLat = degToRad(lat2 - lat1);
        double dLon = degToRad(long2 - long1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(degToRad(lat1)) * Math.cos(degToRad(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    /**
     * Helper function to convert degrees to radians
     * @param deg Location in degrees
     * @return Location in radians
     */
    private static double degToRad(float deg) {
        return deg * (Math.PI / 180.0);
    }

    /**
     * Finds all of the airports in a county and ranks the airports by num of routes
     *
     * @param country the country you want to rank
     * @return dictionary containing string of airports as key and number of routes as value
     */
    static List<Map.Entry> rankAirportsByRoutes(String country) {
        ArrayList<DataPoint> myPoints;
        if (country.equals("All")) {
            myPoints = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        } else {
            ArrayList<String> menus = new ArrayList<>(Arrays.asList(country));
            myPoints = FilterRefactor.filterSelections(menus, "", DataTypes.AIRPORTPOINT);
        }

        ArrayList<String> allCountries = FilterRefactor.filterDistinct("country", "Airport");
        HashMap<String, Integer> airportsPerCountry = new HashMap<>();

        for (String currentCountry : allCountries) {
            airportsPerCountry.put(currentCountry, 0);
        }

        assert myPoints != null;
        for (DataPoint currentPoint : myPoints) {
            AirportPoint currentAirportPoint = (AirportPoint) currentPoint;
            String newCountry = currentAirportPoint.getAirportCountry();
            int counter = airportsPerCountry.get(newCountry);
            airportsPerCountry.put(currentAirportPoint.getAirportCountry(), counter + 1);
        }
        List<Map.Entry> results = new ArrayList(airportsPerCountry.entrySet());
        results = sortDictByValue(results);

        return results;
    }


    /**
     * Analyses all of given airports and ranks them according to most or least incoming
     * or outgoing routes
     *
     * @param airports   the list of airports needing to be filtered
     * @return the list of airports points filtered by number of incoming and outgoing routes
     */
    static ArrayList<DataPoint> rankAirportsByRoutes(ArrayList<DataPoint> airports) {
        ArrayList<DataPoint> rankedData = new ArrayList<>();
        List<AirportPoint> myPoints = new ArrayList<>();
        for (DataPoint cp : airports) {
            AirportPoint cp2 = (AirportPoint) cp;
            myPoints.add(cp2);
        }
        Collections.sort(myPoints, (x, y) -> compareAirportPointSize(x, y));
        for (AirportPoint currentPoint : myPoints) {
            DataPoint convertedRoutePoint = currentPoint;
            rankedData.add(convertedRoutePoint);
        }
        return rankedData;
    }

    /**
     * Helper function to compare between the number of routes between two airports
     * @param first First airport
     * @param second Second airport
     * @return An integer indicating whether the first airport has more routes than the second (1 if more routes, -1 if fewer routes)
     */
    private static int compareAirportPointSize(AirportPoint first, AirportPoint second) {
        if (first.getIncomingRoutes() + first.getOutgoingRoutes() == second.getIncomingRoutes() + second.getOutgoingRoutes()) {
            if (first.getAirportName().compareTo(second.getAirportName()) >= 0) {
                return 1;
            } else {
                return -1;
            }

        } else if (first.getIncomingRoutes() + first.getOutgoingRoutes() < second.getIncomingRoutes() + second.getOutgoingRoutes()) {
            return 1;
        } else {
            return -1;
        }

    }

    /**
     * Finds the number of airports in each country
     *
     * @return a dict with number of country as key and number of airports in it as value
     */
    static List<Map.Entry> numAirportsPerCountry() {
        ArrayList<DataPoint> allAirports = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        ArrayList<String> allCountries = FilterRefactor.filterDistinct("country", "Airport");
        HashMap<String, Integer> airportsPerCountry = new HashMap<>();

        for (String country : allCountries) {
            airportsPerCountry.put(country, 0);
        }
        for (DataPoint currentPoint : allAirports) {
            AirportPoint currentAirportPoint = (AirportPoint) currentPoint;
            String country = currentAirportPoint.getAirportCountry();
            int counter = airportsPerCountry.get(country);
            airportsPerCountry.put(currentAirportPoint.getAirportCountry(), counter + 1);
        }

        List<Map.Entry> results = new ArrayList(airportsPerCountry.entrySet());
        results = sortDictByValue(results);
        return results;
    }

    /**
     * Ranks the number of airlines in all of the countries
     *
     * @return a dict with countries as the key and number of airlines in that country as the value
     */
    public static List<Map.Entry> numAirlinesPerCountry() {
        ArrayList<DataPoint> allAirports = FilterRefactor.getAllPoints(DataTypes.AIRLINEPOINT);
        ArrayList<String> allCountries = FilterRefactor.filterDistinct("country", "Airline");
        HashMap<String, Integer> airlinesPerCountry = new HashMap<>();

        for (String country : allCountries) {
            airlinesPerCountry.put(country, 0);
        }

        for (DataPoint currentPoint : allAirports) {
            AirlinePoint currentAirlinePoint = (AirlinePoint) currentPoint;

            String country = currentAirlinePoint.getCountry();
            int counter = airlinesPerCountry.get(country);

            airlinesPerCountry.put(currentAirlinePoint.getCountry(), counter + 1);
        }

        List<Map.Entry> results = new ArrayList(airlinesPerCountry.entrySet());
        results = sortDictByValue(results);
        return results;
    }


    /**
     * Finds the routes that involves each type of equipment
     *
     * @return dictionary where the key is the equipment name and value is the number of route that use it
     */
    static List<Map.Entry> routesPerEquipment() {
        ArrayList<DataPoint> routePoints = FilterRefactor.getAllPoints(DataTypes.ROUTEPOINT);
        ArrayList<String> uniqueEquip = new ArrayList<>();
        HashSet<String> equipSet = new HashSet<>();

        for (DataPoint routePoint : routePoints) {
            RoutePoint currentPoint = (RoutePoint) routePoint;
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

        for (String country : uniqueEquip) {
            routesPerEquip.put(country, 0);
        }
        for (DataPoint currentPoint : routePoints) {
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

    /**
     * Sorts dictionary by the value
     * @param results List containing dictionary values where the key is the equipment name and value is the number of route that use it
     * @return A sorted list collection containing these dictionary entries
     */
    private static List<Map.Entry> sortDictByValue(List<Map.Entry> results) {
        Collections.sort(results, (o1, o2) -> {
            int val1 = (int) o1.getValue();
            int val2 = (int) o2.getValue();

            if (val1 < val2) {
                return 1;
            } else if (val1 > val2) {
                return -1;
            } else {
                return 0;
            }
        });
        return results;
    }

}
