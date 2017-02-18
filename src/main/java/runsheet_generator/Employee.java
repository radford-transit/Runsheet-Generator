package main.java.runsheet_generator;

public class Employee {
	Name name;
	boolean hasCDL;
	boolean lineInstructor;

	/**
	 * Constructs and Employee object
	 * @param lastName Last name of the employee, which may or may not contain
	 * information about whether the employee holds a CDL or is a line instructor
	 * @param firstName First name of the employee
	 */
	public Employee(String lastName, String firstName) {
		this.hasCDL = getCDLStatus(lastName);
		this.lineInstructor = getLineInstructorStatus(lastName);
		this.name = new Name(lastName, firstName);
	}

	/**
	 * Tells whether the employee holds a CDL
	 * @param lastName Last name of the employee, which may or may not contain
	 * information about whether the employee holds a CDL
	 * @return false if the last name contains a variation of "(nocdl)" with
	 * any amount of whitespace between characters and any combination of
	 * lowercase and uppercase characters. Otherwise, returns true.
	 */
	private boolean getCDLStatus(String lastName) {
		return !lastName.toLowerCase().replaceAll(" ", "").contains("(nocdl)");
	}

	/**
	 * Tells whether the employee is a line instructor
	 * @param lastName Last name of the employee, which may or may not contain
	 * information about whether the employee is a line instructor
	 * @return true if the last name contains a variation of "(lineinstructor)"
	 * with any amount of whitespace between characters and any combination of
	 * lowercase and uppcase characters. Otherwise, returns false.
	 */
	private boolean getLineInstructorStatus(String lastName) {
		return lastName.toLowerCase().replaceAll(" ", "").contains("(lineinstructor)");
	}

	/**
	 * {@inheritDoc}
	 */
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
