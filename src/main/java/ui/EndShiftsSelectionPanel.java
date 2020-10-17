package ui;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import main.RouteDrivingShift;
import main.Settings;
import swing_extensions.JCheckBoxList;

public class EndShiftsSelectionPanel extends JPanel {
  // End shifts selection label
  private JLabel endShiftsSelectionLabel =
      new JLabel(
          String.format(
              "<html><div style=\"width:%dpx;\">%s</div><html>",
              400,
              "<html>"
                  + " <b>Select shifts ending at the shop</b>"
                  + " <br>"
                  + " <br>"
                  + " Select the route shifts that will end at the shop. These "
                  + " will be <b>bolded</b> in the runsheet."
                  + "</html>"));
  // End shifts selection check box list
  public JCheckBoxList shiftsList = new JCheckBoxList();
  // Check box list goes in a scroll pane
  private JScrollPane scrollPane = new JScrollPane(this.shiftsList);

  public EndShiftsSelectionPanel(ActionListener buttonListener) {
    super();

    // Add action listener to shifts list
    this.shiftsList.addActionListener(buttonListener);
    this.setEndShiftsListData(Settings.shiftsEndingAtShop);

    // Add components
    this.add(this.endShiftsSelectionLabel);
    this.add(this.scrollPane);
  }

  public void setEndShiftsListData(RouteDrivingShift[] data) {
    ArrayList<String> descriptions = new ArrayList<String>();
    for (int i = 0; i < data.length; i++) {
      descriptions.add(data[i].route.id + "" + data[i].period + " - " + data[i].route.name);
    }
    this.shiftsList.setData(descriptions.toArray());
    this.shiftsList.selectAll();
  }
}
