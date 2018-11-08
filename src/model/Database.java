
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {

	private List<Person> people;
	private Connection con;

	public Database() {
		people = new LinkedList<Person>();
	}

	public void addPerson(Person person) {
		people.add(person);
	}

	public List<Person> getPeople() {
		return Collections.unmodifiableList(people);
	}

	public void save() throws SQLException {

		String checkSql = "select count(*) as count from people where id=?";
		PreparedStatement checkStmt = con.prepareStatement(checkSql);
		String insertSql = "insert into people(id,name,age,employment_status,tax_id,us_citizen,gender,occupation) values (?,?,?,?,?,?,?,?)";
		PreparedStatement insertStatement=con.prepareStatement(insertSql);
		
		String updateSql = "update people set name=?,age=?,employment_status=?,tax_id=?,us_citizen=?,gender=?,occupation=? where id=?" ;
		
		PreparedStatement updateStatement = con.prepareStatement(updateSql);
		
		for (Person person : people) {
			int id = person.getId();
			AgeCategory ageCat=person.getAgeCategory();
			EmploymentCategory empCat =person.getEmpCat();
			Gender gender=person.getGender();
			String name=person.getName();
			String occupation=person.getOccupation();
			String taxID=person.getTaxID();
			boolean usCitizen=person.isUsCitizen();
			
			checkStmt.setInt(1, id);

			
			ResultSet checkResult = checkStmt.executeQuery();
			checkResult.next();

			int count = checkResult.getInt(1);

			if (count == 0) {
				int col=1;
				insertStatement.setInt(col++, id);
				insertStatement.setString(col++, name);
				insertStatement.setString(col++, ageCat.name());
				insertStatement.setString(col++, empCat.name());
				insertStatement.setString(col++, taxID);
				insertStatement.setBoolean(col++, usCitizen);
				insertStatement.setString(col++, gender.name());
				insertStatement.setString(col++, occupation);
				
				insertStatement.executeUpdate();
				
		
			}

			else {
				System.out.println("Updating person with id: " + id);
				int col=1;
				
				updateStatement.setString(col++, name);
				updateStatement.setString(col++, ageCat.name());
				updateStatement.setString(col++, empCat.name());
				updateStatement.setString(col++, taxID);
				updateStatement.setBoolean(col++, usCitizen);
				updateStatement.setString(col++, gender.name());
				updateStatement.setString(col++, occupation);
				updateStatement.setInt(col++, id);
				
				updateStatement.executeUpdate();
			}
		}
		updateStatement.close();
		insertStatement.close();
		checkStmt.close();
	}
	
	public void load() throws SQLException {
		people.clear();
		
		String sql="select id,name,age,employment_status,tax_id,us_citizen,gender,occupation from people order by name";
		Statement selectStatement = con.createStatement();
		ResultSet results=selectStatement.executeQuery(sql);
		
		while(results.next()) {
			int id =results.getInt("id");
			String name = results.getString("name");
			String age = results.getString("age");
			String emp = results.getString("employment_status");
			String tax = results.getString("tax_id");
			boolean isUs = results.getBoolean("us_citizen");
			String gender = results.getString("gender");
			String occ = results.getString("occupation");

			people.add(new Person(id,name,occ,AgeCategory.valueOf(age),EmploymentCategory.valueOf(emp),tax,isUs,Gender.valueOf(gender)));
	
		}
		
		results.close();
		selectStatement.close();
		
	}

	public void connect() throws Exception {

		if (con != null) {
			return;
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver not found");
		}

		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "1254789";
		con = DriverManager.getConnection(url, user, password);
		System.out.println("Connected" + con);
	}

	public void disConnect() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Disconnected");
	}

	public void saveToFile(File file) {
		FileOutputStream fos;

		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			Person[] persons = people.toArray(new Person[people.size()]);
			oos.writeObject(persons);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void loadFromFile(File file) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			Person[] persons = (Person[]) ois.readObject();
			people.clear();
			people.addAll(Arrays.asList(persons));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removePerson(int row) {
		people.remove(row);
	}
}
