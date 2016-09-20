package seng202.group2.blackbirdControl;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import seng202.group2.blackbirdModel.Flight;
import seng202.group2.blackbirdModel.FlightPoint;

/**
 * Created by emr65 on 20/09/16.
 */
public class FlightTabController {

    private MainController mainController;

    //FLIGHT and FLIGHT POINT tables
    @FXML private TableView<Flight> flightTable;
    @FXML private TableView<FlightPoint> flightPointTable;

    //FLIGHT Table columns
    @FXML private TableColumn flightSourceCol;
    @FXML private TableColumn flightDestCol;

    //FLIGHT POINT Table columns
    @FXML private TableColumn flightPointTypeCol;
    @FXML private TableColumn flightPointLocaleCol;
    @FXML private TableColumn flightPointAltitudeCol;
    @FXML private TableColumn flightPointLatitudeCol;
    @FXML private TableColumn flightPointLongitudeCol;



    public void show(){
        //mainController.show();
        flightTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Flight"));
    }




    public void setMainController(MainController controller) {
        this.mainController = controller;
    }






}
