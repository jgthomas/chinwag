package database;

import java.sql.*;
import java.util.*;

public class Database {

    private static String url = "jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/group22";
    private static String username = "group22";
    private static String password = "group22";
    public static Connection connection;

    /**
     * Opens a connection between server and database.
     */
    public static void makeConnection(){
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection between server and database.
     */
    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Retrieves the salt value for a user.
     * @param username Username of the current client.
     * @return Salt value as a String.
     */
    public static synchronized String getSalt(String username) {
    	try (PreparedStatement statement = connection.prepareStatement(
        		"SELECT salt FROM users WHERE username = ?")){
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getString("salt");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Checks if the input username and password hash combination is contained
     * in the database.
     * @param username Username of the current client.
     * @param hash Hash of the input password.
     * @return True if valid user, else False.
     */
    public static synchronized boolean isValidUser(String username, String hash){
        try (PreparedStatement statement = connection.prepareStatement(
        		"SELECT * FROM users WHERE username = ? AND pw_hash = ?")){
            statement.setString(1, username);
            statement.setString(2, hash);
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

    /**
     * Checks if the input username exists in the database.
     * @param username Username supplied by the current client.
     * @return True if the username exists, else False.
     */
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
    
    /**
     * Checks if the input usernames are listed as friends in the database.
     * @param username Username of current logged in client.
     * @param friend Username of the friend of the current logged in client.
     * @return True if friend relationship exists, else False.
     */
    public static synchronized boolean isFriend(String username, String friend) {
    	try(PreparedStatement statement = connection.prepareStatement(
        		"SELECT * FROM friend WHERE username = ? AND friend = ?")){
            statement.setString(1, username);
            statement.setString(2, friend);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inserts a new user into the database.
     * @param username Username of the new user.
     * @param salt Salt value for the new user.
     * @param pwHash Hash of password for the new user.
     */
    public static synchronized void insertNewUser(String username, String salt, String pwHash){
        try (PreparedStatement statement = connection.prepareStatement(
        		"INSERT INTO users (username, salt, pw_hash) VALUES (?, ?, ?)")){
            statement.setString(1, username);
            statement.setString(2, salt);
            statement.setString(3, pwHash);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Inserts a new friend relationship into the database.
     * @param username Username of the current logged in client.
     * @param friend Username of the friend being added.
     */
    public static synchronized void insertFriend(String username, String friend) {
    	try (PreparedStatement statement = connection.prepareStatement(
        		"INSERT INTO friend (username, friend) VALUES (?, ?)")){
    		statement.setString(1, username);
    		statement.setString(2, friend);
    		statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Removes a specified friend relationship from the database.
     * @param username Username of the current logged in client.
     * @param friend Username of the friend being removed.
     */
    public static synchronized void removeFriend(String username, String friend) {
    	try (PreparedStatement statement = connection.prepareStatement(
        		"DELETE FROM friend WHERE username = ? AND friend = ?")){
    		statement.setString(1, username);
    		statement.setString(2, friend);
    		statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
//	/**
//	 * Inserts users from a file, allowing for bulk adding of many users.
//	 * Do not need to synchronise as this will happen just once to populate
//	 * the database.
//	 * @param path
//	 * @throws IOException
//	 */
//	public static void insertBulkUsers(String path) throws IOException {
//		
//		try (BufferedReader br = new BufferedReader(new FileReader(path));
//				PreparedStatement insertStatement = connection.prepareStatement(
//						"INSERT INTO users (username, password) VALUES (?,?)");) 
//		{
//			String line;
//			
//			while ((line = br.readLine()) != null) {
//				String[] lineArr = line.split(" ");
//				insertStatement.setString(1, lineArr[0]);
//				insertStatement.setString(2, lineArr[1]);
//				insertStatement.executeUpdate();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

    /**
     * Checks if an input chatname exists in the database.
     * @param chatname Name of chat.
     * @return True if the chatname exists, else False.
     */
	public static synchronized boolean chatExists(String chatname){
		try (PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM chatsession WHERE chatname = ?")) {
			statement.setString(1, chatname);
			return statement.executeQuery().next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Adds a username to a specified chat in the database.
	 * @param chatname Name of chat to add user into.
	 * @param username Username of user.
	 */
	public static synchronized void addUserToChat(String chatname, String username){
	    try (PreparedStatement statement = connection.prepareStatement(
	    		"INSERT INTO chatsession (chatname, username) VALUES (?, ?)"))
	    {
	        statement.setString(1, chatname);
	        statement.setString(2, username);
	        statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Removes a username from a specified chat in the database.
	 * @param chatname Name of chat to remove user from.
	 * @param username Username of user.
	 */
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
    
    /**
     * Inserts a message into the database message history table.
     * @param message Message object containing chatname, sender, content, and timestamp.
     */
    public static synchronized void insertMessage(Message message) {
    	try (PreparedStatement statement = connection.prepareStatement(
    			"INSERT INTO message (chatname, sender, content, timestamp) VALUES (?, ?, ?, ?)"))
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

    public static synchronized void insertImage(Image image){
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO image (chatname, sender, path, timestamp) VALUES (?, ?, ?, ?)"))
        {
            statement.setString(1, image.getChatname());
            statement.setString(2, image.getSender());
            statement.setString(3, image.getPath());
            statement.setTimestamp(4, image.getTimestamp());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Gets a List of chats that a user is a member of.
     * @param username Username of current logged in client.
     * @return List of chatnames that user is a member of.
     */
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

    /**
     * Gets a List of the users in a particular chat.
     * @param chatname Name of the chat being queried.
     * @return List of usernames in the specified chat.
     */
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
    
    /**
     * Gets a List of the usernames of friends for a particular user.
     * @param username Username of currently logged in client.
     * @return List of usernames of friends of the user.
     */
    public static synchronized List<String> retrieveFriends(String username){
	    try (PreparedStatement statement = connection.prepareStatement(
	    		"SELECT friend FROM friend WHERE username = ?")){
	        statement.setString(1, username);
	        ResultSet rs = statement.executeQuery();
	        List<String> friends = new ArrayList<>();
	        while (rs.next()){
	            friends.add(rs.getString(1));
            }
            return friends;
        } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
        }
    }
    
    /**
     * Gets the message history for a particular chat.
     * @param chatname Name of the chat being queried.
     * @param limit Number of Messages to be returned.
     * @return ArrayList of Message objects for the specified chat.
     */
    public static synchronized ArrayList<Message> retrieveMessages (String chatname, int limit) {
    	try (PreparedStatement statement = connection.prepareStatement(
    			"SELECT * FROM message WHERE chatname = ? ORDER BY timestamp DESC LIMIT ?"))
    	{
    		statement.setString(1, chatname);
    		statement.setInt(2, limit);
    		ResultSet rs = statement.executeQuery();
    		ArrayList<Message> messages = new ArrayList<>();
    		
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








