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
import seng202.group2.blackbirdModel.*;

import java.util.ArrayList;

/**
 * Created by sbe67 on 15/09/16.
 */
public class AirlineAddingPopUpController {

    private AirlineTabController airlineTabController;

    @FXML private TextField Name;
    @FXML private Text airlineID;
    @FXML private TextField Alias;
    @FXML private TextField IATA;
    @FXML private TextField ICAO;
    @FXML private TextField Callsign;
    @FXML private TextField Country;
    @FXML private Text addAirlineInvalidText;
    @FXML private CheckBox Active;
    private Stage adderStage;
    private Parent root;

    public void control() {
        adderStage.setScene(new Scene(root));
        adderStage.setTitle("Add Airline Information");
        adderStage.initModality(Modality.NONE);
        adderStage.initOwner(null);
        airlineID.setText(Integer.toString(DataBaseRefactor.getMaxInColumn("AIRLINE", "ID") + 1));

        adderStage.show();
    }

    public void createButtonPressed(){;

        String[] airlinePoint = getValues();
        if(Validator.checkAirline(airlinePoint)) {
            ArrayList<DataPoint> myAirlineData = new ArrayList<>();
            DataPoint myAirlinePoint = DataPoint.createDataPointFromStringArray(airlinePoint, DataTypes.AIRLINEPOINT);
            myAirlineData.add(myAirlinePoint);

            DataBaseRefactor.insertDataPoints(myAirlineData);
            airlineTabController.airlineFilterButtonPressed();
            adderStage.close();
        } else {
            addAirlineInvalidText.setVisible(true);
        }
    }

    public void cancelButtonPressed(){
        //just closes the stage
        adderStage.close();
    }

    private String[] getValues(){
        String airlineName = Name.getText().toString();
        String id= airlineID.getText().toString();
        String airlineAlias = Alias.getText().toString();
        String airlineIATA = IATA.getText().toString();
        String airlineICAO = ICAO.getText().toString();
        String airlineCallsign = Callsign.getText().toString();
        String airlineCountry = Country.getText().toString();
        boolean ActiveChecked = Active.isSelected();
        String airlineActive;
        if (ActiveChecked){
            airlineActive = "Y";
        }else{
            airlineActive = "N";
        }

        return new String[] {id, airlineName, airlineAlias, airlineIATA, airlineICAO, airlineCallsign, airlineCountry,
                                airlineActive};
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void setAdderStage(Stage adderStage) {
        this.adderStage = adderStage;
    }

    public void setAirlineTabController(AirlineTabController controller) {
        airlineTabController = controller;
    }

    public void enterPressed(KeyEvent ke)
    {
        if(ke.getCode() == KeyCode.ENTER)
        {
            createButtonPressed();
        }
    }
}
