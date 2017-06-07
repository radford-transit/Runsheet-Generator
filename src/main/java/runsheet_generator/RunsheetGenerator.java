package main.java.runsheet_generator;

import java.io.File;
import java.io.FileOutputStream;

public class RunsheetGenerator {
	public static void main(String[] args) throws Exception {
		Settings settings = new Settings(new File("/settings/settings.dat"));
		
		settings.ignoredPositions.add("Operations Supervisor");
		settings.ignoredPositions.add("Meeting");
		settings.ignoredPositions.add("Admin/Marketing");

		settings.noShiftChanges = false;

<<<<<<< HEAD
		Schedule schedule;

		if (settings.noShiftChanges)
			schedule = new Schedule("EXPORT.CSV", new Date("2/23/2017"), settings.ignoredPositions);
		else {
			settings.firstShiftChangeHour = 14;
			schedule = new Schedule("EXPORT.CSV", new Date("2/23/2017"), settings.ignoredPositions, settings.firstShiftChangeHour);
		}
=======
		int firstShiftChangeHour = 7;

		Schedule schedule = noShiftChanges
				? new Schedule("EXPORT.CSV", new Date("4/27/2017"), ignoredPositions)
				: new Schedule("EXPORT.CSV", new Date("4/27/2017"), ignoredPositions, firstShiftChangeHour);
>>>>>>> origin/master

		System.out.println(schedule);

		Runsheet runsheet = new Runsheet(schedule);

		FileOutputStream out = new FileOutputStream("runsheet.xlsx");
		runsheet.write(out);
		runsheet.close();
		out.close();
	}
}
