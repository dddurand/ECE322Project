package Other.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.IllegalComponentStateException;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.text.JTextComponent;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import Database.Database;
import Identifiers.Identifier;
import Other.AutoCompleteJComboBox;

public class AutoCompleteJComboBoxTest {

	private Database database;
	private AutoCompleteJComboBox box;

	@Before
	public void setUp() throws Exception {
		database = EasyMock.createMock(Database.class);
		box = AutoCompleteJComboBox.createAutoCompleteBox(database);
	}

	@Test
	public void testConstructor() {
		assertTrue(box.isEditable());
		assertTrue(box.getItemsList().isEmpty());
	}

	@Test
	public void testGetItemsList() {
		assertEquals(new Vector<Identifier>(), box.getItemsList());
	}

	@Test
	public void testSetMode() {
		box.setMode('a');
		assertEquals('a', box.getMode());
	}

	@Test
	public void testDefaultValue() {
		Vector<Identifier> identifiers = new Vector<Identifier>();
		identifiers.add(new Identifier("anId", "aName", 'c'));
		EasyMock.expect(database.partialNameQuery("partialQuery", 'b')).andReturn(identifiers);
		EasyMock.replay(database);
		box.setMode('b');
		box.DefaultValue("partialQuery");
		assertEquals(1, box.getItemsList().size());
		assertEquals(new Identifier("anId", "aName", 'c'), box.getItemsList().get(0));
	}

	@Test
	public void testKeyTyped_notEnter() {
		KeyEvent event = EasyMock.createMock(KeyEvent.class);
		EasyMock.expect(event.getKeyChar()).andReturn('a');
		EasyMock.replay(event);
		box.keyTyped(event);
		assertEquals(0, box.getItemsList().size());
	}

	@Test
	public void testKeyTyped_enter() {
		JTextComponent textBox = (JTextComponent) box.getEditor().getEditorComponent();
		textBox.setText("asdASD");
		box.setMode('b');
		KeyEvent event = EasyMock.createMock(KeyEvent.class);
		EasyMock.expect(event.getKeyChar()).andReturn('\n');
		Vector<Identifier> identifiers = new Vector<Identifier>();
		identifiers.add(new Identifier("anId", "aName", 'c'));
		EasyMock.expect(database.partialNameQuery("asdASD", 'b')).andReturn(identifiers);
		EasyMock.replay(event, database);
		try {
			box.keyTyped(event);
		} catch (IllegalComponentStateException e) {
			// Expected - since there is no actual physical component on the screen
		}
		assertEquals(1, box.getItemsList().size());
		assertEquals(new Identifier("anId", "aName", 'c'), box.getItemsList().get(0));
	}
}
