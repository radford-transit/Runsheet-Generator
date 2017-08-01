package main;

public class TrainingShift extends Shift implements Comparable<TrainingShift> {
	/**
	 * Constructs a TrainingShift object
	 * @param name The name of the training shift
	 * @param time The time period of the training shift
	 * @param employee The employee assinged to the training shift
	 */
	public TrainingShift(String name, TimePeriod time, Employee employee) {
		super(name.replace("(S&E)", "")
							.replace("(S)", "")
							.replace("(H)", "")
							.replace("(E)", "")
							.trim(),
					time,
					employee);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(TrainingShift other) {
		return this.time.compareTo(other.time) == -1 ? -1 : 1;
	}
}
