public class Route implements Comparable<Route> {
	int id;
	String name;
	
	public Route(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Route(String name) throws Exception {
		this.id = getRouteID(name);
		this.name = getRouteName(name);
	}
	
	private int getRouteID(String name) throws Exception {
		if (!(Character.isDigit(name.charAt(0))
				&& Character.isDigit(name.charAt(1))))
			throw new Exception("Not a route shift.");
		return Integer.parseInt(name.substring(0, 2));
	}
	
	private String getRouteName(String fullName) {
		return fullName.substring(5, fullName.length());
	}
	
	public boolean equals(Route other) {
		return this.id == other.id && this.name.equals(other.name);
	}
	
	@Override
	public int compareTo(Route other) {
		return (
			this.id < other.id ?
					-1
					: this.id == other.id ?
							0 : 1
		);
	}
}
