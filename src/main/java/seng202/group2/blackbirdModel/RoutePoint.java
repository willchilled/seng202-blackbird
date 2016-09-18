package seng202.group2.blackbirdModel;

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
    private String destAirportName;
    private String destAirportCountry;

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

    public String getDestAirportName() {
        return destAirportName;
    }

    public void setDestAirportName(String destAirportName) {
        this.destAirportName = destAirportName;
    }

    public String getDestAirportCountry() {
        return destAirportCountry;
    }

    public void setDestAirportCountry(String destAirportCountry) {
        this.destAirportCountry = destAirportCountry;
    }
    //private int correctEntry;

    private AirportPoint source;
    private AirportPoint destination;


    public RoutePoint(String airline, int airlineID) {

        this.airline = airline;
        this.airlineID = airlineID;
    }



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

//	public int getCorrectEntry() {
//		return correctEntry;
//	}
//
//	public void setCorrectEntry(int correctEntry) {
//		this.correctEntry = correctEntry;
//	}

    @Override
    public String toString() {
        //WE HAVE ADDED IN A ROUTE ID...SHOULD I EXPORT THIS? OR WILL IT NOT MATTER BECAUSE IT WILL BE RE-LOADED THE SAME?
        //WHAT IF USER FILTERS DATA AND EXPORTS, THE IDS WILL BE DIFFERENT WHEN RE-LOADED?
        //NOT EXPORTING FOR NOW, BUT NEED TO DISCUSS


        //String, int, string, int, string, int, string, int, string

        return String.format("%s, %s, %s, %s,%s ,%s ,%s, %s, %s, %s, %s, %s, %s",
                airline, airlineID, srcAirport, srcAirportID, dstAirport, dstAirportID, codeshare, stops, equipment, srcAirportCountry, destAirportCountry, srcAirportName, destAirportName);

    }
}
