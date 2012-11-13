package Identifiers.test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import Identifiers.PatientIdentifier;

public class PatientIdentifierTest {

	private PatientIdentifier id;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		id = new PatientIdentifier("someId", "name", "someAddress", new Date(2005, 10, 5), "745213");
	}

	@Test
	public void testID() {
		assertEquals("someId", id.ID());
	}

	@Test
	public void testSetID() {
		id.setID("newId");
		assertEquals("newId", id.ID());
	}

	@Test
	public void testName() {
		assertEquals("name", id.Name());
	}

	@Test
	public void testSetName() {
		id.setName("someNewName");
		assertEquals("someNewName", id.Name());
	}

	@Test
	public void testAddress() {
		assertEquals("someAddress", id.Address());
	}

	@Test
	public void testSetAddress() {
		id.setAddress("newAddress");
		assertEquals("newAddress", id.Address());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testBday() {
		assertEquals(new Date(2005, 10, 5), id.Bday());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testSetBday() {
		id.setBday(new Date(2003, 5, 23));
		assertEquals(new Date(2003, 5, 23), id.Bday());
	}

	@Test
	public void testPhoneNum() {
		assertEquals("745213", id.PhoneNum());
	}

	@Test
	public void testSetPhoneNum() {
		id.setPhoneNum("85234");
		assertEquals("85234", id.PhoneNum());
	}
}
