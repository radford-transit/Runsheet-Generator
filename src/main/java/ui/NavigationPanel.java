package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

public class NavigationPanel extends JPanel {
	// Back button
	JButton backButton = new JButton("Back");
	// Next button
	JButton nextButton = new JButton("Next");
	
	// Buttons panel specifically for containing navigation buttons
	private JPanel buttonsPanel = new JPanel();
	
	/**
	 * Constructs a NavigationPanel object
	 */
	public NavigationPanel(
			ActionListener backAction, ActionListener nextAction) {
		super(new BorderLayout());
		
		// Make and add buttons panel
		this.makeButtonsPanel();
		this.addFunctionality(backAction, nextAction);
		this.add(this.buttonsPanel, BorderLayout.EAST);
	}
	
	/**
	 * Adds functionality to the navigation panel buttons
	 */
	private void addFunctionality(
			ActionListener back, ActionListener next) {
		// Back button
		this.backButton.addActionListener(back);
		// Next button
		this.nextButton.addActionListener(next);
	}
	
	/**
	 * Makes a buttons panel containing a back button and a next button
	 */
	private void makeButtonsPanel() {
		// Buttons panel has a box layout
		this.buttonsPanel.setLayout(
				new BoxLayout(this.buttonsPanel, BoxLayout.X_AXIS));
		
		// Add buttons to buttons panel
		this.buttonsPanel.add(backButton);
		this.buttonsPanel.add(nextButton);
	}
}
