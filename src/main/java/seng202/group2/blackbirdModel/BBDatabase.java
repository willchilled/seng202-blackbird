package seng202.group2.blackbirdModel;
import com.sun.xml.internal.bind.v2.model.core.ID;
import seng202.group2.blackbirdView.GUIController;

import javax.swing.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        //added additional constraints on some of the fields, but i think adding some on parsing as well may simplify things?
        String sql = "CREATE TABLE AIRPORT " +
                        "(ID INT PRIMARY KEY    NOT NULL," +
                        " NAME           VARCHAR(40)   NOT NULL," +
                        " CITY           VARCHAR(40)   NOT NULL," +
                        " COUNTRY        VARCHAR(40)   NOT NULL," +
                        " IATA           CHAR(3)," +    //database isn't happy with any duplicate values, including null
                        " ICAO           CHAR(4)," +
                        " LATITUDE       FLOAT constraint check_lat check (LATITUDE between '-90' and '90')," +
                        " LONGITUDE      FLOAT constraint check_long check (LONGITUDE between '-180' and '180'),"+
                        " ALTITUDE       FLOAT constraint check_alt check (ALTITUDE between '-1500' and '15000')," +
                        " TIMEZONE       FLOAT constraint check_time check (TIMEZONE between '-12.00' and '14.00')," +
                        " DST            CHAR(1) constraint check_dst check (DST in ('E', 'A', 'S', 'O', 'Z', 'N', 'U'))," +
                        " TZ             VARCHAR(40))";
       System.out.println(sql);
        return sql;

    }

    private static String createAirlineTable(){
        String sql = "CREATE TABLE AIRLINE " +
                "(ID INT PRIMARY KEY    NOT NULL," +
                " NAME           VARCHAR(40)   NOT NULL," +
                " ALIAS           VARCHAR(40)   NOT NULL," +
                " IATA           VARCHAR(40)   NOT NULL," +
                " ICAO           VARCHAR(40)," +
                " CALLSIGN           VARCHAR(40)," +
                " COUNTRY           VARCHAR(40)," +
                " ACTIVE           VARCHAR(1))";
        //System.out.println(sql);
        return sql;
    }

    private static String createRouteTable(){
        //creates a route table for sqlite, routes includes links to both the airport and the equipment tables
        String sql = "CREATE TABLE ROUTE" +
                "(IDnum     INTEGER NOT NULL /*ID number for the route*/," +
                "Airline    CHAR(2) NOT NULL /*Airline iata for route*/," +
                "Airlineid  INTEGER /*ID of Airline for route*/," +
                "Src        CHAR(3) NOT NULL /*Source location for route*/," +
                "Srcid      INTEGER NOT NULL /*ID number for source location location*/," +
                "Dst        CHAR(3) NOT NULL /*Destination location for route*/," +
                "Dstid      INTEGER NOT NULL /*ID number for destination location*/," +
                "Codeshare  CHAR(1) /*'Y' if operated by another carrier*/," +
                "Stops      INTEGER NOT NULL /*Number of stops the route takes*/," +
                "foreign key (Src, Srcid, Dst, Dstid) references AIRPORT," +
                "PRIMARY KEY (IDnum)" +
                ")";;
        return sql;
    }

    private static String createEquipmentTable(){
        //creates an equipment table for sqlite, is used to give routes the multivalued atribute equipment
        String sql = "CREATE TABLE EQUIPMENT" +
                "(IDnum          INTEGER NOT NULL /*Comes from route*/, " +
                "EquipmentName CHAR(3) NOT NULL," +
                "PRIMARY KEY (EquipmentName, IDnum), "+
                "FOREIGN KEY (IDnum) " +
                "REFERENCES ROUTE (IDnum)" +
                ")";
        return sql;
    }

    private static String createFlightTable(){
        String sql = "CREATE TABLE FLIGHT" +
                "(Flightid  ROWID/*ID number for the flight*/,"+
                "SrcICAO    CHAR(4) NOT NULL /*Source Airport ICAO code*/," +
                "DstICAO    CHAR(4) NOT NULL /*Destination Airport ICAO code*/,"+
                "PRIMARY KEY (Flightid)" +
                ")";
        return sql;
    }

    private static String createFlightPointTable(){
        String sql = "CREATE TABLE FLIGHTPOINT" +
                "(SeqOrder         INTEGER NOT NULL /*gives the sequence of the flight points*/," +
                "LocaleID       VARCHAR(5) NOT NULL, "+
                "LocationType   CHAR(3) NOT NULL /*Type of location*/, "+
                "Altitude       INTEGER NOT NULL /*Altitudinal co-ordinates for flight point*/, " +
                "Latitude       REAL NOT NULL /*Latitudinal co-ordinates for flight point*/, " +
                "Longitude      REAL NOT NULL /*Longitudinal co-ordinates for flight point*/, "+
                "Flightid       INTEGER NOT NULL /*comes from flight*/," +
                "PRIMARY KEY (SeqOrder, Flightid),"+
                "FOREIGN KEY (Flightid)" +
                "REFERENCES FLIGHT (Flightid) ON DELETE CASCADE" +
                ")";
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

            sql = createAirlineTable();
            stmt.executeUpdate(sql);

            sql = createRouteTable();
            stmt.executeUpdate(sql);

            sql = createEquipmentTable();
            stmt.executeUpdate(sql);

            sql = createFlightTable();
            stmt.executeUpdate(sql);

            sql = createFlightPointTable();
            stmt.executeUpdate(sql);


            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("AIPORT, AIRLINE, ROUTE, EQUIPMENT, FLIGHT Table created successfully");

    }


    //##########################Adding  Data#########################################//

    public static void addFlighttoDB(ArrayList<FlightPoint> flightPoints){
        //Adding flight points into data base
        try {
            //connect yo DB
            Connection c = makeConnection();
            Statement stmt = null;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            //System.out.println("Opened database successfully");
            stmt = c.createStatement();

            //first make a flight with start and end
            FlightPoint srcPoint =  flightPoints.get(0);
            FlightPoint dstPoint =  flightPoints.get(flightPoints.size() - 1);

            String srcICAO = srcPoint.getLocaleID();
            String dstICAO = dstPoint.getLocaleID();


            String sql = "INSERT INTO FLIGHT(SrcICAO, DstICAO) " +
                    "VALUES (" + "\"" + srcICAO + "\", " + "\"" + dstICAO + "\");";

            stmt.executeUpdate(sql);

            //get the new flight id
            sql = "SELECT MAX(Flightid) FROM FLIGHT;";
            ResultSet rs = stmt.executeQuery( sql );
            int flightid = rs.getInt("Flightid");

            //initialise order to show the sequence of the flight points
            int order = 1;


            //for all flight points
            for (FlightPoint point: flightPoints){

                //get info for point
                String locID = point.getLocaleID();
                String locType = point.getType();
                int altitude = point.getAltitude();
                float latitude = point.getLatitude();
                float longitude = point.getLongitude();

                sql = "INSERT INTO FLIGHTPOINT (SeqOrder, LocaleID, LocationType, Altitude, Latitude, Longitude, Flightid)" +
                        "Values (" +
                        order + ", " +
                        "\"" + locID +"\", " +
                        "\"" + locType +"\", " +
                        altitude + ", " +
                        latitude + ", " +
                        longitude + ")";

                //execute route sql
                stmt.executeUpdate(sql);

                //increment order for next point
                order ++;

            }
            stmt.close();
            c.commit();
            c.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
                addSingleAirport(airport, stmt);
            }

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {   //should this exception be made more specific? Or surrounding a more specific code block?
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

            //System.exit(0);
            //System.out.println("Could not add :");

        }
        System.out.println("Records created successfully");
    }

    //Replaced this with addSingleAirport- my bad. Needed a separate method to add each individual entry to make the try-catch useful
//    protected static void addAiportPointToDB(AirportPoint airport) {


    private static void addSingleAirport(AirportPoint airport, Statement stmt) {
        int airportID = airport.getAirportID();
        String airportName = airport.getAirportName();
        String City = airport.getAirportCity();
        String Country = airport.getAirportCountry();
        String Iata = airport.getIata();
        String Icao = airport.getIcao();
        float Latitude = airport.getLatitude();
        float Longitude = airport.getLongitude();
        float Altitude = airport.getAltitude();
        float timezone = airport.getTimeZone();
        String Dst = airport.getDst();
        String tz= airport.getTz();
        //System.out.println(airportID + airportName + City + Country + Iata + Icao + Latitude + Longitude + Altitude + timezone + Dst + tz);

        String sql = "INSERT INTO AIRPORT (ID, NAME, CITY, COUNTRY, IATA, ICAO, LATITUDE, Longitude ,ALTITUDE, TIMEZONE, DST, TZ) " +
                "VALUES (" +
                + airportID + ", \"" +
                airportName  + "\", \"" +
                City + "\", \"" +
                Country +  "\", \"" +
                Iata + "\", \"" +
                Icao + "\", " +
                Latitude + ", " +
                Longitude + ", " +
                Altitude + ", " +
                timezone + ", \"" +
                Dst + "\", \"" +
                tz + "\"); ";

        // System.out.println(sql);
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.out.println("Could not add: " + airportID + " " + airportName + " " + City + " " + Country + ", " + Iata + ", " + Icao);
            //Bring up some sort of alert box here?? Need some sort of way of communicating this to user
            //ISSUE: if there are a lot of errors, you'll be stuck closing each dialog box...... Could we have a separate window or panel for reviewing bad entries?
            JOptionPane.showMessageDialog(new JPanel(), "Error adding data in, please review entry:\n" +
                    "Could not add: " + airportID + ", " + airportName + ", " + City + ", " + Country + ", " + Iata + ", " + Icao,
                    "Error", JOptionPane.ERROR_MESSAGE);
            //System.exit(0);
        }
    }

    public static void addRoutePointstoDB(ArrayList<RoutePoint> routePoints) {
        //adds routes into the database
        try{
            //Connect to DB
            Connection c = makeConnection();
            Statement stmt = null;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            stmt = c.createStatement();

            //Add all routes
            for (RoutePoint route : routePoints) {

                //get info for route
                int IDnum = route.getRouteID();
                String Airline = route.getAirline();
                int Airlineid = route.getAirlineID();
                String src = route.getSrcAirport();
                int srcid = route.getSrcAirportID();
                String dst = route.getDstAirport();
                int dstid = route.getDstAirportID();
                String codeshare = route.getCodeshare();
                int Stops = route.getStops();

                //make route sql text to execute
                String sql = "INSERT INTO ROUTE(IDnum, Airline, Airlineid, Src, Srcid, Dst, Dstid, Codeshare, Stops)" +
                        "VALUES (" +
                        + IDnum + ", " +
                        "\"" + Airline + "\", " +
                        Airlineid + ", " +
                        "\"" + src + "\", " +
                        srcid + ", " +
                        "\"" + dst + "\", " +
                        dstid + ", " +
                        "\"" + codeshare + "\", " +
                        Stops + ");";

                //execute route sql
                stmt.executeUpdate(sql);

                //equipment is special cos its a dick
                String strEquipment = route.getEquipment();
                String[] ListEquipment = strEquipment.split(" ");

                //make equipment sql
                //for all equipment in route
                for(String equip : ListEquipment){
                    //add equipment to equipment table
                    sql = "INSERT INTO EQUIPMENT (IDnum, EquipmentName)" +
                            "VALUES(" +
                            "" + IDnum + ", " +
                            "\"" + equip + "\");";
                    stmt.executeUpdate(sql);
                }
            }
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

            //System.out.println("Could not add :");
        }
        System.out.println("Records created successfully");
        }

    public static void addAirlinePointstoDB(ArrayList<AirlinePoint> airlinePoints) {
        //This method adds multiple points to the Database
        try {

            Connection c = makeConnection();
            Statement stmt = null;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            //System.out.println("Opened database successfully");
            stmt = c.createStatement();

            for (AirlinePoint airport : airlinePoints) {

                int id = airport.getAirlineID();
                String name = airport.getAirlineName();
                //System.out.println(name);
                String alias = airport.getAirlineAlias();
                String iata = airport.getIata();
                String icao = airport.getIcao();
                String callsign = airport.getCallsign();
                String country = airport.getCountry();
                String active = airport.getActive();



                String sql = "INSERT INTO AIRLINE (ID, NAME, ALIAS, IATA, ICAO, CALLSIGN, COUNTRY, ACTIVE) " +
                        "VALUES (" +
                        + id + ", \"" +
                        name  + "\", \"" +
                        alias + "\", \"" +
                        iata +  "\", \"" +
                        icao + "\", \"" +
                        callsign + "\", \"" +
                        country + "\", \""+
                        active + "\");";
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
            // System.out.println(airportID + airportName + City + Country + Iata + Icao + Latitude + Longitude + Altitude + timezone + Dst + tz);
        }
        System.out.println("Records created successfully");
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
                float Longitude = rs.getFloat("Longitude");
                float Altitude = rs.getFloat("ALTITUDE");
                float timezone = rs.getFloat("TIMEZONE");
                String Dst = rs.getString("DST");
                String tz= rs.getString("TZ");
               // System.out.println(airportName);

                AirportPoint myPoint = new AirportPoint(airportID, airportName);
                myPoint.setAirportCity(City);
                myPoint.setAirportCountry(Country);
                myPoint.setIata(Iata);
                myPoint.setIcao(Icao);
                myPoint.setLatitude(Latitude);
                myPoint.setLongitude(Longitude);
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

    public static ArrayList<AirlinePoint> performAirlinesQuery(String sql) {
        Connection c = makeConnection();
        ArrayList<AirlinePoint> allPoints = new ArrayList<AirlinePoint>();

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
                String alias = rs.getString("alias");
                String Iata = rs.getString("IATA");
                String Icao = rs.getString("ICAO");
                String callsign = rs.getString("CALLSIGN");
                String country = rs.getString("COUNTRY");
                String active = rs.getString("Active");


                // System.out.println(airportName);
                AirlinePoint myPoint = new AirlinePoint(airportID, airportName);
                myPoint.setAirlineName(airportName);
                myPoint.setAirlineAlias(alias);
                myPoint.setIata(Iata);
                myPoint.setCallsign(callsign);
                myPoint.setCountry(country);
                myPoint.setActive(active);

                allPoints.add(myPoint);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Airlines Query done successfully:" + sql);

        return allPoints;

    }

    public static ArrayList<String> performDistinctRoutesQuery(String sql) {

        Connection c = makeConnection();
        ArrayList<AirlinePoint> allPoints = new ArrayList<AirlinePoint>();

        ArrayList<String> uniqueEntities = new ArrayList<>();

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( sql );
            while ( rs.next() ) {

                String myresult = rs.getString(1); //We can use 0 as it only returns 1 result
                //rs.get
                //System.out.println(myresult);
                uniqueEntities.add(myresult);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Disting Query Query done successfully:" + sql);

        return uniqueEntities;

    }
}

