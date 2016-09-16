package seng202.group2.blackbirdModel;

public class FlightPoint extends DataPoint {

    private String locaLtype;
    private String localeID;
    private int altitude;
    private float latitude;
    private float longitude;
    private int correctEntry=1;

    public FlightPoint(String type, String localeID, int altitude, float latitude, float longitude) {

        this.locaLtype = type;
        this.localeID = localeID;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public FlightPoint(String[] currentLine) {
        super();

        //System.out.println(currentLine.toString());

        try {
            if (currentLine.length == 5) {
                this.locaLtype = currentLine[0];
                this.localeID = currentLine[1];
                this.altitude = Integer.parseInt(currentLine[2]);
                this.latitude = Float.parseFloat(currentLine[3]);
                this.longitude = Float.parseFloat(currentLine[4]);


            }
        }
        catch(NumberFormatException e){
            this.locaLtype = "I AM INCORRECT";
        }

    }

    public String getLocalType() {
        return locaLtype;
    }

    public void setLocalType(String type) {
        this.locaLtype = type;
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


        //String, int, int, int, int

        return String.format("%s, %s, %s, %s,%s",
                locaLtype, localeID, altitude, latitude, longitude);

    }


    public int getCorrectEntry() {
        return correctEntry;
    }

    public void setCorrectEntry(int correctEntry) {
        this.correctEntry = correctEntry;
    }
}
