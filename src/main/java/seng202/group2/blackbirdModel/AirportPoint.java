package seng202.group2.blackbirdModel;


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


    public AirportPoint(int airportID, String airportName) {

        this.airportID = airportID;
        this.airportName = airportName;

    }

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


    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                airportID, airportName, airportCity, airportCountry, iata, icao, latitude, longitude, altitude, timeZone, dst, tz);

    }



}
