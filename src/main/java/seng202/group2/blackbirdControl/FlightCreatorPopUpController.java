package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.*;

import java.util.ArrayList;

/**
 * This Flight creator pop up class allows enters to manually add
 * a flight point to the program.
 *
 * @author Team2
 * @version 1.0
 * @since 28/9/2016
 */
public class FlightCreatorPopUpController {

    private Stage creatorStage;
    private Parent root;
    private FlightTabController flightTabController;
    private WebEngine webEngine;

    @FXML private TableView<DataPoint> flightAdderTable;
    @FXML private TableColumn flightAdderLocaleIDCol;
    @FXML private TableColumn flightAdderTypeCol;
    @FXML private TableColumn flightAdderAltCol;
    @FXML private TableColumn flightAdderLatCol;
    @FXML private TableColumn flightAdderLongCol;

    @FXML private Text flightAdderErrorText;
    @FXML private Text flightCreatorMapErrorText;
    @FXML private WebView webView;

    @FXML private TextField wayPointIDText;
    @FXML private ComboBox wayPointTypeComboBox;
    @FXML private TextField wayPointAltText;
    @FXML private TextField wayPointLatText;
    @FXML private TextField wayPointLongText;

    /**
     * Sets the root for the pop up
     *
     * @param root The parent root
     */
    public void setRoot(Parent root) {
        this.root = root;
    }

    /**
     * Sets the stage for the pop up
     *
     * @param creatorStage The stage for the pop up
     */
    void setCreatorStage(Stage creatorStage) {
        this.creatorStage = creatorStage;
    }

    /**
     * Sets the related flight tab for the pop up
     *
     * @param controller The flight tab invoking the pop up
     * @see FlightTabController
     */
    void setFlightTabController(FlightTabController controller) {
        flightTabController = controller;
    }

    /**
     * Initialises the flight creator pop up.
     */
    public void control() {
        creatorStage.setScene(new Scene(root));
        creatorStage.setTitle("Create Flight");
        creatorStage.initModality(Modality.NONE);
        creatorStage.initOwner(null);
        flightAdderTable.setPlaceholder(new Label("Add Intermediate WayPoints by filling the fields above and pressing 'Add WayPoint'"));
        wayPointTypeComboBox.setItems(FXCollections.observableArrayList("APT", "VOR", "FIX", "NDB", "DME", "LATLON"));
        initMap();
        creatorStage.show();
    }

    /**
     * User cancels making a flight, closes the pop up
     */
    public void flightCreatorCancelButtonPressed() {
        creatorStage.close();
    }

    /**
     * User clicks the create button to create a flight.
     */
    public void flightCreatorCreateButtonPressed() {
        if (flightAdderTable.getItems().size() == 0) {
            flightAdderErrorText.setText("A flight must have at least two points");
            flightAdderErrorText.setVisible(true);
            return;
        }
        FlightPoint finalPoint = (FlightPoint) flightAdderTable.getItems().get(flightAdderTable.getItems().size() - 1);
        if (finalPoint.getLocalType().equals("APT") && flightAdderTable.getItems().size() > 1) {
            flightAdderErrorText.setVisible(false);
            ArrayList<DataPoint> myFlightData = new ArrayList<>(flightAdderTable.getItems());
            Database.insertDataPoints(myFlightData, null);
            ArrayList<DataPoint> allPoints = Filter.getAllPoints(DataTypes.FLIGHT);
            flightTabController.updateFlightsTable(allPoints);
            creatorStage.close();
        } else {
            flightAdderErrorText.setText("Final point must be of type APT");
            flightAdderErrorText.setVisible(true);
        }
    }

    /**
     * User clicks the add waypoint button to create a waypoint within the flight.
     */
    public void addWayPointButtonPressed() {
        String[] flightPoint = getWayPointValues();
        if (flightAdderTable.getItems().size() == 0 && !flightPoint[0].equals("APT")) {
            flightAdderErrorText.setText("First point must be of type APT");
            flightAdderErrorText.setVisible(true);
        } else {
            String[] checkData = Validator.checkFlightPoint(flightPoint);
            if (HelperFunctions.allValid(checkData)) {
                flightAdderErrorText.setVisible(false);
                ArrayList<DataPoint> myFlightPointData = new ArrayList<>();
                DataPoint myFlightPoint = DataPoint.createDataPointFromStringArray(flightPoint, DataTypes.FLIGHTPOINT, 0, null);
                myFlightPointData.add(myFlightPoint);
                updateWayPointTable(myFlightPointData);
            } else {
                Validator.displayFlightPointError(checkData);
                flightAdderErrorText.setText("Bad data! Please check constraints");
                flightAdderErrorText.setVisible(true);
            }
        }
    }

    /**
     * Helper function to obtain the waypoint values entered
     *
     * @return A string array containing the values obtained from input fields
     */
    private String[] getWayPointValues() {
        String wayPointID = wayPointIDText.getText();
        String type;
        if (wayPointTypeComboBox.getSelectionModel().isEmpty()) {
            type = "";
        } else {
            type = wayPointTypeComboBox.getValue().toString();
        }
        String alt = wayPointAltText.getText();
        String lat = wayPointLatText.getText();
        String lng = wayPointLongText.getText();
        return new String[]{type, wayPointID, alt, lat, lng};
    }

    /**
     * Updates the waypoint table to display entered waypoints
     *
     * @param point The current waypoints
     */
    private void updateWayPointTable(ArrayList<DataPoint> point) {
        flightAdderTable.getItems().addAll(point);
        FlightPoint myPoint = (FlightPoint) point.get(0);

        flightAdderLocaleIDCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("LocaleID"));
        flightAdderTypeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("LocalType"));
        flightAdderAltCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("Altitude"));
        flightAdderLatCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("Latitude"));
        flightAdderLongCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("Longitude"));

        Flight myFlight = new Flight(new ArrayList<>(flightAdderTable.getItems()));
        Route myRoute = new Route(Route.makeRoutePoints(myFlight));
        displayRoute(myRoute);
    }

    /**
     * Initializes the map using the class WebView to render HTML content
     *
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
            flightCreatorMapErrorText.setVisible(false);
        } catch (netscape.javascript.JSException e) {
            flightCreatorMapErrorText.setVisible(true);
        }
    }

}
