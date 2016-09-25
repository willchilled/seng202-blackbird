package seng202.group2.blackbirdControl;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by emr65 on 21/09/16.
 */
public class HelperFunctions {


    protected static File getFile(String message) {
        //gets a file of a specified type
        //gets a file of a specified type
        File myFile;
        String cwd = System.getProperty("user.dir");
        File userDirectory = new File(cwd);

        FileChooser fc = new FileChooser();
        fc.setTitle(message);

        fc.setInitialDirectory(userDirectory);

        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        fc.setInitialDirectory(userDirectory);

        //Choose the file
        myFile = fc.showOpenDialog(null);
        //Make sure a file was selected, if not return default

        if(myFile != null) {
            return myFile;
        }
        else{
            return null;
        }

    }

    protected static File makeFile(String message) {
        File myFile;
        String cwd = System.getProperty("user.dir");
        File userDirectory = new File(cwd);

        FileChooser fc = new FileChooser();
        fc.setTitle(message);

        fc.setInitialDirectory(userDirectory);

        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        fc.setInitialDirectory(userDirectory);

        //Choose the file
        myFile = fc.showSaveDialog(null);
        //Make sure a file was selected, if not return default

        if(myFile != null) {
            return myFile;
        }
        else{
            return null;
        }

    }

    protected static ObservableList<String> addNullValue(ObservableList<String> populatedList){
        //adds a null value at top of list
        populatedList.add(0, "None");
        return populatedList;
    }

    protected ObservableList<String> removeNullValue(ObservableList<String> populatedList){
        //Removes null value from a list
        if (populatedList.get(0) == "None"){
            populatedList.remove(0);
        }

        return populatedList;
    }
}
