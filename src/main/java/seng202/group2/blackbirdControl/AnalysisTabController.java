package seng202.group2.blackbirdControl;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.DataPoint;
import seng202.group2.blackbirdModel.DataTypes;

import java.util.*;

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


    @FXML ComboBox airportDistanceCB1;
    @FXML ComboBox airportDistanceCB2;
    @FXML Label calculatedDistance;
    @FXML Label distanceHeader;

    private String country;

    private String airport1;
    private String airport2;

    private MainController mainController;

    ObservableList<String> airportCountryList = FXCollections.observableArrayList("No values Loaded");
    ObservableList<String> airportDistanceList = FXCollections.observableArrayList("No values Loaded");


    private boolean routeChartOpen = false;
    private boolean airportChartOpen = false;
    private boolean airlineChartOpen = false;
    private boolean equipmentChartOpen = false;


    @FXML
    private void initialize() {
        xAxisAirline.setTickLabelRotation(90);

    }
    @FXML
    protected void setRouteGraphData() {

        routeChart.getData().clear();
        ObservableList<String> airportNames = FXCollections.observableArrayList();
        ArrayList<DataPoint>  myPoints;

        if(getCountry() == null){
            ArrayList<String> menus = new ArrayList<>(Arrays.asList(getCountry()));
            myPoints = FilterRefactor.filterSelections(menus, "", DataTypes.AIRPORTPOINT);
        }
        else if (getCountry().equals("All")){
            myPoints = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        }
        else{
            ArrayList<String> menus = new ArrayList<>(Arrays.asList(getCountry()));
            myPoints = FilterRefactor.filterSelections(menus, "", DataTypes.AIRPORTPOINT);
        }

        myPoints  = Analyser.rankAirportsByRoutes(myPoints, true);
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

        //myStage.close();

    }

    @FXML
    private void setAirportsByCountryGraph() {

        if(!airportChartOpen) {
            System.out.println("opening");
            airportChart.getData().clear();
            ObservableList<String> countryNames = FXCollections.observableArrayList();
            yAxisAirport.setCategories(countryNames);

            List<Map.Entry> airportsPerCountry = Analyser.numAirportsPerCountry();
            xAxisAirport.setTickLabelRotation(270);

            XYChart.Series series = new XYChart.Series();

            int maxAirports = 20;

            if (airportsPerCountry.size() < 20) {
                maxAirports = airportsPerCountry.size();
            }

            for (int i = 0; i < maxAirports; i++) {
                String name = (String) airportsPerCountry.get(i).getKey();
                if (!countryNames.contains(name)) {
                    countryNames.add(name);
                } else {
                    countryNames.add(name + i);
                }
            }

            for (int i = 0; i < maxAirports; i++) {
                series.getData().add(new XYChart.Data((Integer) airportsPerCountry.get(i).getValue(), countryNames.get(i)));
                //TODO Reverse me
                //TODO choice for number displayed lolol
            }
            airportChart.getData().addAll(series);
            airportChartOpen = true;
        }
        else{
            System.out.println("closing");
            airportChartOpen = false;
        }
    }

    @FXML
    private void setAirlinesPerCountryGraph() {
        if(!airlineChartOpen) {

            airlineChart.getData().clear();
            ObservableList<String> countryNames = FXCollections.observableArrayList();
            yAxisAirline.setCategories(countryNames);
            List<Map.Entry> airlinesPerCountry = Analyser.numAirlinesPerCountry();
            XYChart.Series series = new XYChart.Series();
            int maxAirlines = 20;

            if (airlinesPerCountry.size() < 20) {
                maxAirlines = airlinesPerCountry.size();
            }
            for (int i = 0; i < maxAirlines; i++) {
                String name = (String) airlinesPerCountry.get(i).getKey();
                if (!countryNames.contains(name)) {
                    countryNames.add(name);
                } else {
                    countryNames.add(name + i);
                }
            }
            for (int i = 0; i < maxAirlines; i++) {
                series.getData().add(new XYChart.Data((Integer) airlinesPerCountry.get(i).getValue(), countryNames.get(i)));
                //TODO Reverse me
                //TODO choice for number displayed lolol
            }
            airlineChart.getData().addAll(series);
            airlineChartOpen = true;
        }
        else {
            airlineChartOpen = false;
        }
    }


    @FXML
    private void setEquipmentChartData() {

        if(!equipmentChartOpen) {
            equipmentChart.getData().clear();
            ObservableList<String> equipmentNames = FXCollections.observableArrayList();
            xAxisEquipment.setCategories(equipmentNames);
            xAxisEquipment.setTickLabelRotation(270);

            List<Map.Entry> rotuesPerEquip = Analyser.routesPerEquipment();
            XYChart.Series series = new XYChart.Series();

            int maxEquip = 20;

            if (rotuesPerEquip.size() < 20) {
                maxEquip = rotuesPerEquip.size();
            }

            for (int i = 0; i < maxEquip; i++) {
                String name = (String) rotuesPerEquip.get(i).getKey();
                if (!equipmentNames.contains(name)) {
                    equipmentNames.add(name);
                } else {
                    equipmentNames.add(name + i);
                }
            }

            for (int i = 0; i < maxEquip; i++) {
                series.getData().add(new XYChart.Data(equipmentNames.get(i), (Integer) rotuesPerEquip.get(i).getValue()));

            }
            equipmentChart.getData().addAll(series);
            equipmentChartOpen = true;
        }
        else{
            equipmentChartOpen = false;
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

    private ObservableList<String> populateAirportDistanceList(){
        //Populates the dropdown of airline countries
        ArrayList<DataPoint> airports = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        ArrayList<String> nameIcaoAirport = new ArrayList<String>();
        for(DataPoint curAirport: airports){
            AirportPoint castedPoint = (AirportPoint) curAirport;
            if(castedPoint.getIcao().length() > 0 && castedPoint.getAirportName().length() > 0) {
                String pair = castedPoint.getAirportName() + " " + castedPoint.getIcao();
                if (!nameIcaoAirport.contains(pair)) {
                    nameIcaoAirport.add(pair);
                }
            }
        }
        ObservableList<String> airportDistList = FXCollections.observableArrayList(nameIcaoAirport);
        Collections.sort(airportDistList, String.CASE_INSENSITIVE_ORDER);
        airportDistList.add(0,"Select Airport");

        return airportDistList;
    }

    //TODO this might not be necessary. Check how it runs without it. Delete if possible
/*    public void countrySwap() {
        String currentCountry;
        currentCountry = (String) airportCountryFilterMenu.getSelectionModel().getSelectedItem();
        setCountry(currentCountry);
        setRouteGraphData();
    }*/

    public void checkData(){
        if(!routeChartOpen){
        ArrayList<DataPoint> airports = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        if(airports.size() > 0) {
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
            routeChartOpen = true;
            }
        }
        else{
            routeChartOpen = false;
        }
    }


    public void setAirportDistanceCalculator(){
        ArrayList<DataPoint> airports = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        String airport1 = "";
        String airport2 = "";
        if(airports.size() > 0) {
            airportDistanceList = populateAirportDistanceList();
            airportDistanceCB1.setValue(airportDistanceList.get(0));
            airportDistanceCB2.setValue(airportDistanceList.get(0));
            airportDistanceCB1.setItems(airportDistanceList);
            airportDistanceCB2.setItems(airportDistanceList);

            airportDistanceCB1.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    setAirport1(t1);
                    calculateDistance();

                }
            });

            airportDistanceCB2.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    setAirport2(t1);
                    calculateDistance();


                }
            });

        }

    }

    private void calculateDistance() {
        if(getAirport1() == null || getAirport2() == null){

        }

        else if(!(getAirport1().equals("Select Airport") || getAirport2().equals("Select Airport"))){

            AirportPoint d1 = (AirportPoint) FilterRefactor.findAirportPointForDistance(getAirport1());
            AirportPoint d2 = (AirportPoint) FilterRefactor.findAirportPointForDistance(getAirport2());

            double distance = Analyser.calculateDistance(d1, d2);
            String header = "Distance between " + d1.getAirportName() + " and " + d2.getAirportName() + ": ";
            distanceHeader.setText(header);
            calculatedDistance.setText(String.valueOf(distance).format("%1$,.2f", distance) + " km.");


        }
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry(){return this.country;}

    public String getAirport2() {
        return airport2;
    }

    public void setAirport2(String airport2) {
        this.airport2 = airport2;
    }

    public String getAirport1() {
        return airport1;
    }

    public void setAirport1(String airport1) {
        this.airport1 = airport1;
    }
}