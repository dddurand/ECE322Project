package Identifiers.test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import Identifiers.Search2Identifier;

public class Search2IdentifierTest {

	private Search2Identifier s2;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		s2 = new Search2Identifier("patient", "someTest", new Date(2010, 10, 13));
	}

	@Test
	public void testGetPatientName() {
		assertEquals("patient", s2.getPatientName());
	}

	@Test
	public void testGetTestName() {
		assertEquals("someTest", s2.getTestName());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetPrescribeDate() {
		assertEquals(new Date(2010, 10, 13), s2.getPrescribeDate());
	}
}
