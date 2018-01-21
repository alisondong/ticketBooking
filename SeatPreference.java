package ticketBooking;

/**
 * The Enum SeatPreference.
 */
public enum SeatPreference {
	
	Window(0), 
	Aisle(1), 
	Center(2);

	private Integer preference;

	/**
	 * Instantiates a new seat preference.
	 *
	 * @param prefenece the prefenece
	 */
	private SeatPreference(Integer prefenece) {
		this.preference = prefenece;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Integer getValue() {
		return this.preference;
	}

	/**
	 * From value.
	 *
	 * @param value the value
	 * @return the seat preference
	 */
	public static SeatPreference fromValue(Integer value) {
		for (SeatPreference obj : SeatPreference.values()) {
			if (obj.preference.equals(value)) {
				return obj;
			}
		}
		return null;
	}
	
	/**
	 * From value.
	 *
	 * @param value the value
	 * @return the seat preference
	 */
	public static SeatPreference fromValue(String value) {
		for (SeatPreference obj : SeatPreference.values()) {
			if (SeatPreference.valueOf(value).equals(obj)) {
				return obj;
			}
		}
		return null;
	}	
}
