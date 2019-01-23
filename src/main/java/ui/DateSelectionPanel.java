package ui;

import java.awt.event.ActionListener;
import javax.swing.*;
import main.*;

public class DateSelectionPanel extends JPanel {
  // Date selection label
  private JLabel label =
      new JLabel(
          String.format(
              "<html><div style=\"width:%dpx;\">%s</div><html>",
              400,
              "<html>"
                  + " <b>Select date</b>"
                  + " <br>"
                  + " <br>"
                  + " Select the date of the runsheet you wish to make."
                  + "</html>"));
  // Date selection combo box
  public JComboBox<Date> comboBox = new JComboBox<Date>(new DefaultComboBoxModel<Date>());

  /** Constructs a DateSelectionPanel object */
  public DateSelectionPanel(ActionListener comboBoxListener) {
    super();

    // Add listener to combo box
    this.comboBox.addActionListener(comboBoxListener);

    // Add components
    this.add(this.label);
    this.add(this.comboBox);
  }
}
