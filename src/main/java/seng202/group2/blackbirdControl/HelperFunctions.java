package seng202.group2.blackbirdControl;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import seng202.group2.blackbirdModel.DataBaseRefactor;

import java.io.File;
import java.util.Optional;

/**
 * Created by emr65 on 21/09/16.
 */
public class HelperFunctions {


    protected static File getFile(String message, boolean save) {
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
        if(save){
            myFile = fc.showSaveDialog(null);
        }
        else{
            myFile = fc.showOpenDialog(null);
        }
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


    public static void deleteSingleEntry(String sql){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Entry");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();

        if((result.isPresent()) && (result.get() == ButtonType.OK)){
            DataBaseRefactor.editDataEntry(sql);
            //Query
            //Update
        }

        //SHOW ALERT
        //DO THE QUERY
    }

}
