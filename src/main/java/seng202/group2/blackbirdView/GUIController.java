package seng202.group2.blackbirdView;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group2.blackbirdControl.Filter;
import seng202.group2.blackbirdModel.*;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

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

    public void setAllAirportPoints(ArrayList<AirportPoint> points){this.allPoints = points;}

    public void setAirlineActiveList(ObservableList<String> activeList){this.airlineActiveList = activeList;}

    public ObservableList<String> getAirlineActiveList(){return airlineActiveList;}

    public ArrayList<AirportPoint> getAllAirportPoints(){return allPoints;}

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
   // @FXML private TableView<AirportPoint> airportTable;



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




    @FXML
    private void initialize(){
        //Automatic initialisation when the program starts

        airportFilterMenu.setValue(airPortCountryList.get(0));
        airportFilterMenu.setItems(airPortCountryList);
        airlineFilterMenu.setValue(airlineCountryList.get(0));
        airlineFilterMenu.setItems(airlineCountryList);
        airlineActiveMenu.setItems(airlineActiveList);
        airlineActiveMenu.setValue(airlineActiveList.get(0));
    }

    private File getFile() {
        //gets a file of a specified type
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
        //SQQliteJDBC myDb = new SQQliteJDBC();
        //SQLiteJDBC.dropTables();
        BBDatabase.createTables();
        //SQQliteJDBC.dropTables();
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
        //Adds the aiport data into the filter menu, updates airport Country list
        System.out.println("Add Airport Data");
        File f;
        f = getFile();
        ArrayList<AirportPoint> airportPoints = Parser.parseAirportData(f);
        BBDatabase.addAiportPortsToDB(airportPoints);
        airportPoints = Filter.getAllAirportPointsFromDB();
        setAllAirportPoints(airportPoints); //imports setter, keeps track of all points

        updateAirportsTable(airportPoints);
        airPortCountryList = populateAirportCountryList();
        airportFilterMenu.setItems(airPortCountryList);
        airportFilterMenu.setValue(airPortCountryList.get(0));

     /**
      * Test Airport Points

        ArrayList<AirportPoint> airportPoints = new ArrayList<AirportPoint>();

        AirportPoint testAirport = new AirportPoint(233, "TestAirportName");
        testAirport.setAirportCity("Christchurch");
        testAirport.setAirportCountry("New Zealand");
        testAirport.setIata("CHC");
        testAirport.setIcao("NZCH");
        testAirport.setLatitude(43.489358f);
        testAirport.setLongitude(172.532225f);
        testAirport.setAltitude(127);
        testAirport.setTimeZone(12);
        testAirport.setDst("Z");
        testAirport.setTz("Pacific/Auckland");

        AirportPoint testAirport2 = new AirportPoint(355, "TestAirportName2");
        testAirport2.setAirportCity("London");
        testAirport2.setAirportCountry("New Zealand");
        testAirport2.setIata("CHC");
        testAirport2.setIcao("NZCH");
        testAirport2.setLatitude(43.489358f);
        testAirport2.setLongitude(172.532225f);
        testAirport2.setAltitude(127);
        testAirport2.setTimeZone(12);
        testAirport2.setDst("Z");
        testAirport2.setTz("Pacific/Auckland");

        airportPoints.add(testAirport);
        airportPoints.add(testAirport2); **/



    }

    public void addAirlineData(){
        //Adds airline data into filter menu, updates airline data list

       // System.out.println("Add Airline Data");

        File f;
        f = getFile();
        ArrayList<AirlinePoint> myAirlineData = Parser.parseAirlineData(f);
        BBDatabase.addAirlinePointstoDB(myAirlineData);

        myAirlineData = Filter.getAllAirlinePointsfromDB();
        //System.out.println(myAirlineData);
       // myAirlineData = Filter.getAllAirlinePointsfromDB();
        setAllAirlinePoints(myAirlineData);
        setAirlineActiveList(populateAirlineActiveList());

        airlineActiveMenu.setItems(getAirlineActiveList());
        airlineActiveMenu.setValue(getAirlineActiveList().get(0));
        airlineCountryList = populateAirlineCountryList();
        airlineFilterMenu.setItems(airlineCountryList);
        airlineFilterMenu.setValue(airlineCountryList.get(0));
        updateAirlinesTable(myAirlineData);


        /*
        //Creating a test airline to add in while waiting for the parser to be working
        ArrayList<AirlinePoint> airlinePoints = new ArrayList<AirlinePoint>();

        AirlinePoint testAirline = new AirlinePoint(1, "Test Airline 1");
        testAirline.setAirlineAlias("ANA");
        testAirline.setIata("NH");
        testAirline.setIcao("ANA");
        testAirline.setCallsign("ALL NIPPON");
        testAirline.setCountry("Japan");
        testAirline.setActive("Y");

        AirlinePoint testAirline2 = new AirlinePoint(2, "Test Airline 2");
        testAirline2.setAirlineAlias("YYT");
        testAirline2.setIata("RE");
        testAirline2.setIcao("YYT");
        testAirline2.setCallsign("WOW TEST");
        testAirline2.setCountry("A Country");
        testAirline2.setActive("Y");

        airlinePoints.add(testAirline);
        airlinePoints.add(testAirline2);
        */


    }

    public void addRouteData(){
        //adds route data into country rout list

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
        //Updates the view with a given set of airline points
        airlineTable.getItems().setAll(points);
        airlineIDCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, Integer>("airlineID"));
        airlineNameCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("airlineName"));
        airlineAliasCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("airlineAlias"));
        airlineIATACol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("iata"));
        airlineICAOCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("icao"));
        airlineCallsignCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("callsign"));
        airlineCountryCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("country"));
        airlineActCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("active"));

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


    }


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

        if (countrySelection != "None"){
            ArrayList<AirportPoint> filteredPoints = Filter.filterAirportCountry2(countrySelection);
            updateAirportsTable(filteredPoints);
        }
        else{
            updateAirportsTable(getAllAirportPoints());
        }

        //ArrayList<AirportPoint> allPoints = getAllAirportPoints(); //airportTable.getItems();
        //ArrayList<AirportPoint> filteredPoints = Filter.filterAirportCountry(allPoints, selection);
        //updateAirportsTable(filteredPoints);

    }

    public void airlinefilterButtonPressed(ActionEvent actionEvent) {
        //Gets airline values from selection and filters based on selection

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
        // gets all airline points and populates list
        ArrayList<AirlinePoint> allPoints = getAllAirlinePoints(); //airportTable.getItems()
        updateAirlinesTable(allPoints);

        airlineFilterMenu.setValue(airlineCountryList.get(0));
        airlineFilterMenu.setItems(airlineCountryList);

        airlineActiveMenu.setItems(airlineActiveList);
        airlineActiveMenu.setValue(airlineActiveList.get(0));


    }

    public void airportSeeAllButtonPressed(ActionEvent actionEvent) {
        //gets all airport points and updates view
        String selection = airportFilterMenu.getValue().toString();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("See All button pressed ! " + selection);
        alert.showAndWait();



        ArrayList<AirportPoint> allPoints = getAllAirportPoints(); //airportTable.getItems();
        // ArrayList<AirportPoint> filteredPoints = Filter.filterAirportCountry(allPoints, selection);
        updateAirportsTable(allPoints);


    }

    public void routesSeeallDataButtonPressed(ActionEvent actionEvent) {}


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
        ArrayList<String> countries = Filter.filterUniqueAirLineCountries(getAllAirlinePoints());
        ObservableList<String> countryList = FXCollections.observableArrayList(countries);
        countryList = addNullValue(countryList); //we need to add a null value
        //System.out.println(countryList);
        return countryList;
    }

    private ObservableList<String> populateAirlineActiveList(){
        //populates the activr airlines dropdown
        ObservableList<String> airlineActiveList = FXCollections.observableArrayList("None", "Active", "Inactive");

        return airlineActiveList;
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




}
