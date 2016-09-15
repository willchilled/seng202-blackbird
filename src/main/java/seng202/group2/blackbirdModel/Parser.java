package seng202.group2.blackbirdModel;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	//issues: if an entry contains a comma within, it messes with the indexing of the line
	//handling semantics: checking which fields should be unique, checking for valid IATA/ICAO codes, deciding which fields
	//shouldn't be null

	
	//FLIGHTS
	private static boolean checkNull(String[] dataEntry){
		boolean nullEntry = false;
		for (String data : dataEntry) {
			//System.out.println(data);
			if (data.equals("\\N")) {
				//System.out.println("Null entry true");
				nullEntry = true;
			}
		}
		return nullEntry;
	}

	//fields of airpoint point shouldn't be null?
	public static ArrayList<FlightPoint> parseFlightData(File file){
		
		ArrayList<FlightPoint> myFlightSet = new ArrayList<FlightPoint>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			int count = 0;
			while ((line = br.readLine()) != null) {
				count++;
			    if(numberOfCommas(line) == 4) {
                    String[] flightPoint = line.split(",");
                    
                    if(!checkNull(flightPoint)){
						try {
							//TO BE TESTED
							String type = flightPoint[0];
							String localeID = flightPoint[1];
							int altitude = Integer.parseInt(flightPoint[2]);
							float latitude = Float.parseFloat(flightPoint[3]);
							float longitude = Float.parseFloat(flightPoint[4]);
							FlightPoint myFlightPoint = new FlightPoint(type, localeID, altitude, latitude,
									longitude);
							myFlightSet.add(myFlightPoint);
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(new JPanel(), "There was some incorrect data in your file on line: " + count,
									"Error", JOptionPane.ERROR_MESSAGE);
							System.err.println( e.getClass().getName() + ": " + e.getMessage() );
						}

                    } else {
						JOptionPane.showMessageDialog(new JPanel(), "Error on line: " + count + ". Empty data fields are not allowed for flight entries.\n" +
								"Please review your input file.", "Error", JOptionPane.ERROR_MESSAGE);
                    	System.err.println("Error: Null field in flight data. All fields must be filled");
						//currently, aborting if any null fields present in flight data.
                    	break;
                    }
                } else {
					//will any flight data have extra commas?
					JOptionPane.showMessageDialog(new JPanel(), "There was some incorrect data in your file on line: " + count,
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return myFlightSet;
	}

	private static String[] removeQuotes(String[] line){
		for (int i=0; i<line.length; i++){
			line[i] = line[i].replaceAll("^\"|\"$", "");
		}
		return line;
	}

	public static RoutePoint checkRouteData(String[] routePoint, int count){
		RoutePoint myRoutePoint = new RoutePoint("", -1); 	//Set as -1 to test foreign key constraints
		myRoutePoint.setRouteID(count);
		try {
			myRoutePoint.setAirline(routePoint[0]);
			myRoutePoint.setAirlineID(Integer.parseInt(routePoint[1]));
			myRoutePoint.setSrcAirport(routePoint[2]);    //cant be null?

			if (routePoint[3].equals("\\N") || routePoint[3].isEmpty()) {
				myRoutePoint.setSrcAirportID(-1);    //accounting for empty, to be consistent with other checks. Set as -1 to test foreign key constraints
			} else {
				myRoutePoint.setSrcAirportID(Integer.parseInt(routePoint[3]));
			}

			myRoutePoint.setDstAirport(routePoint[4]);
			if (routePoint[5].equals("\\N") || routePoint[5].isEmpty()) {	//Set as -1 to test foreign key constraints
				myRoutePoint.setDstAirportID(-1);
			} else {
				myRoutePoint.setDstAirportID(Integer.parseInt(routePoint[5]));
			}
			myRoutePoint.setCodeshare(routePoint[6]);
			myRoutePoint.setStops(Integer.parseInt(routePoint[7]));
			myRoutePoint.setEquipment(routePoint[8]);
		} catch (NumberFormatException e) {
			myRoutePoint.setAirline("Error on input file line: " + count);
			return myRoutePoint;
		}
		return myRoutePoint;
	}

	//ROUTES
	//add commas in for route equipment
	public static ArrayList<RoutePoint> parseRouteData(File file){

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

                if(numberOfCommas(line) == 8) {
                    String[] routePoint = line.split(",", -1);	//-1 seemed to be required for if the very last field is empty

					routePoint = removeQuotes(routePoint);
					RoutePoint myRoutePoint = checkRouteData(routePoint, count);
					myRouteData.add(myRoutePoint);
                } else {
					RoutePoint myRoutePoint = new RoutePoint("", -1);
					myRoutePoint.setRouteID(count);
					myRouteData.add(myRoutePoint);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return myRouteData;
	}

    //AIRLINES
	public static AirlinePoint checkAirlineData(String[] airlinePoint, int count) {
		AirlinePoint myAirlinePoint = new AirlinePoint(0, "");
		try {
			myAirlinePoint.setAirlineID(Integer.parseInt(airlinePoint[0]));
			myAirlinePoint.setAirlineName(airlinePoint[1]);	//let people name airline whatever they want

			if (airlinePoint[2].equals("\\N")) {
				myAirlinePoint.setAirlineAlias("");	//set as empty string if null
			} else {
				myAirlinePoint.setAirlineAlias(airlinePoint[2]);
			}

			if (airlinePoint[3].isEmpty() || airlinePoint[3].equals("\\N")) {
				myAirlinePoint.setIata("");
			} else {
				if (checkAlphaNumeric(airlinePoint[3])) {
					myAirlinePoint.setIata(airlinePoint[3]);
				} else {
					myAirlinePoint.setAirlineName("Error on input file line: " + count);
					return myAirlinePoint;
				}
			}

			if (airlinePoint[4].isEmpty() || airlinePoint[4].equals("\\N")) {
				myAirlinePoint.setIcao("");
			} else {
				if (checkAlphaNumeric(airlinePoint[4])) {
					myAirlinePoint.setIcao(airlinePoint[4]);
				} else {
					myAirlinePoint.setAirlineName("Error on input file line: " + count);
					return myAirlinePoint;
				}
			}

			myAirlinePoint.setCallsign(airlinePoint[5]);
			if (airlinePoint[6].isEmpty()) {
				myAirlinePoint.setCountry("");
			} else {
				myAirlinePoint.setCountry(airlinePoint[6]);    //should not be null, handle by parser later
			}
			myAirlinePoint.setActive(airlinePoint[7]);
		} catch (NumberFormatException e) {
			myAirlinePoint.setAirlineName("Error on input file line: " + count);
			return myAirlinePoint;
		}
		return myAirlinePoint;
	}

	public static ArrayList<AirlinePoint> parseAirlineData(File file){
		
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

                if(numberOfCommas(line) == 7) {
					String[] airlinePoint = line.split(",");
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
		
		return myAirlineData;	}

	private static boolean checkAlphaNumeric(String s) {
		return (s.matches("[a-zA-Z0-9]+"));	//does not accept empty spaces atm
	}

	//AIRPORTS
	public static AirportPoint checkAirportData(String[] airportPoint, int count) {
		AirportPoint myAirportPoint = new AirportPoint(0, "");
		try {
			myAirportPoint.setAirportID(Integer.parseInt(airportPoint[0]));
			myAirportPoint.setAirportName(airportPoint[1]);	//future: test just for alpha chars + space
			myAirportPoint.setAirportCity(airportPoint[2]); //test for just alpha chars
			myAirportPoint.setAirportCountry(airportPoint[3]); //test for just alpha chars

			if (airportPoint[4].isEmpty() || airportPoint[4].equals("\\N")) {
				myAirportPoint.setIata("");
			} else {
				if (checkAlphaNumeric(airportPoint[4])) {
					myAirportPoint.setIata(airportPoint[4]);
				} else {
					myAirportPoint.setAirportName("Error on input file line: " + count);
					return myAirportPoint;
				}
			}

			if (airportPoint[5].isEmpty() || airportPoint[5].equals("\\N")) {
				myAirportPoint.setIcao("");
			} else {
				if (checkAlphaNumeric(airportPoint[5])) {
					myAirportPoint.setIcao(airportPoint[5]);
				} else {
					myAirportPoint.setAirportName("Error on input file line: " + count);
					return myAirportPoint;
				}
			}
			myAirportPoint.setLatitude(Float.parseFloat(airportPoint[6]));
			myAirportPoint.setLongitude(Float.parseFloat(airportPoint[7]));
			myAirportPoint.setAltitude(Integer.parseInt(airportPoint[8]));
			myAirportPoint.setTimeZone(Float.parseFloat(airportPoint[9]));
			myAirportPoint.setDst(airportPoint[10]);
			if (airportPoint[11].equals("\\N")) {
				myAirportPoint.setTz("");		//default to unknown?
			} else {
				myAirportPoint.setTz(airportPoint[11]);
			}
		} catch (NumberFormatException e) {
			myAirportPoint.setAirportName("Error on input file line: " + count);
			return myAirportPoint;
		}
		return myAirportPoint;
	}

	public static ArrayList<AirportPoint> parseAirportData(File file){

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

			    if(numberOfCommas(line) == 11) {
                    String[] airportPoint = line.split(",");

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

	private static int numberOfCommas(String line){
        return line.length() - line.replace(",", "").length();
    }


	public static void linkRoutesAndAirports(ArrayList<AirportPoint> airports, ArrayList<RoutePoint> routes) {
		//function to link routes and airports, however it is very inefficient atm
		//called when flags from GUI (airportdataloaded = true, routedataloaded = true)
		for (RoutePoint route : routes) {
			//int operatingAirlineId = route.getAirlineID();	//should routes also link to airlines?
			int srcAirportId = route.getSrcAirportID();
			int destAirportId = route.getDstAirportID();

			for (AirportPoint airport : airports) {
				if (srcAirportId == airport.getAirportID()) {
					route.setSource(airport);
					airport.incrementRoutes();

				} else if (destAirportId == airport.getAirportID()) {
					route.setDestination(airport);
					airport.incrementRoutes();
				} else {
					//TODO
					//raise an exception here? a route is using an airport that doesn't exist
				}
			}
		}
	}
	

	
	//TEST FOR PARSER. OBV FILES ARE LOCAL TO ME. CHANGE THESE DIRECTORIES TO TRY FOR YOURSELF. ONLY FOUR
	//ATTRIBUTES HAVE BEEN TESTED FOR EACH FIELD ALTHOUGH IT IS IMPORTANT TO TRY OTHERS AS WELL.
	public static void main(String[] args) {

		JPanel mainPanel = new JPanel();
		
		//TESTING FLIGHT FILES A OK :)
		File f;
		String cwd = System.getProperty("user.dir");

		JFileChooser jfc = new JFileChooser(cwd);
		int userChoice = jfc.showOpenDialog(mainPanel);

		switch (userChoice) {
			case JFileChooser.APPROVE_OPTION:
				f = jfc.getSelectedFile();
				if (f.exists() && f.isFile() && f.canRead()) {

					//Custom button text
					Object[] options = {"Airport",
							"Airline", "Flight", "Route",
							"Cancel"};
					String s = (String) JOptionPane.showInputDialog(mainPanel,
							"Choose file type",
							"Open file",
							JOptionPane.PLAIN_MESSAGE,
							null,
							options,
							options[2]);
					if (s.equals("Airport")) {
						//TESTING AIRPORT FILES
						ArrayList<AirportPoint> myAirportData = parseAirportData(f);
//						for (int i = 0; i < 500; i++) {
//							System.out.println("Test Airport Data row[" + i + "]");
//							System.out.println("AirportID: " + myAirportData.get(i).getAirportID());
//							System.out.println("AirportName: " + myAirportData.get(i).getAirportName());
//							System.out.println("AirportCity: " + myAirportData.get(i).getAirportCity());
//							System.out.println("AirportCountry: " + myAirportData.get(i).getAirportCountry());
//							//System.out.println("AirportLatitude: ") + myAirportData.get(i).getLatitude());
//							System.out.println("\n");
//						}
						System.out.println("Airports successfully added: " + myAirportData.size());
					} else if (s.equals("Airline")) {
						//TESTING AIRLINE FILES A OK :)
						ArrayList<AirlinePoint> myAirlineData = parseAirlineData(f);
//						for (int i = 0; i < 500; i++) {
//							System.out.println("Test Airline Data row[" + i + "]");
//							System.out.println("Airline ID: " + myAirlineData.get(i).getAirlineID());
//							System.out.println("Airline Name: " + myAirlineData.get(i).getAirlineName());
//							System.out.println("Airline Alias: " + myAirlineData.get(i).getAirlineAlias());
//							System.out.println("IATA Code: " + myAirlineData.get(i).getIata());
//							System.out.println("\n");
//						}
						System.out.println("Airlines successfully added: " + myAirlineData.size());
					} else if (s.equals("Route")) {
						ArrayList<RoutePoint> myRouteData = parseRouteData(f);
						//equipment filter test
//						ArrayList<RoutePoint> myRoutes = Filter.routeEquipment(myRouteData, "747 CR2");
//						for (RoutePoint equip : myRoutes){
//							System.out.println(equip.getEquipment());
//						}
//						for (int i = 0; i < 500; i++) {
//							System.out.println("Test Route Data row[" + i + "]");
//							System.out.println("Airline: " + myRouteData.get(i).getAirline());
//							System.out.println("AirlineID: " + myRouteData.get(i).getAirlineID());
//							System.out.println("Src Airport: " + myRouteData.get(i).getSrcAirport());
//							System.out.println("Src Airport ID: " + myRouteData.get(i).getSrcAirportID());
//							System.out.println("Dst Airport: " + myRouteData.get(i).getDstAirport());
//							System.out.println("Dst Airport ID: " + myRouteData.get(i).getDstAirportID());
//							System.out.println("CodeShare: " + myRouteData.get(i).getCodeshare());
//							System.out.println("Stops: " + myRouteData.get(i).getStops());
//							System.out.print("Equipment:");
							/*
							for (String equip :myRouteData.get(i).getEquipment()){
								System.out.print(" " + equip);
							}
							*/
//							System.out.println("\n");
							//System.out.println("Equipment: " + myRouteData.get(i).getEquipment());
						//}
						System.out.println("Routes successfully added: " + myRouteData.size());
					} else if (s.equals("Flight")) {
						ArrayList<FlightPoint> myFlightData = parseFlightData(f);
						for (int i = 0; i < myFlightData.size(); i++) {
							System.out.println("Test Flight Data Row[" + i + "]");
							System.out.println("Type: " + myFlightData.get(i).getType());
							System.out.println("LocaleID: " + myFlightData.get(i).getLocaleID());
							System.out.println("Altitude: " + myFlightData.get(i).getAltitude());
							System.out.println("Latitude: " + myFlightData.get(i).getLatitude());
							System.out.println("Longitude: " + myFlightData.get(i).getLongitude());
							System.out.println("\n");
						}
					} else if (s.equals("Cancel")) {
						return;
					}
					return;
				}
			case JFileChooser.CANCEL_OPTION:
				// fall through
			case JFileChooser.ERROR_OPTION:
				return;
		}

	}

}

