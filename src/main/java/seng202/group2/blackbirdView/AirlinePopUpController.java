package seng202.group2.blackbirdView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.blackbirdControl.Filter;
import seng202.group2.blackbirdModel.AirlinePoint;
import seng202.group2.blackbirdModel.BBDatabase;
import seng202.group2.blackbirdView.GUIController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AirlinePopUpController {

    private AirlinePoint airlinePoint;
    private GUIController myGUI;

    private Stage stage;
    private Parent root;



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
    @FXML private TextField airlineIDTextEdit;
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
    void setUpPopUp(){


        stage.setScene(new Scene(root));
        stage.setTitle("View/Edit Data");
        stage.initModality(Modality.NONE);
        stage.initOwner(null);

        stage.show();


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
        airlineIDTextEdit.setVisible(false);
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
        if(idText.getText() != ""){
            airlineIDTextEdit.setText(idText.getText());
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
        String id = airlineIDTextEdit.getText();
        String country = airlineCountryTextEdit.getText();
        String alias = airlineAliasTextEdit.getText();
        String iata = airlineIATATextEdit.getText();
        String icao = airlineICAOTextEdit.getText();
        String callsign = airlineCallsignTextEdit.getText();
        String active = airlineActiveTextEdit.getText();

        List<String> attributes = Arrays.asList(name, id, country, alias, iata, icao, callsign, active);

        System.out.println("calling valid Entries");

        if(validEntries(attributes)) {

            System.out.println("Lets save this data!");
            airlineNameTextEdit.setVisible(false);
            airlineIDTextEdit.setVisible(false);
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



            String sql = String.format("UPDATE AIRLINE SET ID='%1$s', NAME='%2$s', COUNTRY='%3$s', ALIAS='%4$s'," +
                    " IATA='%5$s', ICAO='%6$s', CALLSIGN='%7$s', ACTIVE='%8$s' WHERE" +
                    " ID='%9$s'",

                    id, name, country, alias, iata, icao, callsign, active, idText.getText());

//            String betterSql = "UPDATE AIRLINE SET ID=\"%1$s\" "+
//                                "NAME=\"%2$s\", ";// +
////                                "COUNTRY=\"%3$s\" " +
////                                "ALIAS=\"%4$s\" " +
////                                "IATA=\"%$5s\" " +
////                                "ICAO=\"%$6s\" " +
////                                "CALLSIGN=\"%7$s\" " +
////                                "ACTIVE=\"%8$s\" ";
//            betterSql = String.format(betterSql, idText.getText(), nameText.getText());
//            System.out.println(betterSql);
                    System.out.println(sql);

            nameText.setText(airlineNameTextEdit.getText());
            idText.setText(airlineIDTextEdit.getText());
            countryText.setText(airlineCountryTextEdit.getText());
            aliasText.setText(airlineAliasTextEdit.getText());
            iataText.setText(airlineIATATextEdit.getText());
            icaoText.setText(airlineICAOTextEdit.getText());
            callsignText.setText(airlineCallsignTextEdit.getText());
            activeText.setText(airlineActiveTextEdit.getText());

            BBDatabase.editAirlineDataEntry(sql);
            //myGUI.airlinefilterButtonPressed();
           // ArrayList<AirlinePoint> allPoints = new ArrayList<>();
            //allPoints = Filter.getAllAirlinePointsfromDB();
           // allPoints = BBDatabase.performAirlinesQuery("SELECT * FROM AIRLINE WHERE COUNTRY =\"Stefan\" ");
          //  System.out.println(allPoints);




            //AirlinePopUpController.

        }else{
            airlineInvalidDataText.setVisible(true);
        }
        stage.close();
    }

    public void cancelEdit(){

        System.out.println("Nah fuck this!");
        airlineNameTextEdit.setVisible(false);
        airlineIDTextEdit.setVisible(false);
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

    public void setPopupControllerRoot(Parent popupControllerRoot) {
        this.root = popupControllerRoot;

    }

    public void setPopupControllerStage(Stage popupControllerStage) {
        this.stage = popupControllerStage;
    }
}

