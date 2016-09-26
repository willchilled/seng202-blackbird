

package seng202.group2.blackbirdModel;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;


/**
 * This class handles parsing of data, using an imported library called opencsv and the class CSVReader
 * to read through an inputted file.
 */
public class ParserRefactor {

    /**
     * Parses a given file using CSVReader to generate a string array. This is then passed to the DataPoint
     * static method to create a new DataPoint, and added to an arraylist of DataPoints.
     * @param file The input file
     * @param pointType The type of points that we are wanting to create
     * @return The arraylist of datapoints that have been parsed from the file.
     * @see DataPoint
     * @see CSVReader
     */
    public static ArrayList<DataPoint> parseFile(File file, DataTypes pointType){
        ArrayList<DataPoint> allDataPoints =  new ArrayList<>();
        int count = 0;
        try {
            CSVReader reader = new CSVReader(new FileReader(file), ',', '"', '\0');
            String [] currentLine;
            while ((currentLine = reader.readNext()) != null) {
                count++;
                String[] formattedLine = formatLine(currentLine);
                DataPoint currentDataPoint = DataPoint.createDataPointFromStringArray(formattedLine, pointType, count);
                allDataPoints.add(currentDataPoint);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allDataPoints;
    }

    /**
     * Helper method to format each file line; removes all of the \N's in a line replacing them with null
     * @param currentLine The current line, as a string array
     * @return The formatted line, as a string array
     */
    private static String[] formatLine(String[] currentLine) {
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

