package seng202.group2.blackbirdView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import seng202.group2.blackbirdModel.AirlinePoint;
import javafx.scene.control.TextField;
import seng202.group2.blackbirdModel.AirportPoint;

public class AirportPopUpController {

    private AirportPoint airportPoint;

/**
    public AirlinePopUpController(AirlinePoint airportPoint) {
        this.airportPoint = airportPoint;
        this.airlineName = airportPoint.getAirlineName();
    }**/

    @FXML private Label airportNameText;
    @FXML private Label airportIdText;
    @FXML private Label airportCityText;
    @FXML private Label airportCountryText;
    @FXML private Label airportLatitdueText;
    @FXML private Label airportLongitudeText;
    @FXML private Label airportAltitudeText;
    @FXML private Label airportIataText;
    @FXML private Label airportIcaoText;
    @FXML private Label airportTimeZoneText;
    @FXML private Label airportDstText;
    @FXML private Label airportTzText;
    @FXML private TextField airportNameTextEdit;
    @FXML private TextField airportIDTextEdit;
    @FXML private TextField airportCityTextEdit;
    @FXML private TextField airportCountryTextEdit;
    @FXML private TextField airportIATATextEdit;
    @FXML private TextField airportICAOTextEdit;
    @FXML private TextField airportLatTextEdit;
    @FXML private TextField airportLongTextEdit;
    @FXML private TextField airportAltTextEdit;
    @FXML private TextField airportTimeZoneTextEdit;
    @FXML private TextField airportDstTextEdit;
    @FXML private TextField airportTZTextEdit;
    @FXML private Button airportEditButton;
    @FXML private Button airportFinishButton;
    @FXML private Button airportCancelButton;



    @FXML
    void setUpPopUp(){
        airportNameText.setText(airportPoint.getAirportName());
        airportIdText.setText(String.valueOf(airportPoint.getAirportID()));
        airportCountryText.setText(airportPoint.getAirportCountry());
        airportCityText.setText(airportPoint.getAirportCity());
        airportLatitdueText.setText(String.valueOf(airportPoint.getLatitude()));
        airportLongitudeText.setText(String.valueOf(airportPoint.getLongitude()));
        airportAltitudeText.setText(String.valueOf(airportPoint.getAltitude()));
        airportIataText.setText(airportPoint.getIata());
        airportIcaoText.setText(airportPoint.getIcao());
        airportTimeZoneText.setText(String.valueOf(airportPoint.getTimeZone()));
        airportDstText.setText(airportPoint.getDst());
        airportTzText.setText(airportPoint.getTz());


      //  nameText.setVisible(true);
        //nameText.setText("Hi");
        //String ms = nameText.getText();
    }

    public void setAirportPoint(AirportPoint airportPoint) {

        this.airportPoint = airportPoint;
    }

    public void editAirport(){

        airportNameText.setVisible(false);
        airportNameTextEdit.setVisible(true);
        airportIDTextEdit.setVisible(false);
        airportCityTextEdit.setVisible(true);
        airportCountryTextEdit.setVisible(true);
        airportIATATextEdit.setVisible(true);
        airportICAOTextEdit.setVisible(true);
        airportLatTextEdit.setVisible(true);
        airportLongTextEdit.setVisible(true);
        airportAltTextEdit.setVisible(true);
        airportTimeZoneTextEdit.setVisible(true);
        airportDstTextEdit.setVisible(true);
        airportTZTextEdit.setVisible(true);

        airportEditButton.setVisible(false);
        airportFinishButton.setVisible(true);
        airportCancelButton.setVisible(true);

        if(airportNameText.getText() != ""){
            airportNameTextEdit.setText(airportNameText.getText());
        }
        if(airportIdText.getText() != ""){
            airportIDTextEdit.setText(airportIdText.getText());
        }
        if(airportCityText.getText() != ""){
            airportCityTextEdit.setText(airportCityText.getText());
        }
        if(airportCountryText.getText() != ""){
            airportCountryTextEdit.setText(airportCountryText.getText());
        }
        if(airportIataText.getText() != ""){
            airportIATATextEdit.setText(airportIataText.getText());
        }
        if(airportIcaoText.getText() != ""){
            airportICAOTextEdit.setText(airportIcaoText.getText());
        }
        if(airportLatitdueText.getText() != ""){
            airportLatTextEdit.setText(airportLatitdueText.getText());
        }
        if(airportLongitudeText.getText() != ""){
            airportLongTextEdit.setText(airportLongitudeText.getText());
        }
        if(airportAltitudeText.getText() != ""){
            airportAltTextEdit.setText(airportAltitudeText.getText());
        }
        if(airportTimeZoneText.getText() != ""){
            airportTimeZoneTextEdit.setText(airportTimeZoneText.getText());
        }
        if(airportDstText.getText() != ""){
            airportDstTextEdit.setText(airportDstText.getText());
        }
        if(airportTzText.getText() != ""){
            airportTZTextEdit.setText(airportTzText.getText());
        }



    }

    public void commitEdit(){

        airportNameText.setVisible(true);
        airportNameTextEdit.setVisible(false);
        airportIDTextEdit.setVisible(false);
        airportCityTextEdit.setVisible(false);
        airportCountryTextEdit.setVisible(false);
        airportIATATextEdit.setVisible(false);
        airportICAOTextEdit.setVisible(false);
        airportLatTextEdit.setVisible(false);
        airportLongTextEdit.setVisible(false);
        airportAltTextEdit.setVisible(false);
        airportTimeZoneTextEdit.setVisible(false);
        airportDstTextEdit.setVisible(false);
        airportTZTextEdit.setVisible(false);

        airportEditButton.setVisible(true);
        airportFinishButton.setVisible(false);
        airportCancelButton.setVisible(false);

        System.out.println("Lets save this dat!");


    }

    public void cancelEdit(){

        airportNameText.setVisible(true);
        airportNameTextEdit.setVisible(false);
        airportIDTextEdit.setVisible(false);
        airportCityTextEdit.setVisible(false);
        airportCountryTextEdit.setVisible(false);
        airportIATATextEdit.setVisible(false);
        airportICAOTextEdit.setVisible(false);
        airportLatTextEdit.setVisible(false);
        airportLongTextEdit.setVisible(false);
        airportAltTextEdit.setVisible(false);
        airportTimeZoneTextEdit.setVisible(false);
        airportDstTextEdit.setVisible(false);
        airportTZTextEdit.setVisible(false);

        airportEditButton.setVisible(true);
        airportFinishButton.setVisible(false);
        airportCancelButton.setVisible(false);

        System.out.println("NAH FUCK THIS!");

    }
}

