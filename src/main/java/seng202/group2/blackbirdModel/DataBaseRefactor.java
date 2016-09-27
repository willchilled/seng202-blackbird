

package seng202.group2.blackbirdModel;

import javafx.scene.control.Alert;
import seng202.group2.blackbirdControl.ErrorTabController;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * This class acts as an interface between our program and the database. It includes methods for preparing
 * the sql statements and performing these to update the database.
 */
public class DataBaseRefactor {

    private static int FlightCount = 0;
    private static int flightPointCount = 0;
    private static String dataBaseName = "jdbc:sqlite:default.db";


    /**
     * @return Returns the current database name.
     */
    public static String getDatabaseName() {
        return dataBaseName;
    }

    /**
     * @param dataBaseName Sets the current database name.
     */
    public static void setDataBaseName(String dataBaseName) {
        DataBaseRefactor.dataBaseName = dataBaseName;
    }

    /**
     * Creates a connection to the database.
     * @return The connection to the database.
     */
    private static Connection makeConnection(){
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

    /**
     * Inserts DataPoints into the database, by first preparing the specific sql statements with the help of other
     * database methods.
     *
     * @param myPoints An arraylist of DataPoints that we want to insert.
     */
    public static void insertDataPoints(ArrayList<DataPoint> myPoints, ErrorTabController errorTabController) {
        boolean allCorrect = true;
        try {
            Connection currentConnection = makeConnection();
            Class.forName("org.sqlite.JDBC");
            currentConnection = DriverManager.getConnection(getDatabaseName());
            currentConnection.setAutoCommit(false);
            PreparedStatement preparedStatement = null;

            if (myPoints.get(0).getType().equals(DataTypes.FLIGHTPOINT)) {
                FlightCount++;
                addFlight(myPoints, preparedStatement, currentConnection);
                //System.out.println("HERE");
                //System.out.println(FlightCount);
            }

            flightPointCount = 0;

            for (DataPoint currentPoint : myPoints) {
                //addSingleAirline(airline, stmt);
//                System.out.println(currentPoint.getType());
//                System.out.println(myPoints.toString());

                DataTypes dataType = currentPoint.getType();
                //DataTypes dataType = currentPoint.getType();
                //PreparedStatement preparedStatement = null;

                switch (dataType){
                    case AIRLINEPOINT:
                        preparedStatement = prepareInsertAirlineSql(currentPoint, preparedStatement, currentConnection);
                        break;

                    case AIRPORTPOINT:
                        preparedStatement = prepareInsertAirportSql(currentPoint, preparedStatement, currentConnection);
                        break;
                    case ROUTEPOINT:
                        preparedStatement = prepareInsertRouteSql(currentPoint, preparedStatement, currentConnection);
                        break;
                    case FLIGHTPOINT:
                        //This behaves differently because the data is ArrayList<DataPoint<FlightPoints>>
                        //FlightPoint myFlight = (FlightPoint) myPoints.get(0);
                        preparedStatement = prepareInsertFlightPointStatement(currentPoint, preparedStatement, currentConnection);
                        flightPointCount++;
                        break;
                }

                try{
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                } catch (SQLException e){   //failed to put datapoint into database
                    //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                    BadData badPoint = new BadData(currentPoint.getFileLine(), currentPoint.toString(), currentPoint.getType());
                    if (errorTabController != null) {
                        errorTabController.updateBadEntries(badPoint, currentPoint.getType());
                    }
                    allCorrect = false;

                }
            }
            //.close();
            currentConnection.commit();
            currentConnection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
            System.out.println("Some error occurred when making connection? :");
        }

        if (!allCorrect) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Some file entries could not be added");
            alert.setContentText("See the Errors tab for more details");

            alert.show();
        }
        //System.out.println("Records created successfully");
    }

    /**
     * Prepares a sql statement for inserting a flightpoint.
     * @param currentPoint The flightpoint that we want to add to the database.
     * @param preparedStatement The current sql statement
     * @param currentConnection The current connection to the database.
     * @return The prepared sql statement for a flightpoint
     */
    private static PreparedStatement prepareInsertFlightPointStatement(DataPoint currentPoint, PreparedStatement preparedStatement, Connection currentConnection) {
        FlightPoint flightPoint = (FlightPoint) currentPoint;
        String sql = "INSERT INTO FLIGHTPOINT(SeqOrder, LocaleID, LocationType, Altitude, Latitude, Longitude, FlightIDNum) VALUES (?,?,?,?,?,?,?);";
        try {
            preparedStatement = currentConnection.prepareStatement(sql);
            preparedStatement.setInt(1, flightPointCount);
            preparedStatement.setString(2, flightPoint.getLocaleID());
            preparedStatement.setString(3, flightPoint.getLocalType());
            preparedStatement.setFloat(4, flightPoint.getAltitude());
            preparedStatement.setFloat(5, flightPoint.getLatitude());
            preparedStatement.setFloat(6, flightPoint.getLongitude());
            preparedStatement.setInt(7, FlightCount);
            // System.out.println(FlightCount);

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.out.println("DataBaseRefactor.prepareInsertFlightPointStatement failed");
            //e.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Helps to create a new flight entry, as flights are made up of their flightpoints, each flight needs to
     * also be added in to another flight table.
     * @param myPoints The arraylist of DataPoints
     * @param preparedStatement The current sql statement
     * @param currentConnection The current connection to the database
     */
    private static void addFlight(ArrayList<DataPoint> myPoints, PreparedStatement preparedStatement, Connection currentConnection) {
        //System.out.println(myPoints.size());
        FlightPoint sourcePoint = (FlightPoint) myPoints.get(0);
        FlightPoint destPoint = (FlightPoint) myPoints.get(myPoints.size()-1);

        String flightSource  = sourcePoint.getLocaleID();
        String destSource = destPoint.getLocaleID();
        //System.out.println(flightSource + "==" +  destSource);
        preparedStatement = prepareInsertFlightStatement(flightSource, destSource, preparedStatement, currentConnection);

        try{
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (Exception e){
            //System.out.println("Cant add " +  myPoints.toString());
            System.out.println("DataBaseRefactor.addFlight failed");
        }
    }

    /**
     * Helper function to prepare a sql statement to insert a flight
     * @param flightSource The source of the flight
     * @param destSource The destination of the flight
     * @param preparedStatement The current sql statement
     * @param currentConnection The current connection to the database
     * @return The prepared sql statement for a flight
     */
    private static PreparedStatement prepareInsertFlightStatement(String flightSource, String destSource, PreparedStatement preparedStatement, Connection currentConnection) {
        String sql = "INSERT INTO FLIGHT(SrcICAO, DstICAO) VALUES (?,?);";
        //System.out.println(sql);
        try {
            preparedStatement = currentConnection.prepareStatement(sql);
            preparedStatement.setString(1, flightSource);
            preparedStatement.setString(2, destSource);

        } catch (SQLException e) {
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            //System.out.println("Could no print ");
            //e.printStackTrace();
            System.out.println("DataBaseRefactor.prepareInsertFlightStatement failed");
        }
        return preparedStatement;
    }

    /**
     * Prepares a route sql statement to insert a route into the database.
     * @param currentPoint The route that we are wanting to insert
     * @param preparedStatement The current sql statement
     * @param currentConnection The current connection to the database
     * @return The prepared sql statement to insert a route into the database.
     */
    private static PreparedStatement prepareInsertRouteSql(DataPoint currentPoint, PreparedStatement preparedStatement, Connection currentConnection) {
        RoutePoint route = (RoutePoint) currentPoint;

        //int idNum = route.getRouteID();
        String airline = route.getAirline();
        int airlineID = route.getAirlineID();
        String src = route.getSrcAirport();
        int srcId = route.getSrcAirportID();
        String dst = route.getDstAirport();
        int dstId = route.getDstAirportID();
        String codeshare = route.getCodeshare();
        int stops = route.getStops();
        String equp = route.getEquipment();

        //make route sql text to execute
        String sql = "INSERT INTO ROUTE(IDnum, Airline, Airlineid, Src, Srcid, Dst, Dstid, Codeshare, Stops, Equipment) VALUES (?,?,?,?,?,?,?,?,?,?)";
        //System.out.println(sql + "I GET TO HERE") ;
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
            preparedStatement.setString(10, equp);
        } catch (SQLException e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("DataBaseRefactor.prepareInsertRouteSql failed");
        }

        return preparedStatement;
    }

    /**
     * Prepares an airport sql statement to insert an airport into the database
     * @param currentPoint The airport point that we are wanting to insert
     * @param preparedStatement The current sql statement
     * @param currentConnection The current connection to the database
     * @return The prepared sql statement to insert an airport into the database.
     */
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
            preparedStatement.setInt(1, airportID);
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
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("DataBaseRefactor.prepareInsertAirportSql failed");
        }

        return preparedStatement;
    }

    /**
     * Prepares an airline sql statement to insert an airline into the database
     * @param currentPoint The airline point that we are wanting to insert
     * @param preparedStatement The current sql statement
     * @param currentConnection The current connection to the database
     * @return The prepared sql statement to insert an airline into the database.
     */
    private static PreparedStatement prepareInsertAirlineSql(DataPoint currentPoint, PreparedStatement preparedStatement, Connection currentConnection) {
        AirlinePoint airline = (AirlinePoint) currentPoint; //Casting to make more specific
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
            System.out.println("DataBaseRefactor.prepareInsertAirlineSql failed");
        }
        return preparedStatement;
    }

    public static void editDataEntry(String sql){

        Connection c = makeConnection();

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getDatabaseName());
            c.setAutoCommit(false);
            stmt = c.createStatement();
            stmt.executeUpdate( sql );

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Performing edit:" + sql);

    }

    /**
     * Performs a generic query to the database e.g. for filtering and searching. Calls the static method of
     * Datapoint to recreate it from the database response.
     * @param sql The sql string to execute
     * @param dataType The current data type that we are working with
     * @return The arraylist of DataPoints returned from the database query.
     * @see DataPoint
     */
    public static ArrayList<DataPoint> performGenericQuery(String sql, DataTypes dataType) {

        ArrayList<DataPoint> resultPoints = new ArrayList<>();

        try {
            Connection currentConnection = makeConnection();
            Class.forName("org.sqlite.JDBC");
            currentConnection = DriverManager.getConnection(getDatabaseName());
            currentConnection.setAutoCommit(false);
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            String[] attributes = null;

            while(rs.next()) {

                int width = rsmd.getColumnCount();

//                if(dataType == DataTypes.ROUTEPOINT) {
//                    //System.out.println("Now here");
//                    attributes = new String[width-1];
//                    for (int i = 1; i < width; i++) {
//
//                        attributes[i-1] = rs.getString(i + 1);
//                        //System.out.println(i + " " + attributes[i-1]);
//                    }
//
//                } else {
                    attributes = new String[width];
                    for (int i = 0; i < width; i++) {

                        attributes[i] = rs.getString(i + 1);
                         //System.out.println(i + " " + attributes[i]);
//                    }
                }
                DataPoint myPoint = DataPoint.createDataPointFromStringArray(attributes, dataType, 0, null);
                resultPoints.add(myPoint);
                // System.out.println(myPoint.toString());
            }
            preparedStatement.close();
            currentConnection.close();
            rs.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("DataBaseRefactor.performGenericQuery query failed");
            e.printStackTrace();
        }

        return resultPoints;
    }

    /**
     * Helper function to find distinct strings of a specified column with the table. Used to populate
     * the filter dropdown menus.
     * @param sql The sql string to be executed
     * @return The arraylist of distinct strings
     */
    public static ArrayList<String> performDistinctQuery(String sql){

        ArrayList<String> distinctResults = new ArrayList<>();

        try {
            Connection currentConnection = makeConnection();
            Class.forName("org.sqlite.JDBC");
            currentConnection = DriverManager.getConnection(getDatabaseName());
            currentConnection.setAutoCommit(false);
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int i = 0;

            while(rs.next()) {
                distinctResults.add(rs.getString(1));
            }

            preparedStatement.close();
            currentConnection.commit();
            currentConnection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("DataBaseRefactor.performDistinctQuery query failed");
            e.printStackTrace();
        }

        return distinctResults;
    }

    /**
     * Grabs the Max value of an attribute in a column. Namely for finding the max ID of an entity in a database for the
     * purpose of incrementing when adding a new value. Note this only works for integer values.
     * @param tableName The table from which to grab the max value
     * @param columnName The attribute of which you want the max value
     * @return Returns an int of the max value
     */
    public static int getMaxInColumn(String tableName, String columnName){
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


    /**
     * Deletes an existing database.
     */
    public static void deleteDBFile(){
        //deletes pre existing database file
        String cwd = System.getProperty("user.dir");

        File f = new File(cwd+"/default.db");

        if(f.exists() && f.isFile()){
            f.delete();
        }
    }

    //#######################MAKING TABLES//

    /**
     * Prepares the sql string to create a new airport table within the database.
     * @return The sql string to create a new airport table within the database.
     */
    private static String createAirportTable(){
        //added additional constraints on some of the fields, but i think adding some on parsing as well may simplify things?
        String sql = "CREATE TABLE AIRPORT " +
                "(ID INTEGER PRIMARY KEY    NOT NULL," +
                " NAME           VARCHAR(40)   NOT NULL," +
                " CITY           VARCHAR(40)," +
                " COUNTRY        VARCHAR(40)   NOT NULL," +
                " IATA           CHAR(3)," +    //database isn't happy with any duplicate values, including null. Note: can have either IATA or ICAO, perform check if it has at least one?
                " ICAO           CHAR(4)," +
                " LATITUDE       FLOAT constraint check_lat check (LATITUDE between '-90' and '90')," +
                " LONGITUDE      FLOAT constraint check_long check (LONGITUDE between '-180' and '180'),"+
                " ALTITUDE       FLOAT constraint check_alt check (ALTITUDE between '-1500' and '15000')," +
                " TIMEZONE       FLOAT constraint check_time check (TIMEZONE between '-12.00' and '14.00')," +
                " DST            CHAR(1) constraint check_dst check (DST in ('E', 'A', 'S', 'O', 'Z', 'N', 'U', ''))," +
                " TZ             VARCHAR(40))";
        //System.out.println(sql);
        return sql;

    }

    /**
     * Prepares the sql string to create a new airline table within the database.
     * @return The sql string to create a new airline table within the database.
     */
    private static String createAirlineTable(){
        String sql = "CREATE TABLE AIRLINE " +
                "(ID INTEGER PRIMARY KEY    NOT NULL constraint check_id check (ID > 0)," +
                " NAME           VARCHAR(40)   NOT NULL," +
                " ALIAS           VARCHAR(40)," +   //alias can be null
                " IATA           CHAR(2)," +    //can have either IATA or ICAO
                " ICAO           CHAR(3)," +
                " CALLSIGN           VARCHAR(40)," +
                " COUNTRY           VARCHAR(40)," + //not null?
                " ACTIVE           CHAR(1) constraint check_active check (ACTIVE in ('Y', 'N')) )";
        //System.out.println(sql);
        return sql;
    }

    /**
     * Prepares the sql string to create a new route table within the database.
     * @return The sql string to create a new route table within the database.
     */
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
                "Stops      INTEGER constraint check_stops check (Stops >= 0) /*Number of stops the route takes*/," +
                "Equipment  VARCHAR(50), " +
                "foreign key (Srcid) references AIRPORT," +
                "foreign key (Dstid) references AIRPORT" +    //foreign key can only be primary key of other table
                ")";
        return sql;
    }

//    private static String createEquipmentTable(){
//        //creates an equipment table for sqlite, is used to give routes the multivalued atribute equipment
//        String sql = "CREATE TABLE EQUIPMENT" +
//                "(IDnum          INTEGER NOT NULL /*Comes from route*/, " +
//                "EquipmentName CHAR(3) NOT NULL," +     //can this be null?
//                "PRIMARY KEY (EquipmentName, IDnum), "+
//                "FOREIGN KEY (IDnum) REFERENCES ROUTE (IDnum)" +
//                ")";
//        return sql;
//    }

    /**
     * Prepares the sql string to create a new flight table within the database.
     * @return The sql string to create a new flight table within the database.
     */
    private static String createFlightTable(){
        String sql = "CREATE TABLE FLIGHT" +
                "(FlightIDNum   INTEGER PRIMARY KEY AUTOINCREMENT/*incrementing number to identify flight*/," +
                "SrcICAO        VARCHAR(4) NOT NULL /*Source ICAO code*/," +   //either the IATA(3) or ICAO(4)
                "DstICAO        VARCHAR(4) NOT NULL /*Destination ICAO code*/" +       //either the IATA(3) or ICAO(4)
                ")";
        //System.out.println(sql);
        return sql;
    }

    /**
     * Prepares the sql string to create a new flightpoint table within the database.
     * @return The sql string to create a new flightpoint table within the database.
     */
    private static String createFlightPointTable(){
        String sql = "CREATE TABLE FLIGHTPOINT" +
                "(SeqOrder         INTEGER NOT NULL /*gives the sequence of the flight points*/," +
                "LocaleID       VARCHAR(5) NOT NULL, "+
                "LocationType   CHAR(3) NOT NULL /*Type of location*/, "+
                "Altitude       INTEGER NOT NULL /*Altitudinal co-ordinates for flight point*/ constraint check_flightalt check (Altitude between -1500 and 100000), " +
                "Latitude       FLOAT NOT NULL constraint check_flightlat check (LATITUDE between '-90' and '90') /*Latitudinal co-ordinates for flight point*/, " +
                "Longitude      FLOAT NOT NULL constraint check_flightlong check (LONGITUDE between '-180' and '180') /*Longitudinal co-ordinates for flight point*/, "+
                "FlightIDNum       INTEGER NOT NULL /*comes from flight*/," +
                "PRIMARY KEY (FlightIDNum, SeqOrder)," +
                "FOREIGN KEY (FlightIDNum)" +
                "REFERENCES FLIGHT (FlightIDNum) ON DELETE CASCADE" +
                ")";
        return sql;
    }

    /**
     * Creates one table for each type of datapoint in the database, in order to store data.
     */
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

    /**
     * Drops a table, then re-creates it, in order to clear a single table
     * @param type the enum DataType of the table to be dropped
     * */
    public static void clearTable(DataTypes type){
        try {
            System.out.println("In the drop table method");
            Connection currentConnection = makeConnection();
            Class.forName("org.sqlite.JDBC");
            currentConnection = DriverManager.getConnection(getDatabaseName());
            currentConnection.setAutoCommit(false);
            PreparedStatement preparedStatement = null;
            PreparedStatement preparedStatement2 = null;
            String sqlDrop = "";
            String sqlCreate = "";

            switch(type){
                case AIRLINEPOINT:
                    sqlDrop = "DROP TABLE AIRLINE";
                    sqlCreate = createAirlineTable();
                    break;
                case AIRPORTPOINT:
                    sqlDrop = "DROP TABLE AIRPORT";
                    sqlCreate = createAirportTable();
                    break;
                case ROUTEPOINT:
                    sqlDrop = "DROP TABLE ROUTE";
                    sqlCreate = createRouteTable();
                    break;
                case FLIGHTPOINT:
                    sqlDrop = "DROP TABLE FLIGHTPOINT";
                    sqlCreate = createFlightPointTable();
                    break;
                case FLIGHT:
                    sqlDrop = "DROP TABLE  FLIGHT";
                    sqlCreate = createFlightTable();
            }
                try{
                    preparedStatement = currentConnection.prepareStatement(sqlDrop);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();

                    preparedStatement = currentConnection.prepareStatement(sqlCreate);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();

                }catch (Exception e){
                    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                    System.out.println("Prepared statement failed");
                }
            currentConnection.commit();
            currentConnection.close();
        } catch (Exception e) {
            System.out.println("Some error occurred when making connection? :");
            e.printStackTrace();
        }
    }

    /**
     * A method to check if the database has a column in  a table
     */
    public static boolean checkDBForColumn(String tableName, String columnName) {
        boolean exists = false;
        try {
            Connection currentConnection = makeConnection();
            Class.forName("org.sqlite.JDBC");
            currentConnection = DriverManager.getConnection(getDatabaseName());
            currentConnection.setAutoCommit(false);

            DatabaseMetaData md = currentConnection.getMetaData();
            ResultSet rs = md.getColumns(null, null, tableName, columnName);
            if (rs.next()) {
                exists = true;
            }
            currentConnection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

}

