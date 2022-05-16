package dimka.blinb.collection.utilities;

import dimka.blinb.collection.exception.NameIsEmpty;
import dimka.blinb.collection.exception.OutOfRange;
import dimka.blinb.collection.objects.Coordinates;
import dimka.blinb.collection.objects.Location;
import dimka.blinb.collection.objects.Route;

import java.sql.*;

public class ORM_API {
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "postgres";
    private static final String password = "admin";
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement preparedStatement;
    private static ResultSet rs;

    public static Boolean ConnectionToDB() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            /**
            // Initialize Table
            stmt = connection.createStatement();
            stmt.execute("CREATE TABLE routes(" +
                    "NAME   TEXT        NOT NULL," +
                    "ID     INT         NOT NULL," +
                    "X1     INT         NOT NULL,       Y1 FLOAT NOT NULL," +
                    "CREATION_DATE DATE NOT NULL," +
                    "NAME_FROM  TEXT,   X2 FLOAT,       Y2 INT," +
                    "NAME_TO    TEXT    NOT NULL,       X3 FLOAT NOT NULL, Y3 INT NOT NULL," +
                    "DISTANCE   FLOAT   NOT NULL," +
                    "LOGIN      TEXT    NOT NULL) ");
            stmt.execute("CREATE TABLE users(" +
                    "LOGIN      TEXT    NOT NULL ," +
                    "PASSWORD   TEXT    NOT NULL)");
             **/
            uploadCollection();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Boolean addNewUser(String user, String password) {
        try {
            preparedStatement = connection.prepareStatement("insert into users values (?,?)");
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static Boolean userExist(String user, String password) {

        try {
            preparedStatement = connection.prepareStatement(" select * from users where login = ? and password= ?");
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void uploadCollection() {
        // загрузить в БД коллекцию
        try {
            preparedStatement = connection.prepareStatement("INSERT into routes values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            Collection.LHM.entrySet().stream().filter(x -> {
                try {
                    return !isContain(x.getKey());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return false;
            }).forEach(x -> {
                try {
                    preparedStatement.setString(1, x.getValue().getName());
                    preparedStatement.setInt(2, x.getValue().getID());
                    preparedStatement.setLong(3, x.getValue().getCoordinates().getX());
                    preparedStatement.setFloat(4, x.getValue().getCoordinates().getY());
                    preparedStatement.setTimestamp(5, Timestamp.valueOf(x.getValue().getCreationDate()));
                    // Location FROM
                    preparedStatement.setString(6, x.getValue().getFrom().getName());
                    preparedStatement.setFloat(7, x.getValue().getFrom().getX());
                    preparedStatement.setLong(8, x.getValue().getFrom().getY());
                    // Location TO
                    preparedStatement.setString(9, x.getValue().getTo().getName());
                    preparedStatement.setFloat(10, x.getValue().getTo().getX());
                    preparedStatement.setLong(11, x.getValue().getTo().getY());
                    // Distance
                    preparedStatement.setFloat(12, x.getValue().getDistance());
                    preparedStatement.setString(13, x.getValue().getLogin());

                    preparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadCollection() {
        // загрузить из БД коллекцию
        try {
            try {
                Collection.LHM.clear();
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select * from routes");
            while (rs.next()) {
                Route r = new Route();
                r.setName(rs.getString(1));
                r.setID(rs.getInt(2));
                r.setCoordinates(new Coordinates(
                        rs.getLong(3),
                        rs.getFloat(4)));
                r.setCreationDate(rs.getTimestamp(5));
                r.setFrom(new Location(
                        rs.getString(6),
                        rs.getFloat(7),
                        rs.getLong(8)));
                r.setTo(new Location(
                        rs.getString(9),
                        rs.getFloat(10),
                        rs.getLong(11)));
                r.setDistance(rs.getFloat(12));
                r.setLogin(rs.getString(13));
            }

        } catch (SQLException | NameIsEmpty | OutOfRange e) {
            e.printStackTrace();
        }

    }

    public static Boolean isContain(Integer element_id) throws SQLException {
        stmt = connection.createStatement();
        rs = stmt.executeQuery("select id from routes where id=" + String.valueOf(element_id));
        if(rs.next())
            return true;
        return false;
    }

    public static Integer getNewId() {
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT nextval('ID')");
            if (rs.next()) {
                return rs.getInt(1);
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
