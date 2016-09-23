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
import java.util.ResourceBundle;
import java.util.logging.Filter;

import static javafx.fxml.FXMLLoader.load;


public class MainController implements Initializable {

    @FXML private MenuBarController menuBarController;
    @FXML private FlightTabController flightTabController;
    @FXML private RouteTabController routeTabController;
    @FXML private AirportTabController airportTabController;
    @FXML private AirlineTabController airlineTabController;


    @FXML private Tab airlineTab;
    @FXML private Tab routeTab;
    @FXML private Tab flightTab;
    @FXML private Tab airportTab;

    @FXML private TabPane mainTabPane;
    @FXML private GridPane openPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuBarController.setMainController(this);
        flightTabController.setMainController(this);
        routeTabController.setMainController(this);
        airportTabController.setMainController(this);
        airlineTabController.setMainController(this);

    }

    public void show(){
        mainTabPane.setVisible(true);
        openPane.setVisible(false);
        airlineTabController.show();
        airportTabController.show();
        flightTabController.show();
        routeTabController.show();

        //I moved the create tables feature to the menu bar controller
    }

    public void addAirlineData(){
        airlineTabController.addAirlineData();
    }

    public void addAirportData() {
        airportTabController.addAirportData();
    }

    public void addFlightData() {
        flightTabController.addFlightData();
    }

    public void addRouteData() {
        routeTabController.addRouteData();
    }

    public void exportAirportData(){
        airportTabController.exportAirportData();
    }

    public void exportAirlineData(){
        airlineTabController.exportAirlineData();
    }

    public void exportRouteData(){
        routeTabController.exportRouteData();
    }

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

/*    public void updateTab(DataTypes type) {
        mainTabPane.getSelectionModel().select(airportTab);


    }*/
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


}



/*******************************************************************************************************************
     *******************************************************************************************************************
     *************************************** FILTERING BUTTONS**********************************************************
     *******************************************************************************************************************
     ******************************************************************************************************************//*


    public void routesFilterButtonPressed(ActionEvent actionEvent) {
        System.out.println("I HAVE BEEN PRESSED");
        String sourceSelection = routesFilterBySourceMenu.getValue().toString();
        String destSelection = routesFilterbyDestMenu.getValue().toString();
        String stopsSelection = routesFilterByStopsMenu.getValue().toString();
        String equipSelection = routesFilterbyEquipMenu.getValue().toString();
        String searchQuery = routesSearchMenu.getText().toString();
        ArrayList<RoutePoint> routePoints = new ArrayList<>();


        ArrayList<String> menusPressed = new ArrayList<>(Arrays.asList(sourceSelection, destSelection, stopsSelection, equipSelection));
        boolean allNone = true;

        for (String menuItem: menusPressed){
            if (!menuItem.equals("None")){
                allNone = false;
            }
        }
        if (!searchQuery.equals("")){
            allNone = false;

        }
        if (!allNone){
            routePoints = *****DEPRECATED --->> Filter*****.filterRoutesBySelections(menusPressed, searchQuery);


        }
        else{
            routePoints = getAllRoutePoints();
        }

        updateRoutesTable(routePoints);



    }

}*/



