package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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

/**
 * Controller for the route tab. Handles displaying of data, and acts as a controller for adding and editing data.
 *
 * @author Team2
 * @version 1.0
 * @since 22/9/2016
 */
public class RouteTabController {

    private MainController mainController;
    private RouteTabController instance;
    private ObservableList<String> routesFilterBySourceList  = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> routesFilterbyDestList  = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> routesFilterByStopsList  = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> routesFilterbyEquipList  = FXCollections.observableArrayList("No values Loaded");

    @FXML private TableView<DataPoint> routeTable;
    @FXML private TableColumn routeAirlineCol;
    @FXML private TableColumn routeAirlineIDCol;
    @FXML private TableColumn routeSrcCol;
    @FXML private TableColumn routeSrcIDCol;
    @FXML private TableColumn routeDestCol;
    @FXML private TableColumn routeDestIDCol;
    @FXML private TableColumn routeCSCol;
    @FXML private TableColumn routeStopsCol;
    @FXML private TableColumn routeEqCol;
    @FXML private TableColumn routeSrcCountryCol;
    @FXML private TableColumn routeDstCountryCol;
    @FXML private TableColumn routeSrcNameCol;
    @FXML private TableColumn routeDstNameCol;

    @FXML private ComboBox routesFilterBySourceMenu;
    @FXML private ComboBox routesFilterbyDestMenu;
    @FXML private ComboBox routesFilterByStopsMenu;
    @FXML private ComboBox routesFilterbyEquipMenu;
    @FXML private TextField routesSearchMenu;

    /**
     * Creates the route tab controller.
     */
    public RouteTabController() {
        instance = this;
    }

    /**
     * Initializes the route tab
     */
    public void initialize() {
        routesFilterBySourceMenu.setValue(getRoutesFilterBySourceList().get(0));
        routesFilterBySourceMenu.setItems(getRoutesFilterBySourceList());
        routesFilterbyDestMenu.setValue(getRoutesFilterbyDestList().get(0));
        routesFilterbyDestMenu.setItems(getRoutesFilterbyDestList());
        routesFilterByStopsMenu.setValue(getRoutesFilterByStopsList().get(0));
        routesFilterByStopsMenu.setItems(getRoutesFilterByStopsList());
        routesFilterbyEquipMenu.setValue(getRoutesFilterbyEquipList().get(0));
        routesFilterbyEquipMenu.setItems(getRoutesFilterbyEquipList());
    }

    /**
     * The initial view when the table is empty.
     */
    public void show() {
        routeTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Route"));
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
     * Brings up adding popup to insert a single route value
     */
    public void addSingleRoute() {
        try {
            Stage adderStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RouteAddingPopUp.fxml"));
            Parent root = loader.load();

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

    /**
     * Helps to filter routes, obtaining the values from the filter dropdowns
     */
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
        String searchQuery = routesSearchMenu.getText();
        ArrayList<DataPoint> routePoints;

        if (sourceSelection.equals("No values Loaded") && destSelection.equals("No values Loaded") && stopsSelection.equals("No values Loaded") && equipSelection.equals("No values Loaded")) {
            routePoints = Filter.getAllPoints(DataTypes.ROUTEPOINT);
        } else {
            ArrayList<String> menusPressed = new ArrayList<>(Arrays.asList(sourceSelection, destSelection, stopsSelection, equipSelection));
            routePoints = Filter.filterSelections(menusPressed, searchQuery, DataTypes.ROUTEPOINT);
        }
        updateRoutesDropdowns();
        updateRoutesTable(routePoints);
    }

    /**
     * Shows all the routes currently stored within the database.
     */
    public void routesSeeAllDataButtonPressed() {
        ArrayList<DataPoint> allPoints = Filter.getAllPoints(DataTypes.ROUTEPOINT);
        updateRoutesDropdowns();
        routesFilterBySourceMenu.setValue(routesFilterBySourceList.get(0));
        routesFilterbyDestMenu.setValue(routesFilterbyDestList.get(0));
        routesFilterByStopsMenu.setValue(routesFilterByStopsList.get(0));
        routesFilterbyEquipMenu.setValue(routesFilterbyEquipList.get(0));
        routesSearchMenu.clear();
        updateRoutesTable(allPoints);
    }

    /**
     * Adds route data using a file chooser. Only valid data will be added into the persistent database.
     *
     * @param replace boolean to inform if data being added is replacing or merging
     * @see Parser
     * @see DatabaseInterface
     */
    void addRouteData(boolean replace) {
        File f = HelperFunctions.getFile("Add Route Data", false);
        if (f == null) {
            return;
        }
        if(replace) {
            DatabaseInterface.clearTable(DataTypes.ROUTEPOINT);
        }
        ErrorTabController errorTab = mainController.getErrorTabController();
        ArrayList<DataPoint> myRouteData = Parser.parseFile(f, DataTypes.ROUTEPOINT, errorTab);
        DatabaseInterface.insertDataPoints(myRouteData, errorTab);

        ArrayList<DataPoint> validRouteData = Filter.getAllPoints(DataTypes.ROUTEPOINT);
        updateRoutesTable(validRouteData);
        updateRoutesDropdowns();
        routesFilterBySourceMenu.setValue(routesFilterBySourceList.get(0));
        routesFilterbyDestMenu.setValue(routesFilterbyDestList.get(0));
        routesFilterByStopsMenu.setValue(routesFilterByStopsList.get(0));
        routesFilterbyEquipMenu.setValue(routesFilterbyEquipList.get(0));

        mainController.updateAirports();
        mainController.updateTab(DataTypes.ROUTEPOINT);
    }

    /**
     * Updates the route table displaying data
     *
     * @param points The arraylist of data points
     */
    void updateRoutesTable(ArrayList<DataPoint> points) {
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

        routeTable.getItems().addListener((ListChangeListener<DataPoint>) c -> {
            routeTable.getColumns().get(0).setVisible(false);
            routeTable.getColumns().get(0).setVisible(true);
        });

        routeTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    RoutePoint myPoint = (RoutePoint) routeTable.getSelectionModel().getSelectedItem();
                    Stage stage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/routePopup.fxml"));
                        Parent root = loader.load();
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
    private void populateRoutesFilterBySourceList() {
        ArrayList<String> uniqueSources = Filter.filterDistinct("Src", "ROUTE");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = HelperFunctions.addNullValue(uniqueObservableSources);
        routesFilterBySourceList = uniqueObservableSources;
        routesFilterBySourceMenu.setItems(uniqueObservableSources);

    }

    /**
     * Populates route destination dropdown
     */
    private void populateRoutesFilterbyDestList() {
        ArrayList<String> uniqueSources = Filter.filterDistinct("Dst", "ROUTE");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = HelperFunctions.addNullValue(uniqueObservableSources);
        routesFilterbyDestList = uniqueObservableSources;
        routesFilterbyDestMenu.setItems(uniqueObservableSources);

    }

    /**
     * Populates route stops dropdown
     */
    private void populateRoutesFilterByStopsList() {
        ArrayList<String> uniqueSources = Filter.filterDistinct("Stops", "ROUTE");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = HelperFunctions.addNullValue(uniqueObservableSources);
        routesFilterByStopsList = uniqueObservableSources;
        routesFilterByStopsMenu.setItems(uniqueObservableSources);
    }

    /**
     * Populates route equipment dropdown
     */
    private void populateRoutesFilterByEquipList() {
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

    /**
     * Helper method to find the IATA or ICAO code of an airline/airport when given the name
     * @param name The name of the airline or airport
     * @param type The type of data point
     * @return A string array containing two values, the point id and the point's IATA or ICAO code, if present.
     */
    private static String[] getIataOrIcao(String name, DataTypes type) {
        String[] returnString = new String[2];
        if (type == DataTypes.AIRLINEPOINT) {
            String sql = "SELECT * FROM AIRLINE WHERE NAME='" + name + "'";
            ArrayList<DataPoint> foundAirline = DatabaseInterface.performGenericQuery(sql, DataTypes.AIRLINEPOINT);
            AirlinePoint myAirline = (AirlinePoint) foundAirline.get(0);
            returnString[0] = Integer.toString(myAirline.getAirlineID());
            if (!myAirline.getIata().isEmpty()) {
                returnString[1] = myAirline.getIata();
            } else if (!myAirline.getIcao().isEmpty()) {
                returnString[1] = myAirline.getIcao();
            } else {
                returnString[1] = "";
            }
        }

        if (type == DataTypes.AIRPORTPOINT) {
            String sql = "SELECT * FROM AIRPORT WHERE NAME='" + name + "'";
            ArrayList<DataPoint> foundSource = DatabaseInterface.performGenericQuery(sql, DataTypes.AIRPORTPOINT);
            AirportPoint mySource = (AirportPoint) foundSource.get(0);
            returnString[0] = Integer.toString(mySource.getAirportID());
            if (!mySource.getIata().isEmpty()) {
                returnString[1] = mySource.getIata();
            } else if (!mySource.getIcao().isEmpty()) {
                returnString[1] = mySource.getIcao();
            } else {
                returnString[1] = "";
            }
        }
        return returnString;
    }

    /**
     * Helper function to create a string array based off of the found data- from user selection now to using the
     * found fields, to generate a string array in the correct format to create a route point
     * @param values The initial string array, without the proper fields
     * @return A complete string array for creating a route point
     */
    static String[] getFields(String[] values) {
        String airline;
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

    /**
     * Exports current route data in the table, which may be a filtered subset of all data.
     */
    void exportRouteData() {
        ArrayList<DataPoint> myPoints = new ArrayList<>(routeTable.getItems());
        Exporter.exportData(myPoints);
    }

    /**
     * Assigns an action for the enter key
     *
     * @param ke The keyevent that occurred (the Enter key event)
     */
    public void enterPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ENTER) {
            routesFilterButtonPressed();
        }
    }

    /**
     * @return Returns the observable string populating the filter route by source menu
     */
    private ObservableList<String> getRoutesFilterBySourceList() {
        return routesFilterBySourceList;
    }

    /**
     * @return Returns the observable string populating the filter route by destination menu
     */
    private ObservableList<String> getRoutesFilterbyDestList() {
        return routesFilterbyDestList;
    }

    /**
     * @return Returns the observable string populating the filter route by stops menu
     */
    private ObservableList<String> getRoutesFilterByStopsList() {
        return routesFilterByStopsList;
    }

    /**
     * @return Returns the observable string populating the filter route by equipment menu
     */
    private ObservableList<String> getRoutesFilterbyEquipList() {
        return routesFilterbyEquipList;
    }

}