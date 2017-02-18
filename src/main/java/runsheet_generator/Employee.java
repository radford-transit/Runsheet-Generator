package main.java.runsheet_generator;

public class Employee {
	Name name;
	boolean hasCDL;
	boolean lineInstructor;

	public Employee(String lastName, String firstName) {
		this.hasCDL = getCDLStatus(lastName);
		this.lineInstructor = getLineInstructorStatus(lastName);
		this.name = new Name(lastName, firstName);
	}

	private boolean getCDLStatus(String lastName) {
		return !lastName.toLowerCase().replaceAll(" ", "").contains("(nocdl)");
	}

	private boolean getLineInstructorStatus(String lastName) {
		return lastName.toLowerCase().replaceAll(" ", "").contains("(lineinstructor)");
	}

	@Override
	public String toString() {
		return (
				name.equals(new Name("", "")) ?
						"NO DRIVER ASSIGNED"
						: this.name.toString() + "\n"
							+ (this.hasCDL ?
									"  CDL Holder"
									: "  Non-CDL Holder")
							+ (this.lineInstructor?
									"\n  Line Instructor"
									: "")
		);
	}
}
