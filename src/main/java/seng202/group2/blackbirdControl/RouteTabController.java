package seng202.group2.blackbirdControl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng202.group2.blackbirdModel.RoutePoint;

/**
 * Created by emr65 on 20/09/16.
 */
public class RouteTabController {

    private MainController mainController;

    //ROUTE table
    @FXML private TableView<RoutePoint> routeTable;

    //Route Table columns
    @FXML private TableColumn routeAirlineCol;
    @FXML private TableColumn routeAirlineIDCol;
    @FXML private TableColumn routeSrcCol;
    @FXML private TableColumn routeSrcIDCol;
    @FXML private TableColumn routeDestCol;
    @FXML private TableColumn routeDestIDCol;
    @FXML private TableColumn routeCSCol;
    @FXML private TableColumn routeStopsCol;
    @FXML private TableColumn routeEqCol;
    @FXML private TableColumn routeErrorCol;

    @FXML private ComboBox routesFilterBySourceMenu;
    @FXML private ComboBox routesFilterbyDestMenu;
    @FXML private ComboBox routesFilterByStopsMenu;
    @FXML private ComboBox routesFilterbyEquipMenu;
    @FXML private TextField routesSearchMenu;

    public void show(){
        //mainController.show();
        routeTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Route"));
    }




    public void setMainController(MainController controller) {
        this.mainController = controller;
    }


    public void addSingleRoute(ActionEvent actionEvent) {
    }

    public void routesFilterButtonPressed(ActionEvent actionEvent) {
    }

    public void routesSeeAllDataButtonPressed(ActionEvent actionEvent) {
    }

    public void addRouteData(ActionEvent actionEvent) {

    }
}