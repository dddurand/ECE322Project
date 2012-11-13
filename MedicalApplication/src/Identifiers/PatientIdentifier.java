package Identifiers;

import java.sql.Date;

public class PatientIdentifier {
	private String id;
	private String name;
	private String address;
	private Date bday;
	private String phoneNum;

	public PatientIdentifier(String id, String name, String address, Date bday,
			String phoneNum) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.bday = bday;
		this.phoneNum = phoneNum;
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
