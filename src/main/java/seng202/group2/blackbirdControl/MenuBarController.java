package seng202.group2.blackbirdControl;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import seng202.group2.blackbirdModel.DataBaseRefactor;
import seng202.group2.blackbirdModel.DataTypes;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * Created by emr65 on 20/09/16.
 */
public class MenuBarController {

    private MainController mainController;


    @FXML
    MenuItem addDataMenuButton;
    //export menu stuff
    @FXML
    MenuItem exportDataMenuButton;
    @FXML
    MenuItem exportDatabase;
    @FXML
    MenuItem exportFlightMenuButton;
    @FXML
    MenuItem exportRouteMenuButton;
    @FXML
    MenuItem exportAirportMenuButton;
    @FXML
    MenuItem exportAirlineMenuButton;


    public void show(){
        addDataMenuButton.setDisable(false);
        mainController.show();
        exportDataMenuButton.setDisable(false);
        exportDataMenuButton.setDisable(false);
        exportDatabase.setDisable(false);
        //
        DataBaseRefactor.createTables();
    }

    /**
     * A method to load a previous database file
     */
    public void loadDb(){
        //get their file
        File theirDB = HelperFunctions.getFile("Choose a Database");
        if (theirDB== null) {
            System.out.println("No File was loaded");
            return;
        }
        //get the default database
        String cwd = System.getProperty("user.dir");
        String dbFileName = (cwd+"/default.db");
        try {
            FileChannel src = new FileInputStream(theirDB).getChannel();
            FileChannel dest = new FileOutputStream(dbFileName).getChannel();
            dest.transferFrom(src, 0, src.size());
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
        DataBaseRefactor.clearTable(DataTypes.AIRLINEPOINT);
    }

    /**
     * Calls DatabaseRefactor.clearTable
     * Drops and recreates the table to remove all data
     */
    public void deleteAirportData(){
        DataBaseRefactor.clearTable(DataTypes.AIRPORTPOINT);
    }

    /**
     * Calls DatabaseRefactor.clearTable
     * Drops and recreates the table to remove all data
     */
    public void deleteRouteData(){
        DataBaseRefactor.clearTable(DataTypes.ROUTEPOINT);
    }


    /**
     * Shows alert that allows user to choose to either replace or merge when adding a new data file
     * Calls mainController.addXXXData for each datatype, and DataBaseRefactor.clearTable when user chooses to replace
     * @param type
     */
    public void showAddOptions(DataTypes type){

        ButtonType mergeButton = new ButtonType("Merge");
        ButtonType replaceButton = new ButtonType("Replace");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Testing", mergeButton, replaceButton, ButtonType.CANCEL);
        alert.setTitle("Replace or Merge New Data");
        alert.setHeaderText("Would you like to merge or replace?");
        alert.setContentText("Warning: replace deletes existing data in the table");
        alert.showAndWait().ifPresent(response -> {
            if(response == mergeButton || response == replaceButton){
                if(response == replaceButton){
                    DataBaseRefactor.clearTable(type);
                }
                switch(type){
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
        });

    }





}
