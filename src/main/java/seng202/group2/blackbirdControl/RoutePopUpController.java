package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
//import seng202.group2.blackbirdModel.AirlinePoint;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.*;

import java.util.ArrayList;
import java.util.Optional;

public class RoutePopUpController {

    private RoutePoint routePoint;
    private RouteTabController routeTabController;

    private Stage stage;
    @FXML private GridPane infoText;
    @FXML private Label routeIDText;
    @FXML private Label routeAirlineText;
    @FXML private Label routeAirlineIDText;
    @FXML private Label routeSrcIDText;
    @FXML private Label routeSrcText;
    @FXML private Label routeSourceNameText;
    @FXML private Label routeSourceCountryText;
    @FXML private Label routeDestIDText;
    @FXML private Label routeDestText;
    @FXML private Label routeDestNameText;
    @FXML private Label routeDestCountryText;
    @FXML private Label routeCSText;
    @FXML private Label routeStopsText;
    @FXML private Label routeEquipText;
    @FXML private Text routeInvalidData;

    @FXML private GridPane editText;
    @FXML private ComboBox airlineSelection;
    @FXML private ComboBox sourceSelection;
    @FXML private ComboBox destSelection;
    @FXML private CheckBox codeshareSelection;
    @FXML private TextField routeStopsTextEdit;
    @FXML private TextField routeEquipmentTextEdit;

    @FXML private Button routeDeleteButton;
    @FXML private Button routeEditButton;
    @FXML private Button routeFinishButton;
    @FXML private Button routeCancelButton;
    @FXML private Pane refreshMessage;

    @FXML
    public void setUpPopUp(){
        routeIDText.setText(String.valueOf(routePoint.getRouteID()));
        routeAirlineText.setText(routePoint.getAirline());
        routeAirlineIDText.setText(String.valueOf(routePoint.getAirlineID()));
        routeSrcIDText.setText(String.valueOf(routePoint.getSrcAirportID()));
        routeSrcText.setText(routePoint.getSrcAirport());
        routeSourceNameText.setText(routePoint.getSrcAirportName());
        routeSourceCountryText.setText(routePoint.getSrcAirportCountry());
        routeDestIDText.setText(String.valueOf(routePoint.getDstAirportID()));
        routeDestText.setText(routePoint.getDstAirport());
        routeDestNameText.setText(routePoint.getDstAirportName());
        routeDestCountryText.setText(routePoint.getDstAirportCountry());
        routeCSText.setText(routePoint.getCodeshare());
        routeStopsText.setText(String.valueOf(routePoint.getStops()));
        routeEquipText.setText(routePoint.getEquipment());

        if(!(routeCSText.getText() == null)) {
            if (!routeCSText.getText().isEmpty()) {
                codeshareSelection.setSelected(true);
            }
        }

        if(!(routeStopsText.getText() == null)) {
            routeStopsTextEdit.setText(routeStopsText.getText());
        }

        if(!(routeEquipText.getText() == null)) {
            routeEquipmentTextEdit.setText(routeEquipText.getText());
        }


        ArrayList<String> airlineNames = Filter.filterDistinct("Name", "Airline");
        ObservableList<String> airlineMenu = FXCollections.observableArrayList(airlineNames);
        airlineMenu = HelperFunctions.addNullValue(airlineMenu);

        ArrayList<String> uniqueAirportList = Filter.filterDistinct("Name", "Airport");
        ObservableList<String> uniqueAirports = FXCollections.observableArrayList(uniqueAirportList);
        uniqueAirports = HelperFunctions.addNullValue(uniqueAirports);
        Integer[] myIndices = getMenuIndex(airlineMenu, uniqueAirports);

        airlineSelection.setItems(airlineMenu);
        airlineSelection.setValue(airlineMenu.get(myIndices[0]));

        sourceSelection.setItems(uniqueAirports);
        sourceSelection.setValue(uniqueAirports.get(myIndices[1]));

        destSelection.setItems(uniqueAirports);
        destSelection.setValue(uniqueAirports.get(myIndices[2]));
    }

    private Integer[] getMenuIndex(ObservableList<String> airlineMenu, ObservableList<String> uniqueAirports) {
        Integer[] returnIndices = new Integer[3];
        String sql = "SELECT * FROM AIRLINE WHERE ID=" + routePoint.getAirlineID();

        int myIndex = 0;
        ArrayList<DataPoint> myAirline = Database.performGenericQuery(sql, DataTypes.AIRLINEPOINT);
        if (myAirline.size() > 1) {
            System.err.println("More than one airline found");
        } else if (myAirline.size() == 0) {
            myIndex = 0;
        } else {
            AirlinePoint grabName = (AirlinePoint) myAirline.get(0);
            myIndex = airlineMenu.indexOf(grabName.getAirlineName());
        }
        returnIndices[0] = myIndex;

        String sql1 = "SELECT * FROM AIRPORT WHERE ID=" + routePoint.getSrcAirportID();
        int sourceIndex = 0;
        ArrayList<DataPoint> sourceAirport = Database.performGenericQuery(sql1, DataTypes.AIRPORTPOINT);
        if (sourceAirport.size() > 1) {
            System.err.println("More than one airport found");
        } else if (sourceAirport.size() == 0) {
            sourceIndex = 0;
        } else {
            AirportPoint grabName = (AirportPoint) sourceAirport.get(0);
            sourceIndex = uniqueAirports.indexOf(grabName.getAirportName());
        }
        returnIndices[1] = sourceIndex;

        String sql2 = "SELECT * FROM AIRPORT WHERE ID=" + routePoint.getDstAirportID();
        int destIndex = 0;
        ArrayList<DataPoint> destAirport = Database.performGenericQuery(sql2, DataTypes.AIRPORTPOINT);
        if (destAirport.size() > 1) {
            System.err.println("More than one airport found");
        } else if (destAirport.size() == 0) {
            destIndex = 0;
        } else {
            AirportPoint grabName = (AirportPoint) destAirport.get(0);
            destIndex = uniqueAirports.indexOf(grabName.getAirportName());
        }
        returnIndices[2] = destIndex;

        return returnIndices;
    }

    public void setRoutePoint(RoutePoint routePoint) {

        this.routePoint = routePoint;
    }

    public void editRoute(){

        infoText.setVisible(false);
        editText.setVisible(true);
        routeEditButton.setVisible(false);
        routeFinishButton.setVisible(true);
        routeCancelButton.setVisible(true);
        routeDeleteButton.setVisible(false);
    }

    public void commitEdit(){
        String[] values = getValues();
        String[] checkData = Validator.checkRoute(values);
        if (HelperFunctions.allValid(checkData)) {
            String[] valueFields = RouteTabController.getFields(values);

            //DataPoint myRoutePoint = DataPoint.createDataPointFromStringArray(valueFields, DataTypes.ROUTEPOINT);
            String sql = String.format("UPDATE ROUTE SET Airline=\"%1$s\", Airlineid=\"%2$s\", Src=\"%3$s\", Srcid=\"%4$s\"," +
                            " Dst=\"%5$s\", Dstid=\"%6$s\", Codeshare=\"%7$s\", Stops=\"%8$s\", Equipment=\"%9$s\" WHERE IDnum=\"%10$s\"",
                    valueFields[0], valueFields[1], valueFields[2], valueFields[3], valueFields[4], valueFields[5],
                    valueFields[6], valueFields[7], valueFields[8], routeIDText.getText());
            Database.editDataEntry(sql);
            routeTabController.routesFilterButtonPressed();

            String sql1 = "SELECT * FROM ROUTE WHERE IDnum='" + routeIDText.getText() + "'";
            ArrayList<DataPoint> myRoute = Database.performGenericQuery(sql1, DataTypes.ROUTEPOINT);
            RoutePoint myEditedRoute = (RoutePoint) myRoute.get(0);

            setRoutePoint(myEditedRoute);

            infoText.setVisible(true);
            editText.setVisible(false);

            routeEditButton.setVisible(true);
            routeFinishButton.setVisible(false);
            routeCancelButton.setVisible(false);

            //TODO instead of making the window close after editing, get it to display the updated and linked values
            stage.close();
        } else {
            Validator.displayRouteError(checkData);
            routeInvalidData.setVisible(true);
        }
    }

    private String[] getValues() {
        String myAirline = airlineSelection.getValue().toString();
        String mySource = sourceSelection.getValue().toString();
        String myDest = destSelection.getValue().toString();
        String routeCodeshare = "";
        String routeStops = routeStopsTextEdit.getText();
        String routeEquipment = routeEquipmentTextEdit.getText();

        boolean codeshareChecked = codeshareSelection.isSelected();
        if (codeshareChecked){
            routeCodeshare = "Y";
        }

        if(routeStops.isEmpty()) {
            routeStops = "0";
        }
        String[] values = {myAirline, mySource, myDest, routeCodeshare, routeStops, routeEquipment};
        return values;
    }


    public void cancelEdit(){
        infoText.setVisible(true);
        editText.setVisible(false);

        routeEditButton.setVisible(true);
        routeFinishButton.setVisible(false);
        routeCancelButton.setVisible(false);
        routeDeleteButton.setVisible(true);
    }

    public void enterPressed(KeyEvent ke)
    {
        if(ke.getCode() == KeyCode.ENTER)
        {
            commitEdit();
        }
    }

    /**
     * Deletes a single route point. Asks user for confirmation before deleting.
     */
    public void deleteSingleRoute(){
        String sql = "";
        int id = routePoint.getRouteID();
        sql = String.format("DELETE FROM ROUTE WHERE IDnum = %s", id);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Entry");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();

        if((result.isPresent()) && (result.get() == ButtonType.OK)) {
            Database.editDataEntry(sql);
            routeTabController.routesFilterButtonPressed();
            stage.close();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setRouteTabController(RouteTabController controller) {
        routeTabController = controller;
    }





}