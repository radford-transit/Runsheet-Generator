package ui;
import javax.swing.*;

import main.TimePeriod;

import swing_extensions.JRadioButtonList;

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
	// First shift change hour radio button list
	public JRadioButtonList radioButtons = new JRadioButtonList();
	// Radio button list goes in a scroll pane
	private JScrollPane scrollPane = new JScrollPane(this.radioButtons);
	
	/**
	 * Constructs a FirstShiftChangeSelectionPanel object
	 */
	public FirstShiftChangeSelectionPanel() {
		super();
		
		// Add components
		this.add(this.firstShiftChangeSelectionLabel);
		this.add(this.noShiftChangeCheckBox);
		this.add(this.scrollPane);
	}
	
	public void setShiftChangeHoursListData(String[] data) {
		this.radioButtons.setData(data);
	}
}
