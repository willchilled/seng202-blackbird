package seng202.group2.blackbirdView;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group2.blackbirdModel.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by emr65 on 13/08/16.
 */
public class GUIController {

    @FXML
    private TabPane mainTabPane;

    @FXML
    private javafx.scene.control.MenuItem newProjMenu;

    @FXML
    private MenuItem addDataMenuButton;

    @FXML private TableView<AirportPoint> airportTable;
    @FXML private TableView<AirlinePoint> airlineTable;
    @FXML private TableView<RoutePoint> routeTable;
   // @FXML private TableView<AirportPoint> airportTable;



// AIRPORT Table columns
    @FXML private TableColumn airportIDCol;
    @FXML private TableColumn airportNameCol;
    @FXML private TableColumn airportCityCol;
    @FXML private TableColumn airportCountryCol;
    @FXML private TableColumn airportIATACol;
    @FXML private TableColumn airportICAOCol;
    @FXML private TableColumn airportLatCol;
    @FXML private TableColumn airportLongCol;
    @FXML private TableColumn airportAltCol;
    @FXML private TableColumn airportTimeCol;
    @FXML private TableColumn airportDSTCol;
    @FXML private TableColumn airportTZCol;

// AIRLINE Table columns
    @FXML private TableColumn airlineIDCol;
    @FXML private TableColumn airlineNameCol;
    @FXML private TableColumn airlineAliasCol;
    @FXML private TableColumn airlineIATACol;
    @FXML private TableColumn airlineICAOCol;
    @FXML private TableColumn airlineCallsignCol;
    @FXML private TableColumn airlineCountryCol;
    @FXML private TableColumn airlineActCol;


// ROUTE Table columns
    @FXML private TableColumn routeAirlineCol;
    @FXML private TableColumn routeAirlineIDCol;
    @FXML private TableColumn routeSrcCol;
    @FXML private TableColumn routeSrcIDCol;
    @FXML private TableColumn routeDestCol;
    @FXML private TableColumn routeDestIDCol;
    @FXML private TableColumn routeCSCol;
    @FXML private TableColumn routeStopsCol;
    @FXML private TableColumn routeEqCol;




    public void show(){

        mainTabPane.setVisible(true);
        addDataMenuButton.setDisable(false);
    }

    public void addAirportData(){



        System.out.println("Add Airport Data");

        //File f;
        //f = getFile();
        //ArrayList<AirportPoint> airportPoints;
        //ArrayList<AirportPoint> myAirportData = Parser.parseAirportData(f);

        ArrayList<AirportPoint> airportPoints = new ArrayList<AirportPoint>();

        AirportPoint testAirport = new AirportPoint(233, "TestAirportName");
        testAirport.setAirportCity("Christchurch");
        testAirport.setAirportCountry("New Zealand");
        testAirport.setIata("CHC");
        testAirport.setIcao("NZCH");
        testAirport.setLatitude(43.489358f);
        testAirport.setLongitude(172.532225f);
        testAirport.setAltitude(127);
        testAirport.setTimeZone(12);
        testAirport.setDst("Z");
        testAirport.setTz("Pacific/Auckland");

        AirportPoint testAirport2 = new AirportPoint(355, "TestAirportName2");
        testAirport2.setAirportCity("London");
        testAirport2.setAirportCountry("New Zealand");
        testAirport2.setIata("CHC");
        testAirport2.setIcao("NZCH");
        testAirport2.setLatitude(43.489358f);
        testAirport2.setLongitude(172.532225f);
        testAirport2.setAltitude(127);
        testAirport2.setTimeZone(12);
        testAirport2.setDst("Z");
        testAirport2.setTz("Pacific/Auckland");

        airportPoints.add(testAirport);
        airportPoints.add(testAirport2);

        airportTable.getItems().setAll(airportPoints);

        airportIDCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, Integer>("airportID"));
        airportNameCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("airportName"));
        airportCityCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("airportCity"));
        airportCountryCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("airportCountry"));
        airportIATACol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("iata"));
        airportICAOCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("icao"));
        airportLatCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("latitude"));
        airportLongCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("longitude"));
        airportAltCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("altitude"));
        airportTimeCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("timeZone"));
        airportDSTCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("dst"));
        airportTZCol.setCellValueFactory(new PropertyValueFactory<AirportPoint, String>("tz"));

    }

    public void addAirlineData(){

        System.out.println("Add Airline Data");

        //File f;
        //f = getFile();
        //ArrayList<AirlinePoint> airlinePoints;
        //ArrayList<AirlinePoint> myAirlineData = Parser.parseAirlineData(f);


        //Creating a test airline to add in while waiting for the parser to be working
        ArrayList<AirlinePoint> airlinePoints = new ArrayList<AirlinePoint>();

        AirlinePoint testAirline = new AirlinePoint(1, "Test Airline 1");
        testAirline.setAirlineAlias("ANA");
        testAirline.setIata("NH");
        testAirline.setIcao("ANA");
        testAirline.setCallsign("ALL NIPPON");
        testAirline.setCountry("Japan");
        testAirline.setActive("Y");

        AirlinePoint testAirline2 = new AirlinePoint(2, "Test Airline 2");
        testAirline2.setAirlineAlias("YYT");
        testAirline2.setIata("RE");
        testAirline2.setIcao("YYT");
        testAirline2.setCallsign("WOW TEST");
        testAirline2.setCountry("A Country");
        testAirline2.setActive("Y");

        airlinePoints.add(testAirline);
        airlinePoints.add(testAirline2);

        airlineTable.getItems().setAll(airlinePoints);

        airlineIDCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, Integer>("airlineID"));
        airlineNameCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("airlineName"));
        airlineAliasCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("airlineAlias"));
        airlineIATACol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("iata"));
        airlineICAOCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("icao"));
        airlineCallsignCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("callsign"));
        airlineCountryCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("country"));
        airlineActCol.setCellValueFactory(new PropertyValueFactory<AirlinePoint, String>("active"));
    }



    public void addRouteData(){

        System.out.println("Add Route Data");

        //File f;
        //f = getFile();
        //ArrayList<RoutePoint> routePoints;
        //ArrayList<RoutePoint> myRouteData = Parser.parseRouteData(f);


        //Creating a test airline to add in while waiting for the parser to be working
        ArrayList<RoutePoint> routePoints = new ArrayList<RoutePoint>();

        RoutePoint testRoute = new RoutePoint("route airline", 56);
        testRoute.setSrcAirport("Src Airport hey");
        testRoute.setSrcAirportID(444);
        testRoute.setDstAirport("Dst airport ho");
        testRoute.setDstAirportID(55);
        testRoute.setCodeshare("Y");
        testRoute.setStops(0);
        testRoute.setEquipment(new String[]{"777", "747"});


        RoutePoint testRoute2 = new RoutePoint("route airline 2", 66);
        testRoute2.setSrcAirport("Src Airport ho");
        testRoute2.setSrcAirportID(333);
        testRoute2.setDstAirport("Dst airport hey");
        testRoute2.setDstAirportID(779);
        testRoute2.setCodeshare("Y");
        testRoute2.setStops(2);
        testRoute2.setEquipment(new String[]{"747"});


        routePoints.add(testRoute);
        routePoints.add(testRoute2);

        routeTable.getItems().setAll(routePoints);

        routeAirlineCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("airline"));
        routeAirlineIDCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, Integer>("airlineID"));
        routeSrcCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("srcAirport"));
        routeSrcIDCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("srcAirportID"));
        routeDestCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("dstAirport"));
        routeDestIDCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("dstAirportID"));
        routeCSCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String>("codeshare"));
        routeStopsCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, Integer>("stops"));
        routeEqCol.setCellValueFactory(new PropertyValueFactory<RoutePoint, String[]>("equipment"));


    }




    public File getFile() {

        File f;

        String cwd = System.getProperty("user.dir");

        JFileChooser jfc = new JFileChooser(cwd);
        int userChoice = jfc.showOpenDialog(null);

        switch (userChoice) {
            case JFileChooser.APPROVE_OPTION:
                f = jfc.getSelectedFile();
                if (f.exists() && f.isFile() && f.canRead()) {
                    return f;
                }
            case JFileChooser.CANCEL_OPTION:
                // fall through
            case JFileChooser.ERROR_OPTION:
                System.out.println("ERROR IN FILE");
        }
        return null;
    }
}
