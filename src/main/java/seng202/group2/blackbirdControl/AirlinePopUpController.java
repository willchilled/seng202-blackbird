package seng202.group2.blackbirdControl;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.AirlinePoint;
import seng202.group2.blackbirdModel.DataBaseRefactor;
import seng202.group2.blackbirdModel.DataTypes;

import java.util.Optional;

public class AirlinePopUpController {

    private AirlinePoint airlinePoint;

    private AirlineTabController airlineTabController;
    private Stage stage;

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
    @FXML private Button airlineDeleteButton;

    @FXML
    void setUpPopUp(){
        nameText.setText(airlinePoint.getAirlineName());
        idText.setText(String.valueOf(airlinePoint.getAirlineID()));
        countryText.setText(airlinePoint.getCountry());
        aliasText.setText(airlinePoint.getAirlineAlias());
        iataText.setText(airlinePoint.getIata());
        icaoText.setText(airlinePoint.getIcao());
        callsignText.setText(airlinePoint.getCallsign());
        activeText.setText(airlinePoint.getActive());
    }

    public void setAirlinePoint(AirlinePoint airlinePoint) {
        this.airlinePoint = airlinePoint;
    }

    /**
     * Called upon pressing the edit button in the view data menu. Switches to editing mode.
     */
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
        airlineDeleteButton.setVisible(false);

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
    }

    /**
     * Called upon pressing the Finish button in the editing menu. Takes all editable fields and updates the selected
     * point in the database and then returns to the data viewing menu
     */
    public void commitEdit(){

        String name = airlineNameTextEdit.getText();
        String country = airlineCountryTextEdit.getText();
        String alias = airlineAliasTextEdit.getText();
        String iata = airlineIATATextEdit.getText();
        String icao = airlineICAOTextEdit.getText();
        String callsign = airlineCallsignTextEdit.getText();
        String active = airlineActiveTextEdit.getText();

        String[] attributes = new String[] {idText.getText(), name, alias, iata, icao, callsign, country, active};

        if(Validator.checkAirline(attributes)) {

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

            String sql = String.format("UPDATE AIRLINE SET NAME='%1$s', COUNTRY='%2$s', ALIAS='%3$s'," +
                            " IATA='%4$s', ICAO='%5$s', CALLSIGN='%6$s', ACTIVE='%7$s' WHERE ID='%8$s'",
                    name, country, alias, iata, icao, callsign, active, idText.getText());

            DataBaseRefactor.editDataEntry(sql);

            nameText.setText(name);
            countryText.setText(country);
            aliasText.setText(alias);
            iataText.setText(iata);
            icaoText.setText(icao);
            callsignText.setText(callsign);
            activeText.setText(active);

            airlineTabController.airlineFilterButtonPressed();
            airlineDeleteButton.setVisible(true);

        }else{
            airlineInvalidDataText.setVisible(true);
        }
    }

    /**
     * Discards all edited fields and returns to the data viewing menu
     */
    public void cancelEdit(){

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
        airlineDeleteButton.setVisible(true);

    }

    /**
     * Deletes a single airline point. Asks user for confirmation before deleting.
     */
    public void deleteSingleAirline(){
        String sql = "";
        int id = airlinePoint.getAirlineID();
        sql = String.format("DELETE FROM AIRLINE WHERE ID = %s", id);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Entry");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();

        if((result.isPresent()) && (result.get() == ButtonType.OK)) {
            DataBaseRefactor.editDataEntry(sql);
            airlineTabController.airlineFilterButtonPressed();
            stage.close();
        }
    }

    public void setAirlineTabController(AirlineTabController controller) {
        this.airlineTabController = controller;
    }

    public void enterPressed(KeyEvent ke)
    {
        if(ke.getCode() == KeyCode.ENTER)
        {
            commitEdit();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}

