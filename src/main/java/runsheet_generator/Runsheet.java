
package main.java.runsheet_generator;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import java.awt.Color;

public class Runsheet extends XSSFWorkbook {
	private Schedule schedule;
	private XSSFSheet sheet = createSheet("Runsheet");
	private Map<String, XSSFCellStyle> styles;

	private XSSFPrintSetup printSetup = sheet.getPrintSetup();

	/**
	 * Constructs a Runsheet object
	 * @param schedule A Schedule object to write the runsheet from
	 * @throws Exception
	 */
	public Runsheet(Schedule schedule) throws Exception {
		super();
		
		this.schedule = schedule;

		styles = createStyles(this);

		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(false);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);

		// Current row being written
		int currentRow = 0;
		
		// Title row
		Row titleRow = sheet.createRow(currentRow);
		titleRow.setHeightInPoints(19);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue("Radford Transit");
		titleCell.setCellStyle(styles.get("title"));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$I$1"));

		// Date row
		currentRow++;
		Row dateRow = sheet.createRow(currentRow);
		dateRow.setHeightInPoints(17);
		Cell dateCell = dateRow.createCell(0);
		dateCell.setCellValue(this.schedule.date.toString());
		dateCell.setCellStyle(styles.get("date"));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$I$2"));

		// Headers row
		currentRow += 2;
		Row headersRow = sheet.createRow(currentRow);
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

		// Write headers
		for (int i = 0; i < headerValues.length; i++) {
			Cell headerCell = headersRow.createCell(i);
			headerCell.setCellValue(headerValues[i]);
			headerCell.setCellStyle(styles.get("header"));
		}

		// Merge cells H4 and I4 for shift change header
		sheet.addMergedRegion(CellRangeAddress.valueOf("$H$4:$I$4"));

		// Shift rows
		currentRow++;
		for (int i = 0 ; i < this.schedule.routeDrivingShifts.size(); i++) {
			if (i == 0) {
				currentRow += i;
				writePeriodRow(currentRow, this.schedule.routeDrivingShifts.get(i).period);
				currentRow -= i;
				currentRow++;
			}
			else {
				if (this.schedule.routeDrivingShifts.get(i).time.start.hour >
							this.schedule.routeDrivingShifts.get(i - 1).time.start.hour) {
					currentRow += i;
					writePeriodRow(currentRow, this.schedule.routeDrivingShifts.get(i).period);
					currentRow -= i;
					currentRow++;
				}
			}

			currentRow += i;
			Row shiftRow = sheet.createRow(currentRow);
			currentRow -= i;

			Cell checkCell = shiftRow.createCell(0);
			checkCell.setCellStyle(styles.get("shiftA"));

			Cell lastNameCell = shiftRow.createCell(1);
			lastNameCell.setCellStyle(styles.get("shiftA"));
			lastNameCell.setCellValue(this.schedule.routeDrivingShifts.get(i).employee.name.last);

			Cell firstNameCell = shiftRow.createCell(2);
			firstNameCell.setCellStyle(styles.get("shiftA"));
			firstNameCell.setCellValue(this.schedule.routeDrivingShifts.get(i).employee.name.first);

			Cell busCell = shiftRow.createCell(3);
			busCell.setCellStyle(styles.get("bus"));

			Cell positionCell = shiftRow.createCell(4);
			if (!this.schedule.routeDrivingShifts.get(i).equals(
					this.schedule.lastShiftOnRoute(this.schedule.routeDrivingShifts.get(i).route)))
				positionCell.setCellStyle(styles.get("shiftA"));
			else positionCell.setCellStyle(styles.get("positionBold"));
			positionCell.setCellValue(this.schedule.routeDrivingShifts.get(i).name);

			Cell startTimeCell = shiftRow.createCell(5);
			startTimeCell.setCellStyle(styles.get("time"));
			startTimeCell.setCellValue(this.schedule.routeDrivingShifts.get(i).time.start.toString());

			Cell endTimeCell = shiftRow.createCell(6);
			endTimeCell.setCellStyle(styles.get("time"));
			endTimeCell.setCellValue(this.schedule.routeDrivingShifts.get(i).time.end.toString());

			Cell shiftChangeBeforeCell = shiftRow.createCell(7);
			shiftChangeBeforeCell.setCellStyle(styles.get("shiftA"));

			Cell shiftChangeAfterCell = shiftRow.createCell(8);
			shiftChangeAfterCell.setCellStyle(styles.get("shiftA"));

			// If loop has reached end of list, currentRow is at the end of the runsheet
			if (i == this.schedule.routeDrivingShifts.size() - 1)
				currentRow += i;
		}

		// Set 0th column width
		sheet.setColumnWidth(0, 750);
		// Set bus column width
		sheet.setColumnWidth(3, 1750);
		// Set time column widths
		for (int i = 5; i < 9; i++)
			sheet.setColumnWidth(i, 2304);


		// Autosize last name, first name, and route columns to fit text content
		if (sheet.getColumnWidth(1) > 4608)
			sheet.autoSizeColumn(1);
		else sheet.setColumnWidth(1, 4608);
		if (sheet.getColumnWidth(2) > 4608)
			sheet.autoSizeColumn(2);
		else sheet.setColumnWidth(2, 4608);
		if (sheet.getColumnWidth(4) > 6400)
			sheet.autoSizeColumn(4);
		else sheet.setColumnWidth(4, 6400);

		// Write bold comment cell
		currentRow++;
		Row boldCommentRow = sheet.createRow(currentRow);
		Cell boldCommentCell = boldCommentRow.createCell(8);
		boldCommentCell.setCellStyle(styles.get("boldComment"));
		boldCommentCell.setCellValue("*Bold shifts end at the shop");

		setPrintSetup();
	}

	/**
	 * Writes a period row on the runsheet
	 * @param row Row number on the spreadsheet
	 * @param period Period ID
	 */
	private void writePeriodRow(int row, char period) {
		Row periodRow = sheet.createRow(row);

		Cell cell = periodRow.createCell(0);
		cell.setCellStyle(styles.get("periodLabel"));
		cell.setCellValue("" + period);

		for (int i = 1; i < 8; i++) {
			cell = periodRow.createCell(i);
			cell.setCellStyle(styles.get("shiftChange"));
		}

		// Merge cells H and I for shift change info
		sheet.addMergedRegion(CellRangeAddress.valueOf("$H$" + (row + 1)+ ":$I$" + (row + 1)));
	}

	private static Map<String, XSSFCellStyle> createStyles(
			Workbook wb) {
		Map<String, XSSFCellStyle> styles =
				new HashMap<String, XSSFCellStyle>();

		XSSFCellStyle style;

		// Title cell style
		style = (XSSFCellStyle) wb.createCellStyle();
		Font titleFont = wb.createFont();
		titleFont.setFontName("Cambria");
		titleFont.setBold(true);
		titleFont.setFontHeightInPoints((short) 15);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(titleFont);
		styles.put("title", style);

		// Date cell style
		style = (XSSFCellStyle) wb.createCellStyle();
		Font dateFont = wb.createFont();
		dateFont.setFontName("Cambria");
		dateFont.setBold(true);
		dateFont.setFontHeightInPoints((short) 13);
		dateFont.setColor(Font.COLOR_RED);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(dateFont);
		styles.put("date", style);

		// Header cells style
		style = (XSSFCellStyle) wb.createCellStyle();
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial Narrow");
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 10);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(headerFont);
		styles.put("header", style);

		// Period label cell style
		style = (XSSFCellStyle) wb.createCellStyle();
		Font periodLabelFont = wb.createFont();
		periodLabelFont.setFontName("Arial");
		periodLabelFont.setBold(true);
		periodLabelFont.setFontHeightInPoints((short) 11);
		style.setFillForegroundColor(new XSSFColor(new Color(211, 211, 211)));
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(periodLabelFont);
		styles.put("periodLabel", style);

		// Shift change cell style
		style = (XSSFCellStyle) wb.createCellStyle();
		Font shiftChangeFont = wb.createFont();
		shiftChangeFont.setFontName("Arial");
		shiftChangeFont.setBold(true);
		shiftChangeFont.setFontHeightInPoints((short) 8);
		style.setFillForegroundColor(new XSSFColor(new Color(211, 211, 211)));
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(shiftChangeFont);
		styles.put("shiftChange", style);

		// First name, last name, position name, and per-employee shift change cell style
		style = (XSSFCellStyle) wb.createCellStyle();
		Font shiftFontA = wb.createFont();
		shiftFontA.setFontName("Arial");
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
		style = (XSSFCellStyle) wb.createCellStyle();
		Font busFont = wb.createFont();
		busFont.setFontName("Arial");
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
		style = (XSSFCellStyle) wb.createCellStyle();
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(busFont);
		styles.put("positionBold", style);

		// Time cell style
		style = (XSSFCellStyle) wb.createCellStyle();
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(shiftFontA);
		styles.put("time", style);

		// Bold comment cell style
		style = (XSSFCellStyle) wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.RIGHT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(busFont);
		styles.put("boldComment", style);

		return styles;
	}

	private void setPrintSetup() {
		this.sheet.setMargin(Sheet.TopMargin, 0.0);
		this.sheet.setMargin(Sheet.BottomMargin, 0.0);
		this.sheet.setMargin(Sheet.LeftMargin, 0.0);
		this.sheet.setMargin(Sheet.RightMargin, 0.0);

		printSetup.setFitHeight((short) 1);
		printSetup.setFitWidth((short) 1);
		printSetup.setNoColor(false);
	}
}
