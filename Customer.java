package hotel_management_app.Java_Hotel_Management_App;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class Customer extends User implements PrimaryActions{

	Scanner in = new Scanner(System.in);
	Database db = new Database();
	
	public Customer() {}
		
	@Override
	public void add_new(String id, String f_name, String l_name, String d_o_b)
	{				
		try
		{		
					
			Connection conn = db.getConnection();
			PreparedStatement post = conn.prepareStatement("INSERT INTO Customer (id_number, first_name, last_name, date_of_birth)"
					+ " VALUES ('" + id + "', '" + f_name + "', '" + l_name + "', '" + d_o_b + "')");
			post.executeUpdate();
			System.out.println("Record added");
		}
		catch(Exception e)
		{
			System.out.println("So sorry, error adding customer :( " + e.getMessage());
		}
		
	}
		
	//register a new customer
	protected void register_new()
	{
		super.register_new();
		add_new(getId_number(), getFirst_name(), getLast_name(), getDate_of_birth());
	}
			
	//get customer name from id
	public String get_customer_name(String id)
	{
		
		try
		{
			Connection conn = db.getConnection();
			PreparedStatement read = conn.prepareStatement("SELECT first_name, last_name FROM Customer WHERE id_number = '" + id + "'");
			ResultSet result = read.executeQuery();
			
			String name = "unavailable";
			while(result.next())
			{
				name = result.getString("first_name") + " " + result.getString("last_name");
			}
			
			return name;
			
		}
		catch(Exception e) { System.out.println("Error :( " + e.getMessage()); }
		
		
		return null;
	}
	
	//book room
	public void book_new(Booking booking, Room room)
	{	
		
		try
		{
			System.out.print("\nHas the customer stayed with us before y|n: ");
			String choice = in.next();
			
			if(choice.equals("n") || choice.equals("N"))
			{
				
				register_new();
				booking.add_booking(booking, room, getId_number(), get_customer_name(getId_number()));
				
			}
			else
			{			
				System.out.print("\nCustomer id number: ");
				String id = in.next();
				booking.add_booking(booking, room, id, get_customer_name(id));				
			}			
			
			
		}
		catch(Exception e)
		{
			System.out.println("An error has occurred :( " + e.getMessage());
		}
				
	};
	
	//checked in/out
	public void check_in(Booking booking, Room room)
	{		
		try
		{			
			System.out.print("Does the customer have a reservation y|n: ");
			String response = in.next();
			
			if(response.equals("y") || response.equals("Y")){ get_cheking_in_and_out_info(booking, 1); }
			else{ book_new(booking, room); }
			
		}
		catch(Exception e) { System.out.println("Error :( " + e.getMessage()); }		
		
	}	
	
	public void check_out(Booking booking){ get_cheking_in_and_out_info(booking, 0); }
	
	private void get_cheking_in_and_out_info(Booking booking, int checked_in)
	{
		System.out.println(booking.get_bookings());
		System.out.print("\nEnter booking id: ");
		int id = in.nextInt();
		booking.update_booking(id, checked_in);
	}
	

}







