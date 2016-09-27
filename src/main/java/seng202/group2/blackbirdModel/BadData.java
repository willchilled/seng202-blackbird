package seng202.group2.blackbirdModel;

/**
 * Created by mch230 on 25/09/16.
 */
public class BadData {
    private int fileLine;
    private String entry;
    private DataTypes type;

    public String getErrorMessage() {
        return errorMessage;
    }

    private String errorMessage;

    public BadData(int fileLine, String entry, DataTypes type) {
        this.fileLine = fileLine;
        this.entry = entry;
        this.type = type;
    }

    public BadData(int fileLine, String entry, DataTypes type, String message) {
        this.fileLine = fileLine;
        this.entry = entry;
        this.type = type;
        errorMessage = message;
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
