package ui;
import java.awt.event.ActionListener;

import javax.swing.*;

import swing_extensions.JRadioButtonList;

public class FirstShiftChangeSelectionPanel extends JPanel {
	// First shift change selection label
	private JLabel firstShiftChangeSelectionLabel = new JLabel(
			String.format(
					"<html><div style=\"width:%dpx;\">%s</div><html>",
					400,
					"<html>"
							+ " <b>Select hour of first shift change</b>"
							+ " <br>"
							+ " <br>"
							+ " Select the hour during which the first shift change occurs."
							+ "</html>"));
	// No shift changes check box
	public JCheckBox noShiftChangeCheckBox =
			new JCheckBox("There are no shift changes");
	// First shift change hour radio button list
	public JRadioButtonList radioButtons = new JRadioButtonList();
	// Radio button list goes in a scroll pane
	private JScrollPane scrollPane = new JScrollPane(this.radioButtons);
	
	/**
	 * Constructs a FirstShiftChangeSelectionPanel object
	 */
	public FirstShiftChangeSelectionPanel(
			ActionListener noShiftChangeCheckBoxListener) {
		super();
		
		// Add action listener
		this.noShiftChangeCheckBox.addActionListener(noShiftChangeCheckBoxListener);
		
		// Add components
		this.add(this.firstShiftChangeSelectionLabel);
		this.add(this.noShiftChangeCheckBox);
		this.add(this.scrollPane);
	}
	
	public void setShiftChangeListData(Object[] data) {
		this.radioButtons.setData(data);
		
		/* If there are no possible shift changes, select no shift changes check
		 * box and disable changing its selection state
		 */
		if (this.radioButtons.length() == 0) {
			this.noShiftChangeCheckBox.setSelected(true);
			this.noShiftChangeCheckBox.setEnabled(false);
		}
	}
}
