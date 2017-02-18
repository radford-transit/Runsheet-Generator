public class NonRouteDrivingShift extends Shift implements Comparable<NonRouteDrivingShift> {
	public NonRouteDrivingShift(String name, TimePeriod time,
			Employee employee) {
		super(name, time, employee);
	}
	
	@Override
	public int compareTo(NonRouteDrivingShift other) {
		return (
				this.time.compareTo(other.time) == -1 ?
						-1 : 1
		);
	}
}
