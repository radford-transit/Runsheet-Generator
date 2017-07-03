package runsheet_generator;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class Settings {
	ArrayList<String> ignoredPositions = new ArrayList<String>();
	File exportFile;
	Path runsheetPath;
	boolean noShiftChanges;
	int firstShiftChangeHour;
	
	public Settings(File settingsFile) {
		
	}
}
