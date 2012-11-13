package Identifiers.test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import Identifiers.TestResults;

public class TestResultsTest {

	private TestResults tr;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		tr = new TestResults("testId", "5345346", "someName", "3525", "someDoctor", "typeId123",
				"someTest", "someLab", new Date(100, 12, 23));
	}

	@Test
	public void testGetTestId() {
		assertEquals("typeId123", tr.getTestId());
	}

	@Test
	public void testGetPatientInfo() {
		assertEquals("5345346: someName", tr.getPatientInfo());
	}

	@Test
	public void testGetDoctorInfo() {
		assertEquals("3525: someDoctor", tr.getDoctorInfo());
	}

	@Test
	public void testGetMedicalLab() {
		assertEquals("someLab", tr.getMedicalLab());
	}

	@Test
	public void testGetTestInfo() {
		assertEquals("typeId123: someTest", tr.getTestInfo());
	}
	
	@Test
	public void testGetRecordId() {
		assertEquals("testId", tr.getRecordId());
	}
	
	@Test
	public void testGetPrescribeDate() {
		assertEquals("2001-01-23", tr.getPrescribedDate());
	}
}
