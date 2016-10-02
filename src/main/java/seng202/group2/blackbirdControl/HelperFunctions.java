package seng202.group2.blackbirdControl;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Class for some additional helper functions
 *
 * @author Team2
 * @version 1.0
 * @since 22/9/2016
 */
public class HelperFunctions {

    /**
     * Helps choose a file
     *
     * @param message Title of the file chooser window
     * @param save    Flag indicating whether we are saving or loading a file- true for saving
     * @return The selected file object
     */
    static File getFile(String message, boolean save) {
        File myFile;
        String cwd = System.getProperty("user.dir");
        File userDirectory = new File(cwd);
        FileChooser fc = new FileChooser();
        fc.setTitle(message);
        fc.setInitialDirectory(userDirectory);

        if (!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        fc.setInitialDirectory(userDirectory);
        if (save) {
            myFile = fc.showSaveDialog(null);
        } else {
            myFile = fc.showOpenDialog(null);
        }

        if (myFile != null) {   //file has been selected
            return myFile;
        } else {
            return null;
        }
    }

    /**
     * Adds a null value at top of an observable list
     *
     * @param populatedList The current observable list
     * @return The observable list with a null value at the beginning
     */
    static ObservableList<String> addNullValue(ObservableList<String> populatedList) {
        populatedList.add(0, "None");
        return populatedList;
    }

    /**
     * Removes a null value from the top of an observable list, if present
     *
     * @param populatedList The current observable list
     * @return The current observable list with the null value removed from the beginning, if present
     */
    protected ObservableList<String> removeNullValue(ObservableList<String> populatedList) {
        if (populatedList.get(0).equals("None")) {
            populatedList.remove(0);
        }
        return populatedList;
    }

    /**
     * Helper function to check there are no null values inside a string array
     *
     * @param checkData The given string array
     * @return Boolean indicating if no null is inside the string array
     */
    static boolean allValid(String[] checkData) {
        boolean allValid = true;
        for (String data : checkData) {
            if (data != null) {
                allValid = false;
            }
        }
        return allValid;
    }

}
