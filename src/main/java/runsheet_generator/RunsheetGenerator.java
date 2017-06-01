package main.java.runsheet_generator;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class RunsheetGenerator {
	public static void main(String[] args) throws Exception {
			ArrayList<String> ignoredPositions = new ArrayList<String>();
		ignoredPositions.add("Operations Supervisor");
		ignoredPositions.add("Meeting");
		ignoredPositions.add("Admin/Marketing");

		boolean noShiftChanges = false;

		int firstShiftChangeHour = 7;

		Schedule schedule = noShiftChanges
				? new Schedule("EXPORT.CSV", new Date("4/27/2017"), ignoredPositions)
				: new Schedule("EXPORT.CSV", new Date("4/27/2017"), ignoredPositions, firstShiftChangeHour);

		System.out.println(schedule);

		Runsheet runsheet = new Runsheet(schedule);

		FileOutputStream out = new FileOutputStream("runsheet.xlsx");
		runsheet.write(out);
		runsheet.close();
		out.close();
	}
}
