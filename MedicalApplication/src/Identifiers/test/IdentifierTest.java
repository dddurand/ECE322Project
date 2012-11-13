package Identifiers.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Identifiers.Identifier;

public class IdentifierTest {

	private Identifier id;

	@Before
	public void setUp() throws Exception {
		id = new Identifier("someId", "someName", 'a');
	}

	@Test
	public void testGetName() {
		assertEquals("someName", id.getName());
	}

	@Test
	public void testGetId() {
		assertEquals("someId", id.getId());
	}

	@Test
	public void testGetType() {
		assertEquals('a', id.getType());
	}
	
	@Test
	public void testSetType() {
		id.setType('b');
		assertEquals('b', id.getType());
	}
	
	@Test
	public void testSetName() {
		id.setName("someOtherName");
		assertEquals("someOtherName", id.getName());
	}

	@Test
	public void testSetId() {
		id.setId("someOtherId");
		assertEquals("someOtherId", id.getId());
	}
}
