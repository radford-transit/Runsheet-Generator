package main.java.runsheet_generator;

public class ShiftChange {
	int hour;
	ShiftChangeID id;

	/**
	 * Constructs a ShiftChange object
	 * @param hour The hour of the shift change
	 * @param id The ShiftChangeID to assign to the shift change
	 */
	public ShiftChange(int hour, ShiftChangeID id) {
		this.hour = hour;
		this.id = id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.hour + ":15 - " + this.id;
	}
}
