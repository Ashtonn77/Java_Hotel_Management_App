package hotel_management_app.Java_Hotel_Management_App;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

	
	public Connection getConnection() throws Exception
	{
		
		try
		{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/hotel_management_db";
			String username = "root";
			String password = "A$htonn77";
			
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url, username, password);
			
			System.out.println("Connection successful :)");
			
			return conn;
		}
		catch(Exception e)
		{
			System.out.println("So sorry, something seems to be broken :( " + e.getMessage());
		}
		
		return null;		
	}
		
}


