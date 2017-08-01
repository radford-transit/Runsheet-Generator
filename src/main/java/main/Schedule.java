package main;

import java.util.*;

import org.apache.commons.csv.*;

public class Schedule {
	/** Date of the schedule */
	public Date date;

	/** The schedule's route driving shifts */
	public ArrayList<RouteDrivingShift> routeDrivingShifts =
			new ArrayList<RouteDrivingShift>();
	/** The schedule's non-route driving shifts */
	public ArrayList<NonRouteDrivingShift> nonRouteDrivingShifts =
			new ArrayList<NonRouteDrivingShift>();
	/** The schedule's training shifts */
	public ArrayList<TrainingShift> trainingShifts =
			new ArrayList<TrainingShift>();

	/** The set of shift changes */
	public ArrayList<ShiftChange> shiftChanges =
			new ArrayList<ShiftChange>();

	/**
	 * Constructs a Schedule object
	 * @param fileName Path of WhenToWork export file (CSV)
	 * @param date Date to get schedule from
	 * @param includedPositions Names of positions to include in the schedule
	 * @throws Exception
	 */
	public Schedule(Date date, String[] includedPositions) throws Exception {
		this.date = date;

		// Read the CSV file records starting from the second record to skip the header
		for (int i = 1; i < CSVReader.csvRecords.size(); i++) {
			CSVRecord csvRecord = CSVReader.csvRecords.get(i);

			// Only add shifts with selected date and in include list
			if (new Date(csvRecord.get(CSVReader.Header.DATE)).equals(this.date)
					&& Arrays.asList(includedPositions).contains(
							csvRecord.get(CSVReader.Header.POSITION_NAME))) {
				/* If period is one character (as opposed to empty),
				 * then it's a route driving shift
				 */
				this.add(
						CSVReader.recordDescribesRouteDrivingShift(csvRecord)
								? new RouteDrivingShift(
										csvRecord.get(CSVReader.Header.POSITION_NAME),
										csvRecord.get(CSVReader.Header.PERIOD).charAt(0),
										new TimePeriod(
												new TimePoint(csvRecord.get(
														CSVReader.Header.START_TIME)),
												new TimePoint(csvRecord.get(
														CSVReader.Header.END_TIME))),
										new Employee(
												csvRecord.get(CSVReader.Header.LAST_NAME),
												csvRecord.get(CSVReader.Header.FIRST_NAME)))
								: csvRecord.get(CSVReader.Header.POSITION_NAME).equals("Training")
										? new TrainingShift(
												csvRecord.get(CSVReader.Header.DESCRIPTION),
												new TimePeriod(
														new TimePoint(csvRecord.get(
																CSVReader.Header.START_TIME)),
														new TimePoint(csvRecord.get(
																CSVReader.Header.END_TIME))),
												new Employee(
														csvRecord.get(CSVReader.Header.LAST_NAME),
														csvRecord.get(CSVReader.Header.FIRST_NAME)))
										: new NonRouteDrivingShift(
												csvRecord.get(CSVReader.Header.POSITION_NAME),
												csvRecord.get(CSVReader.Header.DESCRIPTION),
												new TimePeriod(
														new TimePoint(csvRecord.get(
																CSVReader.Header.START_TIME)),
														new TimePoint(csvRecord.get(
																CSVReader.Header.END_TIME))),
												new Employee(
														csvRecord.get(CSVReader.Header.LAST_NAME),
														csvRecord.get(CSVReader.Header.FIRST_NAME))));
			}
		}
	}

	/**
	 * Constructs a Schedule object
	 * @param fileName Path of WhenToWork export file (CSV)
	 * @param date Date to get schedule from
	 * @param includedPositions Names of positions not include in the schedule
	 * @param firstShiftChangeHour The hour of the first shift change for the day
	 * @throws Exception
	 */
	public Schedule(Date date,
			String[] includedPositions, ShiftChange firstShiftChange) throws Exception {
		this(date, includedPositions);
		this.shiftChanges = shiftChanges(firstShiftChange);
	}

	/**
	 * Adds a shift to the Schedule
	 * @param shift The shift object to add to the Schedule
	 * @throws Exception
	 */
	private void add(Shift shift) throws Exception {
		if (shift instanceof RouteDrivingShift)
			this.add((RouteDrivingShift) shift);
		else if (shift instanceof TrainingShift)
			this.add((TrainingShift) shift);
		else this.add((NonRouteDrivingShift) shift);
	}

	/**
	 * Adds a route driving shift to the Schedule
	 * @param shift The route driving shift to add to the Schedule
	 * @throws Exception
	 */
	private void add(RouteDrivingShift shift) throws Exception {
		if (this.routeDrivingShifts.size() == 0)
			this.routeDrivingShifts.add(shift);

		else if (this.routeDrivingShifts.size() == 1)
			if (shift.compareTo(this.routeDrivingShifts.get(0)) == -1)
				this.routeDrivingShifts.add(0, shift);
			else
				this.routeDrivingShifts.add(shift);

		else for (int i = 0; i < this.routeDrivingShifts.size(); i++)
			if (shift.compareTo(this.routeDrivingShifts.get(i)) == -1) {
				this.routeDrivingShifts.add(i, shift);
				break;
			}

			else if (i == this.routeDrivingShifts.size() - 1) {
				this.routeDrivingShifts.add(shift);
				break;
			}
	}

	/**
	 * Adds a non-route driving shift to the Schedule
	 * @param shift The non-route driving shift to add to the Schedule
	 */
	private void add(NonRouteDrivingShift shift) {
		if (this.nonRouteDrivingShifts.size() == 0)
			this.nonRouteDrivingShifts.add(shift);

		else if (this.nonRouteDrivingShifts.size() == 1)
			if (shift.compareTo(this.nonRouteDrivingShifts.get(0)) < 0)
				this.nonRouteDrivingShifts.add(0, shift);
			else
				this.nonRouteDrivingShifts.add(shift);

		else for (int i = 0; i < this.nonRouteDrivingShifts.size(); i++)
			if (shift.compareTo(this.nonRouteDrivingShifts.get(i)) < 0) {
				this.nonRouteDrivingShifts.add(i, shift);
				break;
			}

			else if (i == this.nonRouteDrivingShifts.size() - 1) {
				this.nonRouteDrivingShifts.add(shift);
				break;
			}
	}

	/**
	 * Adds a training shift to the Schedule
	 * @param shift The training shift to add to the Schedule
	 */
	private void add(TrainingShift shift) {
		if (this.trainingShifts.size() == 0)
			this.trainingShifts.add(shift);

		else if (this.trainingShifts.size() == 1)
			if (shift.compareTo(this.trainingShifts.get(0)) == -1)
				this.trainingShifts.add(0, shift);
			else
				this.trainingShifts.add(shift);

		else
			for (int i = 0; i < this.trainingShifts.size(); i++)
				if (shift.compareTo(this.trainingShifts.get(i)) == -1) {
					this.trainingShifts.add(i, shift);
					break;
				}

				else if (i == this.trainingShifts.size() - 1) {
					this.trainingShifts.add(shift);
					break;
				}
	}

	/**
	 * Gets a list of shift changes, starting with the provided one.
	 * @param firstShiftChange The first shift change
	 * @return A list of shift changes, starting with the provided one
	 */
	private ArrayList<ShiftChange> shiftChanges(ShiftChange firstShiftChange) {
		ArrayList<ShiftChange> shiftChanges = new ArrayList<ShiftChange>();

		if (this.routeDrivingShifts.size() == 0) return shiftChanges;
		else if (this.routeDrivingShifts.size() == 1) {
			if (this.routeDrivingShifts.get(0).time.start.hour >= firstShiftChange.hour)
				shiftChanges.add(
						new ShiftChange(
								this.routeDrivingShifts.get(0).time.start.hour,
								new ShiftChangeID(
										this.routeDrivingShifts.get(0).period, 1)));
		}
		else { // this.routeDrivingShifts.size() > 1
			if (this.routeDrivingShifts.get(0).time.start.hour >= firstShiftChange.hour)
				// Add first shift change
				shiftChanges.add(
						new ShiftChange(
								this.routeDrivingShifts.get(0).time.start.hour,
								new ShiftChangeID(
										this.routeDrivingShifts.get(0).period,
										1)));

			int nthShiftChangeInPeriod = 1;

			char currentPeriod = this.routeDrivingShifts.get(0).period;

			for (int i = 1; i < this.routeDrivingShifts.size(); i++) {
				if (this.routeDrivingShifts.get(i).time.start.hour == firstShiftChange.hour
						&& this.routeDrivingShifts.get(i).time.start.hour !=
						this.routeDrivingShifts.get(i - 1).time.start.hour) {
					currentPeriod = this.routeDrivingShifts.get(i).period;
					shiftChanges.add(
							new ShiftChange(
									this.routeDrivingShifts.get(i).time.start.hour,
									new ShiftChangeID(
											this.routeDrivingShifts.get(i).period,
											1)));
				}
				else if (this.routeDrivingShifts.get(i).time.start.hour > firstShiftChange.hour) {
					if (this.routeDrivingShifts.get(i).time.start.hour >
							this.routeDrivingShifts.get(i - 1).time.start.hour) {
						if (this.routeDrivingShifts.get(i).period == currentPeriod)
							nthShiftChangeInPeriod++;
						else {
							currentPeriod = this.routeDrivingShifts.get(i).period;
							nthShiftChangeInPeriod = 1;
						}
						shiftChanges.add(
								new ShiftChange(
										this.routeDrivingShifts.get(i).time.start.hour,
										new ShiftChangeID(
												this.routeDrivingShifts.get(i).period,
												nthShiftChangeInPeriod)));
					}
				}
			}
		}

		return shiftChanges;
	}

	/**
	 * Gets the last shift on the provided route
	 * @param route The route to get the last shift from
	 * @return The last shift on the provided route
	 * @throws Exception
	 */
	public RouteDrivingShift lastShiftOnRoute(Route route) throws Exception {
		for (int i = this.routeDrivingShifts.size() - 1; i > 0; i--) {
			if (route.equals(this.routeDrivingShifts.get(i).route))
				return this.routeDrivingShifts.get(i);
		}
		throw new Exception();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String scheduleStr = "\n-------- Route Driving Shifts --------\n";
		for (int i = 0; i < this.routeDrivingShifts.size(); i++) {
			scheduleStr = scheduleStr + routeDrivingShifts.get(i) + "\n";
			if (i < this.routeDrivingShifts.size() - 1
					&& this.routeDrivingShifts.get(i).time.start.hour <
					this.routeDrivingShifts.get(i + 1).time.start.hour)
				scheduleStr = scheduleStr + "\n---\n";
  }
		scheduleStr = scheduleStr + "\n-------- Non-Route Driving Shifts --------\n";
		for (int i = 0; i < this.nonRouteDrivingShifts.size(); i++)
			scheduleStr = scheduleStr + nonRouteDrivingShifts.get(i) + "\n";
		scheduleStr = scheduleStr + "\n-------- Training Shifts --------\n";
		for (int i = 0; i < this.trainingShifts.size(); i++)
			scheduleStr = scheduleStr + trainingShifts.get(i) + "\n";
		scheduleStr = scheduleStr + "\n-------- Shift Changes --------\n";
		for (int i = 0; i < this.shiftChanges.size(); i++)
			scheduleStr = scheduleStr + shiftChanges.get(i) + "\n";

		return scheduleStr;
	}
}
