package main;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EventListener;

public class Settings {
	// Date
	public static Date date;
	// Included positions
	public static ArrayList<String> includedPositions = new ArrayList<String>();
	// Export file
	public static Path exportFilePath = null;
	// First shift change hour
	public static int firstShiftChangeHour = -1;
	// Runsheet path
	public static Path runsheetPath = null;
	
	public Settings() {}
}
