package database;

import org.junit.*;
import server_command.Hasher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DatabaseTest {

	private static String url = "jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/group22";
	private static String username = "group22";
	private static String password = "group22";
	private static Connection connection;

	@Before
	public void before(){
		try {
			connection = DriverManager.getConnection(url, username, password);
			Database.connection = connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// This method tests if the connection with database if established.
	@Test
	public void test1(){
		try {
			assertEquals(true, Database.connection.isValid(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// This method tests the getSalt(String username) method.
	@Test
	public void test2(){
		String expected = "6SUlBYyLBSUndyw5TOu/grRLtRnnFyUVF0m30Ql6WA4=";
		String actual = Database.getSalt("jayon");
		assertEquals(expected, actual);
	}

	// This method tests the isValidUser(String username, String hash) method.
	@Test
	public void test3(){
		byte[] salt = Hasher.stringToBytes("6SUlBYyLBSUndyw5TOu/grRLtRnnFyUVF0m30Ql6WA4=");
		String password = "123456";
		String hash = Hasher.hashPassword(password, salt);
		assertEquals(true, Database.isValidUser("jayon", hash));
		assertEquals(false, Database.isValidUser("aidan", hash));
	}

	// This method tests the userExists(String username) method.
	@Test
	public void test4(){
		String username = "jayonnn";
		assertEquals(true, Database.userExists("jayon"));
		assertEquals(false, Database.userExists(username));
	}

	// This method tests the isFriend(String username, String friend) method.
	@Test
	public void test5(){
		String username = "ben";
		String friend1 = "aidan";
		String friend2 = "handsomedancer";
		assertEquals(true, Database.isFriend(username, friend1));
		assertEquals(false, Database.isFriend(username, friend2));
	}

	// This method tests the insertNewUser(String username, String salt, String pwHash) method.
	@Test
	public void test6(){

	}

	// This method tests the insertFriend(String username, String friend) method.
	@Test
	public void test7(){

	}

	// This method tests the removeFriend(String username, String friend) method.
	@Test
	public void test8(){

	}

	// This method tests the chatExists(String chatname) method.
	@Test
	public void test9(){
		String chatName1 = "fwends";
		String chatName2 = "hellobrum";
		assertEquals(true, Database.chatExists(chatName1));
		assertEquals(false, Database.chatExists(chatName2));
	}

	// This method tests the addUserToChat(String chatname, String username) method.
	@Test
	public void test10(){

	}

	// This method tests the removeUserFromChat(String chatname, String username) method.
	@Test
	public void test11(){

	}

	// This method tests the insertMessage(Message message) method.
	@Test
	public void test12(){

	}

	// This method tests the insertImage(Image image) method.
	@Test
	public void test13(){

	}

	// This method tests the retrieveChatSessions(String username) method.
	@Test
	public void test14(){
		List<String> actual = new ArrayList<>();
		actual.add("cheeseChat");
		actual.add("nonsense");
		List<String> expected = Database.retrieveChatSessions("bob");
		assertEquals(expected, actual);
	}

}
