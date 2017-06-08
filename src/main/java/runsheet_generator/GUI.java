package main.java.runsheet_generator;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.BorderLayout;

public class GUI {

	JFrame frame;

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextPane txtpnStep = new JTextPane();
		txtpnStep.setText("1. Log in to WhenToWork."
											+	"\n2. Click on 'SCHEDULE'"
											+ "\n3. Navigate to the day of of the runsheet you wish to make."
											+ "\n4. Click on 'Export' (in the top right corner)."
											+ "\n5. In the window that pops up, make sure ALL boxes are checked."
											+ "\n6. Click on 'Create Export File'."
											+ "\n7. Save the export file to a location you can easily remember (like your Downloads folder).");
		frame.getContentPane().add(txtpnStep, BorderLayout.CENTER);
	}

}
