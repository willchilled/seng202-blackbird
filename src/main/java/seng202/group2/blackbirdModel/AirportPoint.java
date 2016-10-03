package seng202.group2.blackbirdModel;

import org.apache.commons.lang3.StringUtils;
import seng202.group2.blackbirdControl.ErrorTabController;

/**
 * A subclass of Datapoint that stores information about an airport
 *
 * @author Team2
 * @version 2.0
 * @since 19/9/2016
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
    private int incomingRoutes = 0;
    private int outgoingRoutes = 0;
    private int correctEntry = 0;

    /**
     * Creates an AirportPoint with an ID and Name
     *
     * @param airportID   The ID number for the airport
     * @param airportName The Name of the airport
     */
    public AirportPoint(int airportID, String airportName) {
        this.airportID = airportID;
        this.airportName = airportName;
    }

    /**
     * Attempts to create an AirportPoint with a list of strings of length 12.
     * If successful it creates an AirportPoint with values from list and correctEntry as 0.
     * If unsuccessful it creates am AirportPoint with airportID -1, airportName as currentLine.toString() and correctEntry 1.
     *
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
    public AirportPoint(String[] currentLine, int count, ErrorTabController errorTabController) {
        super();
        if (currentLine.length == 12 || currentLine.length == 14) {
            try {
                this.airportID = Integer.parseInt(currentLine[0]);
                this.airportName = currentLine[1];
                this.airportCity = currentLine[2];
                this.airportCountry = currentLine[3];
                this.iata = currentLine[4];
                this.icao = currentLine[5];
                if (currentLine[6].isEmpty()) {
                    this.latitude = 0;
                } else {
                    this.latitude = Float.parseFloat(currentLine[6].trim());
                }
                if (currentLine[7].isEmpty()) {
                    this.longitude = 0;
                } else {
                    this.longitude = Float.parseFloat(currentLine[7].trim());
                }
                if (currentLine[8].isEmpty()) {
                    this.altitude = 0;
                } else {
                    this.altitude = Float.parseFloat(currentLine[8]);
                }
                if (currentLine[9].isEmpty()) {
                    timeZone = 0;
                } else {
                    this.timeZone = Float.parseFloat(currentLine[9]);
                }
                this.dst = currentLine[10];
                this.tz = currentLine[11];

                if (currentLine.length == 14) { // We add a special case for when there is data in the array
                    this.outgoingRoutes = Integer.parseInt(currentLine[12]);
                    this.incomingRoutes = Integer.parseInt(currentLine[13]);
                }
            } catch (NumberFormatException e) {
                BadData badAirport = new BadData(count, StringUtils.join(currentLine, ", "), DataTypes.AIRPORTPOINT,
                        "Invalid numeric values given (within Airport ID, altitude, longitude or latitude fields)");
                if (errorTabController != null) {
                    errorTabController.updateBadEntries(badAirport, DataTypes.AIRPORTPOINT);
                    errorTabController.setAllCorrect(false);
                }
                this.correctEntry = 1;
            }
        } else {
            BadData badAirport = new BadData(count, StringUtils.join(currentLine, ", "), DataTypes.AIRPORTPOINT,
                    "Invalid file line length, expecting 12 comma separated values");
            if (errorTabController != null) {
                errorTabController.updateBadEntries(badAirport, DataTypes.AIRPORTPOINT);
                errorTabController.setAllCorrect(false);
            }
            this.correctEntry = 1;
        }
    }

    /**
     * Returns the AirportPoint in the form of a string
     *
     * @return airportID, airportName, airportCity, airportCountry, iata, icao, latitude, longitude, altitude, timeZone, dst, tz
     */
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                airportID, airportName, airportCity, airportCountry, iata, icao, latitude, longitude, altitude, timeZone, dst, tz);

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

    int getCorrectEntry() {
        return correctEntry;
    }

    public void setCorrectEntry(int correctEntry) {
        this.correctEntry = correctEntry;
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

