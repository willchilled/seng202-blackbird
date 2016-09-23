package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by emr65 on 20/09/16.
 */
public class FlightTabController {

    private MainController mainController;

    //FLIGHT and FLIGHT POINT tables
    @FXML private TableView<Flight> flightTable;
    @FXML private TableView<DataPoint> flightPointTable;

    //FLIGHT Table columns
    @FXML private TableColumn flightSourceCol;
    @FXML private TableColumn flightDestCol;

    //FLIGHT POINT Table columns
    @FXML private TableColumn flightPointTypeCol;
    @FXML private TableColumn flightPointLocaleCol;
    @FXML private TableColumn flightPointAltitudeCol;
    @FXML private TableColumn flightPointLatitudeCol;
    @FXML private TableColumn flightPointLongitudeCol;

    @FXML private ComboBox flightSrcICAOMenu;
    @FXML private ComboBox flightDstICAOMenu;

    ObservableList<String> flightSrcICAOList = FXCollections.observableArrayList("No values Loaded");
    ObservableList<String> flightDstICAOList  = FXCollections.observableArrayList("No values Loaded");



    public void show(){
        //mainController.show();
        flightTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Flight"));
        flightPointTable.setPlaceholder(new Label("No data in table. Double click on a flight"));
    }




    public void setMainController(MainController controller) {
        this.mainController = controller;
    }


    public void addFlightData() {
            File f = HelperFunctions.getFile("Add Flight Data");
            if (f == null) {
                return;
            }
            ArrayList<DataPoint> myFlightData = ParserRefactor.parseFile(f, DataTypes.FLIGHTPOINT);
            //BBDatabase.addFlighttoDB(myFlightData);
            DataBaseRefactor.insertDataPoints(myFlightData);
            ArrayList<DataPoint> flightdata = FilterRefactor.getAllPoints(DataTypes.FLIGHTPOINT);


            //updateFlightFields();
            updateFlightsTable(myFlightData);
            mainController.updateTab(DataTypes.FLIGHT);
    }


//================vvvvvvvvvv=============FLIGHT STUFF TO BE IMPLEMENTED=================vvvvvvvvvvvvvv===============//

    //TODO We need to implement this when we can work out how to make sceneBuilder work!
//    private void updateFlightFields() {
//
//        flightSrcICAOList = populateFlightSrcList();
//        flightSrcICAOMenu.setItems(flightSrcICAOList);
//        flightSrcICAOMenu.setValue(flightSrcICAOList.get(0));
//
//
//        flightDstICAOList = populateFlightDstList();  //populating from valid data in database
//        flightDstICAOMenu.setItems(flightDstICAOList);
//        flightDstICAOMenu.setValue(flightDstICAOList.get(0));
//    }
//
//    private ObservableList<String> populateFlightSrcList(){
//        ArrayList<String> srcICAOs = FilterRefactor.filterDistinct("SrcICAO", "FLIGHT");
//        ObservableList<String> srcICAOList = FXCollections.observableArrayList(srcICAOs);
//        srcICAOList = HelperFunctions.addNullValue(srcICAOList); //we need to add a null value
//        return srcICAOList;
//    }
//
//
//    private ObservableList<String> populateFlightDstList(){
//        ArrayList<String> dstICAOs = FilterRefactor.filterDistinct("DstICAO", "FLIGHT");
//        ObservableList<String> dstICAOList = FXCollections.observableArrayList(dstICAOs);
//        dstICAOList = HelperFunctions.addNullValue(dstICAOList); //we need to add a null value
//        return dstICAOList;
//    }
//
//    public void flightFilterButtonPressed() {
//        String srcAirport = flightSrcMenu.getValue().toString();
//        String dstAirport = flightDstMenu.getValue().toString();
//        String searchQuery = flightSearchQuery.getText().toString();
//
//        ArrayList<String> menusPressed  = new ArrayList<String>();
//        menusPressed.add(srcAirport);
//        menusPressed.add(dstAirport);
//
//
//        ArrayList<DataPoint> allPoints = FilterRefactor.filterSelections(menusPressed, searchQuery, DataTypes.FLIGHT);
//        updateFlightsTable(allPoints);
//
//    }
//
//    public void flightMakeFlightButtonPressed(ActionEvent actionEvent) {
//
//        //Brings up popup to create a flight
//        try {
//            Stage adderStage = new Stage();
//            Parent root;
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FlightCreatorPopUp.fxml"));
//            root = loader.load();
//
//            //use controller to control it
//            AirlineAddingPopUpController popUpController = loader.getController();
//            popUpController.setAdderStage(adderStage);
//            popUpController.setRoot(root);
//            popUpController.control();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

// ======================^^^^^^^==================FLIGHT STUFF TO BE IMPLEMENTED===============^^^^^^^^^^^============//


    private void updateFlightsTable(ArrayList<DataPoint> points) {

        Flight myFlight = new Flight(points);


        flightTable.getItems().addAll(myFlight);
        flightSourceCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("srcAirport"));
        flightDestCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("destAirport"));

        flightTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    //exportFlightMenuButton.setDisable(false);

                    Flight pressedFlight = flightTable.getSelectionModel().getSelectedItem();

                    flightPointTable.getItems().setAll(pressedFlight.getFlightPoints());

                    flightPointTypeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("localType"));
                    flightPointLocaleCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("localeID"));
                    flightPointAltitudeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, Integer>("altitude"));
                    flightPointLatitudeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("latitude"));
                    flightPointLongitudeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("longitude"));
                }

            }
        });

    }


    public void exportFlightData() {
        //Giver user a warning that it will only export the currently selected flight (the one in the flightpoint table)
        //NEED TO LABEL THE FLIGHT TABLES.

        ArrayList<DataPoint> myPoints = new ArrayList<DataPoint>(flightPointTable.getItems());
        Exporter.exportData(myPoints);
    }
}
