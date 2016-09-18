package seng202.group2.blackbirdModel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.File;
import java.sql.*;
import java.util.*;

/**
 * Created by sha162 on 10/09/16.
 */
public class BBDatabase {

    private static String dataBaseName = "jdbc:sqlite:default.db";


    public static int getMaxInColumn(String tableName, String columnName) {
        //Returns the highest value in a column for a table
        int highID = 0;
        try {
            //Connect to DB
            Connection c = makeConnection();
            Statement stmt = null;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            stmt = c.createStatement();

            String sql = "SELECT " + columnName + ", MAX(" + columnName + ") FROM " + tableName;
            ResultSet rs = stmt.executeQuery(sql);
            highID = rs.getInt(columnName);
            stmt.close();
            c.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highID;
    }

    public static String getDatabaseName() {
        return dataBaseName;
    }

    public static void setDataBaseName(String dataBaseName) {
        BBDatabase.dataBaseName = dataBaseName;
    }

    private static Connection makeConnection() {
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

    public static void deleteDBFile() {
        //deletes pre existing database file
        String cwd = System.getProperty("user.dir");

        File f = new File(cwd + "/default.db");

        if (f.exists() && f.isFile()) {
            f.delete();
        }
    }

    //#######################MAKING TABLES//
    private static String createAirportTable() {
        //added additional constraints on some of the fields, but i think adding some on parsing as well may simplify things?
        String sql = "CREATE TABLE AIRPORT " +
                "(ID INTEGER PRIMARY KEY    NOT NULL," +
                " NAME           VARCHAR(40)   NOT NULL," +
                " CITY           VARCHAR(40)   NOT NULL," +
                " COUNTRY        VARCHAR(40)   NOT NULL," +
                " IATA           CHAR(3)," +    //database isn't happy with any duplicate values, including null. Note: can have either IATA or ICAO, perform check if it has at least one?
                " ICAO           CHAR(4)," +
                " LATITUDE       FLOAT constraint check_lat check (LATITUDE between '-90' and '90')," +
                " LONGITUDE      FLOAT constraint check_long check (LONGITUDE between '-180' and '180')," +
                " ALTITUDE       FLOAT constraint check_alt check (ALTITUDE between '-1500' and '15000')," +
                " TIMEZONE       FLOAT constraint check_time check (TIMEZONE between '-12.00' and '14.00')," +
                " DST            CHAR(1) constraint check_dst check (DST in ('E', 'A', 'S', 'O', 'Z', 'N', 'U', 'null'))," +
                " TZ             VARCHAR(40))";
        return sql;

    }

    private static String createAirlineTable() {
        String sql = "CREATE TABLE AIRLINE " +
                "(ID INTEGER PRIMARY KEY    NOT NULL," +
                " NAME           VARCHAR(40)   NOT NULL," +
                " ALIAS           VARCHAR(40)," +   //alias can be null
                " IATA           CHAR(2)," +    //can have either IATA or ICAO
                " ICAO           CHAR(3)," +
                " CALLSIGN           VARCHAR(40)," +
                " COUNTRY           VARCHAR(40) NOT NULL," + //not null?
                " ACTIVE           CHAR(1) constraint check_active check (ACTIVE in ('Y', 'N')) )";
        return sql;
    }

    private static String createRouteTable() {
        //creates a route table for sqlite, routes includes links to both the airport and the equipment tables
        String sql = "CREATE TABLE ROUTE" +
                "(IDnum     INTEGER NOT NULL /*ID number for the route*/," +
                "Airline    VARCHAR(3) /*Airline iata for route*/," +  //this is either the IATA(2) or ICAO(3)
                "Airlineid  INTEGER /*ID of Airline for route*/," +
                "Src        VARCHAR(4) NOT NULL /*Source location for route*/," +   //either the IATA(3) or ICAO(4)
                "Srcid      INTEGER NOT NULL /*ID number for source location location*/," +
                "Dst        VARCHAR(4) NOT NULL /*Destination location for route*/," +   //either the IATA(3) or ICAO(4)
                "Dstid      INTEGER NOT NULL /*ID number for destination location*/," +
                "Codeshare  CHAR(1) constraint check_codeshare check (Codeshare in ('Y', '')) /*'Y' if operated by another carrier*/," +    //accept 'N'?
                "Stops      INTEGER NOT NULL /*Number of stops the route takes*/," +
                "Equipment  VARCHAR(50), " +
                "srcAirportName VARCHAR(100)," +
                "dstAirportName VARCHAR(100)," +
                "srcAirportCountry VARCHAR(100)," +
                "dstAirportCountry VARCHAR(100)," +
                "foreign key (Srcid) references AIRPORT," +
                "foreign key (Dstid) references AIRPORT" +    //foreign key can only be primary key of other table
                ")";
        //System.out.print(sql);
        return sql;
    }

    private static String createAirlineRouteTable(){

        String sql = "CREATE TABLE AIRLINEROUTES" +
                " (AID   INTEGER PRIMARY KEY NOT NULL /*ID number for airport*/," +
                " RID      INTEGER PRIMARY KEY NOT NULL /*ID number for route*/," +
                "foreign key (AID) references AIRPORT" +
                "foreign key (RID) references ROUTE" +
                ")";
        return sql;
    }

    private static String createFlightTable(){
        String sql = "CREATE TABLE FLIGHT" +
                "(FlightIDNum   INTEGER PRIMARY KEY /*incrementing number to identify flight*/," +
                "SrcICAO        VARCHAR(4) NOT NULL /*Source ICAO code*/," +   //either the IATA(3) or ICAO(4)
                "DstICAO        VARCHAR(4) NOT NULL /*Destination ICAO code*/" +       //either the IATA(3) or ICAO(4)
                ")";
        return sql;
    }

    private static String createFlightPointTable() {
        String sql = "CREATE TABLE FLIGHTPOINT" +
                "(SeqOrder         INTEGER NOT NULL UNIQUE /*gives the sequence of the flight points*/," +
                "LocaleID       VARCHAR(5) NOT NULL, " +
                "LocationType   CHAR(3) NOT NULL /*Type of location*/, " +
                "Altitude       INTEGER NOT NULL /*Altitudinal co-ordinates for flight point*/, " +
                "Latitude       FLOAT NOT NULL constraint check_lat check (LATITUDE between '-90' and '90') /*Latitudinal co-ordinates for flight point*/, " +
                "Longitude      FLOAT NOT NULL constraint check_long check (LONGITUDE between '-180' and '180') /*Longitudinal co-ordinates for flight point*/, " +
                "FlightIDNum       INTEGER NOT NULL /*comes from flight*/," +
                "PRIMARY KEY (FlightIDNum, SeqOrder)," +
                "FOREIGN KEY (FlightIDNum)" +
                "REFERENCES FLIGHT (FlightIDNum) ON DELETE CASCADE" +
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

            stmt = c.createStatement();
            String sql = createAirportTable();
            stmt.executeUpdate(sql);

            sql = createAirlineTable();
            stmt.executeUpdate(sql);

            sql = createRouteTable();
            stmt.executeUpdate(sql);

//            sql = createEquipmentTable();
//            stmt.executeUpdate(sql);

            sql = createFlightTable();
            stmt.executeUpdate(sql);

            sql = createFlightPointTable();
            stmt.executeUpdate(sql);


            stmt.close();
            c.close();
        } catch (Exception e) {
           // System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }


    //##########################Adding  Data#########################################//

    //Airline Adding
    public static void addAirlinePointstoDB(ArrayList<AirlinePoint> airlinePoints) {
        //This method adds multiple points to the Database
        try {

            Connection c = makeConnection();
            Statement stmt = null;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            stmt = c.createStatement();

            for (AirlinePoint airline : airlinePoints) {
                addSingleAirline(airline, stmt);
            }

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
           // System.err.println(e.getClass().getName() + ": " + e.getMessage());

            //System.exit(0);
        }
    }

    private static void addSingleAirline(AirlinePoint airline, Statement stmt) {
        int id = airline.getAirlineID();
        String name = airline.getAirlineName();
        String alias = airline.getAirlineAlias();
        String iata = airline.getIata();
        String icao = airline.getIcao();
        String callsign = airline.getCallsign();
        String country = airline.getCountry();
        String active = airline.getActive();


        String sql = "INSERT INTO AIRLINE (ID, NAME, ALIAS, IATA, ICAO, CALLSIGN, COUNTRY, ACTIVE) " +
                "VALUES (" +
                +id + ", \"" +
                name + "\", \"" +
                alias + "\", \"" +
                iata + "\", \"" +
                icao + "\", \"" +
                callsign + "\", \"" +
                country + "\", \"" +
                active + "\");";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
          //  System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    //Airport Adding
    public static void addAirportPointsToDB(ArrayList<AirportPoint> airportPoints) {
        //This method adds multiple points to the Database
        try {
            Connection c = makeConnection();
            Statement stmt = null;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            stmt = c.createStatement();

            for (AirportPoint airport : airportPoints) {
                addSingleAirport(airport, stmt);
            }

            stmt.close();
            c.commit();
            c.close();
        } catch (SQLException e) {  //error in database connection
          //  System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

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
        String tz = airport.getTz();

        String sql = "INSERT INTO AIRPORT (ID, NAME, CITY, COUNTRY, IATA, ICAO, LATITUDE, Longitude ,ALTITUDE, TIMEZONE, DST, TZ) " +
                "VALUES (" +
                + airportID + ", \"" +
                airportName + "\", \"" +
                City + "\", \"" +
                Country + "\", \"" +
                Iata + "\", \"" +
                Icao + "\", " +
                Latitude + ", " +
                Longitude + ", " +
                Altitude + ", " +
                timezone + ", \"" +
                Dst + "\", \"" +
                tz + "\"); ";
        //System.out.println(sql);

        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            //System.out.println("Poos" + sql);
          //  System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    //Route Adding
    public static void addRoutePointstoDB(ArrayList<RoutePoint> routePoints) {
        //adds routes into the database
        try {
            //Connect to DB
            Connection c = makeConnection();
            Statement stmt = null;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            stmt = c.createStatement();

            //Add all routes
            for (RoutePoint route : routePoints) {
                //make route in db
                addSingleRoutetoDB(route, stmt);
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
          //  System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }
    }

    private static void addSingleRoutetoDB(RoutePoint route, Statement stmt) {
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
        String equip = route.getEquipment();
        String srcAiportname = route.getSrcAirportName();
        String dstAiportName = route.getDestAirportName();
        String srcAirportCountry = route.getSrcAirportCountry();
        String dstAirportCountry = route.getDestAirportCountry();

        //make route sql text to execute
        String routeSql = "INSERT INTO ROUTE(IDnum, Airline, Airlineid, Src, Srcid, Dst, Dstid, Codeshare, Stops, Equipment, srcAirportName, dstAirportName, srcAirportCountry, dstAirportCountry)" +
                "VALUES (" +
                +IDnum + ", " +
                "\"" + Airline + "\", " +
                Airlineid + ", " +
                "\"" + src + "\", " +
                srcid + ", " +
                "\"" + dst + "\", " +
                dstid + ", " +
                "\"" + codeshare + "\", " +
                Stops + ", " + "\"" + equip + "\", \"" +
                srcAiportname + "\", \"" + dstAiportName + "\", \""  + srcAirportCountry + "\", \"" + dstAirportCountry + "\"" + ");";

        try {
            stmt.executeUpdate(routeSql);
        } catch (SQLException e) {
            //bad route data
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return;
        }

    }


    //Flight Adding
    //I AM NOT SURE IF BAD FLIGHT DATA IS BEING ADDED TO THE DATABASE CURRENTLY
    //BUT HAVE THROWN AN EXCEPTION TO STOP THE TABLE FROM UPDATING IF ANY EXCEPTION OCCURS.
    public static void addFlighttoDB(ArrayList<FlightPoint> flightPoints) throws SQLException {
        //Adding flight points into data base
        try {
            //Connect to DB
            Connection c = makeConnection();
            Statement stmt = null;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            stmt = c.createStatement();

            //first make a flight with start and end
            FlightPoint srcPoint = flightPoints.get(0);
            FlightPoint dstPoint = flightPoints.get(flightPoints.size() - 1);

            String srcICAO = srcPoint.getLocaleID();
            String dstICAO = dstPoint.getLocaleID();


            String sql = "INSERT INTO FLIGHT(SrcICAO, DstICAO) " +
                    "VALUES (" +
                    "\"" + srcICAO + "\", " +
                    "\"" + dstICAO + "\"" +
                    ");";

            stmt.executeUpdate(sql);

            //get the new flight id
            sql = "SELECT FlightIDNum, MAX(FlightIDNum) FROM FLIGHT";
            ResultSet rs = stmt.executeQuery(sql);
            int flightid = rs.getInt("FlightIDNum");

            //initialise order to show the sequence of the flight points
            int order = 1;

            //for all flight points
            for (FlightPoint point : flightPoints) {
                addSingleFlighttoDB(point, stmt, flightid, order);
                order++;

            }

            stmt.close();
            c.commit();
            c.close();

        } catch (SQLException e) {
           // System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw e;
        } catch (ClassNotFoundException e) {
           // System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private static void addSingleFlighttoDB(FlightPoint point, Statement stmt, int flightid, int order) throws SQLException {
        //get info for point

        String locID = point.getLocaleID();
        String locType = point.getType();
        int altitude = point.getAltitude();
        float latitude = point.getLatitude();
        float longitude = point.getLongitude();
        String FlightSql = "INSERT INTO FLIGHTPOINT(SeqOrder, LocaleID, LocationType, Altitude, Latitude, Longitude, FlightIDNum)" +
                "Values (" +
                order + ", " +
                "\"" + locID + "\", " +
                "\"" + locType + "\", " +
                altitude + ", " +
                latitude + ", " +
                longitude + ", " +
                flightid + ")";

        //execute route sql
        try {
            stmt.executeUpdate(FlightSql);
        } catch (SQLException e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw e;
        }

        //increment order for next point
    }

    public static void linkRoutesandAirports(ArrayList<AirportPoint> airports, ArrayList<RoutePoint> routes) {
        try {
            BBDatabase.dropRouteTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<RoutePoint> updatedPoints = new ArrayList<>();
        Set<RoutePoint> myRouteSet = new HashSet<>();
        for (RoutePoint route : routes) {
            //int operatingAirlineId = route.getAirlineID();	//should routes also link to airlines?
            int srcAirportId = route.getSrcAirportID();
            int destAirportId = route.getDstAirportID();
            for (AirportPoint airport : airports) {
                if (srcAirportId == airport.getAirportID()) {
                    route.setSrcAirportCountry(airport.getAirportCountry());
                    route.setSrcAirportName(airport.getAirportName());
                    airport.incrementIncRoutes();
//                    if (!updatedPoints.contains(route)){
//                        updatedPoints.add(route);
//                    }


                } if (destAirportId == airport.getAirportID()) {
                    route.setDestAirportCountry(airport.getAirportCountry());
                    route.setDestAirportName(airport.getAirportName());
                    airport.incrementOutgoingRoutes();
//                    if (!updatedPoints.contains(route)){
//                        updatedPoints.add(route);
//                    }
                }

            }
            //myRouteSet.add(route);
        }


        //ArrayList<RoutePoint> myList = (ArrayList<RoutePoint>) myRouteSet;
        ArrayList<String> test = new ArrayList<>();
        BBDatabase.addRoutePointstoDB(routes);
//        for (RoutePoint route: updatedPoints){
//            //System.out.println(route);
//           // BBDatabase.editDataEntry(route);
//            String sql2 = String.format("UPDATE ROUTE SET srcAirportName=\"%s\", " +
//                    "dstAirportName=\"%s\",  srcAirportCountry=\"%s\", dstAirportCountry=\"%s\" WHERE idnum=\"%s\"",
//                    route.getSrcAirport(), route.getSrcAirportID(), route.getSrcAirportID(), route.getDstAirport(), route.getRouteID());
//           //String sql = String.format("UPDATE ROUTE SET Airline='%1$s', Airlineid='%2$s', Src='%3$s', Srcid='%4$s'," +
//                           // " Dst='%5$s', Dstid='%6$s', Codeshare='%7$s', Stops='%8$s' WHERE IDnum='%9$s'",
//                    //route.getAirline(), route.getAirlineID(), route.getSource(), route.getSrcAirportID(), route.getDestination(), route.getDstAirportID(), route.getCodeshare(), 1000, route.getRouteID());
//
//            test.add(sql2);
//        }
//        BBDatabase.editDataEntries(test);
    }

    private static void dropRouteTable() throws SQLException {
        Connection c = makeConnection();

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);

            stmt = c.createStatement();
            //for (String sql: test){
              //  System.out.println(sql);
            stmt.executeUpdate("DROP TABLE ROUTE");

            String sql = createRouteTable();
            stmt.executeUpdate(sql);

            } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        c.commit();
        c.close();
        System.out.println("whoops i dropped a table");

    }


    //######################### FILTER METHODS#################################
    public static ArrayList<AirportPoint> performAirportsQuery(String sql) {
        Connection c = makeConnection();
        ArrayList<AirportPoint> allPoints = new ArrayList<AirportPoint>();

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {


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
                String tz = rs.getString("TZ");

                AirportPoint myPoint = new AirportPoint(airportID, airportName);
                myPoint.setAirportCity(City);
                myPoint.setAirportCountry(Country);
                myPoint.setIata(Iata);
                myPoint.setIcao(Icao);
                myPoint.setLatitude(Latitude);
                myPoint.setLongitude(Longitude);
                myPoint.setAltitude((int) Altitude);
                myPoint.setTimeZone(timezone);
                myPoint.setDst(Dst);
                myPoint.setTz(tz);
                allPoints.add(myPoint);


            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
           // System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }

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
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {


                int airportID = rs.getInt("ID");
                String airportName = rs.getString("NAME");
                String alias = rs.getString("alias");
                String Iata = rs.getString("IATA");
                String Icao = rs.getString("ICAO");
                String callsign = rs.getString("CALLSIGN");
                String country = rs.getString("COUNTRY");
                String active = rs.getString("Active");


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
        } catch (Exception e) {
          //  System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return allPoints;

    }


    public static ArrayList<RoutePoint> performRoutesQuery(String sql) {
        Connection c = makeConnection();
        ArrayList<RoutePoint> allPoints = new ArrayList<RoutePoint>();

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {


                int routeID = rs.getInt("IDnum");
                String airline = rs.getString("Airline");
                String airlineID = rs.getString("Airlineid");
                String src = rs.getString("Src");
                String srcID = rs.getString("Srcid");
                String dest = rs.getString("Dst");
                String destID = rs.getString("Dstid");
                String codeShare = rs.getString("Codeshare");
                String stops = rs.getString("Stops");
                String equip = rs.getString("Equipment");
                String srcAirportName = rs.getString("srcAirportName");
                String dstAirportName  = rs.getString("dstAirportName");
                String srcAirportCountry =  rs.getString("srcAirportCountry");
                String dstAirportCountry = rs.getString("dstAirportCountry");


                RoutePoint myPoint = new RoutePoint(airline, Integer.parseInt(airlineID));
                myPoint.setSrcAirport(src);
                myPoint.setSrcAirportID(Integer.parseInt(srcID));
                myPoint.setDstAirport(dest);
                myPoint.setDstAirportID(Integer.parseInt(destID));
                myPoint.setCodeshare(codeShare);
                myPoint.setStops(Integer.parseInt(stops));
                myPoint.setRouteID(routeID);
                myPoint.setEquipment(equip);
                myPoint.setSrcAirportName(srcAirportName);
                myPoint.setDestAirportName(dstAirportName);
                myPoint.setSrcAirportCountry(srcAirportCountry);
                myPoint.setDestAirportCountry(dstAirportCountry);

                allPoints.add(myPoint);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return allPoints;

    }


    public static ArrayList<Flight> performFlightsQuery(String sql) {
        Connection c = makeConnection();
        ArrayList<Flight> allPoints = new ArrayList<Flight>();

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {

                System.out.println("Were Searching yo");


                int flightID = rs.getInt("FlightIDNum");
                String departure = rs.getString("SrcICAO");
                String destination = rs.getString("DstICAO");

                System.out.println("A");
                List<FlightPoint> flightPoints = performFlightPointQuery(flightID);

                System.out.println("B");
                Flight myFlight = new Flight(flightPoints);
                System.out.println("C");
                myFlight.setFlightID(flightID);
                System.out.println("D");
                myFlight.setSrcAirport(departure);
                System.out.println("E");
                myFlight.setDestAirport(destination);
                System.out.println("F");

                allPoints.add(myFlight);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Well were fucked");
            System.exit(0);
        }
        System.out.println("Return YE");
        return allPoints;

    }

    public static List<FlightPoint> performFlightPointQuery(int flightID) {
        Connection c = makeConnection();
        List<FlightPoint> allPoints = null;

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "SELECT * FROM FLIGHTPOINT WHERE FlightIDNum='" + flightID + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {

                System.out.println("Were getting FlightPoints yo");


                int seqOrder = rs.getInt("SeqOrder");
                String localeID = rs.getString("LocaleID");
                String locationType = rs.getString("LocationType");
                int altitude = rs.getInt("Altitude");
                float latitude = rs.getFloat("Latitude");
                float longitude = rs.getFloat("Longitude");


                System.out.println("Creating flightpoint");

                FlightPoint myFlight = new FlightPoint(locationType, localeID, altitude, latitude, longitude);

                allPoints.add(myFlight);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Well were fucked");
            System.exit(0);
        }
        System.out.println("Return YE");
        return allPoints;

    }


    public static ArrayList<String> performDistinctStringQuery(String sql) {

        Connection c = makeConnection();
        ArrayList<AirlinePoint> allPoints = new ArrayList<AirlinePoint>();

        ArrayList<String> uniqueEntities = new ArrayList<>();

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {

                String myresult = rs.getString(1); //We can use 0 as it only returns 1 result
                //rs.get
                uniqueEntities.add(myresult);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
          //  System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return uniqueEntities;

    }

    public static void editDataEntry(String sql) {

        Connection c = makeConnection();

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            JPanel newpanel = new JPanel();
//            JOptionPane.showMessageDialog(newpanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            //message pop up here
        } catch (ClassNotFoundException e) {
            System.err.println("Error creating database");
            //System.exit(0);
        }

    }


    private static void editDataEntries(ArrayList<String> test) {

        Connection c = makeConnection();

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            int i = 0;
            stmt = c.createStatement();
            for (String sql: test){
                System.out.println(sql);
                stmt.executeUpdate(sql);

                if (i%10 ==0){
                    System.out.println("Thinking ..");
                }
                i++;
            }
            stmt.close();

            c.commit();
            c.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            JPanel newpanel = new JPanel();
//            JOptionPane.showMessageDialog(newpanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            //message pop up here
        } catch (ClassNotFoundException e) {
            System.err.println("Error creating database");
            //System.exit(0);
        }
    }

    public static void performTestQuery(String sql) {
        Connection c = makeConnection();
        ArrayList<RoutePoint> allPoints = new ArrayList<RoutePoint>();

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("-------------");
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println("-------------");
                //System.out.println(rs.getString(2)
                // );
//                int routeID = rs.getInt("IDnum");
//                String airline = rs.getString("Airline");
//                String airlineID = rs.getString("Airlineid");
//                String srcID = rs.getString("Srcid");
//                String dest = rs.getString("Dst");
//                String destID = rs.getString("Dstid");
//                String codeShare = rs.getString("Codeshare");
//                String stops = rs.getString("Stops");
//                String equip = rs.getString("Equipment");
//
//
//                RoutePoint myPoint = new RoutePoint(airline, Integer.parseInt(airlineID));
//                myPoint.setSrcAirport(src);
//                myPoint.setSrcAirportID(Integer.parseInt(srcID));
//                myPoint.setDstAirport(dest);
//                myPoint.setDstAirportID(Integer.parseInt(destID));
//                myPoint.setCodeshare(codeShare);
//                myPoint.setStops(Integer.parseInt(stops));
//                myPoint.setRouteID(routeID);
//                myPoint.setEquipment(equip);
//
//                allPoints.add(myPoint);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}

