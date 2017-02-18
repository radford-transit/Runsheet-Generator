import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class Runsheet extends XSSFWorkbook {
	Sheet sheet = createSheet("Runsheet");
	
	public Runsheet(Schedule schedule) throws Exception {
		super();
		
		Map<String, CellStyle> styles = createStyles(this);
		
		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(false);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);
		
		// Title row
		Row titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(19);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue("Radford Transit");
		titleCell.setCellStyle(styles.get("title"));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$I$1"));
		
		// Date row
		Row dateRow = sheet.createRow(1);
		dateRow.setHeightInPoints(17);
		Cell dateCell = dateRow.createCell(0);
		dateCell.setCellValue(schedule.date.toString());
		dateCell.setCellStyle(styles.get("date"));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$I$2"));
		
		// Headers row
		Row headersRow = sheet.createRow(2);
		headersRow.setHeightInPoints((short) 15);
		String[] headerValues = {
				"",
				"Last Name",
				"First Name",
				"Bus #",
				"Route",
				"Start Time",
				"End Time",
				"Shift Change",
				""
		};
		
		
		for (int i = 0; i < headerValues.length; i++) {
			Cell headerCell = headersRow.createCell(i);
			headerCell.setCellValue(headerValues[i]);
			headerCell.setCellStyle(styles.get("header"));
		}
		
		// Shift rows
		int rowOffset = 3;
		for (int i = 0 ; i < schedule.routeDrivingShifts.size(); i++) {
			if (i == 0) {
				writePeriodRow(rowOffset + i, schedule.routeDrivingShifts.get(i).period);
				rowOffset++;
			}
			else {
				if (schedule.routeDrivingShifts.get(i).route.id <
							schedule.routeDrivingShifts.get(i - 1).route.id &&
						schedule.routeDrivingShifts.get(i).time.start.hour >
							schedule.routeDrivingShifts.get(i - 1).time.start.hour) {
					rowOffset++;
					Row periodRow = sheet.createRow(rowOffset + i);
				}
			}
				
			Row shiftRow = sheet.createRow(rowOffset + i);
			
			Cell checkCell = shiftRow.createCell(0);
			checkCell.setCellStyle(styles.get("shiftA"));
			
			Cell lastNameCell = shiftRow.createCell(1);
			lastNameCell.setCellStyle(styles.get("shiftA"));
			lastNameCell.setCellValue(schedule.routeDrivingShifts.get(i).employee.name.last);
			
			Cell firstNameCell = shiftRow.createCell(2);
			firstNameCell.setCellStyle(styles.get("shiftA"));
			firstNameCell.setCellValue(schedule.routeDrivingShifts.get(i).employee.name.first);
			
			Cell busCell = shiftRow.createCell(3);
			busCell.setCellStyle(styles.get("bus"));
			busCell.setCellValue(".");
			
			Cell positionCell = shiftRow.createCell(4);
			if (!schedule.routeDrivingShifts.get(i).equals(
					schedule.lastShiftOnRoute(schedule.routeDrivingShifts.get(i).route)))
				positionCell.setCellStyle(styles.get("shiftA"));
			else positionCell.setCellStyle(styles.get("positionBold"));
			positionCell.setCellValue(schedule.routeDrivingShifts.get(i).name);
			
			Cell startTimeCell = shiftRow.createCell(5);
			startTimeCell.setCellStyle(styles.get("time"));
			startTimeCell.setCellValue(schedule.routeDrivingShifts.get(i).time.start.toString());
			
			Cell endTimeCell = shiftRow.createCell(6);
			endTimeCell.setCellValue(schedule.routeDrivingShifts.get(i).time.end.toString());
			
			Cell shiftChangeBeforeCell = shiftRow.createCell(7);
			shiftChangeBeforeCell.setCellStyle(styles.get("shiftB"));
			
			Cell shiftChangeAfterCell = shiftRow.createCell(8);
			shiftChangeAfterCell.setCellStyle(styles.get("shiftA"));
		}
	}
	
	private void writePeriodRow(int row, char period) {
		Row periodRow = sheet.createRow(row);
		
	}
	
	private static Map<String, CellStyle> createStyles(
			Workbook wb) {
		Map<String, CellStyle> styles =
				new HashMap<String, CellStyle>();
		
		// Title cell style
		CellStyle style = wb.createCellStyle();
		Font titleFont = wb.createFont();
		titleFont.setBold(true);
		titleFont.setFontHeightInPoints((short) 15);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(titleFont);
		styles.put("title", style);
		
		// Date cell style
		style = wb.createCellStyle();
		Font dateFont = wb.createFont();
		dateFont.setBold(true);
		dateFont.setFontHeightInPoints((short) 13);
		dateFont.setColor(Font.COLOR_RED);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(dateFont);
		styles.put("date", style);
		
		// Header cells style
		style = wb.createCellStyle();
		Font headerFont = wb.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 10);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(headerFont);
		styles.put("header", style);
		
		// Period label cell style
		style = wb.createCellStyle();
		Font periodLabelFont = wb.createFont();
		periodLabelFont.setBold(true);
		periodLabelFont.setFontHeightInPoints((short) 11);
		style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(periodLabelFont);
		styles.put("periodLabel", style);
		
		// Shift change cell style
		style = wb.createCellStyle();
		Font shiftChangeFont = wb.createFont();
		shiftChangeFont.setBold(true);
		shiftChangeFont.setFontHeightInPoints((short) 8);
		style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(shiftChangeFont);
		styles.put("shiftChange", style);
		
		// First name, last name, position name, and per-employee shift change cell style
		style = wb.createCellStyle();
		Font shiftFontA = wb.createFont();
		shiftFontA.setFontHeightInPoints((short) 10);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(shiftFontA);
		styles.put("shiftA", style);
		
		// Bus number cell style
		style = wb.createCellStyle();
		Font busFont = wb.createFont();
		busFont.setFontHeightInPoints((short) 10);
		busFont.setBold(true);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(busFont);
		styles.put("bus", style);
		
		// Bold position name cell style
		style = wb.createCellStyle();
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(busFont);
		styles.put("positionBold", style);
		
		// Time cell style
		style = wb.createCellStyle();
		shiftFontA.setFontHeightInPoints((short) 10);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(shiftFontA);
		styles.put("time", style);
		
		return styles;
	}
}
