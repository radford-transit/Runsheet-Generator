package ui;

import java.awt.event.*;

public class MainPanels {
  InstructionsPanel instructionsPanel = new InstructionsPanel();
  ExportFileSelectionPanel exportFileSelectionPanel;
  DateSelectionPanel dateSelectionPanel;
  PositionsSelectionPanel positionsSelectionPanel;
  FirstShiftChangeSelectionPanel firstShiftChangeSelectionPanel;
  EndShiftsSelectionPanel endShiftsSelectionPanel;
  RunsheetPathSelectionPanel runsheetPathSelectionPanel;

  /**
   * Constructs a MainPanels object
   *
   * @param exportFileSelectionPanelButtonListener Button listener for the export file selection
   *     panel
   * @param dateSelectionPanelActionListener Action listener for the data selection panel
   * @param positionsSelectionPanelActionListener Action listener for the positions selection panel
   * @param noFirstShiftChangeCheckBoxListener Check box listener for the "no first shift change"
   *     check box
   * @param endShiftsSelectionPanelActionListener Action listener for the end shifts selection panel
   * @param runsheetPathSelectionPanelButtonListener Button listener for the runsheet path selection
   *     panel
   */
  public MainPanels(
      ActionListener exportFileSelectionPanelButtonListener,
      ActionListener dateSelectionPanelActionListener,
      ActionListener positionsSelectionPanelActionListener,
      ActionListener noFirstShiftChangeCheckBoxListener,
      ActionListener endShiftsSelectionPanelActionListener,
      ActionListener runsheetPathSelectionPanelButtonListener) {
    this.exportFileSelectionPanel =
        new ExportFileSelectionPanel(exportFileSelectionPanelButtonListener);
    this.dateSelectionPanel = new DateSelectionPanel(dateSelectionPanelActionListener);
    this.positionsSelectionPanel =
        new PositionsSelectionPanel(positionsSelectionPanelActionListener);
    this.firstShiftChangeSelectionPanel =
        new FirstShiftChangeSelectionPanel(noFirstShiftChangeCheckBoxListener);
    this.endShiftsSelectionPanel =
        new EndShiftsSelectionPanel(endShiftsSelectionPanelActionListener);
    this.runsheetPathSelectionPanel =
        new RunsheetPathSelectionPanel(runsheetPathSelectionPanelButtonListener);
  }
}
