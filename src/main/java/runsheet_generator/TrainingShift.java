package main.java.runsheet_generator;

public class TrainingShift extends Shift implements Comparable<TrainingShift> {
	public TrainingShift(String name, TimePeriod time, Employee employee) {
		super(name, time, employee);
	}

	@Override
	public int compareTo(TrainingShift other) {
		return (
				this.time.compareTo(other.time) == -1 ?
						-1 : 1
		);
	}
}
