import java.sql.SQLException;

import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

public class TestDatabase {
	

	
public static void main(String[] args) {
	
	System.out.println("Running Database test");
	Database db = new Database();
	
	try {
		db.connect();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	db.addPerson(new Person("Józsi", "gamer", AgeCategory.adult, EmploymentCategory.employed, "HKL124", true,Gender.male) );
	db.addPerson(new Person("Peti", "geciláda", AgeCategory.senior, EmploymentCategory.unemployed, null, false,Gender.female) );

	try {
		db.save();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		db.load();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
