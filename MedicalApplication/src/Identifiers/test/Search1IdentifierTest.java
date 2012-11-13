package Identifiers.test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import Identifiers.Search1Identifier;

public class Search1IdentifierTest {

	private Search1Identifier s1;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		s1 = new Search1Identifier("someName", "someTest", new Date(2012, 11, 13), "passed");
	}

	@Test
	public void testGetPatientName() {
		assertEquals("someName", s1.getPatientName());
	}

	@Test
	public void testGetTestName() {
		assertEquals("someTest", s1.getTestName());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetTestDate() {
		assertEquals(new Date(2012, 11, 13), s1.getTestDate());
	}

	@Test
	public void testGetTestResult() {
		assertEquals("passed", s1.getResult());
	}
}
