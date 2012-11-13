package Identifiers;

import java.sql.Date;

public class TestResults {
	private String patient_no;
	private String Patientname;
	private String DoctorName;
	private String DoctorId;
	private String test_id;
	private String test_name;
	private String Medical_Lab;
	private Date prescribe_date;
	private String record_id;

	public TestResults(String test_id, String patient_no, String name,
			String employee_no, String Dname, String type_id, String test_name,
			String medical_lab, Date prescribe_date) {
		this.patient_no = patient_no;
		this.Patientname = name;
		this.DoctorName = Dname;
		this.DoctorId = employee_no;
		this.record_id = test_id;
		this.test_name = test_name;
		this.Medical_Lab = medical_lab;
		this.prescribe_date = prescribe_date;
		this.test_id = type_id;

	}

	public String getTestId() {
		return this.test_id;
	}

	public String getPatientInfo() {
		return this.patient_no + ": " + this.Patientname;
	}

	public String getDoctorInfo() {
		return this.DoctorId + ": " + this.DoctorName;
	}

	public String getTestInfo() {
		return this.test_id + ": " + this.test_name;
	}

	public String getMedicalLab() {
		return this.Medical_Lab;
	}

	public String getPrescribedDate() {
		return this.prescribe_date.toString();
	}

	public String getRecordId() {
		return this.record_id;
	}
}
