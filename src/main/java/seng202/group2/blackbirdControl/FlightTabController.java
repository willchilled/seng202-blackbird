package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Controller for the flight tab. Handles displaying of data, and acts as a controller for adding and deleting data.
 */
public class FlightTabController {

    private MainController mainController;
    private FlightTabController instance;
    private Flight flight;
    private FlightPoint flightPoint;
    private WebEngine webEngine;
    private ObservableList<String> flightSrcICAOList = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> flightDstICAOList  = FXCollections.observableArrayList("No values Loaded");

    //Flight table
    @FXML private TableView<DataPoint> flightTable;
    @FXML private TableView<DataPoint> flightPointTable;
    @FXML private TableColumn flightSourceCol;
    @FXML private TableColumn flightDestCol;

    //Flight point table
    @FXML private TableColumn flightPointTypeCol;
    @FXML private TableColumn flightPointLocaleCol;
    @FXML private TableColumn flightPointAltitudeCol;
    @FXML private TableColumn flightPointLatitudeCol;
    @FXML private TableColumn flightPointLongitudeCol;

    @FXML private ComboBox flightSrcICAOMenu;
    @FXML private ComboBox flightDstICAOMenu;
    @FXML private TextField flightSearchQuery;
    @FXML private Text flightTabMapErrorText;
    @FXML private WebView webView;

    /**
     * Creates the flight tab
     */
    public FlightTabController() {
        instance = this;
    }

    /**
     * Initializes the tables
     */
    public void show() {
        flightTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Flight"));
        flightPointTable.setPlaceholder(new Label("No data in table. Double click on a flight"));
    }

    /**
     * Initializes the flight tab
     */
    public void initialize() {
        flightSrcICAOMenu.setValue(flightSrcICAOList.get(0));
        flightSrcICAOMenu.setItems(flightSrcICAOList);

        flightDstICAOMenu.setValue(flightDstICAOList.get(0));
        flightDstICAOMenu.setItems(flightDstICAOList);
        initMap();
    }

    /**
     *
     * @param controller
     */
    void setMainController(MainController controller) {
        this.mainController = controller;
    }

    /**
     * Called upon clicking the Add Flight Data option in the menu bar. Adds a specified CSV file of a flight to the
     * database and then updates the table
     */
    void addFlightData() {
        File f = HelperFunctions.getFile("Add Flight Data", false);
        if (f == null) {
            return;
        }
        ErrorTabController errorTab = mainController.getErrorTabController();
        ArrayList<DataPoint> myFlightData = ParserRefactor.parseFile(f, DataTypes.FLIGHTPOINT, errorTab);
        if (myFlightData == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Flight Error");
            alert.setHeaderText("Invalid flight file");
            alert.setContentText("Invalid entries were found, please check your file");
            alert.showAndWait();
            return;
        }
        DataBaseRefactor.insertDataPoints(myFlightData, errorTab);
        ArrayList<DataPoint> myFlights = Filter.getAllPoints(DataTypes.FLIGHT);
        updateFlightFields();
        updateFlightsTable(myFlights);
        mainController.updateTab(DataTypes.FLIGHT);
    }

    /**
     * A method to display the flights
     */
    void displayFlights() {
        ArrayList<DataPoint> myFlights = Filter.getAllPoints(DataTypes.FLIGHT);
        updateFlightFields();
        updateFlightsTable(myFlights);
        mainController.updateTab(DataTypes.FLIGHT);
    }

    /**
     * Updates all flight filter dropdowns with current data
     */
    private void updateFlightFields() {
        flightSrcICAOList = populateFlightSrcList();
        flightSrcICAOMenu.setItems(flightSrcICAOList);
        flightSrcICAOMenu.setValue(flightSrcICAOList.get(0));

        flightDstICAOList = populateFlightDstList();
        flightDstICAOMenu.setItems(flightDstICAOList);
        flightDstICAOMenu.setValue(flightDstICAOList.get(0));
    }

    /**
     * Populates the SrcICAOs dropdown upon adding data.
     *
     * @return The list of current SrcICAOs in the database for flights
     */
    private ObservableList<String> populateFlightSrcList() {
        ArrayList<String> srcICAOs = Filter.filterDistinct("SrcICAO", "FLIGHT");
        ObservableList<String> srcICAOList = FXCollections.observableArrayList(srcICAOs);
        srcICAOList = HelperFunctions.addNullValue(srcICAOList);
        return srcICAOList;
    }

    /**
     * Populates the DstICAO dropdown upon adding data.
     *
     * @return The list of current DstICAOs in the database for flights
     */
    private ObservableList<String> populateFlightDstList() {
        ArrayList<String> dstICAOs = Filter.filterDistinct("DstICAO", "FLIGHT");
        ObservableList<String> dstICAOList = FXCollections.observableArrayList(dstICAOs);
        dstICAOList = HelperFunctions.addNullValue(dstICAOList);
        return dstICAOList;
    }

    /**
     * Called upon pressing the 'Filter' button in the flights tab. Grabs all info from the filter selections and sends
     * it to the Filter class to create a search query to obtain all flight points. Updates the flight table with these
     * points.
     */
    public void flightFilterButtonPressed() {
        String srcAirport = flightSrcICAOMenu.getValue().toString();
        String dstAirport = flightDstICAOMenu.getValue().toString();
        String searchQuery = flightSearchQuery.getText();
        ArrayList<DataPoint> allPoints;

        if (srcAirport.equals("No values loaded") && dstAirport.equals("No values loaded")) {
            allPoints = Filter.getAllPoints(DataTypes.FLIGHT);
        } else {
            ArrayList<String> menusPressed = new ArrayList<>();
            menusPressed.add(srcAirport);
            menusPressed.add(dstAirport);
            allPoints = Filter.filterSelections(menusPressed, searchQuery, DataTypes.FLIGHT);
        }
        updateFlightsTable(allPoints);
    }

    /**
     * Called upon pressing the 'Add Flight' button in Flights. Opens the flight creator
     */
    public void flightMakeFlightButtonPressed() {
        try {
            Stage creatorStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FlightCreatorPopUp.fxml"));
            Parent root = loader.load();

            FlightCreatorPopUpController popUpController = loader.getController();
            popUpController.setFlightTabController(instance);
            popUpController.setCreatorStage(creatorStage);
            popUpController.setRoot(root);
            popUpController.control();
            mainController.mainMenuHelper();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Assigns an action for the enter key
     *
     * @param ke The keyevent that occurred (the Enter key event)
     */
    public void enterPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ENTER) {
            flightFilterButtonPressed();
        }
    }

    /**
     * Updates the filght tables upon being called. Also contains a nested function to update the flightPoint table
     * upon clicking a row
     *
     * @param filteredFlights A list of the flights with which to update the tables
     */
    public void updateFlightsTable(ArrayList<DataPoint> filteredFlights) {
        flightPointTable.getItems().setAll();
        flightTable.getItems().setAll(filteredFlights);
        flightSourceCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("srcAirport"));
        flightDestCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("destAirport"));

        flightTable.getItems().addListener((ListChangeListener<DataPoint>) c -> {
            flightTable.getColumns().get(0).setVisible(false);
            flightTable.getColumns().get(0).setVisible(true);
        });

        flightTable.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                Flight pressedFlight = (Flight) flightTable.getSelectionModel().getSelectedItem();
                if (!(pressedFlight == null)) {
                    flight = pressedFlight;
                } else {
                    return;
                }
                Route myRoute = new Route(Route.makeRoutePoints(flight));
                displayRoute(myRoute);

                flightPointTable.getItems().setAll(pressedFlight.getFlightPoints());
                flightPointTypeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("localType"));
                flightPointLocaleCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("localeID"));
                flightPointAltitudeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, Float>("altitude"));
                flightPointLatitudeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("latitude"));
                flightPointLongitudeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("longitude"));
            }
        });

        flightPointTable.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                FlightPoint pressedPoint = (FlightPoint) flightPointTable.getSelectionModel().getSelectedItem();
                if (!(pressedPoint == null)) {
                    flightPoint = pressedPoint;
                } else {
                    return;
                }
                double lat = flightPoint.getLatitude();
                double lng = flightPoint.getLongitude();
                ArrayList<Position> myWayPoint = new ArrayList<>();
                myWayPoint.add(new Position(lat, lng));
                Route myMarker = new Route(myWayPoint);
                displayWayPoint(myMarker);
            }
        });
    }

    /**
     * Exports the current flightPoint data showing into a CSV file of the users chosing.
     */
    void exportFlightData() {
        ArrayList<DataPoint> myPoints = new ArrayList<>(flightPointTable.getItems());
        Exporter.exportData(myPoints);
    }

    /**
     * Deletes a single flight
     */
    public void deleteSingleFlight() {
        Flight pressedFlight = (Flight) flightTable.getSelectionModel().getSelectedItem();
        if (!(pressedFlight == null)) {
            flight = pressedFlight;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No flight selected to delete");
            alert.setContentText("Please select a flight first");
            alert.showAndWait();
            return;
        }
        int id = flight.getFlightID();
        String flightSql = String.format("DELETE FROM FLIGHT WHERE FlightIDNum = %s", id);
        String flightPointSql = String.format("DELETE FROM FLIGHTPOINT WHERE FlightIDNum = %s", id);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Entry");
        alert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            DataBaseRefactor.editDataEntry(flightSql);
            DataBaseRefactor.editDataEntry(flightPointSql);
            flightFilterButtonPressed();
            mainController.mainMenuHelper();
        }
    }

    /**
     * Initializes the map using the class WebView to render HTML content
     * @see WebView
     */
    private void initMap() {
        webEngine = webView.getEngine();
        webEngine.load(getClass().getClassLoader().getResource("map.html").toExternalForm());
    }

    /**
     * Displays a route on the map using a JSON query executed by the web engine
     *
     * @param newRoute The route to be displayed on the map
     * @see WebView
     */
    private void displayRoute(Route newRoute) {
        try {
            String scriptToExecute = "displayRoute(" + newRoute.toJSONArray() + ");";
            webEngine.executeScript(scriptToExecute);
            flightTabMapErrorText.setVisible(false);
        } catch (netscape.javascript.JSException e) {
            flightTabMapErrorText.setVisible(true);
        }
    }

    /**
     * Displays a particular waypoint
     * @param newWayPoint The waypoint to display
     */
    private void displayWayPoint(Route newWayPoint) {
        try {
            String scriptToExecute = "displayWayPoint(" + newWayPoint.toJSONArray() + ")";
            webEngine.executeScript(scriptToExecute);
            flightTabMapErrorText.setVisible(false);
        } catch (netscape.javascript.JSException e) {
            flightTabMapErrorText.setVisible(true);
        }
    }

    /**
     * See all the flights in the current project
     */
    public void flightseeAllButtonPressed() {
        ArrayList<DataPoint> allPoints;
        allPoints = Filter.getAllPoints(DataTypes.FLIGHT);
        updateFlightsTable(allPoints);
    }
}
