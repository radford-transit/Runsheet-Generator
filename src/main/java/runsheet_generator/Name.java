package main.java.runsheet_generator;

public class Name {
	String last;
	String first;

	/**
	 * Constructs a name object
	 * @param last The last name
	 * @param first The first name
	 */
	public Name(String last, String first) {
		this.last = last
				.replaceAll(" ", "")
				.replace("(NOCDL)", "")
				.replace("(LineInstructor)", "");
		this.first = first;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return last + ", " + first;
	}

	@Override
	public boolean equals(Object other) {
		return
				other instanceof Name ?
					this.last.equals(((Name) other).last)
					&& this.first.equals(((Name) other).first)
				: false;
	}
}
