package hotel_management_app.Java_Hotel_Management_App;

public interface PrimaryActions {

	public void add_new(String id, String f_name, String l_name, String d_o_b);
	
	public String get_customer_name(String id);
	
	public void check_in(Booking booking, Room room);
	
	public void check_out(Booking booking);
	
}
