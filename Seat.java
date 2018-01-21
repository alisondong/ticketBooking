package ticketBooking;

import java.io.Serializable;

/**
 * The Class of a Seat.
 */
public class Seat implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int row;
	
	private Character col;
	
	private SeatClass seatType;
	
	private SeatPreference seatPreference;
	
	boolean isBooked;
	
	private Customer customer = null;
	
	private String group = null;
	
	/**
	 * Instantiates a new seat.
	 *
	 * @param int row the row
	 * @param int col the col
	 * @param SeatClass seatType the seat type
	 * @param SeatPreference preference the preference
	 */
	public Seat(int row, Character col, SeatClass seatType, SeatPreference preference) {
		this.row = row;
		this.col = col;
		this.seatType = seatType;
		this.seatPreference = preference;
		isBooked = false;
	}
	
	/**
	 * Gets the row.
	 *
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Sets the row.
	 *
	 * @param row the new row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * Gets the col.
	 *
	 * @return the col
	 */
	public Character getCol() {
		return col;
	}

	/**
	 * Sets the col.
	 *
	 * @param col the new col
	 */
	public void setCol(Character col) {
		this.col = col;
	}

	/**
	 * Gets the seat type.
	 *
	 * @return the seat type
	 */
	public SeatClass getSeatType() {
		return seatType;
	}

	/**
	 * Sets the seat type.
	 *
	 * @param seatType the new seat type
	 */
	public void setSeatType(SeatClass seatType) {
		this.seatType = seatType;
	}

	/**
	 * Checks if is booked.
	 *
	 * @return true, if is booked
	 */
	public boolean isBooked() {
		return isBooked;
	}

	/**
	 * Sets the booked.
	 *
	 * @param isBooked the new booked
	 */
	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	/**
	 * Gets the customer.
	 *
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Sets the customer.
	 *
	 * @param customer the new customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
	 * @param group the new group
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Gets the seat preference.
	 *
	 * @return the seat preference
	 */
	public SeatPreference getSeatPreference() {
		return seatPreference;
	}

	/**
	 * Sets the seat preference.
	 *
	 * @param seatPreference the new seat preference
	 */
	public void setSeatPreference(SeatPreference seatPreference) {
		this.seatPreference = seatPreference;
	}	
}
