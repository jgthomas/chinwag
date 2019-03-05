package serverclient;

import java.sql.*;

public class Database {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public Database(){
        this.url = "jdbc:postgresql://mod-fund-databases.cs.bham.ac.uk/hxc889";
        this.username = "hxc889";
        this.password = "jayonwead";
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){ return connection; }

    public ResultSet makeLoginQuery(String username){
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?")){
            statement.setString(1, username);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void makeAttemptsIncrement(String username, int i){
        try (PreparedStatement statement = connection.prepareStatement("UPDATE users SET attempts = ? WHERE username = ?")){
            statement.setInt(1, i + 1);
            statement.setString(2, username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}