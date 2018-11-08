package gui;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {

	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JTextField nameField;
	private JTextField occupationField;
	private JButton okButton;
	private FormListener formListener;
	private JList ageList;
	private JComboBox empCombo;
	private JLabel citizenLabel;
	private JCheckBox citizenBox;
	private JTextField taxField;
	private JLabel taxLabel;
	private JRadioButton maleBtn;
	private JRadioButton femaleBtn;
	private ButtonGroup btnGroup;

	public FormPanel() {

		nameLabel = new JLabel("Name ");
		occupationLabel = new JLabel("Occupation ");
		nameField = new JTextField(10);
		occupationField = new JTextField(10);
		okButton = new JButton("OK");
		ageList = new JList();
		empCombo = new JComboBox();
		citizenBox = new JCheckBox();
		citizenLabel = new JLabel("US citizen: ");
		taxLabel = new JLabel("Tax ID: ");
		taxField = new JTextField(8);
		taxField.setEnabled(false);
		taxLabel.setEnabled(false);
		maleBtn = new JRadioButton("male");
		femaleBtn = new JRadioButton("female");
		btnGroup = new ButtonGroup();
		
		//Set up mnemonics 
		
		okButton.setMnemonic(KeyEvent.VK_O);
		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameField);

		btnGroup.add(maleBtn);
		btnGroup.add(femaleBtn);
		maleBtn.setSelected(true);
		maleBtn.setActionCommand("male");
		femaleBtn.setActionCommand("female");


		DefaultListModel ageModel = new DefaultListModel();
		ageModel.addElement(new AgeCategory(0, "Under 18"));
		ageModel.addElement(new AgeCategory(1, "Between 18 and 65"));
		ageModel.addElement(new AgeCategory(2, "65 or over"));
		ageList.setModel(ageModel);
		ageList.setPreferredSize(new Dimension(100, 60));
		ageList.setBorder(BorderFactory.createEtchedBorder());
		ageList.setSelectedIndex(1);

		DefaultComboBoxModel empModel = new DefaultComboBoxModel();
		empModel.addElement("employed");
		empModel.addElement("self-employed");
		empModel.addElement("unemployed");
		empCombo.setModel(empModel);
		empCombo.setPreferredSize(new Dimension(100, 60));
		empCombo.setBorder(BorderFactory.createEtchedBorder());
		empCombo.setSelectedIndex(0);
		
		
		citizenBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean isEnabled = citizenBox.isSelected();
				taxField.setEnabled(isEnabled);
				taxLabel.setEnabled(isEnabled);

			}

		});

		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String name = nameField.getText();
				String occupation = occupationField.getText();
				AgeCategory ageCat = (AgeCategory) ageList.getSelectedValue();
				String empCat = (String) empCombo.getSelectedItem();
				boolean usCitizen = citizenBox.isSelected();
				String taxID = taxField.getText();
				String gender = btnGroup.getSelection().getActionCommand();
				System.out.println(gender);
				

				FormEvent ev = new FormEvent(this, name, occupation, ageCat.getId(), empCat, taxID, usCitizen, gender);

				if (formListener != null) {
					formListener.formEventOccured(ev);

				}
			}

		});

		Dimension dim = getPreferredSize();
		dim.width = 400;
		setMinimumSize(dim);
		setPreferredSize(dim);

		Border innerBorder = BorderFactory.createTitledBorder("Add a Person!");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		layoutElements();

	}

	public void layoutElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		///// FIRST ROW////

		gc.gridy = 0;

		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 0;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);

		add(nameLabel, gc);

		gc.gridx = 1;
		gc.gridy = 0;
		gc.weightx = 20;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(nameField, gc);

		///// NEXT ROW////

		gc.gridy++;

		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 0;

		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(occupationLabel, gc);

		gc.gridx = 1;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(occupationField, gc);

		///// NEXT ROW////

		gc.gridy++;

		gc.ipadx = 15;
		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 0;

		gc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Age: "), gc);

		gc.ipadx = 15;
		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 1;

		gc.anchor = GridBagConstraints.LINE_START;
		add(ageList, gc);

		///// NEXT ROW////

		gc.gridy++;

		gc.ipadx = 15;
		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 0;

		gc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Employement status: "), gc);

		gc.ipadx = 15;
		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 1;

		gc.anchor = GridBagConstraints.LINE_START;
		add(empCombo, gc);

		///// NEXT ROW////

		gc.gridy++;

		gc.ipadx = 15;
		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 0;

		gc.anchor = GridBagConstraints.LINE_END;
		add(citizenLabel, gc);

		gc.ipadx = 15;
		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 1;

		gc.anchor = GridBagConstraints.LINE_START;
		add(citizenBox, gc);

		///// NEXT ROW////

		gc.gridy++;

		gc.ipadx = 15;
		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 0;

		gc.anchor = GridBagConstraints.LINE_END;
		add(taxLabel, gc);

		gc.ipadx = 15;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 1;

		gc.anchor = GridBagConstraints.LINE_START;
		add(taxField, gc);

		///// NEXT ROW////

		gc.gridy++;

		gc.ipadx = 15;
		gc.weightx = 0;
		gc.weighty = 0.1;

		gc.gridx = 0;

		gc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Gender: "), gc);

		
		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 1;

		gc.anchor = GridBagConstraints.LINE_START;
		add(maleBtn, gc);

		///// NEXT ROW////

		gc.gridy++;

		
		gc.weightx = 0.1;
		gc.weighty = 0.05;

		gc.gridx = 1;

		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(femaleBtn, gc);

		///// NEXT ROW////

		gc.gridy++;

		gc.ipadx = 10;
		gc.weightx = 1.0;
		gc.weighty = 3.0;

		gc.gridx = 1;

		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(okButton, gc);

	}

	public void setFormListener(FormListener listener) {
		this.formListener = listener;
	}

	public class AgeCategory {
		private int id;
		private String text;

		public AgeCategory(int id, String text) {
			this.id = id;
			this.text = text;
		}

		public String toString() {
			return text;
		}

		public int getId() {
			return id;
		}
	}
}
