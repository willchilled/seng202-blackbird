package seng202.group2.blackbirdControl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.Database;
import seng202.group2.blackbirdModel.DataTypes;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Optional;

/**
 * Controller for the menu bars of the program. Involves functions for displaying menus at appropriate times and
 * invoking the correct actions in other tabs based off menu selection via the Main Controller.
 *
 * @author Team2
 * @version 1.0
 * @since 21/9/2016
 */
public class MenuBarController {

    private MainController mainController;
    private boolean opened = false;

    @FXML MenuItem addDataMenuButton;

    @FXML MenuItem exportDatabaseButton;
    @FXML MenuItem exportDataMenuButton;
    @FXML MenuItem exportFlightMenuButton;
    @FXML MenuItem exportRouteMenuButton;
    @FXML MenuItem exportAirportMenuButton;
    @FXML MenuItem exportAirlineMenuButton;

    @FXML MenuItem deleteDataMenuButton;
    @FXML MenuItem deleteFlightMenuButton;
    @FXML MenuItem deleteRouteMenuButton;
    @FXML MenuItem deleteAirportMenuButton;
    @FXML MenuItem deleteAirlineMenuButton;

    /**
     * Sets the boolean flag as to whether the project is being opened for the first time or not (the state where
     * the persistent database is empty)
     *
     * @param opened True if persistent data has been loaded up, false otherwise.
     */
    void setOpened(boolean opened) {
        this.opened = opened;
    }

    /**
     * Determines the initial menubars to show.
     */
    public void show() {
        if (!opened) {
            showMenus();
            Database.createTables();
            opened = true;
        } else {
            opened = true;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("New Project");
            alert.setHeaderText("Create new project?");
            alert.setContentText("Warning: this will overwrite any unsaved data.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Database.createTables();
                mainController.showTables();
                showMenuHelper();
                mainController.clearErrors(DataTypes.AIRPORTPOINT);
                mainController.clearErrors(DataTypes.AIRLINEPOINT);
                mainController.clearErrors(DataTypes.ROUTEPOINT);
            }
        }
    }

    /**
     * Enables menu items
     */
    void showMenus() {
        addDataMenuButton.setDisable(false);
        mainController.show();
        exportDataMenuButton.setDisable(false);
        exportDatabaseButton.setDisable(false);
        deleteDataMenuButton.setDisable(false);
    }

    /**
     * Links back to the MainController of the program
     *
     * @param controller The controller for the tab window
     * @see MainController
     */
    void setMainController(MainController controller) {
        this.mainController = controller;
    }

    /**
     * When user chooses to add route data, determines if there is existing route data before adding and shows
     * menu bars appropriately.
     */
    public void addRouteData() {
        showAddOptions(DataTypes.ROUTEPOINT);
        showMenuHelper();
    }

    /**
     * When user chooses to add flight data, determines if there is existing flight data before adding and shows
     * menu bars appropriately.
     */
    public void addFlightData() {
        mainController.addFlightData();
        showMenuHelper();
    }

    /**
     * When user chooses to add airline data, determines if there is existing airline data before adding and shows
     * menu bars appropriately.
     */
    public void addAirlineData() {
        showAddOptions(DataTypes.AIRLINEPOINT);
        showMenuHelper();
    }

    /**
     * When user chooses to add airport data, determines if there is existing airport data before adding and shows
     * menu bars appropriately.
     */
    public void addAirportData() {
        showAddOptions(DataTypes.AIRPORTPOINT);
        showMenuHelper();
    }

    /**
     * Shows alert that allows user to choose to either replace or merge when adding a new data file
     * Calls mainController.addXXXData for each datatype, and Database.clearTable when user chooses to replace
     *
     * @param type The datatype that we are adding to
     */
    private void showAddOptions(DataTypes type) {
        int size = Filter.getAllPoints(type).size();
        if (size > 0) {
            ButtonType mergeButton = new ButtonType("Merge");
            ButtonType replaceButton = new ButtonType("Replace");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Testing", mergeButton, replaceButton, ButtonType.CANCEL);
            alert.setTitle("Replace or Merge New Data");
            alert.setHeaderText("Would you like to merge or replace?");
            alert.setContentText("Warning: replace deletes existing data in the table");
            alert.showAndWait().ifPresent(response -> {
                if (response == mergeButton || response == replaceButton) {
                    if (response == replaceButton) {
                        Database.clearTable(type);
                    }
                    AddDataToController(type);
                }
            });
        } else {
            AddDataToController(type);
        }
    }

    /**
     * Calls the relevant tab that data is being added to, via the main controller.
     * @param type The data type that we are adding to
     */
    private void AddDataToController(DataTypes type) {
        switch (type) {
            case AIRLINEPOINT:
                mainController.addAirlineData();
                break;
            case ROUTEPOINT:
                mainController.addRouteData();
                break;
            case AIRPORTPOINT:
                mainController.addAirportData();
                break;
            //Flights is not checked here, as we do not need to check for merging or overwriting an imported flight file.
        }
    }

    /**
     * Exports the current route data set, via main controller
     */
    public void exportRouteData() {
        mainController.exportRouteData();
    }

    /**
     * Exports the current flight selected, via main controller
     */
    public void exportFlightData() {
        mainController.exportFlightData();
    }

    /**
     * Exports the current airline data set, via main controller
     */
    public void exportAirlineData() {
        mainController.exportAirlineData();
    }

    /**
     * Exports the current airport data set, via main controller
     */
    public void exportAirportData() {
        mainController.exportAirportData();
    }

    /**
     * Saves the current project by exporting the database with its data sets loaded inside.
     *
     * @see Exporter
     */
    public void exportDatabase() {
        Exporter.exportDataBase();
    }

    /**
     * Calls DatabaseRefactor.clearTable()
     * Drops and recreates the table to remove all data
     */
    public void deleteAirlineData() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Airline Data");
        alert.setHeaderText("Delete All Airline Data");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Database.clearTable(DataTypes.AIRLINEPOINT);
            mainController.showTables();
            mainController.updateTab(DataTypes.AIRLINEPOINT);
            mainController.clearErrors(DataTypes.AIRLINEPOINT);
            showMenuHelper();
        }
    }

    /**
     * Calls DatabaseRefactor.clearTable
     * Drops and recreates the table to remove all data
     */
    public void deleteAirportData() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Airport Data");
        alert.setHeaderText("Delete All Airport Data");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Database.clearTable(DataTypes.AIRPORTPOINT);
            mainController.showTables();
            mainController.updateTab(DataTypes.AIRPORTPOINT);
            mainController.clearErrors(DataTypes.AIRPORTPOINT);
            showMenuHelper();
        }
    }

    /**
     * Calls DatabaseRefactor.clearTable
     * Drops and recreates the table to remove all data
     */
    public void deleteRouteData() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Route Data");
        alert.setHeaderText("Delete All Route Data");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Database.clearTable(DataTypes.ROUTEPOINT);
            mainController.showTables();
            mainController.updateTab(DataTypes.ROUTEPOINT);
            mainController.clearErrors(DataTypes.ROUTEPOINT);
            showMenuHelper();
        }
    }

    /**
     * Calls DatabaseRefactor.clearTable
     * Drops and recreates the table to remove all data
     */
    public void deleteFlightData() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Flight Data");
        alert.setHeaderText("Delete All Flight Data");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Database.clearTable(DataTypes.FLIGHT);
            Database.clearTable(DataTypes.FLIGHTPOINT);
            mainController.showTables();
            mainController.updateTab(DataTypes.FLIGHT);
            showMenuHelper();
        }
    }

    /**
     * Assesses the size of the current data sets, to determine which submenus to enable.
     */
    void showMenuHelper() {
        int airlineSize = Filter.getAllPoints(DataTypes.AIRLINEPOINT).size();
        int airportSize = Filter.getAllPoints(DataTypes.AIRPORTPOINT).size();
        int routeSize = Filter.getAllPoints(DataTypes.ROUTEPOINT).size();
        int flightSize = Filter.getAllPoints(DataTypes.FLIGHT).size();
        System.out.println(airlineSize);
        System.out.println(airportSize);
        System.out.println(routeSize);
        System.out.println(flightSize);
        if (routeSize > 0) {
            exportRouteMenuButton.setDisable(false);
            deleteRouteMenuButton.setDisable(false);
        } else {
            exportRouteMenuButton.setDisable(true);
            deleteRouteMenuButton.setDisable(true);
        }

        if (airportSize > 0) {
            exportAirportMenuButton.setDisable(false);
            deleteAirportMenuButton.setDisable(false);
        } else {
            exportAirportMenuButton.setDisable(true);
            deleteAirportMenuButton.setDisable(true);
        }
        if (airlineSize > 0) {
            exportAirlineMenuButton.setDisable(false);
            deleteAirlineMenuButton.setDisable(false);
        } else {
            exportAirlineMenuButton.setDisable(true);
            deleteAirlineMenuButton.setDisable(true);
        }
        if (flightSize > 0) {
            exportFlightMenuButton.setDisable(false);
            deleteFlightMenuButton.setDisable(false);
        } else {
            exportFlightMenuButton.setDisable(true);
            deleteFlightMenuButton.setDisable(true);
        }
    }

    /**
     * A method to load a previous database file
     */
    public void loadDb() {
        File theirDB = HelperFunctions.getFile("Choose a Database", false);
        if (theirDB == null) {
            return;
        }
        String cwd = System.getProperty("user.dir");
        String dbFileName = (cwd + "/default.db");
        try {
            FileChannel src = new FileInputStream(theirDB).getChannel();
            FileChannel dest = new FileOutputStream(dbFileName).getChannel();
            dest.transferFrom(src, 0, src.size());
            databaseCorrector();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainController.show();
        showMenuHelper();
        showMenus();
        mainController.showTables();
    }

    /**
     * Checks if tables in database are in the correct format to be loaded into the program. If any are incorrect, reject
     * database and instead create a new project for the user.
     */
    private void databaseCorrector() {
        //check flights in loaded database
        String[] flightTableColumns = {"FlightIDNum", "SrcICAO", "DstICAO"};
        String[] flightPointTableColumns = {"SeqOrder", "LocaleID", "LocationType", "Altitude", "Latitude", "Longitude", "FlightIDNum"};
        if (!(Validator.tableColumnchecker("FLIGHT", flightTableColumns))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in loading File");
            alert.setHeaderText("There was a problem with the flight data.");
            alert.setContentText("It has been removed, this change has not been saved.");
            alert.showAndWait();
            Database.clearTable(DataTypes.FLIGHT);
            Database.clearTable(DataTypes.FLIGHTPOINT);
            Database.createTables();
        } else if (!(Validator.tableColumnchecker("FLIGHTPOINT", flightPointTableColumns))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in loading File");
            alert.setHeaderText("There was a problem with the flight data.");
            alert.setContentText("It has been removed, this change has not been saved.");
            alert.showAndWait();
            Database.clearTable(DataTypes.FLIGHTPOINT);
            Database.clearTable(DataTypes.FLIGHT);
            Database.createTables();
        }

        //check routes in loaded database
        String[] routeTableColumns = {"IDnum", "Airline", "Airlineid", "Src", "Srcid", "Dst", "Dstid", "Codeshare", "Stops", "Equipment"};
        if (!(Validator.tableColumnchecker("ROUTE", routeTableColumns))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in loading File");
            alert.setHeaderText("There was a problem with the route data.");
            alert.setContentText("It has been removed, this change has not been saved.");
            alert.showAndWait();
            Database.clearTable(DataTypes.ROUTEPOINT);
            Database.createTables();
        }

        //check airlines in loaded database
        String[] airlineTableColumns = {"ID", "NAME", "ALIAS", "IATA", "ICAO", "CALLSIGN", "COUNTRY", "ACTIVE"};
        if (!(Validator.tableColumnchecker("AIRLINE", airlineTableColumns))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in loading File");
            alert.setHeaderText("There was a problem with the airline data.");
            alert.setContentText("It has been removed, this change has not been saved.");
            alert.showAndWait();
            Database.clearTable(DataTypes.AIRLINEPOINT);
            Database.createTables();
        }

        //check airports in loaded database
        String[] airportTableColumns = {"ID", "NAME", "CITY", "COUNTRY", "IATA", "ICAO", "LATITUDE", "LONGITUDE", "ALTITUDE", "TIMEZONE", "DST", "TZ"};
        if (!(Validator.tableColumnchecker("AIRPORT", airportTableColumns))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in loading File");
            alert.setHeaderText("There was a problem with the airport data.");
            alert.setContentText("It has been removed, this change has not been saved.");
            alert.showAndWait();
            Database.clearTable(DataTypes.AIRPORTPOINT);
            Database.createTables();
        }
    }

    /**
     * Opens the user manual in a pop up
     */
    public void openHelp() {
        Stage stage;
        Parent root;
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HelpPopUp.fxml"));
            root = loader.load();
            HelpViewerController popUpController = loader.getController();
            popUpController.setStage(stage);
            popUpController.setRoot(root);
            popUpController.start();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
