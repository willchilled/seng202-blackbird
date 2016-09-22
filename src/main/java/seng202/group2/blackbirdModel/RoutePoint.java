

package seng202.group2.blackbirdModel;

/**
 * A subclass of dataPoint that stores information about a route
 */

public class RoutePoint extends DataPoint {

    private int routeID;    //our given id to routes
    private String airline; //index 0 in input file
    private int airlineID;
    private String srcAirport;
    private int srcAirportID;
    private String dstAirport;
    private int dstAirportID;
    private String codeshare;
    private int stops;
    private String equipment;
    private String srcAirportName;
    private String srcAirportCountry;
    private String dstAirportName;
    private String dstAirportCountry;

    //private int correctEntry;

    private AirportPoint source;
    private AirportPoint destination;
    private int correctEntry=0;

    /**
     * Creates a RoutePoint with an airline and airlineID
     * @param airline The airline IATA code for the route
     * @param airlineID The airlineID for the route
     */
    public RoutePoint(String airline, int airlineID) {

        this.airline = airline;
        this.airlineID = airlineID;
    }

    /**
     * Attempts to create an RoutePoint with a list of strings of length 9.
     * If successful it creates an RoutePoint with values from list and correctEntry as 0.
     * If unsuccessful it creates am RoutePoint with routeID -1, airline as currentLine.toString() and correctEntry 1.
     * @param currentLine The list of strings holding the information for the RoutePoint in index of:
     *                    0 airline,
     *                    1 airlineID,
     *                    2 scrAirport,
     *                    3 scrAirportID,
     *                    4 dstAirport,
     *                    5 dstAirportID,
     *                    6 codeshare,
     *                    7 stops,
     *                    8 equipment,
     */
    public RoutePoint(String[] currentLine) {
        super();

        if (currentLine.length == 9 || currentLine.length == 13){

            //AirlinePoint myAirlinePoint = new AirlinePoint(-1, "");
            try {

                //this.routeID = Integer.parseInt(currentLine[0]);	//should not be null
                this.airline = currentLine[0];	//let people name airline whatever they want
                //System.out.println("I EXPECT AN INT: " +currentLine[0]);
                this.airlineID = Integer.parseInt(currentLine[1]);
                // System.out.println("");
                this.srcAirport= currentLine[2];
                this.srcAirportID = Integer.parseInt(currentLine[3]);
                this.dstAirport = currentLine[4];
                this.dstAirportID = Integer.parseInt(currentLine[5]);

                this.codeshare = currentLine[6];    //should not be null, handle by parser later
                this.stops = Integer.parseInt(currentLine[7]);
                this.equipment = currentLine[8];
                //

                if (currentLine.length == 13){
                    this.srcAirportName = currentLine[9];
                    this.srcAirportCountry = currentLine[10];
                    this.dstAirportName = currentLine[11];
                    this.dstAirportCountry = currentLine[12];
                }

                //System.out.println("here");

            }
            catch(NumberFormatException e) {
                //AirlinePoint myAirlinePoint = new
                this.routeID = -1;
                //this.airline = currentLine.toString();
                this.correctEntry = 1;
            }

        }

    }

    /**
     * A function to determine if a RoutePoint has the same routeID
     * @param obj A RoutePoint to check equality of type against
     * @return  A boolean variable, true if the RoutePoint has the same routeID
     */
    @Override
    public boolean equals(Object obj) {
        RoutePoint mypoint =  (RoutePoint) obj;
        return routeID == mypoint.getRouteID();
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public int getAirlineID() {
        return airlineID;
    }

    public void setAirlineID(int airlineID) {
        this.airlineID = airlineID;
    }

    public String getSrcAirport() {
        return srcAirport;
    }

    public void setSrcAirport(String srcAirport) {
        this.srcAirport = srcAirport;
    }

    public int getSrcAirportID() {
        return srcAirportID;
    }

    public void setSrcAirportID(int srcAirportID) {
        this.srcAirportID = srcAirportID;
    }

    public String getDstAirport() {
        return dstAirport;
    }

    public void setDstAirport(String dstAirport) {
        this.dstAirport = dstAirport;
    }

    public int getDstAirportID() {
        return dstAirportID;
    }

    public void setDstAirportID(int dstAirportID) {
        this.dstAirportID = dstAirportID;
    }

    public String getCodeshare() {
        return codeshare;
    }

    public void setCodeshare(String codeshare) {
        this.codeshare = codeshare;
    }

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public AirportPoint getSource() {
        return source;
    }

    public void setSource(AirportPoint source) {
        this.source = source;
    }

    public AirportPoint getDestination() {
        return destination;
    }

    public void setDestination(AirportPoint destination) {
        this.destination = destination;
    }

    /**
     * Returns the RoutePoint in the form of a string
     * @return airline, airlineID, srcAirport, srcAirportID, dstAirport, dstAirportID, codeshare, stops, equipment
     */
    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s,%s ,%s ,%s, %s, %s, %s, %s, %s, %s",
                airline, airlineID, srcAirport, srcAirportID, dstAirport, dstAirportID, codeshare, stops, equipment, srcAirportName, srcAirportCountry, dstAirportName, dstAirportCountry);

    }

    public int getCorrectEntry() {
        return correctEntry;
    }

    public void setCorrectEntry(int correctEntry) {
        this.correctEntry = correctEntry;
    }

    public String getSrcAirportName() {
        return srcAirportName;
    }

    public void setSrcAirportName(String srcAirportName) {
        this.srcAirportName = srcAirportName;
    }

    public String getSrcAirportCountry() {
        return srcAirportCountry;
    }

    public void setSrcAirportCountry(String srcAirportCountry) {
        this.srcAirportCountry = srcAirportCountry;
    }

    public String getDstAirportName() {
        return dstAirportName;
    }

    public void setDstAirportName(String dstAirportName) {
        this.dstAirportName = dstAirportName;
    }

    public String getDstAirportCountry() {
        return dstAirportCountry;
    }

    public void setDstAirportCountry(String dstAirportCountry) {
        this.dstAirportCountry = dstAirportCountry;
    }
}

