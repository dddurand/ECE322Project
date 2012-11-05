package Identifiers;

import java.sql.Date;

public class Search2Identifier {

	private String patientName;
	private String testName;
	private Date prescribeDate;

	public Search2Identifier(String p_name, String t_name, Date p_date) {
		this.patientName = p_name;
		this.testName = t_name;
		this.prescribeDate = p_date;
	}

	public String get_patientName() {
		return this.patientName;
	}

	public String get_testName() {
		return this.testName;
	}

	public Date get_prescribeDate() {
		return this.prescribeDate;
	}

}
