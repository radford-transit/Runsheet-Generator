package main;
import ui.*;

import java.io.FileOutputStream;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class RunsheetGenerator {
	
	public static void main(String[] args) throws Exception {
		System.out.println(Thread.currentThread().getId());
		// Set look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		UI window = null;
		try {
			window = new UI();
			window.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Wait for user to finish
		while (!window.complete.get()) {}
		
		Schedule schedule = Settings.noShiftChanges
				? new Schedule(Settings.date, Settings.includedPositions)
				: new Schedule(Settings.date, Settings.includedPositions, Settings.firstShiftChange);
				
		Runsheet runsheet = new Runsheet(schedule);

		FileOutputStream out = new FileOutputStream(Settings.runsheetPath.toFile());
		runsheet.write(out);
		runsheet.close();
		out.close();
	}
}