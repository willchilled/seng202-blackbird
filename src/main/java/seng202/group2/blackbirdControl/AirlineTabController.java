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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by emr65 on 20/09/16.
 */
public class AirlineTabController {

    private MainController mainController;

    //Airline Table
    @FXML private TableView<DataPoint> airlineTable;

    //Airline Table columns
    @FXML private TableColumn airlineIDCol;
    @FXML private TableColumn airlineNameCol;
    @FXML private TableColumn airlineAliasCol;
    @FXML private TableColumn airlineIATACol;
    @FXML private TableColumn airlineICAOCol;
    @FXML private TableColumn airlineCallsignCol;
    @FXML private TableColumn airlineCountryCol;
    @FXML private TableColumn airlineActCol;
    @FXML private TableColumn airlineErrorCol;

    //Searching and filtering
    @FXML private ComboBox airlineFilterMenu;
    //@FXML private ComboBox airlineActiveMenu;
    @FXML private TextField airlineSearchQuery;

    //Buttons
    @FXML private Button airlineFilterButton;
    @FXML private Button airlineSeeAllButton;
    @FXML private Button addAirlineToTable;
    @FXML private ComboBox airlineActiveMenu;

    ObservableList<String> airlineCountryList = FXCollections.observableArrayList("No values Loaded");
    ObservableList<String> airlineActiveList  = FXCollections.observableArrayList("No values Loaded");


    public void show(){
        airlineTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Airline"));
    }

    public void addAirlineData(){
        //Adds airline data into filter menu, updates airline data list

        //Adds data into DataBase thus filtering it against database constraints, then pulling out remaining "good"
        // data to populate the GUI.
        File f = HelperFunctions.getFile("Add Airline Data");
        ArrayList<DataPoint> myPoints = ParserRefactor.parseFile(f, DataTypes.AIRLINEPOINT);
        DataBaseRefactor.insertDataPoints(myPoints);
        ArrayList<DataPoint> validAirlineData = FilterRefactor.getAllPoints(DataTypes.AIRLINEPOINT);
        System.out.println(validAirlineData.get(0).toString());

        //Populates DropDowns according to data
        updateAirlineFields();

        // Populates Rows in the Airline Table
        updateAirlinesTable(validAirlineData);    //update with all airline data, including bad data
    }

    private void updateAirlineFields() {

        airlineActiveList = FXCollections.observableArrayList("None", "Active", "Inactive");
        airlineActiveMenu.setItems(airlineActiveList);
        airlineActiveMenu.setValue(airlineActiveList.get(0));


        airlineCountryList = populateAirlineCountryList();  //populating from valid data in database
        airlineFilterMenu.setItems(airlineCountryList);
        airlineFilterMenu.setValue(airlineCountryList.get(0));
    }

    private ObservableList<String> populateAirlineCountryList(){
        //Populates the airline countries list
        ArrayList<String> countries = FilterRefactor.filterDistinct("country", "Airline");
        ObservableList<String> countryList = FXCollections.observableArrayList(countries);
        countryList = HelperFunctions.addNullValue(countryList); //we need to add a null value
        return countryList;
    }

    private void updateAirlinesTable(ArrayList<DataPoint> points) {


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
                    System.out.println(myText);
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




    public void airlineFilterButtonPressed(ActionEvent actionEvent) {
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


    public void airlineSeeAllButtonPressed(ActionEvent actionEvent) {
        // gets all airline points and populates list
        ArrayList<DataPoint> allPoints = FilterRefactor.getAllPoints(DataTypes.AIRLINEPOINT); //airportTable.getItems()
        updateAirlinesTable(allPoints);
        airlineFilterMenu.setValue(airlineCountryList.get(0));
        airlineFilterMenu.setItems(airlineCountryList);

        airlineActiveMenu.setItems(airlineActiveList);
        airlineActiveMenu.setValue(airlineActiveList.get(0));


    }


    public void addSingleAirline(ActionEvent actionEvent) {
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

    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    public void exportAirlineData() {
        ArrayList<DataPoint> myPoints = new ArrayList<DataPoint>(airlineTable.getItems());
        Exporter.exportData(myPoints);
    }
}
