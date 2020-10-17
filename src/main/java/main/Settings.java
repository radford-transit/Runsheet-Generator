package main;

import java.nio.file.Path;

public class Settings {
  /** Date of the runsheet */
  public static Date date;
  /** Included non-route positions on the runsheet */
  public static String[] includedPositions = new String[0];
  /** Path of the WhenToWork export file */
  public static Path exportFilePath = null;
  /** The first shift change */
  public static ShiftChange firstShiftChange = null;
  /** Route driving shifts that end at the shop */
  public static RouteDrivingShift[] shiftsEndingAtShop = new RouteDrivingShift[0];
  /** Path of the directory to save the runsheet to */
  public static Path runsheetPath = null;

  /** Prints the settings data to the console. */
  public static void print() {
    System.out.println("Date:                      " + date.toString());
    System.out.print("Included positions:        ");
    for (int i = 0; i < includedPositions.length; i++) {
      if (i == 0) System.out.println(includedPositions[i]);
      else System.out.println("                           " + includedPositions[i]);
    }
    System.out.print("Shifts ending at the shop:        ");
    for (int i = 0; i < shiftsEndingAtShop.length; i++) {
      if (i == 0) System.out.println(shiftsEndingAtShop[i]);
      else System.out.println("                                  " + shiftsEndingAtShop[i]);
    }
    System.out.println(
        "Export file path:          "
            + exportFilePath.toString()
            + "\n"
            + "First shift change:   			"
            + firstShiftChange.toString()
            + "\n"
            + "Runsheet path:             "
            + runsheetPath);
  }
}
