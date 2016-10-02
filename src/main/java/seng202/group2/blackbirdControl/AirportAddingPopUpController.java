package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This airport adding pop up class allows the user to add individual airports to the program.
 *
 * @author Team2
 * @version 2.0
 * @since 19/9/2016
 */
public class AirportAddingPopUpController {

    private Stage adderStage;
    private Parent root;
    private boolean added = false;
    private AirportTabController airportTabController;

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

    /**
     * Initialises the airport adding pop
     */
    public void control() {
        adderStage.setScene(new Scene(root));
        adderStage.setTitle("Add Airport Information");
        adderStage.initModality(Modality.NONE);
        adderStage.initOwner(null);
        airportID.setText(Integer.toString(DatabaseInterface.getMaxInColumn("AIRPORT", "ID") + 1));

        adderStage.show();
        ObservableList<String> timeZones = populateTimeZoneMenu();
        tzComboBox.setItems(timeZones);
        tzComboBox.setValue(timeZones.get(0));

        ObservableList<String> dstValues = populateDSTMenu();
        dstComboBox.setItems(dstValues);
        dstComboBox.setValue(dstValues.get(0));
    }

    /**
     * This method is called when the create button is pressed, passing the
     * inputted values through to be checked by both the parser and the
     * database. If an error occurs, shows an error message.
     */
    public void createButtonPressed() {
        String[] airportPoint = getValues();
        String[] checkData = Validator.checkAirport(airportPoint);
        ArrayList<String> airport = Filter.filterDistinct("Name", "Airport");
        if(HelperFunctions.allValid(checkData) && (!airport.contains(airportPoint[1]))) {
            ArrayList<DataPoint> myAirportData = new ArrayList<>();
            DataPoint myAirportPoint = DataPoint.createDataPointFromStringArray(airportPoint, DataTypes.AIRPORTPOINT, 0, null);
            myAirportData.add(myAirportPoint);
            DatabaseInterface.insertDataPoints(myAirportData, null);
            airportTabController.airportFilterButtonPressed();
            airportTabController.updateAirportFields();
            added = true;
            adderStage.close();
        } else {
            Validator.displayAirportError(checkData);
            addAirportInvalidText.setVisible(true);
        }
    }

    /**
     * Closes the pop up on cancel
     */
    public void cancelButtonPressed() {
        adderStage.close();
    }

    /**
     * Obtains the input within the text fields of the pop up
     *
     * @return The full string of values obtained from the text fields.
     */
    private String[] getValues() {
        String airportName = Name.getText();
        String id = airportID.getText();
        String airportCity = City.getText();
        String airportCountry = Country.getText();
        String airportIATA = IATA.getText();
        String airportICAO = ICAO.getText();
        String airportLatitude = Latitude.getText();
        String airportLongitude = Longitude.getText();
        String airportAltitude = Altitude.getText();
        String airportTZ;

        if (tzComboBox.getValue().toString().equals("None")) {
            airportTZ = "0.0";
        } else {
            airportTZ = tzComboBox.getValue().toString().substring(3, 9);
        }

        String airportDST;
        if (dstComboBox.getValue().toString().equals("None")) {
            airportDST = "";
        } else {
            airportDST = dstComboBox.getValue().toString();
        }
        String airportTZOlson = tzOlson.getText();

        return new String[]{id, airportName, airportCity, airportCountry, airportIATA, airportICAO, airportLatitude,
                airportLongitude, airportAltitude, airportTZ, airportDST, airportTZOlson};
    }

    /**
     * Populates the time zone drop down box.
     *
     * @return The observable list which populates the time zone drop down.
     */
    private ObservableList<String> populateTimeZoneMenu() {
        ObservableList<String> timeZones = FXCollections.observableArrayList();
        try {
            String cwd = System.getProperty("user.dir");
            ArrayList<String> tZones = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(cwd + "/src/main/resources/TimeZones.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                tZones.add(line);
            }
            timeZones = FXCollections.observableArrayList(tZones);
        } catch (IOException e) {
            e.printStackTrace();
        }
        timeZones = HelperFunctions.addNullValue(timeZones);
        return timeZones;
    }

    /**
     * Populates the daylight savings time drop down box.
     *
     * @return The observable list which populates the daylight savings time drop down.
     */
    private ObservableList<String> populateDSTMenu() {
        ObservableList<String> timeZones = FXCollections.observableArrayList(Arrays.asList("E", "A", "S", "O", "Z", "N", "U"));
        timeZones = HelperFunctions.addNullValue(timeZones);
        return timeZones;
    }

    /**
     * Sets the stage for the pop up
     *
     * @param adderStage The stage for the pop up
     */
    void setAdderStage(Stage adderStage) {
        this.adderStage = adderStage;
    }

    /**
     * Sets the root for the pop up
     *
     * @param root The parent root
     */
    public void setRoot(Parent root) {
        this.root = root;
    }

    /**
     * Sets the related airport tab for the pop up
     *
     * @param airportTabController The airport tab invoking the pop up
     * @see AirportTabController
     */
    void setAirportTabController(AirportTabController airportTabController) {
        this.airportTabController = airportTabController;
    }

    /**
     * Assigns an action for the enter key
     *
     * @param ke The keyevent that occurred (the Enter key event)
     */
    public void enterPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ENTER) {
            createButtonPressed();
        }
    }

    /**
     * Helper function for the error tab controller, to detect whether the point was added or not
     *
     * @return Boolean for whether the point was added or not.
     */
    public boolean isAdded() {
        return added;
    }
}