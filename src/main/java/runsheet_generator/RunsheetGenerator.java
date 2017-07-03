package runsheet_generator;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;

public class RunsheetGenerator {
	public static void main(String[] args) throws Exception {
		try {
			GUI window = new GUI();
			window.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Settings settings = new Settings(new File("/settings/settings.dat"));

		settings.ignoredPositions.add("Operations Supervisor");
		settings.ignoredPositions.add("Meeting");
		settings.ignoredPositions.add("Admin/Marketing");

		settings.noShiftChanges = false;
		settings.firstShiftChangeHour = 7;

		Schedule schedule = settings.noShiftChanges
				? new Schedule("EXPORT.CSV", new Date("4/7/2017"), settings.ignoredPositions)
				: new Schedule("EXPORT.CSV", new Date("4/7/2017"), settings.ignoredPositions, settings.firstShiftChangeHour);

		System.out.println(schedule);

		Runsheet runsheet = new Runsheet(schedule);

		FileOutputStream out = new FileOutputStream("runsheet.xlsx");
		runsheet.write(out);
		runsheet.close();
		out.close();
	}
}
