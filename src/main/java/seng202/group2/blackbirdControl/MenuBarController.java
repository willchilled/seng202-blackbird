package seng202.group2.blackbirdControl;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import seng202.group2.blackbirdModel.DataBaseRefactor;
import seng202.group2.blackbirdModel.DataTypes;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Optional;

/**
 * Created by emr65 on 20/09/16.
 */
public class MenuBarController {

    private MainController mainController;
    private boolean opened = false;

    @FXML
    MenuItem addDataMenuButton;
    @FXML
    MenuItem exportDataMenuButton;
    @FXML
    MenuItem exportDatabaseButton;
    @FXML
    MenuItem exportFlightMenuButton;
    @FXML
    MenuItem exportRouteMenuButton;
    @FXML
    MenuItem exportAirportMenuButton;
    @FXML
    MenuItem exportAirlineMenuButton;


    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public void show() {
//        int size1 = FilterRefactor.getAllPoints(DataTypes.AIRLINEPOINT).size();
//        int size2 = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT).size();
//        int size3 = FilterRefactor.getAllPoints(DataTypes.ROUTEPOINT).size();
//        int size4 = FilterRefactor.getAllPoints(DataTypes.FLIGHT).size();
//        System.out.println(size1);
//        System.out.println(size2);
//        System.out.println(size3);
//        System.out.println(size4);
//        if (size1 == 0 && size2 == 0 && size3 == 0 && size4 == 0) {
//            showMenus();
//            DataBaseRefactor.createTables();
//            opened = true;
//        } else {
        if (opened == false) {
            showMenus();
            DataBaseRefactor.createTables();
            opened = true;
        } else {
            opened = true;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("New Project");
            alert.setHeaderText("Create new project?");
            alert.setContentText("Warning: this will overwrite any unsaved data.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                DataBaseRefactor.createTables();
                mainController.showTables();
                //TODO get the errors table to clear here
            }
        }
    }

    public void showMenus() {
        addDataMenuButton.setDisable(false);
        mainController.show();
        exportDataMenuButton.setDisable(false);
        exportDataMenuButton.setDisable(false);
        exportDatabaseButton.setDisable(false);
    }

    /**
     * A method to load a previous database file and correct it
     */
    public void loadDb(){
        //get their file
        File theirDB = HelperFunctions.getFile("Choose a Database", false);
        if (theirDB== null) {
            return;
        }
        //get the default database
        String cwd = System.getProperty("user.dir");
        String dbFileName = (cwd+"/default.db");
        try {
            FileChannel src = new FileInputStream(theirDB).getChannel();
            FileChannel dest = new FileOutputStream(dbFileName).getChannel();
            dest.transferFrom(src, 0, src.size());
            databaseCorrector();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        exportRouteMenuButton.setDisable(false);
        exportFlightMenuButton.setDisable(false);
        exportAirlineMenuButton.setDisable(false);
        exportAirportMenuButton.setDisable(false);
        mainController.show();
        addDataMenuButton.setDisable(false);
        exportDatabaseButton.setDisable(false);
        mainController.showTables();
    }

    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    /**
     * Calls mainController.addRouteData()
     * Makes the export for route data usable
     */
    public void addRouteData() {
        showAddOptions(DataTypes.ROUTEPOINT);
        exportRouteMenuButton.setDisable(false); //make export possible
    }

    /**
     * Calls mainController.addFlightData()
     * Makes the export for flight data usable
     */
    public void addFlightData() {
        mainController.addFlightData();
        exportFlightMenuButton.setDisable(false); //make export possible
    }

    /**
     * Calls mainController.addAirlineData()
     * Makes the export for airline data usable
     */
    public void addAirlineData() {
        showAddOptions(DataTypes.AIRLINEPOINT);
        exportAirlineMenuButton.setDisable(false); //make export possible
    }

    public void exportMenusHelper(boolean airport, boolean airline, boolean route, boolean flight) {
        if (airport) {
            exportAirportMenuButton.setDisable(false);
        }
        if (airline) {
            exportAirlineMenuButton.setDisable(false);
        }
        if (route) {
            exportRouteMenuButton.setDisable(false);
        }
        if (flight) {
            exportFlightMenuButton.setDisable(false);
        }
    }

    /**
     * Calls mainController.addAirportData()
     * Makes the export for airport data usable
     */
    public void addAirportData() {
        showAddOptions(DataTypes.AIRPORTPOINT);
        exportAirportMenuButton.setDisable(false); //make export possible
    }

    /**
     * Calls mainController.exportRouteData();
     */
    public void exportRouteData() {
        mainController.exportRouteData();
    }

    /**
     * Calls mainController.exportFlightData();
     */
    public void exportFlightData() {
        mainController.exportFlightData();
    }

    /**
     * Calls mainController.exportAirlineData();
     */
    public void exportAirlineData() {
        mainController.exportAirlineData();
    }

    /**
     * Calls mainController.exportAirportData();
     */
    public void exportAirportData() {
        mainController.exportAirportData();
    }

    /**
     * Calls Exporter.exportDatabase()
     */
    public void exportDatabase(){
        Exporter.exportDataBase();
    }

    /**
     * Calls DatabaseRefactor.clearTable()
     * Drops and recreates the table to remove all data
     */
    public void deleteAirlineData(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Airline Data");
        alert.setHeaderText("Delete All Airline Data");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            DataBaseRefactor.clearTable(DataTypes.AIRLINEPOINT);
            mainController.showTables();
            mainController.updateTab(DataTypes.AIRLINEPOINT);
            //airlineFilterButtonPressed();
            //TODO find a way to refresh tables here;
        }
    }

    /**
     * Calls DatabaseRefactor.clearTable
     * Drops and recreates the table to remove all data
     */
    public void deleteAirportData(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Airport Data");
        alert.setHeaderText("Delete All Airport Data");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            DataBaseRefactor.clearTable(DataTypes.AIRPORTPOINT);
            mainController.showTables();
            mainController.updateTab(DataTypes.AIRPORTPOINT);
        }
    }

    /**
     * Calls DatabaseRefactor.clearTable
     * Drops and recreates the table to remove all data
     */
    public void deleteRouteData(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Route Data");
        alert.setHeaderText("Delete All Route Data");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            DataBaseRefactor.clearTable(DataTypes.ROUTEPOINT);
            mainController.showTables();
            mainController.updateTab(DataTypes.ROUTEPOINT);
        }
    }

    //TODO adding a delete flights menu button?


    /**
     * Shows alert that allows user to choose to either replace or merge when adding a new data file
     * Calls mainController.addXXXData for each datatype, and DataBaseRefactor.clearTable when user chooses to replace
     * @param type
     */
    public void showAddOptions(DataTypes type) {
        //IF TABLE NOT EMPTY
        int size = FilterRefactor.getAllPoints(type).size();
        if (size > 0) {
            ButtonType mergeButton = new ButtonType("Merge");
            ButtonType replaceButton = new ButtonType("Replace");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Testing", mergeButton, replaceButton, ButtonType.CANCEL);
            alert.setTitle("Replace or Merge New Data");
            alert.setHeaderText("Would you like to merge or replace?");
            alert.setContentText("Warning: replace deletes existing data in the table");
            //TODO clean up this so same switch isn't used twice. Any suggestions greatly appreciated
            alert.showAndWait().ifPresent(response -> {
                if (response == mergeButton || response == replaceButton) {
                    if (response == replaceButton) {
                        DataBaseRefactor.clearTable(type);
                    }
                    AddDataToController(type);

                }
            });
        } else {
                AddDataToController(type);
        }
    }


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
        }
    }

    /**
     * Checks if tables in database are in the correct format and correct ones that are not
     */
    private void databaseCorrector(){
        //check flight table
        String[] flightTableColumns = {"FlightIDNum", "SrcICAO", "DstICAO"};
        String[] flightPointTableColumns = {"SeqOrder", "LocaleID", "LocationType", "Altitude", "Latitude", "Longitude", "FlightIDNum"};
        if (!(Validator.tableColumnchecker("FLIGHT", flightTableColumns))) {
            //Give Alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in loading File");
            alert.setHeaderText("There was a problem with the flight data.");
            alert.setContentText("It has been removed, this change has not been saved.");
            alert.showAndWait();
            //Must clear both flight and flightpoint table
            DataBaseRefactor.clearTable(DataTypes.FLIGHT);//correct if needed
            DataBaseRefactor.clearTable(DataTypes.FLIGHTPOINT);//correct if needed
        } else if (!(Validator.tableColumnchecker("FLIGHTPOINT", flightPointTableColumns))) { //check flight point table
            //Give Alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in loading File");
            alert.setHeaderText("There was a problem with the flight data.");
            alert.setContentText("It has been removed, this change has not been saved.");
            alert.showAndWait();
            //Must clear both flight and flightpoint table
            DataBaseRefactor.clearTable(DataTypes.FLIGHTPOINT);//correct if needed
            DataBaseRefactor.clearTable(DataTypes.FLIGHT);//correct if needed
        }
        //check route table
        String[] routeTableColumns = {"IDnum", "Airline", "Airlineid", "Src", "Srcid", "Dst", "Dstid", "Codeshare", "Stops", "Equipment"};
        if (!(Validator.tableColumnchecker("ROUTE", routeTableColumns))) {
            //Give Alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in loading File");
            alert.setHeaderText("There was a problem with the route data.");
            alert.setContentText("It has been removed, this change has not been saved.");
            alert.showAndWait();
            DataBaseRefactor.clearTable(DataTypes.ROUTEPOINT);//correct if needed
        }
        //check airline table
        String[] airlineTableColumns = {"ID", "NAME", "ALIAS", "IATA", "ICAO", "CALLSIGN", "COUNTRY", "ACTIVE"};
        if (!(Validator.tableColumnchecker("AIRLINE", airlineTableColumns))) {
            //Give Alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in loading File");
            alert.setHeaderText("There was a problem with the airline data.");
            alert.setContentText("It has been removed, this change has not been saved.");
            alert.showAndWait();
            DataBaseRefactor.clearTable(DataTypes.AIRLINEPOINT);//correct if needed
        }
        //check airport table
        String[] airportTableColumns = {"ID", "NAME", "CITY", "COUNTRY", "IATA", "ICAO", "LATITUDE", "LONGITUDE", "ALTITUDE", "TIMEZONE", "DST", "TZ"};
        if (!(Validator.tableColumnchecker("AIRPORT", airportTableColumns))) {
            //Give Alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in loading File");
            alert.setHeaderText("There was a problem with the airport data.");
            alert.setContentText("It has been removed, this change has not been saved.");
            alert.showAndWait();
            DataBaseRefactor.clearTable(DataTypes.AIRPORTPOINT);//correct if needed
        }
    }

}
