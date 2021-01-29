package hotel_management_app.Java_Hotel_Management_App;

import java.util.Scanner;

public abstract class User {

	Scanner in = new Scanner(System.in);
	
	//variables
	private String first_name;
	private String last_name;
	private String id_number;
	private int day;
	private String month;	
	private int year;
	private String date_of_birth;
	
	//constructor
	public User() {}
	
	//getters
	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getId_number() {
		return id_number;
	}

	public int getDay() {
		return day;
	}

	public String getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public String getDate_of_birth() {
		return date_of_birth;
	}
		
	//methods that are inherited
	protected void register_new()
	{
				
		try
		{
			System.out.print("\nEnter first name: ");
			this.first_name = in.next();
						
			System.out.print("\nEnter last name: ");
			this.last_name = in.next();		
						
			System.out.print("\nEnter id number: ");
			this.id_number = in.next();
			
			System.out.print("\nEnter day of birth (1-31): ");
			this.day = in.nextInt();
						
			System.out.print("\nEnter month of birth (January-Decemeber | Jan-Dec): ");
			this.month = in.next();
						
			System.out.print("\nEnter year of birth (current year-2500): ");
			this.year = in.nextInt();
					
			in.nextLine();
		}
		catch(Exception e) { System.out.println("Invalid entry :( " + e.getMessage());}
		
		
		this.date_of_birth = day + "-" + month + "-" + year;
		
	}	
		
}
