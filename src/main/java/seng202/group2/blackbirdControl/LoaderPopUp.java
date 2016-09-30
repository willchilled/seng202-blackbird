package seng202.group2.blackbirdControl;

/**
 * Created by emr65 on 30/09/16.
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class LoaderPopUp {

    final Float[] values = new Float[] {-1.0f, 0f, 0.6f, 1.0f};
    final Label [] labels = new Label[values.length];
    final ProgressBar[] pbs = new ProgressBar[values.length];
    final ProgressIndicator[] pins = new ProgressIndicator[values.length];
    final HBox hbs [] = new HBox [values.length];

    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 500, 150);
        stage.setScene(scene);
        stage.setTitle("Please Wait a Moment");


        final Label label = new Label();
        label.setText("Loading Data. \n This may take a while if you have a large data set.");
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);

        final ProgressIndicator pin = new ProgressIndicator();
        pin.setProgress(-1);
        final HBox hb = new HBox();
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(pin, label);


        final VBox vb = new VBox();
        vb.setSpacing(50);
        Insets myInset = new Insets(40);
        vb.setMargin(hb, myInset);
        vb.getChildren().addAll(hb);
        scene.setRoot(vb);
        stage.show();
    }

}
