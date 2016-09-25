package seng202.group2.blackbirdControl;

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

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mch230 on 25/09/16.
 */
public class ErrorTabController {
    private ArrayList<BadData> badRoutes;
    private ArrayList<BadData> badAirports;
    public ArrayList<BadData> badAirlines;

    ErrorTabController instance;

    @FXML private TableView routeErrors;
    @FXML private TableColumn routeFileLine;
    @FXML private TableColumn routeEntry;

    @FXML private TableView airportErrors;
    @FXML private TableColumn airportFileLine;
    @FXML private TableColumn airportEntry;

    @FXML private TableView airlineErrors;
    @FXML private TableColumn airlineFileLine;
    @FXML private TableColumn airlineEntry;

    public ArrayList<BadData> getBadRoutes() {
        return badRoutes;
    }

    public ArrayList<BadData> getBadAirports() {
        return badAirports;
    }

    public ArrayList<BadData> getBadAirlines() {
        return badAirlines;
    }

    public void setBadRoutes(ArrayList<BadData> badRoutes) {
        this.badRoutes = badRoutes;
    }

    public void setBadAirports(ArrayList<BadData> badAirports) {
        this.badAirports = badAirports;
    }

    public void setBadAirlines(ArrayList<BadData> badAirlines) {
        this.badAirlines = badAirlines;
    }

    protected void updateRouteErrors() {
        routeErrors.getItems().setAll(badRoutes);
        routeFileLine.setCellValueFactory(new PropertyValueFactory<BadData, Integer>("fileLine"));
        routeEntry.setCellValueFactory(new PropertyValueFactory<BadData, Integer>("entry"));

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
                        //popUpController.setRouteTabController(instance);
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

    protected void updateAirportErrors() {
        airportErrors.getItems().setAll(badAirports);
        airportFileLine.setCellValueFactory(new PropertyValueFactory<BadData, Integer>("fileLine"));
        airportEntry.setCellValueFactory(new PropertyValueFactory<BadData, Integer>("entry"));

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
                        //popUpController.setAirportTabController(instance);
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

    protected void updateAirlineErrors() {
        airlineErrors.getItems().setAll(badAirlines);
        airlineFileLine.setCellValueFactory(new PropertyValueFactory<BadData, Integer>("fileLine"));
        airlineEntry.setCellValueFactory(new PropertyValueFactory<BadData, Integer>("entry"));

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
                        //popUpController.setAirlineTabController(instance);
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
}