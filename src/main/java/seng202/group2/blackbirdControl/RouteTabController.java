package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static javafx.application.Application.launch;

/**
 * Created by emr65 on 20/09/16.
 */
public class RouteTabController {

    private MainController mainController;
    RouteTabController instance;

    private ObservableList<String> routesFilterBySourceList  = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> routesFilterbyDestList  = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> routesFilterByStopsList  = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> routesFilterbyEquipList  = FXCollections.observableArrayList("No values Loaded");


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
    @FXML private TableColumn routeSrcCountryCol;
    @FXML private TableColumn routeDstCountryCol;
    @FXML private TableColumn routeSrcNameCol;
    @FXML private TableColumn routeDstNameCol;

    @FXML private ComboBox routesFilterBySourceMenu;
    @FXML private ComboBox routesFilterbyDestMenu;
    @FXML private ComboBox routesFilterByStopsMenu;
    @FXML private ComboBox routesFilterbyEquipMenu;
    @FXML private TextField routesSearchMenu;

    public RouteTabController(){
        instance = this;
    }

    public void initialize(){
        routesFilterBySourceMenu.setValue(getRoutesFilterBySourceList().get(0));
        routesFilterBySourceMenu.setItems(getRoutesFilterBySourceList());
        routesFilterbyDestMenu.setValue(getRoutesFilterbyDestList().get(0));
        routesFilterbyDestMenu.setItems(getRoutesFilterbyDestList());
        routesFilterByStopsMenu.setValue(getRoutesFilterByStopsList().get(0));
        routesFilterByStopsMenu.setItems(getRoutesFilterByStopsList());
        routesFilterbyEquipMenu.setValue(getRoutesFilterbyEquipList().get(0));
        routesFilterbyEquipMenu.setItems(getRoutesFilterbyEquipList());
    }

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

            //use controller to control it
            RouteAddingPopUpController popUpController = loader.getController();
            popUpController.setRouteTabController(instance);
            popUpController.setAdderStage(adderStage);
            popUpController.setRoot(root);
            popUpController.control();
            mainController.mainMenuHelper();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void routesFilterButtonPressed() {
        String sourceSelection;
        String destSelection;
        String stopsSelection;
        String equipSelection;
        try {
        sourceSelection = routesFilterBySourceMenu.getValue().toString();
        destSelection = routesFilterbyDestMenu.getValue().toString();
        stopsSelection = routesFilterByStopsMenu.getValue().toString();
        equipSelection = routesFilterbyEquipMenu.getValue().toString();
        } catch (java.lang.NullPointerException e) {
            sourceSelection = "No values Loaded";
            destSelection = "No values Loaded";
            stopsSelection = "No values Loaded";
            equipSelection = "No values Loaded";
        }
        String searchQuery = routesSearchMenu.getText().toString();
        ArrayList<DataPoint> routePoints;

        if(sourceSelection.equals("No values Loaded") && destSelection.equals("No values Loaded") && stopsSelection.equals("No values Loaded") && equipSelection.equals("No values Loaded")){
            routePoints = Filter.getAllPoints(DataTypes.ROUTEPOINT);
            //updateRoutesTable(routePoints);
        } else {
            ArrayList<String> menusPressed = new ArrayList<>(Arrays.asList(sourceSelection, destSelection, stopsSelection, equipSelection));
            routePoints = Filter.filterSelections(menusPressed, searchQuery,DataTypes.ROUTEPOINT);
        }
        updateRoutesDropdowns();
        updateRoutesTable(routePoints);
    }

    public void routesSeeAllDataButtonPressed(ActionEvent actionEvent) {
        ArrayList<DataPoint> allPoints = Filter.getAllPoints(DataTypes.ROUTEPOINT);
        updateRoutesDropdowns();
        routesFilterBySourceMenu.setValue(routesFilterBySourceList.get(0));
        routesFilterbyDestMenu.setValue(routesFilterbyDestList.get(0));
        routesFilterByStopsMenu.setValue(routesFilterByStopsList.get(0));
        routesFilterbyEquipMenu.setValue(routesFilterbyEquipList.get(0));

        routesSearchMenu.clear();

        updateRoutesTable(allPoints);
    }

    public void addRouteData() {
        
        File f = HelperFunctions.getFile("Add Route Data", false);
        if (f == null) {
            return;
        }


        ErrorTabController errorTab = mainController.getErrorTabController();
        ArrayList<DataPoint> myRouteData = Parser.parseFile(f, DataTypes.ROUTEPOINT, errorTab);
        Database.insertDataPoints(myRouteData, errorTab);

        ArrayList<DataPoint> validRouteData = Filter.getAllPoints(DataTypes.ROUTEPOINT);
        //setAllRoutePoints(myRouteData); //populating local data with all points
        updateRoutesTable(validRouteData);
        updateRoutesDropdowns();
        routesFilterBySourceMenu.setValue(routesFilterBySourceList.get(0));
        routesFilterbyDestMenu.setValue(routesFilterbyDestList.get(0));
        routesFilterByStopsMenu.setValue(routesFilterByStopsList.get(0));
        routesFilterbyEquipMenu.setValue(routesFilterbyEquipList.get(0));

        mainController.updateAirports();
        mainController.updateTab(DataTypes.ROUTEPOINT);
        //myStage.close();

    }

    protected void updateRoutesTable(ArrayList<DataPoint> points) {
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
        routeSrcCountryCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("srcAirportCountry"));
        routeDstCountryCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("dstAirportCountry"));
        routeSrcNameCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("srcAirportName"));
        routeDstNameCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("dstAirportName"));

        routeTable.getItems().addListener(new ListChangeListener<DataPoint>() {
            //This refreshes the current table
            @Override
            public void onChanged(Change<? extends DataPoint> c) {
                routeTable.getColumns().get(0).setVisible(false);
                routeTable.getColumns().get(0).setVisible(true);
            }
        });

        routeTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    RoutePoint myPoint = (RoutePoint) routeTable.getSelectionModel().getSelectedItem();
                    Stage stage = new Stage();
                    Parent root;
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/routePopup.fxml"));
                        root = loader.load();
                        RoutePopUpController popUpController = loader.getController();
                        popUpController.setRouteTabController(instance);
                        popUpController.setStage(stage);
                        popUpController.setRoutePoint(myPoint);
                        popUpController.setUpPopUp();

                        stage.setScene(new Scene(root));
                        stage.setTitle("Detailed Route Information");
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

    /**
     * Updates all of the dropdown menus for route filtering.
     */
    private void updateRoutesDropdowns() {
        populateRoutesFilterBySourceList();
        populateRoutesFilterbyDestList();
        populateRoutesFilterByStopsList();
        populateRoutesFilterByEquipList();
    }

    /**
     * Populates route source dropdown
     */
    private void populateRoutesFilterBySourceList(){
        ArrayList<String> uniqueSources = Filter.filterDistinct("Src", "ROUTE");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = HelperFunctions.addNullValue(uniqueObservableSources);
        routesFilterBySourceList = uniqueObservableSources;
        routesFilterBySourceMenu.setItems(uniqueObservableSources);

    }

    private void populateRoutesFilterbyDestList(){
        ArrayList<String> uniqueSources = Filter.filterDistinct("Dst", "ROUTE");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = HelperFunctions.addNullValue(uniqueObservableSources);
        routesFilterbyDestList = uniqueObservableSources;
        routesFilterbyDestMenu.setItems(uniqueObservableSources);

    }

    private void populateRoutesFilterByStopsList(){
        ArrayList<String> uniqueSources = Filter.filterDistinct("Stops", "ROUTE");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = HelperFunctions.addNullValue(uniqueObservableSources);
        routesFilterByStopsList = uniqueObservableSources;
        routesFilterByStopsMenu.setItems(uniqueObservableSources);

    }

    private void populateRoutesFilterByEquipList(){
        ArrayList<String> uniqueEquip = new ArrayList<>();
        ArrayList<DataPoint> myRoutes = Filter.getAllPoints(DataTypes.ROUTEPOINT);
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
        routesFilterbyEquipList = uniqueObservableSources;
        routesFilterbyEquipMenu.setItems(uniqueObservableSources);

    }


    public static String[] getIataOrIcao(String name, DataTypes type) {
        String[] returnString = new String[2];
        if (type == DataTypes.AIRLINEPOINT) {
            String sql = "SELECT * FROM AIRLINE WHERE NAME='" + name + "'";
            ArrayList<DataPoint> foundAirline = Database.performGenericQuery(sql, DataTypes.AIRLINEPOINT);
            if (foundAirline.size() > 1) {
                System.err.println("Found more than one airline");
                //TODO What should be done here?
            }
            AirlinePoint myAirline = (AirlinePoint) foundAirline.get(0);
            returnString[0] = Integer.toString(myAirline.getAirlineID());
            if (!myAirline.getIata().isEmpty()) {
                returnString[1] = myAirline.getIata();
            } else if (!myAirline.getIcao().isEmpty()) {
                returnString[1] = myAirline.getIcao();
            } else {
                System.err.println("Airline missing IATA and ICAO");
                //TODO What should be done here?
                returnString[1] = "";
            }
        }

        if (type == DataTypes.AIRPORTPOINT) {
            String sql = "SELECT * FROM AIRPORT WHERE NAME='" + name + "'";
            ArrayList<DataPoint> foundSource = Database.performGenericQuery(sql, DataTypes.AIRPORTPOINT);
            if (foundSource.size() > 1) {
                System.err.println("Found more than one airport for route src/dest");
                //TODO What should be done here?
            }
            AirportPoint mySource = (AirportPoint) foundSource.get(0);
            returnString[0] = Integer.toString(mySource.getAirportID());
            if (!mySource.getIata().isEmpty()) {
                returnString[1] = mySource.getIata();
            } else if (!mySource.getIcao().isEmpty()) {
                returnString[1] = mySource.getIcao();
            } else {
                System.err.println("Source Airport missing IATA and ICAO");
                //TODO What should be done here?
                returnString[1] = "";
            }
        }
        return returnString;
    }

    public static String[] getFields(String[] values) {
        String airline; //index 0 in input file
        String airlineID;
        String srcAirport;
        String srcAirportID;
        String dstAirport;
        String dstAirportID;
        String codeshare;
        String stops;
        String equipment;

        String[] airlineFields = RouteTabController.getIataOrIcao(values[0], DataTypes.AIRLINEPOINT);
        airlineID = airlineFields[0];
        airline = airlineFields[1];

        String[] sourceFields = RouteTabController.getIataOrIcao(values[1], DataTypes.AIRPORTPOINT);
        srcAirportID = sourceFields[0];
        srcAirport = sourceFields[1];

        String[] destFields = RouteTabController.getIataOrIcao(values[2], DataTypes.AIRPORTPOINT);
        dstAirportID = destFields[0];
        dstAirport = destFields[1];

        codeshare = values[3];
        stops = values[4];
        equipment = values[5];

        String[] newString = {airline, airlineID, srcAirport, srcAirportID,
                dstAirport, dstAirportID, codeshare, stops, equipment};

        return newString;
    }


    public void exportRouteData() {
        ArrayList<DataPoint> myPoints = new ArrayList<>(routeTable.getItems());
        Exporter.exportData(myPoints);
    }

    public void enterPressed(KeyEvent ke)
    {
        if(ke.getCode() == KeyCode.ENTER)
        {
            routesFilterButtonPressed();
        }
    }

    public void setAllRoutesFilterBySourceList(ArrayList<String> sourceList){ this.routesFilterBySourceList = routesFilterBySourceList;}

    public ObservableList<String> getRoutesFilterBySourceList(){return routesFilterBySourceList;}

    public ObservableList<String> getRoutesFilterbyDestList(){return routesFilterbyDestList;}

    public void setRoutesFilterbyDestList(ArrayList<String> destList){ this.routesFilterbyDestList = routesFilterbyDestList;}

    public void SetRoutesFilterByStopsList(ArrayList<String> stopsList){ this.routesFilterByStopsList = routesFilterByStopsList;}

    public ObservableList<String> getRoutesFilterByStopsList(){return routesFilterByStopsList;}

    public ObservableList<String> getRoutesFilterbyEquipList(){return routesFilterbyEquipList;}

    public void setRoutesFilterbyEquipList(ArrayList<String> equipList){ this.routesFilterbyEquipList = routesFilterbyEquipList;}
}