package ui;

import java.awt.event.*;

public class MainPanels {
  InstructionsPanel instructionsPanel = new InstructionsPanel();
  ExportFileSelectionPanel exportFileSelectionPanel;
  DateSelectionPanel dateSelectionPanel;
  PositionsSelectionPanel positionsSelectionPanel;
  FirstShiftChangeSelectionPanel firstShiftChangeSelectionPanel;
  RunsheetPathSelectionPanel runsheetPathSelectionPanel;

  /**
   * Constructs a MainPanels object
   *
   * @param exportFileSelectionPanelButtonListener Button listener for the export file selection
   *     panel
   */
  public MainPanels(
      ActionListener exportFileSelectionPanelButtonListener,
      ItemListener dateSelectionPanelComboBoxListener,
      ActionListener positionsSelectionPanelActionListener,
      ActionListener noFirstShiftChangeCheckBoxListener,
      ActionListener runsheetPathSelectionPanelButtonListener) {
    this.exportFileSelectionPanel =
        new ExportFileSelectionPanel(exportFileSelectionPanelButtonListener);
    this.dateSelectionPanel = new DateSelectionPanel(dateSelectionPanelComboBoxListener);
    this.positionsSelectionPanel =
        new PositionsSelectionPanel(positionsSelectionPanelActionListener);
    this.firstShiftChangeSelectionPanel =
        new FirstShiftChangeSelectionPanel(noFirstShiftChangeCheckBoxListener);
    this.runsheetPathSelectionPanel =
        new RunsheetPathSelectionPanel(runsheetPathSelectionPanelButtonListener);
  }
}
