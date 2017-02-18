public class ShiftChangeID {
	char period;
	int number;
	
	public ShiftChangeID(char period, int number) {
		this.period = period;
		this.number = number;
	}
	
	@Override
	public String toString() {
		return "" + this.period + this.number;
	}
}
