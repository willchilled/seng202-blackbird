package seng202.group2.blackbirdControl;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
//import jdk.internal.org.objectweb.asm.tree.analysis.Analyzer;
import seng202.group2.blackbirdModel.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Filter;

import static javafx.fxml.FXMLLoader.load;


public class MainController implements Initializable {

    @FXML private MenuBarController menuBarController;
    @FXML private FlightTabController flightTabController;
    @FXML private RouteTabController routeTabController;
    @FXML private AirportTabController airportTabController;
    @FXML private AirlineTabController airlineTabController;
    @FXML private ErrorTabController errorTabController;
    @FXML private AnalysisTabController analysisTabController;


    @FXML private Tab airlineTab;
    @FXML private Tab routeTab;
    @FXML private Tab flightTab;
    @FXML private Tab airportTab;

    @FXML private TabPane mainTabPane;
    @FXML private GridPane openPane;


    /**
     * Sets up connection between the controllers
     * Calls methods to set the main controller of other controllers to this instance.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuBarController.setMainController(this);
        flightTabController.setMainController(this);
        routeTabController.setMainController(this);
        airportTabController.setMainController(this);
        airlineTabController.setMainController(this);
        errorTabController.setMainController(this);
        errorTabController.setRouteTabController(routeTabController);
        errorTabController.setAirportTabController(airportTabController);
        errorTabController.setAirlineTabController(airlineTabController);
        analysisTabController.setMainController(this);
    }

    /**
     * Sets up display when new project is selected from the menu bar
     */
    public void show(){
        mainTabPane.setVisible(true);
        openPane.setVisible(false);
        airlineTabController.show();
        airportTabController.show();
        flightTabController.show();
        routeTabController.show();

        //I moved the create tables feature to the MenuBarController show()
    }
//
//    public AirlineTabController getAirlineTabController() {
//        return airlineTabController;
//    }

    public ErrorTabController getErrorTabController() {
        //advised by tutor to make this method
        return errorTabController;
    }


    /**
     * Calls airlineTabController.addAirlineData() to add airline data from file
     */
    public void addAirlineData(){
        airlineTabController.addAirlineData();
    }

    /**
     * Calls airportTabController.addAirportData() to add airport data from file
     */
    public void addAirportData() {
        airportTabController.addAirportData();
        analysisTabController.setGraphData();
    }

    /**
     * Calls flightTabController.addFlightData() to add flight data from file
     */
    public void addFlightData() {
        flightTabController.addFlightData();
    }

    /**
     * Calls routeTabController.addRouteData() to add route data from file
     */
    public void addRouteData() {
        routeTabController.addRouteData();
        //analysisTabController.setGraphData();
    }

    /**
     * Calls airportTabController.exportAirportData() to export airport table data to a text file
     */
    public void exportAirportData(){
        airportTabController.exportAirportData();
    }

    /**
     * Calls airlineTabController.exportAirlineData() to export airline table data to a text file
     */
    public void exportAirlineData(){
        airlineTabController.exportAirlineData();
    }

    /**
     * Calls routeTabController.exportRouteData() to export route table data to a text file
     */
    public void exportRouteData(){
        routeTabController.exportRouteData();
    }

    /**
     * Calls  flightTabController.exportFlightData() to export flightpoint table data to a text file
     */
    public void exportFlightData(){
        //Giver user a warning that it will only export the currently selected flight (the one in the flightpoint table)
        //NEED TO LABEL THE FLIGHT TABLES.
        flightTabController.exportFlightData();

    }

    public void updateRoutes() {
        //GET POINTS
        ArrayList<DataPoint> routePoints = FilterRefactor.getAllPoints(DataTypes.ROUTEPOINT);
        routeTabController.updateRoutesTable(routePoints);
    }

    public void updateAirports() {
        //getpoints
        ArrayList<DataPoint> airportPoints = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        airportTabController.updateAirportsTable(airportPoints);
    }

    /**
     * Changes the current tab. Called when new data is added, with given DataType.
     * @param type
     */
    public void updateTab(DataTypes type) {

        switch(type){
            case FLIGHT:
                mainTabPane.getSelectionModel().select(flightTab);
                break;
            case ROUTEPOINT:
                mainTabPane.getSelectionModel().select(routeTab);
                break;
            case AIRPORTPOINT:
                mainTabPane.getSelectionModel().select(airportTab);
                break;
            case AIRLINEPOINT:
                mainTabPane.getSelectionModel().select(airlineTab);
                break;
        }
    }

    /**
     * A method that calls the See All methods
     */
    public void showTables(){
        routeTabController.routesSeeAllDataButtonPressed(null);
        airportTabController.airportSeeAllButtonPressed(null);
        airlineTabController.airlineSeeAllButtonPressed(null);
        flightTabController.displayFlights();
    }


}



