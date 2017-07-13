package ui;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import swing_extensions.JCheckBoxList;

public class PositionsSelectionPanel extends JPanel {
	// Positions selection label
	private JLabel positionsSelectionLabel = new JLabel(
			String.format(
					"<html><div style=\"width:%dpx;\">%s</div><html>",
					450,
					"<html>"
							+ " <b>Select positions</b>"
							+ " <br>"
							+ " <br>"
							+ " Select the non-route positions you wish to include on the"
							+ " runsheet. Route positions are included automatically."
							+ "</html>"));
	// Positions selection check box list
	public JCheckBoxList positionsList = new JCheckBoxList();
	// Check box list goes in a scroll pane
	private JScrollPane scrollPane = new JScrollPane(this.positionsList);
	
	/**
	 * Constructs a PositionsSelectionsPanel object
	 */
	public PositionsSelectionPanel(
			ListSelectionListener positionsSelectionListener) {
		super();
		
		// Add components
		this.add(this.positionsSelectionLabel);
		this.add(this.scrollPane);
	}
	
	/**
	 * Sets data in positions list and selects all elements.
	 * @param positions Array of position Strings
	 */
	public void setPositionsListData(String[] positions) {
		// Set list data
		this.positionsList.setData(positions);		
		// Select all list elements
		//this.positionsList.selectAll();
	}
}
