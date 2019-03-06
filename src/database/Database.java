package serverclient;

import java.sql.*;

public class Database {

    private static String url = "jdbc:postgresql://mod-fund-databases.cs.bham.ac.uk/hxc889";
    private static String username = "hxc889";
    private static String password = "jayonwead";
    private static Connection connection = makeConnection();

    public static Connection makeConnection(){
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ResultSet makeLoginQuery(String username){
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?")){
            statement.setString(1, username);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static synchronized void makeAttemptsIncrement(String username, int i){
        try (PreparedStatement statement = connection.prepareStatement("UPDATE users SET attempts = ? WHERE username = ?")){
            statement.setInt(1, i + 1);
            statement.setString(2, username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void insertNewUser(String username, String password){
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password, attempts, locked_time) VALUES (?, ?, 0, null)")){
            statement.setString(1, username);
            statement.setString(2, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}