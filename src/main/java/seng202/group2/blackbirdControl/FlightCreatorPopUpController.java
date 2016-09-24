package seng202.group2.blackbirdControl;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by wmu16 on 24/09/16.
 */
public class FlightCreatorPopUpController {

    private Stage creatorStage;
    private Parent root;
    private FlightTabController flightTabController;

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void setCreatorStage(Stage creatorStage) {
        this.creatorStage = creatorStage;
    }

    public void setFlightTabController(FlightTabController controller) {
        flightTabController = controller;
    }

    public void control() {
        creatorStage.setScene(new Scene(root));
        creatorStage.setTitle("Create Flight");
        creatorStage.initModality(Modality.NONE);
        creatorStage.initOwner(null);

        creatorStage.show();
    }

    public void flightCreatorCancelButtonPressed(){

        creatorStage.close();
    }

    public void flightCreatorCreateButtonPressed(){

        creatorStage.close();
    }


}
