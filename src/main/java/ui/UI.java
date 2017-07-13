package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.CSVReader;
import main.Date;
import main.Settings;

public class UI extends JFrame {
	// JFrame dimensions
	static final int WIDTH = 600;
	static final int HEIGHT = 400;
	
	// Content pane (has border layout)
	JPanel contentPane = new JPanel(new BorderLayout());
	
	// Main panels
	MainPanels mainPanels = new MainPanels(
			// Export file selection panel button listener
			this.makeExportFileSelectionPanelButtonListener(),
			// Date selection listener
			this.makeDateSelectionListener(),
			// Positions list selection listener
			this.makePositionsListSelectionListener(),
			// Runsheet path selection panel button listener
			this.makeRunsheetPathSelectionPanelButtonListener());
	// Navigation panel
	NavigationPanel navigationPanel = 
			new NavigationPanel(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {try {
							// Go back one panel
							changeMainPanel(-1);
						} catch (Exception e1) {e1.printStackTrace();}}
					},
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {try {
							// Go forward one panel
							changeMainPanel(1);
						} catch (Exception e1) {e1.printStackTrace();}}
					});
	
	/**
	 * Constructs a UI object
	 */
	public UI() {
		super("Runsheet Generator");
		this.init();
		
		// Add navigation panel to content pane
		this.contentPane.add(this.navigationPanel, BorderLayout.SOUTH);
		
		// Initialize to instructions panel
		this.contentPane.add(this.mainPanels.instructionsPanel, BorderLayout.CENTER);
		this.navigationPanel.backButton.setEnabled(false);
	}
	
	/**
	 * Sets initial JFrame properties
	 */
	private void init() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set JFrame bounds
		this.setBounds(300, 100, UI.WIDTH, UI.HEIGHT);
		this.setResizable(false);
		
		// Set border of content pane
		final int BORDER_SIZE = 10;
		this.contentPane.setBorder(new EmptyBorder(
				BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
		
		this.setContentPane(this.contentPane);
	}

	/**
	 * Changes the currently active main panel. There are two main actions each
	 * time the main panel is changed. The first is the removal of one main panel
	 * and the addition of another. The second is the setting of each navigation
	 * button's enabled state.
	 * @param panel The direction of the step being taken
	 * @throws Exception 
	 */
	private void changeMainPanel(int direction) throws Exception {
		// If negative direction
		if (direction == -1) {
			// Export file selection panel -> Instructions panel
			if (this.mainPanels.exportFileSelectionPanel.isShowing()) {
				this.contentPane.remove(this.mainPanels.exportFileSelectionPanel);
				this.contentPane.add(this.mainPanels.instructionsPanel);
				
				// Disable back button and enable next button
				this.setNavigationButtonsEnabledStates(false, true);
			}
			// Date selection panel -> Export file selection panel
			else if (this.mainPanels.dateSelectionPanel.isShowing()) {
				this.contentPane.remove(this.mainPanels.dateSelectionPanel);
				this.contentPane.add(this.mainPanels.exportFileSelectionPanel);
				
				// If there is no export file, disable next button.
				if (Settings.exportFilePath == null)
					this.setNavigationButtonsEnabledStates(true, false);
				// Otherwise, enable it
				else this.setNavigationButtonsEnabledStates(true, true);
			}
			// Positions selection panel -> Date selection panel
			else if (this.mainPanels.positionsSelectionPanel.isShowing()) {
				this.contentPane.remove(this.mainPanels.positionsSelectionPanel);
				this.contentPane.add(this.mainPanels.dateSelectionPanel);
				
				// Enable both navigation buttons
				this.setNavigationButtonsEnabledStates(true, true);
			}
			// First shift change selection panel -> Positions selection panel
			else if (this.mainPanels.firstShiftChangeSelectionPanel.isShowing()) {
				this.contentPane.remove(this.mainPanels.firstShiftChangeSelectionPanel);
				this.contentPane.add(this.mainPanels.positionsSelectionPanel);
			}
			// Runsheet path selection panel -> First shift change selection panel
			else if (this.mainPanels.runsheetPathSelectionPanel.isShowing()) {
				this.contentPane.remove(this.mainPanels.runsheetPathSelectionPanel);
				this.contentPane.add(this.mainPanels.firstShiftChangeSelectionPanel);
				
				// Change text of next button to "Next"
				this.navigationPanel.nextButton.setText("Next");
				
				// Enable both navigation buttons
				this.setNavigationButtonsEnabledStates(true, true);
			}
		}
		// If positive direction
		else if (direction == 1) {
			// Instructions panel -> Export file selection panel
			if (this.mainPanels.instructionsPanel.isShowing()) {
				this.contentPane.remove(this.mainPanels.instructionsPanel);
				this.contentPane.add(this.mainPanels.exportFileSelectionPanel);
				
				// If there is no export file, disable next button
				if (Settings.exportFilePath == null)
					this.setNavigationButtonsEnabledStates(true, false);
				// Otherwise, enable it
				else this.setNavigationButtonsEnabledStates(true, true);
			}
			// Export file selection panel -> Date selection panel
			else if (this.mainPanels.exportFileSelectionPanel.isShowing()) {
				this.contentPane.remove(this.mainPanels.exportFileSelectionPanel);
				this.contentPane.add(this.mainPanels.dateSelectionPanel);
			
				// Enable both navigation buttons
				this.setNavigationButtonsEnabledStates(true, true);
			}
			// Date selection panel -> Positions selection panel
			else if (this.mainPanels.dateSelectionPanel.isShowing()) {
				this.contentPane.remove(this.mainPanels.dateSelectionPanel);
				this.contentPane.add(this.mainPanels.positionsSelectionPanel);
			}
			// Positions selection panel -> First shift change selection panel
			else if (this.mainPanels.positionsSelectionPanel.isShowing()) {
				this.contentPane.remove(this.mainPanels.positionsSelectionPanel);
				this.contentPane.add(this.mainPanels.firstShiftChangeSelectionPanel);
			
				// Enable both navigation buttons
				this.setNavigationButtonsEnabledStates(true, true);
			}
			// First shift change selection panel -> Runsheet path selection panel
			else if (this.mainPanels.firstShiftChangeSelectionPanel.isShowing()) {
				this.contentPane.remove(this.mainPanels.firstShiftChangeSelectionPanel);
				this.contentPane.add(this.mainPanels.runsheetPathSelectionPanel);
				
				// Change text of next button to "Done"
				this.navigationPanel.nextButton.setText("Done");
				
				// If no runsheet path is selected, disable next button
				if (Settings.runsheetPath == null)
					this.setNavigationButtonsEnabledStates(true, false);
				// Otherwise, enable it
				else this.setNavigationButtonsEnabledStates(true, true);
			}
		}
		else throw new Exception("Can't go more than one step");
		
		// Refresh content pane
		this.contentPane.revalidate();
		this.contentPane.repaint();
	}
	
	/**
	 * Sets enability of the back and next buttons
	 * @param backEnabled Enability of back button
	 * @param nextEnabled Enability of next button
	 */
	private void setNavigationButtonsEnabledStates(
			boolean backEnabled, boolean nextEnabled) {
		this.navigationPanel.backButton.setEnabled(backEnabled);
		this.navigationPanel.nextButton.setEnabled(nextEnabled);
	}

	/**
	 * Makes an action listener for the export file selection panel button
	 * @return an export file selection panel button action listener
	 */
	private ActionListener makeExportFileSelectionPanelButtonListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get file path
				Settings.exportFilePath = 
						ExportFileSelectionPanel.getFilePath(Settings.exportFilePath);
				// If a file path is selected,
				if (Settings.exportFilePath != null) {
					// Set text in text field
					mainPanels.exportFileSelectionPanel.textField.setText(
							Settings.exportFilePath.toString());
					// Set CSV file for CSVReader
					CSVReader.setCSVFile(Settings.exportFilePath.toFile());
					// Set contents of date selection combo box
					mainPanels.dateSelectionPanel.comboBox.setModel(
							new DefaultComboBoxModel<Date>(CSVReader.getDates()));
					// Enable next button
					setNavigationButtonsEnabledStates(true, true);
				}
				// Otherwise, if no file path is selected,
				else {
					// Set text field to empty String
					mainPanels.exportFileSelectionPanel.textField.setText("");
					// Disable next button
					setNavigationButtonsEnabledStates(true, false);
				}
			}
		};
	}

	/**
	 * Makes an item listener for the date selection combo box
	 * @return an item listener for the date selection combo box
	 */
	private ItemListener makeDateSelectionListener() {
		return new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// Select all positions in positions check box list
				//mainPanels.positionsSelectionPanel.positionsList.selectAll();
				
				// Set date to selection
				Settings.date =
						(Date) mainPanels.dateSelectionPanel.comboBox.getSelectedItem();
				
				// Set list data for positions selection check box list
				mainPanels.positionsSelectionPanel.setPositionsListData(
						CSVReader.getNonRouteDrivingPositionsOnDate(Settings.date));
			}
		};
	}

	/**
	 * Makes a list selection listener for the positions check box list
	 * @return a list selection listener for the positions check box list
	 */
	private ListSelectionListener makePositionsListSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// DO SOMETHING HERE!!!!!!!
			}
		};
	}

	private ActionListener makeRunsheetPathSelectionPanelButtonListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get directory path
				Settings.runsheetPath = 
						RunsheetPathSelectionPanel.getFilePath(Settings.runsheetPath);
				// If a path is selected,
				if (Settings.runsheetPath != null) {
					// Set text in text field
					mainPanels.runsheetPathSelectionPanel.textField.setText(
							Settings.runsheetPath.toString());
					// Enable next button
					setNavigationButtonsEnabledStates(true, true);
				}
				// Otherwise, if no path is selected,
				else {
					// Set text field to empty String
					mainPanels.runsheetPathSelectionPanel.textField.setText("");
					// Disable next button
					setNavigationButtonsEnabledStates(true, false);
				}
			}
		};
	}
}
