package main;

public class ShiftChange implements Comparable {
	/** Hour of the shift change */
	public int hour;
	/** ID of the shift change */
	public ShiftChangeID id;

	/**
	 * Constructs a ShiftChange object
	 * @param hour The hour of the shift change
	 * @param id The ShiftChangeID to assign to the shift change
	 */
	public ShiftChange(int hour, ShiftChangeID id) {
		this.hour = hour;
		this.id = id;
	}
	
	/**
	 * Constructs a ShiftChange object
	 * @param time The time of the shift change
	 * @param id The ShiftChangeID to assign to the shift change
	 */
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

	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof ShiftChange)
			return this.hour == ((ShiftChange) other).hour && this.id == ((ShiftChange) other).id;
		return false;
	}
}
