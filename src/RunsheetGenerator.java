import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RunsheetGenerator {
	public static void main(String[] args) throws Exception {
			ArrayList<String> ignoredPositions = new ArrayList<String>();
		ignoredPositions.add("Operations Supervisor");
		ignoredPositions.add("Meeting");
		ignoredPositions.add("Admin/Marketing");
		
		boolean noShiftChanges = false;
		
		Schedule schedule;
		
		if (noShiftChanges)
			schedule = new Schedule("EXPORT.CSV", new Date("2/15/2017"), ignoredPositions);
		else {
			int firstShiftChangeHour = 10;
			schedule = new Schedule("EXPORT.CSV", new Date("2/15/2017"), ignoredPositions, firstShiftChangeHour);
		}
			
		System.out.println(schedule);
		
		Runsheet runsheet = new Runsheet(schedule);

		FileOutputStream out = new FileOutputStream("runsheet.xlsx");
		runsheet.write(out);
		runsheet.close();
		out.close();
	}
}
