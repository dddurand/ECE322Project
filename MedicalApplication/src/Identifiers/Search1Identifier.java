package Identifiers;

import java.sql.Date;

public class Search1Identifier implements SearchIdentifier {

	private String patientName;
	private String testName;
	private Date testDate;
	private String result;

	public Search1Identifier(String p_name, String t_name, Date t_date, String t_result) {
		this.patientName = p_name;
		this.testName = t_name;
		this.testDate = t_date;
		this.result = t_result;
	}

	@Override
	public String getPatientName() {
		return this.patientName;
	}

	public String getTestName() {
		return this.testName;
	}

	public Date getTestDate() {
		return this.testDate;
	}

	public String getResult() {
		return this.result;
	}
}
