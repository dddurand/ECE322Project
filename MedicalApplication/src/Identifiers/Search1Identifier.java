package Identifiers;

import java.sql.Date;

public class Search1Identifier {

	private String patientName;
	private String testName;
	private Date testDate;
	private String result;

	public Search1Identifier(String p_name, String t_name, Date t_date,
			String t_result) {
		this.patientName = p_name;
		this.testName = t_name;
		this.testDate = t_date;
		this.result = t_result;
	}

	public String get_patientName() {
		return this.patientName;
	}

	public String get_testName() {
		return this.testName;
	}

	public Date get_testDate() {
		return this.testDate;
	}

	public String get_result() {
		return this.result;
	}
}
