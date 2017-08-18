package main;
import ui.*;

import java.awt.Desktop;
import java.io.FileOutputStream;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class RunsheetGenerator {

	public static void main(String[] args) throws Exception {
		// Set look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		// Start user interface
		UI window = null;
		try {
			window = new UI();
			window.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Wait for user to finish
		while (!window.complete.get()) {}

		// Make schedule
		Schedule schedule = Settings.firstShiftChange == null
				? new Schedule(Settings.date, Settings.includedPositions)
				: new Schedule(Settings.date, Settings.includedPositions, Settings.firstShiftChange);


		// Make runsheet
		Runsheet runsheet = new Runsheet(schedule);

		FileOutputStream out = new FileOutputStream(Settings.runsheetPath.toFile());
		runsheet.write(out);
		runsheet.close();
		out.close();

		// Open runsheet
		Desktop.getDesktop().open(Settings.runsheetPath.toFile());
	}
}
