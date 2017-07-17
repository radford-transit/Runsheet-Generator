package main;

public class ShiftChange implements Comparable {
	int hour;
	ShiftChangeID id;

	/**
	 * Constructs a ShiftChange object
	 * @param hour The hour of the shift change
	 * @param id The ShiftChangeID to assign to the shift change
	 */
	public ShiftChange(int hour, ShiftChangeID id) {
		this.hour = hour;
		this.id = id;
	}
	
	public ShiftChange(TimePoint time, ShiftChangeID id) {
		this.hour = time.hour;
		this.id = id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new TimePoint(this.hour, 15).toString() + " - " + new TimePoint(this.hour + 1, 0).toString();
	}

	@Override
	public int compareTo(Object other) {
		if (other instanceof ShiftChange)
			return (
					this.hour < ((ShiftChange) other).hour
							? -1
							: this.hour == ((ShiftChange) other).hour
									? 0
									: 1
			);
		return -2;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof ShiftChange)
			return this.hour == ((ShiftChange) other).hour && this.id == ((ShiftChange) other).id;
		return false;
	}
}
