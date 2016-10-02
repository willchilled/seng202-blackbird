package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.BadData;
import seng202.group2.blackbirdModel.DataTypes;

import java.io.IOException;

/**
 * Controller for the Error tab. Displays bad entries that were not added to the database.
 *
 * @author Team2
 * @version 1.0
 * @since 25/9/2016
 */
public class ErrorTabController {

    private MainController mainController;
    private RouteTabController routeTabController;
    private AirportTabController airportTabController;
    private AirlineTabController airlineTabController;
    private ObservableList<BadData> badRoutes = FXCollections.observableArrayList();
    private ObservableList<BadData> badAirports = FXCollections.observableArrayList();
    private ObservableList<BadData> badAirlines = FXCollections.observableArrayList();
    private boolean allCorrect = true;

    @FXML private TableView<BadData> routeErrors;
    @FXML private TableColumn<BadData, Integer> routeFileLine;
    @FXML private TableColumn<BadData, String> routeEntry;
    @FXML private TableColumn<BadData, String> routeErrorMessage;

    @FXML private TableView<BadData> airportErrors;
    @FXML private TableColumn<BadData, Integer> airportFileLine;
    @FXML private TableColumn<BadData, String> airportEntry;
    @FXML private TableColumn<BadData, String> airportErrorMessage;

    @FXML private TableView<BadData> airlineErrors;
    @FXML private TableColumn<BadData, Integer> airlineFileLine;
    @FXML private TableColumn<BadData, String> airlineEntry;
    @FXML private TableColumn<BadData, String> airlineErrorMessage;

    /**
     * Links back to the MainController of the program
     *
     * @param mainController The controller for the tab
     */
    void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Links back to the airport tab, in order to receive bad data points
     *
     * @param airportTabController The airport tab controller
     */
    void setAirportTabController(AirportTabController airportTabController) {
        this.airportTabController = airportTabController;
    }

    /**
     * Links back to the airline tab, in order to receive bad data points
     *
     * @param airlineTabController The airline tab controller
     */
    void setAirlineTabController(AirlineTabController airlineTabController) {
        this.airlineTabController = airlineTabController;
    }

    /**
     * Links back to the route tab, in order to receive bad data points
     *
     * @param routeTabController The route tab controller
     */
    void setRouteTabController(RouteTabController routeTabController) {
        this.routeTabController = routeTabController;
    }

    /**
     * Initializes the error tab, with the help of three initializer methods.
     */
    @FXML
    public void initialize() {
        initializeRoutes();
        initializeAirports();
        initializeAirlines();
    }

    /**
     * Initializes the routes error table
     */
    private void initializeRoutes() {
        routeErrors.setItems(badRoutes);
        routeFileLine.setCellValueFactory(new PropertyValueFactory<>("fileLine"));
        routeEntry.setCellValueFactory(new PropertyValueFactory<>("entry"));
        routeErrorMessage.setCellValueFactory(new PropertyValueFactory<>("errorMessage"));
        routeErrors.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    try {
                        Stage adderStage = new Stage();
                        Parent root;
                        BadData myPoint = routeErrors.getSelectionModel().getSelectedItem();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RouteAddingPopUp.fxml"));
                        root = loader.load();

                        RouteAddingPopUpController popUpController = loader.getController();
                        popUpController.setRouteTabController(routeTabController);
                        popUpController.setAdderStage(adderStage);
                        popUpController.setRoot(root);
                        popUpController.control();

                        adderStage.setOnHidden(e -> {
                            if (popUpController.isAdded()) {
                                badRoutes.remove(myPoint);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Initializes the airports error table.
     */
    private void initializeAirports() {
        airportErrors.setItems(badAirports);
        airportFileLine.setCellValueFactory(new PropertyValueFactory<>("fileLine"));
        airportEntry.setCellValueFactory(new PropertyValueFactory<>("entry"));
        airportErrorMessage.setCellValueFactory(new PropertyValueFactory<>("errorMessage"));
        airportErrors.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    try {
                        Stage adderStage = new Stage();
                        Parent root;
                        BadData myPoint = airportErrors.getSelectionModel().getSelectedItem();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AirportAddingPopUp.fxml"));
                        root = loader.load();

                        AirportAddingPopUpController popUpController = loader.getController();
                        popUpController.setAirportTabController(airportTabController);
                        popUpController.setAdderStage(adderStage);
                        popUpController.setRoot(root);
                        popUpController.control();

                        adderStage.setOnHidden(e -> {
                            if (popUpController.isAdded()) {
                                badAirports.remove(myPoint);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Initializes the airlines error tab
     */
    private void initializeAirlines() {
        airlineErrors.setItems(badAirlines);
        airlineFileLine.setCellValueFactory(new PropertyValueFactory<>("fileLine"));
        airlineEntry.setCellValueFactory(new PropertyValueFactory<>("entry"));
        airlineErrorMessage.setCellValueFactory(new PropertyValueFactory<>("errorMessage"));
        airlineErrors.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    try {
                        Stage adderStage = new Stage();
                        Parent root;
                        BadData myPoint = airlineErrors.getSelectionModel().getSelectedItem();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AirlineAddingPopUp.fxml"));
                        root = loader.load();

                        AirlineAddingPopUpController popUpController = loader.getController();
                        popUpController.setAirlineTabController(airlineTabController);
                        popUpController.setAdderStage(adderStage);
                        popUpController.setRoot(root);
                        popUpController.control();

                        adderStage.setOnHidden(e -> {
                            if (popUpController.isAdded()) {
                                badAirlines.remove(myPoint);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Appends a bad data point to the Observable List of the same data type.
     *
     * @param badPoint A bad data point
     * @param type     The type of data
     */
    public void updateBadEntries(BadData badPoint, DataTypes type) {
        switch (type) {
            case ROUTEPOINT:
                badRoutes.add(badPoint);
                break;
            case AIRPORTPOINT:
                badAirports.add(badPoint);
                break;
            case AIRLINEPOINT:
                badAirlines.add(badPoint);
                break;
            default:
                break;
        }
    }

    /**
     * Clears the specified error table within the error tab when the clear button is pressed
     *
     * @param errorTypeToClear The type of error to clear
     */
    void clearEntries(DataTypes errorTypeToClear) {
        switch (errorTypeToClear) {
            case AIRLINEPOINT:
                badAirlines.clear();
                break;
            case AIRPORTPOINT:
                badAirports.clear();
                break;
            case ROUTEPOINT:
                badRoutes.clear();
                break;
            default:
                break;
        }
    }

    @FXML
    private void clearRoutes() {
        badRoutes.clear();
    }

    @FXML
    private void clearAirports() {
        badAirports.clear();
    }

    @FXML
    private void clearAirlines() {
        badAirlines.clear();
    }

    /**
     * Sets the error tab boolean flag for if all entries of a file are correct
     *
     * @param allCorrect The value to set the error tab flag to
     */
    public void setAllCorrect(boolean allCorrect) {
        this.allCorrect = allCorrect;
    }

    /**
     * Helper function to show an alert for if any bad data was found.
     */
    void showAddingErrorMessage() {
        if (!allCorrect) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Some file entries could not be added");
            alert.setContentText("See the Errors tab for more details");
            alert.showAndWait();
        }
    }
}
