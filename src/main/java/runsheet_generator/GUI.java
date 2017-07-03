package runsheet_generator;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.Panel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI extends JFrame {
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
						mainPanelCardLayout.previous(mainPanel);
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
						mainPanelCardLayout.next(mainPanel);
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

	static final int WIDTH = 600;
	static final int HEIGHT = 400;
	static final int BORDER_SIZE = 10;
	static final int N_STEPS = 2;
	
	private JPanel contentPane = new JPanel();
	private CardLayout mainPanelCardLayout = new CardLayout();
	private JPanel mainPanel = new JPanel(mainPanelCardLayout);
	private JPanel[] contentPanels = this.createContentPanels();
	private StepButtonsPanel stepButtonsPanel = new StepButtonsPanel();

	/**
	 * Create the application.
	 */
	public GUI() throws Exception {
		super("Runsheet Generator");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, GUI.WIDTH, GUI.HEIGHT);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		this.contentPane.setLayout(new BorderLayout(0, 0));
		
		// Main panel
		this.contentPane.add(this.mainPanel, BorderLayout.CENTER);
		
		// Step buttons panel
		this.stepButtonsPanel.setBorder(new EmptyBorder(GUI.BORDER_SIZE, GUI.BORDER_SIZE, GUI.BORDER_SIZE, GUI.BORDER_SIZE));
		
		// Add content panels to main panel
		for (int i = 0; i < this.contentPanels.length; i++)
			this.mainPanel.add(this.contentPanels[i], "" + i);
		
		// Add main panel and step buttons panel to GUI
		this.getContentPane().add(this.mainPanel, BorderLayout.CENTER);
		this.getContentPane().add(this.stepButtonsPanel, BorderLayout.SOUTH);
		
		this.mainPanelCardLayout.show(this.mainPanel, "0");
	}
	
	
	private JPanel[] createContentPanels() {
		JPanel[] panels = new JPanel[GUI.N_STEPS];
		
		panels[0] = createExportFileInstructionsPanel();
		panels[1] = createExportFileSelectionPanel();
		
		return panels;
	}
	
	
	private JPanel createExportFileInstructionsPanel() {
		JPanel exportFileInstructionsPanel = new JPanel();
		
		JLabel exportFileInstructionsLabel = new JLabel(String.format("<html><div style=\"width:%dpx;\">%s</div><html>",
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
						+ "</html>"));
		exportFileInstructionsPanel.add(exportFileInstructionsLabel);
		
		return exportFileInstructionsPanel;
	}
	
	
	private JPanel createExportFileSelectionPanel() {
		JPanel exportFileSelectionPanel = new JPanel();
		
		exportFileSelectionPanel.setLayout(new BoxLayout(exportFileSelectionPanel, BoxLayout.Y_AXIS));
		
		JLabel exportFileSelectionLabel = new JLabel();
		exportFileSelectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		exportFileSelectionLabel.setText(String.format("<html><div style=\"width:%dpx;\">%s</div><html>",
				450,
				"<html>"
						+ " <b>Select the export file</b>"
						+ " <br>"
						+ " <br>"
						+ " This is the file you just saved from WhenToWork. Its name is probably something like 'EXPORT.csv'."
						+ "</html>"));
		exportFileSelectionPanel.add(exportFileSelectionLabel);
		
		Panel exportFileSelectionActivityPanel = new Panel();
		exportFileSelectionPanel.add(exportFileSelectionActivityPanel);
		
		JTextField exportFileSelectionTextField = new JTextField();
		exportFileSelectionActivityPanel.add(exportFileSelectionTextField);
		exportFileSelectionTextField.setColumns(30);
		
		JButton exportFileSelectionBrowseButton = new JButton("Browse...");
		exportFileSelectionActivityPanel.add(exportFileSelectionBrowseButton);
		
		return exportFileSelectionPanel;
	}
	
}

