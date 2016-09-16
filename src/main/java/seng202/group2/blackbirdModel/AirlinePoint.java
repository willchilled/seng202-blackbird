package seng202.group2.blackbirdModel;

public class AirlinePoint extends DataPoint {

    private int airlineID;
    private String airlineName;
    private String airlineAlias;
    private String iata;
    private String icao;
    private String callsign;
    private String country;
    private String active;
    private int correctEntry=1;


    public AirlinePoint(int airlineID, String airlineName) {

        this.airlineID = airlineID;
        this.airlineName = airlineName;

    }

    public AirlinePoint(String[] currentLine) {
        super();
        if (currentLine.length == 8){
            //AirlinePoint myAirlinePoint = new AirlinePoint(-1, "");
            try {
                this.airlineID = Integer.parseInt(currentLine[0]);	//should not be null
                this.airlineName = currentLine[1];	//let people name airline whatever they want
                this.airlineAlias = currentLine[2];
                this.iata= currentLine[3];
                this.icao =currentLine[4];
                this.callsign = currentLine[5];
                this.country = currentLine[6].trim();    //should not be null, handle by parser later
                this.active = currentLine[7].trim();
                this.correctEntry = 1;
            }
            catch(NumberFormatException e) {
                //AirlinePoint myAirlinePoint = new
                this.airlineID = -1;
                this.airlineName = currentLine.toString();
                this.correctEntry = 0;
            }

        }
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


    @Override
    public String toString() {

        return String.format("%s, %s, %s, %s, %s, %s, %s, %s",
                airlineID, airlineName, airlineAlias, iata, icao, callsign, country, active);

    }


    public int getCorrectEntry() {
        return correctEntry;
    }

    public void setCorrectEntry(int correctEntry) {
        this.correctEntry = correctEntry;
    }
}
