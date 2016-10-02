package seng202.group2.blackbirdModel;

/**
 *
 * Class for displaying bad data
 */
public class BadData {
    private int fileLine;
    private String entry;
    private DataTypes type;
    private String errorMessage;

    /**
     * Constructor for the bad data type
     *
     * @param fileLine the line where the error occured
     * @param entry all of the data for that line
     * @param type  the datatype of the point
     * @param message the error message that will be displayed
     */
    public BadData(int fileLine, String entry, DataTypes type, String message) {
        this.fileLine = fileLine;
        this.entry = entry;
        this.type = type;
        errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getEntry() {
        return entry;
    }

    public int getFileLine() {
        return fileLine;
    }

    public DataTypes getType() {
        return type;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
