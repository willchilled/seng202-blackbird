package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
//import seng202.group2.blackbirdModel.AirlinePoint;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
//    @FXML private TextField routeCShareTextEdit;
    @FXML private TextField routeStopsTextEdit;
    @FXML private TextField routeEquipmentTextEdit;

//    @FXML private TextField routeSrcTextEdit;
//    @FXML private TextField routeSrcIDTextEdit;
//    @FXML private TextField routeDstTextEdit;
//    @FXML private TextField routeDstIDTextEdit;
//    @FXML private TextField routeAirlineTextEdit;
//    @FXML private TextField routeAirlineIDTextEdit;


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
        routeEquipText.setText(String.valueOf(routePoint.getEquipment()));

        ArrayList<String> airlineNames = FilterRefactor.filterDistinct("Name", "Airline");
        ObservableList<String> airlineMenu = FXCollections.observableArrayList(airlineNames);
        airlineMenu = HelperFunctions.addNullValue(airlineMenu);
        airlineSelection.setItems(airlineMenu);
        //airlineSelection.setValue(airlineMenu.get(0)); //TODO set initial value as the current value

        ArrayList<String> sourceAirports = FilterRefactor.filterDistinct("Name", "Airport");
        ObservableList<String> sourceNames = FXCollections.observableArrayList(sourceAirports);
        sourceNames = HelperFunctions.addNullValue(sourceNames);
        sourceSelection.setItems(sourceNames);
        //sourceSelection.setValue(sourceNames.get(0)); //TODO set initial value as the current value
        destSelection.setItems(sourceNames);
        //destSelection.setValue(sourceNames.get(0));   //TODO set initial value as the current value
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
        //refreshMessage.setVisible(true);
//

//
//
//        if(routeSrcText.getText() != ""){
//            routeSrcTextEdit.setText(routeSrcText.getText());
//        }
//        if(routeSrcIDText.getText() != ""){
//            routeSrcIDTextEdit.setText(routeSrcIDText.getText());
//        }
//        if(routeDestText.getText() != ""){
//            routeDstTextEdit.setText(routeDestText.getText());
//        }
//        if(routeDestIDText.getText() != ""){
//            routeDstIDTextEdit.setText(routeDestIDText.getText());
//        }
//        if(routeAirlineText.getText() != ""){
//            routeAirlineTextEdit.setText(routeAirlineText.getText());
//        }
//        if(routeAirlineIDText.getText() != ""){
//            routeAirlineIDTextEdit.setText(routeAirlineIDText.getText());
//        }
//        if(routeCSText.getText() != ""){
//            routeCShareTextEdit.setText(routeCSText.getText());
//        }
//        if(routeStopsText.getText() != ""){
//            routeStopsTextEdit.setText(routeStopsText.getText());
//        }
//        if(routeEquipText.getText() != ""){
//            routeEquipmentTextEdit.setText(routeEquipText.getText());
//        }


    }

    public void commitEdit(){
        String[] values = getValues();
        if (Validator.checkRoute(values)) {
            String[] valueFields = RouteTabController.getFields(values);

            //DataPoint myRoutePoint = DataPoint.createDataPointFromStringArray(valueFields, DataTypes.ROUTEPOINT);
            String sql = String.format("UPDATE ROUTE SET Airline='%1$s', Airlineid='%2$s', Src='%3$s', Srcid='%4$s'," +
                            " Dst='%5$s', Dstid='%6$s', Codeshare='%7$s', Stops='%8$s', Equipment=\"%9$s\" WHERE IDnum='%10$s'",
                    valueFields[0], valueFields[1], valueFields[2], valueFields[3], valueFields[4], valueFields[5],
                    valueFields[6], valueFields[7], valueFields[8], routeIDText.getText());
            DataBaseRefactor.performGenericQuery(sql, DataTypes.ROUTEPOINT);
//            ArrayList<DataPoint> myRouteData = new ArrayList<>();
//            myRouteData.add(myRoutePoint);
//            DataBaseRefactor.insertDataPoints(myRouteData);
            routeTabController.routesFilterButtonPressed();

            stage.close();
        } else {
            routeInvalidData.setVisible(true);
        }

//        String src = routeSrcTextEdit.getText();
//        String srcID = routeSrcIDTextEdit.getText();
//        String dst = routeDstTextEdit.getText();
//        String dstID = routeDstIDTextEdit.getText();
//        String airline = routeAirlineTextEdit.getText();
//        String airlineID = routeAirlineIDTextEdit.getText();
//        String codeShare = routeCShareTextEdit.getText();
//        String stops = routeStopsTextEdit.getText();
//        String equipment = routeEquipmentTextEdit.getText();
//
//
//        List<String> attributes = Arrays.asList(src, srcID, dst, dstID, airline, airlineID, codeShare, stops, equipment);
//
//        if(validEntries(attributes)){
//            routeIDText.setVisible(true);
//
//            routeSrcTextEdit.setVisible(false);
//            routeSrcIDTextEdit.setVisible(false);
//            routeDstTextEdit.setVisible(false);
//            routeDstIDTextEdit.setVisible(false);
//            routeAirlineTextEdit.setVisible(false);
//            routeAirlineIDTextEdit.setVisible(false);
//            routeCShareTextEdit.setVisible(false);
//            routeStopsTextEdit.setVisible(false);
//            routeEquipmentTextEdit.setVisible(false);
//            routeInvalidData.setVisible(false);
//            refreshMessage.setVisible(false);
//
//
//            routeEditButton.setVisible(true);
//            routeFinishButton.setVisible(false);
//            routeCancelButton.setVisible(false);
//

//
//
//
//            routeSrcText.setText(routeSrcTextEdit.getText());
//            routeSrcIDText.setText(routeSrcIDTextEdit.getText());
//            routeDestText.setText(routeDstTextEdit.getText());
//            routeDestIDText.setText(routeDstIDTextEdit.getText());
//            routeAirlineText.setText(routeAirlineTextEdit.getText());
//            routeAirlineIDText.setText(routeAirlineIDTextEdit.getText());
//            routeCSText.setText(routeCShareTextEdit.getText());
//            routeStopsText.setText(routeStopsTextEdit.getText());
//            routeEquipText.setText(routeEquipmentTextEdit.getText());
//
//            System.out.println("Performing query: " + sql);
//            //TODO route editing database call
//
//            routeTabController.routesFilterButtonPressed();
//            stage.close();
//
//        } else {
//            routeInvalidData.setVisible(true);
//        }
    }

    private String[] getValues() {
        String myAirline = airlineSelection.getValue().toString();
        String mySource = sourceSelection.getValue().toString();
        String myDest = destSelection.getValue().toString();
        String routeCodeshare = "";
        String routeStops = routeStopsTextEdit.getText().toString();
        String routeEquipment = routeEquipmentTextEdit.getText().toString();

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
        //refreshMessage.setVisible(false);
    }

    //TODO fix this method here to check values properly
    public boolean validEntries(List<String> attributes){

        return true;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setRouteTabController(RouteTabController controller) {
        routeTabController = controller;
    }
}