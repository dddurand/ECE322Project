package Other;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

import Database.Database;
import Identifiers.Identifier;

@SuppressWarnings("serial")
public class AutoCompleteJComboBox extends JComboBox implements KeyListener {

	static Vector<Identifier> itemsList = new Vector<Identifier>();
	JTextComponent editor;
	char mode;

	public AutoCompleteJComboBox() {
		super(itemsList);

		this.setEditable(true);

		editor = (JTextComponent) this.getEditor().getEditorComponent();
		editor.addKeyListener(this);
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {

		String query = editor.getText();

		if (e.getKeyChar() == '\n') {

			String name = editor.getName();

			Vector<Identifier> result = Database.partialNameQuery(query,
					this.mode);
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

		Vector<Identifier> result = Database.partialNameQuery(Thequery,
				this.mode);
		itemsList.clear();

		for (int i = 0; i < result.size(); i++) {
			itemsList.add(i, (result.get(i)));

		}
	}
}
