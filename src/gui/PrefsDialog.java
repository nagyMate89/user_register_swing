package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

public class PrefsDialog extends JDialog {

	private JButton okBtn;
	private JButton cancelBtn;
	private JSpinner portSpinner;
	private SpinnerNumberModel spinnerModel;
	private JTextField userField;
	private JPasswordField passwordField;
	private PrefsListener listener;

	public PrefsDialog(JFrame parent) {
		super(parent, "Preferences", false);

		okBtn = new JButton("OK");
		cancelBtn = new JButton("Cancel");

		spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
		portSpinner = new JSpinner(spinnerModel);
		userField = new JTextField(10);
		passwordField = new JPasswordField(10);

		layoutControls();

		okBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Integer value = (Integer) portSpinner.getValue();
				String user = userField.getText();
				int port = (Integer) portSpinner.getValue();
				char[] password = passwordField.getPassword();
				listener.preferencesChanged(user, new String(password), port);
				setVisible(false);

			}

		});

		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

			}
		});

		setSize(270, 180);

		setLocationRelativeTo(parent);
	}

	public void layoutControls() {
		JPanel controlsPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		
		int space = 5;
		
		Border titleBorder = BorderFactory.createTitledBorder("Database Preferences");
		Border spaceBorder = BorderFactory.createEmptyBorder(space,space,space,space);

		buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder,titleBorder));
		controlsPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();

		/// First Row///
		gc.gridy = 0;

		gc.weightx = 1;
		gc.weighty = 1;
		Insets rightPadding = new Insets(0, 0, 0, 10);
		Insets noPadding = new Insets(0, 0, 0, 0);
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.EAST;

		gc.gridx = 0;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("username: "), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(userField, gc);

		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;

		controlsPanel.add(new JLabel("password:"), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;

		controlsPanel.add(passwordField, gc);

		/// Next Row///

		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 0;

		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;

		controlsPanel.add(new JLabel("Port: "), gc);

		gc.gridx = 1;

		gc.anchor = GridBagConstraints.WEST;

		gc.insets = noPadding;

		controlsPanel.add(portSpinner, gc);

		/// Buttons Panel///

		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		buttonsPanel.add(okBtn);
		buttonsPanel.add(cancelBtn);

		Dimension btnSize = cancelBtn.getPreferredSize();
		okBtn.setPreferredSize(btnSize);

		/// Add sub Panels to dialog///
		setLayout(new BorderLayout());
		add(controlsPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
	}

	public void setPrefsListener(PrefsListener listener) {
		this.listener = listener;
	}

	public void setDefaults(String user, String password, int port) {
		userField.setText(user);
		passwordField.setText(password);
		portSpinner.setValue(port);
	}
}
