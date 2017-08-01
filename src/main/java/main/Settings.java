package main;

import java.nio.file.Path;

public class Settings {
	/** Date of the runsheet */
	public static Date date;
	/** Included non-route positions on the runsheet */
	public static String[] includedPositions = new String[0];
	/** Path of the WhenToWork export file */
	public static Path exportFilePath = null;
	/** Whether there are any shift changes on the runsheet */
	public static boolean noShiftChanges = false;
	/** The first shift change */
	public static ShiftChange firstShiftChange = null;
	/** Path of the directory to save the runsheet to */
	public static Path runsheetPath = null;
	
	/**
	 * Prints the settings data to the console.
	 */
	public static void print() {
		System.out.println(
				"Date:                      " + date.toString());
		System.out.print("Included positions:        ");
		for (int i = 0; i < includedPositions.length; i++) {
			if (i == 0)
				System.out.println(includedPositions[i]);
			else System.out.println("                           " + includedPositions[i]);
		}
		System.out.println(
				"Export file path:          " + exportFilePath.toString() + "\n" +
				"First shift change:   			" + firstShiftChange.toString() + "\n" +
				"Runsheet path:             " + runsheetPath
		);
	}
}
