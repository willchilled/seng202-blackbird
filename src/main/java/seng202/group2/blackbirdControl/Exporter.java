package seng202.group2.blackbirdControl;

/**
 * Created by emr65 on 15/09/16.
 */

import javafx.stage.FileChooser;
import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.DataPoint;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;


public class Exporter {


    public static void exportData(ArrayList<DataPoint> points){

        File fileToSave;
        String cwd = System.getProperty("user.dir");
        File userDirectory = new File(cwd);

        FileChooser fc = new FileChooser();
        fc.setTitle("Save");

        fc.setInitialDirectory(userDirectory);

        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        fc.setInitialDirectory(userDirectory);

        //Choose the file
        fileToSave = fc.showSaveDialog(null);
        if(fileToSave != null) {
            //Make sure a file was selected, if not return default

            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileToSave), "utf-8"))) {

                for (int i = 0; i < points.size(); i++) {
                    writer.write(points.get(i).toString() + "\n");
                    //   System.out.println(points.get(i).toString());
                }
                System.out.println("WOW HERE OMG");

                //writer.write("something");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        }


    }



