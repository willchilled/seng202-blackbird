package seng202.group2.blackbirdControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.blackbirdModel.*;

import java.util.ArrayList;

/**
 * This Route Adding pop up class allows enters to manually add
 * a route point to the program.
 *
 * @author Team2
 * @version 2.0
 * @since 19/9/2016
 */
public class RouteAddingPopUpController {

    private Stage adderStage;
    private Parent root;
    private RouteTabController routeTabController;
    private boolean added = false;

    @FXML private ComboBox airlineSelection;
    @FXML private ComboBox sourceSelection;
    @FXML private ComboBox destSelection;
    @FXML private CheckBox Codeshare;
    @FXML private TextField Stops;
    @FXML private TextField Equipment;
    @FXML private Text addRouteInvalidText;

    /**
     * Initialises the route adding pop up.
     */
    public void control() {
        adderStage.setScene(new Scene(root));
        adderStage.setTitle("Add Route Information");
        adderStage.initModality(Modality.NONE);
        adderStage.initOwner(null);

        ArrayList<String> airlineNames = Filter.filterDistinct("Name", "Airline");
        ObservableList<String> airlineMenu = FXCollections.observableArrayList(airlineNames);
        airlineMenu = HelperFunctions.addNullValue(airlineMenu);
        airlineSelection.setItems(airlineMenu);
        airlineSelection.setValue(airlineMenu.get(0));

        ArrayList<String> sourceAirports = Filter.filterDistinct("Name", "Airport");
        ObservableList<String> sourceNames = FXCollections.observableArrayList(sourceAirports);
        sourceNames = HelperFunctions.addNullValue(sourceNames);
        sourceSelection.setItems(sourceNames);
        sourceSelection.setValue(sourceNames.get(0));
        destSelection.setItems(sourceNames);
        destSelection.setValue(sourceNames.get(0));

        adderStage.show();
    }

    /**
     * This method is called when the create button is pressed, passing the
     * inputted values through to be checked by both the validator and the
     * database. If an error occurs, shows an error message.
     */
    public void createButtonPressed() {
        String[] values = getValues();
        String[] checkData = Validator.checkRoute(values);
        if (HelperFunctions.allValid(checkData)) {
            String[] valueFields = RouteTabController.getFields(values);
            DataPoint myRoutePoint = DataPoint.createDataPointFromStringArray(valueFields, DataTypes.ROUTEPOINT, 0, null);
            ArrayList<DataPoint> myRouteData = new ArrayList<>();
            myRouteData.add(myRoutePoint);
            DatabaseInterface.insertDataPoints(myRouteData, null);
            routeTabController.routesFilterButtonPressed();
            added = true;
            adderStage.close();
        } else {
            Validator.displayRouteError(checkData);
            addRouteInvalidText.setVisible(true);
        }
    }

    /**
     * Helper function to retrieve inputted values that the user has entered
     *
     * @return The string array of values retrieved from the entry fields
     */
    private String[] getValues() {
        String myAirline = airlineSelection.getValue().toString();
        String mySource = sourceSelection.getValue().toString();
        String myDest = destSelection.getValue().toString();
        String routeCodeshare = "";
        String routeStops = Stops.getText();
        String routeEquipment = Equipment.getText();

        boolean codeshareChecked = Codeshare.isSelected();
        if (codeshareChecked) {
            routeCodeshare = "Y";
        }
        if (routeStops.isEmpty()) {
            routeStops = "0";
        }
        String[] values = {myAirline, mySource, myDest, routeCodeshare, routeStops, routeEquipment};
        return values;
    }

    /**
     * Closes the pop up on cancel
     */
    public void cancelButtonPressed() {
        adderStage.close();
    }

    /**
     * Assigns an action for the enter key
     *
     * @param ke The keyevent that occurred (the Enter key event)
     */
    public void enterPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ENTER) {
            createButtonPressed();
        }
    }

    /**
     * Sets the stage for the pop up
     *
     * @param adderStage The stage for the pop up
     */
    void setAdderStage(Stage adderStage) {
        this.adderStage = adderStage;
    }

    /**
     * Sets the root for the pop up
     *
     * @param root The parent root
     */
    public void setRoot(Parent root) {
        this.root = root;
    }

    /**
     * Sets the related route tab for the pop up
     *
     * @param routeTabController The route tab invoking the pop up
     * @see RouteTabController
     */
    void setRouteTabController(RouteTabController routeTabController) {
        this.routeTabController = routeTabController;
    }

    /**
     * Helper function for the error tab controller, to detect whether the point was added or not
     *
     * @return Boolean for whether the point was added or not.
     */
    public boolean isAdded() {
        return added;
    }
}
