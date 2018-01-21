package ticketBooking;

import java.io.Serializable;

/**
 * Customer class describes a customer information.
 */
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String name;
	
	private String group;
	
	private SeatClass userClass;
	
	private Seat seat;
	
	/**
	 * Instantiates a new customer.
	 *
	 * @param name customer name
	 * @param group customer group name
	 * @param seat seat of the customer
	 */
	public Customer(String name, String group, Seat seat) {
		this.name = name;
		this.group = group;
		this.seat = seat;
	}
	
	/**
	 * Instantiates a new customer.
	 *
     * @param name customer name
     * @param group customer group name
	 * @param userClass the seat class of the customer
	 */
	public Customer(String name, String group, SeatClass userClass) {
		this.name = name;
		this.group = group;
		this.userClass = userClass;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new customer name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}
                         
	/**
	 * Sets the group.
	 *
	 * @param group the new customer group
	 */
	public void setGroup(String group) {
		this.group = group;
	}
	
	/**
	 * Gets the seat.
	 *
	 * @return the seat
	 */
	public Seat getSeat() {
		return seat;
	}
	
	/**
	 * Sets the seat.
	 *
	 * @param seat the new customer seat
	 */
	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	/**
	 * Gets the customer class.
	 *
	 * @return the customer class
	 */
	public SeatClass getUserClass() {
		return userClass;
	}

	/**
	 * Sets the customer class.
	 *
	 * @param userClass the new customer seat class
	 */
	public void setUserClass(SeatClass userClass) {
		this.userClass = userClass;
	}
		
}
