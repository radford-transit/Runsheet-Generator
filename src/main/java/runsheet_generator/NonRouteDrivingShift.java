package main.java.runsheet_generator;

public class NonRouteDrivingShift extends Shift implements Comparable<NonRouteDrivingShift> {
	String description;
	
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
		return (
				this.time.compareTo(other.time) == -1 ?
						-1 : 1
		);
	}
}
