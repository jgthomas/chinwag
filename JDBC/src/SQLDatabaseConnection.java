import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 * @author Alexandros Evangelidis
 * @version 2019-02-23
 */
public class SQLDatabaseConnection {

	public static void main(String[] args) throws IOException {

		String url;
		String username;
		String password;

		try (FileInputStream input = new FileInputStream(new File("db.properties"))) {
			Properties props = new Properties();

			props.load(input);

			// String driver = (String) props.getProperty("driver");
			username = (String) props.getProperty("username");
			password = (String) props.getProperty("password");
			url = (String) props.getProperty("URL");

			// We do not need to load the driver explicitly
			// DriverManager takes cares of that
			// Class.forName(driver);
		}

		try (Connection connection = DriverManager.getConnection(url, username, password);
				PreparedStatement insertStatement = connection
						.prepareStatement("INSERT INTO user_info (id_number,first_name,last_name) VALUES (?,?,?) ");) {

			System.out.println("Connection established");

			insertStatement.setInt(1, 45);
			insertStatement.setString(2, "John");
			insertStatement.setString(3, "Smith");

			insertStatement.executeUpdate();

			insertStatement.setInt(1, 46);
			insertStatement.setString(2, "John");
			insertStatement.setString(3, "Anderson");

			insertStatement.executeUpdate();

			// parameterised query
			try (PreparedStatement selectStatement = connection
					.prepareStatement("SELECT first_name, last_name FROM user_info WHERE first_name= ?")) {

				selectStatement.setString(1, "John");

				try (ResultSet resultSet = selectStatement.executeQuery()) {
					while (resultSet.next()) {

						// 1 way to get first name and last name
						// String firstName = resultSet.getString(1);
						// String lastName = resultSet.getString(2);

						// 2nd way
						String firstName = resultSet.getString("first_name");
						String lastName = resultSet.getString("last_name");

						System.out.println(firstName + " " + lastName);
					}
				}
			}
		} catch (SQLException e) {	}
	}

}
