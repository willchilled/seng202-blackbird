package seng202.group2.blackbirdModel;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sha162 on 10/09/16.
 */
public class BBDatabase {

    private static String dataBaseName;

    public static String getDatabaseName() {
        return dataBaseName;
    }

    public static void setDataBaseName(String dataBaseName) {
        BBDatabase.dataBaseName = dataBaseName;
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

    public static void deleteDBFile(){
        //deletes pre existing database file
        String cwd = System.getProperty("user.dir");

        File f = new File(cwd+"/default.db");

        if(f.exists() && f.isFile()){
            f.delete();
        }
    }

    //#######################MAKING TABLES//
    private static String createAirportTable(){
        String sql = "CREATE TABLE AIRPORT " +
                        "(ID INT PRIMARY KEY    NOT NULL," +
                        " NAME           VARCHAR(40)   NOT NULL," +
                        " CITY           VARCHAR(40)   NOT NULL," +
                        " COUNTRY        VARCHAR(40)   NOT NULL," +
                        " IATA           VARCHAR(5)," +
                        " ICAO           VARCHAR(5)," +
                        " LATITUDE       FLOAT," +
                        " LONGTITUDE     FLOAT,"+
                        " ALTITUDE       FLOAT," +
                        " TIMEZONE       FLOAT," +
                        " DST            VARCHAR(5)," +
                        " TZ             VARCHAR(40))";


        System.out.println(sql);
        return sql;

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


    //##########################Adding Airport Data#########################################//
    public static void addAiportPortsToDB(ArrayList<AirportPoint> airportPoints) {
        //This method adds multiple points to the Database
        try {

            Connection c = makeConnection();
            Statement stmt = null;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            //System.out.println("Opened database successfully");
            stmt = c.createStatement();

            for (AirportPoint airport : airportPoints) {
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


            }

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

            //System.exit(0);
            System.out.println("Could not add :");
           // System.out.println(airportID + airportName + City + Country + Iata + Icao + Latitude + Longtitude + Altitude + timezone + Dst + tz);
        }
        System.out.println("Records created successfully");
    }

    protected static void addAiportPointToDB(AirportPoint airport) {
        //This method adds a singular Airport Point to DB -- Use this for adding data
        //need to make catch useful

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



    //######################### FILTER METHODS#################################
    public static ArrayList<AirportPoint> performAirpointsQuery(String sql) {
        Connection c = makeConnection();
        ArrayList<AirportPoint> allPoints = new ArrayList<AirportPoint>();

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( sql );
            while ( rs.next() ) {


                int airportID = rs.getInt("ID");

                String airportName = rs.getString("NAME");
                String City = rs.getString("CITY");
                String Country = rs.getString("COUNTRY");
                String Iata = rs.getString("IATA");
                String Icao = rs.getString("ICAO");
                float Latitude = rs.getFloat("LATITUDE");
                float Longtitude = rs.getFloat("LONGTITUDE");
                float Altitude = rs.getFloat("ALTITUDE");
                float timezone = rs.getFloat("TIMEZONE");
                String Dst = rs.getString("DST");
                String tz= rs.getString("TZ");

                System.out.println(airportName);

                AirportPoint myPoint = new AirportPoint(airportID, airportName);
                myPoint.setAirportCity(City);
                myPoint.setAirportCountry(Country);
                myPoint.setIata(Iata);
                myPoint.setIcao(Icao);
                myPoint.setLatitude(Latitude);
                myPoint.setLongitude(Longtitude);
                myPoint.setAltitude((int)Altitude);
                myPoint.setTimeZone(timezone);
                myPoint.setDst(Dst);
                myPoint.setTz(tz);

                allPoints.add(myPoint);


            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

        return allPoints;
    }

}

