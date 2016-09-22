

package seng202.group2.blackbirdModel;

/**
 * A super class for the data points held in the program
 */
public class DataPoint {
     /* This is a more generic type of Data called a dataPoint
        Its type is defined by a string called type
        To make a data just go Datapoint myPoint = new FlightPoint
        However You would then have to set the Type using myPoint.setType*/

    private DataTypes type;

    public DataTypes getType() {
        return type;
    }

    public void setType(DataTypes type) {
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
    public DataPoint(DataTypes type) {
        this.type = type;
    }

    /**
     * @param dataArray The array of data containing Strings
     * @param type      The input type the Data point should be set to
     * @return A DataPoint with a specificType
     */
    public static DataPoint createDataPointFromStringArray(String[] dataArray, DataTypes type) {

        /*
        This creates a dataPoint from a string array and sets the type
         */

        DataPoint currentPoint = new DataPoint(type);
        if (type == DataTypes.AIRLINEPOINT) {
            currentPoint = new AirlinePoint(dataArray);

        } else if (type == DataTypes.AIRPORTPOINT) {
            currentPoint = new AirportPoint(dataArray);

        } else if (type == DataTypes.ROUTEPOINT) {
            currentPoint = new RoutePoint(dataArray);

        } else if (type == DataTypes.FLIGHTPOINT) {
            currentPoint = new FlightPoint(dataArray);
        }
        currentPoint.type = type;
        return currentPoint;
    }

    /**
     * A function to determine if a data point is of the same type
     * @param obj A data point to check equality of type against
     * @return A boolean variable, true if the data point is of the same type
     */
    @Override
    public boolean equals(Object obj) {
        //megan's test for bad data...
        DataPoint mypoint = (DataPoint) obj;
        if (this.getType() == mypoint.getType()) return true;
        else return false;
    }


}

