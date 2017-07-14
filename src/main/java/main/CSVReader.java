package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.csv.*;

public class CSVReader {
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
	
	// CSV file
	private static File csv;
	// CSV format
	private static CSVFormat csvFormat = 
			CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
	// File reader
	private static FileReader fileReader = null;
	// CSV parser
	private static CSVParser csvParser = null;
	// CSV records
	public static List<CSVRecord> csvRecords = null;
	
	/**
	 * Sets the CSV schedule file
	 * @param csv CSV schedule file
	 */
	public static void setCSVFile(File csv) {
		CSVReader.csv = csv;
		
		// Initialize CSV reader
		try {
			CSVReader.fileReader = new FileReader(CSVReader.csv);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("error: Schedule CSV file not found");
		}
		
		// Initialize CSV parser
		try {
			CSVReader.csvParser =
					new CSVParser(CSVReader.fileReader, CSVReader.csvFormat);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(
					"error: IOException when attempting to parse schedule CSV file");
		}
		
		// Initialize CSV records
		try {
			CSVReader.csvRecords = CSVReader.csvParser.getRecords();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(
					"error: IOException when attempting to parse CSV records");
		}
	}
	
	public static Date[] getDates() {
		ArrayList<Date> dates = new ArrayList<Date>();
		
		// Read the CSV file records from the second record to skip the header
		for (int i = 1; i < CSVReader.csvRecords.size(); i++) {
			CSVRecord csvRecord = CSVReader.csvRecords.get(i);
			
			// Add date if it's not already included in dates
			try {
				if (!dates.contains(new Date(csvRecord.get(Header.DATE))))
					dates.add(new Date(csvRecord.get(Header.DATE)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		// Sort dates
		Collections.sort(dates);
		
		return dates.toArray(new Date[dates.size()]);
	}
	
	/**
	 * Gets an array of non-route driving position Strings on the given date.
	 * @param date Date from which the positions will be listed
	 * @return An array of non-route driving position Strings on the given date
	 */
	public static String[] getNonRouteDrivingPositionsOnDate(Date date) {
		ArrayList<String> positions = new ArrayList<String>();
		
		// Read the CSV file records from the second record to skip the header
		for (int i = 1; i < CSVReader.csvRecords.size(); i++) {
			CSVRecord csvRecord = CSVReader.csvRecords.get(i);
			
			/*
			 *  Add position if it's on the provided date, its corresponding record
			 *  does not describe it as a route driving shift, and it isn't already
			 *  included in positions list
			 */
			try {
				if (new Date(csvRecord.get(Header.DATE)).equals(date)
						&& !CSVReader.recordDescribesRouteDrivingShift(csvRecord)
						&& !positions.contains(csvRecord.get(Header.POSITION_NAME)))
					positions.add(csvRecord.get(Header.POSITION_NAME));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		// Sort positions
		Collections.sort(positions);
		
		return positions.toArray(new String[positions.size()]);
	}
	
	public static boolean recordDescribesRouteDrivingShift(CSVRecord csvRecord) {
		return csvRecord.get(Header.PERIOD).length() == 1;
	}

	public static int[] getPossibleShiftChangeTimesOnDate(Date date) {
		ArrayList<Integer> shiftChangeTimes =
				new ArrayList<Integer>();
		
		// Read the CSV file records from the second record to skip the header
		for (int i = 1; i < CSVReader.csvRecords.size(); i++) {
			CSVRecord csvRecord = CSVReader.csvRecords.get(i);
			try {
				if (new Date(csvRecord.get(Header.DATE)).equals(date)
						&& CSVReader.recordDescribesRouteDrivingShift(csvRecord))
					shiftChangeTimes.add(
							new Integer(new TimePoint(csvRecord.get(Header.START_TIME)).hour));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}						
		}
		
		int[] shiftChangeTimesInts = new int[shiftChangeTimes.size()];
		for (int i = 0; i < shiftChangeTimes.size(); i++) {
			shiftChangeTimesInts[i] = shiftChangeTimes.get(i).intValue();
		}
		
		return shiftChangeTimesInts;
	}
}
