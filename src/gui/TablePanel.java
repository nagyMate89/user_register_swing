package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import model.Person;

public class TablePanel extends JPanel {

	private PersonTableModel tableModel;
	private JTable table;
	private JPopupMenu popup;
	private PersonTableListener listener;

	
	public TablePanel() {
		tableModel = new PersonTableModel();
		popup = new JPopupMenu();
		table = new JTable(tableModel);

		
		
		JMenuItem removeItem = new JMenuItem("Delete element");
		popup.add(removeItem);
		
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int row=table.rowAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row,row);
				
				if (e.getButton()==MouseEvent.BUTTON3) {
					popup.show(table, e.getX(), e.getY());
				}
				
			}
			
		});
		
		removeItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				listener.rowDeleted(row);
				tableModel.fireTableRowsDeleted(row, row);
				
				
			}
			
			
		});
		
		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	public void setData (List<Person> db) {
		tableModel.setData(db);
	}
	public void refresh() {
		tableModel.fireTableDataChanged();
	}
	
	public void addPersonTableListener(PersonTableListener listener) {
		this.listener=listener;
	}
}
