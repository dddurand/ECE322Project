package Identifiers;

public class Search3Identifier {
	private String patientName;
	private String address;
	private String phone;

	public Search3Identifier(String p_name, String addr, String ph) {
		this.patientName = p_name;
		this.address = addr;
		this.phone = ph;
	}

	public String get_patientName() {
		return this.patientName;
	}

	public String get_address() {
		return this.address;
	}

	public String get_phone() {
		return this.phone;
	}
}
