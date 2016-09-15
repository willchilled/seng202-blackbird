package seng202.group2.blackbirdModel;

public class FlightPoint extends DataPoint {
	
	private String type;
	private String localeID;
	private int altitude;
	private float latitude;
	private float longitude;
	//private int correctEntry;
	
	public FlightPoint(String type, String localeID, int altitude, float latitude, float longitude){
		
		this.type = type;
		this.localeID = localeID;
		this.altitude = altitude;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocaleID() {
		return localeID;
	}

	public void setLocaleID(String localeID) {
		this.localeID = localeID;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
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

//	public int getCorrectEntry() {
//		return correctEntry;
//	}
//
//	public void setCorrectEntry(int correctEntry) {
//		this.correctEntry = correctEntry;
//	}

	@Override
	public String toString() {

		String returnString = String.format("\"%s\",%s,%s,%s,%s,",
				type, localeID, altitude, latitude, longitude);
		//String, int, int, int, int

		return returnString;

	}


}
