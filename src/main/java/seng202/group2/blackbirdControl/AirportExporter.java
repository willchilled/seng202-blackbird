package seng202.group2.blackbirdControl;

/**
 * Created by emr65 on 15/09/16.
 */

import seng202.group2.blackbirdModel.AirportPoint;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class AirportExporter {


    public static void exportAirportData(ArrayList<AirportPoint> points){

        JFileChooser chooser = new JFileChooser();

        try(FileWriter fw = new FileWriter(chooser.getSelectedFile()+".txt")){

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
