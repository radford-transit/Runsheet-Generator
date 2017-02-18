public class Shift {
	String name;
	TimePeriod time;
	Employee employee;
	
	public Shift(String name, TimePeriod time, Employee employee) {
		this.name = name;
		this.time = time;
		this.employee = employee;
	}
	
	@Override
	public String toString() {
		return time + "\n  " + name + "\n  " + employee;
	}
}
