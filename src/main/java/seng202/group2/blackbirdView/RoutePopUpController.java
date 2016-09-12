package seng202.group2.blackbirdView;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
//import seng202.group2.blackbirdModel.AirlinePoint;
import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.RoutePoint;

public class RoutePopUpController {

    private RoutePoint routePoint;

/**
    public AirlinePopUpController(AirlinePoint airportPoint) {
        this.airportPoint = airportPoint;
        this.airlineName = airportPoint.getAirlineName();
    }**/

    @FXML private Label routeIDText;
    @FXML private Label routeStopsText;
    @FXML private Label routeDestText;
    @FXML private Label routeCSText;
    @FXML private Label routeAirlineIDText;
    @FXML private Label routeSrcIDText;
    @FXML private Label routeEquipText;
    @FXML private Label routeDestIDText;
    @FXML private Label routeSrcText;
    @FXML private Label routeAirlineText;


    @FXML
    void setUpPopUp(){
        routeIDText.setText("Route ID: "+ String.valueOf(routePoint.getRouteID()));
        routeStopsText.setText(String.valueOf(routePoint.getStops()));
        routeDestText.setText(routePoint.getDstAirport());
        routeCSText.setText(routePoint.getCodeshare());
        routeAirlineIDText.setText(String.valueOf(routePoint.getAirlineID()));
        routeSrcIDText.setText(String.valueOf(routePoint.getSrcAirportID()));
        routeEquipText.setText(String.valueOf(routePoint.getEquipment()));
        routeDestIDText.setText(String.valueOf(routePoint.getDstAirportID()));
        routeSrcText.setText(routePoint.getSrcAirport());
        routeAirlineText.setText(routePoint.getAirline());



      //  nameText.setVisible(true);
        //nameText.setText("Hi");
        //String ms = nameText.getText();
    }

    public void setRoutePoint(RoutePoint routePoint) {

        this.routePoint = routePoint;
    }
}

