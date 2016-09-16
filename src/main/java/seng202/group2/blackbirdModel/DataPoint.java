package seng202.group2.blackbirdModel;

import javax.xml.crypto.Data;

public class DataPoint {

    private String type;

    public static DataPoint createDataPoint(String[] currentLine, String type) {
        DataPoint currentPoint = new DataPoint();
        if ("AirlinePoint".equals(type)){
            currentPoint = new AirlinePoint(currentLine);

        }

        else if("AirportPoint".equals(type)){
            currentPoint = new AirportPoint(currentLine);

        }

        else if("RoutePoint".equals(type)){
            currentPoint = new RoutePoint(currentLine);

        }
        else if("FlightPoint".equals(type)){
            currentPoint = new FlightPoint(currentLine);
            //System.out.println(currentPoint);

        }

        currentPoint.type = type;
        //System.out.println(type);
//        System.out.println(currentPoint);
        return currentPoint;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    	@Override
	public boolean equals(Object obj){
		//megan's test for bad data...
		DataPoint mypoint = (DataPoint) obj;
		if(this.getType() == mypoint.getType()) return true;
		else return false;
	}


    /*TODO
     * WHAT METHODS HERE????

    public void importData(){


    }*/

}
