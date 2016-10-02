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
import java.util.Collections;

/**
 * Controller for the airport tab. Handles displaying of data, and acts as a controller for adding and editing data.
 *
 * @author Team2
 * @version 1.0
 * @since 22/9/2016
 */
public class AirportTabController {

    private MainController mainController;
    private AirportTabController instance;
    private ObservableList<String> airportCountryList = FXCollections.observableArrayList("No values Loaded");

    //Airport table
    @FXML private TableView<DataPoint> airportTable;
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
    @FXML private TableColumn airportIncCol;
    @FXML private TableColumn airportOutCol;

    //Filter and search
    @FXML private ComboBox airportFilterMenu;
    @FXML private TextField airportSearchQuery;

    /**
     * Sets the airport tab controller.
     */
    public AirportTabController() {
        instance = this;
    }

    /**
     * Initializes the airport tab
     */
    public void initialize() {
        airportFilterMenu.setValue(airportCountryList.get(0));
        airportFilterMenu.setItems(airportCountryList);
    }

    /**
     * The initial view when the table is empty.
     */
    public void show() {
        airportTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Airport"));
    }

    /**
     * Adds airport data using a file chooser. Only valid data will be added into the persistent database.
     *
     * @see Parser
     * @see Database
     */
    void addAirportData() {
        File f = HelperFunctions.getFile("Add Airport Data", false);
        if (f == null) {
            return;
        }
        ErrorTabController errorTab = mainController.getErrorTabController();
        ArrayList<DataPoint> myAirportPoints = Parser.parseFile(f, DataTypes.AIRPORTPOINT, errorTab);
        Database.insertDataPoints(myAirportPoints, errorTab);
        ArrayList<DataPoint> validAirportPoints = Filter.getAllPoints(DataTypes.AIRPORTPOINT);

        updateAirportFields();
        airportFilterMenu.setValue(airportCountryList.get(0));
        updateAirportsTable(validAirportPoints);
        mainController.updateRoutes();
        mainController.updateTab(DataTypes.AIRPORTPOINT);
    }

    /**
     * Updates the filtering dropdown menus
     */
    void updateAirportFields() {
        airportCountryList = populateAirportCountryList();
        airportFilterMenu.setItems(airportCountryList);

    }

    /**
     * Populates the filter country dropdowns
     *
     * @return The observable list to populate the combobox
     */
    private ObservableList<String> populateAirportCountryList() {
        ArrayList<String> countries = Filter.filterDistinct("country", "Airport");
        ObservableList<String> countryList = FXCollections.observableArrayList(countries);
        countryList = HelperFunctions.addNullValue(countryList);
        return countryList;
    }

    /**
     * Updates the airport table displaying data
     *
     * @param validAirportPoints The arraylist of data points
     */
    void updateAirportsTable(ArrayList<DataPoint> validAirportPoints) {
        airportTable.getItems().setAll(validAirportPoints);

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
        airportIncCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, Integer>("incomingRoutes"));
        airportOutCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, Integer>("outgoingRoutes"));

        airportTable.getItems().addListener((ListChangeListener<DataPoint>) c -> {
            airportTable.getColumns().get(0).setVisible(false);
            airportTable.getColumns().get(0).setVisible(true);
        });

        airportTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    AirportPoint myPoint = (AirportPoint) airportTable.getSelectionModel().getSelectedItem();
                    Stage stage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/airportPopup.fxml"));
                        Parent root = loader.load();
                        AirportPopUpController popUpController = loader.getController();
                        popUpController.setAirportTabController(instance);
                        popUpController.setAirportPoint(myPoint);
                        popUpController.setUpPopUp();
                        popUpController.setStage(stage);

                        stage.setScene(new Scene(root));
                        stage.setTitle("Detailed Airport Information");
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
     * Helps to filter airports, obtaining the values from the filter dropdownsx
     */
    public void airportFilterButtonPressed() {
        String countrySelection;
        try {
            countrySelection = airportFilterMenu.getValue().toString();
        } catch (java.lang.NullPointerException e) {
                countrySelection = "No values Loaded";
        }
        String searchQuery = airportSearchQuery.getText();
        ArrayList<String> menusPressed = new ArrayList<>(Collections.singletonList(countrySelection));

        ArrayList<DataPoint> filteredPoints;
        if (countrySelection.equals("No values Loaded")) {
            filteredPoints = Filter.getAllPoints(DataTypes.AIRPORTPOINT);
        } else {
            filteredPoints = Filter.filterSelections(menusPressed, searchQuery, DataTypes.AIRPORTPOINT);
        }
        updateAirportFields();
        updateAirportsTable(filteredPoints);
    }

    /**
     * Shows all the airport points currently stored within the database.
     */
    public void airportSeeAllButtonPressed() {
        ArrayList<DataPoint> allPoints = Filter.getAllPoints(DataTypes.AIRPORTPOINT);
        updateAirportsTable(allPoints);
        updateAirportFields();
        airportFilterMenu.setValue(airportCountryList.get(0));
        airportSearchQuery.clear();
    }

    /**
     * Brings up adding popup to insert airport values
     */
    public void addSingleAirport() {
        try {
            Stage adderStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AirportAddingPopUp.fxml"));
            Parent root = loader.load();

            AirportAddingPopUpController popUpController = loader.getController();
            popUpController.setAirportTabController(instance);
            popUpController.setAdderStage(adderStage);
            popUpController.setRoot(root);
            popUpController.control();
            mainController.mainMenuHelper();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * Assigns an action for the enter key
     *
     * @param ke The keyevent that occurred (the Enter key event)
     */
    public void enterPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ENTER) {
            airportFilterButtonPressed();
        }
    }

    /**
     * Exports current airport data in the table, which may be a subset of all data.
     */
    void exportAirportData() {
        ArrayList<DataPoint> myPoints = new ArrayList<>(airportTable.getItems());
        Exporter.exportData(myPoints);
    }
}
