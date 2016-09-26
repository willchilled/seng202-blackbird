

package seng202.group2.blackbirdModel;

import org.apache.commons.lang3.StringUtils;
import seng202.group2.blackbirdControl.ErrorTabController;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A subclass of datapoint that stores information about an airline
 */
public class AirlinePoint extends DataPoint {

    private int airlineID;
    private String airlineName;
    private String airlineAlias;
    private String iata;
    private String icao;
    private String callsign;
    private String country;
    private String active;
    private int correctEntry=0;

    /**
     * Creates an airlinePoint with an ID and Name
     * @param airlineID The ID number for the airline
     * @param airlineName The name of the airline
     */
    public AirlinePoint(int airlineID, String airlineName) {

        this.airlineID = airlineID;
        this.airlineName = airlineName;

    }

    /**
     * Attempts to create an airlinePoint from a list of strings with length 8.
     * If successful it creates an airline with values from list and correctEntry as 0.
     * If unsuccessful it creates am airline with airlineID -1, airlineName as currentLine.toString() and correctEntry 1.
     * @param currentLine   The list of strings holding the information for the airline in index of:
     *                      0 airlineID,
     *                      1 airlineName,
     *                      2 airlineAlias,
     *                      3 iata,
     *                      4 icao,
     *                      5 callsign,
     *                      6 country,
     *                      7 active,
     */
    public AirlinePoint(String[] currentLine, int count, ErrorTabController errorTabController) {
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
                this.country = currentLine[6].trim();
                this.active = currentLine[7].trim().toUpperCase();
            } catch(NumberFormatException e) {
                BadData badAirline = new BadData(count, StringUtils.join(currentLine, ","), DataTypes.AIRLINEPOINT);
                if (errorTabController != null) {
                    errorTabController.updateBadEntries(badAirline, DataTypes.AIRLINEPOINT);
                }
                //AirlinePoint myAirlinePoint = new
                //this.airlineID = -1;
                //this.airlineName = currentLine.toString();
                this.correctEntry = 1;
            }
        } else {
            BadData badAirline = new BadData(count, StringUtils.join(currentLine, ","), DataTypes.AIRLINEPOINT);
            if (errorTabController != null) {
                errorTabController.updateBadEntries(badAirline, DataTypes.AIRLINEPOINT);
            }
            this.correctEntry = 1;
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

    /**
     * Returns the airline in the form of a string
     * @return airlineID, airlineName, airlineAlias, iata, icao, callsign, country, active
     */
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

