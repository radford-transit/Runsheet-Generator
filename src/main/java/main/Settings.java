package main;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EventListener;

public class Settings {
	// Date
	public static Date date;
	// Included positions
	public static String[] includedPositions = new String[0];
	// Export file
	public static Path exportFilePath = null;
	// No shift changes
	public static boolean noShiftChanges = false;
	// First shift change
	public static ShiftChange firstShiftChange = null;
	// Runsheet path
	public static Path runsheetPath = null;
	
	public Settings() {}
	
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
