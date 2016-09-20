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
        //mainController.show();
    }


    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    public void addRouteData(ActionEvent actionEvent) {
    }

    public void addFlightData(ActionEvent actionEvent) {
    }

    public void addAirlineData(ActionEvent actionEvent) {
    }

    public void addAirportData(ActionEvent actionEvent) {
    }

    public void exportRouteData(ActionEvent actionEvent) {
    }

    public void exportFlightData(ActionEvent actionEvent) {
    }

    public void exportAirlineData(ActionEvent actionEvent) {
    }

    public void exportAirportData(ActionEvent actionEvent) {

    }
}
