package ui;

import javax.swing.*;

public class InstructionsPanel extends JPanel {
  // Instructions label
  private JLabel instructionsLabel =
      new JLabel(
          String.format(
              "<html><div style=\"width:%dpx;\">%s</div><html>",
              400,
              "<html>"
                  + "	<b>Before you begin:</b>"
                  + "	<br>"
                  + "	<br>"
                  + "	<ol>"
                  + "		<li>Log in to WhenToWork.</li>"
                  + "		<li>Click on 'SCHEDULE'.</li>"
                  + "		<li>Click on 'Export' (in the top right corner).</li>"
                  + "		<li>In the window that pops up, make sure <em>all</em> boxes are checked.</li>"
                  + "		<li>Click on 'Create Export File'.</li>"
                  + "		<li>Save the export file to a location you can easily remember (like your Downloads folder)."
                  + "	</ol>"
                  + "</html>"));

  /** Constructs an InstructionsPanel object */
  public InstructionsPanel() {
    super();

    this.add(this.instructionsLabel);
  }
}
