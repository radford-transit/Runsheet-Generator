public class TimePoint implements Comparable<TimePoint> {
	int hour;
	int minute;
	
	public TimePoint(String timeStr) throws Exception {
		// timeStr format: "HH:MM BM"
		String period = timeStr.substring(timeStr.indexOf(' ') + 1, timeStr.length());
		if (period.equals("AM"))
			this.hour = Integer.parseInt(timeStr.substring(0, timeStr.indexOf(':'))) % 12;
		else if (period.equals("PM"))
			this.hour = Integer.parseInt(timeStr.substring(0, timeStr.indexOf(':'))) % 12 + 12;
		else throw new Exception("Invalid time point ");
			this.minute = Integer.parseInt(timeStr.substring(timeStr.indexOf(':') + 1, timeStr.indexOf(' ')));
	}
	
	public TimePoint(int hour, int minute) {
		if (!(minute >= 0 && minute < 60))
			System.out.println("ERROR: Invalid minute" + minute);
		
		this.hour = hour;
		this.minute = minute;
	}
	
	@Override
	public String toString() {
		return hour + ":"
				+ (minute >= 0 && minute < 10 ? "0" : "")
				+ minute;
	}
	
	@Override
	public int compareTo(TimePoint other) {  
		return (
				(this.hour < other.hour) ?
						-1
						: (this.hour == other.hour) ?
								(this.minute < other.minute) ?
										-1
										: (this.minute == other.minute)?
												0 : 1
								: 1
		);
	}
}
