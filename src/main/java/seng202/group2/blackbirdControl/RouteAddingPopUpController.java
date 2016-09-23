package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.*;

import java.util.ArrayList;

/**
 * Created by sbe67 on 15/09/16.
 */
public class RouteAddingPopUpController {

    @FXML private ComboBox airlineSelection;
    @FXML private ComboBox sourceSelection;
    @FXML private ComboBox destSelection;

//    @FXML private TextField AirlineIATA;
//    @FXML private TextField AirlineID;
//    @FXML private TextField Src;
//    @FXML private TextField SrcID;
//    @FXML private TextField Dst;
//    @FXML private TextField DstID;
    @FXML private CheckBox Codeshare;
    @FXML private TextField Stops;
    @FXML private TextField Equipment;
    @FXML private Text addRouteInvalidText;
    private Stage adderStage;
    private Parent root;
    private RouteTabController routeTabController;

    public void control() {
        adderStage.setScene(new Scene(root));
        adderStage.setTitle("Add Route Information");
        adderStage.initModality(Modality.NONE);
        adderStage.initOwner(null);

        //populates the drop down boxes
        ArrayList<String> airlineNames = FilterRefactor.filterDistinct("Name", "Airline");
        ObservableList<String> airlineMenu = FXCollections.observableArrayList(airlineNames);
        airlineMenu = HelperFunctions.addNullValue(airlineMenu);
        airlineSelection.setItems(airlineMenu);
        airlineSelection.setValue(airlineMenu.get(0));

        ArrayList<String> sourceAirports = FilterRefactor.filterDistinct("Name", "Airport");
        ObservableList<String> sourceNames = FXCollections.observableArrayList(sourceAirports);
        sourceNames = HelperFunctions.addNullValue(sourceNames);
        sourceSelection.setItems(sourceNames);
        sourceSelection.setValue(sourceNames.get(0));
        destSelection.setItems(sourceNames);
        destSelection.setValue(sourceNames.get(0));

        adderStage.show();
    }

    public void createButtonPressed(){

        String[] values = getValues();
        if (Validator.checkRoute(values)) {
            String[] valueFields = getFields(values);
            DataPoint myRoutePoint = DataPoint.createDataPointFromStringArray(valueFields, DataTypes.ROUTEPOINT);
            ArrayList<DataPoint> myRouteData = new ArrayList<>();
            myRouteData.add(myRoutePoint);
            DataBaseRefactor.insertDataPoints(myRouteData);
            routeTabController.routesFilterButtonPressed();

            adderStage.close();
        } else {
            addRouteInvalidText.setVisible(true);
        }

        //adderStage.close();

    }

    private String[] getValues() {
        String myAirline = airlineSelection.getValue().toString();
        String mySource = sourceSelection.getValue().toString();
        String myDest = destSelection.getValue().toString();
        String routeCodeshare = "";
        String routeStops = Stops.getText().toString();
        String routeEquipment = Equipment.getText().toString();
        //System.out.println(routeStops);

        boolean codeshareChecked = Codeshare.isSelected();
        if (codeshareChecked){
            routeCodeshare = "Y";
        }

        if(routeStops.isEmpty()) {
            routeStops = "0";
        }
        String[] values = {myAirline, mySource, myDest, routeCodeshare, routeStops, routeEquipment};
        return values;
    }

    private String[] getFields(String[] values) {
        String airline; //index 0 in input file
        String airlineID;
        String srcAirport;
        String srcAirportID;
        String dstAirport;
        String dstAirportID;
        String codeshare;
        String stops;
        String equipment;

        String[] airlineFields = RouteTabController.getIataOrIcao(values[0], DataTypes.AIRLINEPOINT);
        airlineID = airlineFields[0];
        airline = airlineFields[1];

        String[] sourceFields = RouteTabController.getIataOrIcao(values[1], DataTypes.AIRPORTPOINT);
        srcAirportID = sourceFields[0];
        srcAirport = sourceFields[1];

        String[] destFields = RouteTabController.getIataOrIcao(values[2], DataTypes.AIRPORTPOINT);
        dstAirportID = destFields[0];
        dstAirport = destFields[1];

        codeshare = values[3];
        stops = values[4];
        equipment = values[5];

        String[] newString = {airline, airlineID, srcAirport, srcAirportID,
                dstAirport, dstAirportID, codeshare, stops, equipment};

        return newString;
    }


    public void cancelButtonPressed(){
        //just closes the stage
        adderStage.close();
    }

    public void setAdderStage(Stage adderStage) {
        this.adderStage = adderStage;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void setRouteTabController(RouteTabController routeTabController) {
        this.routeTabController = routeTabController;
    }
}
