package runsheet_generator;

import java.awt.EventQueue;
import java.awt.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI extends JFrame {

	static final int WIDTH = 600;
	static final int HEIGHT = 400;
	static final int BORDER_SIZE = 10;
	static final int N_STEPS = 6;
	
	private int currentStep = 0;
	
	private JPanel mainPanel = new JPanel();
	private JPanel[] contentPanels = this.createContentPanels();
	private class StepButtonsPanel extends JPanel {
		private JButton backButton = new JButton("Back");
		private JButton nextButton = new JButton("Next");
		
		StepButtonsPanel() throws Exception {
			super();
			
			backButton.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (backButton.isEnabled()) {
						System.out.println("Back");
						currentStep--;
						try {
							goToStep(currentStep);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}
				
			});
			
			nextButton.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (nextButton.isEnabled()) {
						System.out.println("Next");
						currentStep++;
						try {
							goToStep(currentStep);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}
			});
			
			add(backButton);
			add(nextButton);
		}
	}
	private StepButtonsPanel stepButtonsPanel = new StepButtonsPanel();

	/**
	 * Create the application.
	 */
	public GUI() throws Exception {
		super("Runsheet Generator");
		
		this.setSize(GUI.WIDTH, GUI.HEIGHT);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.initialize();
		
		try {
			this.goToStep(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private JPanel[] createContentPanels() {
		JPanel[] panels = new JPanel[GUI.N_STEPS];
		
		panels[0] = createExportFileInstructionsPanel();
		panels[1] = createExportFileSelectionPanel();
		
		return panels;
	}
	
	private void goToStep(int step) throws Exception {
		this.mainPanel.removeAll();
		this.invalidate();
		this.validate();
		
		// Back button is not enabled on export file instructions panel
		if (step == 0)
			this.stepButtonsPanel.backButton.setEnabled(false);
		else this.stepButtonsPanel.backButton.setEnabled(true);

		this.mainPanel.add(this.contentPanels[step]);
		this.invalidate();
		this.validate();
	}
	
	private JPanel createExportFileInstructionsPanel() {
		JPanel panel = new JPanel();
		
		String labelText = String.format("<html><div style=\"width:%dpx;\">%s</div><html>",
				450,
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
						+ "</html>");
		JLabel label = new JLabel(labelText);
		panel.add(label);
		
		return panel;
	}
	
	private JPanel createExportFileSelectionPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(GUI.BORDER_SIZE, GUI.BORDER_SIZE, GUI.BORDER_SIZE, GUI.BORDER_SIZE));
		
		// Label
		String labelText = String.format("<html><div style=\"width:%dpx;\">%s</div><html>",
				450,
				"<html>" + "<br><br><br><br>"
						+ " <b>Select the export file</b>"
						+ " <br>"
						+ " <br>"
						+ " This is the file you just saved from WhenToWork. Its name is probably something like 'EXPORT.csv'."
						+ "</html>");
		JLabel label = new JLabel(labelText);
		label.setVerticalAlignment(JLabel.CENTER);
		panel.add(label, BorderLayout.NORTH);
		
		// Export file path selection panel
		JPanel exportFilePathSelectionPanel = new JPanel();
		exportFilePathSelectionPanel.setLayout(new BoxLayout(exportFilePathSelectionPanel, BoxLayout.X_AXIS));
		exportFilePathSelectionPanel.setBorder(new EmptyBorder(GUI.BORDER_SIZE, GUI.BORDER_SIZE, GUI.BORDER_SIZE, GUI.BORDER_SIZE));
		
		// Export file path text field
		JTextField exportFilePathTextField = new JTextField("Hello there!", 20);
		exportFilePathSelectionPanel.add(exportFilePathTextField);
		
		// Export file path browse button
		JButton exportFilePathBrowseButton = new JButton("Browse...");
		exportFilePathSelectionPanel.add(exportFilePathBrowseButton);
		
		panel.add(exportFilePathSelectionPanel);
		
		return panel;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Main panel
		this.mainPanel.setBorder(new EmptyBorder(GUI.BORDER_SIZE, GUI.BORDER_SIZE, GUI.BORDER_SIZE, GUI.BORDER_SIZE));
		
		// Step buttons panel
		this.stepButtonsPanel.setBorder(new EmptyBorder(GUI.BORDER_SIZE, GUI.BORDER_SIZE, GUI.BORDER_SIZE, GUI.BORDER_SIZE));
		
		this.getContentPane().add(this.mainPanel, BorderLayout.CENTER);
		this.getContentPane().add(this.stepButtonsPanel, BorderLayout.SOUTH);
	}
}

