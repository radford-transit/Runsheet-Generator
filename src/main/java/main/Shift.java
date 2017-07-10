package main;


public class Shift {
	String name;
	TimePeriod time;
	Employee employee;

	/**
	 * Constructs a Shift object
	 * @param name Name of the shift
	 * @param time Time period of the shift
	 * @param employee Employee assigned to the shift
	 */
	public Shift(String name, TimePeriod time, Employee employee) {
		this.name = name;
		this.time = time;
		this.employee = employee;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return time + "\n  " + name + "\n  " + employee;
	}
}
