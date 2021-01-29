package hotel_management_app.Java_Hotel_Management_App;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Booking{

	Scanner in = new Scanner(System.in);
	Database db = new Database();
	
	public String get_current_date()
	{
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();  		   
		try {
			
			Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(String.valueOf(dtf.format(now)) + " 10:00:00");
			return String.valueOf(date);
			
		} 
		catch (Exception e) { System.out.println("An error has occurred :( " + e.getMessage());	}
		
		return null;
	}
	
	public String get_booking_date()
	{		
		
		try
		{
			
			String month = get_booking_month();
			String year = get_booing_year();
			
			String date = year + "-" + month;
			
			return date + " " + "10:00:00";						
		}
		catch(Exception e)
		{
			System.out.println("An error as occurred :( " + e.getMessage());
		}
		
		return null;
	}
	
	//booking year
	private String get_booing_year() {
		
		try 
		{
			int year = Calendar.getInstance().get(Calendar.YEAR);
			System.out.print("\nYear of booking " + year + "-2500: ");
			int choice = in.nextInt();
			
			while(choice < year || choice > 2500)
			{
				
				System.out.print("Invalid year...try again");
				System.out.print("\nYear of booking: ");
				choice = in.nextInt();
				
			}
			
			return String.valueOf(choice);
			
		} 
		catch (Exception e) { System.out.println("An error as occurred :( " + e.getMessage()); }		
		
		return null;
	}

	//booking month
	private String get_booking_month() {
		
		try 
		{
			String[] months_array = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
			LinkedHashMap<Integer, Boolean> months_with_31_days = new LinkedHashMap<Integer, Boolean>();
			months_with_31_days.put(1, true);
			months_with_31_days.put(3, true);
			months_with_31_days.put(5, true);
			months_with_31_days.put(7, true);
			months_with_31_days.put(8, true);
			months_with_31_days.put(10, true);
			months_with_31_days.put(12, true);			
			
			
			int day = get_booking_day();
			System.out.println("\nMonth of booking: \n");
			
			int i = 1;
			for(String month : months_array)
			{
				if(day == 31 && !months_with_31_days.containsKey(i))
				{
					i++;
					continue;
				}
				else if(day == 30 && i == 2)
				{
					i++;
					continue;
				}				
				
				System.out.println((i++) + ". " + month);
			}
		
			int choice = in.nextInt();
			
			while(choice < 0 || choice > 12)
			{
				System.out.print("Invalid month...try again");
				System.out.print("\nMonth of booking: 1-12");
				choice = in.nextInt();
			}
			
			return choice < 10 ? "0" + choice + "-" + day: String.valueOf(choice) + "-" + day;
		} 
		catch (Exception e) { System.out.println("An error as occurred :( " + e.getMessage()); }		
		
		return null;
	}

	//booking day
	private int get_booking_day() {
		
		try {
			
		
		System.out.println("\nDay of booking: \n");
		
		for(int i = 1; i <= 31; i++)
		{
			if(i % 8 == 0) System.out.println("\n");
			System.out.print(" " + i + " ");
		}
		System.out.println("");
		int choice = in.nextInt();
		
		while(choice < 0 || choice > 31)
		{
			System.out.print("Invalid day...try again");
			System.out.print("\nDay of booking: 1-7");
			choice = in.nextInt();
		}
		
		return choice;
		
	} 
	catch (Exception e) { System.out.println("An error as occurred :( " + e.getMessage()); }	
	
	return 0;
	};
			
	//add booking to calendar - insert into database
	public void add_booking(Booking booking, Room room, String id, String name)
	{
		try {
			
			System.out.print("When would you like to book the room for: ");			
			Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(booking.get_booking_date());
			
			int checked_in = booking.get_current_date().equals(String.valueOf(date)) ? 1 : 0;
			
			String room_number = room.get_room_number(room.get_occupied_rooms(String.valueOf(date)));
			Connection conn = db.getConnection();
			PreparedStatement post = conn.prepareStatement("INSERT INTO RoomBookings (customer_id, name, room_number, date, checked_in) VALUES ('" + id + "', '" + name 
															+ "', '" + room_number + "', '" + String.valueOf(date) + "', '" + checked_in + "')");
			post.executeUpdate();
			
			System.out.println("Record added");
			
		} catch (Exception e) {
			System.out.println("So sorry, error booking room :( " + e.getMessage());
		}
	}	
		
	//get all bookings
	public String get_bookings()
	{
		
		try
		{
			System.out.print("\nCustomer id number: ");
			String id = in.next();
			
			Connection conn = db.getConnection();
			PreparedStatement read = conn.prepareStatement("SELECT * FROM RoomBookings WHERE customer_id = '" + id + "'");
			
			ResultSet result = read.executeQuery();
			String response = "";
			
			while(result.next())
			{
				response += ("\nBooking id: " + result.getString("id") + 
							"\nName: " + result.getString("name") + 
							"\nRoom: " + result.getString("room_number") + 
							"\nDate: " + result.getString("date") + 
							"\nChecked in: " + result.getString("checked_in") + "\n" +
							"*********************"); 
			}
			
			return response.isEmpty() ? "No bookings for customer with id: " + id : response;
			
		}
		catch(Exception e) { System.out.println("Error :( " + e.getMessage()); }
		
		return null;
	}
		
	//cancel booking 
	public void cancel_booking()
	{
		try
		{
			System.out.println(get_bookings());
			System.out.print("\nEnter booking id: ");
			int id = in.nextInt();
			
			System.out.print("\nAre you sure you want to delete this record y|n: ");
			String response = in.next();
			
			if(!response.equals("y") && !response.equals("Y")) 
			{ 				
				System.out.println("\nBye");
				System.exit(0); 
			}
			
			Connection conn = db.getConnection();
			PreparedStatement update = conn.prepareStatement("DELETE FROM RoomBookings WHERE id='" + id + "'");
			update.executeUpdate();
			
			System.out.println("Record erased");
			
			
		}
		catch(Exception e) { System.out.println("Error :( " + e.getMessage()); }
		
		
	}
		
	//update room bookings
	public void update_booking(int id, int checked_in)
	{
		try
		{
			Connection conn = db.getConnection();
			PreparedStatement update = conn.prepareStatement("UPDATE RoomBookings SET checked_in='" + checked_in + "' WHERE id='" + id + "'");
			update.executeUpdate();
			
			System.out.println("Update successful :)");
		}
		catch(Exception e) { System.out.println("Error updating bookings :( " + e.getMessage() + "\ncheck details and try again"); }
	}

	
}
