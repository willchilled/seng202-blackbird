package seng202.group2.blackbirdControl;

import javafx.scene.control.Alert;
import seng202.group2.blackbirdModel.DataPoint;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * Exports data sets for the user to view later, and import to a different project later if they desire.
 *
 * @author Team2
 * @version 2.0
 * @since 19/9/2016
 */
class Exporter {

    /**
     * Exports a dataset as a text file
     *
     * @param points The arraylist of datapoints in the current data set
     */
    static void exportData(ArrayList<DataPoint> points) {
        File fileToSave = HelperFunctions.getFile("Save File", true);
        if (fileToSave == null) {
            return;
        }
        if (points.size() == 0) {   //In the case of other data types, the export button is disabled if empty, thus this alert is tailored for flights
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No flight selected for export");
            alert.setContentText("Please select a flight to be exported first");
            alert.showAndWait();
            return;
        }
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileToSave), "utf-8"))) {
            for (DataPoint point : points) {
                writer.write(point.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method to save the current project, saving as a database file.
     */
    static void exportDataBase() {
        File theirDB = HelperFunctions.getFile("Save Project", true);
        if (theirDB == null) {  //user cancelled
            return;
        }
        try {
            theirDB.createNewFile();
            String cwd = System.getProperty("user.dir");
            String dbFileName = (cwd + "/default.db");
            FileChannel src = new FileInputStream(dbFileName).getChannel();
            FileChannel dest = new FileOutputStream(theirDB).getChannel();
            dest.transferFrom(src, 0, src.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
