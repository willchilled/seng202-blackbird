package seng202.group2.blackbirdControl;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
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

    @FXML private TextField AirlineIATA;
    @FXML private TextField AirlineID;
    @FXML private TextField Src;
    @FXML private TextField SrcID;
    @FXML private TextField Dst;
    @FXML private TextField DstID;
    @FXML private CheckBox Codeshare;
    @FXML private TextField Stops;
    @FXML private TextField Equipment;
    @FXML private Text addRouteInvalidText;
    private Stage adderStage;
    private Parent root;

    public void control() {
        adderStage.setScene(new Scene(root));
        adderStage.setTitle("Add Route Information");
        adderStage.initModality(Modality.NONE);
        adderStage.initOwner(null);

        adderStage.show();
    }

    public void createButtonPressed(){

//        String[] routePoint = getValues().split(", ", -1);
//        if(Validator.checkRoute(routePoint)) {
//            ArrayList<DataPoint> myRouteData = new ArrayList<>();
//            DataPoint myRoutePoint = DataPoint.createDataPointFromStringArray(routePoint, DataTypes.ROUTEPOINT);
//            myRouteData.add(myRoutePoint);
//
//            DataBaseRefactor.insertDataPoints(myRouteData);
//            adderStage.close();
//        } else {
//            addRouteInvalidText.setVisible(true);
//        }
//
//        adderStage.close();
    }

    public void cancelButtonPressed(){
        //just closes the stage
        adderStage.close();
    }

    private String getValues() {
        String routeAirlineIATA = AirlineIATA.getText().toString();
        String routeAirlineID = AirlineID.getText().toString();
        String routeSrc = Src.getText().toString();
        String routeSrcID = SrcID.getText().toString();
        String routeDst = Dst.getText().toString();
        String routeDstID = DstID.getText().toString();
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

    public void setAdderStage(Stage adderStage) {
        this.adderStage = adderStage;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }
}
