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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static javafx.fxml.FXMLLoader.load;

/**
 * Created by emr65 on 13/08/16.
 */
public class GUIController {

    //ObservableList<String> airPortCountryList = FXCollections.observableArrayList("Australia", "New Zealand", "Canada"); //maybe need this?
    //ObservableList<String> copy = FXCollections.observableArrayList("Australia", "New Zealand", "Canada"); //maybe need this?
    private ObservableList<String> airPortCountryList = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> airlineCountryList = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> airlineActiveList  = FXCollections.observableArrayList("No values Loaded");

    private ObservableList<String> routesFilterBySourceList  = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> routesFilterbyDestList  = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> routesFilterByStopsList  = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> routesFilterbyEquipList  = FXCollections.observableArrayList("No values Loaded");

    private ObservableList<String> flightsDepartList = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> flightsDestList = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> flightsCountryList = FXCollections.observableArrayList("No values Loaded");

    private ArrayList<AirportPoint> allPoints = new ArrayList<AirportPoint>();
    //ArrayList<AirportPoint> allValidPoints = new ArrayList<>();

    private ArrayList<AirlinePoint> allAirlinePoints = new ArrayList<AirlinePoint>();
    private ArrayList<RoutePoint> allRoutePoints = new ArrayList<RoutePoint>();

    private boolean routesFilled = false;
    private boolean airportsFilled = false;

    public boolean isAirportsFilled() {
        return airportsFilled;
    }

//    public ArrayList<AirportPoint> getAllValidPoints() {
//        return allValidPoints;
//    }
//
//    public void setAllValidPoints(ArrayList<AirportPoint> allValidPoints) {
//        this.allValidPoints = allValidPoints;
//    }
    @FXML private TabPane mainTabPane;
    @FXML private Pane openPane;
    @FXML private Tab flightTab;
    @FXML private Tab routeTab;
    @FXML private Tab airportTab;
    @FXML private Tab airlineTab;

    @FXML private MenuItem exportFlightMenuButton;
    @FXML private MenuItem exportAirportMenuButton;
    @FXML private MenuItem exportAirlineMenuButton;
    @FXML private MenuItem exportRouteMenuButton;



    @FXML
    private javafx.scene.control.MenuItem newProjMenu;
    @FXML
    private MenuItem addDataMenuButton;
    @FXML
    private MenuItem exportDataMenuButton;
    @FXML private TableView<AirportPoint> airportTable;
    @FXML private TableView<AirlinePoint> airlineTable;
    @FXML private TableView<RoutePoint> routeTable;
    @FXML private TableView<Flight> flightTable;
    @FXML private TableView<FlightPoint> flightPointTable;

// AIRPORT Table columns
    @FXML private TableColumn airportIDCol;
    @FXML private TableColumn airportNameCol;
    @FXML private TableColumn airportCityCol;
    @FXML private TableColumn airportCountryCol;
    @FXML private TableColumn airportIATACol;
    @FXML private TableColumn airportICAOCol;
    @FXML private TableColumn airportLatCol;
    @FXML private TableColumn airportIncCol;
    @FXML private TableColumn airportOutCol;
   //public void setAirlineActiveListData(ArrayList<AirlinePoint> points){this.airlineActiveList = points;}
    @FXML private TableColumn airportLongCol;
    @FXML private TableColumn airportAltCol;
    @FXML private TableColumn airportTimeCol;
    @FXML private TableColumn airportDSTCol;
    @FXML private TableColumn airportTZCol;
    @FXML private TableColumn airportErrorCol;

// AIRLINE Table columns
    @FXML private TableColumn airlineIDCol;
    @FXML private TableColumn airlineNameCol;
    @FXML private TableColumn airlineAliasCol;
    //@FXML private TableView<AirportPoint> airportTable;
    @FXML private TableColumn airlineIATACol;
    @FXML private TableColumn airlineICAOCol;
    @FXML private TableColumn airlineCallsignCol;
    @FXML private TableColumn airlineCountryCol;
    @FXML private TableColumn airlineActCol;
    @FXML private TableColumn airlineErrorCol;
// ROUTE Table columns
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
    // FLIGHT POINT Table columns
    @FXML private TableColumn flightPointTypeCol;
    @FXML private TableColumn flightPointLocaleCol;
    @FXML private TableColumn flightPointAltitudeCol;
    @FXML private TableColumn flightPointLatitudeCol;
    @FXML private TableColumn flightPointLongitudeCol;
    //FLIGHT Table columns\
    @FXML private TableColumn flightSourceCol;
    @FXML private TableColumn flightDestCol;
    // Filter Menu testing
    //=================AIRPORTS=====================
    @FXML private ComboBox airportFilterByCountryMenu;
    @FXML private TextField airportSearchQuery;
    @FXML private Button filterButton;
    @FXML private Button airportSeeAllButton;
    //==================AIRLINES=====================
    @FXML private ComboBox airlineFilterMenu;
    @FXML private ComboBox airlineActiveMenu;
    @FXML private TextField airlineSearchQuery;
    //==================ROUTES=====================
    @FXML private ComboBox routesFilterBySourceMenu;
    @FXML private ComboBox routesFilterbyDestMenu;
    @FXML private ComboBox routesFilterByStopsMenu;
    @FXML private ComboBox routesFilterbyEquipMenu;
    @FXML private TextField routesSearchMenu;
    //=================FLIGHTS======================
    @FXML private ComboBox flightsFilterByDepartureMenu;
    @FXML private ComboBox flightsFilterByDestinationMenu;
    @FXML private ComboBox flightsFilterByCountryMenu;
    @FXML private TextField flightSearchQuery;


    public void setAllRoutesFilterBySourceList(ArrayList<String> sourceList){ this.routesFilterBySourceList = routesFilterBySourceList;}

    public ObservableList<String> getRoutesFilterBySourceList(){return routesFilterBySourceList;}

    public ObservableList<String> getRoutesFilterbyDestList(){return routesFilterbyDestList;}

    public void setRoutesFilterbyDestList(ArrayList<String> destList){ this.routesFilterbyDestList = routesFilterbyDestList;}

    public void SetRoutesFilterByStopsList(ArrayList<String> stopsList){ this.routesFilterByStopsList = routesFilterByStopsList;}

    public ObservableList<String> getRoutesFilterByStopsList(){return routesFilterByStopsList;}

    public ObservableList<String> getRoutesFilterbyEquipList(){return routesFilterbyEquipList;}

    public void setRoutesFilterbyEquipList(ArrayList<String> equipList){ this.routesFilterbyEquipList = routesFilterbyEquipList;}

;

    public ArrayList<RoutePoint> getAllRoutePoints() {
        return allRoutePoints;
    }

    public void setAllRoutePoints(ArrayList<RoutePoint> allRoutePoints){
        this.allRoutePoints = allRoutePoints;
    }

    public ObservableList<String> getAirlineActiveList(){return airlineActiveList;}

    public void setAirlineActiveList(ObservableList<String> activeList){this.airlineActiveList = activeList;}

    public ArrayList<AirportPoint> getAllAirportPoints(){return allPoints;}

    public void setAllAirportPoints(ArrayList<AirportPoint> points){this.allPoints = points;}

    public ArrayList<AirlinePoint> getAllAirlinePoints(){return allAirlinePoints;}

    public void setAllAirlinePoints(ArrayList<AirlinePoint> points){this.allAirlinePoints = points;}

    //Airline Popup Info
  //  @FXML private Text nameText;

    @FXML
    private void initialize(){
        //Automatic initialisation when the program starts

//        BBDatabase.createTables(); //COMMENT ME OUT IF YOU WANT PROGRAM TO RUN NORMALL
//        addALLData();              //COMMENT ME OUT IF YOU WANT THE PROGRAM TO RUN NORAMLLY

        airportFilterByCountryMenu.setValue(airPortCountryList.get(0));
        airportFilterByCountryMenu.setItems(airPortCountryList);
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


        flightsFilterByDepartureMenu.setItems(flightsDepartList);
        flightsFilterByDepartureMenu.setValue(flightsDepartList.get(0));
        flightsFilterByDestinationMenu.setItems(flightsDestList);
        flightsFilterByDestinationMenu.setValue(flightsDestList.get(0));
        flightsFilterByCountryMenu.setItems(flightsCountryList);
        flightsFilterByCountryMenu.setValue(flightsCountryList.get(0));


    }

    private File getFile(String message) {
        //gets a file of a specified type
        File myFile;
        String cwd = System.getProperty("user.dir");
        File userDirectory = new File(cwd);

        FileChooser fc = new FileChooser();
        fc.setTitle(message);

        fc.setInitialDirectory(userDirectory);

        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        fc.setInitialDirectory(userDirectory);

        //Choose the file
        myFile = fc.showOpenDialog(null);
        //Make sure a file was selected, if not return default

        if(myFile != null) {
            return myFile;
        }
        else{
            return null;
        }

    }

    public void show(){

        openPane.setVisible(false);
        mainTabPane.setVisible(true);
        //SQQliteJDBC myDb = new SQQliteJDBC();
        //SQLiteJDBC.dropTables();
        BBDatabase.createTables();
        //SQQliteJDBC.dropTables();
        addDataMenuButton.setDisable(false);
        exportDataMenuButton.setDisable(false);
        //addDataMenuButton.setDisable(true);


        routeTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Route"));
        airlineTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Airline"));
        airportTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Airport"));
        flightTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Flight"));
        flightPointTable.setPlaceholder(new Label("No data in table. To add data load a flight and double click to see flight details"));

    }

    /*******************************************************************************************************************
     *******************************************************************************************************************
     *************************************** ADDING DATA ***************************************************************
     *******************************************************************************************************************
     ******************************************************************************************************************/

    //Adding data via Files
    public void addALLData(){
        //MASTER OVERRIDE FUNCTION DONT SCREW WITH THIS UNLESS YOU ARE A WIZARD
        String cwd = System.getProperty("user.dir");
        String airlinesFileString = cwd + "/TestFiles/airlines.txt";
        String airportsFileString = cwd + "/TestFiles/airports.txt";
        String routesFileString = cwd + "/TestFiles/route.txt";
        String flightsFileString = cwd + "/TestFiles/flight.txt";


        File airlinesFile = new File(airlinesFileString);
        File airportsFile = new File(airportsFileString);
        File routesFile = new File(routesFileString);
        File flightsFile = new File(flightsFileString);

        ArrayList<AirlinePoint> airlinePoints = Parser.parseAirlineData(airlinesFile);
        ArrayList<AirportPoint> airportPoints = Parser.parseAirportData(airportsFile);
        ArrayList<RoutePoint> routePoints = Parser.parseRouteData(routesFile);
        ArrayList<FlightPoint> flightPoints = Parser.parseFlightData(flightsFile);



        BBDatabase.deleteDBFile();
        BBDatabase.createTables();
        BBDatabase.addAirlinePointstoDB(airlinePoints);
        BBDatabase.addAirportPointsToDB(airportPoints);
        BBDatabase.addRoutePointstoDB(routePoints);
        try {
            BBDatabase.addFlighttoDB(flightPoints);
        } catch (SQLException e) {


            //System.err.println("Bad flight data");
        }

        airportPoints = Filter.getAllAirportPointsFromDB();
        setAllAirportPoints(airportPoints); //imports setter, keeps track of all points

        updateAirportsTable(airportPoints);
        airPortCountryList = populateAirportCountryList();
        airportFilterByCountryMenu.setItems(airPortCountryList);
        airportFilterByCountryMenu.setValue(airPortCountryList.get(0));

        airlinePoints = Filter.getAllAirlinePointsfromDB();


        // myAirlineData = Filter.getAllAirlinePointsfromDB();
        setAllAirlinePoints(airlinePoints);
        setAirlineActiveList(populateAirlineActiveList());

        airlineActiveMenu.setItems(getAirlineActiveList());
        airlineActiveMenu.setValue(getAirlineActiveList().get(0));
        airlineCountryList = populateAirlineCountryList();
        airlineFilterMenu.setItems(airlineCountryList);
        airlineFilterMenu.setValue(airlineCountryList.get(0));
        updateAirlinesTable(airlinePoints);

        updateRoutesTable(routePoints);
        updateRoutesDropdowns();

        updateFlightsTable(flightPoints);


    }

    public void addAirportData(){
        //Adds the aiport data into the filter menu, updates airport Country list


        File f;
        f = getFile("Add Airport Data");
        if (f == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Oops!");
        alert.setHeaderText("Error in adding airport data");
        alert.setContentText("Some airport data was unable to be added");

        ArrayList<AirportPoint> allairportPoints = Parser.parseAirportData(f);
        if (allairportPoints == null) {
            alert.showAndWait();
            return;
        } else {
            boolean allNull = true;
            for (AirportPoint airport : allairportPoints) {
                if (airport != null) {
                    allNull = false;
                }
            }
            if (allNull) {
                alert.showAndWait();
                return;
            }
        }

        boolean allAdded = BBDatabase.addAirportPointsToDB(allairportPoints);
        ArrayList<AirportPoint> validairportPoints = Filter.getAllAirportPointsFromDB();

        setAllAirportPoints(validairportPoints); //adding all airport data, including bad data
        updateAirportsTable(validairportPoints);

        airPortCountryList = populateAirportCountryList();  //populating from valid data in database
        airportFilterByCountryMenu.setItems(airPortCountryList);
        airportFilterByCountryMenu.setValue(airPortCountryList.get(0));

        exportAirportMenuButton.setDisable(false);

        mainTabPane.getSelectionModel().select(airportTab);

        if(!allAdded) {
            alert.showAndWait();
        }

        //yeah this is kinda high coupling but will be fixed in the refactor
        if (routesFilled) {
            allPoints  = BBDatabase.linkRoutesandAirports(allPoints, allRoutePoints);
            updateAirportsTable2(allPoints);
            updateRoutesTable2(allRoutePoints);
        }

        airportsFilled = true;


    }

    public void addAirlineData(){
        //Adds airline data into filter menu, updates airline data list


        File f;
        f = getFile("Add Airline Data");
        if (f == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Oops!");
        alert.setHeaderText("Some data could not be added");
        alert.setContentText("Please check your input file");

        ArrayList<AirlinePoint> allAirlineData = Parser.parseAirlineData(f);
        if (allAirlineData == null) {
            alert.showAndWait();
            return;
        } else {
            boolean allNull = true;
            for (AirlinePoint airline : allAirlineData) {
                if (airline != null) {
                    allNull = false;
                }
            }
            if (allNull) {
                alert.showAndWait();
                return;
            }
        }

        boolean allAdded = BBDatabase.addAirlinePointstoDB(allAirlineData);

        ArrayList<AirlinePoint> validAirlineData = Filter.getAllAirlinePointsfromDB();

        setAllAirlinePoints(validAirlineData);
        setAirlineActiveList(populateAirlineActiveList());

        if (!allAdded) {
            alert.showAndWait();
        }

        airlineActiveMenu.setItems(getAirlineActiveList());
        airlineActiveMenu.setValue(getAirlineActiveList().get(0));
        airlineCountryList = populateAirlineCountryList();  //populating from valid data in database
        airlineFilterMenu.setItems(airlineCountryList);
        airlineFilterMenu.setValue(airlineCountryList.get(0));
        updateAirlinesTable(validAirlineData);    //update with all airline data, including bad data
        exportAirlineMenuButton.setDisable(false);
        mainTabPane.getSelectionModel().select(airlineTab);



    }

    public void addRouteData(){
        //adds route data into route list
        // UNCOMMENT THIS WHEN THE PARSER IS FULLY WORKING FOR ROUTES
         File f;
         f = getFile("Add Route Data");
        if (f == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Oops!");
        alert.setHeaderText("Some data could not be added");
        alert.setContentText("Please check your input file");

         ArrayList<RoutePoint> myRouteData = Parser.parseRouteData(f);
        if (myRouteData == null) {
            alert.showAndWait();
            return;
        } else {
            boolean allNull = true;
            for (RoutePoint route : myRouteData) {
                if (route != null) {
                    allNull = false;
                }
            }
            if (allNull) {
                alert.showAndWait();
                return;
            }
        }

        boolean allAdded = BBDatabase.addRoutePointstoDB(myRouteData);

        if (!allAdded) {
            alert.showAndWait();
        }
        //WAITING ON METHOD TO GET ROUTES BACK FROM DB
        //ArrayList<AirlinePoint> validRouteData = Filter.getAllRoutePointsfromDB();
        setAllRoutePoints(myRouteData); //populating local data with all points
        updateRoutesTable(myRouteData);
        updateRoutesDropdowns();
        exportRouteMenuButton.setDisable(false);
        mainTabPane.getSelectionModel().select(routeTab);

        if (airportsFilled) {
            BBDatabase.linkRoutesandAirports(allPoints, allRoutePoints);

            allPoints  = BBDatabase.linkRoutesandAirports(allPoints, allRoutePoints);
            System.out.println(allRoutePoints.get(2));
            //System.out.println(allPoints.get(2).getOutgoingRoutes() + " " + allPoints.get(2).getAirportName() + allPoints.get(2));
            updateAirportsTable2(allPoints);
            updateRoutesTable2(allRoutePoints);
        }

        routesFilled = true;

    }

    public void addFlightData(){
        //adds flight data now using the database
        try {

            File f = getFile("Add Flight Data");

            if (f == null) {
                return;
            }
            ArrayList<FlightPoint> myFlightData = Parser.parseFlightData(f);
            if (myFlightData == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Oops!");
                alert.setHeaderText("Error in flight file");
                alert.setContentText("There was an error adding to flights.");
                alert.showAndWait();
                return;
            }

            BBDatabase.addFlighttoDB(myFlightData);
            updateFlightsTable(myFlightData);
            updateFlightsDropdowns();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Oops!");
            alert.setHeaderText("Error in adding data");
            alert.setContentText("Please check your input fields.");
            alert.showAndWait();
            //TODO
        }
        mainTabPane.getSelectionModel().select(flightTab);
    }


    //Adding data via the add buttons
    public void addSingleRoute() {
        //Brings up popup to insert route values
        try {
            Stage adderStage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RouteAddingPopUp.fxml"));
            root = loader.load();

            //use controller to control it
            RouteAddingPopUpController popUpController = loader.getController();
            popUpController.setAdderStage(adderStage);
            popUpController.setRoot(root);
            popUpController.control();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSingleAirline() {
        //Brings up popup to insert airline values
        try {
            Stage adderStage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AirlineAddingPopUp.fxml"));
            root = loader.load();

            //use controller to control it
            AirlineAddingPopUpController popUpController = loader.getController();
            popUpController.setAdderStage(adderStage);
            popUpController.setRoot(root);
            popUpController.control();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSingleAirport() {
        //Brings up popup to insert airport values
        try {
            Stage adderStage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AirportAddingPopUp.fxml"));
            root = loader.load();

            //use controller to control it
            AirportAddingPopUpController popUpController = loader.getController();
            popUpController.setAdderStage(adderStage);
            popUpController.setRoot(root);
            popUpController.control();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*******************************************************************************************************************
     *******************************************************************************************************************
     *************************************** Update Local data tables***************************************************
     *******************************************************************************************************************
     ******************************************************************************************************************/

    private void updateAirlinesTable(ArrayList<AirlinePoint> points){
        //Updates the view with a given set of airline points
        airlineTable.getItems().setAll(points);
        airlineTable.getItems().addListener(new ListChangeListener<DataPoint>() {
            @Override
            public void onChanged(Change<? extends DataPoint> c) {

                airlineTable.getColumns().get(0).setVisible(false);
                airlineTable.getColumns().get(0).setVisible(true);
            }
        });

        airlineIDCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, Integer>("airlineID"));
        airlineNameCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("airlineName"));
        airlineAliasCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("airlineAlias"));
        airlineIATACol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("iata"));
        airlineICAOCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("icao"));
        airlineCallsignCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("callsign"));
        airlineCountryCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("country"));
        airlineActCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("active"));



        airlineTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    String myText = airlineTable.getSelectionModel().getSelectedItem().getAirlineName();
                    Stage stage;
                    Parent root;
                    stage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/airlinePopup.fxml"));
                        root = loader.load();
                        AirlinePopUpController popUpController = loader.getController();
                        popUpController.setAirlinePoint(airlineTable.getSelectionModel().getSelectedItem());
                        popUpController.setUpPopUp();
                        popUpController.setStage(stage);

                        stage.setScene(new Scene(root));
                        stage.setTitle("View/Edit Data");
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

    private void updateRoutesTable2(ArrayList<RoutePoint> points){

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
        routeDstCountryCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("destAirportCountry"));
        routeSrcNameCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("srcAirportName"));
        routeDstNameCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("destAirportName"));

        routeTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Stage stage;
                    Parent root;
                    stage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/routePopup.fxml"));
                        root = loader.load();
                        RoutePopUpController popUpController = loader.getController();
                        popUpController.setRoutePoint(routeTable.getSelectionModel().getSelectedItem());
                        popUpController.setUpPopUp();
                        popUpController.setStage(stage);

                        stage.setScene(new Scene(root));
                        stage.setTitle("View/Edit Data");
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

    private void updateRoutesTable(ArrayList<RoutePoint> points){

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
//        routeSrcCountryCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("srcAirportCountry"));
//        routeDstCountryCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("destAirportCountry"));
//        routeSrcNameCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("srcAirportName"));
//        routeDstNameCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("dstAirportName"));

        routeTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Stage stage;
                    Parent root;
                    stage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/routePopup.fxml"));
                        root = loader.load();
                        RoutePopUpController popUpController = loader.getController();
                        popUpController.setRoutePoint(routeTable.getSelectionModel().getSelectedItem());
                        popUpController.setUpPopUp();
                        popUpController.setStage(stage);

                        stage.setScene(new Scene(root));
                        stage.setTitle("View/Edit Data");
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

    private void updateAirportsTable2(ArrayList<AirportPoint> points){


        //updates airpoirts table with a set of airpoints
        airportTable.getItems().setAll(points);

        airportIDCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, Integer>("airportID"));
        airportNameCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("airportName"));
        airportCityCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("airportCity"));
        airportCountryCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("airportCountry"));
        airportIATACol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("iata"));
        airportICAOCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("icao"));
        airportLatCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("latitude"));
        airportLongCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("longitude"));
        airportAltCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("altitude"));
        airportTimeCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("timeZone"));
        airportDSTCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("dst"));
        airportTZCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("tz"));
        //airportRouteNo.setCellValueFactory(new PropertyValueFactory<AirportPoint, Integer>("incomingRoutes"));
        airportIncCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, Integer>("incomingRoutes"));
        airportOutCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, Integer>("outgoingRoutes"));


        airportTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Stage stage;
                    Parent root;
                    stage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/airportPopup.fxml"));
                        root = loader.load();
                        AirportPopUpController popUpController = loader.getController();
                        popUpController.setAirportPoint(airportTable.getSelectionModel().getSelectedItem());
                        popUpController.setUpPopUp();
                        popUpController.setStage(stage);

                        stage.setScene(new Scene(root));
                        stage.setTitle("View/Edit Data");
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

    private void updateAirportsTable(ArrayList<AirportPoint> points){


        //updates airpoirts table with a set of airpoints
        airportTable.getItems().setAll(points);

        airportIDCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, Integer>("airportID"));
        airportNameCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("airportName"));
        airportCityCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("airportCity"));
        airportCountryCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("airportCountry"));
        airportIATACol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("iata"));
        airportICAOCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("icao"));
        airportLatCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("latitude"));
        airportLongCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("longitude"));
        airportAltCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("altitude"));
        airportTimeCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("timeZone"));
        airportDSTCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("dst"));
        airportTZCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("tz"));
//        airportRouteNo.setCellValueFactory(new PropertyValueFactory<AirportPoint, Integer>("incomingRoutes"));


        airportTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Stage stage;
                    Parent root;
                    stage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/airportPopup.fxml"));
                        root = loader.load();
                        AirportPopUpController popUpController = loader.getController();
                        popUpController.setAirportPoint(airportTable.getSelectionModel().getSelectedItem());
                        popUpController.setUpPopUp();
                        popUpController.setStage(stage);

                        stage.setScene(new Scene(root));
                        stage.setTitle("View/Edit Data");
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

    private void updateFlightsTable(ArrayList<FlightPoint> points){
        //updates FLIGHTS table with a set of FLIGHT POINTS
        Flight myFlight = new Flight(points);


        flightTable.getItems().addAll(myFlight);
        flightSourceCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("srcAirport"));
        flightDestCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("destAirport"));

        flightTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    exportFlightMenuButton.setDisable(false);

                    Flight pressedFlight = flightTable.getSelectionModel().getSelectedItem();

                    flightPointTable.getItems().setAll(pressedFlight.getFlightPoints());

                    flightPointTypeCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, Integer>("type"));
                    flightPointLocaleCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("localeID"));
                    flightPointAltitudeCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("altitude"));
                    flightPointLatitudeCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("latitude"));
                    flightPointLongitudeCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("longitude"));
                }

                }
                });

    }


    /*******************************************************************************************************************
     *******************************************************************************************************************
     *************************************** FILTERING BUTTONS**********************************************************
     *******************************************************************************************************************
     ******************************************************************************************************************/

    public void AirportFilterButtonPressed(){

        //NEED TO ADD CASE FOR NONE SELECTED
        String countrySelection = airportFilterByCountryMenu.getValue().toString();
        String searchQuery = airportSearchQuery.getText();
        ArrayList<AirportPoint> filteredPoints;

        if(countrySelection.equals("No values Loaded")){
            filteredPoints = Filter.getAllAirportPointsFromDB();
        }
        else{
            filteredPoints = Filter.filterAirportsBySelections(countrySelection, searchQuery);
        }

        allPoints  = BBDatabase.linkRoutesandAirports(filteredPoints, allRoutePoints);
       // updateAirportsTable2(allPoints);

        updateAirportsTable(allPoints);

    }

    public void airlinefilterButtonPressed() {
        //Gets airline values from selection and filters based on selection

        String countrySelection = airlineFilterMenu.getValue().toString();
        String activeSelection = airlineActiveMenu.getValue().toString();
        String searchQuery = airlineSearchQuery.getText().toString();
        ArrayList<AirlinePoint> allPoints;
        if (activeSelection =="Active"){
            activeSelection = "Y";
        }
        else if (activeSelection == "Inactive"){
            activeSelection = "N";
        }

        if(countrySelection.equals("No values Loaded") && activeSelection.equals("No values Loaded")){
            allPoints = Filter.getAllAirlinePointsfromDB();
        }
        else{
            ArrayList<String> menusPressed  = new ArrayList<String>();
            menusPressed.add(countrySelection);
            menusPressed.add(activeSelection);
            allPoints = Filter.filterAirlinesBySelections(menusPressed, searchQuery);
        }






        //allPoints = Filter.filterAirlinesBySelections(menusPressed, searchQuery);
        updateAirlinesTable(allPoints);






    }


//    public void flightsFilterButtonPressed(ActionEvent actionEvent) {
//        String departSelection = flightsFilterByDepartureMenu.getValue().toString();
//        String destSelection = flightsFilterByDestinationMenu.getValue().toString();
//
//        // To add country search funtioniallity will have to work out how to integrate countries into flights
//        //String countrySelection = flightsFilterByCountryMenu.getValue().toString();
//        String searchQuery = flightSearchQuery.getText().toString();
//        ArrayList<Flight> flights = new ArrayList<>();
//
//
//        ArrayList<String> menusPressed = new ArrayList<>(Arrays.asList(departSelection, destSelection));
//
//
//        flights = Filter.filterFlightsBySelections(menusPressed, searchQuery);
//
//        updateFlightsTable(flights);
//
//    }

    public void routesFilterButtonPressed(ActionEvent actionEvent) {
        String sourceSelection = routesFilterBySourceMenu.getValue().toString();
        String destSelection = routesFilterbyDestMenu.getValue().toString();
        String stopsSelection = routesFilterByStopsMenu.getValue().toString();
        String equipSelection = routesFilterbyEquipMenu.getValue().toString();
        String searchQuery = routesSearchMenu.getText().toString();
        ArrayList<RoutePoint> routePoints;

        if(sourceSelection.equals("No values Loaded") && destSelection.equals("No values Loaded") && stopsSelection.equals("No values Loaded") && equipSelection.equals("No values Loaded")){
            routePoints = Filter.getAllRoutePointsFromDB();
            updateRoutesTable(routePoints);
        }
        else{
            ArrayList<String> menusPressed = new ArrayList<>(Arrays.asList(sourceSelection, destSelection, stopsSelection, equipSelection));
            routePoints = Filter.filterRoutesBySelections(menusPressed, searchQuery);
        }


        //This is bad style but you win some and you lose some
        //(I lost this one)
        ArrayList<String> uniqueCountries = BBDatabase.performDistinctStringQuery("SELECT DISTINCT Src FROM ROUTE");
        ObservableList<String> myCountries =  FXCollections.observableArrayList(uniqueCountries);
        myCountries = addNullValue(myCountries);
        routesFilterBySourceMenu.setValue(myCountries.get(0));
        routesFilterBySourceMenu.setItems(myCountries);

        ArrayList<String> dstCodes = BBDatabase.performDistinctStringQuery("SELECT DISTINCT Dst FROM ROUTE");
        ObservableList<String> myDstCodes =  FXCollections.observableArrayList(dstCodes);
        myDstCodes = addNullValue(myDstCodes);
        routesFilterbyDestMenu.setValue(myDstCodes.get(0));
        routesFilterbyDestMenu.setItems(myDstCodes);

        ArrayList<String> stops = BBDatabase.performDistinctStringQuery("SELECT DISTINCT Stops FROM ROUTE");
        ObservableList<String> myStops =  FXCollections.observableArrayList(stops);
        myStops = addNullValue(myStops);
        routesFilterByStopsMenu.setValue(myStops.get(0));
        routesFilterByStopsMenu.setItems(myStops);

        ArrayList<String> equip = BBDatabase.performDistinctStringQuery("SELECT DISTINCT equipment FROM ROUTE");
        ObservableList<String> myEquip =  FXCollections.observableArrayList(equip);
        myEquip= addNullValue(myEquip);
        routesFilterbyEquipMenu.setValue(myEquip.get(0));
        routesFilterbyEquipMenu.setItems(myEquip);
       // ArrayList<String>

//        private ObservableList<String> routesFilterBySourceList  = FXCollections.observableArrayList("No values Loaded");
//        private ObservableList<String> routesFilterbyDestList  = FXCollections.observableArrayList("No values Loaded");
//        private ObservableList<String> routesFilterByStopsList  = FXCollections.observableArrayList("No values Loaded");
//        private ObservableList<String> routesFilterbyEquipList  = FXCollections.observableArrayList("No values Loaded");

        //);
        //System.out.println(uniqueCountries);



        updateRoutesTable(routePoints);



    }

    /*******************************************************************************************************************
     *******************************************************************************************************************
     *************************************** SEE ALL BUTTONS ***********************************************************
     *******************************************************************************************************************
     ******************************************************************************************************************/

    public void airlineSeeAllButtonPressed(ActionEvent actionEvent) {
        // gets all airline points and populates list
        ArrayList<AirlinePoint> allPoints = Filter.getAllAirlinePointsfromDB(); //airportTable.getItems()
        updateAirlinesTable(allPoints);

        airlineFilterMenu.setValue(airlineCountryList.get(0));
        airlineFilterMenu.setItems(airlineCountryList);

        airlineActiveMenu.setItems(airlineActiveList);
        airlineActiveMenu.setValue(airlineActiveList.get(0));


    }

    public void airportSeeAllButtonPressed(ActionEvent actionEvent) {
        //gets all airport points and updates view
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setContentText("See All button pressed ! " + selection);
//        alert.showAndWait();


        airportFilterByCountryMenu.setItems(airPortCountryList);
        airportFilterByCountryMenu.setValue(airPortCountryList.get(0));
        ArrayList<AirportPoint> allPoints = Filter.getAllAirportPointsFromDB(); //airportTable.getItems();
        // ArrayList<AirportPoint> filteredPoints = Filter.filterAirportCountry(allPoints, selection);
        updateAirportsTable(allPoints);


    }

    public void routesSeeAllDataButtonPressed(ActionEvent actionEvent) {

        ArrayList<RoutePoint> allPoints = Filter.getAllRoutePointsFromDB();
        updateRoutesTable(allPoints);
    }


    /*******************************************************************************************************************
     *******************************************************************************************************************
     *************************************** POPULATING LOCAL LISTS ON INITIALISATION***********************************
     *******************************************************************************************************************
     ******************************************************************************************************************/

    private ObservableList<String> populateAirportCountryList(){
        //Populates the dropdown of airline countries
        //ArrayList<AirportPoint> allPoints = getAllAirportPoints();
        ArrayList<String> countries = Filter.filterUniqueAirportCountries();
        ObservableList<String> countryList = FXCollections.observableArrayList(countries);
        countryList = addNullValue(countryList);
        return countryList;
    }

    private ObservableList<String> populateAirlineCountryList(){
        //Populates the airline countries list
        ArrayList<String> countries = Filter.filterUniqueAirLineCountries(Filter.getAllAirlinePointsfromDB());
        ObservableList<String> countryList = FXCollections.observableArrayList(countries);
        countryList = addNullValue(countryList); //we need to add a null value
        return countryList;
    }

    private ObservableList<String> populateAirlineActiveList(){
        //populates the activr airlines dropdown
        return FXCollections.observableArrayList("None", "Active", "Inactive");
    }

    private ObservableList<String> addNullValue(ObservableList<String> populatedList){
        //adds a null value at top of list
        populatedList.add(0, "None");
        return populatedList;
    }

    private ObservableList<String> removeNullValue(ObservableList<String> populatedList){
        //Removes null value from a list
        if (populatedList.get(0) == "None"){
            populatedList.remove(0);
        }

        return populatedList;
    }

    private void populateRoutesFilterBySourceList(){
        ArrayList<String> uniqueSources = new ArrayList<String>();
        String sql = "Src";
        uniqueSources = Filter.findDistinctStringFromTable(sql, "ROUTE");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = addNullValue(uniqueObservableSources);
        routesFilterBySourceMenu.setItems(uniqueObservableSources);
        routesFilterBySourceMenu.setValue(uniqueObservableSources.get(0));

    }

    private void populateRoutesFilterbyDestList(){
        ArrayList<String> uniqueSources = new ArrayList<String>();
        String sql = "Dst";
        uniqueSources = Filter.findDistinctStringFromTable(sql, "ROUTE");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = addNullValue(uniqueObservableSources);
        routesFilterbyDestMenu.setItems(uniqueObservableSources);
        routesFilterbyDestMenu.setValue(uniqueObservableSources.get(0));
    }

    private void populateRoutesFilterByStopsList(){
        ArrayList<String> uniqueSources = new ArrayList<String>();
        String sql = "Stops";
        uniqueSources = Filter.findDistinctStringFromTable(sql, "ROUTE");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = addNullValue(uniqueObservableSources);
        routesFilterByStopsMenu.setItems(uniqueObservableSources);
        routesFilterByStopsMenu.setValue(uniqueObservableSources.get(0));
    }

    private void populateRoutesFilterByEquipList(){
        ArrayList<String> uniqueEquip = new ArrayList<>();
        String sql = "Equipment";
        ArrayList<RoutePoint> myRoutes = Filter.getAllRoutePointsFromDB();
        for (RoutePoint route : myRoutes) {
            String[] equip = route.getEquipment().split("\\s+");
            for (String myEquip : equip) {
                if (!uniqueEquip.contains(myEquip)) {
                    uniqueEquip.add(myEquip);
                }
            }
        }
        Collections.sort(uniqueEquip.subList(1, uniqueEquip.size()));
//        uniqueSources = Filter.findDistinctStringFromTable(sql, "ROUTE");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueEquip);
        uniqueObservableSources = addNullValue(uniqueObservableSources);
        routesFilterbyEquipMenu.setItems(uniqueObservableSources);
        routesFilterbyEquipMenu.setValue(uniqueObservableSources.get(0));
    }

    private void populateFlightsFilterbyDepartList(){
        ArrayList<String> uniqueSources = new ArrayList<String>();
        String sql = "SrcICAO";
        uniqueSources = Filter.findDistinctStringFromTable(sql, "FLIGHT");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = addNullValue(uniqueObservableSources);
        flightsFilterByDepartureMenu.setItems(uniqueObservableSources);
        flightsFilterByDepartureMenu.setValue(uniqueObservableSources.get(0));
    }

    private void populateFlightsFilterbyDestList(){
        ArrayList<String> uniqueSources = new ArrayList<String>();
        String sql = "DstICAO";
        uniqueSources = Filter.findDistinctStringFromTable(sql, "FLIGHT");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = addNullValue(uniqueObservableSources);
        flightsFilterByDestinationMenu.setItems(uniqueObservableSources);
        flightsFilterByDestinationMenu.setValue(uniqueObservableSources.get(0));
    }

    private void populateFlightsFilterbyCountryList(){
        ArrayList<String> uniqueSources = new ArrayList<String>();
        String sql = "SrcICAO";
        uniqueSources = Filter.findDistinctStringFromTable(sql, "FLIGHT");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = addNullValue(uniqueObservableSources);
        flightsFilterByDepartureMenu.setItems(uniqueObservableSources);
        flightsFilterByDepartureMenu.setValue(uniqueObservableSources.get(0));
    }





    private void updateRoutesDropdowns() {
        populateRoutesFilterBySourceList();
        populateRoutesFilterbyDestList();
        populateRoutesFilterByStopsList();
        populateRoutesFilterByEquipList();
    }

    private void updateFlightsDropdowns() {
        populateFlightsFilterbyDepartList();
        populateFlightsFilterbyDestList();

        //-----NOT SURE WE CAN REALLY DO THIS YET-----
        //populateFlightsFilterbyCountryList();
    }


    @FXML
    private void exportAirportData(){

        ArrayList<DataPoint> myPoints = new ArrayList<DataPoint>(airportTable.getItems());
        Exporter.exportData(myPoints);

    }

    @FXML
    private void exportAirlineData(){

        ArrayList<DataPoint> myPoints = new ArrayList<DataPoint>(airlineTable.getItems());
        Exporter.exportData(myPoints);

    }

    @FXML
    private void exportRouteData(){

        ArrayList<DataPoint> myPoints = new ArrayList<DataPoint>(routeTable.getItems());
        Exporter.exportData(myPoints);

    }
    @FXML
    private void exportFlightData(){
        //Giver user a warning that it will only export the currently selected flight (the one in the flightpoint table)
        //NEED TO LABEL THE FLIGHT TABLES.

        ArrayList<DataPoint> myPoints = new ArrayList<DataPoint>(flightPointTable.getItems());
        Exporter.exportData(myPoints);

    }





}
