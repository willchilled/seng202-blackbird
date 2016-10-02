package seng202.group2.blackbirdControl;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.DataBaseRefactor;
import seng202.group2.blackbirdModel.DataPoint;
import seng202.group2.blackbirdModel.DataTypes;

import java.util.ArrayList;

/**
 * This Airline Adding pop up class allows enters to manually add
 * an airline point to the program.
 *
 * @author Team2
 * @version 2.0
 * @since 19/9/2016
 */
public class AirlineAddingPopUpController {

    private AirlineTabController airlineTabController;
    private Stage adderStage;
    private Parent root;
    private boolean added = false;

    @FXML private TextField Name;
    @FXML private Text airlineID;
    @FXML private TextField Alias;
    @FXML private TextField IATA;
    @FXML private TextField ICAO;
    @FXML private TextField Callsign;
    @FXML private TextField Country;
    @FXML private Text addAirlineInvalidText;
    @FXML private CheckBox Active;

    /**
     * Initialises the airline adding pop up.
     */
    public void control() {
        adderStage.setScene(new Scene(root));
        adderStage.setTitle("Add Airline Information");
        adderStage.initModality(Modality.NONE);
        adderStage.initOwner(null);
        airlineID.setText(Integer.toString(DataBaseRefactor.getMaxInColumn("AIRLINE", "ID") + 1));
        adderStage.show();
    }

    /**
     * This method is called when the create button is pressed, passing the
     * inputted values through to be checked by both the parser and the
     * database. If an error occurs, shows an alert dialogue box.
     */
    public void createButtonPressed() {
        String[] airlinePoint = getValues();
        if (Validator.checkAirline(airlinePoint)) {
            ArrayList<DataPoint> myAirlineData = new ArrayList<>();
            DataPoint myAirlinePoint = DataPoint.createDataPointFromStringArray(airlinePoint, DataTypes.AIRLINEPOINT, 0, null);
            myAirlineData.add(myAirlinePoint);
            DataBaseRefactor.insertDataPoints(myAirlineData, null);

            airlineTabController.airlineFilterButtonPressed();
            added = true;
            adderStage.close();
        } else {
            addAirlineInvalidText.setVisible(true);
        }
    }

    /**
     * Closes the pop up on cancel
     */
    public void cancelButtonPressed() {
        adderStage.close();
    }

    /**
     * Helper function to retrieve inputted values that the user has entered
     * @return
     */
    private String[] getValues() {
        String airlineName = Name.getText().toString();
        String id = airlineID.getText().toString();
        String airlineAlias = Alias.getText().toString();
        String airlineIATA = IATA.getText().toString();
        String airlineICAO = ICAO.getText().toString();
        String airlineCallsign = Callsign.getText().toString();
        String airlineCountry = Country.getText().toString();
        boolean ActiveChecked = Active.isSelected();
        String airlineActive;
        if (ActiveChecked) {
            airlineActive = "Y";
        } else {
            airlineActive = "N";
        }
        return new String[]{id, airlineName, airlineAlias, airlineIATA, airlineICAO, airlineCallsign, airlineCountry,
                airlineActive};
    }

    /**
     * Sets the root for the pop up
     * @param root The parent root
     */
    public void setRoot(Parent root) {
        this.root = root;
    }

    /**
     * Sets the stage for the pop up
     * @param adderStage The stage for the pop up
     */
    public void setAdderStage(Stage adderStage) {
        this.adderStage = adderStage;
    }

    /**
     * Sets the related airline tab for the pop up
     * @param controller The airline tab invoking the pop up
     */
    public void setAirlineTabController(AirlineTabController controller) {
        airlineTabController = controller;
    }

    /**
     * Assigns an action for the enter key
     * @param ke The keyevent that occurred (the Enter key event)
     */
    public void enterPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ENTER) {
            createButtonPressed();
        }
    }

    /**
     * Helper function for the error tab controller, to detect whether the point was added or not
     * @return Boolean for whether the point was added or not.
     */
    public boolean isAdded() {
        return added;
    }
}
