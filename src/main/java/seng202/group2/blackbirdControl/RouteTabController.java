package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by emr65 on 20/09/16.
 */
public class RouteTabController {

    private MainController mainController;

    //ROUTE table
    @FXML private TableView<DataPoint> routeTable;

    //Route Table columns
    @FXML private TableColumn routeAirlineCol;
    @FXML private TableColumn routeAirlineIDCol;
    @FXML private TableColumn routeSrcCol;
    @FXML private TableColumn routeSrcIDCol;
    @FXML private TableColumn routeDestCol;
    @FXML private TableColumn routeDestIDCol;
    @FXML private TableColumn routeCSCol;
    @FXML private TableColumn routeStopsCol;
    @FXML private TableColumn routeEqCol;
    @FXML private TableColumn routeErrorCol;

    @FXML private ComboBox routesFilterBySourceMenu;
    @FXML private ComboBox routesFilterbyDestMenu;
    @FXML private ComboBox routesFilterByStopsMenu;
    @FXML private ComboBox routesFilterbyEquipMenu;
    @FXML private TextField routesSearchMenu;

    public void show(){
        routeTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Route"));
    }


    public void setMainController(MainController controller) {
        this.mainController = controller;
    }


    public void addSingleRoute(ActionEvent actionEvent) {
        try {
            Stage adderStage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RouteAddingPopUp.fxml"));
            root = loader.load();

            RouteAddingPopUpController popUpController = loader.getController();
            popUpController.setAdderStage(adderStage);
            popUpController.setRoot(root);
            popUpController.control();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void routesFilterButtonPressed(ActionEvent actionEvent) {
        String sourceSelection = routesFilterBySourceMenu.getValue().toString();
        String destSelection = routesFilterbyDestMenu.getValue().toString();
        String stopsSelection = routesFilterByStopsMenu.getValue().toString();
        //TODO fix this when the correct stuff is linked to the GUI
        String equipSelection = routesFilterbyEquipMenu.getValue().toString();
        String searchQuery = routesSearchMenu.getText().toString();
        ArrayList<DataPoint> routePoints;




        if(sourceSelection.equals("No values Loaded") && destSelection.equals("No values Loaded") && stopsSelection.equals("No values Loaded") && equipSelection.equals("No values Loaded")){
            routePoints = FilterRefactor.getAllPoints(DataTypes.ROUTEPOINT);
            updateRoutesTable(routePoints);
        } else {
            ArrayList<String> menusPressed = new ArrayList<>(Arrays.asList(sourceSelection, destSelection, stopsSelection, equipSelection));
            routePoints = FilterRefactor.filterSelections(menusPressed, searchQuery,DataTypes.ROUTEPOINT);
        }

//        for (DataPoint myRoute: routePoints){
//            RoutePoint currentRoute = (RoutePoint) myRoute;
//            System.out.println(currentRoute.toStringWithAirports());
//        }


        //This is bad style but you win some and you lose some
        //(I lost this one)
//        ArrayList<String> uniqueCountries = BBDatabase.performDistinctStringQuery("SELECT DISTINCT Src FROM ROUTE");
//        ObservableList<String> myCountries =  FXCollections.observableArrayList(uniqueCountries);
//        myCountries = HelperFunctions.addNullValue(myCountries);
//        routesFilterBySourceMenu.setValue(myCountries.get(0));
//        routesFilterBySourceMenu.setItems(myCountries);
//
//        ArrayList<String> dstCodes = BBDatabase.performDistinctStringQuery("SELECT DISTINCT Dst FROM ROUTE");
//        ObservableList<String> myDstCodes =  FXCollections.observableArrayList(dstCodes);
//        myDstCodes = HelperFunctions.addNullValue(myDstCodes);
//        routesFilterbyDestMenu.setValue(myDstCodes.get(0));
//        routesFilterbyDestMenu.setItems(myDstCodes);
//
//        ArrayList<String> stops = BBDatabase.performDistinctStringQuery("SELECT DISTINCT Stops FROM ROUTE");
//        ObservableList<String> myStops =  FXCollections.observableArrayList(stops);
//        myStops = HelperFunctions.addNullValue(myStops);
//        routesFilterByStopsMenu.setValue(myStops.get(0));
//        routesFilterByStopsMenu.setItems(myStops);
//
//        ArrayList<String> equip = BBDatabase.performDistinctStringQuery("SELECT DISTINCT equipment FROM ROUTE");
//        ObservableList<String> myEquip =  FXCollections.observableArrayList(equip);
//        myEquip= HelperFunctions.addNullValue(myEquip);
//        routesFilterbyEquipMenu.setValue(myEquip.get(0));
//        routesFilterbyEquipMenu.setItems(myEquip);
        // ArrayList<String>
        //updateRoutesDropdowns();

        updateRoutesTable(routePoints);
    }

    public void routesSeeAllDataButtonPressed(ActionEvent actionEvent) {
        ArrayList<DataPoint> allPoints = FilterRefactor.getAllPoints(DataTypes.ROUTEPOINT); //airportTable.getItems()
        updateRoutesDropdowns();    //TODO better way of clearing filters after pressing see all

        updateRoutesTable(allPoints);

    }

    public void addRouteData() {
        
        File f = HelperFunctions.getFile("Add Route Data");
        if (f == null) {
            return;
        }

        ArrayList<DataPoint> myRouteData = ParserRefactor.parseFile(f, DataTypes.ROUTEPOINT);
        DataBaseRefactor.insertDataPoints(myRouteData);

        //WAITING ON METHOD TO GET ROUTES BACK FROM DB
        ArrayList<DataPoint> validRouteData = FilterRefactor.getAllPoints(DataTypes.ROUTEPOINT);
        //setAllRoutePoints(myRouteData); //populating local data with all points
        updateRoutesTable(myRouteData);
        updateRoutesDropdowns();

    }

    private void updateRoutesTable(ArrayList<DataPoint> points) {
        routeTable.getItems().setAll(points);


        routeAirlineCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("airline"));
        routeAirlineIDCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, Integer>("airlineID"));
        routeSrcCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("srcAirport"));
        routeSrcIDCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("srcAirportID"));
        routeDestCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("dstAirport"));
        routeDestIDCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("dstAirportID"));
        routeCSCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("codeshare"));
        routeStopsCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, Integer>("stops"));
        routeEqCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String[]>("equipment"));







        routeTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    RoutePoint myPoint = (RoutePoint) routeTable.getSelectionModel().getSelectedItem();
                    Stage stage;
                    Parent root;
                    stage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/routePopup.fxml"));
                        root = loader.load();
                        RoutePopUpController popUpController = loader.getController();
                        popUpController.setRoutePoint(myPoint);
                        popUpController.setUpPopUp();

                        stage.setScene(new Scene(root));
                        stage.setTitle("My Popup test");
                        stage.initModality(Modality.NONE);
                        stage.initOwner(null);

                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void updateRoutesDropdowns() {
        populateRoutesFilterBySourceList();
        populateRoutesFilterbyDestList();
        populateRoutesFilterByStopsList();
        populateRoutesFilterByEquipList();
    }

    private void populateRoutesFilterBySourceList(){
        ArrayList<String> uniqueSources = FilterRefactor.filterDistinct("Src", "ROUTE");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = HelperFunctions.addNullValue(uniqueObservableSources);
        routesFilterBySourceMenu.setItems(uniqueObservableSources);
        routesFilterBySourceMenu.setValue(uniqueObservableSources.get(0));
    }

    private void populateRoutesFilterbyDestList(){
        ArrayList<String> uniqueSources = FilterRefactor.filterDistinct("Dst", "ROUTE");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = HelperFunctions.addNullValue(uniqueObservableSources);
        routesFilterbyDestMenu.setItems(uniqueObservableSources);
        routesFilterbyDestMenu.setValue(uniqueObservableSources.get(0));
    }

    private void populateRoutesFilterByStopsList(){
        ArrayList<String> uniqueSources = FilterRefactor.filterDistinct("Stops", "ROUTE");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = HelperFunctions.addNullValue(uniqueObservableSources);
        routesFilterByStopsMenu.setItems(uniqueObservableSources);
        routesFilterByStopsMenu.setValue(uniqueObservableSources.get(0));
    }

    private void populateRoutesFilterByEquipList(){
        ArrayList<String> uniqueEquip = new ArrayList<>();
        ArrayList<DataPoint> myRoutes = FilterRefactor.getAllPoints(DataTypes.ROUTEPOINT);
        for (DataPoint route : myRoutes) {
            RoutePoint myRoute = (RoutePoint) route;
            if (myRoute.getEquipment() == null) {
                continue;
            }
            String[] equip = myRoute.getEquipment().split("\\s+");
            for (String myEquip : equip) {
                if (!uniqueEquip.contains(myEquip)) {
                    uniqueEquip.add(myEquip);
                }
            }
        }

        Collections.sort(uniqueEquip);
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueEquip);
        uniqueObservableSources = HelperFunctions.addNullValue(uniqueObservableSources);
        routesFilterbyEquipMenu.setItems(uniqueObservableSources);
        routesFilterbyEquipMenu.setValue(uniqueObservableSources.get(0));
    }


    public void exportRouteData() {
        ArrayList<DataPoint> myPoints = new ArrayList<>(routeTable.getItems());
        Exporter.exportData(myPoints);
    }
}