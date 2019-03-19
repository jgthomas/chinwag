package database;

import org.junit.*;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;

public class DatabaseTest {

	// This method tests the makeConnection() method.
	@Test
	public void test1(){
		try {
			assertEquals(null, Database.connection);
			Database.makeConnection();
			assertEquals(true, Database.connection.isValid(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// This method tests the getSalt(String username) method.
	@Test
	public void test2(){

	}
}
