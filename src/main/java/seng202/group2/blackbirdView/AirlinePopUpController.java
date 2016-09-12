package seng202.group2.blackbirdView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng202.group2.blackbirdModel.AirlinePoint;

public class AirlinePopUpController {

    private AirlinePoint airlinePoint;

/**
    public AirlinePopUpController(AirlinePoint airlinePoint) {
        this.airlinePoint = airlinePoint;
        this.airlineName = airlinePoint.getAirlineName();
    }**/

    @FXML private Label nameText;
    @FXML private Label idText;
    @FXML private Label countryText;
    @FXML private Label aliasText;
    @FXML private Label iataText;
    @FXML private Label icaoText;
    @FXML private Label callsignText;
    @FXML private Label activeText;


    @FXML
    void setUpPopUp(){
        System.out.println("here!");
        nameText.setText(airlinePoint.getAirlineName());
        idText.setText(String.valueOf(airlinePoint.getAirlineID()));
        countryText.setText(airlinePoint.getCountry());
        aliasText.setText(airlinePoint.getAirlineAlias());
        iataText.setText(airlinePoint.getIata());
        icaoText.setText(airlinePoint.getIcao());
        callsignText.setText(airlinePoint.getCallsign());
        activeText.setText(airlinePoint.getActive());


      //  nameText.setVisible(true);
        //nameText.setText("Hi");
        //String ms = nameText.getText();
    }

    public void setAirlinePoint(AirlinePoint airlinePoint) {
        this.airlinePoint = airlinePoint;
    }


    public void editAirline(){
        System.out.println("nothing happens yet");
        //airlinePoint.setCountry("POOS");
    }
}

