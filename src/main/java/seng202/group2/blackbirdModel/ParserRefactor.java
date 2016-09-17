package seng202.group2.blackbirdModel;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;


/**
 * Created by sha162 on 16/09/16.
  */
public class ParserRefactor {

    public static ArrayList<DataPoint> parseFile(File file, String pointType){
        DataPoint currentDataPoint = new DataPoint();
        ArrayList<DataPoint> allDataPoints =  new ArrayList<DataPoint>();
        try {
            CSVReader reader = new CSVReader(new FileReader(file), ',', '"', '\0');
            String [] currentLine;
            while ((currentLine = reader.readNext()) != null) {
                String[] formattedLine = formatLine(currentLine);
                currentDataPoint = DataPoint.createDataPointFromStringArray(formattedLine, pointType);
                allDataPoints.add(currentDataPoint);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allDataPoints;

    }

    private static String[] formatLine(String[] currentLine) {
        //Removes all of the \N's in a line replacing them with null
        String[] formattedLine = new String[currentLine.length];
        int lineCount = 0;
        for (String line: currentLine){
            if ("\\N".equals(line)){
                line = "";
            }
            if (line.matches("([a-zA-Z0-9]+\\\\\\\\'[a-zA-Z0-9]+)+")){
                line = line.replaceAll("\\\\", "");
            }
            formattedLine[lineCount] = line;
            lineCount +=1;
        }
        return formattedLine;
    }

}

