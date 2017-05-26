package main.java.runsheet_generator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.*;

public class Schedule {
	Date date;

	ArrayList<RouteDrivingShift> routeDrivingShifts =
			new ArrayList<RouteDrivingShift>();
	ArrayList<NonRouteDrivingShift> nonRouteDrivingShifts =
			new ArrayList<NonRouteDrivingShift>();
	ArrayList<TrainingShift> trainingShifts =
			new ArrayList<TrainingShift>();

	// Shift changes
	ArrayList<ShiftChange> shiftChanges =
			new ArrayList<ShiftChange>();

	// WhenToWork schedule CSV column header values
	class Header {
		static final String SHIFT_ID = "Shift ID";
		static final String SCHEDULE_ID = "Schedule ID";
		static final String EMPLOYEE_NUMBER = "Employee Number";
		static final String POSITION_ID = "Position ID";
		static final String POSITION_NAME = "Position Name";
		static final String PERIOD = "Category";
		static final String DESCRIPTION = "Shift Description";
		static final String DATE = "Date";
		static final String START_TIME = "Start Time";
		static final String END_TIME = "End Time";
		static final String DURATION = "Duration";
		static final String DAY_OF_WEEK = "Day Of Week";
		static final String FULL_NAME = "Employee Name";
		static final String FIRST_NAME = "Employee First Name";
		static final String LAST_NAME = "Employee Last Name";
	}

	// WhenToWork schedule CSV column header mapping
	private static final String[] FILE_HEADER_MAPPING = {
		Header.SHIFT_ID, Header.SCHEDULE_ID,
		Header.EMPLOYEE_NUMBER, Header.POSITION_ID,
		Header.POSITION_NAME, Header.PERIOD,
		Header.DESCRIPTION, Header.DATE, Header.START_TIME,
		Header.END_TIME, Header.DURATION,
		Header.DAY_OF_WEEK, Header.FULL_NAME,
		Header.FIRST_NAME, Header.LAST_NAME
	};

	/**
	 * Constructs a Schedule object
	 * @param fileName Path of WhenToWork export file (CSV)
	 * @param date Date to get schedule from
	 * @param ignoredPositions Names of positions to not include in the schedule
	 * @throws Exception
	 */
	public Schedule(String fileName, Date date, List<String> ignoredPositions) throws Exception {
		this.date = date;

		// Create CSVFormat object. New line separates records.
		CSVFormat scheduleCSVFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

		// File reader
		FileReader scheduleCSVReader = null;
		try {
			scheduleCSVReader = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("error: Schedule CSV file not found");
		}
		// CSV parser
		CSVParser scheduleCSVParser = null;
		try {
			scheduleCSVParser = new CSVParser(scheduleCSVReader, scheduleCSVFormat);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error: IOException when attempting to parse schedule CSV file");
		}

		// Schedule CSV records
		List<CSVRecord> scheduleCSVRecords = null;
		try {
			scheduleCSVRecords = scheduleCSVParser.getRecords();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error: IOException when attempting to parse (scheduleCSVRecords");
		}

		// Read the CSV file records starting from the second record to skip the header
		for (int i = 1; i < scheduleCSVRecords.size(); i++) {
			CSVRecord scheduleCSVRecord = scheduleCSVRecords.get(i);

			// Only add shifts with selected date and not in ignored list
			if (new Date(scheduleCSVRecord.get(Header.DATE)).equals(this.date)
					&& !ignoredPositions.contains(scheduleCSVRecord.get(Header.POSITION_NAME))) {
				/* If period is one character (as opposed to empty),
				 * then it's a route driving shift
				 */
				this.add(
						scheduleCSVRecord.get(Header.PERIOD).length() == 1 ?
								new RouteDrivingShift(
										scheduleCSVRecord.get(Header.POSITION_NAME),
												scheduleCSVRecord.get(Header.PERIOD).charAt(0),
										new TimePeriod(
												new TimePoint(scheduleCSVRecord.get(Header.START_TIME)),
												new TimePoint(scheduleCSVRecord.get(Header.END_TIME))),
										new Employee(
												scheduleCSVRecord.get(Header.LAST_NAME),
												scheduleCSVRecord.get(Header.FIRST_NAME)))
								: scheduleCSVRecord.get(Header.POSITION_NAME).equals("Training") ?
										new TrainingShift(
												scheduleCSVRecord.get(Header.POSITION_NAME),
												new TimePeriod(
														new TimePoint(scheduleCSVRecord.get(Header.START_TIME)),
														new TimePoint(scheduleCSVRecord.get(Header.END_TIME))),
												new Employee(
														scheduleCSVRecord.get(Header.LAST_NAME),
														scheduleCSVRecord.get(Header.FIRST_NAME)))
										: new NonRouteDrivingShift(
												scheduleCSVRecord.get(Header.POSITION_NAME),
												scheduleCSVRecord.get(Header.DESCRIPTION),
												new TimePeriod(
														new TimePoint(scheduleCSVRecord.get(Header.START_TIME)),
														new TimePoint(scheduleCSVRecord.get(Header.END_TIME))),
												new Employee(
														scheduleCSVRecord.get(Header.LAST_NAME),
														scheduleCSVRecord.get(Header.FIRST_NAME))));
			}
		}
	}

	/**
	 * Constructs a Schedule object
	 * @param fileName Path of WhenToWork export file (CSV)
	 * @param date Date to get schedule from
	 * @param ignoredPositions Names of positions to not include in the schedule
	 * @param firstShiftChangeHour The hour of the first shift change for the day
	 * @throws Exception
	 */
	public Schedule(String fileName, Date date,
			List<String> ignoredPositions, int firstShiftChangeHour) throws Exception {
		this(fileName, date, ignoredPositions);
		this.shiftChanges = shiftChanges(firstShiftChangeHour);
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

		else
			for (int i = 0; i < this.routeDrivingShifts.size(); i++)
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
		this.nonRouteDrivingShifts.add(shift);
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
	 * Gets a list of shift changes, starting with one during the
	 * provided shift change hour 
	 * @param firstShiftChangeHour The hour of the first shift change
	 * @return A list of shift changes, starting with one during the provided
	 * hour
	 */
	private ArrayList<ShiftChange> shiftChanges(int firstShiftChangeHour) {
		ArrayList<ShiftChange> shiftChanges = new ArrayList<ShiftChange>();

		if (this.routeDrivingShifts.size() == 0) return shiftChanges;
		else if (this.routeDrivingShifts.size() == 1) {
			if (this.routeDrivingShifts.get(0).time.start.hour >= firstShiftChangeHour)
				shiftChanges.add(
						new ShiftChange(
								this.routeDrivingShifts.get(0).time.start.hour,
								new ShiftChangeID(
										this.routeDrivingShifts.get(0).period, 1)));
		}
		else { // this.routeDrivingShifts.size() > 1
			if (this.routeDrivingShifts.get(0).time.start.hour >= firstShiftChangeHour)
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
				if (this.routeDrivingShifts.get(i).time.start.hour == firstShiftChangeHour
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
				else if (this.routeDrivingShifts.get(i).time.start.hour > firstShiftChangeHour) {
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
		scheduleStr = scheduleStr + "\n-------- SHIFT CHANGES --------\n";
		for (int i = 0; i < this.shiftChanges.size(); i++)
			scheduleStr = scheduleStr + shiftChanges.get(i) + "\n";

		return scheduleStr;
	}
}
