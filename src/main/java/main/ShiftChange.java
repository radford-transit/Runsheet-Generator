package main;

public class ShiftChange implements Comparable {
  /** Start time of the shift change */
  public TimePoint startTime;
  /** End time of the shift change */
  public TimePoint endTime;
  /** ID of the shift change */
  public ShiftChangeID id;

  /**
   * Constructs a ShiftChange object
   *
   * @param startTime The start time of the shift change
   * @param endTime The end time of the shift change
   * @param id The ShiftChangeID to assign to the shift change
   */
  public ShiftChange(TimePoint startTime, TimePoint endTime, ShiftChangeID id) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.id = id;
  }

  /**
   * Constructs a ShiftChange object
   *
   * @param startHour The start hour of the shift change
   * @param startMinute The start minute of the shift change
   * @param endHour The end hour of the shift change
   * @param endMinute The end minute of the shift change
   * @param id The ShiftChangeID to assign to the shift change
   */
  public ShiftChange(int startHour, int startMinute, int endHour, int endMinute, ShiftChangeID id) {
    this(new TimePoint(startHour, startMinute), new TimePoint(endHour, endMinute), id);
  }

  /**
   * Constructs a ShiftChange object
   *
   * @param time The time of the shift change
   * @param id The ShiftChangeID to assign to the shift change
   */
  public ShiftChange(TimePoint time, ShiftChangeID id) {
    this(time, new TimePoint(time.hour, 50), id);
  }

  /**
   * Constructs a ShiftChange object
   *
   * @param hour The hour of the shift change
   * @param id The ShiftChangeID to assign to the shift change
   */
  public ShiftChange(int hour, ShiftChangeID id) {
    this(new TimePoint(hour, 10), id);
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return startTime.toString() + " - " + endTime.toString();
  }

  /** {@inheritDoc} */
  @Override
  public int compareTo(Object other) {
    if (other instanceof ShiftChange)
      return this.startTime.compareTo(((ShiftChange) other).startTime);
    return -2;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object other) {
    if (other instanceof ShiftChange)
      return this.startTime.equals(((ShiftChange) other).startTime)
          && this.endTime.equals(((ShiftChange) other).endTime)
          && this.id == ((ShiftChange) other).id
      ;
    return false;
  }
}
