package runsheet_generator;

public class ShiftChange {
	int hour;
	ShiftChangeID id;

	public ShiftChange(int hour, ShiftChangeID id) {
		this.hour = hour;
		this.id = id;
	}

	@Override
	public String toString() {
		return this.hour + ":15 - " + this.id;
	}
}
