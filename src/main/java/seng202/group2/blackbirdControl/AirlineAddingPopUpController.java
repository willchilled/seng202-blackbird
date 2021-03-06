package seng202.group2.blackbirdControl;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.AirlinePoint;
import seng202.group2.blackbirdModel.BBDatabase;
import seng202.group2.blackbirdModel.Parser;

import java.util.ArrayList;

/**
 * Created by sbe67 on 15/09/16.
 */


public class AirlineAddingPopUpController {

    @FXML
    private TextField Name;
    @FXML
    private TextField ID;
    @FXML
    private TextField Alias;
    @FXML
    private TextField IATA;
    @FXML
    private TextField ICAO;
    @FXML
    private TextField Callsign;
    @FXML
    private TextField Country;
    @FXML
    private CheckBox Active;

    private Stage adderStage;
    private Parent root;

    public void control() {
        adderStage.setScene(new Scene(root));
        adderStage.setTitle("Add Airline Information");
        adderStage.initModality(Modality.NONE);
        adderStage.initOwner(null);

        adderStage.show();
    }

    public void createButtonPressed() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Oops!");
        alert.setHeaderText("Error in adding data");
        alert.setContentText("Please check your input fields.");

        String[] airlinePoint = getValues().split(", ", -1);
        int count = BBDatabase.getMaxInColumn("AIRLINE", "ID");
        ArrayList<AirlinePoint> myAirlineData = new ArrayList<>();
        AirlinePoint myAirlinePoint = Parser.checkAirlineData(airlinePoint, count);
        if (myAirlinePoint == null) {
            alert.showAndWait();
            return;
        }
        myAirlineData.add(myAirlinePoint);

        boolean added = BBDatabase.addAirlinePointstoDB(myAirlineData);
        if (!added) {
            alert.showAndWait();
        } else {
            adderStage.close();
        }
    }

    public void cancleButtonPressed() {
        //just closes the stage
        adderStage.close();
    }

    private String getValues() {
        String airlineName = Name.getText().toString();
        String airlineID = ID.getText().toString();
        String airlineAlias = Alias.getText().toString();
        String airlineIATA = IATA.getText().toString();
        String airlineICAO = ICAO.getText().toString();
        String airlineCallsign = Callsign.getText().toString();
        String airlineCountry = Country.getText().toString();
        boolean ActiveChecked = Active.isSelected();
        String values = new String();
        values += airlineID + ", ";
        values += airlineName + ", ";
        values += airlineAlias + ", ";
        values += airlineIATA + ", ";
        values += airlineICAO + ", ";
        values += airlineCallsign + ", ";
        values += airlineCountry + ", ";
        if (ActiveChecked) {
            String airlineActive = "Y";
            values += airlineActive;
        } else {
            String airlineActive = "N";
            values += airlineActive;
        }
        return values;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void setAdderStage(Stage adderStage) {
        this.adderStage = adderStage;
    }
}
