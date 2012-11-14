package Other;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

import Database.Database;
import Identifiers.Identifier;

@SuppressWarnings("serial")
public class AutoCompleteJComboBox extends JComboBox<Identifier> implements KeyListener {

	private final Vector<Identifier> itemsList;
	private final JTextComponent editor;
	private char mode;

	private final Database database;

	public AutoCompleteJComboBox(Database database, Vector<Identifier> itemsList) {
		super(itemsList);
		this.itemsList = itemsList;
		this.database = database;
		this.setEditable(true);
		editor = (JTextComponent) this.getEditor().getEditorComponent();
		editor.addKeyListener(this);
	}

	public Vector<Identifier> getItemsList() {
		return itemsList;
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
		String query = editor.getText();
		if (e.getKeyChar() == '\n') {
			Vector<Identifier> result = database.partialNameQuery(query, this.mode);
			itemsList.clear();

			if (result != null) {
				for (int i = 0; i < result.size(); i++) {
					itemsList.add(i, (result.get(i)));
				}
			}
			this.showPopup();
		}
	}

	public void setMode(char mode) {
		this.mode = mode;
	}

	public char getMode() {
		return mode;
	}

	public void DefaultValue(String Thequery) {
		Vector<Identifier> result = database.partialNameQuery(Thequery, this.mode);
		itemsList.clear();
		for (int i = 0; i < result.size(); i++) {
			itemsList.add(i, (result.get(i)));
		}
	}

	public static AutoCompleteJComboBox createAutoCompleteBox(Database database) {
		Vector<Identifier> identifiers = new Vector<Identifier>();
		return new AutoCompleteJComboBox(database, identifiers);
	}
}
