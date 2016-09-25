package seng202.group2.blackbirdControl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import seng202.group2.blackbirdModel.DataBaseRefactor;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ResourceBundle;

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
        DataBaseRefactor.createTables();
    }

    /**
     * A method to load a previous database file and correct it
     */
    public void loadDb(){
        //get thier file
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
    }

    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    /**
     * Calls mainController.addRouteData()
     * Makes the export for route data usable
     */
    public void addRouteData() {
        mainController.addRouteData();
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
        mainController.addAirlineData();
        exportAirlineMenuButton.setDisable(false); //make export possible
    }

    /**
     * Calls mainController.addAirportData()
     * Makes the export for airport data usable
     */
    public void addAirportData() {
        mainController.addAirportData();
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
     * Calls Exporter.exportDatabase
     */
    public void exportDatabase(){
        Exporter.exportDataBase();
    }

    private void databaseCorrector(){
        boolean flighTableCorrect = Validator.flightTableChecker();
        boolean flightPointTableCorrect = Validator.flightPointTableChecker();

    }
}
