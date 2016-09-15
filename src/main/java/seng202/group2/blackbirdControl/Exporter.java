package seng202.group2.blackbirdControl;

/**
 * Created by emr65 on 15/09/16.
 */

import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.DataPoint;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;


public class Exporter {


    public static void exportData(ArrayList<DataPoint> points){

        JFileChooser chooser = new JFileChooser();
        JPanel mainPanel = new JPanel();
        chooser.setDialogTitle("Export Data as Text File");
        int userChoice = chooser.showSaveDialog(mainPanel);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File fileToSave = chooser.getSelectedFile();
            System.out.println(fileToSave);


            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileToSave), "utf-8"))) {
                
                for(int i = 0; i < points.size(); i++){
                    writer.write(points.get(i).toString()+"\n");
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
