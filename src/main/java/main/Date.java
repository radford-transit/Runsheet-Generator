package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Date implements Comparable {
  /** The year */
  public int year;
  /** The month */
  public int month;
  /** The day */
  public int day;
  /** The day of the week */
  public String dayOfWeek;

  /**
   * Constructs a Date object
   *
   * @param dateStr A String representation of the date
   * @throws ParseException
   */
  public Date(String dateStr) throws ParseException {
    this.year = Integer.parseInt(dateStr.substring(dateStr.lastIndexOf('/') + 1, dateStr.length()));
    this.month = Integer.parseInt(dateStr.substring(0, dateStr.indexOf('/')));
    this.day =
        Integer.parseInt(dateStr.substring(dateStr.indexOf('/') + 1, dateStr.lastIndexOf('/')));
    this.dayOfWeek =
        new SimpleDateFormat("EEEE", Locale.ENGLISH)
            .format(
                new SimpleDateFormat("yyyy-M-d")
                    .parse(String.format("%d-%d-%d", this.year, this.month, this.day)));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object other) {
    return other instanceof Date
        ? this.year == ((Date) other).year
            && this.month == ((Date) other).month
            && this.day == ((Date) other).day
        : false;
  }

  /** {@inheritDoc} */
  @Override
  public int compareTo(Object other) {
    return other instanceof Date
        ? this.year < ((Date) other).year
            ? -1
            : this.year > ((Date) other).year
                ? 1
                : this.month < ((Date) other).month
                    ? -1
                    : this.month > ((Date) other).month
                        ? 1
                        : this.day < ((Date) other).day ? -1 : this.day > ((Date) other).day ? 1 : 0
        : 0;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return (this.dayOfWeek
        + ", "
        + (this.month == 1
            ? "January"
            : this.month == 2
                ? "February"
                : this.month == 3
                    ? "March"
                    : this.month == 4
                        ? "April"
                        : this.month == 5
                            ? "May"
                            : this.month == 6
                                ? "June"
                                : this.month == 7
                                    ? "July"
                                    : this.month == 8
                                        ? "August"
                                        : this.month == 9
                                            ? "September"
                                            : this.month == 10
                                                ? "October"
                                                : this.month == 11 ? "November" : "December")
        + " "
        + this.day
        + ", "
        + this.year);
  }

  /**
   * Gets a text representation of the Date which works as a file name prefix.
   *
   * @return a file name prefix representing the Date
   */
  public String toFileNamePrefixString() {
    return ((this.month <= 9 ? "0" : "")
        + this.month
        + "-"
        + (this.day <= 9 ? "0" : "")
        + this.day
        + "-"
        + this.year);
  }
}
