package main.java.runsheet_generator;

public class Name {
	String last;
	String first;

	public Name(String last, String first) {
		this.last = last
				.replaceAll(" ", "")
				.replace("(NOCDL)", "")
				.replace("(LineInstructor)", "");
		this.first = first;
	}

	@Override
	public String toString() {
		return last + ", " + first;
	}

	public boolean equals(Name other) {
		return
				this.last.equals(other.last)
				&& this.first.equals(other.first);
	}
}
