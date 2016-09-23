package seng202.group2.blackbirdControl;

/**
 * Created by emr65 on 15/09/16.
 */

import seng202.group2.blackbirdModel.AirportPoint;
import seng202.group2.blackbirdModel.DataBaseRefactor;
import seng202.group2.blackbirdModel.DataPoint;

import javax.swing.*;
import java.io.*;
import java.nio.channels.FileChannel;
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

    /**
     * A method to save the database under a filename
     */
    public static void exportDataBase() {

        JFileChooser chooser = new JFileChooser();
        JPanel mainPanel = new JPanel();
        chooser.setDialogTitle("Export Data as Text File");
        int userChoice = chooser.showSaveDialog(mainPanel);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            try {
                File fileToSave = chooser.getSelectedFile();
                fileToSave.createNewFile();
                String cwd = System.getProperty("user.dir");
                String dbFileName = (cwd+"/default.db");
                FileChannel src = new FileInputStream(dbFileName).getChannel();
                FileChannel dest = new FileOutputStream(fileToSave).getChannel();
                dest.transferFrom(src, 0, src.size());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



}
