package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Person;

public class PersonTableModel extends AbstractTableModel {

	private List<Person> db;
	private String[] colNames= {"Name", "ID", "AgeCategory", "Occupation", "Employment Category", "US citizen", "Tax ID", "Gender"};
	
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return colNames[column];
	}
	public void setData(List<Person> db) {
		this.db=db;
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public int getRowCount() {
	return db.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		Person person=db.get(row);
		
		switch(col) {
		case 0:
			return person.getName();
		case 1:
			return person.getId();
		case 2:
			return person.getAgeCategory();
		case 3:
			return person.getOccupation();
		case 4:
			return person.getEmpCat();
		case 5:
			return person.isUsCitizen();
		case 6:
			return person.getTaxID();
		case 7:
		return person.getGender();
		}
		return null;
	}

}
