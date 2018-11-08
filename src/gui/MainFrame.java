package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import controller.Controller;

public class MainFrame extends JFrame {

	private TextPanel textPanel;
	private Toolbar toolbar;
	private FormPanel formPanel;
	private JFileChooser fileChooser;
	private Controller controller;
	private TablePanel tablePanel;
	private PrefsDialog prefsDialog;
	private Preferences prefs;
	private JSplitPane splitPane;
	private JTabbedPane tabPane;
	private MessagePanel messagePanel;
	
	
	public MainFrame() {

		super("Person Database");

		setLayout(new BorderLayout());
		
		tablePanel = new TablePanel();
		textPanel = new TextPanel();
		toolbar = new Toolbar();
		formPanel = new FormPanel();
		setJMenuBar(createMenuBar());
		fileChooser = new JFileChooser();
		controller = new Controller();
		prefsDialog = new PrefsDialog(this);
		prefs = Preferences.userRoot().node("db");
		tabPane = new JTabbedPane();
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tabPane);
		messagePanel = new MessagePanel(this);
		
		
		tabPane.addTab("Person Data", tablePanel);
		tabPane.addTab("Messages", messagePanel);
		
		
		tablePanel.setData(controller.getPeople());
		
		fileChooser.addChoosableFileFilter(new PersonFileFilter());
		toolbar.setToolbarListener(new ToolbarListener() {

			
			public void saveEventOccured() {
				try {
					controller.connect();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Couldn't estabilish connection to Database ", "Connection Error", JOptionPane.ERROR_MESSAGE);
				}
				
				try {
					controller.save();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Couldn't save data to Database ", "File Saving Error", JOptionPane.ERROR_MESSAGE);
				}
			
				
			}

			@Override
			public void refreshEventOccured() {
				try {
					controller.connect();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Couldn't estabilish connection to Database ", "Connection Error", JOptionPane.ERROR_MESSAGE);
				}
				try {
					controller.load();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Couldn't load data from Database ", "Error Loading Files", JOptionPane.ERROR_MESSAGE);

				}
				tablePanel.refresh();
				
			}
			
			
		});
		
		
		formPanel.setFormListener(new FormListener() {
			public void formEventOccured(FormEvent e) {
				
				controller.addPerson(e);
				tablePanel.refresh();
			}
			
			
		});
		
		tablePanel.addPersonTableListener(new PersonTableListener() {
			
			public void rowDeleted(int row) {
				controller.removePerson(row);
			
			}
			
		});
		String user = prefs.get("user", "");
		String password = prefs.get("password", "");
		String port = prefs.get("port", "3306");
		
		prefsDialog.setDefaults(user, password, Integer.parseInt(port));
		
		prefsDialog.setPrefsListener(new PrefsListener() {
			
			@Override
			public void preferencesChanged(String user, String password, int port) {
				prefs.put("user", user );
				prefs.put("password", password);
				prefs.put("port", new Integer(port).toString());
				
			}
		});
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				
				controller.disconnect();
				dispose();
				System.gc();
			}
			
			
		});
		
		
		add(toolbar, BorderLayout.PAGE_START);
		add(splitPane, BorderLayout.CENTER);

		setMinimumSize(new Dimension(600,600));
		setSize(800, 800);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem exportDataItem = new JMenuItem("Export Data...");
		JMenuItem importDataItem = new JMenuItem("Import Data...");
		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exportDataItem);
		fileMenu.add(importDataItem);
		fileMenu.add(exitItem);
		fileMenu.addSeparator();
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		importDataItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showOpenDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION) {
				controller.loadFromFile(fileChooser.getSelectedFile());
				tablePanel.refresh();
				}
				
			}
			
			
		});
		
		importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

		
		exportDataItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showSaveDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION) {
					controller.saveToFile(fileChooser.getSelectedFile());
					
				}
				
			}
			
			
		});
		menuBar.add(fileMenu);
		
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		exitItem.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int action = JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure you want to quit?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
				if (action==JOptionPane.OK_OPTION) {
			WindowListener[] listeners= getWindowListeners();
			
			for(WindowListener listener: listeners) {
				listener.windowClosing(new WindowEvent(MainFrame.this, 0));
			}
				}
				
			}
			
			
		});
		JMenu showMenu = new JMenu("Show");
		JCheckBoxMenuItem showPersonItem = new JCheckBoxMenuItem("Show Person Form");
		showPersonItem.setSelected(true);
		
		showPersonItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ev) {
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem)ev.getSource();
				if (menuItem.isSelected()) {
					splitPane.setDividerLocation((int)formPanel.getMinimumSize().getWidth());
				}
				
				formPanel.setVisible(menuItem.isSelected());
				
			}
		});
		
		JMenuItem prefsItem = new JMenuItem("Preferences...");
		JMenu windowMenu = new JMenu("Window");
		
		prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		
		prefsItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				prefsDialog.setVisible(true);
				
			}
			
			
		});
		
		showMenu.add(showPersonItem);
		windowMenu.add(prefsItem);
		windowMenu.add(showMenu);
		menuBar.add(windowMenu);
		
		
		
		
	
		
		return menuBar;
	}
}
