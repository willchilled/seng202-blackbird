package seng202.group2.blackbirdModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	
	
	//FLIGHTS
	public static ArrayList<FlightPoint> parseFlightData(File file){
		
		ArrayList<FlightPoint> myFlightSet = new ArrayList<FlightPoint>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = br.readLine()) != null) {
			    String[] flightPoint = line.split(",");
			    
				String type = flightPoint[0];
				String localeID = flightPoint[1];
				int altitude = Integer.parseInt(flightPoint[2]);
				float latitude = Float.parseFloat(flightPoint[3]);
				float longitude = Float.parseFloat(flightPoint[4]);

			   FlightPoint myFlightPoint = new FlightPoint(type, localeID, altitude, latitude,
					   longitude);
			   myFlightSet.add(myFlightPoint);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return myFlightSet;
	}

	
	//ROUTES
	public static ArrayList<RoutePoint> parseRouteData(File file){
		
		
		ArrayList<RoutePoint> myRouteData = new ArrayList<RoutePoint>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = br.readLine()) != null) {
			    String[] routePoint = line.split(",");
			    
			    String airline = routePoint[0];
				int airlineID = Integer.parseInt(routePoint[1]);
				RoutePoint myRoutePoint = new RoutePoint(airline, airlineID);
				
				myRoutePoint.setSrcAirport(routePoint[2]);
				myRoutePoint.setSrcAirportID(Integer.parseInt(routePoint[3]));
				myRoutePoint.setDstAirport(routePoint[4]);
				myRoutePoint.setDstAirportID(Integer.parseInt(routePoint[5]));
				myRoutePoint.setCodeshare(routePoint[6]);
				myRoutePoint.setStops(Integer.parseInt(routePoint[7]));
				myRoutePoint.setEquipment(routePoint[8].split(" "));	


			   myRouteData.add(myRoutePoint);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return myRouteData;
	}
	
	
	//AIRLINES
	public static ArrayList<AirlinePoint> parseAirlineData(File file){
		
		ArrayList<AirlinePoint> myAirlineData = new ArrayList<AirlinePoint>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = br.readLine()) != null) {
			    String[] airlinePoint = line.split(",");
			    
				int airlineID = Integer.parseInt(airlinePoint[0]);
				String airlineName = airlinePoint[1];
				AirlinePoint myAirlinePoint = new AirlinePoint(airlineID, airlineName);
				
				myAirlinePoint.setAirlineAlias(airlinePoint[2]);
				myAirlinePoint.setIata(airlinePoint[3]);
				myAirlinePoint.setIcao(airlinePoint[4]);
				myAirlinePoint.setCallsign(airlinePoint[5]);
				myAirlinePoint.setCountry(airlinePoint[6]);
				myAirlinePoint.setActive(airlinePoint[7]);

			   
			   myAirlineData.add(myAirlinePoint);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return myAirlineData;	}
	
	
	
	//AIRPORTS
	public static ArrayList<AirportPoint> parseAirportData(File file){

		ArrayList<AirportPoint> myAirportData = new ArrayList<AirportPoint>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = br.readLine()) != null) {
			    String[] airportPoint = line.split(",");
			    
				int airportID = Integer.parseInt(airportPoint[0]);
				String airportName = airportPoint[1];
				AirportPoint myAirportPoint = new AirportPoint(airportID, airportName);
				
				myAirportPoint.setAirportCity(airportPoint[2]);
				myAirportPoint.setAirportCountry(airportPoint[3]);
				myAirportPoint.setIata(airportPoint[4]);
				myAirportPoint.setIcao(airportPoint[5]);
				myAirportPoint.setLatitude(Float.parseFloat(airportPoint[6]));
				myAirportPoint.setLongitude(Float.parseFloat(airportPoint[7]));
				myAirportPoint.setAltitude(Integer.parseInt(airportPoint[8]));
				myAirportPoint.setTimeZone(Float.parseFloat(airportPoint[9]));
				myAirportPoint.setDst(airportPoint[10]);
				myAirportPoint.setTz(airportPoint[11]);	


			   myAirportData.add(myAirportPoint);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return myAirportData;
	}
	

	
	//TEST FOR PARSER. OBV FILES ARE LOCAL TO ME. CHANGE THESE DIRECTORIES TO TRY FOR YOURSELF. ONLY FOUR
	//ATTRIBUTES HAVE BEEN TESTED FOR EACH FIELD ALTHOUGH IT IS IMPORTANT TO TRY OTHERS AS WELL.
	public static void main(String[] args) {
		
		//TESTING FLIGHT FILES A OK :)
		File flightFile = new File("C:\\Users\\William\\Documents\\flight.csv");
		ArrayList<FlightPoint> myFlightData = parseFlightData(flightFile);
		for(int i = 0; i < myFlightData.size(); i++){
			System.out.println("Test Flight Data Row[" + i + "]");
			System.out.println("Type: " + myFlightData.get(i).getType());
			System.out.println("LocaleID: " + myFlightData.get(i).getLocaleID());
			System.out.println("Altitude: " + myFlightData.get(i).getAltitude());
			System.out.println("Latitude: " + myFlightData.get(i).getLatitude());
			System.out.println("Longitude: " + myFlightData.get(i).getLongitude());
			System.out.println("\n");
		}
		
//		//TESTING ROUTE FILES
//		File routeFile = new File("C:\\Users\\William\\Documents\\routes.csv");
//		ArrayList<RoutePoint> myRouteData = parseRouteData(routeFile);
//		for(int i = 0; i < 50; i++){
//			System.out.println("Test Route Data row[" + i + "]");
//			System.out.println("Airline: " + myRouteData.get(i).getAirline());
//			System.out.println("AirlineID: " + myRouteData.get(i).getAirlineID());
//			System.out.println("Src Airport: " + myRouteData.get(i).getSrcAirport());
//			System.out.println("Src Airport ID: " + myRouteData.get(i).getSrcAirportID());
//			System.out.println("Dst Airport: " + myRouteData.get(i).getDstAirport());
//			System.out.println("Dst Airport ID: " + myRouteData.get(i).getDstAirportID());
//			System.out.println("CodeShare: " + myRouteData.get(i).getCodeshare());
//			System.out.println("Stops: " + myRouteData.get(i).getStops());
//			System.out.println("Equipment: " + myRouteData.get(i).getEquipment());
//			System.out.println("\n");
//		}
		
//		//TESTING AIRLINE FILES A OK :)
//		File airlineFile = new File("C:\\Users\\William\\Documents\\airlines.csv");
//		ArrayList<AirlinePoint> myAirlineData = parseAirlineData(airlineFile);
//		for(int i = 0; i < 50; i++){
//			System.out.println("Test Airline Data row[" + i + "]");
//			System.out.println("Airline ID: " + myAirlineData.get(i).getAirlineID());
//			System.out.println("Airline Name: " + myAirlineData.get(i).getAirlineName());
//			System.out.println("Airline Alias: " + myAirlineData.get(i).getAirlineAlias());
//			System.out.println("IATA Code: " + myAirlineData.get(i).getIata());
//			System.out.println("\n");
//		}
		
//		//TESTING AIRPORT FILES
//		File airportFile = new File("C:\\Users\\William\\Documents\\airports.csv");
//		ArrayList<AirportPoint> myAirportData = parseAirportData(airportFile);
//		for(int i = 0; i < 500; i++){	
//			System.out.println("Test Airport Data row[" + i + "]");
//			System.out.println("AirportID: " + myAirportData.get(i).getAirportID());
//			System.out.println("AirportName: " + myAirportData.get(i).getAirportName());
//			System.out.println("AirportCity: " + myAirportData.get(i).getAirportCity());
//			System.out.println("AirportCountry: " + myAirportData.get(i).getAirportCountry());
//			System.out.println("\n");
//		}
		

	}
	
	
}

