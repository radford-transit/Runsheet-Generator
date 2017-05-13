package main.java.runsheet_generator;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class Runsheet extends XSSFWorkbook {
	Sheet sheet = createSheet("Runsheet");
	
	// Cell styles
	private Map<String, CellStyle> styles = createStyles(this);

	/**
	 * Constructs a Runsheet object
	 * @param schedule A Schedule object to write the runsheet from
	 * @throws Exception
	 */
	public Runsheet(Schedule schedule) throws Exception {
		super();

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

		// Merge H3 and I3 for Shift Change title
		sheet.addMergedRegion(CellRangeAddress.valueOf("$H$3:$I$3"));

		for (int i = 0; i < headerValues.length; i++) {
			Cell headerCell = headersRow.createCell(i);
			headerCell.setCellValue(headerValues[i]);
			headerCell.setCellStyle(styles.get("header"));
		}

		int rowOffset = 3;
		
		writePeriodRow(rowOffset, schedule.routeDrivingShifts.get(0).period);
		rowOffset++;
		
		// Shift rows
		//int rowOffset = 3;
		if (schedule.shiftChanges.size() > 0)
			for (int i = 1 ; i < schedule.routeDrivingShifts.size(); i++) {
				if (i == 0) {
					writePeriodRow(rowOffset + i, schedule.routeDrivingShifts.get(i).period);
					rowOffset++;
				}
				else {
					if (schedule.routeDrivingShifts.get(i).time.start.hour >
								schedule.routeDrivingShifts.get(i - 1).time.start.hour) {
						for (int j = 0; j < schedule.shiftChanges.size(); j++) {
							// If shift hour is same as that of a shift change, include that shift change's info
							if (schedule.routeDrivingShifts.get(i).time.start.hour ==
									schedule.shiftChanges.get(j).hour) {
								writePeriodRow(rowOffset + i, schedule.shiftChanges.get(j));
								rowOffset++;
								break;
							}
						}		
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
			busCell.setCellValue("#");

			Cell positionCell = shiftRow.createCell(4);
			if (schedule.shiftChanges.size() > 0)
				if (!schedule.routeDrivingShifts.get(i).equals(
						schedule.lastShiftOnRoute(schedule.routeDrivingShifts.get(i).route)))
					positionCell.setCellStyle(styles.get("shiftA"));
			else positionCell.setCellStyle(styles.get("positionBold"));
			positionCell.setCellValue(schedule.routeDrivingShifts.get(i).name);

			Cell startTimeCell = shiftRow.createCell(5);
			startTimeCell.setCellStyle(styles.get("time"));
			startTimeCell.setCellValue(schedule.routeDrivingShifts.get(i).time.start.toString());

			Cell endTimeCell = shiftRow.createCell(6);
			endTimeCell.setCellStyle(styles.get("time"));
			endTimeCell.setCellValue(schedule.routeDrivingShifts.get(i).time.end.toString());

			Cell shiftChangeBeforeCell = shiftRow.createCell(7);
			shiftChangeBeforeCell.setCellStyle(styles.get("shiftA"));

			Cell shiftChangeAfterCell = shiftRow.createCell(8);
			shiftChangeAfterCell.setCellStyle(styles.get("shiftA"));
		}

		// Set 0th column width
		sheet.setColumnWidth(0, 500);
		// Set bus column width
		sheet.setColumnWidth(3, 1500);
		// Set time column widths
		for (int i = 5; i < 9; i++)
			sheet.setColumnWidth(i,  2000);


		// Autosize last name, first name, and route columns to fit text content
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(4);
	}
	
	/**
	 * Writes a period row on the runsheet
	 * @param periodRow Period row on the spreadsheet
	 * @param id Period or shift change ID
	 */
	private void writePeriodRow(Row periodRow, String id) {
		Cell periodLabelCell = periodRow.createCell(0);
		periodLabelCell.setCellStyle(styles.get("periodLabel"));
		periodLabelCell.setCellValue(id);
		
		for (int i = 1; i < 9; i++) {
			Cell periodEmptyCell = periodRow.createCell(i);
			periodEmptyCell.setCellStyle(styles.get("periodLabel"));
		}
	}

	/**
	 * Writes a period row on the runsheet
	 * @param row Row number on the spreadsheet
	 * @param period Period ID
	 */
	private void writePeriodRow(int row, char period) {
		Row periodRow = sheet.createRow(row);
		writePeriodRow(periodRow, "" + period);
	}
	
	/**
	 * Writes a period row on the runsheet
	 * @param row Row number on the runsheet
	 * @param shiftChange Shift change
	 */
	private void writePeriodRow(int row, ShiftChange shiftChange) {
		Row periodRow = sheet.createRow(row);
		writePeriodRow(periodRow, shiftChange.id.toString());
		Cell shiftChangeInfoCell = periodRow.createCell(7);
		shiftChangeInfoCell.setCellStyle(styles.get("shiftChangeInfo"));
		shiftChangeInfoCell.setCellValue(shiftChange.toString());
	}
	
	@SuppressWarnings("deprecation")
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
		style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.ALIGN_FILL);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(periodLabelFont);
		styles.put("periodLabel", style);

		// Shift change info cell style
		style = wb.createCellStyle();
		Font shiftChangeInfoFont = wb.createFont();
		shiftChangeInfoFont.setBold(true);
		shiftChangeInfoFont.setFontHeightInPoints((short) 8);
		style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.ALIGN_FILL);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(shiftChangeInfoFont);
		styles.put("shiftChangeInfo", style);

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
