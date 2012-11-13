package Other;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

import Database.Database;
import Identifiers.Identifier;

@SuppressWarnings("serial")
public class AutoCompleteJComboBox extends JComboBox<String> implements KeyListener {

	private final Vector<Identifier> itemsList = new Vector<Identifier>();
	private final JTextComponent editor;
	private char mode;

	private final Database database;

	public AutoCompleteJComboBox(Database database) {
		super();
		this.database = database;
		this.setEditable(true);
		editor = (JTextComponent) this.getEditor().getEditorComponent();
		editor.addKeyListener(this);
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

	public void DefaultValue(String Thequery) {
		Vector<Identifier> result = database.partialNameQuery(Thequery, this.mode);
		itemsList.clear();

		for (int i = 0; i < result.size(); i++) {
			itemsList.add(i, (result.get(i)));

		}
	}
}
