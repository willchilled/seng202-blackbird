package seng202.group2.blackbirdView;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import seng202.group2.blackbirdModel.AirlinePoint;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.DataBaseRefactor;

import java.io.File;
import java.util.Arrays;
import java.util.List;

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
    @FXML private Text airportInvalidDataText;



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

    /**
     * Called upon pressing the edit button in the data viewing menu. Switches to editing mode.
     */
    public void editAirport(){

        airportNameText.setVisible(false);
        airportNameTextEdit.setVisible(true);
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

    /**
     * Called upon pressing the Finish button in the editing menu. Grabs al editable fields and creates a query to pass
     * to the database. Returns to the viewing menu.
     */
    public void commitEdit(){


        String name = airportNameTextEdit.getText();
        String city = airportCityTextEdit.getText();
        String country = airportCountryTextEdit.getText();
        String iata = airportIATATextEdit.getText();
        String icao = airportICAOTextEdit.getText();
        String lat = airportLatTextEdit.getText();
        String lon = airportLongTextEdit.getText();
        String alt = airportAltTextEdit.getText();
        String timeZone = airportTimeZoneTextEdit.getText();
        String dst = airportDstTextEdit.getText();
        String tz = airportTZTextEdit.getText();

        List<String> attributes = Arrays.asList(name, city, country, iata, icao, lat, lon, alt, timeZone, dst, tz);


        if(validEntries(attributes)) {


            airportNameText.setVisible(true);
            airportNameTextEdit.setVisible(false);
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


            String sql = String.format("UPDATE AIRPORT SET NAME='%1$s', CITY='%2$s', COUNTRY='%3$s', IATA='%4$s'," +
                    " ICAO='%5$s', LATITUDE='%6$s', LONGITUDE='%7$s', ALTITUDE='%8$s', TIMEZONE='%9$s', DST='%10$s'," +
                    " TZ='%11$s' WHERE ID='%12$s'",
                    name, city, country, iata, icao, lat, lon, alt, timeZone, dst, tz, airportIdText.getText());

            System.out.println(sql);

            DataBaseRefactor.editDataEntry(sql);

            airportNameText.setText(name);
            airportCityText.setText(city);
            airportCountryText.setText(country);
            airportIataText.setText(iata);
            airportIcaoText.setText(icao);
            airportLatitdueText.setText(lat);
            airportLongitudeText.setText(lon);
            airportAltitudeText.setText(alt);
            airportTimeZoneText.setText(timeZone);
            airportDstText.setText(dst);
            airportTzText.setText(tz);



        }else{
            airportInvalidDataText.setVisible(true);
        }
    }


    /**
     * Called upon pressing the cancel button, discards all editable fields and returns to the data viewing menu.
     */
    public void cancelEdit(){

        airportNameText.setVisible(true);
        airportNameTextEdit.setVisible(false);
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


    }

    /**
     * Checks all editable fields for validity before trying to perform a query for the purpose of pre warnign the user
     * @param attributes The list of editable fields and their respective values
     * @return A boolean indicating if the fields were all valid
     */
    //TODO FIX VALID ENTRIES! Currently only returns true does not check anything!
    public static boolean validEntries(List<String> attributes){

        return true;

    }
}

