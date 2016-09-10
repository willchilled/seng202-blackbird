package seng202.group2.blackbirdModel;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sha162 on 10/09/16.
 */
public class SQLiteJDBC {

    private static String dataBaseName;

    public static String getDatabaseName() {
        return dataBaseName;
    }

    public static void setDataBaseName(String dataBaseName) {
        SQLiteJDBC.dataBaseName = dataBaseName;
    }

    private static Connection makeConnection(){
        // creates a connection with the data base

        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }


    private static String createAirportTable(){
        String sql = "CREATE TABLE AIRPORT " +
                        "(ID INT PRIMARY KEY    NOT NULL," +
                        " NAME           VARCHAR(40)   NOT NULL," +
                        " CITY           VARCHAR(40)   NOT NULL," +
                        " COUNTRY        VARCHAR(40)   NOT NULL," +
                        " IATA           VARCHAR(5)," +
                        " ICAO           VARCHAR(5)," +
                        " Latitude       FLOAT," +
                        " Longtitude     FLOAT,"+
                        " Altitude       FLOAT," +
                        " Timezone       FLOAT," +
                        " DST            VARCHAR(5)," +
                        " TZ             VARCHAR(40))";


        System.out.println(sql);
        return sql;

    }
    public static void deleteDBFile(){
        //deletes pre existing database file
        String cwd = System.getProperty("user.dir");

        File f = new File(cwd+"/default.db");

        if(f.exists() && f.isFile()){
           f.delete();
        }
    }

    public static void createTables() {
        //dropTables();
        setDataBaseName("jdbc:sqlite:default.db");
        deleteDBFile();
        String dbname = getDatabaseName();

        Connection c = makeConnection();
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            //System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = createAirportTable();


            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Airport Table created successfully");

    }

    public static void addAiportPortsToDB(ArrayList<AirportPoint> airportPoints) {
        for (AirportPoint airport : airportPoints) {

            addAiportPoint(airport);


        }
    }

    private static void addAiportPoint(AirportPoint airport) {
        //System.out.println("SHIT HAPPENS");

        int airportID = airport.getAirportID();
        String airportName = airport.getAirportName();
        String City = airport.getAirportCity();
        String Country = airport.getAirportCountry();
        String Iata = airport.getIata();
        String Icao = airport.getIcao();
        float Latitude = airport.getLatitude();
        float Longtitude = airport.getLongitude();
        float Altitude = airport.getAltitude();
        float timezone = airport.getTimeZone();
        String Dst = airport.getDst();
        String tz= airport.getTz();

      //  System.out.println(airportID + airportName + City + Country + Iata + Icao + Latitude + Longtitude + Altitude + timezone + Dst + tz);




        try {
            Connection c = makeConnection();
            Statement stmt = null;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            //System.out.println("Opened database successfully");
            stmt = c.createStatement();
            String sql = "INSERT INTO AIRPORT (ID, NAME, CITY, COUNTRY, IATA, ICAO, LATITUDE, LONGTITUDE ,ALTITUDE, TIMEZONE, DST, TZ) " +
                    "VALUES (" +
                    + airportID + ", \"" +
                    airportName  + "\", \"" +
                    City + "\", \"" +
                    Country +  "\", \"" +
                    Iata + "\", \"" +
                    Icao + "\", " +
                    Latitude + ", " +
                    Longtitude + ", " +
                    Altitude + ", " +
                    timezone + ", \"" +
                    Dst + "\", \"" +
                    tz + "\"); ";

           // System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

           //System.exit(0);
            System.out.println("Could not add :");
            System.out.println(airportID + airportName + City + Country + Iata + Icao + Latitude + Longtitude + Altitude + timezone + Dst + tz);
        }
        //System.out.println("Records created successfully");
    }


}

