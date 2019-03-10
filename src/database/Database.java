package database;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Database {

    private static String url = "jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/group22";
    private static String username = "group22";
    private static String password = "group22";
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
            	return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static synchronized boolean userExists(String username){
        try(PreparedStatement statement = connection.prepareStatement(
        		"SELECT * FROM users WHERE username = ?")){
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
        		"INSERT INTO users (username, password) VALUES (?, ?)")){
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
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

	public static synchronized boolean chatExists(String chatname){
		try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM chatsession WHERE chatname = ?")) {
			statement.setString(1, chatname);
			return statement.executeQuery().next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static synchronized void addUserToChat(String chatname, String username){
	    try (PreparedStatement statement = connection.prepareStatement(
	    		"INSERT INTO chatsession (chatname, username) VALUES (?, ?)"))
	    {
	        statement.setString(1, chatname);
	        statement.setString(2, username);
	        statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void removeUserFromChat(String chatname, String username){
	    try (PreparedStatement statement = connection.prepareStatement(
	    		"DELETE FROM chatsession WHERE chatname = ? AND username = ?"))
	    {
	        statement.setString(1, chatname);
	        statement.setString(2, username);
	        statement.executeQuery();
        } catch (SQLException e) {
	        e.printStackTrace();
        }
    }
    
    public static synchronized void insertMessage (Message message) {
    	try (PreparedStatement statement = connection.prepareStatement(
    			"INSERT INTO message (chatname, sender, content, timestamp) VALUES (?, ?, ?, ?"))
    	{
    		statement.setString(1, message.getChatname());
    		statement.setString(2, message.getSender());
    		statement.setString(3, message.getContent());
    		statement.setTimestamp(4, message.getTimestamp());
    		statement.executeUpdate();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    
    public static synchronized List<String> retrieveChatSessions(String username) {
    	try (PreparedStatement statement = connection.prepareStatement(
    			"SELECT chatname FROM chatsession WHERE username = ? ORDER BY chatname ASC"))
    	{
    		statement.setString(1, username);
    		
    		ResultSet rs = statement.executeQuery();
    		List<String> sessions = new ArrayList<>();
    		
    		while(rs.next()) {
    			String chatname = rs.getString("chatname");
    			sessions.add(chatname);
    		}
    		return sessions;
    	} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }

    public static synchronized List<String> retrieveUsersFromSessions(String chatname){
	    try (PreparedStatement statement = connection.prepareStatement(
	    		"SELECT username FROM chatsession WHERE chatname = ?")){
	        statement.setString(1, chatname);
	        ResultSet rs = statement.executeQuery();
	        List<String> users = new ArrayList<>();
	        while (rs.next()){
	            users.add(rs.getString(1));
            }
            return users;
        } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
        }
    }
    
    public static synchronized List<Message> retrieveMessages (String chatname, int limit) {
    	try (PreparedStatement statement = connection.prepareStatement(
    			"SELECT * FROM message WHERE chatname = ? ORDER BY timestamp ASC LIMIT ?"))
    	{
    		statement.setString(1, chatname);
    		statement.setInt(2, limit);
    		ResultSet rs = statement.executeQuery();
    		List<Message> messages = new ArrayList<>();
    		
    		while(rs.next()) {
    			String chat = rs.getString("chatname");
    			String sender = rs.getString("sender");
    			String content = rs.getString("content");
    			Timestamp timestamp = rs.getTimestamp("timestamp");
    			messages.add(new Message(chat, sender, content, timestamp));
    		}
    		return messages;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
}








