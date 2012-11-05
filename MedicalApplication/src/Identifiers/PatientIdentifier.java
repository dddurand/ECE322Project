package Identifiers;

import java.sql.Date;

public class PatientIdentifier {

	private String id;
	private String name;
	private String address;
	private Date bday;
	private String phoneNum;

	public PatientIdentifier() {

	}

	public PatientIdentifier(String ID, String Name, String Address, Date Bday,
			String PhoneNum) {
		this.id = ID;
		this.name = Name;
		this.address = Address;
		this.bday = Bday;
		this.phoneNum = PhoneNum;

	}

	public String Name() {
		return this.name;

	}

	public String Address() {
		return this.address;

	}

	public String ID() {
		return this.id;

	}

	public Date Bday() {
		return this.bday;

	}

	public String PhoneNum() {
		return this.phoneNum;

	}

	public void setName(String name) {
		this.name = name;

	}

	public void setAddress(String address) {
		this.address = address;

	}

	public void setID(String id) {
		this.id = id;

	}

	public void setBday(Date bday) {
		this.bday = bday;

	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;

	}

}
