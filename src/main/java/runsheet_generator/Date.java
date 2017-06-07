package main.java.runsheet_generator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Date {
	int year, month, day;
	String dayOfWeek;

	/**
	 * Constructs a Date object
	 * @param dateStr A String representation of the date
	 * @throws ParseException
	 */
	public Date(String dateStr) throws ParseException {
		this.year = Integer.parseInt(
				dateStr.substring(
						dateStr.lastIndexOf('/') + 1, dateStr.length()));
		this.month = Integer.parseInt(
				dateStr.substring(0, dateStr.indexOf('/')));
		this.day = Integer.parseInt(
				dateStr.substring(dateStr.indexOf('/') + 1,
						dateStr.lastIndexOf('/')));
		this.dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH)
				.format(
						new SimpleDateFormat("yyyy-M-d").parse(
								String.format("%d-%d-%d",
										this.year, this.month, this.day)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object other) {
		return other instanceof Date
				? this.year == ((Date)other).year
						&& this.month == ((Date)other).month
						&& this.day == ((Date)other).day
				: false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return (
				this.dayOfWeek + ", " + (
					this.month == 1 ?
							"January"
					: this.month == 2 ?
							"February"
					: this.month == 3 ?
							"March"
					: this.month == 4 ?
							"April"
					: this.month == 5 ?
							"May"
					: this.month == 6 ?
							"June"
					: this.month == 7 ?
							"July"
					: this.month == 8 ?
							"August"
					: this.month == 9 ?
							"September"
					: this.month == 10 ?
							"October"
					: this.month == 11 ?
							"November"
					: "December"
				)

				+ " " + this.day + ", " + this.year
		);
	}
}
