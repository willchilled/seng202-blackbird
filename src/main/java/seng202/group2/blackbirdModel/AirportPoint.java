package seng202.group2.blackbirdModel;

/**
 *  A subclass of Datapoint that stores information about an airport
 */
public class AirportPoint extends DataPoint {

    private int airportID;
    private String airportName;
    private String airportCity;
    private String airportCountry;
    private String iata;
    private String icao;
    private float latitude;
    private float longitude;
    private int altitude;
    private float timeZone;
    private String dst;
    private String tz;

    private int correctEntry=1;

    private int numberOfRoutes;

//	@Override
//	public boolean equals(Object obj){
//		//megan's test for bad data...
//		AirportPoint mypoint = (AirportPoint) obj;
//		if(this.getAirportID() == mypoint.getAirportID()) return true;
//		else return false;
//	}

    /**
     * Creates an AirportPoint with an ID and Name
     * @param airportID The ID number for the airport
     * @param airportName The Name of the airport
     */
    public AirportPoint(int airportID, String airportName) {

        this.airportID = airportID;
        this.airportName = airportName;

    }

    /**
     * Attempts to create an AirportPoint with a string list of length 12.
     * If successful it creates an AirportPoint with values from list and correctEntry as 1.
     * If unsuccessful it creates am AirportPoint with airportID -1, airportName as currentLine.toString() & correctEntry 0.
     * @param currentLine The string list holding the information for the airport in index of:
     *                    0 airportID
     *                    1 airportName
     *                    2 airportCity
     *                    3 airportCountry
     *                    4 iata
     *                    5 icao
     *                    6 latitude
     *                    7 longitude
     *                    8 altitude
     *                    9 timeZone
     *                    10 dst
     *                    11 tz
     */
    public AirportPoint(String[] currentLine) {
        super();

        if (currentLine.length == 12){
            //AirlinePoint myAirlinePoint = new AirlinePoint(-1, "");
            try {
                this.airportID = Integer.parseInt(currentLine[0]);	//should not be null
                this.airportName = currentLine[1];	//let people name airline whatever they want
                this.airportCity = currentLine[2];
                this.airportCountry= currentLine[3];
                this.iata =currentLine[4];
                this.icao = currentLine[5];
                this.latitude = Float.parseFloat(currentLine[6].trim());    //should not be null, handle by parser later
                this.longitude = Float.parseFloat(currentLine[7].trim());
                this.altitude = Integer.parseInt(currentLine[8]);
                this.timeZone = Float.parseFloat(currentLine[9]);
                this.dst = currentLine[10];
                this.tz = currentLine[11];
                this.correctEntry = 1;

            }
            catch(NumberFormatException e) {
                //AirlinePoint myAirlinePoint = new
                this.airportID = -1;
                this.airportName = currentLine.toString();
                this.correctEntry = 0;
            }

        }

    }


    /**
     * Increments numberOfRoutes by 1
     */
    public void incrementRoutes() {
        numberOfRoutes++;
    }


    public int getAirportID() {
        return airportID;
    }

    public void setAirportID(int airportID) {
        this.airportID = airportID;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getAirportCity() {
        return airportCity;
    }

    public void setAirportCity(String airportCity) {
        this.airportCity = airportCity;
    }

    public String getAirportCountry() {
        return airportCountry;
    }

    public void setAirportCountry(String airportCountry) {
        this.airportCountry = airportCountry;
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

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public float getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(float timeZone) {
        this.timeZone = timeZone;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }


    public int getCorrectEntry() {
        return correctEntry;
    }

    public void setCorrectEntry(int correctEntry) {
        this.correctEntry = correctEntry;
    }

    /**
     * Returns the AirportPoint in the form of a string
     * @return airportID, airportName, airportCity, airportCountry, iata, icao, latitude, longitude, altitude, timeZone, dst, tz
     */
    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                airportID, airportName, airportCity, airportCountry, iata, icao, latitude, longitude, altitude, timeZone, dst, tz);

    }



}
