package ui;

import java.awt.event.ActionListener;

import javax.swing.*;

public class RunsheetPathSelectionPanel extends JPanel {
	// Runsheet path selection label
	private JLabel runsheetPathSelectionPanel = new JLabel(
			String.format(
					"<html><div style=\"width:%dpx;\">%s</div><html>",
					450,
					"<html>"
							+ " <b>Save runsheet</b>"
							+ " <br>"
							+ " <br>"
							+ " Set the location of the runsheet."
							+ "</html>"));
	// Runsheet path selection text field
	public JTextField textField = new JTextField();
	// Runsheet path selection button
	private JButton button = new JButton("Save To...");

	/**
	 * Constructs a RunsheetPathSelectionPanel object
	 */
	public RunsheetPathSelectionPanel(ActionListener buttonListener) {
		super();
		
		// Text field settings
		this.textField.setEditable(false);
		this.textField.setColumns(30);
		
		// Button settings
		this.button.addActionListener(buttonListener);
		
		// Add components
		this.add(this.runsheetPathSelectionPanel);
		this.add(this.textField);
	}
}
