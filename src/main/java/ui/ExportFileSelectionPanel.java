package ui;

import java.awt.event.ActionListener;
import java.nio.file.Path;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ExportFileSelectionPanel extends JPanel {
	// Export file selection label
	private JLabel label = new JLabel(
			String.format(
					"<html><div style=\"width:%dpx;\">%s</div><html>",
					400,
					"<html>"
							+ " <b>Select export file</b>"
							+ " <br>"
							+ " <br>"
							+ " This is the file you just saved from WhenToWork. Its name is probably something like 'EXPORT.csv'."
							+ "</html>"));
	// Export file selection text field
	public JTextField textField = new JTextField();
	// Export file selection button
	private JButton button = new JButton("Browse...");
	
	/**
	 * Constructs an ExportFileSelectionPabel object
	 * @param buttonListener ActionListener for the browse button
	 */
	public ExportFileSelectionPanel(ActionListener buttonListener) {
		super();
		
		// Text field settings
		this.textField.setEditable(false);
		this.textField.setColumns(30);
		
		// Button settings
		this.button.addActionListener(buttonListener);
		
		// Add components
		this.add(this.label);
		this.add(this.textField);
		this.add(this.button);
	}
	
	/**
	 * Shows a file chooser for selecting an export file. Returns the file path.
	 * @param currentFilePath The currently selected file. Can be null.
	 * @return the selected Path
	 */
	public static Path getFilePath(Path currentFilePath) {
		// File chooser settings
		JFileChooser fileChooser = new JFileChooser(
				// By default, point to user's Downloads folder
				System.getProperty("user.home")
				+ (System.getProperty("os.name").contains("Windows")
						? "\\" : "/")
				+ "Downloads");
						
		fileChooser.setDialogTitle("Select export file");
		fileChooser.setFileFilter(new FileNameExtensionFilter(
				"Comma-Separated Values Files (.csv)", "CSV", "csv"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		// If a new file path was selected, return that path
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			currentFilePath = fileChooser.getSelectedFile().toPath();
		// Otherwise, return the current file path
		return currentFilePath;
	}
}
