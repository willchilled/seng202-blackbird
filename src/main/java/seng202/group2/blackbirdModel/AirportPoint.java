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

    //private int correctEntry;

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

//	public int getCorrectEntry() {
//		return correctEntry;
//	}
//
//	public void setCorrectEntry(int correctEntry) {
//		this.correctEntry = correctEntry;
//	}

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                airportID, airportName, airportCity, airportCountry, iata, icao, latitude, longitude, altitude, timeZone, dst, tz);

    }


}
