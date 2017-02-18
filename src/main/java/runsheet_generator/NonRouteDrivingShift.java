package main.java.runsheet_generator;

public class NonRouteDrivingShift extends Shift implements Comparable<NonRouteDrivingShift> {
	/**
	 * Constructs a NonRouteDrivingShift object
	 * @param name The shift name
	 * @param time A time period for the shift
	 * @param employee An employee to assign to the shift
	 */
	public NonRouteDrivingShift(String name, TimePeriod time,
			Employee employee) {
		super(name, time, employee);
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
