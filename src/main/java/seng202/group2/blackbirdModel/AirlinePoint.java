package seng202.group2.blackbirdModel;

/** An Airline class used to store Airline information */
public class AirlinePoint extends DataPoint {

    private int airlineID;
    private String airlineName;
    private String airlineAlias;
    private String iata;
    private String icao;
    private String callsign;
    private String country;
    private String active;
    //private int correctEntry;


    /**
     * Creates an airlinePoint with an ID and Name
     * @param airlineID The ID number for the airline
     * @param airlineName The name of the airline
     */
    public AirlinePoint(int airlineID, String airlineName) {

        this.airlineID = airlineID;
        this.airlineName = airlineName;

    }

    public int getAirlineID() {
        return airlineID;
    }

    public void setAirlineID(int airlineID) {
        this.airlineID = airlineID;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getAirlineAlias() {
        return airlineAlias;
    }

    public void setAirlineAlias(String airlineAlias) {
        this.airlineAlias = airlineAlias;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

//	public int getCorrectEntry() {
//		return correctEntry;
//	}
//
//	public void setCorrectEntry(int correctEntry) {
//		this.correctEntry = correctEntry;
//	}

    /**
     * Creates a readable string in the format airlineID, airlineName, airlineAlias, IATA, ICAO, Callsign, Country, Active
     * @return the airline as a string
     */
    @Override
    public String toString() {

        return String.format("%s, %s, %s, %s, %s, %s, %s, %s",
                airlineID, airlineName, airlineAlias, iata, icao, callsign, country, active);

    }


}
