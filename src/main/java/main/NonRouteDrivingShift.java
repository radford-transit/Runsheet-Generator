package main;

public class NonRouteDrivingShift extends Shift implements Comparable<NonRouteDrivingShift> {
	/** Description of the non-route driving shift */
	public String description;

	/**
	 * Constructs a NonRouteDrivingShift object
	 * @param name The shift name
	 * @param time A time period for the shift
	 * @param employee An employee to assign to the shift
	 */
	public NonRouteDrivingShift(String name, String description,
			TimePeriod time, Employee employee) {
		super(name, time, employee);
		
		this.description =
				description.replace("(S&E)", "")
									 .replace("(S)", "")
									 .replace("(H)", "")
									 .replace("(E)", "")
									 .trim();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(NonRouteDrivingShift other) {
		return this.name.equals(other.name)
				? this.description.equals(other.description)
						? this.time.start.compareTo(other.time.start)
						: this.description.compareTo(other.description)
				: this.name.compareTo(other.name);
	}
}
