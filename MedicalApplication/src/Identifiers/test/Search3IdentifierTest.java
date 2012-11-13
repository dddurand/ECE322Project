package Identifiers.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Identifiers.Search3Identifier;

public class Search3IdentifierTest {

	private Search3Identifier s3;

	@Before
	public void setUp() throws Exception {
		s3 = new Search3Identifier("patient", "address", "78484");
	}

	@Test
	public void testGetPatientName() {
		assertEquals("patient", s3.get_patientName());
	}

	@Test
	public void testGetAddress() {
		assertEquals("address", s3.get_address());
	}

	@Test
	public void testGetPhoneNumber() {
		assertEquals("78484", s3.get_phone());
	}
}
