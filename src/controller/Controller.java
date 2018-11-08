package controller;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import gui.FormEvent;
import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

public class Controller {
	
	Database db = new Database();
	
	public List<Person> getPeople() {
		return db.getPeople();
	}

	public void addPerson(FormEvent e) {
		String name =e.getName();
		String occupation=e.getOccupation();
		int ageCatID=e.getAgeCategory();
		String empCat=e.getEmpCategory();
		boolean usCitizen = e.getUsCitizen();
		String taxID = e.getTaxID();
		String genderID =e.getGender();
		
		AgeCategory ageCat=null;
		
		switch(ageCatID) {
		case 0: 
			ageCat=AgeCategory.child;
			break;
		case 1:
			ageCat=AgeCategory.adult;
			break;
		case 2:
			ageCat=AgeCategory.senior;
			break;
		}
		
		EmploymentCategory empCategory;
		
		if (empCat.equals("employed")) {
		empCategory=EmploymentCategory.employed;	
		}
		else if (empCat.equals("self-employed")) {
			empCategory=EmploymentCategory.selfEmployed;
		}
		else if (empCat.equals("unemployed")) {
			empCategory=EmploymentCategory.unemployed;
		}
		else  {
			empCategory=EmploymentCategory.other;
		}
		
		Gender gender=null;
		
		if (genderID.equals("male")) {
			gender=Gender.male;
		}
		else if (genderID.equals("female")) {
			gender=Gender.female;
		}
		
		
		Person person = new Person(name,occupation,ageCat,empCategory,taxID,usCitizen,gender);
		
		db.addPerson(person);
	}
	public void saveToFile(File file) {
		db.saveToFile(file);
	}
	public void loadFromFile(File file) {
		db.loadFromFile(file);
	}
	public void removePerson(int row) {
		db.removePerson(row);
	}
	public void connect() throws Exception {
		db.connect();
	}
	public void disconnect() {
		db.disConnect();
	}
	public void save() throws SQLException {
		db.save();
	}
	public void load() throws SQLException {
		db.load();
	}
}
