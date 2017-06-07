package main.java.runsheet_generator;

public class RouteDrivingShift extends Shift implements Comparable<RouteDrivingShift> {
	Route route;
	char period;

	/**
	 * Constructs a RouteDrivingShift object
	 * @param name The shift name
	 * @param period The period of the shift
	 * @param time The time period of the shift
	 * @param employee The employee assigned to the shift
	 * @throws Exception
	 */
	public RouteDrivingShift(String name, char period,
			TimePeriod time, Employee employee) throws Exception {
		super(trim(name), time, employee);
		this.period = period;
		this.route = new Route(name);
	}

	/**
	 * Removes substrings "(Full Service)" and "(City Only Service)" from the
	 * position name
	 * @param positionName
	 * @return The trimmed position name
	 */
	private static String trim(String positionName) {
		return positionName
				.replace("(Full Service)", "")
				.replace("(City Only Service)", "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(RouteDrivingShift other) {
		/* TimePeriod object comparison is for sorting
		 * non-route-driving shifts and training shifts.
		 * Route-driving shifts are instead sorted by
		 * comparing their starting TimePoints and then
		 * their route IDs.
		 */
		return (
			this.time.start.hour < other.time.start.hour
					? -1
					: this.time.start.hour == other.time.start.hour
							? this.route.compareTo(other.route) == -1
									? -1 : 1
							: 1
		);
	}
}
