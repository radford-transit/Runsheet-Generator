package ui;

import java.awt.event.*;

import javax.swing.event.ListSelectionListener;

public class MainPanels {
	InstructionsPanel instructionsPanel = new InstructionsPanel();
	ExportFileSelectionPanel exportFileSelectionPanel;
	DateSelectionPanel dateSelectionPanel;
	PositionsSelectionPanel positionsSelectionPanel;
	FirstShiftChangeSelectionPanel firstShiftChangeSelectionPanel = 
			new FirstShiftChangeSelectionPanel();
	RunsheetPathSelectionPanel runsheetPathSelectionPanel;

	/**
	 * Constructs a MainPanels object
	 * @param exportFileSelectionPanelButtonListener Button listener for the
	 * export file selection panel
	 */
	public MainPanels(
			ActionListener exportFileSelectionPanelButtonListener,
			ItemListener dateSelectionPanelComboBoxListener,
			ListSelectionListener positionsSelectionPanelListListener,
			ActionListener runsheetPathSelectionPanelButtonListener) {
		this.exportFileSelectionPanel = 
				new ExportFileSelectionPanel(exportFileSelectionPanelButtonListener);
		this.dateSelectionPanel =
				new DateSelectionPanel(dateSelectionPanelComboBoxListener);
		this.positionsSelectionPanel =
				new PositionsSelectionPanel(positionsSelectionPanelListListener);
		this.runsheetPathSelectionPanel = 
				new RunsheetPathSelectionPanel(
						runsheetPathSelectionPanelButtonListener);
	}
}
