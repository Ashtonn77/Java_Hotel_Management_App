package hotel_management_app.Java_Hotel_Management_App;

import java.util.Scanner;

public class Main {
	
	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) throws Exception {
			
		Customer customer = new Customer();
		Booking booking = new Booking();
		Room room = new Room();
		
		ui(customer, booking, room);		
		   
	}
	
	public static void back_prompt(Customer customer, Booking booking, Room room)
	{
		System.out.print("Press b to go back to main menu: ");
		String choice = in.next();
		
		if(choice.equals("b") || choice.equals("B"))
		{
			ui(customer, booking, room);
		}
		else
		{
			System.exit(0);
		}
	}
	
	public static void ui(Customer customer, Booking booking, Room room)
	{
		System.out.println("\n");
		System.out.println("\nWELCOME TO THE HEARTBREAK HOTEL:");
		System.out.println("\n1. Reserve room\n2. Check in\n3. Check out\n4. Show reservations");
		int choice = in.nextInt();
		
		switch(choice)
		{
		
		case 1:
			customer.book_new(booking, room);
			back_prompt(customer, booking, room);
			break;
			
		case 2:
			customer.check_in(booking, room);
			back_prompt(customer, booking, room);
			break;
			
		case 3:
			customer.check_out(booking);
			back_prompt(customer, booking, room);
			break;
			
		case 4:
			System.out.println(booking.get_bookings());
			back_prompt(customer, booking, room);
			break;
			
		default:
			System.out.println("Invalid entry");
			back_prompt(customer, booking, room);
			break;
		
		}
		System.out.println("\n");
	}
	
}


