package main;


public class ShiftChangeID {
	/** Period ID for the ShiftChangeID */
	public char period;
	/** Number within the period of the shift change */
	public int number;

	/**
	 * Constructs a ShiftChangeID object
	 * @param period The period ID for the ShiftChangeID
	 * @param number The number within the period of the shift change
	 */
	public ShiftChangeID(char period, int number) {
		this.period = period;
		this.number = number;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "" + this.period + this.number;
	}
}
