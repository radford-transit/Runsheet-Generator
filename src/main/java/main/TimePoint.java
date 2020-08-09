package main;

public class TimePoint implements Comparable<TimePoint> {
  /** Hour of the time point */
  public int hour;
  /** Minute of the time point */
  public int minute;

  /**
   * Constructs a TimePoint object
   *
   * @param timeStr A String representation of the time in the format HH:MM BM
   * @throws Exception
   */
  public TimePoint(String timeStr) throws Exception {
    // timeStr format: "HH:MM BM"
    String period = timeStr.substring(timeStr.indexOf(' ') + 1, timeStr.length());
    if (period.equals("AM"))
      this.hour = Integer.parseInt(timeStr.substring(0, timeStr.indexOf(':'))) % 12;
    else if (period.equals("PM"))
      this.hour = Integer.parseInt(timeStr.substring(0, timeStr.indexOf(':'))) % 12 + 12;
    else throw new Exception("Invalid time point ");
    this.minute =
        Integer.parseInt(timeStr.substring(timeStr.indexOf(':') + 1, timeStr.indexOf(' ')));
  }

  /**
   * Constructs a TimePoint object
   *
   * @param hour The hour of the TimePoint
   * @param minute The minute of the TimePoint
   */
  public TimePoint(int hour, int minute) {
    if (!(minute >= 0 && minute < 60)) System.out.println("ERROR: Invalid minute" + minute);

    this.hour = hour;
    this.minute = minute;
  }

  /**
   * Adds a preceding 0 to a String representing a single-digit integer
   *
   * @param n The integer
   */
  private String twoDigits(int n) {
    return n < 10 && n >= 0 ? "0" + n : "" + n;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return ((hour >= 24)
        ? (hour - 24 - 12 > 0)
            ? (hour - 24 - 12) + ":" + this.twoDigits(minute) + " PM"
            : (hour - 24) + ":" + this.twoDigits(minute) + " AM"
        : (hour - 12 > 0)
            ? (hour - 12) + ":" + this.twoDigits(minute) + " PM"
            : (hour) + ":" + this.twoDigits(minute) + " AM");
  }

  /** {@inheritDoc} */
  @Override
  public int compareTo(TimePoint other) {
    return ((this.hour < other.hour)
        ? -1
        : (this.hour == other.hour)
            ? (this.minute < other.minute) ? -1 : (this.minute == other.minute) ? 0 : 1
            : 1);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object other) {
    return this.hour == ((TimePoint) other).hour && this.minute == ((TimePoint) other).minute;
  }
}
