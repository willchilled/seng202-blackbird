package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.BBDatabase;
import seng202.group2.blackbirdModel.Parser;
import seng202.group2.blackbirdModel.RoutePoint;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sbe67 on 15/09/16.
 */
public class RouteAddingPopUpController {

    ObservableList<String> airports = FXCollections.observableArrayList("No values Loaded");
    @FXML private TextField AirlineIATA;
    @FXML private TextField AirlineID;
    @FXML private ComboBox srcComboBox;
    @FXML private ComboBox dstComboBox;
    @FXML private CheckBox Codeshare;
    @FXML private TextField Stops;
    @FXML private TextField Equipment;
    private Stage adderStage;
    private Parent root;

    public void control() {
        adderStage.setScene(new Scene(root));
        adderStage.setTitle("Add Route Information");
        adderStage.initModality(Modality.NONE);
        adderStage.initOwner(null);

        airports = populateAirports();
        srcComboBox.setItems(airports);
        srcComboBox.setValue(airports.get(0));
        dstComboBox.setItems(airports);
        dstComboBox.setValue(airports.get(0));
        adderStage.show();


    }

    public void createButtonPressed(){
        String[] routePoint = getValues().split(", ");
        String airline = routePoint[0];
        int airlineID = 0;	// 0 if airlineID is null

        if (!routePoint[1].equals("\\N")) {
            airlineID = Integer.parseInt(routePoint[1]);
        }

        RoutePoint myRoutePoint = new RoutePoint(airline, airlineID);
        int count = BBDatabase.getMaxInColumn("ROUTE", "IDnum");
        myRoutePoint = Parser.checkRouteData(routePoint, count);

        myRoutePoint.setRouteID(count);	//set our own routeID

        ArrayList<RoutePoint> myRouteData = new ArrayList<RoutePoint>();
        myRouteData.add(myRoutePoint);

        BBDatabase.addRoutePointstoDB(myRouteData);
        //make so it displays

        adderStage.close();
    }

    public void cancelButtonPressed(){
        //just closes the stage
        adderStage.close();
    }

    private String getValues() {
        String routeAirlineIATA = AirlineIATA.getText().toString();
        String routeAirlineID = AirlineID.getText().toString();
        String routeSrc = srcComboBox.getValue().toString();
        int routeSrcID = BBDatabase.getAirportID(routeSrc); //get src airportID via IATA
        String routeDst = dstComboBox.getValue().toString();
        int routeDstID = BBDatabase.getAirportID(routeDst); //get src airportID via IATA
        boolean codesharChecked = Codeshare.isSelected();
        String routeStops = Stops.getText().toString();
        String routeEquipment = Equipment.getText().toString();
        String values = new String();
        values += routeAirlineIATA;
        values += ", " + routeAirlineID;
        values += ", " + routeSrc;
        values += ", " + routeSrcID;
        values += ", " + routeDst;
        values += ", " + routeDstID;
        if (codesharChecked){
            String routeCodeshare = "Y";
            values += ", " + routeCodeshare;
        }else{
            String routeCodeshare = "";
            values += ", " + routeCodeshare;
        }
        values += ", " + routeStops;
        values += ", " + routeEquipment;

        return values;
    }

    /**
     * A function to get the values needed for the source and destionation combo boxes
     * @return An Observable list of strings holding the distinct IATA codes for airports
     */
    private  ObservableList<String> populateAirports(){
        ArrayList<String> airports =BBDatabase.performDistinctStringQuery("SELECT DISTINCT IATA FROM AIRPORT");
        Collections.sort(airports); //sort so it is easier to find wated airport
        ObservableList<String> airportIATAs = FXCollections.observableArrayList(airports);
        return airportIATAs;
    }

    public void setAdderStage(Stage adderStage) {
        this.adderStage = adderStage;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }
}
