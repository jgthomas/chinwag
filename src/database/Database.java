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

    public static synchronized boolean isValidUser(String username, String password){
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")){
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                return true;
            else {
                try (PreparedStatement anotherStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?")){
                    anotherStatement.setString(1, username);
                    rs = anotherStatement.executeQuery();
                    if (rs.next()) {
                        int i = rs.getInt(3);
                        makeAttemptsIncrement(username, i);
                        // Wrong password
                        return false;
                    } else
                        // User doesn't exit
                        return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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