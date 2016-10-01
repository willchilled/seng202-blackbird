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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by wmu16 on 24/09/16.
 */
public class FlightCreatorPopUpController {

    private Stage creatorStage;
    private Parent root;
    private FlightTabController flightTabController;

    @FXML private TableView<DataPoint> flightAdderTable;
    @FXML private TableColumn flightAdderLocaleIDCol;
    @FXML private TableColumn flightAdderTypeCol;
    @FXML private TableColumn flightAdderAltCol;
    @FXML private TableColumn flightAdderLatCol;
    @FXML private TableColumn flightAdderLongCol;

    @FXML private Text flightAdderErrorText;
    @FXML private Text flightCreatorMapErrorText;

    @FXML private TextField wayPointIDText;
    @FXML private ComboBox wayPointTypeComboBox;
    @FXML private TextField wayPointAltText;
    @FXML private TextField wayPointLatText;
    @FXML private TextField wayPointLongText;

    @FXML private WebView webView;
    private WebEngine webEngine;



    public void setRoot(Parent root) {
        this.root = root;
    }

    public void setCreatorStage(Stage creatorStage) {
        this.creatorStage = creatorStage;
    }

    public void setFlightTabController(FlightTabController controller) {
        flightTabController = controller;
    }

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

    public void flightCreatorCancelButtonPressed(){

        creatorStage.close();
    }

    public void flightCreatorCreateButtonPressed(){

        //This is damn gross ill fix this up later there's probably a better method for getting a specific row in a table -W
        if(flightAdderTable.getItems().size() == 0){
            flightAdderErrorText.setText("YOU MUST HAVE AT LEAST 2 POINTS!");
            flightAdderErrorText.setVisible(true);
            return;
        }
        FlightPoint finalPoint = (FlightPoint) flightAdderTable.getItems().get(flightAdderTable.getItems().size() - 1);
        if(finalPoint.getLocalType().equals("APT") && flightAdderTable.getItems().size() > 1) {
            flightAdderErrorText.setVisible(false);
            ArrayList<DataPoint> myFlightData = new ArrayList<>(flightAdderTable.getItems());
            DataBaseRefactor.insertDataPoints(myFlightData, null);
            ArrayList<DataPoint> allPoints = FilterRefactor.getAllPoints(DataTypes.FLIGHT);
            flightTabController.updateFlightsTable(allPoints);
            creatorStage.close();
        } else {
            flightAdderErrorText.setText("FINAL POINT MUST BE OF TYPE APT!");
            flightAdderErrorText.setVisible(true);
        }

    }

    public void addWayPointButtonPressed(){

        String[] flightPoint = getWayPointValues();
        if(flightAdderTable.getItems().size() == 0 && !flightPoint[0].equals("APT")) {
            flightAdderErrorText.setText("FIRST POINT MUST BE OF TYPE APT!");
            flightAdderErrorText.setVisible(true);
        } else {
            if(Validator.checkFlightPoint(flightPoint)) {
                flightAdderErrorText.setVisible(false);
                ArrayList<DataPoint> myFlightPointData = new ArrayList<>();
                DataPoint myFlightPoint = DataPoint.createDataPointFromStringArray(flightPoint, DataTypes.FLIGHTPOINT, 0, null);
                myFlightPointData.add(myFlightPoint);
                updateWayPointTable(myFlightPointData);
            } else {
                flightAdderErrorText.setText("BAD DATA! PLEASE CHECK CONSTRAINTS");
                flightAdderErrorText.setVisible(true);
            }
        }
    }

    private String[] getWayPointValues(){

        String wayPointID = wayPointIDText.getText().toString();
        String type;
        if(wayPointTypeComboBox.getSelectionModel().isEmpty()) {
            type = "";
        } else {
            type = wayPointTypeComboBox.getValue().toString();
        }
        String alt = wayPointAltText.getText().toString();
        String lat = wayPointLatText.getText().toString();
        String lng = wayPointLongText.getText().toString();

        return new String[] {type, wayPointID, alt, lat, lng};
    }

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
     * Initializes the map with the JavaScript
     */
    private void initMap() {
        webEngine = webView.getEngine();
        webEngine.load(getClass().getClassLoader().getResource("map.html").toExternalForm());
    }

    /**
     *Displays a route on the map by way of making a javascript and executing it through the web engine
     * @param newRoute
     */
    private void displayRoute(Route newRoute) {
        try {
            String scriptToExecute = "displayRoute(" + newRoute.toJSONArray() + ");";
            webEngine.executeScript(scriptToExecute);
            flightCreatorMapErrorText.setVisible(false);
        } catch (netscape.javascript.JSException e){
            flightCreatorMapErrorText.setVisible(true);
        }
    }



}
