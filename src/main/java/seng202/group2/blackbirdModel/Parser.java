package seng202.group2.blackbirdModel;

import javafx.scene.control.Alert;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

    private static int numberOfCommas(String line) {
        return line.length() - line.replace(",", "").length();
    }

    private static boolean checkNull(String[] dataEntry) {
        boolean nullEntry = false;
        for (String data : dataEntry) {
            if (data.equals("\\N")) {
                nullEntry = true;
            }
        }
        return nullEntry;
    }

    private static String[] removeQuotes(String[] line) {
        for (int i = 0; i < line.length; i++) {
            line[i] = line[i].replaceAll("\"", "");
        }
        return line;
    }

    private static boolean checkAlphaNumeric(String s) {
        return (s.matches("[a-zA-Z0-9]+"));    //does not accept empty spaces atm
    }

    //------------------------FLIGHTS-------------------------//

    public static ArrayList<FlightPoint> parseFlightData(File file) {
       // System.out.print("pooooooooo");

        ArrayList<FlightPoint> myFlightSet = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                count++;
                if (numberOfCommas(line) == 4) {
                    //System.out.println("Got here");
                    String[] flightPoint = line.split(",");

                    if (!checkNull(flightPoint)) {
                        try {
                            //TO BE TESTED
                            String type = flightPoint[0].trim();
                            String localeID = flightPoint[1].trim();
                            int altitude = Integer.parseInt(flightPoint[2].trim());
                            float latitude = Float.parseFloat(flightPoint[3].trim());
                            float longitude = Float.parseFloat(flightPoint[4].trim());
                            FlightPoint myFlightPoint = new FlightPoint(type, localeID, altitude, latitude,
                                    longitude);
                            myFlightSet.add(myFlightPoint);
                        } catch (NumberFormatException e) {
                            System.out.println("Poo 0");
                        }

                    } else {
                        System.out.println("Poo 1");
                        return null;
                    }
                } else {
                    System.out.println("Poo 2");
                    return null;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Poo 3");
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myFlightSet;
    }

    //-------------------Route Data--------------------//

    public static RoutePoint checkRouteData(String[] routePoint, int count) {
        RoutePoint myRoutePoint = new RoutePoint("", 0);
        myRoutePoint.setRouteID(count);
        try {
            myRoutePoint.setAirline(routePoint[0].trim());
            if (routePoint[1].equals("\\N")) {
                myRoutePoint.setAirlineID(0);
            } else {
                myRoutePoint.setAirlineID(Integer.parseInt(routePoint[1].trim()));
            }
            myRoutePoint.setSrcAirport(routePoint[2].trim());

            if (routePoint[3].equals("\\N") || routePoint[3].isEmpty()) {
                myRoutePoint.setSrcAirportID(0);    //issues with foreign keys?
            } else {
                myRoutePoint.setSrcAirportID(Integer.parseInt(routePoint[3].trim()));
            }

            myRoutePoint.setDstAirport(routePoint[4].trim());
            if (routePoint[5].equals("\\N") || routePoint[5].isEmpty()) {
                myRoutePoint.setDstAirportID(0);
            } else {
                myRoutePoint.setDstAirportID(Integer.parseInt(routePoint[5].trim()));
            }
            if (routePoint[6].isEmpty() || routePoint[6].equals("\\N")) {
                myRoutePoint.setCodeshare("");
            } else {
                myRoutePoint.setCodeshare(routePoint[6].trim());
            }
            myRoutePoint.setStops(Integer.parseInt(routePoint[7].trim()));
            myRoutePoint.setEquipment(routePoint[8].trim());
        } catch (NumberFormatException e) {
            //myRoutePoint.setAirline("Error on input file line: " + count);
            return null;    ///Return null here?
        }
        return myRoutePoint;
    }

    //ROUTES
    //add commas in for route equipment
    public static ArrayList<RoutePoint> parseRouteData(File file) {


        ArrayList<RoutePoint> myRouteData = new ArrayList<RoutePoint>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            int count = 0;

            while ((line = br.readLine()) != null) {
                count++;
                if (line.isEmpty()) {
                    continue;
                }
                if (numberOfCommas(line) == 8) {
                    String[] routePoint = line.split(",", -1);    //-1 seemed to be required for if the very last field is empty
                    routePoint = removeQuotes(routePoint);
                    RoutePoint myRoutePoint = checkRouteData(routePoint, count);
                    myRouteData.add(myRoutePoint);
                } else {
                    RoutePoint myRoutePoint = new RoutePoint("", 0);
                    myRoutePoint.setRouteID(count);
                    myRouteData.add(myRoutePoint);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myRouteData;
    }

    //---------------------------AIRLINES------------------------------//

    public static AirlinePoint checkAirlineData(String[] airlinePoint, int count) {
        AirlinePoint myAirlinePoint = new AirlinePoint(0, "");
        try {
            myAirlinePoint.setAirlineID(Integer.parseInt(airlinePoint[0].trim()));    //should not be null
            myAirlinePoint.setAirlineName(airlinePoint[1]);    //let people name airline whatever they want

            if (airlinePoint[2].equals("\\N")) {
                myAirlinePoint.setAirlineAlias("");    //set as empty string if null
            } else {
                myAirlinePoint.setAirlineAlias(airlinePoint[2].trim());
            }
            if (airlinePoint[3].isEmpty() || airlinePoint[3].equals("\\N")) {
                myAirlinePoint.setIata("");
            } else {
                if (checkAlphaNumeric(airlinePoint[3].trim())) {
                    myAirlinePoint.setIata(airlinePoint[3].trim());
                } else {
                    myAirlinePoint.setAirlineName("Error on input file line: " + count);
                    return myAirlinePoint;
                }
            }
            if (airlinePoint[4].isEmpty() || airlinePoint[4].equals("\\N")) {
                myAirlinePoint.setIcao("");
            } else {
                if (checkAlphaNumeric(airlinePoint[4].trim())) {
                    myAirlinePoint.setIcao(airlinePoint[4].trim());
                } else {
                    myAirlinePoint.setAirlineName("Error on input file line: " + count);
                    return myAirlinePoint;
                }
            }
            myAirlinePoint.setCallsign(airlinePoint[5].trim());
            if (airlinePoint[6].isEmpty()) {
                myAirlinePoint.setCountry("");
            } else {
                myAirlinePoint.setCountry(airlinePoint[6].trim());    //should not be null, handle by parser later
            }
            myAirlinePoint.setActive(airlinePoint[7].trim());
        } catch (NumberFormatException e) {
            myAirlinePoint.setAirlineName("Error on input file line: " + count);
            return myAirlinePoint;
        }
        return myAirlinePoint;
    }

    public static ArrayList<AirlinePoint> parseAirlineData(File file) {

        ArrayList<AirlinePoint> myAirlineData = new ArrayList<AirlinePoint>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            int count = 0;
            while ((line = br.readLine()) != null) {
                count++;
                //Check number of fields is correct
                if (line.isEmpty()) {
                    continue;
                }

                if (numberOfCommas(line) == 7) {
                    String[] airlinePoint = line.split(",", -1);
                    airlinePoint = removeQuotes(airlinePoint);
                    AirlinePoint myAirlinePoint = checkAirlineData(airlinePoint, count);
                    myAirlineData.add(myAirlinePoint);
                } else {
                    //TODO
                    AirlinePoint myAirlinePoint = new AirlinePoint(0, "Error on input File line: " + count);
                    myAirlineData.add(myAirlinePoint);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myAirlineData;
    }


    //-----------------------------AIRPORTS------------------------------//

    public static AirportPoint checkAirportData(String[] airportPoint, int count) {
        System.out.println(Arrays.toString(airportPoint));
        AirportPoint myAirportPoint = new AirportPoint(0, "");
        try {
            myAirportPoint.setAirportID(Integer.parseInt(airportPoint[0].trim()));    //should not be null
            myAirportPoint.setAirportName(airportPoint[1].trim());    //future: test just for alpha chars + space
            myAirportPoint.setAirportCity(airportPoint[2].trim()); //test for just alpha chars
            myAirportPoint.setAirportCountry(airportPoint[3].trim()); //test for just alpha chars

            if (airportPoint[4].isEmpty() || airportPoint[4].equals("\\N")) {
                myAirportPoint.setIata("");
            } else {
                if (checkAlphaNumeric(airportPoint[4].trim())) {
                    myAirportPoint.setIata(airportPoint[4].trim());
                } else {
                    //myAirportPoint.setAirportName("Error on input file line: " + count);
                    return null;
                }
            }

            if (airportPoint[5].isEmpty() || airportPoint[5].equals("\\N")) {
                myAirportPoint.setIcao("");
            } else {
                if (checkAlphaNumeric(airportPoint[5].trim())) {
                    myAirportPoint.setIcao(airportPoint[5].trim());
                } else {
                    //myAirportPoint.setAirportName("Error on input file line: " + count);
                    return null;
                }
            }

            if (airportPoint[6].isEmpty() || airportPoint[6].equals("\\N")) {
                myAirportPoint.setLatitude(0);
            } else {
                myAirportPoint.setLatitude(Float.parseFloat(airportPoint[6].trim()));
            }

            if (airportPoint[7].isEmpty() || airportPoint[7].equals("\\N")) {
                myAirportPoint.setLongitude(0);
            } else {
                myAirportPoint.setLongitude(Float.parseFloat(airportPoint[7].trim()));
            }

            if (airportPoint[8].isEmpty() || airportPoint[8].equals("\\N")) {
                myAirportPoint.setAltitude(0);
            } else {
                myAirportPoint.setAltitude(Integer.parseInt(airportPoint[8].trim()));
            }

            if (airportPoint[9].isEmpty() || airportPoint[9].equals("\\N")) {
                myAirportPoint.setTimeZone(0);
            } else {
                myAirportPoint.setTimeZone(Float.parseFloat(airportPoint[9].trim()));
            }

            myAirportPoint.setDst(airportPoint[10].trim());

            if (airportPoint[11].equals("\\N") || airportPoint[11].isEmpty()) {
                //TODO FOR MEGAN, FIX HERE
                myAirportPoint.setTz("U");        //default to unknown?
            } else {
                myAirportPoint.setTz(airportPoint[11].trim());
            }
        } catch (NumberFormatException e) {
            //myAirportPoint.setAirportName("Error on input file line: " + count);
            return null;
        }
        return myAirportPoint;
    }

    public static ArrayList<AirportPoint> parseAirportData(File file) {

        ArrayList<AirportPoint> myAirportData = new ArrayList<AirportPoint>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            int count = 0;
            while ((line = br.readLine()) != null) {
                count++;

                if (line.isEmpty()) {
                    continue;
                }

                if (numberOfCommas(line) == 11) {
                    String[] airportPoint = line.split(",", -1);

                    airportPoint = removeQuotes(airportPoint);
                    AirportPoint myAirportPoint = checkAirportData(airportPoint, count);

                    //SQLiteJDBC.addAiportPoint(myAirportPoint);
                    myAirportData.add(myAirportPoint);


                } else {
                    AirportPoint myAirportPoint = new AirportPoint(0, "Input File line: " + count);
                    myAirportData.add(myAirportPoint);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myAirportData;
    }

    //Currently unused
//    public static void linkRoutesAndAirports(ArrayList<AirportPoint> airports, ArrayList<RoutePoint> routes) {
//        //function to link routes and airports
//        //called when flags from GUI (airportdataloaded = true, routedataloaded = true)
//        for (RoutePoint route : routes) {
//            //int operatingAirlineId = route.getAirlineID();	//should routes also link to airlines?
//            int srcAirportId = route.getSrcAirportID();
//            int destAirportId = route.getDstAirportID();
//
//            for (AirportPoint airport : airports) {
//                if (srcAirportId == airport.getAirportID()) {
//                    route.setSource(airport);
//                    airport.incrementRoutes();
//
//                } else if (destAirportId == airport.getAirportID()) {
//                    route.setDestination(airport);
//                    airport.incrementRoutes();
//                } else {
//                    //TODO
//                    //raise an exception here? a route is using an airport that doesn't exist
//                }
//            }
//        }
//    }


}

