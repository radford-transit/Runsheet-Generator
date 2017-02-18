public class TimePeriod implements Comparable<TimePeriod> {
	TimePoint start, end;
	
	public TimePeriod(TimePoint start, TimePoint end) {
		this.start = start;
		/* 
		 * If end time hour is less than start time hour,
		 * it is in the next day. Add 24 hours to it.
		 */
		if (end.hour < start.hour)
			end = new TimePoint(end.hour + 24, end.minute);
		this.end = end;
	}
	
	@Override
	public String toString() {
		return start + " - " + end;
	}
	
	@Override
	public int compareTo(TimePeriod other) {
		return this.start.compareTo(other.start);
	}
}
