package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.*;
import sun.util.resources.cldr.en.TimeZoneNames_en_AU;
import sun.util.resources.cldr.en.TimeZoneNames_en_NZ;
import sun.util.resources.cldr.en.TimeZoneNames_en_SG;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sbe67 on 15/09/16.
 */
public class AirportAddingPopUpController {

    ObservableList<String> timeZones = FXCollections.observableArrayList("No values Loaded");
    ObservableList<String> dstValues = FXCollections.observableArrayList("No values Loaded");
    @FXML private TextField Name;
    @FXML private Text airportID;
    @FXML private TextField City;
    @FXML private TextField Country;
    @FXML private TextField IATA;
    @FXML private TextField ICAO;
    @FXML private TextField Latitude;
    @FXML private TextField Longitude;
    @FXML private TextField Altitude;
    @FXML private ComboBox tzComboBox;
    @FXML private ComboBox dstComboBox;
    @FXML private TextField tzOlson;
    @FXML private Text addAirportInvalidText;

    private Stage adderStage;
    private Parent root;
    private boolean added = false;

    private AirportTabController airportTabController;


    public void control() {
        adderStage.setScene(new Scene(root));
        adderStage.setTitle("Add Airport Information");
        adderStage.initModality(Modality.NONE);
        adderStage.initOwner(null);
        airportID.setText(Integer.toString(DataBaseRefactor.getMaxInColumn("AIRPORT", "ID") + 1));

        adderStage.show();
        timeZones = populateTimeZoneMenu();
        tzComboBox.setItems(timeZones);
        tzComboBox.setValue(timeZones.get(0));

        dstValues = populateDSTMenu();
        dstComboBox.setItems(dstValues);
        dstComboBox.setValue(dstValues.get(0));

    }

    public void createButtonPressed(){

        String[] airportPoint = getValues();
        String[] checkData = Validator.checkAirport(airportPoint);
        if(HelperFunctions.allValid(checkData)) {
            ArrayList<DataPoint> myAirportData = new ArrayList<>();
            DataPoint myAirportPoint = DataPoint.createDataPointFromStringArray(airportPoint, DataTypes.AIRPORTPOINT, 0, null);
            myAirportData.add(myAirportPoint);

            DataBaseRefactor.insertDataPoints(myAirportData, null);
            airportTabController.airportFilterButtonPressed();
            added = true;
            adderStage.close();
        } else {
            Validator.displayAirportError(checkData);
            addAirportInvalidText.setVisible(true);
        }
    }

    public void cancelButtonPressed(){
        //just closes the stage
        adderStage.close();
    }

    private String[] getValues(){
        //Gets Values from the inputs
        String airportName = Name.getText().toString();
        String id = airportID.getText().toString();
        String airportCity = City.getText().toString();
        String airportCountry = Country.getText().toString();
        String airportIATA = IATA.getText().toString();
        String airportICAO = ICAO.getText().toString();
        String airportLatitude = Latitude.getText().toString();
        String airportLongitude = Longitude.getText().toString();
        String airportAltitude = Altitude.getText().toString();
        String airportTZ;
        if(tzComboBox.getValue().toString().equals("None")){
            airportTZ = "0.0";
        } else {
            airportTZ = tzComboBox.getValue().toString().substring(3, 9);
        }
        String airportDST;
        if(dstComboBox.getValue().toString().equals("None")){
            airportDST = "";
        } else {
            airportDST = dstComboBox.getValue().toString();
        }
        String airportTZOlson = tzOlson.getText().toString();

        return new String[] {id, airportName, airportCity, airportCountry, airportIATA, airportICAO, airportLatitude,
                                airportLongitude, airportAltitude, airportTZ, airportDST, airportTZOlson};
    }

    private ObservableList<String> populateTimeZoneMenu() {
        //gets list of time zones for combo box
        ObservableList<String> timeZones = FXCollections.observableArrayList();
        try {
            String cwd = System.getProperty("user.dir");
            ArrayList<String> tZones = new ArrayList<String>();
            BufferedReader br = new BufferedReader(new FileReader(cwd + "/src/main/resources/TimeZones.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                tZones.add(line);
            }
            timeZones = FXCollections.observableArrayList(tZones);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        timeZones = HelperFunctions.addNullValue(timeZones);
        return timeZones;
    }

    private ObservableList<String> populateDSTMenu() {
        //gets list of time zones for combo box
        ObservableList<String> timeZones = FXCollections.observableArrayList(Arrays.asList("E", "A", "S", "O", "Z", "N", "U"));
        timeZones = HelperFunctions.addNullValue(timeZones);
        return timeZones;
    }

    public void setAdderStage(Stage adderStage) {
        this.adderStage = adderStage;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }


    public void setAirportTabController(AirportTabController airportTabController) {
        this.airportTabController = airportTabController;
    }

    public void enterPressed(KeyEvent ke)
    {
        if(ke.getCode() == KeyCode.ENTER)
        {
            createButtonPressed();
        }
    }

    public boolean isAdded() {
        return added;
    }


}