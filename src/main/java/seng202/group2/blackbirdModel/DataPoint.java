

package seng202.group2.blackbirdModel;

import seng202.group2.blackbirdControl.ErrorTabController;
import seng202.group2.blackbirdControl.FilterRefactor;

import java.util.ArrayList;

/**
 * A super class for the data points held in the program
 */
public class DataPoint {
     /* This is a more generic type of Data called a dataPoint
        Its type is defined by a string called type
        To make a data just go Datapoint myPoint = new FlightPoint
        However You would then have to set the Type using myPoint.setType*/

    private DataTypes type;
    private int fileLine;

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

    public DataTypes getType() {
        return type;
    }

    public void setType(DataTypes type) {
        this.type = type;
    }

    public void setFileLine(int fileLine) {
        this.fileLine = fileLine;
    }


    public int getFileLine() {
        return fileLine;
    }


    /**
     * @param dataArray The array of data containing Strings
     * @param type      The input type the Data point should be set to
     * @return A DataPoint with a specificType
     */
    public static DataPoint createDataPointFromStringArray(String[] dataArray, DataTypes type, int count, ErrorTabController errorTab) {
        DataPoint currentPoint = new DataPoint(type);
        if (type == DataTypes.AIRLINEPOINT) {
            currentPoint = new AirlinePoint(dataArray, count, errorTab);

        } else if (type == DataTypes.AIRPORTPOINT) {
            currentPoint = new AirportPoint(dataArray, count, errorTab);

        } else if (type == DataTypes.ROUTEPOINT) {
            currentPoint = new RoutePoint(dataArray, count, errorTab);

        } else if (type == DataTypes.FLIGHTPOINT) {
            currentPoint = new FlightPoint(dataArray);
            FlightPoint myFlightPoint = (FlightPoint) currentPoint;

        } else if (type == DataTypes.FLIGHT) {
            String sql = String.format("SELECT * FROM FLIGHTPOINT WHERE FlightIDNum='%s'", dataArray[0]);
            ArrayList<DataPoint> flightPoints = DataBaseRefactor.performGenericQuery(sql, DataTypes.FLIGHTPOINT);
            //Has grabbed the relevant flightpoints, now construct a new flight
            currentPoint = new Flight(flightPoints);
            //currentPoint will have a correct entry of 1 if incorrect here
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
        DataPoint mypoint = (DataPoint) obj;
        if (this.getType() == mypoint.getType()) return true;
        else return false;
    }

}

