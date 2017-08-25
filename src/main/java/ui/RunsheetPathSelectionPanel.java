package ui;

import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import javax.swing.*;
import main.Settings;

public class RunsheetPathSelectionPanel extends JPanel {
  // Runsheet path selection label
  private JLabel runsheetPathSelectionPanel =
      new JLabel(
          String.format(
              "<html><div style=\"width:%dpx;\">%s</div><html>",
              400,
              "<html>"
                  + " <b>Save runsheet</b>"
                  + " <br>"
                  + " <br>"
                  + " Set the location of the runsheet."
                  + "</html>"));
  // Runsheet path selection text field
  public JTextField textField = new JTextField();
  // Runsheet path selection button
  private JButton button = new JButton("Save To...");

  /** Constructs a RunsheetPathSelectionPanel object */
  public RunsheetPathSelectionPanel(ActionListener buttonListener) {
    super();

    // Text field settings
    this.textField.setEditable(false);
    this.textField.setColumns(30);

    // Button settings
    this.button.addActionListener(buttonListener);

    // Add components
    this.add(this.runsheetPathSelectionPanel);
    this.add(this.textField);
    this.add(this.button);
  }

  /**
   * Shows a file chooser for selecting a runsheet directory. Returns the directory path.
   *
   * @param currentFilePath The currently selected directory path. Can be null.
   * @return the selected Path
   */
  public static Path getFilePath(Path currentFilePath) {
    // File chooser settings
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save runsheet");
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    // If a new file path was selected, return that path
    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
      currentFilePath =
          new File(
                  fileChooser.getSelectedFile().toString()
                      + (System.getProperty("os.name").contains("Windows") ? "\\" : "/")
                      + Settings.date.toFileNamePrefixString()
                      + ".xlsx")
              .toPath();
    // Otherwise, return the current file path
    return currentFilePath;
  }
}
