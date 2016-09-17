package seng202.group2.blackbirdView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import seng202.group2.blackbirdModel.AirlinePoint;
import seng202.group2.blackbirdModel.BBDatabase;

import java.util.Arrays;
import java.util.List;

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
    @FXML private TextField airlineNameTextEdit;
    @FXML private TextField airlineCountryTextEdit;
    @FXML private TextField airlineAliasTextEdit;
    @FXML private TextField airlineIATATextEdit;
    @FXML private TextField airlineICAOTextEdit;
    @FXML private TextField airlineCallsignTextEdit;
    @FXML private TextField airlineActiveTextEdit;
    @FXML private Text airlineInvalidDataText;
    @FXML private Button airlineFinishButton;
    @FXML private Button airlineEditButton;
    @FXML private Button airlineCancelButton;



    @FXML
    public void setUpPopUp(){
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

        airlineNameTextEdit.setVisible(true);
        airlineCountryTextEdit.setVisible(true);
        airlineAliasTextEdit.setVisible(true);
        airlineIATATextEdit.setVisible(true);
        airlineICAOTextEdit.setVisible(true);
        airlineCallsignTextEdit.setVisible(true);
        airlineActiveTextEdit.setVisible(true);
        nameText.setVisible(false);

        airlineFinishButton.setVisible(true);
        airlineCancelButton.setVisible(true);
        airlineEditButton.setVisible(false);


        if(nameText.getText() != ""){
            airlineNameTextEdit.setText((nameText.getText()));
        }
        if(countryText.getText() != ""){
            airlineCountryTextEdit.setText(countryText.getText());
        }
        if(aliasText.getText() != ""){
            airlineAliasTextEdit.setText(aliasText.getText());
        }
        if(iataText.getText() != ""){
            airlineIATATextEdit.setText(iataText.getText());
        }
        if(icaoText.getText() != ""){
            airlineICAOTextEdit.setText(icaoText.getText());
        }
        if(callsignText.getText() != ""){
            airlineCallsignTextEdit.setText(callsignText.getText());
        }
        if(activeText.getText() != ""){
            airlineActiveTextEdit.setText(activeText.getText());
        }



        //airlinePoint.setCountry("POOS");
    }

    public void commitEdit(){

        String name = airlineNameTextEdit.getText();
        String country = airlineCountryTextEdit.getText();
        String alias = airlineAliasTextEdit.getText();
        String iata = airlineIATATextEdit.getText();
        String icao = airlineICAOTextEdit.getText();
        String callsign = airlineCallsignTextEdit.getText();
        String active = airlineActiveTextEdit.getText();

        List<String> attributes = Arrays.asList(name, country, alias, iata, icao, callsign, active);

        System.out.println("calling valid Entries");

        if(validEntries(attributes)) {

            System.out.println("Lets save this data!");
            airlineNameTextEdit.setVisible(false);
            airlineCountryTextEdit.setVisible(false);
            airlineAliasTextEdit.setVisible(false);
            airlineIATATextEdit.setVisible(false);
            airlineICAOTextEdit.setVisible(false);
            airlineCallsignTextEdit.setVisible(false);
            airlineActiveTextEdit.setVisible(false);
            airlineFinishButton.setVisible(false);
            airlineCancelButton.setVisible(false);
            airlineInvalidDataText.setVisible(false);
            airlineEditButton.setVisible(true);
            nameText.setVisible(true);

            System.out.println("ICAO: " + icaoText.getText());
            System.out.println("IATA: " + iataText.getText());



            String sql = String.format("UPDATE AIRLINE SET NAME='%1$s', COUNTRY='%2$s', ALIAS='%3$s'," +
                    " IATA='%4$s', ICAO='%5$s', CALLSIGN='%6$s', ACTIVE='%7$s' WHERE ID='%8$s'",
                    name, country, alias, iata, icao, callsign, active, idText.getText());

            System.out.println(sql);

            nameText.setText(airlineNameTextEdit.getText());
            countryText.setText(airlineCountryTextEdit.getText());
            aliasText.setText(airlineAliasTextEdit.getText());
            iataText.setText(airlineIATATextEdit.getText());
            icaoText.setText(airlineICAOTextEdit.getText());
            callsignText.setText(airlineCallsignTextEdit.getText());
            activeText.setText(airlineActiveTextEdit.getText());

            BBDatabase.editDataEntry(sql);



        }else{
            airlineInvalidDataText.setVisible(true);
        }
    }

    public void cancelEdit(){

        System.out.println("Nah fuck this!");
        airlineNameTextEdit.setVisible(false);;
        airlineCountryTextEdit.setVisible(false);
        airlineAliasTextEdit.setVisible(false);
        airlineIATATextEdit.setVisible(false);
        airlineICAOTextEdit.setVisible(false);
        airlineCallsignTextEdit.setVisible(false);
        airlineActiveTextEdit.setVisible(false);
        airlineFinishButton.setVisible(false);
        airlineCancelButton.setVisible(false);
        airlineInvalidDataText.setVisible(false);
        airlineEditButton.setVisible(true);
        nameText.setVisible(true);
    }

    //TODO FIX VALID ENTRIES! Currently only returns true does not check anything!
    public static boolean validEntries(List<String> attributes){

        return true;

    }

}

