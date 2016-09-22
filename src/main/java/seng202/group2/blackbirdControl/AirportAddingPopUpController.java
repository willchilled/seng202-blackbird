package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.*;

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
    @FXML private TextField Name;
    @FXML private TextField ID;
    @FXML private TextField City;
    @FXML private TextField Country;
    @FXML private TextField IATA;
    @FXML private TextField ICAO;
    @FXML private TextField Latitude;
    @FXML private TextField Longitude;
    @FXML private TextField Altitude;
    @FXML private ComboBox tzComboBox;
    @FXML private TextField DST;
    @FXML private TextField tzOlson;
    private Stage adderStage;
    private Parent root;


    public void control() {
        adderStage.setScene(new Scene(root));
        adderStage.setTitle("Add Airport Information");
        adderStage.initModality(Modality.NONE);
        adderStage.initOwner(null);

        adderStage.show();
        timeZones = populateTimeZoneMenu();
        tzComboBox.setItems(timeZones);
        tzComboBox.setValue(timeZones.get(0));

    }

    public void createButtonPressed(){
        String shit = getValues();
        String[] airportPoint = shit.split(", ");
//        System.out.println(shit);
//        int count = BBDatabase.getMaxInColumn("AIRPORT", "ID");
//
//        AirportPoint myAirportPoint = Parser.checkAirportData(airportPoint, count);
//        ArrayList<AirportPoint> myAirportData = new ArrayList<AirportPoint>();
//        System.out.println(myAirportPoint.getAirportID());
//        System.out.println(myAirportPoint.getAirportName());
//        System.out.println(myAirportPoint.getAirportCity());
//        System.out.println(myAirportPoint.getAirportCountry());
//        System.out.println(myAirportPoint.getIata());
//        System.out.println(myAirportPoint.getIcao());
//        System.out.println(myAirportPoint.getLatitude());
//        System.out.println(myAirportPoint.getLongitude());
//        System.out.println(myAirportPoint.getAltitude());
//        System.out.println(myAirportPoint.getTimeZone());
//        System.out.println(myAirportPoint.getDst());
//        System.out.println(myAirportPoint.getTz());
//        myAirportData.add(myAirportPoint);
//        BBDatabase.addAirportPointsToDB(myAirportData);
        adderStage.close();
    }

    public void cancleButtonPressed(){
        //just closes the stage
        adderStage.close();
    }

    private String getValues(){
        //Gets Values from the inputs
        String airportName = Name.getText().toString();
        String airportID = ID.getText().toString();
        String airportCity = City.getText().toString();
        String airportCountry = Country.getText().toString();
        String airportIATA = IATA.getText().toString();
        String airportICAO = ICAO.getText().toString();
        String airportLatitude = Latitude.getText().toString();
        String airportLongitude = Longitude.getText().toString();
        String airportAltitude = Altitude.getText().toString();
        String airportTZ = tzComboBox.getValue().toString().substring(3,9);
        String airportDST = DST.getText().toString();
        String airportTZOlson = tzOlson.getText().toString();
        String[] allValues = new String[]{airportName, airportID, airportCity, airportCountry, airportIATA, airportICAO, airportLatitude,
        airportLongitude, airportAltitude, airportTZ, airportDST, airportTZOlson};

       // System.out.println(" I AM HERE");
//        for (String a: allValues){
//            System.out.println(a);
//        }
        DataPoint test = DataPoint.createDataPointFromStringArray(allValues, DataTypes.AIRPORTPOINT);
        //System.out.println(test.toString());
        ArrayList<DataPoint> myList= new ArrayList<>(Arrays.asList(test));
        DataBaseRefactor.insertDataPoints(myList);


        String values = new String();
        values += airportID + ", ";
        values += airportName + ", ";
        values += airportCity + ", ";
        values += airportCountry + ", ";
        values += airportIATA + ", ";
        values += airportICAO + ", ";
        values += airportLatitude + ", ";
        values += airportLongitude + ", ";
        values += airportAltitude + ", ";
        values += airportTZ + ", ";
        values += airportDST + ", ";
        values += airportTZOlson;
        return values;
    }

    private ObservableList<String> populateTimeZoneMenu() {
        //gets list of time zones for combo box
        ObservableList<String> Timezones = FXCollections.observableArrayList();
        try {
            String cwd = System.getProperty("user.dir");
            ArrayList<String> tZones = new ArrayList<String>();
            BufferedReader br = new BufferedReader(new FileReader(cwd + "/src/main/resources/TimeZones.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                tZones.add(line);
            }
            Timezones = FXCollections.observableArrayList(tZones);
            return Timezones;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Timezones;
    }

    public void setAdderStage(Stage adderStage) {
        this.adderStage = adderStage;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }
}