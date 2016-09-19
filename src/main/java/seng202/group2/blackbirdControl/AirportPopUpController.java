package seng202.group2.blackbirdControl;

import javafx.fxml.FXML;
import javafx.scene.control.*;
//import seng202.group2.blackbirdModel.AirlinePoint;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.BBDatabase;

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

    private Stage stage;
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
    @FXML private Text airportInvalidData;
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
    @FXML private Pane refreshMessage;



    @FXML
    public void setUpPopUp(){
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


    }

    public void setAirportPoint(AirportPoint airportPoint) {

        this.airportPoint = airportPoint;
    }

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
        refreshMessage.setVisible(true);

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

        String[] validness =  validEntries(attributes);
        if(validness[0] == "T") {

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
            airportInvalidData.setVisible(false);

            airportEditButton.setVisible(true);
            airportFinishButton.setVisible(false);
            airportCancelButton.setVisible(false);
            refreshMessage.setVisible(false);

            String sql = String.format("UPDATE AIRPORT SET NAME='%1$s', CITY='%2$s', COUNTRY='%3$s'," +
                            " IATA='%4$s', ICAO='%5$s', LATITUDE='%6$s', LONGITUDE='%7$s', ALTITUDE='%8$s'," +
                    " TIMEZONE='%9$s', DST='%10$s', TZ='%11$s' WHERE ID='%12$s'",
                    name, city, country, iata, icao, lat, lon, alt, timeZone, dst, tz, airportIdText.getText());


            airportNameText.setText(airportNameTextEdit.getText());
            airportCityText.setText(airportCityTextEdit.getText());
            airportCountryText.setText(airportCountryTextEdit.getText());
            airportIataText.setText(airportIATATextEdit.getText());
            airportIcaoText.setText(airportICAOTextEdit.getText());
            airportLatitdueText.setText(airportLatTextEdit.getText());
            airportLongitudeText.setText(airportLongTextEdit.getText());
            airportAltitudeText.setText(airportAltTextEdit.getText());
            airportTimeZoneText.setText(airportTimeZoneTextEdit.getText());
            airportDstText.setText(airportDstTextEdit.getText());
            airportTzText.setText(airportTZTextEdit.getText());

            System.out.println(sql);

            BBDatabase.editDataEntry(sql);

            stage.close();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Oops!");
            alert.setHeaderText("The " + validness[1] + " entry was invalid");
            alert.setContentText("Please check your input fields.");
            alert.showAndWait();
        }
    }


    public void cancelEdit(){

        airportNameText.setVisible(true);
        airportNameTextEdit.setVisible(false);;
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
        airportInvalidData.setVisible(false);


        airportEditButton.setVisible(true);
        airportFinishButton.setVisible(false);
        airportCancelButton.setVisible(false);
        refreshMessage.setVisible(false);

    }

    /**
     * A function to check if the edited data is valid airport data
     * @param attributes the edited data give
     * @return a list of string ["T",null] if all entries are valid ["F", name of invalid entry] if an entry is not valid
     */
    public static String[] validEntries(List<String> attributes){
        String[] validness = {"T", null};
        String name = attributes.get(0); //Does not need to be checked
        String city = attributes.get(1); //Does not need to be checked
        String country = attributes.get(2); //Does not need to be checked
        String iata = attributes.get(3); //Must be string of length 3 or less
        if(iata == null){
            iata = "";
        }
        String icao = attributes.get(4); //Must be a string of length 4 or less
        if(icao == null){
            icao = "";
        }
        String lat = attributes.get(5); //Must be a float
        if(lat == null){
            lat = "0";
        }
        String lon = attributes.get(6); //Must be a float
        if(lon == null){
            lon = "0";
        }
        String alt = attributes.get(7); //Must be a float
        if(alt == null){
            alt = "0";
        }
        String timeZone = attributes.get(8); //Must be a float
        if(timeZone == null){
            timeZone = "0";
        }
        String dst = attributes.get(9); //a string in 'E', 'A', 'S', 'O', 'Z', 'N', 'U', 'null'
        String tz = attributes.get(10);//Does not need to be checked
        String[] validDST = {"E", "A", "S", "O", "Z", "N", "U", null};
        try {
            if (iata.length() > 3) {
                validness[0] = "F";
                validness[1] = "IATA";
            } else if (icao.length() > 4) {
                validness[0] = "F";
                validness[1] = "ICAO";
            } else if (!(Arrays.asList(validDST).contains(dst))) {
                validness[0] = "F";
                validness[1] = "Daylight Savings";
            } else try {
                validness[0] = "F";
                validness[1] = "Latitude";
                Float.parseFloat(lat);
                validness[0] = "F";
                validness[1] = "Longitude";
                Float.parseFloat(lon);
                validness[0] = "F";
                validness[1] = "Altitude";
                Float.parseFloat(alt);
                validness[0] = "F";
                validness[1] = "Timezone";
                Float.parseFloat(timeZone);
                validness[0] = "T";
                validness[1] = null;
            } catch (NumberFormatException e) {
            }
            return validness;
        }catch (NullPointerException e){
            validness[0] = "F";
            validness[1] = "Null";
            return validness;
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}


