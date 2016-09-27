package seng202.group2.blackbirdControl;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.text.Font;
import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.DataBaseRefactor;
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

    @FXML StackedBarChart<String, Integer> routeChart;
    @FXML BarChart<String, Integer> airportChart;
    @FXML BarChart<String, Integer> airlineChart;
    @FXML BarChart<String, Integer> equipmentChart;

    @FXML CategoryAxis xAxisRoute;
    @FXML NumberAxis yAxisRotue;
    @FXML CategoryAxis yAxisAirport;
    @FXML NumberAxis xAxisAirport;
    @FXML CategoryAxis yAxisAirline;
    @FXML NumberAxis xAxisAirline;
    @FXML CategoryAxis xAxisEquipment;
    @FXML NumberAxis yAxisEquipment;

    @FXML ComboBox airportCountryFilterMenu;

    private String country;

    private MainController mainController;

    ObservableList<String> airportCountryList = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> monthNames = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        xAxisAirline.setTickLabelRotation(90);

    }
    @FXML
    protected void setRouteGraphData() {

        ObservableList<String> airportNames = FXCollections.observableArrayList();
        routeChart.getData().clear();
        ArrayList<DataPoint>  myPoints;

        if (country.equals("All")){
            myPoints = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        }
        else{
            ArrayList<String> menus = new ArrayList<>(Arrays.asList(getCountry()));
            myPoints = FilterRefactor.filterSelections(menus, "", DataTypes.AIRPORTPOINT);
        }

        myPoints  = Analyser.rankAirports(myPoints, true);
        ArrayList<AirportPoint> myAirportPoints = new ArrayList<>();

        for(DataPoint currentPoint: myPoints){
            AirportPoint cp2 = (AirportPoint) currentPoint;
            myAirportPoints.add(cp2);
        }

        if (myPoints.size() > 1){
            xAxisRoute.setCategories(airportNames);
            xAxisRoute.setTickLabelRotation(270);
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
            int maxAirports = 20;

            if (myAirportPoints.size() < 20){
                maxAirports = myAirportPoints.size();
            }

            for (int i = 0; i <maxAirports ; i++) {
                String name = myAirportPoints.get(i).getAirportName();
                if (!airportNames.contains(name)){
                    airportNames.add(name);
                }
                else{
                    airportNames.add(name + i);
                }
            }
            for (int i = 0; i < maxAirports; i++) {
                DataPoint currentPoint = myPoints.get(i);
                AirportPoint castedPoint = (AirportPoint) currentPoint;
                series.getData().add(new XYChart.Data<>(airportNames.get(i), castedPoint.getIncomingRoutes()));
                series2.getData().add(new XYChart.Data<>(airportNames.get(i), castedPoint.getOutgoingRoutes()));
            }
            routeChart.getData().addAll(series, series2);
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private ObservableList<String> populateAirportCountryList(){
        //Populates the dropdown of airline countries
        ArrayList<String> countries = FilterRefactor.filterDistinct("country", "Airport");
        ObservableList<String> countryList = FXCollections.observableArrayList(countries);
        countryList.add(0,"All");

        return countryList;
    }

    public void countrySwap() {
        String currentCountry;
        currentCountry = (String) airportCountryFilterMenu.getSelectionModel().getSelectedItem();
        setCountry(currentCountry);
        setRouteGraphData();
    }


    public void checkData(){
        ArrayList<DataPoint> airports = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        if(airports.size() > 0){
            airportCountryFilterMenu.setVisible(true);
            airportCountryList = populateAirportCountryList();
            airportCountryFilterMenu.setValue(airportCountryList.get(0));
            airportCountryFilterMenu.setItems(airportCountryList);
            setCountry("All");
            airportCountryFilterMenu.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    setCountry(t1);
                    setRouteGraphData();

                }
            });

            String currentCountry;
            currentCountry = (String) airportCountryFilterMenu.getSelectionModel().getSelectedItem();
            setRouteGraphData();
        }
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry(){return this.country;}
}