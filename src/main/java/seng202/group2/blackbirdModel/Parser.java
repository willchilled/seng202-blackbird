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
	//shouldn't be null.
	
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
                    
                    if(!checkNull(flightPoint)) {
                    	String type = flightPoint[0];
                    	String localeID = flightPoint[1];
                    	int altitude = Integer.parseInt(flightPoint[2]);
                    	float latitude = Float.parseFloat(flightPoint[3]);
                    	float longitude = Float.parseFloat(flightPoint[4]);
                    	FlightPoint myFlightPoint = new FlightPoint(type, localeID, altitude, latitude,
                                longitude);
                        myFlightSet.add(myFlightPoint);
                    } else {
                    	System.err.println("Error: Null field in flight data. All fields must be filled");
						//currently, aborting if any null fields present in flight data.
                    	break;
                    }
                } else {
					//will any flight data have extra commas?
					System.err.println("Error: Unexpected comma found on line: " + count);
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

	private static RoutePoint checkRouteData(String[] routePoint, RoutePoint myRoutePoint){

		myRoutePoint.setSrcAirport(routePoint[2]);	//cant be null?
		if (routePoint[3].equals("\\N")) {
			myRoutePoint.setSrcAirportID(0);	//0 as placeholder null value
		} else {
			myRoutePoint.setSrcAirportID(Integer.parseInt(routePoint[3]));
		}
		myRoutePoint.setDstAirport(routePoint[4]);	//cant be null?
		if (routePoint[5].equals("\\N")) {
			myRoutePoint.setDstAirportID(0);
		} else {
			myRoutePoint.setDstAirportID(Integer.parseInt(routePoint[5]));
		}
		myRoutePoint.setCodeshare(routePoint[6]);
		myRoutePoint.setStops(Integer.parseInt(routePoint[7]));
		myRoutePoint.setEquipment(routePoint[8]);

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
                if(numberOfCommas(line) == 8) {
                    String[] routePoint = line.split(",", -1);

					//routePoint = removeQuotes(routePoint);

					String airline = routePoint[0];
					int airlineID = 0;	// 0 if airlineID is null

					if (!routePoint[1].equals("\\N")) {
						airlineID = Integer.parseInt(routePoint[1]);
					}

					RoutePoint myRoutePoint = new RoutePoint(airline, airlineID);

					myRoutePoint = checkRouteData(routePoint, myRoutePoint);
					myRoutePoint.setRouteID(count);	//set our own routeID

					myRouteData.add(myRoutePoint);
                } else {
                    //currently picking up empty lines in text file
					System.err.println("Error: Unexpected comma found on line: " + count);
					System.err.println("The line: " + line);
					//TODO
                    // HANDLE INCORRECT FIELD DATA
                }
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return myRouteData;
	}
	

	private static AirlinePoint checkAirlineData(String[] airlinePoint, AirlinePoint myAirlinePoint) {
		if (airlinePoint[2].equals("\\N")) {
			myAirlinePoint.setAirlineAlias("");	//set as empty string if null?
		} else {
			myAirlinePoint.setAirlineAlias(airlinePoint[2]);
		}
		myAirlinePoint.setIata(airlinePoint[3]);	//rest of fields do not have \N values? only empty strings?
		myAirlinePoint.setIcao(airlinePoint[4]);
		myAirlinePoint.setCallsign(airlinePoint[5]);
		myAirlinePoint.setCountry(airlinePoint[6]);	//should not be null
		myAirlinePoint.setActive(airlinePoint[7]);

		return myAirlinePoint;
	}

	//AIRLINES
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
                if(numberOfCommas(line) == 7) {
                    String[] airlinePoint = line.split(",");
                    
                    //if(!checkNull(airlinePoint)) {

						airlinePoint = removeQuotes(airlinePoint);

	                    int airlineID = Integer.parseInt(airlinePoint[0]);	//shouldnt be null
	                    String airlineName = airlinePoint[1];	//shouldnt be null
	                    AirlinePoint myAirlinePoint = new AirlinePoint(airlineID, airlineName);
						myAirlinePoint = checkAirlineData(airlinePoint, myAirlinePoint);


	                    myAirlineData.add(myAirlinePoint);
                    //} else {
                    	//deal with null data here
                    	//continue;
                    //}
                } else {
                    //TODO
                    //DEAL WITH BAD DATA! not correct amount of fields
					System.err.println("Error: Unexpected comma found on line: " + count);
					System.err.println("The line: " + line);
                }
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return myAirlineData;	}
	
	
	private static AirportPoint checkAirportData(String[] airportPoint, AirportPoint myAirportPoint) {

		myAirportPoint.setAirportCity(airportPoint[2]);
		myAirportPoint.setAirportCountry(airportPoint[3]);
		myAirportPoint.setIata(airportPoint[4]);
		if (airportPoint[5].equals("\\N")) {
			myAirportPoint.setIcao("");	//set as empty string
		} else {
			myAirportPoint.setIcao(airportPoint[5]);
		}

		myAirportPoint.setLatitude(Float.parseFloat(airportPoint[6]));
		myAirportPoint.setLongitude(Float.parseFloat(airportPoint[7]));
		myAirportPoint.setAltitude(Integer.parseInt(airportPoint[8]));
		myAirportPoint.setTimeZone(Float.parseFloat(airportPoint[9]));
		myAirportPoint.setDst(airportPoint[10]);
		if (airportPoint[11].equals("\\N")) {
			myAirportPoint.setTz("");
		} else {
			myAirportPoint.setTz(airportPoint[11]);
		}

		return myAirportPoint;
	}

	//AIRPORTS
	public static ArrayList<AirportPoint> parseAirportData(File file){

		ArrayList<AirportPoint> myAirportData = new ArrayList<AirportPoint>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			int count = 0;
			while ((line = br.readLine()) != null) {
				count++;
			    //Checking if there are the right amount of fields before trying to parse
			    if(numberOfCommas(line) == 11) {
                    String[] airportPoint = line.split(",");

					airportPoint = removeQuotes(airportPoint);

					int airportID = Integer.parseInt(airportPoint[0]);	//shouldnt be null
					String airportName = airportPoint[1];	//shouldnt be null
					AirportPoint myAirportPoint = new AirportPoint(airportID, airportName);
					myAirportPoint = checkAirportData(airportPoint, myAirportPoint);

					myAirportData.add(myAirportPoint);


                } else {
					System.err.println("Error: Unexpected comma found on line: " + count);
					System.err.println("The line: " + line);
                    //TODO
                    //There weren't the right number of fields, handle this data somehow!
                }
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return myAirportData;
	}

	public static int numberOfCommas(String line){
        return line.length() - line.replace(",", "").length();
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

