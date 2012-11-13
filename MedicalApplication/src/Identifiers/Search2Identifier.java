package Identifiers;

import java.sql.Date;

public class Search2Identifier implements SearchIdentifier {
	private String patientName;
	private String testName;
	private Date prescribeDate;

	public Search2Identifier(String p_name, String t_name, Date p_date) {
		this.patientName = p_name;
		this.testName = t_name;
		this.prescribeDate = p_date;
	}

	public String getPatientName() {
		return this.patientName;
	}

	public String getTestName() {
		return this.testName;
	}

	public Date getPrescribeDate() {
		return this.prescribeDate;
	}
}
