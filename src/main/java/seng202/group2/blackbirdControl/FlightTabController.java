package seng202.group2.blackbirdControl;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import seng202.group2.blackbirdModel.*;

import javax.swing.*;
import java.io.File;
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



    public void show(){
        //mainController.show();
        flightTable.setPlaceholder(new Label("No data in table. To add data select File -> Add Data -> Flight"));
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
    
            updateFlightsTable(myFlightData);
            mainController.updateTab(DataTypes.FLIGHT);
    }

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
