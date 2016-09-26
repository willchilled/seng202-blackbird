

package seng202.group2.blackbirdModel;

/**
 * A subclass of dataPoint that stores information about a flight point
 */
public class FlightPoint extends DataPoint {



    private int seqno;
    private String localtype;
    private String localeID;
    private int altitude;
    private float latitude;
    private float longitude;
    private int correctEntry=0;
    private int flightIDNum;

    /**
     * Creates a FlightPoint with values given
     * @param type the locale type
     * @param localeID the locale ID
     * @param altitude the altitude
     * @param latitude the latitude
     * @param longitude the longitude
     */
    public FlightPoint(String type, String localeID, int altitude, float latitude, float longitude) {

        this.localtype = type;
        this.localeID = localeID;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Attempts to create a FlightPoint from a  list of strings of length 5.
     * If successful it creates an FlightPoint with values from list and correctEntry as 0.
     * If unsuccessful it creates am FlightPoint with localtype "I AM INCORRECT".
     * @param currentLine The list of strings holding the information for the FlightPoint in index of:
     *                    0 localtype,
     *                    1 localeID,
     *                    2 altitude,
     *                    3 latitude,
     *                    4 longitude,
     */
    public FlightPoint(String[] currentLine) {
        super();

        //System.out.println(currentLine.toString());

        try {
            if (currentLine.length == 5) {
                this.localtype = currentLine[0];
                this.localeID = currentLine[1];
                this.altitude = Integer.parseInt(currentLine[2]);
                this.latitude = Float.parseFloat(currentLine[3]);
                this.longitude = Float.parseFloat(currentLine[4]);
            }
            //FOR GRABBING OLD FLIGHTPOINTS FROM DATABASE FOR RECREATION
            if (currentLine.length == 7) {
                //this.flightIDNum = Integer.parseInt(currentLine[0]);
                this.localtype = currentLine[2];
                this.localeID = currentLine[1];
                this.altitude = Integer.parseInt(currentLine[3]);
                this.latitude = Float.parseFloat(currentLine[4]);
                this.longitude = Float.parseFloat(currentLine[5]);
                this.flightIDNum = Integer.parseInt(currentLine[6]);
            }
        }
        catch(NumberFormatException e){
            this.localtype = "I AM INCORRECT";
            correctEntry = 1;   //set as 1 here?
        }

    }

    public int getFlightIDNum() {
        return flightIDNum;
    }

    public String getLocalType() {
        return localtype;
    }

    public void setLocalType(String type) {
        this.localtype = type;
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

    /**
     * Returns the FlightPoint in the form of a string
     * @return localtype, localeID, altitude, latitude, longitude
     */
    @Override
    public String toString() {


        //String, int, int, int, int

        return String.format("%s, %s, %s, %s,%s",
                localtype, localeID, altitude, latitude, longitude);

    }


    public int getCorrectEntry() {
        return correctEntry;
    }

    public void setCorrectEntry(int correctEntry) {
        this.correctEntry = correctEntry;
    }
}

