package seng202.group2.blackbirdModel;

/**
 * Created by mch230 on 25/09/16.
 */
public class BadData {
    private int fileLine;
    private String entry;
    private DataTypes type;

    public BadData(int fileLine, String entry, DataTypes type) {
        this.fileLine = fileLine;
        this.entry = entry;
        this.type = type;
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
}
