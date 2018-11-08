package model;

import java.io.Serializable;

public class Person implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7275639526502411856L;
	private static int count=0;
	private int id;
	private String name;
	private String occupation;
	private AgeCategory ageCategory;
	private EmploymentCategory empCat;
	private String taxID;
	private boolean usCitizen;

	@Override

	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", occupation=" + occupation
				+ ", ageCategory=" + ageCategory + ", empCat=" + empCat + ", taxID=" + taxID + ", usCitizen="
				+ usCitizen + ", gender=" + gender + "]";
	}

	private Gender gender;

	public Person(String name, String occupation, AgeCategory ageCategory, EmploymentCategory empCat, String taxID,
			boolean usCitizen, Gender gender) {
		super();
		
		id=count;
		this.name = name;
		this.occupation = occupation;
		this.ageCategory = ageCategory;
		this.empCat = empCat;
		this.taxID = taxID;
		this.usCitizen = usCitizen;
		this.gender = gender;
		count++;
	}
	public Person(int id, String name, String occupation, AgeCategory ageCategory, EmploymentCategory empCat, String taxID,
			boolean usCitizen, Gender gender) {
		this(name,occupation,ageCategory,empCat,taxID,usCitizen,gender);
		
		this.id=id;
	
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public AgeCategory getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(AgeCategory ageCategory) {
		this.ageCategory = ageCategory;
	}

	public EmploymentCategory getEmpCat() {
		return empCat;
	}

	public void setEmpCat(EmploymentCategory empCat) {
		this.empCat = empCat;
	}

	public String getTaxID() {
		return taxID;
	}

	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}

	public boolean isUsCitizen() {
		return usCitizen;
	}

	public void setUsCitizen(boolean usCitizen) {
		this.usCitizen = usCitizen;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
