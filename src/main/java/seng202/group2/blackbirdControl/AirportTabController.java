package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng202.group2.blackbirdModel.AirportPoint;

/**
 * Created by emr65 on 20/09/16.
 */
public class AirportTabController {

    private MainController mainController;

    //Airport Table
    @FXML private TableView<AirportPoint> airportTable;

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

    //ObservableList<String> airportCountryList = FXCollections.observableArrayList("No values Loaded");

    public void show(){
        //mainController.show();
        airportTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Airport"));
    }
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }


    public void airportSeeAllButtonPressed(ActionEvent actionEvent) {
    }

    public void addSingleAirport(ActionEvent actionEvent) {
    }

    public void AirportFilterButtonPressed(ActionEvent actionEvent) {

    }
}
