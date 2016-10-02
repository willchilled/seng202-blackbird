package seng202.group2.blackbirdControl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import seng202.group2.blackbirdModel.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The main controller for the GUI view of the program. Links to the different tab controllers that handle
 * their specific datasets, and provides access for different tabs to communicate with each other if needed.
 *
 * @author Team2
 * @version 2.0
 * @since 19/9/2016
 */
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
     *
     * @param location The location of the project
     * @param resources The additional resources bundle needed to operate the program
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

        int airlineSize = Filter.getAllPoints(DataTypes.AIRLINEPOINT).size();
        int airportSize = Filter.getAllPoints(DataTypes.AIRPORTPOINT).size();
        int routeSize = Filter.getAllPoints(DataTypes.ROUTEPOINT).size();
        int flightSize = Filter.getAllPoints(DataTypes.FLIGHT).size();
        if (airlineSize > 0 || airportSize > 0 || routeSize > 0 || flightSize > 0) {
            show();
            showTables();
            menuBarController.setOpened(true);
            menuBarController.showMenus();
            menuBarController.showMenuHelper();
            if (routeSize > 0) {    //switch to a populated tab
                updateTab(DataTypes.ROUTEPOINT);
            }
            if (airportSize > 0) {
                updateTab(DataTypes.AIRPORTPOINT);
            }
            if (airlineSize > 0) {
                updateTab(DataTypes.AIRLINEPOINT);
            }
            if (flightSize > 0) {
                updateTab(DataTypes.FLIGHT);
            }
        }
    }

    /**
     * Sets up display when new project is selected from the menu bar
     */
    public void show() {
        mainTabPane.setVisible(true);
        openPane.setVisible(false);
        airlineTabController.show();
        airportTabController.show();
        flightTabController.show();
        routeTabController.show();
    }

    /**
     * This method is needed in order to make the error tab accessible to the data tabs, in order for bad
     * data to be received by the error tab.
     *
     * @return The error tab controller
     */
    ErrorTabController getErrorTabController() {
        return errorTabController;
    }

    /**
     * Calls airlineTabController.addAirlineData() to add airline data from file
     */
    void addAirlineData() {
        errorTabController.setAllCorrect(true);
        airlineTabController.addAirlineData();
        errorTabController.showAddingErrorMessage();
    }

    /**
     * Calls airportTabController.addAirportData() to add airport data from file
     */
    void addAirportData() {
        errorTabController.setAllCorrect(true);
        airportTabController.addAirportData();
        errorTabController.showAddingErrorMessage();
    }

    /**
     * Calls flightTabController.addFlightData() to add flight data from file
     */
    void addFlightData() {
        flightTabController.addFlightData();
    }

    /**
     * Calls routeTabController.addRouteData() to add route data from file
     */
    void addRouteData() {
        errorTabController.setAllCorrect(true);
        routeTabController.addRouteData();
        errorTabController.showAddingErrorMessage();
    }

    /**
     * Calls airportTabController.exportAirportData() to export airport table data to a text file
     */
    void exportAirportData() {
        airportTabController.exportAirportData();
    }

    /**
     * Calls airlineTabController.exportAirlineData() to export airline table data to a text file
     */
    void exportAirlineData() {
        airlineTabController.exportAirlineData();
    }

    /**
     * Calls routeTabController.exportRouteData() to export route table data to a text file
     */
    void exportRouteData() {
        routeTabController.exportRouteData();
    }

    /**
     * Calls  flightTabController.exportFlightData() to export flightpoint table data to a text file
     */
    void exportFlightData() {
        //Giver user a warning that it will only export the currently selected flight (the one in the flightpoint table)
        //NEED TO LABEL THE FLIGHT TABLES.
        flightTabController.exportFlightData();

    }

    /**
     * Updates the route table
     */
    void updateRoutes() {
        //GET POINTS
        ArrayList<DataPoint> routePoints = Filter.getAllPoints(DataTypes.ROUTEPOINT);
        routeTabController.updateRoutesTable(routePoints);
    }

    /**
     * Updates the airports table
     */
    void updateAirports() {
        ArrayList<DataPoint> airportPoints = Filter.getAllPoints(DataTypes.AIRPORTPOINT);
        airportTabController.updateAirportsTable(airportPoints);
    }

    /**
     * Changes the current tab. Called when new data is added, with given DataType.
     *
     * @param type The type of data that we want the tab view to switch to
     */
    void updateTab(DataTypes type) {
        switch (type) {
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
    void showTables() {
        routeTabController.routesSeeAllDataButtonPressed();
        airportTabController.airportSeeAllButtonPressed();
        airlineTabController.airlineSeeAllButtonPressed();
        flightTabController.displayFlights();
    }

    /**
     * @return The current tab
     */
    public Tab getCurrentTab() {
        return mainTabPane.getSelectionModel().getSelectedItem();
    }

    /**
     * Helper function to clear the error table of specified type
     * @param errorType The error type we want to clear
     */
    void clearErrors(DataTypes errorType) {
        errorTabController.clearEntries(errorType);
    }

    /**
     * Helper function to disable/enable appropriate menus
     */
    void mainMenuHelper() {
        menuBarController.showMenuHelper();
    }

}



