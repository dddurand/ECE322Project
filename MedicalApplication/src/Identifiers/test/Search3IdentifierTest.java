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
		assertEquals("patient", s3.getPatientName());
	}

	@Test
	public void testGetAddress() {
		assertEquals("address", s3.getAddress());
	}

	@Test
	public void testGetPhoneNumber() {
		assertEquals("78484", s3.getPhone());
	}
}
