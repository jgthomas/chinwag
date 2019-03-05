package database;
import java.io.*;
import java.util.*;
import java.sql.*;

public class JDBC {
	
	private String url;
	private String username;
	private String password;
	
	public JDBC(String path) throws IOException {
		try (FileInputStream input = new FileInputStream(new File(path))) {
			Properties props = new Properties();
			props.load(input);

			this.username = (String) props.getProperty("username");
			this.password = (String) props.getProperty("password");
			this.url = (String) props.getProperty("URL");
		}
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * Inserts users from a file, allowing for bulk adding of many users.
	 * @param path
	 * @throws IOException
	 */
	public void insertBulkUsers(String path) throws IOException {
		
		try (Connection connection = DriverManager.getConnection(this.getUrl(), 
				this.getUsername(), this.getPassword());
				BufferedReader br = new BufferedReader(new FileReader(path));
				PreparedStatement insertStatement = connection
						.prepareStatement("INSERT INTO users "
								+ "(username, password) VALUES (?,?)");) {
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
		
		String url;
		String username;
		String password;
				
		try (FileInputStream input = new FileInputStream(
				new File("db.properties"))) {
			Properties props = new Properties();
			props.load(input);
			
			username = (String) props.getProperty("username");
			password = (String) props.getProperty("password");
			url = (String) props.getProperty("URL");
			
		}
		try (Connection connection = DriverManager.getConnection(url, username, password);
			PreparedStatement insertStatement = 
					connection.prepareStatement("INSERT INTO users "
							+ "(username, password) VALUES (?,?)");) {
					
			System.out.println("Connection established.");
			
			
			File file = new File("users");
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while((line = br.readLine()) != null) {
				String[] lineArr = line.split(" ");
				insertStatement.setString(1, lineArr[0]);
				insertStatement.setString(2, lineArr[1]);
				insertStatement.executeUpdate();
			}
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
