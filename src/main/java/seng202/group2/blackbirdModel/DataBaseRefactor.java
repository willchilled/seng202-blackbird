package seng202.group2.blackbirdModel;

import com.sun.xml.internal.bind.v2.model.core.ID;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;


public class DataBaseRefactor {

    private static String dataBaseName="jdbc:sqlite:default.db";

    public static String getDatabaseName() {
        return dataBaseName;
    }

    public static void setDataBaseName(String dataBaseName) {
        DataBaseRefactor.dataBaseName = dataBaseName;
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

    public static void insertDataPoints(ArrayList<DataPoint> myPoints) {
        System.out.println("HI");

        try {

            Connection currentConnection = makeConnection();
            Statement stmt = null;
            Class.forName("org.sqlite.JDBC");
            currentConnection = DriverManager.getConnection(getDatabaseName());
            currentConnection.setAutoCommit(false);
            stmt = currentConnection.createStatement();
            PreparedStatement preparedStatement = null;



            for (DataPoint currentPoint : myPoints) {
                //addSingleAirline(airline, stmt);
                //System.out.println(currentPoint.getType());

                String dataType = currentPoint.getType();
                //DataTypes dataType = currentPoint.getType();
                //PreparedStatement preparedStatement = null;

                switch (dataType){
                    case "AirlinePoint": //System.out.println("Hey");
                            preparedStatement = prepareInsertAirlineSql(currentPoint, preparedStatement, currentConnection);
                            break;

                    case "AirportPoint":
                            preparedStatement = prepareInsertAirportSql(currentPoint, preparedStatement, currentConnection);
                            break;
                    case "RoutePoint":
                            preparedStatement = perpareInsertRouteSql(currentPoint, preparedStatement, currentConnection);
                            break;
                    case "Flight":
                        break;

                }

                try{
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                }
                catch (Exception e){
                   // System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                    System.out.println("Cant add: " +  currentPoint.toString());
                }


            }



                //.close();
            currentConnection.commit();
            currentConnection.commit();
            currentConnection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            //System.exit(0);
            System.out.println("Could not add :");
        }
        System.out.println("Records created successfully");




    }

    private static PreparedStatement perpareInsertRouteSql(DataPoint currentPoint, PreparedStatement preparedStatement, Connection currentConnection) {
        RoutePoint route = (RoutePoint) currentPoint;

        int idNum = route.getRouteID();
        String airline = route.getAirline();
        int airlineID = route.getAirlineID();
        String src = route.getSrcAirport();
        int srcId = route.getSrcAirportID();
        String dst = route.getDstAirport();
        int dstId = route.getDstAirportID();
        String codeshare = route.getCodeshare();
        int stops = route.getStops();

        //make route sql text to execute
        String sql = "INSERT INTO ROUTE(IDnum, Airline, Airlineid, Src, Srcid, Dst, Dstid, Codeshare, Stops) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            preparedStatement = currentConnection.prepareStatement(sql);
            //System.out.println(preparedStatement + "AAAAH");
           //preparedStatement.setInt(1, idNum);
            preparedStatement.setString(2, airline);
            preparedStatement.setInt(3, airlineID);
            preparedStatement.setString(4, src);
            preparedStatement.setInt(5, srcId);
            preparedStatement.setString(6, dst);
            preparedStatement.setInt(7, dstId);
            preparedStatement.setString(8, codeshare);
            preparedStatement.setInt(9, stops);
        } catch (SQLException e) {

            System.out.println("canont prepare statement");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return preparedStatement;

    }

    private static PreparedStatement prepareInsertAirportSql(DataPoint currentPoint, PreparedStatement preparedStatement, Connection currentConnection) {
        AirportPoint airport = (AirportPoint) currentPoint;


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

        String sql = "INSERT INTO AIRPORT (ID, NAME, CITY, COUNTRY, IATA, ICAO, LATITUDE, LONGITUDE, ALTITUDE, TIMEZONE, DST, TZ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";

        try {
            preparedStatement = currentConnection.prepareStatement(sql);
            //System.out.println(preparedStatement + "AAAAH");
            preparedStatement.setInt(1, airportID);
            // System.out.println(preparedStatement + "AAAAH");
            preparedStatement.setString(2, airportName);
            preparedStatement.setString(3, City);
            preparedStatement.setString(4, Country);
            preparedStatement.setString(5, Iata);
            preparedStatement.setString(6, Icao);
            preparedStatement.setFloat(7, Latitude);
            preparedStatement.setFloat(8, Longitude);
            preparedStatement.setFloat(9, Altitude);
            preparedStatement.setFloat(10, timezone);
            preparedStatement.setString(11, Dst);
            preparedStatement.setString(12, tz);
        } catch (SQLException e) {

            System.out.println("canont prepare statement");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return preparedStatement;

//        //System.out.println(airportID + airportName + City + Country + Iata + Icao + Latitude + Longitude + Altitude + timezone + Dst + tz);
//
//        String sql = "INSERT INTO AIRPORT (ID, NAME, CITY, COUNTRY, IATA, ICAO, LATITUDE, Longitude ,ALTITUDE, TIMEZONE, DST, TZ) " +
//                "VALUES (" +
//                + airportID + ", \"" +
//                airportName + "\", \"" +
//                City + "\", \"" +
//                Country + "\", \"" +
//                Iata + "\", \"" +
//                Icao + "\", " +
//                Latitude + ", " +
//                Longitude + ", " +
//                Altitude + ", " +
//                timezone + ", \"" +
//                Dst + "\", \"" +
//                tz + "\"); ";


    }

    private static PreparedStatement prepareInsertAirlineSql(DataPoint currentPoint, PreparedStatement preparedStatement, Connection currentConnection) {

        AirlinePoint airline = (AirlinePoint) currentPoint; //Casting to make more generic



        int id = airline.getAirlineID();
        String name = airline.getAirlineName();
        //System.out.println(name);
        String alias = airline.getAirlineAlias();
        String iata = airline.getIata();
        String icao = airline.getIcao();
        String callsign = airline.getCallsign();
        String country = airline.getCountry();
        String active = airline.getActive();
        //System.out.println(active);

        String sql = "INSERT INTO AIRLINE (ID, NAME, ALIAS, IATA, ICAO, CALLSIGN, COUNTRY, ACTIVE) VALUES  (?,?,?,?,?,?,?,?);";
        try {
            preparedStatement = currentConnection.prepareStatement(sql);
            //System.out.println(preparedStatement + "AAAAH");
            preparedStatement.setInt(1, id);
           // System.out.println(preparedStatement + "AAAAH");
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, alias);
            preparedStatement.setString(4, iata);
            preparedStatement.setString(5, icao);
            preparedStatement.setString(6, callsign);
            preparedStatement.setString(7, country);
            preparedStatement.setString(8, active);
        } catch (SQLException e) {
            System.out.println("canont prepare statement");
        }

        return preparedStatement;

    }

    private static void addAirlinePoint(DataPoint currentPoint) {



    }





























//
//
//    public static int getMaxInColumn(String tableName, String columnName){
//        //Returns the highest value in a column for a table
//        int highID = 0;
//        try {
//            //Connect to DB
//            Connection c = makeConnection();
//            Statement stmt = null;
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection(getDatabaseName());
//            c.setAutoCommit(false);
//            stmt = c.createStatement();
//
//            String sql = "SELECT " + columnName + ", MAX(" + columnName + ") FROM " + tableName;
//            ResultSet rs = stmt.executeQuery(sql);
//            highID = rs.getInt(columnName);
//            stmt.close();
//            c.close();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return highID;
//    }
//

//
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
                "(ID INTEGER PRIMARY KEY    NOT NULL," +
                " NAME           VARCHAR(40)   NOT NULL," +
                " CITY           VARCHAR(40)   NOT NULL," +
                " COUNTRY        VARCHAR(40)   NOT NULL," +
                " IATA           CHAR(3)," +    //database isn't happy with any duplicate values, including null. Note: can have either IATA or ICAO, perform check if it has at least one?
                " ICAO           CHAR(4)," +
                " LATITUDE       FLOAT constraint check_lat check (LATITUDE between '-90' and '90')," +
                " LONGITUDE      FLOAT constraint check_long check (LONGITUDE between '-180' and '180'),"+
                " ALTITUDE       FLOAT constraint check_alt check (ALTITUDE between '-1500' and '15000')," +
                " TIMEZONE       FLOAT constraint check_time check (TIMEZONE between '-12.00' and '14.00')," +
                " DST            CHAR(1) constraint check_dst check (DST in ('E', 'A', 'S', 'O', 'Z', 'N', 'U'))," +
                " TZ             VARCHAR(40))";
        //System.out.println(sql);
        return sql;

    }

    private static String createAirlineTable(){
        String sql = "CREATE TABLE AIRLINE " +
                "(ID INTEGER PRIMARY KEY    NOT NULL," +
                " NAME           VARCHAR(40)   NOT NULL," +
                " ALIAS           VARCHAR(40)," +   //alias can be null
                " IATA           CHAR(2)," +    //can have either IATA or ICAO
                " ICAO           CHAR(3)," +
                " CALLSIGN           VARCHAR(40)," +
                " COUNTRY           VARCHAR(40) NOT NULL," + //not null?
                " ACTIVE           CHAR(1) constraint check_active check (ACTIVE in ('Y', 'N')) )";
        //System.out.println(sql);
        return sql;
    }

    private static String createRouteTable(){
        //creates a route table for sqlite, routes includes links to both the airport and the equipment tables
        String sql = "CREATE TABLE ROUTE" +
                "(IDnum     INTEGER PRIMARY KEY AUTOINCREMENT /*ID number for the route*/," +
                "Airline    VARCHAR(3) /*Airline iata for route*/," +  //this is either the IATA(2) or ICAO(3)
                "Airlineid  INTEGER /*ID of Airline for route*/," +
                "Src        VARCHAR(4) NOT NULL /*Source location for route*/," +   //either the IATA(3) or ICAO(4)
                "Srcid      INTEGER NOT NULL /*ID number for source location location*/," +
                "Dst        VARCHAR(4) NOT NULL /*Destination location for route*/," +   //either the IATA(3) or ICAO(4)
                "Dstid      INTEGER NOT NULL /*ID number for destination location*/," +
                "Codeshare  CHAR(1) constraint check_codeshare check (Codeshare in ('Y', '')) /*'Y' if operated by another carrier*/," +    //accept 'N'?
                "Stops      INTEGER NOT NULL /*Number of stops the route takes*/," +
                "Equipment  VARCHAR(50), " +
                "foreign key (Srcid, Dstid) references AIRPORT" +    //foreign key can only be primary key of other table
                ")";
        return sql;
    }

    private static String createEquipmentTable(){
        //creates an equipment table for sqlite, is used to give routes the multivalued atribute equipment
        String sql = "CREATE TABLE EQUIPMENT" +
                "(IDnum          INTEGER NOT NULL /*Comes from route*/, " +
                "EquipmentName CHAR(3) NOT NULL," +     //can this be null?
                "PRIMARY KEY (EquipmentName, IDnum), "+
                "FOREIGN KEY (IDnum) REFERENCES ROUTE (IDnum)" +
                ")";
        return sql;
    }

    private static String createFlightTable(){
        String sql = "CREATE TABLE FLIGHT" +
                "(FlightIDNum   INTEGER PRIMARY KEY /*incrementing number to identify flight*/," +
                "SrcICAO        VARCHAR(4) NOT NULL /*Source ICAO code*/," +   //either the IATA(3) or ICAO(4)
                "DstICAO        VARCHAR(4) NOT NULL /*Destination ICAO code*/" +       //either the IATA(3) or ICAO(4)
                ")";
        //System.out.println(sql);
        return sql;
    }

    private static String createFlightPointTable(){
        String sql = "CREATE TABLE FLIGHTPOINT" +
                "(SeqOrder         INTEGER NOT NULL UNIQUE /*gives the sequence of the flight points*/," +
                "LocaleID       VARCHAR(5) NOT NULL, "+
                "LocationType   CHAR(3) NOT NULL /*Type of location*/, "+
                "Altitude       INTEGER NOT NULL /*Altitudinal co-ordinates for flight point*/, " +
                "Latitude       FLOAT NOT NULL constraint check_lat check (LATITUDE between '-90' and '90') /*Latitudinal co-ordinates for flight point*/, " +
                "Longitude      FLOAT NOT NULL constraint check_long check (LONGITUDE between '-180' and '180') /*Longitudinal co-ordinates for flight point*/, "+
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
        PreparedStatement preparedStatement = null;

        Connection c = makeConnection();
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            //System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = createAirportTable();

            preparedStatement = c.prepareStatement(sql);
            preparedStatement.executeUpdate();

            preparedStatement = c.prepareStatement(createAirlineTable());
            preparedStatement.executeUpdate();

            preparedStatement = c.prepareStatement(createRouteTable());
            preparedStatement.executeUpdate();

//            preparedStatement = c.prepareStatement(createEquipmentTable());
//            preparedStatement.executeUpdate();

            preparedStatement = c.prepareStatement(createFlightTable());
            preparedStatement.executeUpdate();

            preparedStatement = c.prepareStatement(createFlightPointTable());
            preparedStatement.executeUpdate();


            stmt.close();
            c.close();
        } catch (Exception e) {
            System.out.println("TABLES NOT CREATED SUCCESSFULLY");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        //System.out.println("AIPORT, AIRLINE, ROUTE, EQUIPMENT, FLIGHT Table created successfully");

    }

//
//    //Airport Adding
//    public static void addAirportPointsToDB(ArrayList<AirportPoint> airportPoints) {
//        //This method adds multiple points to the Database
//        try {
//            Connection c = makeConnection();
//            Statement stmt = null;
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection(getDatabaseName());
//            c.setAutoCommit(false);
//            //System.out.println("Opened database successfully");
//            stmt = c.createStatement();
//
//            for (AirportPoint airport : airportPoints) {
//                addSingleAirport(airport, stmt);
//            }
//
//            stmt.close();
//            c.commit();
//            c.close();
//        } catch (SQLException e) {  //error in database connection
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Records created successfully");
//    }
//
//    private static void addSingleAirport(AirportPoint airport, Statement stmt) {
//        int airportID = airport.getAirportID();
//        String airportName = airport.getAirportName();
//        String City = airport.getAirportCity();
//        String Country = airport.getAirportCountry();
//        String Iata = airport.getIata();
//        String Icao = airport.getIcao();
//        float Latitude = airport.getLatitude();
//        float Longitude = airport.getLongitude();
//        float Altitude = airport.getAltitude();
//        float timezone = airport.getTimeZone();
//        String Dst = airport.getDst();
//        String tz = airport.getTz();
//        //System.out.println(airportID + airportName + City + Country + Iata + Icao + Latitude + Longitude + Altitude + timezone + Dst + tz);
//
//        String sql = "INSERT INTO AIRPORT (ID, NAME, CITY, COUNTRY, IATA, ICAO, LATITUDE, Longitude ,ALTITUDE, TIMEZONE, DST, TZ) " +
//                "VALUES (" +
//                + airportID + ", \"" +
//                airportName + "\", \"" +
//                City + "\", \"" +
//                Country + "\", \"" +
//                Iata + "\", \"" +
//                Icao + "\", " +
//                Latitude + ", " +
//                Longitude + ", " +
//                Altitude + ", " +
//                timezone + ", \"" +
//                Dst + "\", \"" +
//                tz + "\"); ";
//
//        // System.out.println(sql);
//        try {
//            stmt.executeUpdate(sql);
//        } catch (SQLException e) {
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//            System.out.println("Could not add: " + airport);
//        }
//    }
//
//    //Route Adding
//    public static void addRoutePointstoDB(ArrayList<RoutePoint> routePoints) {
//        //adds routes into the database
//        try{
//            //Connect to DB
//            Connection c = makeConnection();
//            Statement stmt = null;
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection(getDatabaseName());
//            c.setAutoCommit(false);
//            stmt = c.createStatement();
//
//            //Add all routes
//            for (RoutePoint route : routePoints) {
//                //make route in db
//                addSingleRoutetoDB(route, stmt);
//            }
//            stmt.close();
//            c.commit();
//            c.close();
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//
//            //System.out.println("Could not add :");
//        }
//        System.out.println("Records created successfully");
//    }
//
//    private static void addSingleRoutetoDB(RoutePoint route, Statement stmt){
//        //get info for route
//        int IDnum = route.getRouteID();
//        String Airline = route.getAirline();
//        int Airlineid = route.getAirlineID();
//        String src = route.getSrcAirport();
//        int srcid = route.getSrcAirportID();
//        String dst = route.getDstAirport();
//        int dstid = route.getDstAirportID();
//        String codeshare = route.getCodeshare();
//        int Stops = route.getStops();
//
//        //make route sql text to execute
//        String routeSql = "INSERT INTO ROUTE(IDnum, Airline, Airlineid, Src, Srcid, Dst, Dstid, Codeshare, Stops)" +
//                "VALUES (" +
//                +IDnum + ", " +
//                "\"" + Airline + "\", " +
//                Airlineid + ", " +
//                "\"" + src + "\", " +
//                srcid + ", " +
//                "\"" + dst + "\", " +
//                dstid + ", " +
//                "\"" + codeshare + "\", " +
//                Stops + ");";
//
//
//        try {
//            stmt.executeUpdate(routeSql);
//        } catch (SQLException e) {
//            //bad route data
//            System.out.println("Could not add route: " + route);
//            return;
//        }
//
//
//
//        //equipment is special cos its a dick
//        String strEquipment = route.getEquipment();
//        String[] ListEquipment = strEquipment.split(" ");
//
//        //make equipment sql
//        //for all equipment in route
//        for (String equip : ListEquipment) {
//            //add equipment to equipment table
//            String EquipSql = "INSERT INTO EQUIPMENT (IDnum, EquipmentName)" +
//                    "VALUES(" +
//                    "" + IDnum + ", " +
//                    "\"" + equip + "\");";
//            try {
//                stmt.executeUpdate(EquipSql);
//            }catch  (SQLException e){
//                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//                System.out.println("Could not add equipment on: " + route);
//            }
//        }
//    }
//
//    //Flight Adding
//    //I AM NOT SURE IF BAD FLIGHT DATA IS BEING ADDED TO THE DATABASE CURRENTLY
//    //BUT HAVE THROWN AN EXCEPTION TO STOP THE TABLE FROM UPDATING IF ANY EXCEPTION OCCURS.
//    public static void addFlighttoDB(ArrayList<FlightPoint> flightPoints) throws SQLException {
//        //Adding flight points into data base
//        try {
//            //Connect to DB
//            Connection c = makeConnection();
//            Statement stmt = null;
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection(getDatabaseName());
//            c.setAutoCommit(false);
//            stmt = c.createStatement();
//
//            //first make a flight with start and end
//            FlightPoint srcPoint = flightPoints.get(0);
//            FlightPoint dstPoint = flightPoints.get(flightPoints.size() - 1);
//
//            String srcICAO = srcPoint.getLocaleID();
//            String dstICAO = dstPoint.getLocaleID();
//
//
//            String sql = "INSERT INTO FLIGHT(SrcICAO, DstICAO) " +
//                    "VALUES (" +
//                    "\"" + srcICAO + "\", " +
//                    "\"" + dstICAO + "\"" +
//                    ");";
//
//            stmt.executeUpdate(sql);
//
//            //get the new flight id
//            sql = "SELECT FlightIDNum, MAX(FlightIDNum) FROM FLIGHT";
//            ResultSet rs = stmt.executeQuery(sql);
//            int flightid = rs.getInt("FlightIDNum");
//
//            //initialise order to show the sequence of the flight points
//            int order = 1;
//
//            //for all flight points
//            for (FlightPoint point : flightPoints) {
//                addSingleFlighttoDB(point, stmt, flightid, order);
//                order++;
//
//            }
//
//            stmt.close();
//            c.commit();
//            c.close();
//
//        } catch (SQLException e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            throw e;
//            //System.out.println("Could not add :");
//        } catch (ClassNotFoundException e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//        }
//        System.out.println("Records created successfully");
//    }
//
//    private static void addSingleFlighttoDB(FlightPoint point, Statement stmt, int flightid, int order) throws SQLException {
//        //get info for point
//
//        String locID = point.getLocaleID();
//        String locType = point.getType();
//        int altitude = point.getAltitude();
//        float latitude = point.getLatitude();
//        float longitude = point.getLongitude();
//        System.out.println("not ded3");
//        String FlightSql = "INSERT INTO FLIGHTPOINT(SeqOrder, LocaleID, LocationType, Altitude, Latitude, Longitude, FlightIDNum)" +
//                "Values (" +
//                order + ", " +
//                "\"" + locID + "\", " +
//                "\"" + locType + "\", " +
//                altitude + ", " +
//                latitude + ", " +
//                longitude + ", " +
//                flightid + ")";
//
//        //execute route sql
//        try {
//            stmt.executeUpdate(FlightSql);
//        } catch (SQLException e) {
//            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            throw e;
//            //System.out.println("Could not add :");
//        }
//        System.out.println("Records created successfully");
//
//        //increment order for next point
//    }
//
//
//
//
//    //######################### FILTER METHODS#################################
//    public static ArrayList<AirportPoint> performAirportsQuery(String sql) {
//        Connection c = makeConnection();
//        ArrayList<AirportPoint> allPoints = new ArrayList<AirportPoint>();
//
//        Statement stmt = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection(getDatabaseName());
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");
//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//
//
//                int airportID = rs.getInt("ID");
//
//                String airportName = rs.getString("NAME");
//                String City = rs.getString("CITY");
//                String Country = rs.getString("COUNTRY");
//                String Iata = rs.getString("IATA");
//                String Icao = rs.getString("ICAO");
//                float Latitude = rs.getFloat("LATITUDE");
//                float Longitude = rs.getFloat("Longitude");
//                float Altitude = rs.getFloat("ALTITUDE");
//                float timezone = rs.getFloat("TIMEZONE");
//                String Dst = rs.getString("DST");
//                String tz = rs.getString("TZ");
//                // System.out.println(airportName);
//
//                AirportPoint myPoint = new AirportPoint(airportID, airportName);
//                myPoint.setAirportCity(City);
//                myPoint.setAirportCountry(Country);
//                myPoint.setIata(Iata);
//                myPoint.setIcao(Icao);
//                myPoint.setLatitude(Latitude);
//                myPoint.setLongitude(Longitude);
//                myPoint.setAltitude((int)Altitude);
//                myPoint.setTimeZone(timezone);
//                myPoint.setDst(Dst);
//                myPoint.setTz(tz);
//                allPoints.add(myPoint);
//
//
//            }
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
//        System.out.println("Operation done successfully");
//
//        return allPoints;
//    }
//
//    public static ArrayList<AirlinePoint> performAirlinesQuery(String sql) {
//        Connection c = makeConnection();
//        ArrayList<AirlinePoint> allPoints = new ArrayList<AirlinePoint>();
//
//        Statement stmt = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection(getDatabaseName());
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");
//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//
//
//                int airportID = rs.getInt("ID");
//                String airportName = rs.getString("NAME");
//                String alias = rs.getString("alias");
//                String Iata = rs.getString("IATA");
//                String Icao = rs.getString("ICAO");
//                String callsign = rs.getString("CALLSIGN");
//                String country = rs.getString("COUNTRY");
//                String active = rs.getString("Active");
//
//
//                // System.out.println(airportName);
//                AirlinePoint myPoint = new AirlinePoint(airportID, airportName);
//                myPoint.setAirlineName(airportName);
//                myPoint.setAirlineAlias(alias);
//                myPoint.setIata(Iata);
//                myPoint.setCallsign(callsign);
//                myPoint.setCountry(country);
//                myPoint.setActive(active);
//
//                allPoints.add(myPoint);
//            }
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
//
//        return allPoints;
//
//    }
//
//
//
//    public static ArrayList<RoutePoint> performRoutesQuery(String sql) {
//        Connection c = makeConnection();
//        ArrayList<RoutePoint> allPoints = new ArrayList<RoutePoint>();
//
//        Statement stmt = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection(getDatabaseName());
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");
//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery( sql );
//            while ( rs.next() ) {
//
//
//                int routeID = rs.getInt("IDnum");
//                String airline = rs.getString("Airline");
//                String airlineID = rs.getString("Airlineid");
//                String src = rs.getString("Src");
//                String srcID = rs.getString("Srcid");
//                String dest = rs.getString("Dst");
//                String destID = rs.getString("Dstid");
//                String codeShare = rs.getString("Codeshare");
//                String stops = rs.getString("Stops");
//
//
//                // System.out.println(airportName);
//                RoutePoint myPoint = new RoutePoint(airline, Integer.parseInt(airlineID));
//                myPoint.setSrcAirport(src);
//                myPoint.setSrcAirportID(Integer.parseInt(srcID));
//                myPoint.setDstAirport(dest);
//                myPoint.setDstAirportID(Integer.parseInt(destID));
//                myPoint.setCodeshare(codeShare);
//                myPoint.setStops(Integer.parseInt(stops));
//                myPoint.setRouteID(routeID);
//
//                allPoints.add(myPoint);
//            }
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch ( Exception e ) {
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//            System.exit(0);
//        }
//        System.out.println("Routes Query done successfully:" + sql);
//
//        return allPoints;
//
//    }
//
//
//    public static ArrayList<String> performDistinctStringQuery(String sql) {
//
//        Connection c = makeConnection();
//        ArrayList<AirlinePoint> allPoints = new ArrayList<AirlinePoint>();
//
//        ArrayList<String> uniqueEntities = new ArrayList<>();
//
//        Statement stmt = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection(getDatabaseName());
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");
//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//
//                String myresult = rs.getString(1); //We can use 0 as it only returns 1 result
//                //rs.get
//                //System.out.println(myresult);
//                uniqueEntities.add(myresult);
//            }
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
//        System.out.println("Disting Query Query done successfully:" + sql);
//
//        return uniqueEntities;
//
//    }
//
//    public static void editAirlineDataEntry(String sql) {
//
//        Connection c = makeConnection();
//
//        Statement stmt = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection(getDatabaseName());
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");
//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery( sql );
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch ( Exception e ) {
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//            System.exit(0);
//        }
//        System.out.println("Disting Query Query done successfully:" + sql);
//
//    }
//
//    public static ArrayList<RoutePoint> performJoinRoutesEquipQuery(String sql) {
//
//        Connection c = makeConnection();
//        ArrayList<RoutePoint> allPoints = new ArrayList<RoutePoint>();
//        String subQuery;
//
//        Statement stmt = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection(getDatabaseName());
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");
//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery( sql );
//            while ( rs.next() ) {
//
//                //System.out.println(rs.getInt(1) + "NOODLES");
//                int routeID = rs.getInt("IDnum");
//                String airline = rs.getString("Airline");
//                String airlineID = rs.getString("Airlineid");
//                String src = rs.getString("Src");
//                String srcID = rs.getString("Srcid");
//                String dest = rs.getString("Dst");
//                String destID = rs.getString("Dstid");
//                String codeShare = rs.getString("Codeshare");
//                String stops = rs.getString("Stops");
//
//                int equipID = rs.getInt("Idnum");
//                String equipName = rs.getString("equipmentName");
//
//                //System.out.println(equipID + equipName);
//                subQuery = String.format("SELECT EQUIPMENT.Equipmentname FROM EQUIPMENT WHERE equipment.IDnum=\"%s\"", equipID);
//                ArrayList<String> equipArray = performDistinctStringQuery(subQuery);
//                equipName = joinEquipArray(equipArray);
//                //System.out.println(equipName);
//
//
//
//                // System.out.println(airportName);
//                RoutePoint myPoint = new RoutePoint(airline, Integer.parseInt(airlineID));
//                myPoint.setSrcAirport(src);
//                myPoint.setSrcAirportID(Integer.parseInt(srcID));
//                myPoint.setDstAirport(dest);
//                myPoint.setDstAirportID(Integer.parseInt(destID));
//                myPoint.setCodeshare(codeShare);
//                myPoint.setStops(Integer.parseInt(stops));
//                myPoint.setRouteID(routeID);
//                myPoint.setEquipment(equipName);
//
//                //System.out.println(myPoint.getRouteID() + " - " + myPoint.getEquipment());
//                if (!allPoints.contains(myPoint)) {
//                    allPoints.add(myPoint);
//                }
//            }
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch ( Exception e ) {
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//            System.exit(0);
//        }
//        System.out.println("Routes Query done successfully:" + sql);
//
//        return allPoints;
//
//    }
//
//    private static String joinEquipArray(ArrayList<String> equipArray) {
//        //This function adds all the strings in the equipment array seperating them by spaces
//        String myEquipment = "";
//        for (int i=0; i<equipArray.size()-1; i++){
//            myEquipment += equipArray.get(i) + " ";
//        }
//        myEquipment += equipArray.get(equipArray.size()-1);
//        return myEquipment;
//    }
}

