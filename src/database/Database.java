package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class Database {

    private static String url = "jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/group22";
    private static String username = "group22";
    private static String password = "group22";
    private static Connection connection;

    public static void makeConnection(){
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e){
            e.printStackTrace();
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
        try (PreparedStatement statement = connection.prepareStatement(
        		"SELECT * FROM users WHERE username = ? AND password = ?")){
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                return true;
            else {
            	return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static synchronized boolean userExists(String username){
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?")){
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static synchronized void insertNewUser(String username, String password){
        try (PreparedStatement statement = connection.prepareStatement(
        		"INSERT INTO users (username, password, attempts, locked_time) VALUES (?, ?, 0, null)")){
            statement.setString(1, username);
            statement.setString(2, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * Inserts users from a file, allowing for bulk adding of many users.
	 * Do not need to synchronise as this will happen just once to populate
	 * the database.
	 * @param path
	 * @throws IOException
	 */
	public static void insertBulkUsers(String path) throws IOException {
		
		try (BufferedReader br = new BufferedReader(new FileReader(path));
				PreparedStatement insertStatement = connection.prepareStatement(
						"INSERT INTO users (username, password) VALUES (?,?)");) 
		{
			String line;
			
			while ((line = br.readLine()) != null) {
				String[] lineArr = line.split(" ");
				insertStatement.setString(1, lineArr[0]);
				insertStatement.setString(2, lineArr[1]);
				insertStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addUserToChat(String chatname, String username){
	    try (PreparedStatement statement = connection.prepareStatement("INSERT INTO chatsession (chatname, username) VALUES (?, ?)")){
	        statement.setString(1, chatname);
	        statement.setString(2, username);
	        statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeUserFromChat(String chatname, String username){
	    try (PreparedStatement statement = connection.prepareStatement("DELETE FROM chatsession WHERE chatname = ? AND username = ?")){
	        statement.setString(1, chatname);
	        statement.setString(2, username);
	        statement.executeQuery();
        } catch (SQLException e) {
	        e.printStackTrace();
        }
    }
}