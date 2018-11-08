package gui;
import java.util.EventObject;

public class FormEvent extends EventObject {

	private String name;
	private String occupation;
	private int ageCategory;
	private String empCategory;
	private String taxID;
	private boolean usCitizen;
	private String gender;



	public String getGender() {
		return gender;
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

	public FormEvent(Object arg0) {
		super(arg0);
		
	}

	public FormEvent(Object source, String name, String occupation, int ageCategory, String empCategory, String taxID, boolean usCitizen, String gender) {
		super(source);
		this.name = name;
		this.occupation = occupation;
		this.ageCategory=ageCategory;
		this.empCategory=empCategory;
		this.taxID=taxID;
		this.usCitizen=usCitizen;
		this.gender=gender;
	}
	
	public int getAgeCategory() {
		return ageCategory;
	}
	
	public String getEmpCategory() {
		return empCategory;
	}
	
	public String getTaxID() {
		return taxID;
	}
	
	public boolean getUsCitizen() {
		return usCitizen;
	}

}
