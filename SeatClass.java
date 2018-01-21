package ticketBooking;

/**
 * The Enum SeatClass.
 */
public enum SeatClass {
	
	FIRST("First"), 
	ECONOMY("Economy");

	private String type;

	/**
	 * Instantiates a new seat class.
	 *
	 * @param type the type
	 */
	private SeatClass(String type) {
		this.type = type;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return String.valueOf(type);
	}

	/**
	 * Return SeatClass object from value.
	 *
	 * @param text the text
	 * @return the seat class
	 */
	public static SeatClass fromValue(String text) {
		for (SeatClass obj : SeatClass.values()) {
			if (obj.type.equals(text)) {
				return obj;
			}
		}
		return null;
	}
}
