package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
//import seng202.group2.blackbirdModel.AirlinePoint;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.RoutePoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoutePopUpController {

    private RoutePoint routePoint;
    private RouteTabController routeTabController;

    private Stage stage;
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

    @FXML private ComboBox airlineSelection;
    @FXML private ComboBox sourceSelection;
    @FXML private ComboBox destSelection;

    @FXML private TextField routeSrcTextEdit;
    @FXML private TextField routeSrcIDTextEdit;
    @FXML private TextField routeDstTextEdit;
    @FXML private TextField routeDstIDTextEdit;
    @FXML private TextField routeAirlineTextEdit;
    @FXML private TextField routeAirlineIDTextEdit;

    @FXML private TextField routeCShareTextEdit;
    @FXML private TextField routeStopsTextEdit;
    @FXML private TextField routeEquipmentTextEdit;
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

        routeInvalidData.setVisible(false);
        routeAirlineIDText.setVisible(false);
        routeSrcIDText.setVisible(false);
        routeSrcText.setVisible(false);
        routeSourceCountryText.setVisible(false);
        routeDestIDText.setVisible(false);
        routeDestText.setVisible(false);
        routeDestCountryText.setVisible(false);
        airlineSelection.setVisible(true);
        sourceSelection.setVisible(true);
        destSelection.setVisible(true);
//
//        routeSrcTextEdit.setVisible(true);
//        routeSrcIDTextEdit.setVisible(true);
//        routeDstTextEdit.setVisible(true);
//        routeDstIDTextEdit.setVisible(true);
//        routeAirlineTextEdit.setVisible(true);
//        routeAirlineIDTextEdit.setVisible(true);
        routeCShareTextEdit.setVisible(true);
        routeStopsTextEdit.setVisible(true);
        routeEquipmentTextEdit.setVisible(true);
        refreshMessage.setVisible(true);

        routeEditButton.setVisible(false);
        routeFinishButton.setVisible(true);
        routeCancelButton.setVisible(true);
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

        String src = routeSrcTextEdit.getText();
        String srcID = routeSrcIDTextEdit.getText();
        String dst = routeDstTextEdit.getText();
        String dstID = routeDstIDTextEdit.getText();
        String airline = routeAirlineTextEdit.getText();
        String airlineID = routeAirlineIDTextEdit.getText();
        String codeShare = routeCShareTextEdit.getText();
        String stops = routeStopsTextEdit.getText();
        String equipment = routeEquipmentTextEdit.getText();


        List<String> attributes = Arrays.asList(src, srcID, dst, dstID, airline, airlineID, codeShare, stops, equipment);

        if(validEntries(attributes)){
            routeIDText.setVisible(true);

            routeSrcTextEdit.setVisible(false);
            routeSrcIDTextEdit.setVisible(false);
            routeDstTextEdit.setVisible(false);
            routeDstIDTextEdit.setVisible(false);
            routeAirlineTextEdit.setVisible(false);
            routeAirlineIDTextEdit.setVisible(false);
            routeCShareTextEdit.setVisible(false);
            routeStopsTextEdit.setVisible(false);
            routeEquipmentTextEdit.setVisible(false);
            routeInvalidData.setVisible(false);
            refreshMessage.setVisible(false);


            routeEditButton.setVisible(true);
            routeFinishButton.setVisible(false);
            routeCancelButton.setVisible(false);

            String sql = String.format("UPDATE ROUTE SET Airline='%1$s', Airlineid='%2$s', Src='%3$s', Srcid='%4$s'," +
                            " Dst='%5$s', Dstid='%6$s', Codeshare='%7$s', Stops='%8$s', Equipment=\"%9$s\" WHERE IDnum='%10$s'",
                    airline, airlineID, src, srcID, dst, dstID, codeShare, stops, equipment, routeIDText.getText());



            routeSrcText.setText(routeSrcTextEdit.getText());
            routeSrcIDText.setText(routeSrcIDTextEdit.getText());
            routeDestText.setText(routeDstTextEdit.getText());
            routeDestIDText.setText(routeDstIDTextEdit.getText());
            routeAirlineText.setText(routeAirlineTextEdit.getText());
            routeAirlineIDText.setText(routeAirlineIDTextEdit.getText());
            routeCSText.setText(routeCShareTextEdit.getText());
            routeStopsText.setText(routeStopsTextEdit.getText());
            routeEquipText.setText(routeEquipmentTextEdit.getText());

            System.out.println("Performing query: " + sql);
            //TODO route editing database call

            routeTabController.routesFilterButtonPressed();
            stage.close();

        } else {
            routeInvalidData.setVisible(true);
        }


    }

    public void cancelEdit(){

        routeInvalidData.setVisible(false);
        routeAirlineIDText.setVisible(true);
        routeSrcIDText.setVisible(true);
        routeSrcText.setVisible(true);
        routeSourceCountryText.setVisible(true);
        routeDestIDText.setVisible(true);
        routeDestText.setVisible(true);
        routeDestCountryText.setVisible(true);

        airlineSelection.setVisible(false);
        sourceSelection.setVisible(false);
        destSelection.setVisible(false);

        routeCShareTextEdit.setVisible(false);
        routeStopsTextEdit.setVisible(false);
        routeEquipmentTextEdit.setVisible(false);
        refreshMessage.setVisible(false);

        routeEditButton.setVisible(true);
        routeFinishButton.setVisible(false);
        routeCancelButton.setVisible(false);

//        routeIDText.setVisible(true);
//
//        routeSrcTextEdit.setVisible(false);
//        routeSrcIDTextEdit.setVisible(false);
//        routeDstTextEdit.setVisible(false);
//        routeDstIDTextEdit.setVisible(false);
//        routeAirlineTextEdit.setVisible(false);
//        routeAirlineIDTextEdit.setVisible(false);
//        routeCShareTextEdit.setVisible(false);
//        routeStopsTextEdit.setVisible(false);
//        routeEquipmentTextEdit.setVisible(false);
//        routeInvalidData.setVisible(false);
//
//        routeEditButton.setVisible(true);
//        routeFinishButton.setVisible(false);
//        routeCancelButton.setVisible(false);
//        refreshMessage.setVisible(false);
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