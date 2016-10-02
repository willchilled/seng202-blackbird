package seng202.group2.blackbirdControl;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.group2.blackbirdModel.*;
import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.DataPoint;
import seng202.group2.blackbirdModel.DataTypes;

import java.util.*;

/**
 * Controller for the analyser tab. Handles generation of graphs
 *
 * @author Team2
 * @version 1.0
 * @since 19/9/2016
 */
public class AnalysisTabController {

    private WebEngine webEngine;
    private String country;
    private String airport1;
    private String airport2;
    private MainController mainController;

    private boolean routeChartOpen = false;
    private boolean airportChartOpen = false;
    private boolean airlineChartOpen = false;
    private boolean equipmentChartOpen = false;

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
    @FXML WebView webView;
    @FXML Text analysisTabMapErrorText;

    /**
     * Initializes the analysis tab
     */
    @FXML
    private void initialize() {
        initMap();
    }

    /**
     * Method that clears data from the Airports by Incoming/Outgoing Routes chart,
     * then re-populates it depending on the selected country.
     */
    private void populateRouteGraph() {
        routeChart.getData().clear();
        ObservableList<String> airportNames = FXCollections.observableArrayList();
        ArrayList<DataPoint> myPoints;

        if (getCountry() == null) {
            ArrayList<String> menus = new ArrayList<>(Arrays.asList(getCountry()));
            myPoints = FilterRefactor.filterSelections(menus, "", DataTypes.AIRPORTPOINT);
        } else if (getCountry().equals("All") || getCountry().equals("Select Country")) {
            myPoints = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        } else {
            ArrayList<String> menus = new ArrayList<>(Arrays.asList(getCountry()));
            myPoints = FilterRefactor.filterSelections(menus, "", DataTypes.AIRPORTPOINT);
        }

        myPoints = Analyser.rankAirportsByRoutes(myPoints);
        ArrayList<AirportPoint> myAirportPoints = new ArrayList<>();
        for (DataPoint currentPoint : myPoints) {
            AirportPoint cp2 = (AirportPoint) currentPoint;
            myAirportPoints.add(cp2);
        }

        if (myPoints.size() > 1) {
            xAxisRoute.setCategories(airportNames);
            xAxisRoute.setTickLabelRotation(270);
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
            int maxAirports = 20;
            if (myAirportPoints.size() < 20) {
                maxAirports = myAirportPoints.size();
            }

            for (int i = 0; i < maxAirports; i++) {
                String name = myAirportPoints.get(i).getAirportName();
                if (!airportNames.contains(name)) {
                    airportNames.add(name);
                } else {
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

    /**
     * Method that populates the graph with the 20 countries with the most airports
     * as attained from the numAirportsPerCountry in the Analyser class.
     *
     * @see Analyser
     */
    @FXML
    private void populateAirportsByCountryGraph() {
        if (!airportChartOpen) {
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
                series.getData().add(new XYChart.Data(airportsPerCountry.get(i).getValue(), countryNames.get(i)));
            }
            airportChart.getData().addAll(series);
            airportChartOpen = true;
        } else {
            airportChartOpen = false;
        }
    }

    /**
     * Method that populates the graph with the 20 countries with the most airlines
     * as attained from the numAirlinesPerCountry in the Analyser class.
     *
     * @see Analyser
     */
    @FXML
    private void populateAirlinesPerCountryGraph() {
        if (!airlineChartOpen) {
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
                series.getData().add(new XYChart.Data(airlinesPerCountry.get(i).getValue(), countryNames.get(i)));
            }
            airlineChart.getData().addAll(series);
            airlineChartOpen = true;
        } else {
            airlineChartOpen = false;
        }
    }

    /**
     * Method that populates the graph with the 20 plane types (equipment) with the most routes
     * using these planes as attained from the routesPerEquipment in the Analyser class.
     *
     * @see Analyser
     */
    @FXML
    private void populateEquipmentChartData() {
        if (!equipmentChartOpen) {
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
                series.getData().add(new XYChart.Data(equipmentNames.get(i), rotuesPerEquip.get(i).getValue()));
            }
            equipmentChart.getData().addAll(series);
            equipmentChartOpen = true;
        } else {
            equipmentChartOpen = false;
        }
    }

    /**
     * Creates link between the AnalysisTab and the MainController
     *
     * @param mainController The controller for this tab window
     */
    void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Method to create the list of countries that will populate the ComboBox used
     * to control the data shown by the RouteChart
     *
     * @return Observable list of the country names of the airports loaded
     */
    private ObservableList<String> populateAirportCountryList() {
        ArrayList<String> countries = FilterRefactor.filterDistinct("country", "Airport");
        ObservableList<String> countryList = FXCollections.observableArrayList(countries);
        countryList.add(0, "All");
        countryList.add(0, "Select Country");
        return countryList;
    }

    /**
     * Method to create a list of airports as strings. This list is later used to populate the
     * calculating distance comoboxes.
     *
     * @return Observable list of airport name and ICAO pairs
     */
    private ObservableList<String> populateAirportDistanceList() {
        //Populates the dropdown of airline countries
        ArrayList<DataPoint> airports = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        ArrayList<String> nameIcaoAirport = new ArrayList<>();
        for (DataPoint curAirport : airports) {
            AirportPoint castedPoint = (AirportPoint) curAirport;
            if (castedPoint.getIcao().length() > 0 && castedPoint.getAirportName().length() > 0) {
                String pair = castedPoint.getAirportName() + " " + castedPoint.getIcao();
                if (!nameIcaoAirport.contains(pair)) {
                    nameIcaoAirport.add(pair);
                }
            }
        }
        ObservableList<String> airportDistList = FXCollections.observableArrayList(nameIcaoAirport);
        Collections.sort(airportDistList, String.CASE_INSENSITIVE_ORDER);
        airportDistList.add(0, "Select Airport");
        return airportDistList;
    }

    /**
     * A method that prepares the routeChart to be populated.
     * Gets the selected country from the airportCountryFilterMenu ComoboBox
     * Then calls the populateRouteGraph method
     */
    public void prepareRouteChart() {
        if (!routeChartOpen) {
            ArrayList<DataPoint> airports = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
            if (airports.size() > 0) {
                airportCountryFilterMenu.setVisible(true);
                ObservableList<String> airportCountryList = populateAirportCountryList();
                airportCountryFilterMenu.setValue(airportCountryList.get(0));
                airportCountryFilterMenu.setItems(airportCountryList);
                setCountry("All");
                airportCountryFilterMenu.valueProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue ov, String t, String t1) {
                        setCountry(t1);
                        populateRouteGraph();
                    }
                });
                populateRouteGraph();
                routeChartOpen = true;
            }
        } else {
            routeChartOpen = false;
        }
    }

    /**
     * Method to populate the Comoboxes, then change the values of airport1 and airport2 upon
     * user selection. CalculateDistance() is then called to find the distance between the 2
     * airports.
     */
    public void airportDistanceSetup() {
        ArrayList<DataPoint> airports = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        if (airports.size() > 0) {
            ObservableList<String> airportDistanceList = populateAirportDistanceList();
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

    /**
     * Method to get the AirportPoints corresponding to the ICAO and name of the selected
     * airports, then call Analyser.calculateDistance, and update the display in the GUI to show the
     * distance.
     *
     * @see Analyser
     */
    private void calculateDistance() {
        if (getAirport1() == null || getAirport2() == null) {
            //empty
        } else if (!(getAirport1().equals("Select Airport") || getAirport2().equals("Select Airport"))) {
            AirportPoint d1 = (AirportPoint) FilterRefactor.findAirportPointForDistance(getAirport1());
            AirportPoint d2 = (AirportPoint) FilterRefactor.findAirportPointForDistance(getAirport2());
            ArrayList<Position> positions = new ArrayList<>();

            double d1Lat = d1.getLatitude();
            double d1Long = d1.getLongitude();
            double d2Lat = d2.getLatitude();
            double d2Long = d2.getLongitude();
            Position p1 = new Position(d1Lat, d1Long);
            Position p2 = new Position(d2Lat, d2Long);
            positions.add(p1);
            positions.add(p2);
            Route newRoute = new Route(positions);
            displayRoute(newRoute);

            double distance = Analyser.calculateDistance(d1, d2);
            String header = "Distance between " + d1.getAirportName() + " and " + d2.getAirportName() + ": ";
            distanceHeader.setText(header);
            calculatedDistance.setText(String.valueOf(distance).format("%1$,.2f", distance) + " km.");
        }
    }

    /**
     * @return the country selected in the airportCountryFilterMenu ComboBox
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Initializes the map with the JavaScript
     */
    private void initMap() {
        webEngine = webView.getEngine();
        webEngine.load(getClass().getClassLoader().getResource("map.html").toExternalForm());
    }

    /**
     * Displays a route on the map by way of making a javascript and executing it through the web engine
     *
     * @param newRoute Route to be displayed
     */
    private void displayRoute(Route newRoute) {
        try {
            String scriptToExecute = "displayRoute(" + newRoute.toJSONArray() + ");";
            webEngine.executeScript(scriptToExecute);
            analysisTabMapErrorText.setVisible(false);
        } catch (netscape.javascript.JSException e) {
            analysisTabMapErrorText.setVisible(true);
        }
    }

    /**
     * Method to set country to the value selected in the airportCountryFilterMenu Combobox
     * @param country The country selected
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return String containing the name and ICAO of the airport selected in the
     * airportDistanceCB1 ComoboBox
     */
    private String getAirport1() {
        return airport1;
    }

    /**
     * Method to set airport1 to the value selected in the airportDistanceCB1 Combobox
     *
     * @param airport1 The first airport
     */
    private void setAirport1(String airport1) {
        this.airport1 = airport1;
    }

    /**
     * @return String containing the name and ICAO of the airport selected in the
     * airportDistanceCB2 ComoboBox
     */
    private String getAirport2() {
        return airport2;
    }

    /**
     * Method to set airport2 to the value selected in the airportDistanceCB2 Combobox
     *
     * @param airport2 The second airport
     */
    private void setAirport2(String airport2) {
        this.airport2 = airport2;
    }

}