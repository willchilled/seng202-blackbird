package seng202.group2.blackbirdView;


import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private TableView<AirportPoint> airportTable;



    public void show(){

        mainTabPane.setVisible(true);
    }

    public void addAirportData(){

        System.out.println("Add Data");

        File f;

        f = getFile();

        ArrayList<AirportPoint> airportPoints;

        airportPoints = Parser.parseAirportData(f);

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
