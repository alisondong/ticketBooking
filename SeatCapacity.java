package ticketBooking;

/**
 * The Class SeatCapacity.
 */
public class SeatCapacity implements Comparable<SeatCapacity> {

	private int rowNumber;
	
	private int capacity;

	/**
	 * Instantiates a new seat capacity.
	 *
	 * @param rowNumber the row number
	 * @param capacity the capacity
	 */
	public SeatCapacity(int rowNumber, int capacity) {
		this.rowNumber = rowNumber;
		this.capacity = capacity;
	}

	/**
	 * Gets the row number.
	 *
	 * @return the row number
	 */
	public int getRowNumber() {
		return rowNumber;
	}

	/**
	 * Sets the row number.
	 *
	 * @param rowNumber the new row number
	 */
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	/**
	 * Gets the capacity.
	 *
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Sets the capacity.
	 *
	 * @param capacity the new capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public int compareTo(SeatCapacity other) {
		return (this.getCapacity() > other.getCapacity() ? -1 : (this.getCapacity() == other.getCapacity() ? 0 : 1));
	}
}
