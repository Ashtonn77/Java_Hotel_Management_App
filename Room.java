package hotel_management_app.Java_Hotel_Management_App;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Room {
	
	Scanner in = new Scanner(System.in);
	Database db = new Database();
	
	private LinkedHashMap<String, Boolean> rooms;
	
	public Room()
	{
		String room_letters = "ABCDE";
		rooms = new LinkedHashMap<String, Boolean>();
		int i = 0;
		
		for(int floor = 0; floor < 10; floor++)
		{
			for(int room = 0; room < 5; room++)
			{
				
				String door_value = (floor + 1) + "" + room_letters.charAt(room);
				rooms.put(door_value, true);
				
			}			
			
		}
	
	}	
		
	//get all rooms currently occupied
	public ArrayList<String> get_occupied_rooms(String date)
	{
		try
		{
			ArrayList<String> occupied = new ArrayList<String>();
			Connection conn = db.getConnection();
			PreparedStatement read = conn.prepareStatement("SELECT room_number FROM RoomBookings WHERE date = '" + date + "'");
		
			ResultSet result = read.executeQuery();
			
			while(result.next())
			{
				occupied.add(result.getString("room_number"));
			}
			
			return occupied;
		}
		catch(Exception e) { System.out.println("An error occured :( " + e.getMessage()); }
		
		return null;
	}
	
	public void show_availble_rooms(ArrayList<String> occupiedRooms)
	{		
		
		for(String room : occupiedRooms)
		{
			rooms.put(room, false);			
		}		
		
		int i = 0;
		for(String key : rooms.keySet())
		{
			
			if(i == 5)
			{
				System.out.println("\n");
				i = 0;
			}
			
			if(rooms.get(key)) { System.out.print(" " + key + " "); }
			else { System.out.print(" " + "**" + " "); }
			i++;
			
		}
		
	}
	
	public String get_room_number(ArrayList<String> occupiedRooms)
	{
		try 
		{			
			show_availble_rooms(occupiedRooms);
			System.out.println("\nEnter room: ");
			String room_number = in.next();
			
			while(!rooms.containsKey(room_number) || occupiedRooms.contains(room_number))
			{
				System.out.println("Invalid room number or room is currently occupied...try another room");
				room_number = in.next();
			}
			
			return room_number;
			
		} 
		catch (Exception e) { System.out.println("An error has occurred :( " + e.getMessage());	}
		
		
		return null;
	}
		
}
