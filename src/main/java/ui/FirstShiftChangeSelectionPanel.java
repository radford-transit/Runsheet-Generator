package ui;
import javax.swing.*;

public class FirstShiftChangeSelectionPanel extends JPanel {
	// First shift change selection label
	private JLabel firstShiftChangeSelectionLabel = new JLabel(
			String.format(
					"<html><div style=\"width:%dpx;\">%s</div><html>",
					450,
					"<html>"
							+ " <b>Select hour of first shift change</b>"
							+ " <br>"
							+ " <br>"
							+ " Select the hour during which the first shift change occurs."
							+ "</html>"));
	// No shift changes check box
	private JCheckBox noShiftChangeCheckBox =
			new JCheckBox("There are no shift changes");
	
	/**
	 * Constructs a FirstShiftChangeSelectionPanel object
	 */
	public FirstShiftChangeSelectionPanel() {
		super();
		
		// Add components
		this.add(this.firstShiftChangeSelectionLabel);
		this.add(this.noShiftChangeCheckBox);
	}
}
