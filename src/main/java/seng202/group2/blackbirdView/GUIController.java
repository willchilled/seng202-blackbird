package seng202.group2.blackbirdView;


import javafx.collections.FXCollections;
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
import seng202.group2.blackbirdControl.Filter;
import seng202.group2.blackbirdModel.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javafx.fxml.FXMLLoader.load;

/**
 * Created by emr65 on 13/08/16.
 */
public class GUIController {

    //ObservableList<String> airPortCountryList = FXCollections.observableArrayList("Australia", "New Zealand", "Canada"); //maybe need this?
    //ObservableList<String> copy = FXCollections.observableArrayList("Australia", "New Zealand", "Canada"); //maybe need this?
    ObservableList<String> airPortCountryList = FXCollections.observableArrayList("No values Loaded");
    ObservableList<String> airlineCountryList = FXCollections.observableArrayList("No values Loaded");
    ObservableList<String> airlineActiveList  = FXCollections.observableArrayList("No values Loaded");

    ArrayList<AirportPoint> allPoints = new ArrayList<AirportPoint>();
    ArrayList<AirlinePoint> allAirlinePoints = new ArrayList<AirlinePoint>();

    public void setAllPoints(ArrayList<AirportPoint> points){this.allPoints = points;}

    public void setAirlineActiveList(ObservableList<String> activeList){this.airlineActiveList = activeList;}

    public ObservableList<String> getAirlineActiveList(){return airlineActiveList;}

    public ArrayList<AirportPoint> getAllPoints(){return allPoints;}

    public void setAllAirlinePoints(ArrayList<AirlinePoint> points){this.allAirlinePoints = points;}

   //public void setAirlineActiveListData(ArrayList<AirlinePoint> points){this.airlineActiveList = points;}

    public ArrayList<AirlinePoint> getAllAirlinePoints(){return allAirlinePoints;}


    @FXML
    private TabPane mainTabPane;

    @FXML
    private javafx.scene.control.MenuItem newProjMenu;

    @FXML
    private MenuItem addDataMenuButton;

    @FXML private TableView<AirportPoint> airportTable;
    @FXML private TableView<AirlinePoint> airlineTable;
    @FXML private TableView<RoutePoint> routeTable;
    //@FXML private TableView<AirportPoint> airportTable;



// AIRPORT Table columns
    @FXML private TableColumn airportIDCol;
    @FXML private TableColumn airportNameCol;
    @FXML private TableColumn airportCityCol;
    @FXML private TableColumn airportCountryCol;
    @FXML private TableColumn airportIATACol;
    @FXML private TableColumn airportICAOCol;
    @FXML private TableColumn airportLatCol;
    @FXML private TableColumn airportLongCol;
    @FXML private TableColumn airportAltCol;
    @FXML private TableColumn airportTimeCol;
    @FXML private TableColumn airportDSTCol;
    @FXML private TableColumn airportTZCol;

// AIRLINE Table columns
    @FXML private TableColumn airlineIDCol;
    @FXML private TableColumn airlineNameCol;
    @FXML private TableColumn airlineAliasCol;
    @FXML private TableColumn airlineIATACol;
    @FXML private TableColumn airlineICAOCol;
    @FXML private TableColumn airlineCallsignCol;
    @FXML private TableColumn airlineCountryCol;
    @FXML private TableColumn airlineActCol;


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


    // Filter Menu testing
    @FXML private ChoiceBox airportFilterMenu;
    @FXML private Button filterButton;
    @FXML private Button airportSeeAllButton;

    @FXML private ChoiceBox airlineFilterMenu;
    @FXML private ChoiceBox airlineActiveMenu;

    //Airline Popup Info
  //  @FXML private Text nameText;


    @FXML
    private void initialize(){

        airportFilterMenu.setValue(airPortCountryList.get(0));
       // airPortCountryList = populateCountryList();
        airportFilterMenu.setItems(airPortCountryList);
        airlineFilterMenu.setValue(airlineCountryList.get(0));
        airlineFilterMenu.setItems(airlineCountryList);

        airlineActiveMenu.setItems(airlineActiveList);
        airlineActiveMenu.setValue(airlineActiveList.get(0));
    }

    private File getFile() {

        File f;
        String cwd = System.getProperty("user.dir");

        JFileChooser jfc = new JFileChooser(cwd);
        int userChoice = jfc.showOpenDialog(null);

        switch (userChoice) {
            case JFileChooser.APPROVE_OPTION:
                f = jfc.getSelectedFile();
                if (f.exists() && f.isFile() && f.canRead()) {

                    return f;
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
        addDataMenuButton.setDisable(false);
        routeTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Route"));
        airlineTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Airline"));
        airportTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Airport"));
    }

    /*******************************************************************************************************************
     *******************************************************************************************************************
     *************************************** ADDING DATA ***************************************************************
     *******************************************************************************************************************
     ******************************************************************************************************************/

    public void addAirportData(){

        System.out.println("Add Airport Data");
        File f;
        f = getFile();
        ArrayList<AirportPoint> airportPoints = Parser.parseAirportData(f);
        setAllPoints(airportPoints); //imports setter, keeps track of all points
        updateAirportsTable(airportPoints);
        airPortCountryList = populateCountryList();
        airportFilterMenu.setItems(airPortCountryList);
        airportFilterMenu.setValue(airPortCountryList.get(0));



    }

    public void addAirlineData(){

       // System.out.println("Add Airline Data");

        File f;
        f = getFile();
        ArrayList<AirlinePoint> myAirlineData = Parser.parseAirlineData(f);

        setAllAirlinePoints(myAirlineData);

        setAirlineActiveList(populateAirlineActiveList());

        airlineActiveMenu.setItems(getAirlineActiveList());
        airlineActiveMenu.setValue(getAirlineActiveList().get(0));
        airlineCountryList = populateAirlineCountryList();
        airlineFilterMenu.setItems(airlineCountryList);
        airlineFilterMenu.setValue(airlineCountryList.get(0));
        updateAirlinesTable(myAirlineData);



    }

    public void addRouteData(){

        System.out.println("Add Route Data");

        // UNCOMMENT THIS WHEN THE PARSER IS FULLY WORKING FOR ROUTES
         File f;
         f = getFile();
         ArrayList<RoutePoint> myRouteData = Parser.parseRouteData(f);



        //COMMENT THIS OUT/DELETE ONCE PARSER WORKING
        //Creating a test airline to add in while waiting for the parser to be working
/**
        ArrayList<RoutePoint> routePoints = new ArrayList<RoutePoint>();

        RoutePoint testRoute = new RoutePoint("route airline", 56);
        testRoute.setSrcAirport("Src Airport hey");
        testRoute.setSrcAirportID(444);
        testRoute.setDstAirport("Dst airport ho");
        testRoute.setDstAirportID(55);
        testRoute.setCodeshare("Y");
        testRoute.setStops(0);
        //testRoute.setEquipment(new String[]{"777", "747"});


        RoutePoint testRoute2 = new RoutePoint("route airline 2", 66);
        testRoute2.setSrcAirport("Src Airport ho");
        testRoute2.setSrcAirportID(333);
        testRoute2.setDstAirport("Dst airport hey");
        testRoute2.setDstAirportID(779);
        testRoute2.setCodeshare("Y");
        testRoute2.setStops(2);
        // testRoute2.setEquipment(new String[]{"747"});


        routePoints.add(testRoute);
        routePoints.add(testRoute2);  //UP UNTIL HERE COMMENTED OUT / DELETED WHEN PARSER WORKING
**/
        routeTable.getItems().setAll(myRouteData);

        routeAirlineCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("airline"));
        routeAirlineIDCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, Integer>("airlineID"));
        routeSrcCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("srcAirport"));
        routeSrcIDCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("srcAirportID"));
        routeDestCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("dstAirport"));
        routeDestIDCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("dstAirportID"));
        routeCSCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("codeshare"));
        routeStopsCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, Integer>("stops"));
        routeEqCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String[]>("equipment"));


    }

    /*******************************************************************************************************************
     *******************************************************************************************************************
     *************************************** Update Local data tables***************************************************
     *******************************************************************************************************************
     ******************************************************************************************************************/

    private void updateAirlinesTable(ArrayList<AirlinePoint> points){

        airlineTable.getItems().setAll(points);
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
                    System.out.println(myText);
                    Stage stage;
                    Parent root;
                    stage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/airlinePopup.fxml"));
                        root = loader.load();
                        AirlinePopUpController popUpController = loader.getController();
                        popUpController.setAirlinePoint(airlineTable.getSelectionModel().getSelectedItem());
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


    /*******************************************************************************************************************
     *******************************************************************************************************************
     *************************************** FILTERING BUTTONS**********************************************************
     *******************************************************************************************************************
     ******************************************************************************************************************/

    public void AirportFilterButtonPressed(){
        /*
        System.out.println("HERE!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("filter button pressed ! " + selection);
        alert.showAndWait();
        */
        String selection = airportFilterMenu.getValue().toString();
        ArrayList<AirportPoint> allPoints = getAllPoints(); //airportTable.getItems();
        ArrayList<AirportPoint> filteredPoints = Filter.filterAirportCountry(allPoints, selection);
        updateAirportsTable(filteredPoints);

    }

    public void airlinefilterButtonPressed(ActionEvent actionEvent) {

        String countrySelection = airlineFilterMenu.getValue().toString();
        String activeSelection = airlineActiveMenu.getValue().toString();

        ArrayList<AirlinePoint> allPoints = getAllAirlinePoints();
        ArrayList<AirlinePoint> filteredPoints = new ArrayList<AirlinePoint>();

        if (countrySelection != "None"){
            filteredPoints = Filter.filterAirlineCountry(allPoints, countrySelection);
        }
        if (activeSelection != "None") {
            if (activeSelection != "None") {

                if (activeSelection == "Active") {
                    filteredPoints = Filter.activeAirlines(filteredPoints, true);

                } else {
                    filteredPoints = Filter.activeAirlines(filteredPoints, false);
                }
                // filteredPoints = Filter //add filter method


            }
        }
        updateAirlinesTable(filteredPoints);





    }

    public void routesFilterbyCountryButtonPressed(ActionEvent actionEvent) {
    }

    /*******************************************************************************************************************
     *******************************************************************************************************************
     *************************************** SEE ALL BUTTONS ***********************************************************
     *******************************************************************************************************************
     ******************************************************************************************************************/

    public void airlineSeeAllButtonPressed(ActionEvent actionEvent) {
        ArrayList<AirlinePoint> allPoints = getAllAirlinePoints(); //airportTable.getItems()
        updateAirlinesTable(allPoints);

        airlineFilterMenu.setValue(airlineCountryList.get(0));
        airlineFilterMenu.setItems(airlineCountryList);

        airlineActiveMenu.setItems(airlineActiveList);
        airlineActiveMenu.setValue(airlineActiveList.get(0));


    }

    public void airportSeeAllButtonPressed(ActionEvent actionEvent) {

        String selection = airportFilterMenu.getValue().toString();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("See All button pressed ! " + selection);
        alert.showAndWait();



        ArrayList<AirportPoint> allPoints = getAllPoints(); //airportTable.getItems();
        // ArrayList<AirportPoint> filteredPoints = Filter.filterAirportCountry(allPoints, selection);
        updateAirportsTable(allPoints);


    }

    public void routesSeeallDataButtonPressed(ActionEvent actionEvent) {}


    /*******************************************************************************************************************
     *******************************************************************************************************************
     *************************************** POPULATING LOCAL LISTS ON INITIALISATION***********************************
     *******************************************************************************************************************
     ******************************************************************************************************************/

    private ObservableList<String> populateCountryList(){
        //ArrayList<AirportPoint> allPoints = getAllPoints();
        ArrayList<String> countries = Filter.filterAirportCountries(getAllPoints());
        ObservableList<String> countryList = FXCollections.observableArrayList(countries);

        return countryList;
    }

    private ObservableList<String> populateAirlineCountryList(){
        ArrayList<String> countries = Filter.filterAirLineCountries(getAllAirlinePoints());
        ObservableList<String> countryList = FXCollections.observableArrayList(countries);
        countryList = addNullValue(countryList); //we need to add a null value
        //System.out.println(countryList);
        return countryList;
    }

    private ObservableList<String> populateAirlineActiveList(){
        ObservableList<String> airlineActiveList = FXCollections.observableArrayList("None", "Active", "Inactive");

        return airlineActiveList;
    }

    private ObservableList<String> addNullValue(ObservableList<String> populatedList){
        populatedList.add(0, "None");
        return populatedList;
    }

    private ObservableList<String> removeNullValue(ObservableList<String> populatedList){
        if (populatedList.get(0) == "None"){
            populatedList.remove(0);
        }

        return populatedList;
    }




}
