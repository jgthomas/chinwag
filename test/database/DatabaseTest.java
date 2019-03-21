package database;

import org.junit.Test;
import static org.junit.Assert.*;

import org.mockito.*;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseTest {
    private static String url = "jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/group22";
    private static String username = "group22";
    private static String password = "group22";
	Connection connection;
	
	@Before
	public void before() {
		connection = DriverManager.getConnection(url, username, password);
	}
	
	@After
	public void after() {
		connection.close();
	}
	
	// test connection
	@Test
	public void test1a() {
		
	}
}
