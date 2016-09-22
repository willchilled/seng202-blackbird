package seng202.group2.blackbirdView;


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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.blackbirdControl.*;
import seng202.group2.blackbirdModel.*;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static javafx.fxml.FXMLLoader.load;
import static seng202.group2.blackbirdModel.DataTypes.AIRLINEPOINT;

/**
 * Created by emr65 on 13/08/16.
 */
public class GUIController {

    //ObservableList<String> airPortCountryList = FXCollections.observableArrayList("Australia", "New Zealand", "Canada"); //maybe need this?
    //ObservableList<String> copy = FXCollections.observableArrayList("Australia", "New Zealand", "Canada"); //maybe need this?
    ObservableList<String> airPortCountryList = FXCollections.observableArrayList("No values Loaded");
    ObservableList<String> airlineCountryList = FXCollections.observableArrayList("No values Loaded");
    ObservableList<String> airlineActiveList  = FXCollections.observableArrayList("No values Loaded");

    ObservableList<String> routesFilterBySourceList  = FXCollections.observableArrayList("No values Loaded");
    ObservableList<String> routesFilterbyDestList  = FXCollections.observableArrayList("No values Loaded");
    ObservableList<String> routesFilterByStopsList  = FXCollections.observableArrayList("No values Loaded");
    ObservableList<String> routesFilterbyEquipList  = FXCollections.observableArrayList("No values Loaded");



    ArrayList<AirportPoint> allPoints = new ArrayList<>();
    //ArrayList<AirportPoint> allValidPoints = new ArrayList<>();

    ArrayList<DataPoint> allAirlinePoints = new ArrayList<>();
    ArrayList<DataPoint> allRoutePoints = new ArrayList<DataPoint>();

//    public ArrayList<AirportPoint> getAllValidPoints() {
//        return allValidPoints;
//    }
//
//    public void setAllValidPoints(ArrayList<AirportPoint> allValidPoints) {
//        this.allValidPoints = allValidPoints;
//    }
    @FXML
    private TabPane mainTabPane;
    @FXML
    private javafx.scene.control.MenuItem newProjMenu;
    @FXML
    private MenuItem addDataMenuButton;
    @FXML private TableView<DataPoint> airportTable;
    @FXML private TableView<DataPoint> airlineTable;
    @FXML private TableView<DataPoint> routeTable;
    @FXML private TableView<Flight> flightTable;
    @FXML private TableView<DataPoint> flightPointTable;
// AIRPORT Table columns
    @FXML private TableColumn airportIDCol;
    @FXML private TableColumn airportNameCol;
    @FXML private TableColumn airportCityCol;
    @FXML private TableColumn airportCountryCol;
    @FXML private TableColumn airportIATACol;
    @FXML private TableColumn airportICAOCol;
    @FXML private TableColumn airportLatCol;

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
    @FXML private ComboBox airportFilterMenu;
    @FXML private TextField airportSearchQuery;
    @FXML private Button filterButton;
    @FXML private Button airportSeeAllButton;
    @FXML private ComboBox airlineFilterMenu;
    @FXML private ComboBox airlineActiveMenu;
    @FXML private TextField airlineSearchQuery;
    @FXML private ComboBox routesFilterBySourceMenu;
    @FXML private ComboBox routesFilterbyDestMenu;
    @FXML private ComboBox routesFilterByStopsMenu;
    @FXML private ComboBox routesFilterbyEquipMenu;
    @FXML private TextField routesSearchMenu;

    public void setAllRoutesFilterBySourceList(ArrayList<String> sourceList){ this.routesFilterBySourceList = routesFilterBySourceList;}

    public ObservableList<String> getRoutesFilterBySourceList(){return routesFilterBySourceList;}

    public ObservableList<String> getRoutesFilterbyDestList(){return routesFilterbyDestList;}

    public void setRoutesFilterbyDestList(ArrayList<String> destList){ this.routesFilterbyDestList = routesFilterbyDestList;}

    public void SetRoutesFilterByStopsList(ArrayList<String> stopsList){ this.routesFilterByStopsList = routesFilterByStopsList;}

    public ObservableList<String> getRoutesFilterByStopsList(){return routesFilterByStopsList;}

    public ObservableList<String> getRoutesFilterbyEquipList(){return routesFilterbyEquipList;}

    public void setRoutesFilterbyEquipList(ArrayList<String> equipList){ this.routesFilterbyEquipList = routesFilterbyEquipList;}

;

    public ArrayList<DataPoint> getAllRoutePoints() {
        return allRoutePoints;
    }

    public void setAllRoutePoints(ArrayList<DataPoint> allRoutePoints){
        this.allRoutePoints = allRoutePoints;
    }

    public ObservableList<String> getAirlineActiveList(){return airlineActiveList;}

    public void setAirlineActiveList(ObservableList<String> activeList){this.airlineActiveList = activeList;}

    public ArrayList<AirportPoint> getAllAirportPoints(){return allPoints;}

    public void setAllAirportPoints(ArrayList<AirportPoint> points){this.allPoints = points;}

    public ArrayList<DataPoint> getAllAirlinePoints(){return allAirlinePoints;}

    public void setAllAirlinePoints(ArrayList<DataPoint> points){this.allAirlinePoints = points;}

    //Airline Popup Info
  //  @FXML private Text nameText;

    /**
     * Dont delete this method, it is needed
     */
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

    }

    private File getFile() {
        //gets a file of a specified type
        File myFile;
        String cwd = System.getProperty("user.dir");

        JFileChooser jfc = new JFileChooser(cwd);
        int userChoice = jfc.showOpenDialog(null);

        switch (userChoice) {
            case JFileChooser.APPROVE_OPTION:
                myFile = jfc.getSelectedFile();
                if (myFile.exists() && myFile.isFile() && myFile.canRead()) {

                    return myFile;
                }
            case JFileChooser.CANCEL_OPTION:
                // fall through
            case JFileChooser.ERROR_OPTION:
                System.out.println("ERROR IN FILE");
        }
        return null;
    }

    public void show(){

        mainTabPane.setVisible(true);
        //SQQliteJDBC myDb = new SQQliteJDBC();
        //SQLiteJDBC.dropTables();
        DataBaseRefactor.createTables();
        //SQQliteJDBC.dropTables();
        addDataMenuButton.setDisable(false);
        //addDataMenuButton.setDisable(true);


        routeTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Route"));
        airlineTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Airline"));
        airportTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Airport"));
        flightTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Flight"));

    }

    /*******************************************************************************************************************
     *******************************************************************************************************************
     *************************************** ADDING DATA ***************************************************************
     *******************************************************************************************************************
     ******************************************************************************************************************/

    //Adding data via Files
//    public void addALLData(){
//        //MASTER OVERRIDE FUNCTION DONT SCREW WITH THIS UNLESS YOU ARE A WIZARD
//        String cwd = System.getProperty("user.dir");
//        String airlinesFileString = cwd + "/TestFiles/airlines.txt";;
//        String airportsFileString = cwd + "/TestFiles/airports.txt";
//        String routesFileString = cwd + "/TestFiles/route.txt";
//        String flightsFileString = cwd + "/TestFiles/flight.txt";
//
//
//        File airlinesFile = new File(airlinesFileString);
//        File airportsFile = new File(airportsFileString);
//        File routesFile = new File(routesFileString);
//        File flightsFile = new File(flightsFileString);
//
//        ArrayList<AirlinePoint> airlinePoints = Parser.parseAirlineData(airlinesFile);
//        ArrayList<AirportPoint> airportPoints = Parser.parseAirportData(airportsFile);
//        ArrayList<RoutePoint> routePoints = Parser.parseRouteData(routesFile);
//        ArrayList<FlightPoint> flightPoints = Parser.parseFlightData(flightsFile);
//
//
//updateRefactoredAirportsTable
//        BBDatabase.deleteDBFile();
//        BBDatabase.createTables();
//        BBDatabase.addAirlinePointstoDB(airlinePoints);
//        BBDatabase.addAirportPointsToDB(airportPoints);
//        BBDatabase.addRoutePointstoDB(routePoints);
//        try {
//            BBDatabase.addFlighttoDB(flightPoints);
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(new JPanel(), "There was some incorrect data in your flight file.",
//                    "Error", JOptionPane.ERROR_MESSAGE);
//            System.err.println("Bad flight data");
//        }
//
//        airportPoints = Filter.getAllAirportPointsFromDB();
//        setAllAirportPoints(airportPoints); //imports setter, keeps track of all points
//
//        updateAirportsTable(airportPoints);
//        airPortCountryList = populateAirportCountryList();
//        airportFilterMenu.setItems(airPortCountryList);
//        airportFilterMenu.setValue(airPortCountryList.get(0));
//
//        airlinePoints = FilterRefactor.getAllPoints("Airline");
//
//
//        //System.out.println(myAirlineData);
//        // myAirlineData = Filter.getAllAirlinePointsfromDB();
//        setAllAirlinePoints(airlinePoints);
//
//        airlineActiveList = FXCollections.observableArrayList("None", "Active", "Inactive");
//        airlineActiveMenu.setItems(airlineActiveList);
//        airlineActiveMenu.setValue(airlineActiveList.get(0));
//        setAirlineActiveList(airlineActiveList);
//updateRefactoredAirportsTable
//        airlineActiveMenu.setItems(getAirlineActiveList());
//        airlineActiveMenu.setValue(getAirlineActiveList().get(0));
//        airlineCountryList = populateAirlineCountryList();
//        airlineFilterMenu.setItems(airlineCountryList);
//        airlineFilterMenu.setValue(airlineCountryList.get(0));
//        updateAirlinesTable(airlinePoints);
//
//        updateRoutesTable(routePoints);
//        updateRoutesDropdowns();
//
//        updateFlightsTable(flightPoints);
//
//
//    }

    public void addAirportData(){
        //Adds the aiport data into the filter menu, updates airport Country list
        System.out.println("Add Airport Data");
        File f;
        f = getFile();
//        ArrayList<AirportPoint> allairportPoints = Parser.parseAirportData(f);
//        BBDatabase.addAirportPointsToDB(allairportPoints);
//        ArrayList<AirportPoint> validairportPoints = Filter.getAllAirportPointsFromDB();
//
//        setAllAirportPoints(validairportPoints); //adding all airport data, including bad data
//        updateAirportsTable(validairportPoints);
//
//        airPortCountryList = populateAirportCountryList();  //populating from valid data in database
//        airportFilterMenu.setItems(airPortCountryList);
//        airportFilterMenu.setValue(airPortCountryList.get(0));

        /* Refactored version */
        ArrayList<DataPoint> myAirportPoints = ParserRefactor.parseFile(f, DataTypes.AIRPORTPOINT);
        DataBaseRefactor.insertDataPoints(myAirportPoints);
        ArrayList<DataPoint> validAirportPoints = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        updateRefactoredAirportsTable(validAirportPoints);
        updateAirportFields();

    }

    /**
     * Updates the Airport dropdowns by populating them with current data from the database
     */
    public void updateAirportFields(){

        airlineActiveList = FXCollections.observableArrayList("None", "Active", "Inactive");
        airlineActiveMenu.setItems(airlineActiveList);
        airlineActiveMenu.setValue(airlineActiveList.get(0));


        airPortCountryList = populateAirportCountryList();  //populating from valid data in database
        airportFilterMenu.setItems(airPortCountryList);
        airportFilterMenu.setValue(airPortCountryList.get(0));

    }

    private ObservableList<String> populateAirlineCountryList(){
        //Populates the airline countries list
        ArrayList<String> countries = Filter.filterUniqueAirLineCountries(Filter.getAllAirlinePointsfromDB());
        ObservableList<String> countryList = FXCollections.observableArrayList(countries);
        countryList = addNullValue(countryList); //we need to add a null value
        //System.out.println(countryList);
        return countryList;
    }

    private void updateRefactoredAirportsTable(ArrayList<DataPoint> validAirportPoints) {

        airportTable.getItems().setAll(validAirportPoints);


        airportIDCol.setCellValueFactory(new PropertyValueFactory<DataPoint, Integer>("airportID"));
        airportNameCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("airportName"));
        airportCityCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("airportCity"));
        airportCountryCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("airportCountry"));
        airportIATACol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("iata"));
        airportICAOCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("icao"));
        airportLatCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("latitude"));
        airportLongCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("longitude"));
        airportAltCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("altitude"));
        airportTimeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("timeZone"));
        airportDSTCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("dst"));
        airportTZCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("tz"));

        airportTable.getItems().addListener(new ListChangeListener<DataPoint>() {
            //This refreshes the current table
            @Override
            public void onChanged(Change<? extends DataPoint> c) {
                airportTable.getColumns().get(0).setVisible(false);
                airportTable.getColumns().get(0).setVisible(true);
            }
        });


        airportTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Stage stage;
                    Parent root;
                    stage = new Stage();
                    try {
                        AirportPoint myPoint = (AirportPoint) airportTable.getSelectionModel().getSelectedItem();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/airportPopup.fxml"));
                        root = loader.load();
                        AirportPopUpController popUpController = loader.getController();
                        popUpController.setAirportPoint(myPoint);
                        popUpController.setUpPopUp();

                        stage.setScene(new Scene(root));
                        stage.setTitle("My Popup test");
                        stage.initModality(Modality.NONE);
                        stage.initOwner(null);

                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                        //System.out.println("AH NO!");
                    }

                }
            }
        });
    }

    public void addAirlineData(){
        //Adds airline data into filter menu, updates airline data list

        //Adds data into DataBase thus filtering it against database constraints, then pulling out remaining "good"
        // data to populate the GUI.
        File f;
        f = getFile();
        ArrayList<DataPoint> myPoints = ParserRefactor.parseFile(f, DataTypes.AIRLINEPOINT);
        DataBaseRefactor.insertDataPoints(myPoints);
        ArrayList<DataPoint> validAirlineData = FilterRefactor.getAllPoints(DataTypes.AIRLINEPOINT);
        System.out.println(validAirlineData.get(0).toString());

        //Populates DropDowns according to data
        updateAirlineFields();

       // Populates Rows in the Airline Table
        updateAirlinesTable(validAirlineData);    //update with all airline data, including bad data
    }

    public void updateAirlineFields(){

        airlineActiveList = FXCollections.observableArrayList("None", "Active", "Inactive");
        airlineActiveMenu.setItems(airlineActiveList);
        airlineActiveMenu.setValue(airlineActiveList.get(0));


        airlineCountryList = populateAirlineCountryList();  //populating from valid data in database
        airlineFilterMenu.setItems(airlineCountryList);
        airlineFilterMenu.setValue(airlineCountryList.get(0));

    }


    public void addRouteData(){
        //adds route data into route list

        System.out.println("Add Route Data");

        // UNCOMMENT THIS WHEN THE PARSER IS FULLY WORKING FOR ROUTES
         File f;
         f = getFile();

         ArrayList<DataPoint> myRouteData = ParserRefactor.parseFile(f, DataTypes.ROUTEPOINT);
        DataBaseRefactor.insertDataPoints(myRouteData);
        //BBDatabase.addRoutePointstoDB(myRouteData);
        //WAITING ON METHOD TO GET ROUTES BACK FROM DB
        //ArrayList<AirlinePoint> validRouteData = Filter.getAllRoutePointsfromDB();
        //setAllRoutePoints(myRouteData); //populating local data with all points
        updateRoutesTable(myRouteData);
        //updateRoutesDropdowns();

    }

    public void addFlightData(){
        //adds flight data now using the database
        System.out.println("Add Flight Data");




        //try {
            File f = getFile();
            ArrayList<DataPoint> myFlightData = ParserRefactor.parseFile(f, "FlightPoint");
            //BBDatabase.addFlighttoDB(myFlightData);
            DataBaseRefactor.insertDataPoints(myFlightData);
        ArrayList<DataPoint> flightdata = FilterRefactor.getAllPoints("FlightPoint");

            updateFlightsTable(myFlightData);
        //updateFlightsDropdowns();
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(new JPanel(), "There was some incorrect data in your flight file.",
//                    "Error", JOptionPane.ERROR_MESSAGE);
//            System.out.println("Error in flight, aborted.");
//        }
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

    private void updateAirlinesTable(ArrayList<DataPoint> points){
        //Updates the view with a given set of airline points
        airlineTable.getItems().setAll(points);

        airlineIDCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("airlineID"));
        airlineNameCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("airlineName"));
        airlineAliasCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("airlineAlias"));
        airlineIATACol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("iata"));
        airlineICAOCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("icao"));
        airlineCallsignCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("callsign"));
        airlineCountryCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("country"));
        airlineActCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("active"));

        airlineTable.getItems().addListener(new ListChangeListener<DataPoint>() {
            //This refreshes the current table
            @Override
            public void onChanged(Change<? extends DataPoint> c) {
                airlineTable.getColumns().get(0).setVisible(false);
                airlineTable.getColumns().get(0).setVisible(true);
            }
        });

        airlineTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

                    //Cast to airline Point to be able to get the name of the point
                    AirlinePoint myPoint = (AirlinePoint) airlineTable.getSelectionModel().getSelectedItem();
                    String myText = myPoint.getAirlineName();
                    Stage stage;
                    Parent root;
                    stage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/airlinePopup.fxml"));
                        root = loader.load();
                        AirlinePopUpController popUpController = loader.getController();
                        popUpController.setAirlinePoint(myPoint);
                        popUpController.setUpPopUp();

                        stage.setScene(new Scene(root));
                        stage.setTitle("View/Edit Data");
                        stage.initModality(Modality.NONE);
                        stage.initOwner(null);

                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                        //System.out.println("AH NO!");
                    }

                }
            }
        });
    }

    private void updateRoutesTable(ArrayList<DataPoint> points){

        routeTable.getItems().setAll(points);

        routeAirlineCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("airline"));
        routeAirlineIDCol.setCellValueFactory(new PropertyValueFactory<DataPoint, Integer>("airlineID"));
        routeSrcCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("srcAirport"));
        routeSrcIDCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("srcAirportID"));
        routeDestCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("dstAirport"));
        routeDestIDCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("dstAirportID"));
        routeCSCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("codeshare"));
        routeStopsCol.setCellValueFactory(new PropertyValueFactory<DataPoint, Integer>("stops"));
        routeEqCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String[]>("equipment"));

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
                    Stage stage;
                    Parent root;
                    stage = new Stage();
                    RoutePoint myPoint = (RoutePoint) routeTable.getSelectionModel().getSelectedItem();
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
                        //System.out.println("AH NO!");
                    }

                }
            }
        });


    }

    private void updateAirportsTable(ArrayList<AirportPoint> points){

        System.out.println(points.get(0).toString());

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


//        airportTable.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event){
//                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
//                    Stage stage;
//                    Parent root;
//                    stage = new Stage();
//                    try {
//                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/airportPopup.fxml"));
//                        root = loader.load();
//                        AirportPopUpController popUpController = loader.getController();
//                        popUpController.setAirportPoint(airportTable.getSelectionModel().getSelectedItem());
//                        popUpController.setUpPopUp();
//
//                        stage.setScene(new Scene(root));
//                        stage.setTitle("My Popup test");
//                        stage.initModality(Modality.NONE);
//                        stage.initOwner(null);
//
//                        stage.show();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        //System.out.println("AH NO!");
//                    }
//
//                }
//            }
//        });


    }

    private void updateFlightsTable(ArrayList<DataPoint> points){
        //updates FLIGHTS table with a set of FLIGHT POINTS
        Flight myFlight = new Flight(points);


        flightTable.getItems().addAll(myFlight);
        flightSourceCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("srcAirport"));
        flightDestCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("destAirport"));

        flightTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    //exportFlightMenuButton.setDisable(false);

                    Flight pressedFlight = flightTable.getSelectionModel().getSelectedItem();

                    flightPointTable.getItems().setAll(pressedFlight.getFlightPoints());

                    flightPointTypeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("localType"));
                    flightPointLocaleCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("localeID"));
                    flightPointAltitudeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, Integer>("altitude"));
                    flightPointLatitudeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("latitude"));
                    flightPointLongitudeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("longitude"));
                }

            }
        });

    }


    // I HAVE BEEN COMENTED OUT BECAUSE I AM NOT WORKING

//    private void updateFlightsTable(ArrayList<FlightPoint> points){
//        //updates FLIGHTS table with a set of FLIGHT POINTS
//        Flight myFlight = new Flight(points);
//
//
//        flightTable.getItems().addAll(myFlight);
//        flightSourceCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("srcAirport"));
//        flightDestCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("destAirport"));
//
//        flightTable.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event){
//                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
//
//                    Flight pressedFlight = flightTable.getSelectionModel().getSelectedItem();
//
//                    flightPointTable.getItems().setAll(pressedFlight.getFlightPoints());
//
//                    flightPointTypeCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, Integer>("type"));
//                    flightPointLocaleCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("localeID"));
//                    flightPointAltitudeCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("altitude"));
//                    flightPointLatitudeCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("latitude"));
//                    flightPointLongitudeCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("longitude"));
//                }
//
//                }
//                });
//
//    }


    /*******************************************************************************************************************
     *******************************************************************************************************************
     *************************************** FILTERING BUTTONS**********************************************************
     *******************************************************************************************************************
     ******************************************************************************************************************/

    public void AirportFilterButtonPressed(){
        //Gets values from selection and filters based on selection
        /*
        System.out.println("HERE!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("filter button pressed ! " + selection);
        alert.showAndWait();
        */ //NEED TO ADD CASE FOR NONE SELECTED
        String countrySelection = airportFilterMenu.getValue().toString();
        String searchQuery = airportSearchQuery.getText();
        System.out.println(searchQuery);


//        ArrayList<AirportPoint> filteredPoints = Filter.filterAirportsBySelections(countrySelection, searchQuery);
//        updateAirportsTable(filteredPoints);

        ArrayList<String> menusPressed = new ArrayList<>(Arrays.asList(countrySelection));
        ArrayList<DataPoint> myFilteredPoints = FilterRefactor.filterSelections(menusPressed, searchQuery, DataTypes.AIRPORTPOINT);
        updateRefactoredAirportsTable(myFilteredPoints);


        //ArrayList<AirportPoint> allPoints = getAllAirportPoints(); //airportTable.getItems();
        //ArrayList<AirportPoint> filteredPoints = Filter.filterAirportCountry(allPoints, selection);
        //updateAirportsTable(filteredPoints);

    }

    public void airlinefilterButtonPressed(ActionEvent actionEvent) {
        //Gets airline values from selection and filters based on selection

        String countrySelection = airlineFilterMenu.getValue().toString();
        String activeSelection = airlineActiveMenu.getValue().toString();
        String searchQuery = airlineSearchQuery.getText().toString();

        if (activeSelection =="Active"){
            activeSelection = "Y";
        }
        else if (activeSelection == "Inactive"){
            activeSelection = "N";
        }

        ArrayList<String> menusPressed  = new ArrayList<String>();
        menusPressed.add(countrySelection);
        menusPressed.add(activeSelection);


        ArrayList<DataPoint> allPoints = FilterRefactor.filterSelections(menusPressed, searchQuery, DataTypes.AIRLINEPOINT);
        updateAirlinesTable(allPoints);


    }

    public void routesFilterButtonPressed(ActionEvent actionEvent) {
        System.out.println("I HAVE BEEN PRESSED");
        String sourceSelection = routesFilterBySourceMenu.getValue().toString();
        String destSelection = routesFilterbyDestMenu.getValue().toString();
        String stopsSelection = routesFilterByStopsMenu.getValue().toString();
        String equipSelection = routesFilterbyEquipMenu.getValue().toString();
        String searchQuery = routesSearchMenu.getText().toString();
        ArrayList<DataPoint> routePoints = new ArrayList<>();


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
            routePoints = FilterRefactor.filterSelections(menusPressed, searchQuery, DataTypes.ROUTEPOINT);
            //routePoints = Filter.filterRoutesBySelections(menusPressed, searchQuery);


        }
        else{
            routePoints = getAllRoutePoints();
        }

        updateRoutesTable(routePoints);



    }

    /*******************************************************************************************************************
     *******************************************************************************************************************
     *************************************** SEE ALL BUTTONS ***********************************************************
     *******************************************************************************************************************
     ******************************************************************************************************************/

    public void airlineSeeAllButtonPressed(ActionEvent actionEvent) {
        // gets all airline points and populates list
        ArrayList<DataPoint> allPoints = FilterRefactor.getAllPoints(DataTypes.AIRLINEPOINT); //airportTable.getItems()
        updateAirlinesTable(allPoints);

        airlineFilterMenu.setValue(airlineCountryList.get(0));
        airlineFilterMenu.setItems(airlineCountryList);

        airlineActiveMenu.setItems(airlineActiveList);
        airlineActiveMenu.setValue(airlineActiveList.get(0));


    }

    public void airportSeeAllButtonPressed(ActionEvent actionEvent) {
        //gets all airport points and updates view
        String selection = airportFilterMenu.getValue().toString();
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setContentText("See All button pressed ! " + selection);
//        alert.showAndWait();



        ArrayList<DataPoint> allPoints = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT); //airportTable.getItems();
        // ArrayList<AirportPoint> filteredPoints = Filter.filterAirportCountry(allPoints, selection);
        updateRefactoredAirportsTable(allPoints);


    }

    public void routesSeeAllDataButtonPressed(ActionEvent actionEvent) {
        updateRoutesTable(getAllRoutePoints());
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
        ArrayList<String> uniqueSources = new ArrayList<String>();
        String sql = "EquipmentName";
        uniqueSources = Filter.findDistinctStringFromTable(sql, "EQUIPMENT");
        ObservableList<String> uniqueObservableSources = FXCollections.observableArrayList(uniqueSources);
        uniqueObservableSources = addNullValue(uniqueObservableSources);
        routesFilterbyEquipMenu.setItems(uniqueObservableSources);
        routesFilterbyEquipMenu.setValue(uniqueObservableSources.get(0));
    }



    private void updateRoutesDropdowns() {
        System.out.println("Ahoy");
        populateRoutesFilterBySourceList();
        populateRoutesFilterbyDestList();
        populateRoutesFilterByStopsList();
        populateRoutesFilterByEquipList();
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
