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

/**
 * Controller for the airline tab. Handles displaying of data, and acts as a controller for adding and editing data.
 *
 * @author Team2
 * @version 1.0
 * @since 19/9/2016
 */
public class AirlineTabController {

    private MainController mainController;
    private AirlineTabController instance;
    private ObservableList<String> airlineCountryList = FXCollections.observableArrayList("No values Loaded");
    private ObservableList<String> airlineActiveList  = FXCollections.observableArrayList("No values Loaded");

    //Airline Table
    @FXML private TableView<DataPoint> airlineTable;
    @FXML private TableColumn airlineIDCol;
    @FXML private TableColumn airlineNameCol;
    @FXML private TableColumn airlineAliasCol;
    @FXML private TableColumn airlineIATACol;
    @FXML private TableColumn airlineICAOCol;
    @FXML private TableColumn airlineCallsignCol;
    @FXML private TableColumn airlineCountryCol;
    @FXML private TableColumn airlineActCol;

    //Searching and filtering
    @FXML private ComboBox airlineFilterMenu;
    @FXML private TextField airlineSearchQuery;
    @FXML private ComboBox airlineActiveMenu;

    /**
     * Sets the airline tab controller.
     */
    public AirlineTabController() {
        instance = this;
    }

    /**
     * Initializes the airline tab
     */
    @FXML
    private void initialize() {
        airlineFilterMenu.setValue(airlineCountryList.get(0));
        airlineFilterMenu.setItems(airlineCountryList);
        airlineActiveMenu.setItems(airlineActiveList);
        airlineActiveMenu.setValue(airlineActiveList.get(0));
    }

    /**
     * The initial view when the table is empty.
     */
    public void show() {
        airlineTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Airline"));
    }

    /**
     * Adds airline data using a file chooser. Only valid data will be added into the persistent database.
     *
     * @see ParserRefactor
     * @see DataBaseRefactor
     */
    void addAirlineData() {
        File f = HelperFunctions.getFile("Add Airline Data", false);
        if (f == null) {
            return;
        }
        ErrorTabController errorTab = mainController.getErrorTabController();
        ArrayList<DataPoint> myPoints = ParserRefactor.parseFile(f, DataTypes.AIRLINEPOINT, errorTab);
        DataBaseRefactor.insertDataPoints(myPoints, errorTab);
        ArrayList<DataPoint> validAirlineData = FilterRefactor.getAllPoints(DataTypes.AIRLINEPOINT);

        //Populates DropDowns according to data
        updateAirlineFields();
        airlineActiveMenu.setValue(airlineActiveList.get(0));
        airlineFilterMenu.setValue(airlineCountryList.get(0));

        // Populates Rows in the Airline Table
        updateAirlinesTable(validAirlineData);
        mainController.updateTab(DataTypes.AIRLINEPOINT);
    }

    /**
     * Updates the filtering dropdown menus
     */
    public void updateAirlineFields() {
        airlineActiveList = FXCollections.observableArrayList("None", "Active", "Inactive");
        airlineActiveMenu.setItems(airlineActiveList);

        airlineCountryList = populateAirlineCountryList();
        airlineFilterMenu.setItems(airlineCountryList);
    }

    /**
     * Populates the filter country dropdowns
     *
     * @return The observable list to populate the combobox
     */
    private ObservableList<String> populateAirlineCountryList() {
        ArrayList<String> countries = FilterRefactor.filterDistinct("country", "Airline");
        ObservableList<String> countryList = FXCollections.observableArrayList(countries);
        countryList = HelperFunctions.addNullValue(countryList);
        return countryList;
    }

    /**
     * Updates the airline table displaying data
     *
     * @param points The arraylist of data points
     */
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

        airlineTable.getItems().addListener((ListChangeListener<DataPoint>) c -> {
            airlineTable.getColumns().get(0).setVisible(false);
            airlineTable.getColumns().get(0).setVisible(true);
        });

        airlineTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    AirlinePoint myPoint = (AirlinePoint) airlineTable.getSelectionModel().getSelectedItem();
                    Stage stage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/airlinePopup.fxml"));
                        Parent root = loader.load();
                        AirlinePopUpController popUpController = loader.getController();
                        popUpController.setAirlineTabController(instance);
                        popUpController.setAirlinePoint(myPoint);
                        popUpController.setUpPopUp();
                        popUpController.setStage(stage);

                        stage.setScene(new Scene(root));
                        stage.setTitle("Detailed Airline Data");
                        stage.initModality(Modality.NONE);
                        stage.initOwner(null);

                        stage.show();
                    } catch (IOException e) {   //an IO exception when getting the resource
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Helps to filter airlines, obtaining the values from the filter dropdowns
     */
    public void airlineFilterButtonPressed() {
        String countrySelection;
        String activeSelection;
        try {
            countrySelection = airlineFilterMenu.getValue().toString();
            activeSelection = airlineActiveMenu.getValue().toString();
        } catch (java.lang.NullPointerException e) {
            countrySelection = "No values Loaded";
            activeSelection = "No values Loaded";
        }
        String searchQuery = airlineSearchQuery.getText();
        ArrayList<DataPoint> allPoints;

        if (activeSelection.equals("Active")) {
            activeSelection = "Y";
        } else if (activeSelection.equals("Inactive")) {
            activeSelection = "N";
        }
        if (countrySelection.equals("No values Loaded") && activeSelection.equals("No values Loaded")) {
            allPoints = FilterRefactor.getAllPoints(DataTypes.AIRLINEPOINT);
        } else {
            ArrayList<String> menusPressed = new ArrayList<>();
            menusPressed.add(countrySelection);
            menusPressed.add(activeSelection);
            allPoints = FilterRefactor.filterSelections(menusPressed, searchQuery, DataTypes.AIRLINEPOINT);
        }
        updateAirlinesTable(allPoints);
    }

    /**
     * Shows all the airline points currently stored within the database.
     */
    public void airlineSeeAllButtonPressed() {
        ArrayList<DataPoint> allPoints = FilterRefactor.getAllPoints(DataTypes.AIRLINEPOINT); //airportTable.getItems()
        updateAirlinesTable(allPoints);
        updateAirlineFields();
        airlineFilterMenu.setValue(airlineCountryList.get(0));
        airlineFilterMenu.setItems(airlineCountryList);

        airlineActiveMenu.setItems(airlineActiveList);
        airlineActiveMenu.setValue(airlineActiveList.get(0));

        airlineSearchQuery.clear();
    }

    /**
     * Brings up adding popup to insert airline values
     */
    public void addSingleAirline() {
        try {
            Stage adderStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AirlineAddingPopUp.fxml"));
            Parent root = loader.load();

            AirlineAddingPopUpController popUpController = loader.getController();
            popUpController.setAirlineTabController(instance);
            popUpController.setAdderStage(adderStage);
            popUpController.setRoot(root);
            popUpController.control();
            mainController.mainMenuHelper();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Assigns an action for the enter key
     *
     * @param ke The keyevent that occurred (the Enter key event)
     */
    public void enterPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ENTER) {
            airlineFilterButtonPressed();
        }
    }

    /**
     * Links back to the MainController of the program
     *
     * @param controller The controller for the tab window
     */
    void setMainController(MainController controller) {
        this.mainController = controller;
    }

    /**
     * Exports current airline data in the table, which may be a subset of all data.
     */
    void exportAirlineData() {
        ArrayList<DataPoint> myPoints = new ArrayList<>(airlineTable.getItems());
        Exporter.exportData(myPoints);
    }

}
