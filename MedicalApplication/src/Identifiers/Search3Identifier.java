package Identifiers;

public class Search3Identifier implements SearchIdentifier {
	private String patientName;
	private String address;
	private String phone;

	public Search3Identifier(String p_name, String addr, String ph) {
		this.patientName = p_name;
		this.address = addr;
		this.phone = ph;
	}

	public String getPatientName() {
		return this.patientName;
	}

	public String getAddress() {
		return this.address;
	}

	public String getPhone() {
		return this.phone;
	}
}
