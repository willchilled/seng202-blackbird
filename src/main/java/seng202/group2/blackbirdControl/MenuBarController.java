package seng202.group2.blackbirdControl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by emr65 on 20/09/16.
 */
public class MenuBarController {

    private MainController mainController;


    @FXML
    MenuItem addDataMenuButton;

    public void show(){
        addDataMenuButton.setDisable(false);
        mainController.show();
    }


    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    public void addRouteData(ActionEvent actionEvent) {
        mainController.addRouteData();
    }

    public void addFlightData(ActionEvent actionEvent) {
        mainController.addFlightData();
    }

    public void addAirlineData(ActionEvent actionEvent) {
        mainController.addAirlineData();

    }

    public void addAirportData(ActionEvent actionEvent) {
        mainController.addAirportData();
    }

    public void exportRouteData(ActionEvent actionEvent) {
        mainController.exportRouteData();
    }

    public void exportFlightData(ActionEvent actionEvent) {
        mainController.exportFlightData();
    }

    public void exportAirlineData(ActionEvent actionEvent) {
        mainController.exportAirlineData();
    }

    public void exportAirportData(ActionEvent actionEvent) {
        mainController.exportAirportData();

    }
}
