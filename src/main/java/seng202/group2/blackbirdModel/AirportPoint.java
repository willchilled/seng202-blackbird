

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
    private float altitude;
    private float timeZone;
    private String dst;
    private String tz;
    private int incomingRoutes=0;
    private int outgoingRoutes=0;

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
     * Attempts to create an AirportPoint with a list of strings of length 12.
     * If successful it creates an AirportPoint with values from list and correctEntry as 1.
     * If unsuccessful it creates am AirportPoint with airportID -1, airportName as currentLine.toString() and correctEntry 0.
     * @param currentLine The list of strings holding the information for the airport in index of:
     *                    0 airportID,
     *                    1 airportName,
     *                    2 airportCity,
     *                    3 airportCountry,
     *                    4 iata,
     *                    5 icao,
     *                    6 latitude,
     *                    7 longitude,
     *                    8 altitude,
     *                    9 timeZone,
     *                    10 dst,
     *                    11 tz,
     */
    public AirportPoint(String[] currentLine) {
        super();
        //System.out.println(currentLine.length);

        if (currentLine.length == 12 || currentLine.length == 14){
            //AirlinePoint myAirlinePoint = new AirlinePoint(-1, "");
            try {
                // System.out.println("Int AID: " + currentLine[0]);
                this.airportID = Integer.parseInt(currentLine[0]);	//should not be null
                //System.out.println("STRING ANAME: " + currentLine[1]);
                this.airportName = currentLine[1];	//let people name airline whatever they want
                //System.out.println("STRING ACITY: " + currentLine[2]);
                this.airportCity = currentLine[2];
                //System.out.println("STRING ACOUNTRY: " + currentLine[3]);
                this.airportCountry= currentLine[3];
                //System.out.println("STRING IATA: " + currentLine[4]);
                this.iata =currentLine[4];
                //System.out.println("STRING ICAO: " + currentLine[5]);
                this.icao = currentLine[5];
                //System.out.println("FLOAT LAT: " + currentLine[6]);
                this.latitude = Float.parseFloat(currentLine[6].trim());    //should not be null, handle by parser later
                //System.out.println("FLOAT LONG: " + currentLine[7]);
                this.longitude = Float.parseFloat(currentLine[7].trim());
                //System.out.println("INT ALT: " + currentLine[8]);
                this.altitude = Float.parseFloat(currentLine[8]);
                //System.out.println("FLOAT TIMEZONE: " + currentLine[9]);
                this.timeZone = Float.parseFloat(currentLine[9]);
                //System.out.println("STRING DST: " + currentLine[10]);
                this.dst = currentLine[10];
                //System.out.println("STRING TZ: " + currentLine[11]);
                this.tz = currentLine[11];
                this.correctEntry = 1;

                if (currentLine.length == 14){ // We add a special case for when there is data in the array
                    this.outgoingRoutes = Integer.parseInt(currentLine[12]);
                    this.incomingRoutes = Integer.parseInt(currentLine[13]);//check this is the right way round
                }

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

    public float getAltitude() {
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
        return String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                airportID, airportName, airportCity, airportCountry, iata, icao, latitude, longitude, altitude, timeZone, dst, tz, incomingRoutes, outgoingRoutes);

    }


    public int getIncomingRoutes() {
        return incomingRoutes;
    }

    public void setIncomingRoutes(int incomingRoutes) {
        this.incomingRoutes = incomingRoutes;
    }

    public int getOutgoingRoutes() {
        return outgoingRoutes;
    }

    public void setOutgoingRoutes(int outgoingRoutes) {
        this.outgoingRoutes = outgoingRoutes;
    }
}

