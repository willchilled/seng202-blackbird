package seng202.group2.blackbirdControl;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import seng202.group2.blackbirdModel.BadData;

import java.util.ArrayList;

/**
 * Created by mch230 on 25/09/16.
 */
public class ErrorTabController {
    @FXML private TableView<?> routeErrors;
    @FXML private TableColumn<?, ?> routeFileLine;
    @FXML private TableColumn<?, ?> routeEntry;

    @FXML private TableView<?> airportErrors;
    @FXML private TableColumn<?, ?> airportFileLine;
    @FXML private TableColumn<?, ?> airportEntry;

    @FXML private TableView<?> airlineErrors;
    @FXML private TableColumn<?, ?> airlineFileLine;
    @FXML private TableColumn<?, ?> airlineEntry;

//    protected void updateErrors(ArrayList<BadData>)
}
