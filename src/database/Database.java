package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class Database {

    private static String url = "jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/group22";
    private static String username = "group22";
    private static String password = "eduo72sd5v";
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
        try (PreparedStatement statement = connection.prepareStatement(
        		"SELECT * FROM users WHERE username = ? AND password = ?")){
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                return true;
            else {
                try (PreparedStatement anotherStatement = connection.
                		prepareStatement("SELECT * FROM users WHERE username = ?")){
                    anotherStatement.setString(1, username);
                    rs = anotherStatement.executeQuery();
                    if (rs.next()) {
                        int i = rs.getInt(3);
                        //makeAttemptsIncrement(username, i);
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

    /* Planning to keep attempts counter on server because this is temporary.
     * If later we decide that we want to store the attempts counter on the 
     * database then we can reinstate the following method:
     */
//    public static synchronized void makeAttemptsIncrement(String username, int i){
//        try (PreparedStatement statement = connection.prepareStatement(
//        		"UPDATE users SET attempts = ? WHERE username = ?")){
//            statement.setInt(1, i + 1);
//            statement.setString(2, username);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public static synchronized void insertNewUser(String username, String password){
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password, attempts, locked_time) VALUES (?, ?, 0, null)")){
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
	
	public static void main(String[] args) throws IOException {
		insertBulkUsers("/home/aidan/Documents/Semester2/SoftwareWorkshop/Project/users");
	}
}