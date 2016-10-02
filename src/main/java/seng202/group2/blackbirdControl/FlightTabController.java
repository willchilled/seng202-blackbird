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

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Filter;

/**
 * Created by emr65 on 20/09/16.
 */
public class FlightTabController {

    private MainController mainController;
    private FlightTabController instance;

    private Flight flight;
    private FlightPoint flightPoint;

    //MapShit
    @FXML private WebView webView;
    private WebEngine webEngine;

    //FLIGHT and FLIGHT POINT tables
    @FXML private TableView<DataPoint> flightTable;
    @FXML private TableView<DataPoint> flightPointTable;

    //FLIGHT Table columns
    @FXML private TableColumn flightSourceCol;
    @FXML private TableColumn flightDestCol;

    //FLIGHT POINT Table columns
    @FXML private TableColumn flightPointTypeCol;
    @FXML private TableColumn flightPointLocaleCol;
    @FXML private TableColumn flightPointAltitudeCol;
    @FXML private TableColumn flightPointLatitudeCol;
    @FXML private TableColumn flightPointLongitudeCol;

    @FXML private ComboBox flightSrcICAOMenu;
    @FXML private ComboBox flightDstICAOMenu;
    @FXML private TextField flightSearchQuery;

    @FXML private Text flightTabMapErrorText;

    ObservableList<String> flightSrcICAOList = FXCollections.observableArrayList("No values Loaded");
    ObservableList<String> flightDstICAOList  = FXCollections.observableArrayList("No values Loaded");



    public FlightTabController() {
        instance = this;
    }

    public void show(){
        //mainController.show();
        flightTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Flight"));
        flightPointTable.setPlaceholder(new Label("No data in table. Double click on a flight"));
    }

    public void initialize(){
        flightSrcICAOMenu.setValue(flightSrcICAOList.get(0));
        flightSrcICAOMenu.setItems(flightSrcICAOList);

        flightDstICAOMenu.setValue(flightDstICAOList.get(0));
        flightDstICAOMenu.setItems(flightDstICAOList);
        initMap();
    }




    public void setMainController(MainController controller) {
        this.mainController = controller;
    }


    /**
     * Called upon clicking the Add Flight Data option in the menu bar. Adds a specified CSV file of a flight to the
     * database and then updates the table
     */
    public void addFlightData() {
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
        ArrayList<DataPoint> myFlights = FilterRefactor.getAllPoints(DataTypes.FLIGHT);

        //TODO prevent a flight that doesn't begin/end in APT from being added to database, with error message

        updateFlightFields();
        updateFlightsTable(myFlights);
        mainController.updateTab(DataTypes.FLIGHT);
    }

    /**
     * A method to display the flights
     */
    public void displayFlights(){
        ArrayList<DataPoint> myFlights = FilterRefactor.getAllPoints(DataTypes.FLIGHT);

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


        flightDstICAOList = populateFlightDstList();  //populating from valid data in database
        flightDstICAOMenu.setItems(flightDstICAOList);
        flightDstICAOMenu.setValue(flightDstICAOList.get(0));
    }

    /**
     * Populates the SrcICAOs dropdown upon adding data.
     * @return The list of current SrcICAOs in the database for flights
     */
    private ObservableList<String> populateFlightSrcList(){
        ArrayList<String> srcICAOs = FilterRefactor.filterDistinct("SrcICAO", "FLIGHT");
        ObservableList<String> srcICAOList = FXCollections.observableArrayList(srcICAOs);
        srcICAOList = HelperFunctions.addNullValue(srcICAOList); //we need to add a null value
        return srcICAOList;
    }

    /**
     * Populates the DstICAO dropdown upon adding data.
     * @return The list of current DstICAOs in the database for flights
     */
    private ObservableList<String> populateFlightDstList(){
        ArrayList<String> dstICAOs = FilterRefactor.filterDistinct("DstICAO", "FLIGHT");
        ObservableList<String> dstICAOList = FXCollections.observableArrayList(dstICAOs);
        dstICAOList = HelperFunctions.addNullValue(dstICAOList); //we need to add a null value
        return dstICAOList;
    }


    /**
     * Called upon pressing the 'Filter' button in the flights tab. Grabs all info from the filter selections and sends
     * it to the Filter class to create a search query to obtain all flight points. Updates the flight table with these
     * points.
     */
    public void flightFilterButtonPressed() {
        //TODO this might be why delete flight isn't working
        String srcAirport = flightSrcICAOMenu.getValue().toString();
        String dstAirport = flightDstICAOMenu.getValue().toString();
        String searchQuery = flightSearchQuery.getText().toString();
        ArrayList<DataPoint> allPoints;

        if(srcAirport.equals("No values loaded") && dstAirport.equals("No values loaded")){
            allPoints = FilterRefactor.getAllPoints(DataTypes.FLIGHT);
        }
        else {
            ArrayList<String> menusPressed = new ArrayList<>();
            menusPressed.add(srcAirport);
            menusPressed.add(dstAirport);

            //Returns selected Flights !NOT FLIGHT POINTS!//
            allPoints = FilterRefactor.filterSelections(menusPressed, searchQuery, DataTypes.FLIGHT);
        }
        updateFlightsTable(allPoints);


    }

    /**
     * Called upon pressing the 'Add Flight' button in Flights. Opens the flight creator
     * @param actionEvent Mouse Click
     */
    public void flightMakeFlightButtonPressed(ActionEvent actionEvent) {

        //Brings up popup to create a flight
        try {
            Stage creatorStage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FlightCreatorPopUp.fxml"));
            root = loader.load();

            //use controller to control it
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

    public void enterPressed(KeyEvent ke)
    {
        if(ke.getCode() == KeyCode.ENTER)
        {
            flightFilterButtonPressed();
        }
    }

    /**
     * Updates the filght tables upon being called. Also contains a nested function to update the flightPoint table
     * upon clicking a row
     * @param filteredFlights A list of the flights with which to update the tables
     */
    public void updateFlightsTable(ArrayList<DataPoint> filteredFlights) {

        flightPointTable.getItems().setAll();
        flightTable.getItems().setAll(filteredFlights);
        flightSourceCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("srcAirport"));
        flightDestCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("destAirport"));

        flightTable.getItems().addListener(new ListChangeListener<DataPoint>() {
            //This refreshes the current table
            @Override
            public void onChanged(Change<? extends DataPoint> c) {
                flightTable.getColumns().get(0).setVisible(false);
                flightTable.getColumns().get(0).setVisible(true);
            }
        });

        flightTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    //exportFlightMenuButton.setDisable(false);

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

            }
        });

        flightPointTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 1) {

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
            }
        });

    }


    /**
     * Exports the current flightPoint data showing into a CSV file of the users chosing.
     */
    public void exportFlightData() {
        //Giver user a warning that it will only export the currently selected flight (the one in the flightpoint table)
        //NEED TO LABEL THE FLIGHT TABLES.

        ArrayList<DataPoint> myPoints = new ArrayList<DataPoint>(flightPointTable.getItems());
        Exporter.exportData(myPoints);
    }

    public void deleteSingleFlight(){
        //TODO delete single flight isn't working, I think the id is always 0 <- delete seems to work now, but needs to clear waypoints also
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
        String flightSql = "";
        String flightPointSql = "";
        int id = flight.getFlightID();
        flightSql = String.format("DELETE FROM FLIGHT WHERE FlightIDNum = %s", id);
        flightPointSql = String.format("DELETE FROM FLIGHTPOINT WHERE FlightIDNum = %s", id);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Entry");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();

        if((result.isPresent()) && (result.get() == ButtonType.OK)) {
            DataBaseRefactor.editDataEntry(flightSql);
            DataBaseRefactor.editDataEntry(flightPointSql);
            flightFilterButtonPressed();
            mainController.mainMenuHelper();
        }
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
            flightTabMapErrorText.setVisible(false);
        } catch (netscape.javascript.JSException e){
            flightTabMapErrorText.setVisible(true);
        }
    }

    private void displayWayPoint(Route newWayPoint) {
        try{
            String scriptToExecute = "displayWayPoint(" + newWayPoint.toJSONArray() + ")";
            webEngine.executeScript(scriptToExecute);
            flightTabMapErrorText.setVisible(false);
        } catch (netscape.javascript.JSException e){
            flightTabMapErrorText.setVisible(true);
        }
    }

    public void flightseeAllButtonPressed(ActionEvent actionEvent) {

        ArrayList<DataPoint> allPoints;
        allPoints = FilterRefactor.getAllPoints(DataTypes.FLIGHT);
        updateFlightsTable(allPoints);

    }
}
