package main;

import java.awt.Color;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

public class Runsheet extends XSSFWorkbook {
  private Schedule schedule;
  private XSSFSheet sheet = createSheet("Runsheet");
  private Map<String, XSSFCellStyle> styles;

  /**
   * Constructs a Runsheet object
   *
   * @param schedule A Schedule object to write the runsheet from
   * @throws Exception
   */
  public Runsheet(Schedule schedule) throws Exception {
    super();

    this.schedule = schedule;

    this.styles = createStyles(this);

    // Set 0th column width
    this.sheet.setColumnWidth(0, 750);
    // Set bus column width
    this.sheet.setColumnWidth(3, 1750);
    // Set time column widths
    for (int i = 5; i < 9; i++) this.sheet.setColumnWidth(i, 2304);

    // Current row being written
    int currentRow = 0;

    currentRow =
        (
        // Write credit note
        this.writeCreditNote(
            // Write Bold Comment
            this.writeBoldComment(
                // Write training shift rows
                this.writeTrainingShiftRows(
                    // Write non-route driving shift rows
                    this.writeNonRouteDrivingShiftRows(
                        // Write route driving shift rows
                        this.writeRouteDrivingShiftRows(
                            // Write headers row
                            this.writeHeadersRow(
                                // Write date row
                                this.writeDateRow(
                                    // Write title row
                                    this.writeTitleRow(currentRow)))))))));

    // Autosize last name, first name, and route columns to fit text content
    this.autosizeColumns();

    this.setPrintSetup();
  }

  /**
   * Writes the title row of the runsheet.
   *
   * @param row The number of the title row
   * @return The number of the first row after the title row
   */
  private int writeTitleRow(int row) {
    // Title row
    Row titleRow = this.sheet.createRow(row);
    titleRow.setHeightInPoints(19);
    Cell titleCell = titleRow.createCell(0);
    titleCell.setCellValue("Radford Transit");
    titleCell.setCellStyle(this.styles.get("title"));
    this.sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$I$1"));

    return row + 1;
  }

  /**
   * Writes the date row of the runsheet.
   *
   * @param row The number of the date row
   * @return The number of the first row after the date row
   */
  private int writeDateRow(int row) {
    Row dateRow = this.sheet.createRow(row);
    dateRow.setHeightInPoints(17);
    Cell dateCell = dateRow.createCell(0);
    dateCell.setCellValue(this.schedule.date.toString());
    dateCell.setCellStyle(this.styles.get("date"));
    this.sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$I$2"));

    return row + 2;
  }

  /**
   * Writes a header row to the runsheet.
   *
   * @param row The number of the header row
   * @return The number of the first row after the header row
   */
  private int writeHeadersRow(int row) {
    Row headersRow = sheet.createRow(row);
    headersRow.setHeightInPoints((short) 15);
    String[] headerValues = {
      "", "Last Name", "First Name", "Bus #", "Route", "Start Time", "End Time", "Shift Change", ""
    };

    // Write headers
    for (int i = 0; i < headerValues.length; i++) {
      Cell headerCell = headersRow.createCell(i);
      headerCell.setCellValue(headerValues[i]);
      headerCell.setCellStyle(styles.get("header"));
    }

    // Merge cells H4 and I4 for shift change header
    this.sheet.addMergedRegion(CellRangeAddress.valueOf("$H$4:$I$4"));

    return row + 1;
  }

  /**
   * Writes a route driving shift row to the runsheet.
   *
   * @param row The number of the shift row
   * @param shift The route driving shift to be written
   * @throws Exception
   */
  private void writeShiftRow(Row row, RouteDrivingShift shift) throws Exception {
    Cell checkCell = row.createCell(0);
    checkCell.setCellStyle(this.styles.get("shiftA"));

    Cell lastNameCell = row.createCell(1);
    lastNameCell.setCellStyle(this.styles.get("shiftA"));
    lastNameCell.setCellValue(shift.employee.name.last);

    Cell firstNameCell = row.createCell(2);
    firstNameCell.setCellStyle(this.styles.get("shiftA"));
    firstNameCell.setCellValue(shift.employee.name.first);

    Cell busCell = row.createCell(3);
    busCell.setCellStyle(this.styles.get("bus"));

    Cell positionCell = row.createCell(4);
    if (!shift.equals(this.schedule.lastShiftOnRoute(shift.route)))
      positionCell.setCellStyle(this.styles.get("shiftA"));
    else positionCell.setCellStyle(this.styles.get("positionBold"));
    positionCell.setCellValue(shift.name);

    Cell startTimeCell = row.createCell(5);
    startTimeCell.setCellStyle(this.styles.get("time"));
    startTimeCell.setCellValue(shift.time.start.toString());

    Cell endTimeCell = row.createCell(6);
    endTimeCell.setCellStyle(this.styles.get("time"));
    endTimeCell.setCellValue(shift.time.end.toString());

    Cell shiftChangeBeforeCell = row.createCell(7);
    shiftChangeBeforeCell.setCellStyle(this.styles.get("shiftA"));

    Cell shiftChangeAfterCell = row.createCell(8);
    shiftChangeAfterCell.setCellStyle(this.styles.get("shiftA"));
  }

  /**
   * Writes a non-route driving shift to the runsheet.
   *
   * @param row The number of the shift row
   * @param shift The non-route driving shift to be written
   */
  private void writeShiftRow(Row row, NonRouteDrivingShift shift) {
    Cell checkCell = row.createCell(0);
    checkCell.setCellStyle(this.styles.get("shiftA"));

    Cell lastNameCell = row.createCell(1);
    lastNameCell.setCellStyle(this.styles.get("shiftA"));
    lastNameCell.setCellValue(shift.employee.name.last);

    Cell firstNameCell = row.createCell(2);
    firstNameCell.setCellStyle(this.styles.get("shiftA"));
    firstNameCell.setCellValue(shift.employee.name.first);

    Cell busCell = row.createCell(3);
    busCell.setCellStyle(this.styles.get("bus"));

    Cell positionCell = row.createCell(4);
    positionCell.setCellStyle(this.styles.get("shiftA"));
    positionCell.setCellValue(shift.description);

    Cell startTimeCell = row.createCell(5);
    startTimeCell.setCellStyle(this.styles.get("time"));
    startTimeCell.setCellValue(shift.time.start.toString());

    Cell endTimeCell = row.createCell(6);
    endTimeCell.setCellStyle(this.styles.get("time"));
    endTimeCell.setCellValue(shift.time.end.toString());

    Cell shiftChangeBeforeCell = row.createCell(7);
    shiftChangeBeforeCell.setCellStyle(this.styles.get("shiftA"));

    Cell shiftChangeAfterCell = row.createCell(8);
    shiftChangeAfterCell.setCellStyle(this.styles.get("shiftA"));
  }

  /**
   * Writes a training shift row to the runsheet.
   *
   * @param row The number of the shift row
   * @param shift The training shift to be written
   */
  private void writeShiftRow(Row row, TrainingShift shift) {
    Cell checkCell = row.createCell(0);
    checkCell.setCellStyle(this.styles.get("shiftA"));

    Cell lastNameCell = row.createCell(1);
    lastNameCell.setCellStyle(this.styles.get("shiftA"));
    lastNameCell.setCellValue(shift.employee.name.last);

    Cell firstNameCell = row.createCell(2);
    firstNameCell.setCellStyle(this.styles.get("shiftA"));
    firstNameCell.setCellValue(shift.employee.name.first);

    Cell busCell = row.createCell(3);
    busCell.setCellStyle(this.styles.get("bus"));

    Cell positionCell = row.createCell(4);
    positionCell.setCellStyle(this.styles.get("shiftA"));
    positionCell.setCellValue(shift.name);

    Cell startTimeCell = row.createCell(5);
    startTimeCell.setCellStyle(this.styles.get("time"));
    startTimeCell.setCellValue(shift.time.start.toString());

    Cell endTimeCell = row.createCell(6);
    endTimeCell.setCellStyle(this.styles.get("time"));
    endTimeCell.setCellValue(shift.time.end.toString());

    Cell shiftChangeBeforeCell = row.createCell(7);
    shiftChangeBeforeCell.setCellStyle(this.styles.get("shiftA"));

    Cell shiftChangeAfterCell = row.createCell(8);
    shiftChangeAfterCell.setCellStyle(this.styles.get("shiftA"));
  }

  /**
   * Writes a series of route driving shift rows to the runsheet.
   *
   * @param currentRow The number of the first row to write to
   * @return the first row after the shift rows
   * @throws Exception
   */
  private int writeRouteDrivingShiftRows(int currentRow) throws Exception {
    if (!this.schedule.routeDrivingShifts.isEmpty()) {
      for (int i = 0; i < this.schedule.routeDrivingShifts.size(); i++) {
        if (i == 0) {
          currentRow += i;
          currentRow =
              writePeriodRow(currentRow, "" + this.schedule.routeDrivingShifts.get(i).period);
          currentRow -= i;
        } else if (this.schedule.routeDrivingShifts.get(i).time.start.hour
                > this.schedule.routeDrivingShifts.get(i - 1).time.start.hour
            && Runsheet.hourIsInShiftChanges(
                this.schedule.routeDrivingShifts.get(i).time.start.hour,
                this.schedule.shiftChanges)) {
          currentRow += i;
          currentRow =
              writeShiftChangeRow(
                  currentRow,
                  Runsheet.shiftChangeAtHour(
                      this.schedule.routeDrivingShifts.get(i).time.start.hour,
                      this.schedule.shiftChanges));
          currentRow -= i;
        }

        // Write shift row
        currentRow += i;
        Row shiftRow = this.sheet.createRow(currentRow);
        currentRow -= i;

        this.writeShiftRow(shiftRow, this.schedule.routeDrivingShifts.get(i));

        /* If loop has reached end of list, currentRow is at the end of the
         * non-route driving shifts section of the runsheet
         */
        if (i == this.schedule.routeDrivingShifts.size() - 1) currentRow += i;
      }

      return currentRow + 1;
    }
    return currentRow;
  }

  /**
   * Writes a series of non-route driving shift rows to the runsheet.
   *
   * @param currentRow The number of the first row to write to
   * @return the first row after the shift rows
   */
  private int writeNonRouteDrivingShiftRows(int currentRow) {
    if (!this.schedule.nonRouteDrivingShifts.isEmpty()) {
      for (int i = 0; i < this.schedule.nonRouteDrivingShifts.size(); i++) {
        if (i == 0
            || (i > 0
                && !this.schedule
                    .nonRouteDrivingShifts
                    .get(i)
                    .name
                    .equals(this.schedule.nonRouteDrivingShifts.get(i - 1).name))) {
          currentRow += i;
          currentRow =
              this.writePeriodRow(currentRow, this.schedule.nonRouteDrivingShifts.get(i).name);
          currentRow -= i;
        }

        // Write shift row
        currentRow += i;
        Row shiftRow = this.sheet.createRow(currentRow);
        currentRow -= i;

        this.writeShiftRow(shiftRow, this.schedule.nonRouteDrivingShifts.get(i));

        /* If loop has reached end of list, currentRow is at the end of the
         * non-route driving shifts section of the runsheet
         */
        if (i == this.schedule.nonRouteDrivingShifts.size() - 1) currentRow += i;
      }

      return currentRow + 1;
    }
    return currentRow;
  }

  /**
   * Writes a series of training shift rows to the runsheet.
   *
   * @param currentRow The number of the first row to write to
   * @return the first row after the shift rows
   */
  private int writeTrainingShiftRows(int currentRow) {
    if (!this.schedule.trainingShifts.isEmpty()) {
      currentRow = this.writePeriodRow(currentRow, "Training");

      for (int i = 0; i < this.schedule.trainingShifts.size(); i++) {
        // Write shift row
        currentRow += i;
        Row shiftRow = this.sheet.createRow(currentRow);
        currentRow -= i;

        this.writeShiftRow(shiftRow, this.schedule.trainingShifts.get(i));

        /* If loop has reached end of list, currentRow is at the end of the
         * training shifts section of the runsheet
         */
        if (i == this.schedule.trainingShifts.size() - 1) currentRow += i;
      }

      return currentRow + 1;
    }
    return currentRow;
  }

  /**
   * Writes the comment about bold shifts to the runsheet.
   *
   * @param row The number of the row to write to
   * @return the first row after the bold comment row
   */
  private int writeBoldComment(int row) {
    Row boldCommentRow = this.sheet.createRow(row);
    Cell boldCommentCell = boldCommentRow.createCell(8);
    boldCommentCell.setCellStyle(this.styles.get("boldComment"));
    boldCommentCell.setCellValue("*Bold shifts end at the shop");

    return row + 1;
  }

  /**
   * Write the note crediting the author.
   *
   * @param row The number of the row to write to
   * @return the first row after the bold comment row
   */
  private int writeCreditNote(int row) {
    Row creditNoteRow1 = this.sheet.createRow(row);
    Cell creditNoteRow1Cell = creditNoteRow1.createCell(0);
    creditNoteRow1Cell.setCellStyle(this.styles.get("creditNote"));
    creditNoteRow1Cell.setCellValue("Created by Zack Beach for Radford Transit");

    row += 1;

    Row creditNoteRow2 = this.sheet.createRow(row);
    Cell creditNoteRow2Cell = creditNoteRow2.createCell(0);
    creditNoteRow2Cell.setCellStyle(this.styles.get("creditNote"));
    creditNoteRow2Cell.setCellValue("github.com/zbeach");

    return row + 1;
  }

  /** Autosizes the runsheet's columns where needed. */
  private void autosizeColumns() {
    if (this.sheet.getColumnWidth(1) > 4608) this.sheet.autoSizeColumn(1);
    else this.sheet.setColumnWidth(1, 4608);
    if (this.sheet.getColumnWidth(2) > 4608) this.sheet.autoSizeColumn(2);
    else this.sheet.setColumnWidth(2, 4608);
    if (this.sheet.getColumnWidth(4) > 6400) this.sheet.autoSizeColumn(4);
    else this.sheet.setColumnWidth(4, 6400);
  }

  /**
   * Writes a period row on the runsheet
   *
   * @param row Row number on the spreadsheet
   * @param period Period ID
   * @return the first row after the period row
   */
  private int writePeriodRow(int row, String period) {
    Row periodRow = this.sheet.createRow(row);

    Cell cell = periodRow.createCell(0);
    cell.setCellStyle(this.styles.get("periodLabel"));
    cell.setCellValue(period);

    for (int i = 1; i < 8; i++) {
      cell = periodRow.createCell(i);
      cell.setCellStyle(this.styles.get("shiftChange"));
    }

    // Merge cells H and I for shift change info
    this.sheet.addMergedRegion(CellRangeAddress.valueOf("$H$" + (row + 1) + ":$I$" + (row + 1)));

    return row + 1;
  }

  /**
   * Writes a shift change row on the runsheet
   *
   * @param row Row number on the spreadsheet
   * @param shiftChange Shift change to write
   * @return the first row after the period row
   */
  private int writeShiftChangeRow(int row, ShiftChange shiftChange) {
    Row shiftChangeRow = this.sheet.createRow(row);

    Cell cell = shiftChangeRow.createCell(0);
    cell.setCellStyle(this.styles.get("periodLabel"));
    if (shiftChange.id.number < 2) cell.setCellValue("" + shiftChange.id.period);
    else cell.setCellValue(shiftChange.id.toString());

    for (int i = 1; i < 7; i++) {
      cell = shiftChangeRow.createCell(i);
      cell.setCellStyle(this.styles.get("shiftChange"));
    }

    cell = shiftChangeRow.createCell(7);
    cell.setCellStyle(this.styles.get("shiftChange"));
    cell.setCellValue(shiftChange.toString());

    // Merge cells H and I for shift change info
    this.sheet.addMergedRegion(CellRangeAddress.valueOf("$H$" + (row + 1) + ":$I$" + (row + 1)));

    return row + 1;
  }

  /**
   * Tells whether the provided hour is the hour of one of the shift changes
   *
   * @param hour The hour to check against the shift changes
   * @param shiftChanges The list of shift changes
   * @return true if the provided hour is the hour of one of the shift changes, or false otherwise
   */
  private static boolean hourIsInShiftChanges(int hour, ArrayList<ShiftChange> shiftChanges) {
    for (int i = 0; i < shiftChanges.size(); i++)
      if (shiftChanges.get(i).startTime.hour == hour) return true;
    return false;
  }

  /**
   * Gets the shift change at the provided hour
   *
   * @param hour The hour of the shift change
   * @param shiftChanges The list of shift changes
   * @return the shift change at the provided hour
   */
  private static ShiftChange shiftChangeAtHour(int hour, ArrayList<ShiftChange> shiftChanges) {
    for (int i = 0; i < shiftChanges.size(); i++)
      if (shiftChanges.get(i).startTime.hour == hour) return shiftChanges.get(i);
    return null;
  }

  /**
   * Makes the styles for the runsheet.
   *
   * @param wb The workbook to work with
   * @return The styles for the runsheet
   */
  private static Map<String, XSSFCellStyle> createStyles(Workbook wb) {
    Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();

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

    // Credit note cell style
    style = (XSSFCellStyle) wb.createCellStyle();
    Font creditNoteFont = wb.createFont();
    creditNoteFont.setFontName("Arial");
    creditNoteFont.setBold(false);
    creditNoteFont.setFontHeightInPoints((short) 8);
    creditNoteFont.setColor(new XSSFColor(new Color(77, 77, 77)).getIndex());
    style.setAlignment(HorizontalAlignment.LEFT);
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    style.setFont(creditNoteFont);
    styles.put("creditNote", style);

    return styles;
  }

  /** Sets the print setup for the runsheet document. */
  private void setPrintSetup() {
    PrintSetup printSetup = this.sheet.getPrintSetup();
    printSetup.setLandscape(false);
    this.sheet.setFitToPage(true);
    this.sheet.setHorizontallyCenter(true);

    this.sheet.setMargin(Sheet.TopMargin, 0.0);
    this.sheet.setMargin(Sheet.BottomMargin, 0.0);
    this.sheet.setMargin(Sheet.LeftMargin, 0.0);
    this.sheet.setMargin(Sheet.RightMargin, 0.0);

    printSetup.setFitHeight((short) 1);
    printSetup.setFitWidth((short) 1);
    printSetup.setNoColor(false);
  }
}
