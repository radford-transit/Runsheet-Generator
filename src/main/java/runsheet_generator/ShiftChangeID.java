package runsheet_generator;

public class ShiftChangeID {
	char period;
	int number;

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
