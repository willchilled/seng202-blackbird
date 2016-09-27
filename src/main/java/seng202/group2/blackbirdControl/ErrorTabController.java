package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.BadData;
import seng202.group2.blackbirdModel.DataTypes;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mch230 on 25/09/16.
 */
public class ErrorTabController {
    private ObservableList<BadData> badRoutes = FXCollections.observableArrayList();
    private ObservableList<BadData> badAirports = FXCollections.observableArrayList();
    private ObservableList<BadData> badAirlines = FXCollections.observableArrayList();

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

    private MainController mainController;
    private RouteTabController routeTabController;
    private AirportTabController airportTabController;
    private AirlineTabController airlineTabController;


    public void updateBadEntries(BadData badPoint, DataTypes type) {
        switch (type) {
            case ROUTEPOINT: badRoutes.add(badPoint); break;
            case AIRPORTPOINT: badAirports.add(badPoint); break;
            case AIRLINEPOINT: badAirlines.add(badPoint); break;
            default: return;
        }
    }

    @FXML
    public void initialize() {
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
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RouteAddingPopUp.fxml"));
                        root = loader.load();

                        //use controller to control it
                        RouteAddingPopUpController popUpController = loader.getController();
                        popUpController.setRouteTabController(routeTabController);
                        popUpController.setAdderStage(adderStage);
                        popUpController.setRoot(root);
                        popUpController.control();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AirportAddingPopUp.fxml"));
                        root = loader.load();

                        //use controller to control it
                        AirportAddingPopUpController popUpController = loader.getController();
                        popUpController.setAirportTabController(airportTabController);
                        popUpController.setAdderStage(adderStage);
                        popUpController.setRoot(root);
                        popUpController.control();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


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
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AirlineAddingPopUp.fxml"));
                        root = loader.load();

                        //use controller to control it
                        AirlineAddingPopUpController popUpController = loader.getController();
                        popUpController.setAirlineTabController(airlineTabController);
                        popUpController.setAdderStage(adderStage);
                        popUpController.setRoot(root);
                        popUpController.control();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setAirportTabController(AirportTabController airportTabController) {
        this.airportTabController = airportTabController;
    }

    public void setAirlineTabController(AirlineTabController airlineTabController) {
        this.airlineTabController = airlineTabController;
    }

    public void setRouteTabController(RouteTabController routeTabController) {
        this.routeTabController = routeTabController;
    }
}
