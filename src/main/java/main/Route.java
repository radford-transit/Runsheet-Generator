package main;


public class Route implements Comparable<Route> {
	int id;
	String name;

	/**
	 * Constructs a Route object
	 * @param id The route ID
	 * @param name The route name
	 */
	public Route(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Constructs a Route object
	 * @param name The route name
	 * @throws Exception
	 */
	public Route(String name) throws Exception {
		this.id = getRouteID(name);
		this.name = getRouteName(name);
	}

	/**
	 * Returns the ID of the route, given the route name
	 * @param name The route name
	 * @return the route ID
	 * @throws Exception
	 */
	private int getRouteID(String name) throws Exception {
		if (name.equals("Mega Bus Connect"))
			return 40;
		else if ((!(Character.isDigit(name.charAt(0))
				&& Character.isDigit(name.charAt(1)))) &&
						!name.equals("Mega Bus Connect"))
			throw new Exception("Not a route shift.");
		return Integer.parseInt(name.substring(0, 2));
	}

	/**
	 * Returns the name of the route, given a string containing the route ID
	 * and the name of the route
	 * @param fullName The route name
	 * @return
	 */
	private String getRouteName(String fullName) {
		return fullName.substring(5, fullName.length());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object other) {
		return other instanceof Route
				? this.id == ((Route)other).id && this.name.equals(((Route)other).name)
				: false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Route other) {
		return this.id < other.id
				? -1
				: this.id == other.id
						? 0 : 1;
	}
}
