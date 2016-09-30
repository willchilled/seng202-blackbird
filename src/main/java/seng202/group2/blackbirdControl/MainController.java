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
    @FXML private Tab analysisTab;

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

        //check the existing database for persistent data
        DataBaseRefactor.createTables();    //comment this out for persistent data storage tests
        int airlineSize = FilterRefactor.getAllPoints(DataTypes.AIRLINEPOINT).size();
        int airportSize = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT).size();
        int routeSize = FilterRefactor.getAllPoints(DataTypes.ROUTEPOINT).size();
        int flightSize = FilterRefactor.getAllPoints(DataTypes.FLIGHT).size();
        System.out.println(airlineSize);
        System.out.println(airportSize);
        System.out.println(routeSize);
        System.out.println(flightSize);
        if (airlineSize > 0 || airportSize > 0 || routeSize > 0 || flightSize > 0) {
            show();
            showTables();
            menuBarController.setOpened(true);
            menuBarController.showMenus();

            boolean flightsPresent=false;
            boolean airlinesPresent=false;
            boolean airportsPresent=false;
            boolean routesPresent=false;
            if (flightSize > 0) {
                flightsPresent = true;
                updateTab(DataTypes.FLIGHT);
            }
            if (airlineSize > 0) {
                airlinesPresent = true;
                updateTab(DataTypes.AIRLINEPOINT);
            }
            if (airportSize > 0) {
                airportsPresent = true;
                updateTab(DataTypes.AIRPORTPOINT);
            }
            if (routeSize > 0) {
                routesPresent = true;
                updateTab(DataTypes.ROUTEPOINT);
            }
            menuBarController.exportMenusHelper(airportsPresent, airlinesPresent, routesPresent, flightsPresent);
        }
       /* mainTabPane.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldTab, newTab) -> {
                    if (newTab == analysisTab) {
                        analysisTabController.checkData();
                    }
                });*/
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
        //analysisTabController.setRouteGraphData();
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
        //myStage.close();
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

    public Tab getCurrentTab() {
        Tab currentTab = mainTabPane.getSelectionModel().getSelectedItem();
        return currentTab;
    }

    public void clearErrors(DataTypes errorType) {
        errorTabController.clearEntries(errorType);
    }

    public void singleExportHelper(DataTypes pointType) {
        switch (pointType) {
            case AIRLINEPOINT: menuBarController.exportMenusHelper(false, true, false, false); break;
            case AIRPORTPOINT: menuBarController.exportMenusHelper(true, false, false, false); break;
            case ROUTEPOINT: menuBarController.exportMenusHelper(false, false, true, false); break;
            case FLIGHT: menuBarController.exportMenusHelper(false, false, false, true); break;
            default: return;
        }
    }
}



