package seng202.group2.blackbirdModel;

public class DataPoint {
     /* This is a more generic type of Data called a dataPoint
        Its type is defined by a string called type
        To make a data just go Datapoint myPoint = new FlightPoint
        However You would then have to set the Type using myPoint.setType*/

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    /**
     * Creates Data Point with type null
     */
    public DataPoint() {
        this.type = null;
    }

    /**
     * @param type creates a dataPoint with a type
     */
    public DataPoint(String type) {
        this.type = type;
    }

    /**
     * @param dataArray The array of data containing Strings
     * @param type      The input type the Data point should be set to
     * @return A DataPoint with a specificType
     */
    public static DataPoint createDataPointFromStringArray(String[] dataArray, String type) {
        /*
        This creates a dataPoint from a string array and sets the type
         */

        DataPoint currentPoint = new DataPoint(type);
        if ("AirlinePoint".equals(type)) {
            currentPoint = new AirlinePoint(dataArray);

        } else if ("AirportPoint".equals(type)) {
            currentPoint = new AirportPoint(dataArray);

        } else if ("RoutePoint".equals(type)) {
            currentPoint = new RoutePoint(dataArray);

        } else if ("FlightPoint".equals(type)) {
            currentPoint = new FlightPoint(dataArray);
        }
        currentPoint.type = type;
        return currentPoint;
    }

    @Override
    public boolean equals(Object obj) {
        //megan's test for bad data...
        DataPoint mypoint = (DataPoint) obj;
        if (this.getType() == mypoint.getType()) return true;
        else return false;
    }







}
