package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.DataPoint;
import seng202.group2.blackbirdModel.DataTypes;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by emr65 on 26/09/16.
 */
public class AnalysisTabController {

    @FXML
    StackedBarChart<String, Integer> graph1;

    @FXML CategoryAxis xAxis;
    @FXML NumberAxis yAxis;

    private MainController mainController;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        // Get an array with the English month names.
        String[] months =  {"a","b","c","d","e","f","g","h","i","j","k","l"};
        // Convert it to a list and add it to our ObservableList of months.
        monthNames.addAll(Arrays.asList(months));

        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(monthNames);
        //setPersonData();
        //setGraphData();
    }

    protected void setGraphData() {
        ArrayList<DataPoint> myPoints = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        //myPoints  = Analyser.rankAirports(myPoints, true);


        for(DataPoint currentPoint: myPoints){
            AirportPoint cp2 = (AirportPoint) currentPoint;
            System.out.println(cp2.toStringWithRoutes());
            //DataPoint convertedRoutePoint = currentPoint;
            //rankedData.add(convertedRoutePoint);
        }


        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        XYChart.Series<String, Integer> series2 = new XYChart.Series<>();

        for (int i = 0; i < 10; i++) {
            DataPoint currentPoint = myPoints.get(i);
            AirportPoint castedPoint = (AirportPoint) currentPoint;
            System.out.println(castedPoint.getIncomingRoutes() + "--" + castedPoint.getOutgoingRoutes());
            series.getData().add(new XYChart.Data<>(monthNames.get(i), castedPoint.getIncomingRoutes() ));
            series2.getData().add(new XYChart.Data<>(monthNames.get(i), castedPoint.getOutgoingRoutes() ));
        }

        graph1.getData().addAll(series, series2);

    }

    /**
     * Sets the persons to show the statistics for.
     *
     */
    public void setPersonData() {
        // Count the number of people having their birthday in a specific month.
        int[] monthCounter = new int[]{1,2,3,4,5,6,7,8,9,10,11,12};
        int[] pooCounter = new int[]{2,4,6,8,10,12,14,16,18,20,22,24};
        
        
        

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        XYChart.Series<String, Integer> series2 = new XYChart.Series<>();

        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
            series2.getData().add(new XYChart.Data<>(monthNames.get(i), pooCounter[i]));
        }


        graph1.getData().addAll(series, series2);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}