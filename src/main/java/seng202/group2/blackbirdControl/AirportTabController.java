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
import java.util.Arrays;

/**
 * Created by emr65 on 20/09/16.
 */
public class AirportTabController {

    private MainController mainController;

    //Airport Table
    @FXML private TableView<DataPoint> airportTable;

    //Airport table columns
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
    @FXML private TableColumn airportErrorCol;

    //Filter and search
    @FXML private ComboBox airportFilterMenu;
    @FXML private TextField airportSearchQuery;

    //BUTTONS
    @FXML private Button airportSeeAllButton;
    @FXML private Button addAirportToTable;
    @FXML private Button filterButton;

    ObservableList<String> airportCountryList = FXCollections.observableArrayList("No values Loaded");

    public void show(){
        //mainController.show();
        airportTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Airport"));
    }

    public void addAirportData() {
        File f;
        f = HelperFunctions.getFile("Add Airport Data");
        ArrayList<DataPoint> myAirportPoints = ParserRefactor.parseFile(f, DataTypes.AIRPORTPOINT);
        DataBaseRefactor.insertDataPoints(myAirportPoints);
        ArrayList<DataPoint> validAirportPoints = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT);
        //System.out.println(validAirportPoints.get(1));
        updateAirportFields();
        updateAirportsTable(validAirportPoints);

    }

    private void updateAirportFields() {



        airportCountryList = populateAirportCountryList();  //populating from valid data in database
        //System.out.println(airportCountryList);
        airportFilterMenu.setItems(airportCountryList);
        airportFilterMenu.setValue(airportCountryList.get(0));
    }

    private ObservableList<String> populateAirportCountryList(){
        //Populates the dropdown of airline countries
        //ArrayList<AirportPoint> allPoints = getAllAirportPoints();
        ArrayList<String> countries = FilterRefactor.filterDistinct("country", "Airport");
        ObservableList<String> countryList = FXCollections.observableArrayList(countries);
        countryList = HelperFunctions.addNullValue(countryList);

        return countryList;
    }

    private void updateAirportsTable(ArrayList<DataPoint> validAirportPoints) {

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
                        stage.setTitle("Airport");
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

    public void AirportFilterButtonPressed(ActionEvent actionEvent) {

        //NEED TO ADD CASE FOR NONE SELECTED
        String countrySelection = airportFilterMenu.getValue().toString();
        String searchQuery = airportSearchQuery.getText();
        System.out.println(searchQuery);

        ArrayList<String> menusPressed = new ArrayList<>(Arrays.asList(countrySelection));
        ArrayList<DataPoint> myFilteredPoints = FilterRefactor.filterSelections(menusPressed, searchQuery, DataTypes.AIRPORTPOINT);
        updateAirportsTable(myFilteredPoints);

    }


    public void airportSeeAllButtonPressed(ActionEvent actionEvent) {
        //gets all airport points and updates view
        ArrayList<DataPoint> allPoints = FilterRefactor.getAllPoints(DataTypes.AIRPORTPOINT); //airportTable.getItems();
        updateAirportsTable(allPoints);
    }

    public void addSingleAirport(ActionEvent actionEvent) {
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


    public void setMainController(MainController controller) {
        this.mainController = controller;
    }


    public void exportAirportData() {
        ArrayList<DataPoint> myPoints = new ArrayList<DataPoint>(airportTable.getItems());
        Exporter.exportData(myPoints);
    }
}
