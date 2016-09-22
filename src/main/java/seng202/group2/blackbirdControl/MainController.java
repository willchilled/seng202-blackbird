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

import static javafx.fxml.FXMLLoader.load;

/**
 * Created by emr65 on 13/08/16.
 */
public class MainController implements Initializable {

    @FXML private MenuBarController menuBarController;
    @FXML private FlightTabController flightTabController;
    @FXML private RouteTabController routeTabController;
    @FXML private AirportTabController airportTabController;
    @FXML private AirlineTabController airlineTabController;

    @FXML private TabPane mainTabPane;


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
        airlineTabController.show();
        airportTabController.show();

        DataBaseRefactor.createTables();
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

}


/*

    @FXML
    private void initialize(){
        //Automatic initialisation when the program starts

        //BBDatabase.createTables(); //COMMENT ME OUT IF YOU WANT PROGRAM TO RUN NORMALL
        //addALLData();              //COMMENT ME OUT IF YOU WANT THE PROGRAM TO RUN NORAMLLY

        airportFilterMenu.setValue(airPortCountryList.get(0));
        airportFilterMenu.setItems(airPortCountryList);
        airlineFilterMenu.setValue(airlineCountryList.get(0));
        airlineFilterMenu.setItems(airlineCountryList);
        airlineActiveMenu.setItems(airlineActiveList);
        airlineActiveMenu.setValue(airlineActiveList.get(0));

        routesFilterBySourceMenu.setValue(getRoutesFilterBySourceList().get(0));
        routesFilterBySourceMenu.setItems(getRoutesFilterBySourceList());
        routesFilterbyDestMenu.setValue(getRoutesFilterbyDestList().get(0));
        routesFilterbyDestMenu.setItems(getRoutesFilterbyDestList());
        routesFilterByStopsMenu.setValue(getRoutesFilterByStopsList().get(0));
        routesFilterByStopsMenu.setItems(getRoutesFilterByStopsList());
        routesFilterbyEquipMenu.setValue(getRoutesFilterbyEquipList().get(0));
        routesFilterbyEquipMenu.setItems(getRoutesFilterbyEquipList());


    */
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
            routePoints = Filter.filterRoutesBySelections(menusPressed, searchQuery);


        }
        else{
            routePoints = getAllRoutePoints();
        }

        updateRoutesTable(routePoints);



    }

}*/



